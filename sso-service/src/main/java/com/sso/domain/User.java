package com.sso.domain;

import java.util.List;

/**
 * @author mark huang
 * @since 2023/4/7
 */
public class User implements UserDetails {

    private Integer id;
    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 记录状态（active, inactive）
     */
    private String status;
    /**
     * 通讯token
     */
    private String authToken;
    /**
     * 角色
     */
    private List<String> roles;
    /**
     * 权限
     */
    private List<String> permissions;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    @Override
    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    @Override
    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}
