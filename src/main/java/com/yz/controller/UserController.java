package com.yz.controller;

import com.yz.annotation.CheckToken;
import com.yz.entity.User;
import com.yz.service.UserService;
import com.yz.utils.ConstantUtils;
import com.yz.utils.RedisToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 处理rpc调用请求
 * Created by yz on 2018/7/29.
 */
@RestController
public class UserController {

    @Autowired
    private RedisToken redisToken;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/createRedisToken")
    public String createRedisToken(){
        return redisToken.getToken();
    }

    // 使用CheckToken注解方式保证请求幂等性
    @RequestMapping(value = "/addUser")
    @CheckToken(type = ConstantUtils.EXTAPIHEAD)
    public String addOrder(User user, HttpServletRequest request){
        // 业务逻辑
        int result = userService.addUser(user);
        return result >0 ? "添加成功" : "添加失败";
    }

}
