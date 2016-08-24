package com.spring.mybatis.service;

import java.util.List;

public interface RoleService {
	
	/**
	 * @description 获取权限
	 * @author wchuang
	 * @time 2016年8月22日 下午12:00:49
	 */
	List<String> getPermissions(String account);
	
	/**
	 * @description 获取角色
	 * @author wchuang
	 * @time 2016年8月22日 下午12:00:59
	 */
	List<String> getRoles(String account);
}
