package com.mol.oos;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

/**
 * oos获取客户端
 */
public class OOSClient {

    private static final String OOS_ACCESS_ID = "047fd6cd7ddd816050b6";
    private static final String OOS_SECRET_ID = "f1986faca1b86e368175496f3a8563d15f2fc131";

    private volatile static AmazonS3 oosClient;


    private OOSClient(){
        if(oosClient == null){
            // 创建一个AmazonS3 客户端对象
            oosClient = new AmazonS3Client(new AWSCredentials() {
                public String getAWSAccessKeyId() {
                    return OOS_ACCESS_ID;//你的accesskey
                }
                public String getAWSSecretKey() {
                    return OOS_SECRET_ID;//你的secretKey
                }
            });
            // 设置API服务器
            oosClient.setEndpoint("oos-cn.ctyunapi.cn");//设置你的资源池域名
            ClientConfiguration clientConfig = new ClientConfiguration();
            clientConfig.setConnectionTimeout(30*1000);
            clientConfig.setSocketTimeout(30*1000) ;
            ((AmazonS3Client) oosClient).setConfiguration(clientConfig);
        }
    }

    public static AmazonS3 getClient() {
        if (oosClient == null) {
            synchronized (OOSClient.class) {
                if (oosClient == null) {
                    oosClient = new AmazonS3Client(new AWSCredentials() {
                        public String getAWSAccessKeyId() {
                            return OOS_ACCESS_ID;//你的accesskey
                        }
                        public String getAWSSecretKey() {
                            return OOS_SECRET_ID;//你的secretKey
                        }
                    });
                    // 设置API服务器
                    oosClient.setEndpoint("oos-cn.ctyunapi.cn");//设置你的资源池域名
                    ClientConfiguration clientConfig = new ClientConfiguration();
                    clientConfig.setConnectionTimeout(30*1000);
                    clientConfig.setSocketTimeout(30*1000) ;
                    ((AmazonS3Client) oosClient).setConfiguration(clientConfig);
                }
            }
        }
        return oosClient;
    }
}
