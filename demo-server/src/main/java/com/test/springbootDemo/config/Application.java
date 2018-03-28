package com.test.springbootDemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * 应用配置
 * 
 */
@Configuration
@ComponentScan(basePackages = {"com.test.springbootDemo"})
@EnableAspectJAutoProxy
public class Application {
    /**
     * PropertySourcesPlaceholderConfigurer
     * @return bean
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholder() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}