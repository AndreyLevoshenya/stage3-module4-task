package com.mjc.school.service.implementation.config;

import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@AutoConfigureTestEntityManager
@ComponentScan(basePackages = {"com.mjc.school.service", "com.mjc.school.repository"})
public class TestConfig {
}
