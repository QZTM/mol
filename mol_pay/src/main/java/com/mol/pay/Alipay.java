package com.mol.pay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import lombok.Data;
import util.TimeUtil;

/**
 * 支付宝支付
 */
@Data
public class Alipay {

    private String charset = "utf-8";
    private String serverUrl = "https://openapi.alipay.com/gateway.do";
    private String appId = "2019072665956811";
    private String signType = "RSA2";
    private String dataFormat = "json";
    private String previt_key="MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDIf7i/APc6IeZq5mMC3nCIdrAPTTOXQyp4GZrf+jTQbCWhh6qepx2Qv2fhuPH6+wcCWyc8gVaNMG/vkTNzoNLQfeHhag01DutWbRCPKebaUVUhRtFUnZjF8ulsUPUtPoQ6pyMzzywWcoh+YF7J1+9MVY67u75tCJg5d3URNMAKtVnAH6/xWt+3oV6y8KW2e/FrImL0zlf8UKYJceFQ59/CwQ5yQZgKreGcjhhS74ce+iU/28hAXq689yLgFiMbfWbvwR3cWag7bfr5IZ+GdkTuYohcXqtH/hcfUOni9Iar1lc7cVMcIY8A+IDjG3/DNFKIM/aB31YJr2tA6hyJenZlAgMBAAECggEBAJupVYStOkQex6adIae7jQDVrNDkbjZ0xgciUfSsa86Y+ApVquir9C9J+1vq26uwsvtoS6kU/V52iaEkSR7vcFzALtt0G7bi02FxIZcUwA8lcAN62NBp3o9ojUM0A3XSAJUM6C60HJV87G25Yj4w8K+CCWPFWg2Ky6xqCzCFlMd6LpL86usOTcdx1j3RfHQNif9esFlEbcetXVhm/k/5YOtF36rBGklZbN3NZ/SbA1YW6YpHEuTyN7p5ryjI3zCGpt4TX6RSGcUbfnxCTIZ1W5u9WPM4w8Hlb6xf1MFZsN3lyE7EXShF1bemli628dU1ZMQMz6RTWrbhNvNG0SNko4ECgYEA7BIwwIAJZ4r32V7Ck/OKEbCNluBU3Nteb/6DLsYxTHjLDMKg8KZub9lVHyIkUxtbUFRTxnApVtCAi1qOKZYZHExV17Fy/56LMRtsEeY80ufVr9ameH/VfsEsyfJKEeO6sffcn7EKXY+nO17b6aiuooI34XP/CSA0q5DNdGwOYgUCgYEA2WzFFw9C3UAezuJC9Uqa5t0eNGe6t5mVABADKphRyv9sLuUC5VCtZfupr9apkFqcdry/KOwAP9CkkfbymBeQMrnJhdDPnVdxIJ4nf85VDp7qKevxXqMFbupY3iOkJptOPBdJWg2olDnGXzmrBywcVNAYl2aQUmpG/f5heS++EOECgYEAtQNNto12qJp6aYarlF7No7rJFsN0ztS2mRGC7T6zVnvY1jP2zBAR5lmzV2gKil8TqYi/pA0k5ZiCuFf7Yg/huT0fJYC/ORoMiN8KAr+UK/PweiARDZyXy9W7zi2mdgk0gnoYBaru08bu6CtYXNYm8hz/VDRpfknfmR4zK+4fKTECgYBs7gOoU+sJ8g/9Unp9bg1BUNLLKpTvWzC9QUh01K9V5rYKoI3c2ZimGXmgMQnu2pa9Hj8ff214i02IG5LbBCMoZtBCDKWXjLGOx+2+KF+Q3akvq5fX9BDKyvoPAQhCl1iQ3pHPXjxxVqi+GKRJCZ6AF2naoHX59Gjw47tt/a9ZwQKBgFd6iPfueBlNFr4SejE6td/q0WEY/4pQZB1+NaFPp57IwCn8cFRS0RgWXky3eUFV6RiCKQncjSD/9csdq2qPX5PZxaY2lKiunRF1mTPZ6kPHUYbIGY6I2ptih3zhEZh6NnMHEknp71mLcFntYdFcVzhETghIZhZF8CfdCqGebdpx";
    private String public_key="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAla/6pfAy7aK8yjBmYFZS+mPqmBS3s2vlrvt5dHEmwuOlbxjbRKcWbHHe/ozWHEizyIBSMPH9EXUN6ns/RZjoSFO8w03CMgOpfm01NlsstKoGV3KlD5yvh4qERKTdW+d0PS3TfqG2tcrnEyY9lHZbiwxS2VJi7kyz5erKtEu7eiIWrLIiGfnrdDI+hYPix5G+DVwxVbAcAIC3+RG1dTVrB5IuY0Evrr9YSjLxOm0KX75GfNyYMUvvSFhtM8WA0BocY+Giwv7hwhGJdvIhbdvxlMuVkcJRJ+MaLBXu01Vt62wQ3A8OKoGQgHs2dGiBLySoiqGJpThzaDicy37LTqrf7wIDAQAB";
    private AlipayClient alipayClient = new DefaultAlipayClient(serverUrl,appId,previt_key,dataFormat,charset,public_key,signType);

