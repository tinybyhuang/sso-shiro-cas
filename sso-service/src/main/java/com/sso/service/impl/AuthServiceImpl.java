package com.sso.service.impl;

import com.sso.domain.AuthenticationException;
import com.sso.domain.User;
import com.sso.domain.UserDetails;
import com.sso.service.AuthService;
import org.apache.commons.lang.StringUtils;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.Cas20ProxyTicketValidator;
import org.jasig.cas.client.validation.TicketValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @author mark huang
 * @since 2023/4/7
 */
@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Resource
    private Cas20ProxyTicketValidator ticketValidator;

    @Override
    public UserDetails loginByCas(String ticket, String service) {
        String account;
        try {
            Assertion assertion = ticketValidator.validate(ticket, service);
            account = assertion.getPrincipal().getName();
        } catch (TicketValidationException e) {
            log.error("loginByCas failed, ticket:{}, service:{}", ticket, service);
            throw new AuthenticationException("登录失败");
        }
        if (StringUtils.isBlank(account)) {
            return null;
        }
        log.info("loginByCas succeed, ticket:{}, service:{}, account:{}", ticket, service, account);

        //自行实现根据账号获取用户信息的业务逻辑
        return buildDefaultUser(account);
    }

    private UserDetails buildDefaultUser(String account) {
        User defaultUser = new User();
        defaultUser.setId(1);
        defaultUser.setUserName("sso");
        defaultUser.setStatus("active");
        defaultUser.setAuthToken("sso");
        defaultUser.setRoles(new ArrayList<>());
        defaultUser.setPermissions(new ArrayList<>());

        return defaultUser;
    }
}
