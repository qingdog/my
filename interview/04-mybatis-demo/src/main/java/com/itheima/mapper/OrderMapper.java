package com.itheima.mapper;

import com.itheima.pojo.Order;
import com.itheima.pojo.User;

import java.util.List;

public interface OrderMapper {

    List<Order> findByUid(Integer id);

}
