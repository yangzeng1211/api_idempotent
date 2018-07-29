package com.yz.controller;

import com.yz.annotation.CheckToken;
import com.yz.annotation.CreatToken;
import com.yz.entity.User;
import com.yz.service.UserService;
import com.yz.utils.ConstantUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 处理表单提交请求
 * Created by yz on 2018/7/29.
 */
@Controller
public class UserPageController {

    @Autowired
    private UserService userService;

    /**
     * 页面跳转,使用自定义注解生成token,传递到跳转页面中
     * @param req
     * @return
     */
    @RequestMapping("/indexPage")
    @CreatToken
    public String indexPage(HttpServletRequest req){
        //req.setAttribute("token",redisToken.getToken());
        return "index";
    }

    // 使用CheckToken注解方式保证请求幂等性
    @RequestMapping(value = "/addUserForPage")
    @CheckToken(type = ConstantUtils.EXTAPIFROM)
    @ResponseBody
    public String addOrder(User user, HttpServletRequest request){
        // 业务逻辑
        int result = userService.addUser(user);
        return result >0 ? "添加成功" : "添加失败";
    }
}
