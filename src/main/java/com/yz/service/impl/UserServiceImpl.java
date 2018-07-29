package com.yz.service.impl;

import com.yz.entity.User;
import com.yz.mapper.UserMapper;
import com.yz.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by yz on 2018/7/29.
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public int addUser(User user) {
        return userMapper.addUser(user);
    }
}
