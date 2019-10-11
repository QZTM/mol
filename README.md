# mol
茉尔易购、供应商、专家

##redis缓存使用：
###1.springboot项目使用说明：
   ①pom文件中引入mol_cache
    
            <dependency>
                <groupId>com.mol</groupId>
                <artifactId>mol_cache</artifactId>
                <version>1.0-SNAPSHOT</version>
            </dependency>
            
   ②启动类中注入bean

        @Bean
        public CacheHandle getCacheHandler(){
            return CacheHandle.getCacheHandle();
        }
        
   ③在使用的地方依赖注入：
    
        @Autowired
        private CacheHandle cacheHandle;
        
   ④使用：
   
    保存字符型：(返回值为value)
    cacheHandle.saveStr(key,过期时间（单位为秒）,value);
    
    保存对象型：(返回值为int)
    cacheHandle.cacheHandle.saveObj(key,过期时间,Object obj);
    
    删除对象：(返回值为int)
    cacheHandle.del(key);



⑤⑥⑦
