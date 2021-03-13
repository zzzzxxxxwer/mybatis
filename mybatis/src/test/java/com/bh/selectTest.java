package com.bh;

import com.bh.dao.OrdersCustomMapper;
import com.bh.pojo.Orderdetail;
import com.bh.pojo.Orders;
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
import java.util.List;

public class selectTest {
    //每次执行前首先加载会话工厂
    private SqlSessionFactory sqlSessionFactory;// SqlSession:创建sesssion工厂

    @Before
    public void createSqlSessionFactory() throws IOException {
        //获取配置文件
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        //创建SqlSessionFactory
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //SqlSessionFactory是通过 SqlSessionFactoryBuilder 进行创建
    }

    //一对多查询:查询订单及订单下的详情信息
    @Test
    public void testFindOrdersDetailList() {     //获取 session
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //获取代理对象
        OrdersCustomMapper mapper = sqlSession.getMapper(OrdersCustomMapper.class);
        //调用方法完成功能
        List<Orderdetail> ordersDetailList = mapper.findOrdersDetailList();
        System.out.println(ordersDetailList.size());
        System.out.println(ordersDetailList);
        sqlSession.close();//释放资源
    }

    //多对多查询
    @Test
    public void testfind() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //获取代理对象
        OrdersCustomMapper mapper = sqlSession.getMapper(OrdersCustomMapper.class);
        //调用方法完成功能
        List<Orderdetail> orderdetailList = mapper.findUserOrderListResultMap();
        System.out.println(orderdetailList);
        sqlSession.close();//释放资源
    }
    //一对一查询延迟加载
    @Test
    public void testfindOrdersList3() {   //获取session
        SqlSession session = sqlSessionFactory.openSession();
        OrdersCustomMapper userMapper = session.getMapper(OrdersCustomMapper.class);  //获限mapper接口实例
        List<Orders> list = userMapper.findOrdersList3();//查询订单信息
        System.out.println(list);
        //开始加载，通过orders.getUser方法进行加载
        for (Orders orders : list) {
            System.out.println(orders.getUser());
        }   //关闭session
        session.close();
    }
}
