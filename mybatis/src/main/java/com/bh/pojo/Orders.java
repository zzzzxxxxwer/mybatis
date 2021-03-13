package com.bh.pojo;

import java.util.List;

public class Orders {
    private int id;
    private int userId;
    private int number;
    private String username;   // 用户名称
    private String address;    // 用户地址
    private User user;
    private Orderdetail orderdetails;
    List<Orderdetail> orderdetail;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Orderdetail getOrderdetails() {
        return orderdetails;
    }

    public void setOrderdetails(Orderdetail orderdetails) {
        this.orderdetails = orderdetails;
    }

    public List<Orderdetail> getOrderdetail() {
        return orderdetail;
    }

    public void setOrderdetail(List<Orderdetail> orderdetail) {
        this.orderdetail = orderdetail;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", userId=" + userId +
                ", number=" + number +
                ", username='" + username + '\'' +
                ", address='" + address + '\'' +
//                ", user=" + user +
                ", orderdetails=" + orderdetails +
                ", orderdetail=" + orderdetail +
                '}';
    }
}
