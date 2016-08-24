package com.spring.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.mybatis.model.Role;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
    
    List<String> getRoles(@Param("account")String account);
}