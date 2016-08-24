package com.spring.mybatis.service;

import com.spring.mybatis.model.User;

public interface UserService {
	
	User getUser(int userId);
	
	void addUser(User user);
	
	void deleteUser(int userId);
	
	void updateUser(User user);
	
	/**
	 * @description 获取用户信息
	 * @author wchuang
	 * @time 2016年8月22日 下午12:01:15
	 */
	User getUserByAccount(String account);

}
