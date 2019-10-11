package com.mol.supplier;

import com.mol.cache.CacheHandle;
import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import util.IdWorker;

@EnableScheduling
@EnableAsync
@Configuration
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class SupplierApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SupplierApplication.class);
    }

        @Bean
        public IdWorker getIdWork(){
            return new IdWorker();
        }

        @Bean
        public CacheHandle getCacheHandler(){
            return CacheHandle.getCacheHandle();
        }
}
