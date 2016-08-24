package com.spring.mybatis.service.impl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.spring.mybatis.dao.UserMapper;
import com.spring.mybatis.model.User;
import com.spring.mybatis.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Resource
	private UserMapper userMapper;

	@Override
	public User getUser(int userId) {
		
		return userMapper.selectByPrimaryKey(userId);
	}

	@Override
	public void addUser(User user) {
		
		userMapper.insert(user);
	}

	@Override
	public void deleteUser(int userId) {
		
		userMapper.deleteByPrimaryKey(userId);
	}

	@Override
	public void updateUser(User user) {
		
		userMapper.updateByPrimaryKeySelective(user);
	}

	@Override
	public User getUserByAccount(String account) {
		
		return userMapper.getUserByAccount(account);
	}

}
