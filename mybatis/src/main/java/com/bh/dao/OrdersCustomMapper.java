package com.bh.dao;

import com.bh.pojo.*;

import java.util.List;

public interface OrdersCustomMapper {
    public List<OrdersCustom> findOrdersList();

    List<Orders> findOrdersListResultMap();

    List<Orderdetail> findOrdersDetailList();//查询订单及订单下的详情信息

    List<Orderdetail> findUserOrderListResultMap();

    List<Orders> findOrdersList3();

    public User findUserById(int id);

}
