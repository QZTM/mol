package com.mol.cache;

import com.alibaba.fastjson.JSON;
import lombok.extern.java.Log;

@Log
public class RedisCacheHandle<T> implements CacheHandle{

    private RedisUtil redisUtil = RedisUtil.getRedisUtil();;

    @Override
    public String saveStr(String key, Integer seconds, String value) {
        String setex = "";
        if(seconds != null){
             setex = redisUtil.setex(key, seconds, value);
        }else{
            setex = redisUtil.set(key,value);
        }
        log.info("redis设置缓存。。。key:"+key+",value:"+value+",seconds:"+seconds+",返回值："+setex);
        return setex;
    }

    @Override
    public int del(String key) {
        Long del = redisUtil.del(key);
        log.info("redis删除缓存....,key:"+key+",,,返回值："+del);
        return 1;
    }

    @Override
    public String getStr(String key) {
        String value = redisUtil.get(key);
        log.info("redis根据key..("+key+")获取值，获得的值为："+value);
        return value;
    }

    @Override
    public boolean exist(String key) {
        Boolean exists = redisUtil.exists(key);
        log.info("redis根据key..("+key+")判断是否存在值，是否存在？："+exists);
        return exists;
    }

    @Override
    public int saveObj(String key, Integer seconds, Object o) {
        String setex = "";
        String objJsonStr = JSON.toJSONString(o);
        if(seconds != null){
            setex = redisUtil.setex(key,seconds,objJsonStr);
        }else{
            setex = redisUtil.set(key,objJsonStr);
        }
        log.info("redis缓存对象，key:"+key+",seconds:"+seconds+",返回值："+setex);
        return 1;
    }

    @Override
    public Object getObj(String key, Class clazz) {
        if(exist(key)){
            String objJsonStr = redisUtil.get(key);
            log.info("redis获取对象");
            return JSON.parseObject(objJsonStr,clazz);
        }
        return null;
    }

}
