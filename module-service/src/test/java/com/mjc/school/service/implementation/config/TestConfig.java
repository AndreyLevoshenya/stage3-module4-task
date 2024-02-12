package com.mjc.school.service.implementation.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootConfiguration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = {"com.mjc.school.service", "com.mjc.school.repository"})
public class TestConfig {
}
