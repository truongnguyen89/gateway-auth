package com.football.gateway;

import com.football.gateway.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class GatewayAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayAuthApplication.class, args);
    }
}
