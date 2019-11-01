package com.mol.supplier.config;

import com.mol.supplier.interceptor.SessionInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import util.UploadUtils;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    //spring拦截器加载在springcontentText之前，所以这里用@Bean提前加载。否则会导致过滤器中的@AutoWired注入为空
    @Bean
    SessionInterceptor jwtInterceptor(){
        return new SessionInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        System.out.println("session拦截器启动");
        registry.addInterceptor(jwtInterceptor())
                .excludePathPatterns("static/**/**","/microApp/login/**","/callbackto","/error","/callback","**/img/**","/index/**","**/js/**","**/css/**","/static/upload/**","/templates/**","/index/findAll","/css/**","/js/**","/img/**","/layui/**","/actuator/**","/msg/**","/microApp/**/**","/microApp/regist/**")
                .addPathPatterns("/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/upload/**").addResourceLocations("file:"+UploadUtils.getDirFile().getAbsolutePath()+"/imgs","file:"+ UploadUtils.getDirFile().getAbsolutePath()+"/videos","file:"+UploadUtils.getDirFile().getAbsolutePath()+"/documents","file:"+UploadUtils.getDirFile().getAbsolutePath()+"/sounds","classpath:/templates");
        registry.addResourceHandler("/index/**").addResourceLocations("classpath:/templates");
    }

}
