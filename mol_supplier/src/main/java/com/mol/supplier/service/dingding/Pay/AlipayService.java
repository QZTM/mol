package com.mol.supplier.service.dingding.Pay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.mol.supplier.entity.dingding.Pay.pui_supplier_deposit;
import com.mol.supplier.mapper.newMysql.dingding.Pay.AlipayMapper;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AlipayService//支付宝支付等业务体
{
    @Resource
    private AlipayMapper alipay_mapper;
    static final String previt_key="MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDIf7i/APc6IeZq5mMC3nCIdrAPTTOXQyp4GZrf+jTQbCWhh6qepx2Qv2fhuPH6+wcCWyc8gVaNMG/vkTNzoNLQfeHhag01DutWbRCPKebaUVUhRtFUnZjF8ulsUPUtPoQ6pyMzzywWcoh+YF7J1+9MVY67u75tCJg5d3URNMAKtVnAH6/xWt+3oV6y8KW2e/FrImL0zlf8UKYJceFQ59/CwQ5yQZgKreGcjhhS74ce+iU/28hAXq689yLgFiMbfWbvwR3cWag7bfr5IZ+GdkTuYohcXqtH/hcfUOni9Iar1lc7cVMcIY8A+IDjG3/DNFKIM/aB31YJr2tA6hyJenZlAgMBAAECggEBAJupVYStOkQex6adIae7jQDVrNDkbjZ0xgciUfSsa86Y+ApVquir9C9J+1vq26uwsvtoS6kU/V52iaEkSR7vcFzALtt0G7bi02FxIZcUwA8lcAN62NBp3o9ojUM0A3XSAJUM6C60HJV87G25Yj4w8K+CCWPFWg2Ky6xqCzCFlMd6LpL86usOTcdx1j3RfHQNif9esFlEbcetXVhm/k/5YOtF36rBGklZbN3NZ/SbA1YW6YpHEuTyN7p5ryjI3zCGpt4TX6RSGcUbfnxCTIZ1W5u9WPM4w8Hlb6xf1MFZsN3lyE7EXShF1bemli628dU1ZMQMz6RTWrbhNvNG0SNko4ECgYEA7BIwwIAJZ4r32V7Ck/OKEbCNluBU3Nteb/6DLsYxTHjLDMKg8KZub9lVHyIkUxtbUFRTxnApVtCAi1qOKZYZHExV17Fy/56LMRtsEeY80ufVr9ameH/VfsEsyfJKEeO6sffcn7EKXY+nO17b6aiuooI34XP/CSA0q5DNdGwOYgUCgYEA2WzFFw9C3UAezuJC9Uqa5t0eNGe6t5mVABADKphRyv9sLuUC5VCtZfupr9apkFqcdry/KOwAP9CkkfbymBeQMrnJhdDPnVdxIJ4nf85VDp7qKevxXqMFbupY3iOkJptOPBdJWg2olDnGXzmrBywcVNAYl2aQUmpG/f5heS++EOECgYEAtQNNto12qJp6aYarlF7No7rJFsN0ztS2mRGC7T6zVnvY1jP2zBAR5lmzV2gKil8TqYi/pA0k5ZiCuFf7Yg/huT0fJYC/ORoMiN8KAr+UK/PweiARDZyXy9W7zi2mdgk0gnoYBaru08bu6CtYXNYm8hz/VDRpfknfmR4zK+4fKTECgYBs7gOoU+sJ8g/9Unp9bg1BUNLLKpTvWzC9QUh01K9V5rYKoI3c2ZimGXmgMQnu2pa9Hj8ff214i02IG5LbBCMoZtBCDKWXjLGOx+2+KF+Q3akvq5fX9BDKyvoPAQhCl1iQ3pHPXjxxVqi+GKRJCZ6AF2naoHX59Gjw47tt/a9ZwQKBgFd6iPfueBlNFr4SejE6td/q0WEY/4pQZB1+NaFPp57IwCn8cFRS0RgWXky3eUFV6RiCKQncjSD/9csdq2qPX5PZxaY2lKiunRF1mTPZ6kPHUYbIGY6I2ptih3zhEZh6NnMHEknp71mLcFntYdFcVzhETghIZhZF8CfdCqGebdpx";
    static final String public_key="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAla/6pfAy7aK8yjBmYFZS+mPqmBS3s2vlrvt5dHEmwuOlbxjbRKcWbHHe/ozWHEizyIBSMPH9EXUN6ns/RZjoSFO8w03CMgOpfm01NlsstKoGV3KlD5yvh4qERKTdW+d0PS3TfqG2tcrnEyY9lHZbiwxS2VJi7kyz5erKtEu7eiIWrLIiGfnrdDI+hYPix5G+DVwxVbAcAIC3+RG1dTVrB5IuY0Evrr9YSjLxOm0KX75GfNyYMUvvSFhtM8WA0BocY+Giwv7hwhGJdvIhbdvxlMuVkcJRJ+MaLBXu01Vt62wQ3A8OKoGQgHs2dGiBLySoiqGJpThzaDicy37LTqrf7wIDAQAB";
    AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do","2019072665956811",previt_key, "json", "utf-8",public_key, "RSA2");

    //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
    public Map  Pay_deposit(String supplierId)//交押金,参数：商品名 备注 支付金额
    {
        Map map=new HashMap();
        try {
            List<pui_supplier_deposit> supplier_deposits=alipay_mapper.select_supplier(supplierId);//遍历数据库供应商的所有缴纳押金的订单
            for (pui_supplier_deposit supplier_deposit:supplier_deposits)
            {
                if (supplier_deposit.getReturnMoney().equals("否"))//判断供应商是否已经缴纳押金防止重复缴纳
                {
                    map.put("Body","该供应商已交押金，不可重复缴纳");
                    map.put("Order_id",null);

                    return map;
                }
            }
            String Order_id=new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
            AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
            request.setNotifyUrl("http://fyycg66.vaiwan.com/Check_Alipay");//回调地址
            //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
            model.setBody("押金缴纳");//商品名称
            model.setSubject("注册成为丰源中科生态供应商需要缴纳相应押金");//商品说明或备注
            model.setOutTradeNo(Order_id);
            model.setTimeoutExpress("30m");
            model.setTotalAmount("0.01");//支付金额
            model.setProductCode("QUICK_MSECURITY_PAY");
            request.setBizModel(model);
            //  request.setNotifyUrl("商户外网可以访问的异步地址");
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request); //这里和普通的接口调用不同，使用的是sdkExecute
            map.put("Body",response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。;
            map.put("Order_id",Order_id);
            map.put("supplier_Id",supplierId);
            return  map;
        }
        catch (AlipayApiException e)
        {
            e.printStackTrace();
            map.put("errer",e.toString());
            return map;
        }
    }


    public boolean Check_Alipay_success(String order_id,String supplier_id)//查询是否支付成功，参数：商家订单号 供应商id,支付金额
    {
        AlipayTradeQueryRequest alipayRequest = new AlipayTradeQueryRequest();//设置请求参数
        alipayRequest.setBizContent("{\"out_trade_no\":\""+ order_id+"\"}");//商户订单号，商户网站订单系统中唯一订单号
        try {
            JSONObject jsonData = JSONObject.fromObject(alipayClient.execute(alipayRequest).getBody());//查询
            JSONObject result = (JSONObject) jsonData.get("alipay_trade_query_response");
            if ((result.get("code")).equals("10000") && (result.get("msg")).equals("Success"))//是否支付成功
            {
                if (alipay_mapper.select_order(order_id,"支付宝")==null)//本地数据库没有的情况下保存订单到本地数据库
                {
                    pui_supplier_deposit deposit=new pui_supplier_deposit();//
                    deposit.setOrderId(order_id);
                    deposit.setSupplierId(supplier_id);
                    deposit.setMoney((String) result.get("total_amount"));//金额""total_amount" -> "0.01"
                    deposit.setCreadTime((String) result.get("send_pay_date"));//支付日期"send_pay_date" -> "2019-07-31 09:51:13"
                    deposit.setReturnMoney("否");//是否已经退款
                    deposit.setPayType("支付宝");
                    deposit.setTradeNo((String)result.get("trade_no"));//支付宝生成的唯一订单号
                    alipay_mapper.add_order(deposit);
                }
                return true;//支付成功
            }
            else
            {
                return false;
            }
        }
        catch (Exception e)
        {
            return  false;
        }
    }

    public Map Return_money(String Order_id)//退还押金,参数：商家订单id 备注 退款金额
    {
        Map map=new HashMap();
        try
        {
            pui_supplier_deposit supplier_deposit=alipay_mapper.select_order(Order_id,"支付宝");
            if (supplier_deposit==null)
            {
                map.put("status",false);
                return map;//未查到押金订单，不可退款
            }
            else
            {
                AlipayTradeRefundRequest alipayRequest = new AlipayTradeRefundRequest();
                String out_request_no =supplier_deposit.getSupplierId(); //标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传
                alipayRequest.setBizContent("{\"out_trade_no\":\""+ Order_id +"\","//商家订单id
                        //  + "\"trade_no\":\""+ trade_no +"\","
                        + "\"refund_reason\":\""+ "丰源中科供应商押金退还" +"\","//备注
                        + "\"refund_amount\":\""+ supplier_deposit.getMoney() +"\","//退款金额
                        + "\"out_request_no\":\""+ out_request_no +"\"}");//唯一退款号
                alipayClient.execute(alipayRequest).getBody();//执行退款
                map.put("status",true);
                map.put("SupplierId",out_request_no);
                return map;
            }
        }
        catch (Exception e)
        {
            map.put("status",false);
            return map;
        }
    }

    public boolean Check_Return_money_success(String order_id)//查询成功退款的订单，参数：商家订单号 退款请求号
    {
        try {
            if (alipay_mapper.select_order(order_id,"支付宝")!=null)//查看本地数据库是否有押金信息
            {
                AlipayTradeFastpayRefundQueryRequest alipayRequest = new AlipayTradeFastpayRefundQueryRequest();  //设置请求参数
                alipayRequest.setBizContent("{\"out_trade_no\":\""+ order_id+"\","//商户订单号，商户网站订单系统中唯一订单号
                        + "\"out_request_no\":\""+ alipay_mapper.select_order(order_id,"支付宝").getSupplierId() +"\"}");//请求退款接口时，传入的退款请求号，如果在退款请求时未传入，则该值为创建交易时的外部交易号，必填
                JSONObject jsonData = JSONObject.fromObject(alipayClient.execute(alipayRequest).getBody());//查询
                JSONObject result = (JSONObject) jsonData.get("alipay_trade_fastpay_refund_query_response");
                if (result.get("refund_amount")!=null)//判断是否已经退款
                {
                    alipay_mapper.update_order(order_id,"是",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") .format(new Date()));
                    return true;
                }
                else
                {
                    return false;
                }
            }
             else
            {
                return false;
            }
        }
        catch (Exception e)
        {
           return false;
        }
    }
}
