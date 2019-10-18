package com.mol.expert;

import com.mol.cache.CacheHandle;
import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableCaching
@EnableScheduling
@EnableAsync
@Configuration
public class CloudPurchaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudPurchaseApplication.class, args);
    }

    @Bean
    public CacheHandle getCacheHandler(){
        return CacheHandle.getCacheHandle();
    }
}
