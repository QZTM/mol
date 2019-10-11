package com.mol.ddmanage.Hander;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Intercept_Config implements WebMvcConfigurer
{

    @Autowired
    intercept intercept1;
    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(intercept1).excludePathPatterns("/Login/verification_login","/Home/new_file").addPathPatterns("/**");

    }
}
