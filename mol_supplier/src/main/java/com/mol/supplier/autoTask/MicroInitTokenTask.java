package com.mol.supplier.autoTask;

import com.mol.cache.CacheHandle;
import com.mol.supplier.service.microApp.MicroJsapiTicketService;
import com.mol.supplier.service.microApp.MicroTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(500)
public class MicroInitTokenTask implements ApplicationRunner {

    @Autowired
    private MicroTokenService microTokenService;
    @Autowired
    private MicroJsapiTicketService microJsapiTicketService;

    @Autowired
    private CacheHandle cacheHandle;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        cacheHandle.del(MicroTokenService.MICROAPPTOKENKEY);
        cacheHandle.del(MicroJsapiTicketService.MICROAPPJSAPITICKETKEY);
        microTokenService.getToken();
        microJsapiTicketService.getJsApiTicket();

    }
}
