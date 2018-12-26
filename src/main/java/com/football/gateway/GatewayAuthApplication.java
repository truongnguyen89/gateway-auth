package com.football.gateway;

import com.football.gateway.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
@EnableZuulProxy
public class GatewayAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayAuthApplication.class, args);
    }
}
