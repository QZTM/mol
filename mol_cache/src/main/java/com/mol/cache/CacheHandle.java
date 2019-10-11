package com.mol.cache;

/**
 * cache操作器
 * 增删改查
 */
public interface CacheHandle <T>{


    /**
     * 设置缓存,对象为字符串
     * @param key       key
     * @param seconds   过期时间(秒)
     * @param value     存的字符串
     * @return
     */
    public String saveStr(String key,Integer seconds,String value);

    /**
     * 根据key删除对象
    * @param key
     * @return
     */
    public int del(String key);

    /**
     * 根据key获取字符串
     * @param key
     * @return
     */
    public String getStr(String key);

    /**
     * 判断是否存在
     * @param key
     * @return
     */
    public boolean exist(String key);

    /**
     * 存储对象
     * @param key
     * @param seconds
     * @param t
     * @return
     */
    public int saveObj(String key,Integer seconds,T t);


    /**
     * 获取对象
     * @param key
     * @return
     */
    public T getObj(String key,Class clazz);

    public static CacheHandle getCacheHandle(){
        return new RedisCacheHandle();
    };


}
