package com.ezab.backendMarket.config;


import com.ezab.backendMarket.converters.OrderConverter;
import com.ezab.backendMarket.converters.ProductConverter;
import com.ezab.backendMarket.converters.UserConverter;
import com.ezab.backendMarket.helpers.TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

@Configuration
public class BeanConfiguration {

    @Value("${config.datetimeFormat}")
    private String datetimeFormatter;

    @Bean
    public ProductConverter getProductConverter() {
        return new ProductConverter();
    }

    @Bean
    public OrderConverter getOrderConverter() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern(datetimeFormatter);
        return new OrderConverter(format, getProductConverter(), getUserConverter());
    }

    @Bean
    public UserConverter getUserConverter() {
        return new UserConverter();
    }


    @Bean
    public TokenService getTokenService() {
        return new TokenService();
    }

}
