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




##短信（mol_sms）的使用：

    ①pom文件中引入mol_cache
    
            <dependency>
                <groupId>com.mol</groupId>
                <artifactId>mol_sms</artifactId>
                <version>1.0-SNAPSHOT</version>
            </dependency>
     
    ②在使用的地方设置工具类：
    
        private SendMsmHandler sendMsmHandler = SendMsmHandler.getSendMsmHandler();
        
    
    ③使用：
    
        String sendResult = sendMsmHandler.sendMsm(XiaoNiuMsm.SIGNNAME_MEYG, templateCode,phone);
        
    
        参数说明：
            XiaoNiuMsm.SIGNNAME_MEYG        在小牛云短信平台建立的应用的名称，已设置为常亮，必填
            templateCode                    模板id,已在XiaoNiuMsmTemplate中设置模板，根据业务选择，例如：XiaoNiuMsmTemplate.供应商注册模板();
            phone                           接收短信的手机号码
          
          
        返回值说明：  
            sendResult:         短信发送成功XXXXXX      短信发送失败      两种字符串型返回值,其中XXXXXX代表真实发送的验证码，前端谨慎处理