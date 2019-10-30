package com.mol.supplier.config;

import lombok.extern.java.Log;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@Log
public class HttpSessionConfig {

    private static final Map<String, HttpSession> sessions = new HashMap<>();

    public List<HttpSession> getActiveSessions() {
        return new ArrayList<>(sessions.values());
    }

    @Bean
    public HttpSessionListener httpSessionListener() {
        return new HttpSessionListener() {
            @Override
            public void sessionCreated(HttpSessionEvent hse) {
                sessions.put(hse.getSession().getId(), hse.getSession());
                log.info("session created,sessionId:"+hse.getSession().getId());
            }

            @Override
            public void sessionDestroyed(HttpSessionEvent hse) {
                log.info("session destroyed,sessionId:"+hse.getSession().getId());
                sessions.remove(hse.getSession().getId());
            }
        };
    }
}
