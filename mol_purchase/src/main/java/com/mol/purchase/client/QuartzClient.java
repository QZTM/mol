package com.mol.purchase.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("mol-quartz")
public interface QuartzClient {

    @RequestMapping("/moljob/addquoteendjobwithendtime")
    public void addquoteendjobwithendtime(@RequestParam String orderId, @RequestParam String endTime);

    @RequestMapping("/moljob/addexpertreviewendjobwithendtime")
    public void addexpertreviewendjobwithendtime(@RequestParam String orderId,@RequestParam String endTime);

    @RequestMapping("/moljob/addexpertreviewendjobwithdelaySeconds")
    public void addexpertreviewendjobwithdelaySeconds(@RequestParam String orderId,@RequestParam Long delaySeconds);
}
