package com.bh.dao;

import com.bh.pojo.User;

public interface mapper {
    //通过id查找用户
    public User findUserById(int id);
}
