package com.sso.controller;

import com.sso.domain.UserDetails;
import com.sso.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author mark huang
 * @since 2023/4/7
 */
@RestController
public class LoginController {

    @Resource
    private AuthService authService;

    @PostMapping("/login-by-cas")
    public UserDetails loginByCas(String ticket, String service) {

        return authService.loginByCas(ticket, service);
    }
}
