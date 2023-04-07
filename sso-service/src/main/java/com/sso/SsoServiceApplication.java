package com.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.sso")
public class SsoServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsoServiceApplication.class, args);
    }

}
