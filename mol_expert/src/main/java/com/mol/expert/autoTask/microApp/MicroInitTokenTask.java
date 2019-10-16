package com.mol.expert.autoTask.microApp;

import com.mol.expert.service.microApp.MicroJsapiTicketService;
import com.mol.expert.service.microApp.MicroTokenService;
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


    @Override
    public void run(ApplicationArguments args) throws Exception {
        microTokenService.getToken(MicroTokenService.MICROAPPTOKENKEY);
        microJsapiTicketService.getJsApiTicket(MicroJsapiTicketService.MICROAPPJSAPITICKETKEY);
    }
}
