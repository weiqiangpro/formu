package com.formu.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.formu.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import javax.sql.DataSource;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource dataSource() {
        return new DruidDataSource();
    }

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/login", "/article/get/*", "/article/getbyid/*","/auth","/YB/login","/ybsuccess.html","/yb","/text.html",
                                     "/article/getbyuser/*/*","/category/get", "/pho/**","/user/follows/*","/user/followeds/*",
                                     "/user/other/*","/user/send","/upfile","/comment/get/*/*","/user/register","/user/registeremail",
                                     "/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");
    }



}
