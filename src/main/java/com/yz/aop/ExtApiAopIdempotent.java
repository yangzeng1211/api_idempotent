package com.yz.aop;

import com.yz.annotation.CheckToken;
import com.yz.annotation.CreatToken;
import com.yz.utils.ConstantUtils;
import com.yz.utils.RedisToken;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 接口幂等切面
 * Created by yz on 2018/7/29.
 */
@Aspect
@Component
public class ExtApiAopIdempotent {

    @Autowired
    private RedisToken redisToken;

    // 切入点，拦截所有请求
    @Pointcut("execution(public * com.yz.controller.*.*(..))")
    public void rlAop(){}

    // 前置通知，生成Token
    @Before("rlAop()")
    public void before(JoinPoint point){
        MethodSignature signature = (MethodSignature) point.getSignature();
        CreatToken declaredAnnotation = signature.getMethod().getDeclaredAnnotation(CreatToken.class);
        if(declaredAnnotation != null){
            getRequest().setAttribute("token",redisToken.getToken());
        }
    }

    // 环绕通知拦截所有访问
    @Around("rlAop()")
    public Object doBefore(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // 判断方法上是否有加ExtApiAopIdempotent注解
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        CheckToken declaredAnnotation = methodSignature.getMethod().getDeclaredAnnotation(CheckToken.class);
        if(declaredAnnotation != null){
            String type = declaredAnnotation.type();
            String token = null;
            HttpServletRequest request = getRequest();
            if(type.equals(ConstantUtils.EXTAPIHEAD)){
                // 获取请求头中的token令牌
                token = request.getHeader("token");
            }else{
                // 从表单中获取token
                token = request.getParameter("token");
            }
            if(StringUtils.isEmpty(token)){
                return "参数错误";
            }
            // 校验token
            boolean isToken = redisToken.checkToken(token);
            if(!isToken){
                return "请勿重复提交!";
            }
        }
        // 放行
        Object proceed = proceedingJoinPoint.proceed();
        return proceed;
    }

    public HttpServletRequest getRequest(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        return request;
    }

    public void response(String msg)throws IOException{
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = attributes.getResponse();
        response.setHeader("Content-type","text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        try {
            writer.print(msg);
        } finally {
            writer.close();
        }
    }

}
