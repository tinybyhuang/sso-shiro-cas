package com.sso.service;

import com.sso.domain.UserDetails;

/**
 * @author mark huang
 * @since 2023/4/7
 */
public interface AuthService {

    UserDetails loginByCas(String ticket, String service);
}
