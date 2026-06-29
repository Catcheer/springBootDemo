package com.example.springbootdemo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    /**
     * jwt密钥
     */
    private String secret;

    /**
     * access token过期时间
     */
    private Long accessExpiration;

    /**
     * refresh token过期时间
     */
    private Long refreshExpiration;
}