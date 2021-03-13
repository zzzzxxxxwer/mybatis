package com.bh;

import com.bh.dao.UserMapper;
import com.bh.dao.OrdersCustomMapper;
import com.bh.pojo.*;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class UserMapperTest {
    //每次执行前首先加载会话工厂
    private SqlSessionFactory sqlSessionFactory;// SqlSession:创建sesssion工厂

    @Before
/* 执行@Test前先加载createSqlSessionFactory()方法：创建sesssion工厂
    或者写成static方法，每次执行之前都先加载static静态代码块*/
    public void createSqlSessionFactory() throws IOException {
        //获取配置文件
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        //创建SqlSessionFactory
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //SqlSessionFactory是通过 SqlSessionFactoryBuilder 进行创建
    }
   /* SqlSession 中封装了对数据库的操作，如：查询selectone selectList/Map 、插入Insert、更新update、删除delete。
     通过 SqlSessionFactory 创建 SqlSession，而 SqlSessionFactory 是通过 SqlSessionFactoryBuilder 进行创建*/

    //Mapper.java接口测试
    @Test
    public void testFindUserByIdtwo() throws Exception {
        SqlSession session = sqlSessionFactory.openSession();//获取session
        UserMapper userMapper = session.getMapper(UserMapper.class);//获取mapper接口的代理对象
        User user = userMapper.findUserById(31);
        //调用代理对象findUserById()方法
        System.out.println(user);
        session.close();
    }

    @Test
    public void testFindUserByUsernameone() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        List<User> list = userMapper.findUserByName("周");
        System.out.println(list);
    }

    @Test
    public void testInsertUser() {
        SqlSession session = sqlSessionFactory.openSession();//获取session
        UserMapper userMapper = session.getMapper(UserMapper.class);//获取mapper接口的代理对象
        //要添加的数据
        User user = new User();
        user.setUsername("张三");
        user.setBirthday(new Date());
        user.setSex("男");
        user.setAddress("南京市");
        userMapper.insertUser(user);//通过调用mapper接口insertUser()方法添加用户
        session.commit();
        session.close();//关闭session
    }

    @Test
    public void testFindUserByUser() {
        //获取session
        SqlSession session = sqlSessionFactory.openSession();
        //创建mapper接口实例
        UserMapper userMapper = session.getMapper(UserMapper.class);
        //构造查询条件user对象
        User user = new User();
        user.setId(10);
        user.setUsername("李");
        //传递user对象查询用户列表
        List<User> list = userMapper.findUserByUser(user);
        System.out.println(list);
        session.close(); //关闭session
    }

    //用户信息综合查询即多条件查询
    @Test
    public void testFindUserList() {
        //创建对象--获取 session
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //创建 UserMapper对象，mybatis自动生成 mapper代理对象
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        //创建包装对象
        UserQueryVo userQueryVo = new UserQueryVo();
//        UserCustom userCustom = new UserCustom();
        User user = new User();
        user.setSex("女");
        user.setUsername("周涛");
        //传入多个 id
        ArrayList<Integer> ids = new ArrayList<>();
       /* ids.add(1);
        ids.add(2);
        ids.add(3);*/
        //将 ids 通过 userQueryVo 传入 statement 中。
        userQueryVo.setIds(ids);
        userQueryVo.setUser(user);
        //调用方法完成多条件查询
        List<UserQueryVo> userList = userMapper.findUserList(userQueryVo);
        System.out.println(userList);
        sqlSession.close(); //释放资源
    }

    //传递 HashMap 综合查询用户信息
    @Test
    public void testFindUserByHashMap() {
        //获取 session
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //获取 mapper 接口实例
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        //构造查询条件，即 Map 对象
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", 31);
        map.put("username", "周");
        //传递对象完成查询
        List<User> list = userMapper.findUserByHashMap(map);
        System.out.println(list);
        sqlSession.close();//释放资源
    }

    //输出简单类型
    @Test
    public void testFindUserCount() {
        //获取 session
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //获取接口对象
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = new User();
        user.setUsername("李三");
        //传递对象完成查询
        int count = userMapper.findUserByCount(user);
        System.out.println(count);
        //释放 session
        sqlSession.close();
    }

    @Test
    public void testFindUserById() {
        //获取session
        SqlSession session = sqlSessionFactory.openSession();
        //获得mapper接口实例
        UserMapper userMapper = session.getMapper(UserMapper.class);
        //通过mapper接口调用statement
        User user = userMapper.findUserById(1);
        System.out.println(user);
        //关闭session
        session.close();
    }

    @Test
    public void testFindUserByUsername() {
        //获取session
        SqlSession session = sqlSessionFactory.openSession();
        UserMapper userMapper = session.getMapper(UserMapper.class);//获限mapper接口实例
        //如果使用占位符号则必须在传参数中加%
//        List<User> list1 = userMapper.selectUserByName("%管理员%");
        //如果使用${}原始符号则不用人为在参数中加%
        List<User> list = userMapper.findUserByName("小");
        System.out.println(list);
        //关闭session
        session.close();
    }

    //根据 id 查询用户信息，使用 resultMap 输出
    @Test
    public void testFindUserByIdResultMap() {
        SqlSession sqlSession = sqlSessionFactory.openSession();//获取对象
        //获取 UserMapper 对象，mybatis 自动生成代理对象
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        //调用方法完成功能
        List<User> user = userMapper.findUserByIdResultMap(31);
        System.out.println(user);
        //释放资源
        sqlSession.close();
    }

    //用户信息综合查询即多条件查询
    @Test
    public void testFindUserListone() {
        //创建对象--获取 session
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //创建 UserMapper 对象，mybatis 自动生成 mapper 代理对象
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        UserQueryVo userQueryVo = new UserQueryVo();//创建包装对象
        UserCustom userCustom = new UserCustom();//创建包装对象
        userCustom.setUsername("周");
        userCustom.setSex("女");
        //传入多个 id
        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(33);
        ids.add(34);
        ids.add(35);
        //将 ids 通过 userQueryVo 传入 statement 中
        userQueryVo.setIds(ids);
        userQueryVo.setUserCustom(userCustom);
        //调用方法完成多条件查询
        List<UserQueryVo> userList = userMapper.findUserListone(userQueryVo);
        System.out.println(userList);
        //通过 pojo 传递 list
        List<Integer> ids1 = new ArrayList<Integer>();
        ids.add(1);//查询id为1的用户
        ids.add(10); //查询id为10的用户
        userQueryVo.setIds(ids);
        List<UserQueryVo> list = userMapper.findUserList(userQueryVo);
        System.out.println(list);
        sqlSession.close();  //释放资源
    }

    //传递单个 List
    @Test
    public void testselectUserByList() {
        //获取session
        SqlSession session = sqlSessionFactory.openSession();
        //获得mapper接口实例
        UserMapper userMapper = session.getMapper(UserMapper.class);
        List<User> userlist = new ArrayList<User>();//构造查询条件List:new List集合
        User user = new User();
        user.setId(1);
        userlist.add(user);
        user = new User();//新建user对象，重新new User类的对象= User user1=new User();
        user.setId(10);
        userlist.add(user);
        User user1 = new User();
        user1.setId(34);
        userlist.add(user1);
        //传递userlist列表查询用户列表
        List<User> list = userMapper.selectUserByList(userlist);
        System.out.println(list);
        session.close(); //关闭session
    }

    //递单个数组（数组中是 pojo）
    @Test
    public void testselectUserByArray() {
        //获取session
        SqlSession session = sqlSessionFactory.openSession();
        //获限mapper接口实例
        UserMapper userMapper = session.getMapper(UserMapper.class);
        //构造查询条件List：new userlist数组
        Object[] userlist = new Object[2];
        User user = new User();
        user.setId(36);
        userlist[0] = user;
        user = new User();
        user.setId(37);
        userlist[1] = user;
        List<User> list = userMapper.selectUserByArray(userlist); //传递user对象查询用户列表
        System.out.println(list);
        //关闭session
        session.close();
    }

    @Test
    public void testselectUserByArraytwo() {
        //获取session
        SqlSession session = sqlSessionFactory.openSession();
        //获限mapper接口实例
        UserMapper userMapper = session.getMapper(UserMapper.class);
        //构造查询条件List
        Object[] userlist = new Object[2];
        userlist[0] = "29";
        userlist[1] = "37";
        List<User> list = userMapper.selectUserByArraytwo(userlist);//传递user对象查询用户列表
        System.out.println(list);
        session.close();  //关闭session
    }

    //查询所有订单
    @Test
    public void testFindOrdersList() {
        SqlSession sqlSession = sqlSessionFactory.openSession();//获取 session
        //获取接口对象
        OrdersCustomMapper ordersCustomMapper = sqlSession.getMapper(OrdersCustomMapper.class);
        //调用方法完成功能
        List<OrdersCustom> ordersList = ordersCustomMapper.findOrdersList();
        System.out.println(ordersList);
        sqlSession.close(); //释放资源
    }

    //查询所有订单：方式二
    @Test
    public void testFindOrdersListResultMap() {
        SqlSession sqlSession = sqlSessionFactory.openSession();//获取 session
        OrdersCustomMapper ordersCustomMapper = sqlSession.getMapper(OrdersCustomMapper.class);       //获取代理对象
        //调用方法完成功能
        List<Orders> ordersListResultMap = ordersCustomMapper.findOrdersListResultMap();
        System.out.println(ordersListResultMap);
        System.out.println(ordersListResultMap.size());
        //释放资源
        sqlSession.close();
    }
    //一对多查询:查询订单及订单下的详情信息
    @Test
    public void testFindOrdersDetailList() {
        SqlSession sqlSession = sqlSessionFactory.openSession(); //获取 session
        //获取代理对象
        OrdersCustomMapper mapper = sqlSession.getMapper(OrdersCustomMapper.class);
        //调用方法完成功能
        List<Orderdetail> ordersDetailList = mapper.findOrdersDetailList();
        System.out.println(ordersDetailList.size());
        System.out.println(ordersDetailList);
        sqlSession.close();//释放资源
    }

}

