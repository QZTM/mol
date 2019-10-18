package com.mol.notification;

import entity.ServiceResult;

public interface SendNotification {
    public ServiceResult sendOaFromE(String userIdList, String userName, String token, long agentId );

    public ServiceResult sendOaFromThird(String userIdList,Long agentId,String token);

    public static SendNotification getSendNotification(){
        return  SendNotificationImp.getSendNotification();
    }
}
