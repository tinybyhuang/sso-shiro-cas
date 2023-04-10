package com.sso.domain;

/**
 * @author mark huang
 * @since 2023/4/7
 */
public class AuthenticationException extends RuntimeException {
    public AuthenticationException() {
    }

    public AuthenticationException(String message) {
        super(message);
    }
}
