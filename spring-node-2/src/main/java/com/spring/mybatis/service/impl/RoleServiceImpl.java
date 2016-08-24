package com.spring.mybatis.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.spring.mybatis.dao.PermissionMapper;
import com.spring.mybatis.dao.RoleMapper;
import com.spring.mybatis.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
	@Resource
	private RoleMapper roleMapper;
	@Resource
	private PermissionMapper permissionMapper;

	@Override
	public List<String> getPermissions(String account) {
		
		return permissionMapper.getPermissions(account);
	}

	@Override
	public List<String> getRoles(String account) {
		
		return roleMapper.getRoles(account);
	}

}
