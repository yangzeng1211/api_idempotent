package com.yz.mapper;

import com.yz.entity.User;

/**
 * Created by yz on 2018/7/29.
 */
public interface UserMapper {
    int addUser(User user);
    User getUser(int id);
    void removeUser(int id);
}
