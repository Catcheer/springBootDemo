package com.example.springbootdemo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class StartupLogger implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(StartupLogger.class);
    private final Environment env;

    public StartupLogger(Environment env) {
        this.env = env;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            log.info("Startup: spring.redis.host={}, SPRING_REDIS_HOST(env)={}", env.getProperty("spring.redis.host"), System.getenv("SPRING_REDIS_HOST"));
            log.info("Startup: System.getProperty(spring.redis.host)={}", System.getProperty("spring.redis.host"));
        } catch (Exception e) {
            log.warn("Failed to log startup properties", e);
        }
    }
}
