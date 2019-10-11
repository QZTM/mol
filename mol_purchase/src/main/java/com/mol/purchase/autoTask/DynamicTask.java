package com.mol.purchase.autoTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.mol.purchase.service.dingding.dataCopy.AutoManageLogService;

import java.io.IOException;

@Component
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class DynamicTask {

    @Autowired
    private AutoManageLogService autoManageLogService;

    //3.添加定时任务
    //@Scheduled(cron = "0/5 * * * * ?")
    @Scheduled(cron = "0 0 1 * * ? ")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    private void configureTasks() throws IOException {
        //autoManageLogService.zipLog();
    }
}
