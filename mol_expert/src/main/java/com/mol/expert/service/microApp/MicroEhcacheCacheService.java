package com.mol.expert.service.microApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class MicroEhcacheCacheService {

    @Autowired
    private MicroJsapiTicketCacheService microJsapiTicketCacheService;
    @Autowired
    private MicroSmsCacheService microSmsCacheService;
    @Autowired
    private MicroTokenCacheService microTokenCacheService;

    public MicroJsapiTicketCacheService getMicroJsapiTicketCacheService() {
        return microJsapiTicketCacheService;
    }

    public MicroSmsCacheService getMicroSmsCacheService() {
        return microSmsCacheService;
    }

    public MicroTokenCacheService getMicroTokenCacheService() {
        return microTokenCacheService;
    }
}

@Service
class MicroJsapiTicketCacheService {
    static final String cacheName = "jsapiticket";

    @CachePut(value = cacheName, key = "#name")
    public Object put(String name, Object obj) {
        return obj;
    }

    @CacheEvict(value = cacheName, allEntries = true)
    public void deleteAll() {
    }

    @CacheEvict(value = cacheName, key = "#name")
    public void delete(String name) {
    }

    @Cacheable(value = cacheName, key = "#name")
    public Object get(String name) {
        return null;
    }
}

@Service
class MicroSmsCacheService {

    public static final String cacheName = "msm";

    @CachePut(value = cacheName, key = "#name")
    public Object put(String name, Object obj) {
        return obj;
    }

    @CacheEvict(value = cacheName, allEntries = true)
    public void deleteAll() {
    }

    @CacheEvict(value = cacheName, key = "#name")
    public void delete(String name) {
    }

    @Cacheable(value = cacheName, key = "#name")
    public Object get(String name) {
        return null;
    }


}

@Service
class MicroTokenCacheService {
    public static final String cacheName = "microtoken";

    @CachePut(value = cacheName, key = "#name")
    public Object put(String name, Object obj) {
        return obj;
    }

    @CacheEvict(value = cacheName, allEntries = true)
    public void deleteAll() {
    }

    @CacheEvict(value = cacheName, key = "#name")
    public void delete(String name) {
    }

    @Cacheable(value = cacheName, key = "#name")
    public Object get(String name) {
        return null;
    }
}
