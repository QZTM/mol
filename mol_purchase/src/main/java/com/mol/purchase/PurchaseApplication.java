package com.mol.purchase;

import com.mol.cache.CacheHandle;
import com.mol.notification.SendNotification;
import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import util.IdWorker;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableCaching
@EnableScheduling
@EnableAsync
@Configuration
public class PurchaseApplication {
    public static void main(String[] args) {

        SpringApplication.run(PurchaseApplication.class,args);
    }



    @Bean("idWork")
    public IdWorker getIdWork(){
        return new IdWorker();
    }

    @Bean
    public CacheHandle getCacheHandler(){
        return CacheHandle.getCacheHandle();
    }

    @Bean
    public SendNotification getSendNotification(){
        return SendNotification.getSendNotification();
    }


}
