package com.bh.dao;

import com.bh.pojo.OrdersCustom;
import com.bh.pojo.User;
import com.bh.pojo.UserQueryVo;

import java.util.HashMap;
import java.util.List;
/**
 * 用户管理mapper接口  */
public interface UserMapper {
//    public User findUserById(int id);
    public List<User> findUserByName(String username);
    public void insertUser(User user);
    public List<User> findUserByUser(User user);
    public List<UserQueryVo> findUserList(UserQueryVo userQueryVo);
    public List<User> findUserByHashMap(HashMap map);
    int findUserByCount(User user);
    User findUserById(int id);
    List<User> findUserByIdResultMap(int id);
    public List<UserQueryVo> findUserListone(UserQueryVo userQueryVo);
    List<User> selectUserByList(List userlist);
    List<User> selectUserByArray(Object[] userlist);
    List<User> selectUserByArraytwo(Object[] userlist);
    void updateUser(User user);//修改数据
}/*
 Mapper接口方法名()和 Mapper.xml 中定义的 statement 的 id 相同
 Mapper接口方法的输入参数类型:形参和 mapper.xml中定义的 statement 的 parameterType 的类型相同
 Mapper接口方法的输出参数类型和 mapper.xml中定义的 statement 的 resultType 的类型相同
*/
