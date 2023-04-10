package com.sso.domain;

import java.util.List;

/**
 * @author mark huang
 * @since 2023/4/7
 */
public interface UserDetails {

    Integer getId();

    String getUserName();

    String getStatus();

    String getAuthToken();

    List<String> getRoles();

    List<String> getPermissions();

}
