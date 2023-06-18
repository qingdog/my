package com.itheima.pojo;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class User  implements Serializable {

    private Integer id;
    private String username;
    private String password;
    private String gender;
    private String addr;
    private LocalDateTime birthday;
    private List<Order> orderList;



    public User(){

    }

    public User(String username,String password,String gender,String addr){
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.addr = addr;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", addr='" + addr + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
