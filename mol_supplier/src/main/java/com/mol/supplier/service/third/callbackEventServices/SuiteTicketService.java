package com.mol.supplier.service.third.callbackEventServices;

import com.alibaba.fastjson.JSONObject;
import com.mol.supplier.config.Constant;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SuiteTicketService implements BaseEventService {
    @Override
    public void action(JSONObject obj) {

        Set<String> keys = obj.keySet();
        System.out.println("****************");
        for(String key:keys){
            System.out.println(key+"----------"+obj.getString(key));
        }
        Constant.THIRD_SUITE_TICKET = obj.getString("SuiteTicket");
        System.out.println("****************");
    }
}
