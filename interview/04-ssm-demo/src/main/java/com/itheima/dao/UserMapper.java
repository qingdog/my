package com.itheima.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.pojo.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from t_user where id = #{id}")
    public User getById(Integer id);

    public void save(User user);

    public void update(User user);

    @Delete("delete from t_user where id = #{id}")
    public void delete(Integer id);
}
