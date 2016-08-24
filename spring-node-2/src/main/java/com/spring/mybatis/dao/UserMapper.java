package com.spring.mybatis.dao;

import org.apache.ibatis.annotations.Param;

import com.spring.mybatis.model.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    User getUserByAccount(@Param("account")String account);
}