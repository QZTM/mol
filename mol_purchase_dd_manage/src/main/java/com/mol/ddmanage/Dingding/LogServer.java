package com.mol.ddmanage.Dingding;

import com.mol.ddmanage.Util.Dingding_Tools;
import com.mol.ddmanage.config.Dingding_config;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class LogServer {
    //或直接指定时间间隔，例如：5秒
    @Scheduled(fixedRate=1000*3000)
    private void configureTasks() {
        Dingding_config.DingdingAPP_Token= Dingding_Tools.GetAPPdingding_token();
        if ( Dingding_config.DingdingAPP_Token!=null)
        {
            System.out.println("服务注册成功");
        }
        else
        {
            System.out.println("服务注册失败");
        }
}
}
