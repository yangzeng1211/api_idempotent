package com.yz.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * 生成token,放入redis中
 * Created by yz on 2018/7/29.
 */
@Component
public class RedisToken {

    @Autowired
    private BaseRedisService baseRedisService;

    private static final long TOKENTIME = 60*60;

    public String getToken(){
        String token = "token"+UUID.randomUUID();
        baseRedisService.setString(token,token,TOKENTIME);
        return token;
    }

    public boolean checkToken(String tokenKey){
        String tokenValue = baseRedisService.getString(tokenKey);
        if(StringUtils.isEmpty(tokenValue)){
            return false;
        }
        // 保证每个接口对应的token只能访问一次，保证接口幂等性问题
        baseRedisService.delKey(tokenKey);
        return true;
    }
}
