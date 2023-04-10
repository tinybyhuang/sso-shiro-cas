package com.sso.config;

import org.jasig.cas.client.validation.Cas20ProxyTicketValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author markhuang
 * @since 2023/4/7
 */
@Configuration
public class CasClientConfig {

    @Value("${cas.server.url-prefix}")
    private String casServerUrlPrefix;

    @Bean
    public Cas20ProxyTicketValidator ticketValidator() {
        Cas20ProxyTicketValidator ticketValidator = new Cas20ProxyTicketValidator(casServerUrlPrefix);
        ticketValidator.setAcceptAnyProxy(true);
        ticketValidator.setProxyCallbackUrl("/proxy/receptor");
        return ticketValidator;
    }

}
