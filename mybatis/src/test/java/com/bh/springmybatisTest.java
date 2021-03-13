package com.bh;

import com.bh.dao.OrdersCustomMapper;
import com.bh.dao.UserMapper;
import com.bh.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.io.InputStream;

public class springmybatisTest {
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

    //使用 mapper 扫描器
    @Test
    public void test() {
        // 1.加载配置文件，创建spring容器
        ApplicationContext act = new ClassPathXmlApplicationContext("applicationContext.xml");
        OrdersCustomMapper mapper = act.getBean(OrdersCustomMapper.class);
        User user = mapper.findUserById(1);
        System.out.println(user);
    }

    //一级缓存测试
    @Test
    public void oneTest() throws Exception {
        //获取sqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //获取mapper接口的代理对象
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user1 = userMapper.findUserById(1);
        System.out.println(user1);
        //第二次查询，由于是同一个session则不再向数据发出语句直接从缓存取出
        User user2 = userMapper.findUserById(1);
        System.out.println(user2);
        if (sqlSession != null) {
            sqlSession.close();
        }
    }

    //一级缓存测试2
    @Test
    public void twoTest() throws Exception {
        //获取sqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //获取mapper接口的代理对象
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user1 = userMapper.findUserById(1);
        System.out.println(user1);
        //执行更新
        User user2 = new User();
        user2.setId(1);
        user2.setUsername("小红");
        user2.setSex("女");
        userMapper.updateUser(user2);
        System.out.println(user2);
        if (sqlSession != null) {
            sqlSession.close();
        }
    }

}