    private Alipay alipay = new Alipay();
    private Alipay(){

    }

    public Alipay(String orderId,String body,String subject,String timeoutExpress,String callbackUrl,String totalAmount,String productCode){
        this.orderId = orderId;
        this.body = body;
        this.subject = subject;
        this.timeoutExpress = timeoutExpress;
        this.callbackUrl = callbackUrl;
        this.totalAmount = totalAmount;
        this.productCode = productCode;
    }

    public Alipay getAlipay() {
        if (alipay == null) {
            synchronized (Alipay.class) {
                if (alipay == null) {
                    alipay = new Alipay();
                }
            }
        }
        return alipay;
    }



    public Alipay getAlipay(String orderId,String body,String subject,String timeoutExpress,String callbackUrl,String totalAmount,String productCode) {
        if (alipay == null) {
            synchronized (Alipay.class) {
                if (alipay == null) {
                    alipay = new Alipay(orderId,body,subject,timeoutExpress,callbackUrl,totalAmount,productCode);
                }
            }
        }else{
            alipay.setOrderId(orderId)
                    .setBody(body)
                    .setSubject(subject)
                    .setTimeoutExpress(timeoutExpress)
                    .setCallbackUrl(callbackUrl)
                    .setTotalAmount(totalAmount)
                    .setProductCode(productCode);

        }
        return alipay;
    }






    private String timeoutExpress = "30m";
    public Alipay setTimeoutExpress(String timeoutExpress){
        this.timeoutExpress = timeoutExpress;
        return this;
    }

    //订单编号
    private String orderId = "";
    public Alipay setOrderId(String orderId){
        this.orderId = orderId;
        return this;
    }

    //回调地址
    private String callbackUrl = "http://fyycg66.vaiwan.com/Check_Alipay";
    public Alipay setCallbackUrl(String callbackUrl){
        this.callbackUrl = callbackUrl;
        return this;
    }

    //商品名称
    private String body = "押金缴纳";
    public Alipay setBody(String body){
        this.body = body;
        return this;
    }

    //商品说明或备注
    private String subject = "注册成为丰源中科生态供应商需要缴纳相应押金";
    public Alipay setSubject(String subject){
        this.subject = subject;
        return this;
    }

    //金额
    private String totalAmount = "0.01";
    public Alipay setTotalAmount(String totalAmount){
        this.totalAmount = totalAmount;
        return this;
    }

    private String productCode = "";
    public Alipay setProductCode(String productCode){
        this.productCode = productCode;
        return this;
    }







    public void pay() throws AlipayApiException {
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        request.setNotifyUrl(callbackUrl);//回调地址
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody(body);//商品名称
        model.setSubject(subject);//商品说明或备注
        model.setOutTradeNo(orderId);
        model.setTimeoutExpress(timeoutExpress);
        model.setTotalAmount(totalAmount);//支付金额
        model.setProductCode(productCode);
        request.setBizModel(model);
        //  request.setNotifyUrl("商户外网可以访问的异步地址");
        AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request); //这里和普通的接口调用不同，使用的是sdkExecute
        System.out.println(response.getBody());

    }

    public static void main(String[] args) {
        String orderId = TimeUtil.getNow(TimeUtil.payOrderFormat);
        System.out.println("orderId:");
        System.out.println(orderId);
        Alipay alipay = new Alipay(orderId,"专家认证费用","认证","30m","http://fyycg66.vaiwan.com/Check_Alipay","200.00","QUICK_MSECURITY_PAY");
        try {
            alipay.pay();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
    }
}


