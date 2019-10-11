package com.mol.supplier.service.Weixin.Pay;

import com.mol.supplier.entity.dingding.Pay.pui_supplier_deposit;
import com.mol.supplier.mapper.newMysql.Weixin.Pay.WeixinPayMapper;
import com.mol.supplier.util.PayUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class WeixinPay_service
{
    @Resource
    WeixinPayMapper weixinPayMapper;
    //小程序id
    public static final String appid = "wx29f66e2e1ad11fe0";//
    //微信支付的商户id
    public static final String mch_id = "1489352152";
    //微信支付的商户密钥
    public static final String key = "fgzfp5l5wdxdtq44h4yuu68zil0j7oqd";
    //支付成功后的服务器回调url
    public static final String notify_url = "http://fyycg66.vaiwan.com/WeixinPay/notify_url";
    //签名方式，固定值
    public static final String SIGNTYPE = "MD5";
    //交易类型，小程序支付的固定值为JSAPI
    public static final String TRADETYPE = "JSAPI";
    //微信统一下单接口地址
    public static final String pay_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";//https://api.mch.weixin.qq.com/secapi/pay/refund
    //退款连接
    public static  final String Retrun_money_url="https://api.mch.weixin.qq.com/secapi/pay/refund";
    //查询支付结果连接
    public static  final String Select_Pay_rest_url="https://api.mch.weixin.qq.com/pay/orderquery";

    public boolean View_JudgePay_Service(Map map)
    {
        String SupplierId=map.get("SupplierId").toString();//获取供应商id
        for (pui_supplier_deposit deposit_1:weixinPayMapper.select_supplier(SupplierId))//判断供应商是否已经交押金了
        {
            String jj=deposit_1.getReturnMoney();
            if (deposit_1.getReturnMoney().equals("否"))//只要有一个订单没有退款就是已经交押金
            {
                Map map2=new HashMap();
                map2.put("erre","您已经交押金了，不需要再缴纳押金");
                return false;
            }
        }
        return true;
    }
   public Map Pay_service(Map map1,String total_fee)//支付
   {
       try{
           String out_trade_no=new SimpleDateFormat("yyyyMMddHHmmssSSS") .format(new Date());
           //生成的随机字符串
           String nonce_str = PayUtil.getRandomStringByLength(32);
           //商品名称
           String body = "注册供应商押金";
           //获取客户端的ip地址
           String spbill_create_ip = PayUtil.getLocalIP();//"218.59.239.86";//
           String openid=map1.get("openid").toString();//"ooQ0w5Qq2K4QX9oJUxtgwYtR5LpI";
           String SupplierId=map1.get("SupplierId").toString();//获取供应商id


           //组装参数，用户生成统一下单接口的签名
           Map<String, String> packageParams = new HashMap<String, String>();
           packageParams.put("appid", appid);
           packageParams.put("body", body);
           packageParams.put("mch_id", mch_id);
           packageParams.put("nonce_str", nonce_str);
           packageParams.put("notify_url", notify_url);//支付成功后的回调地址
           packageParams.put("openid", openid);
           packageParams.put("out_trade_no", out_trade_no);//商户订单号
           packageParams.put("spbill_create_ip", spbill_create_ip);
           packageParams.put("total_fee", total_fee);//支付金额，这边需要转成字符串类型，否则后面的签名会失败
           packageParams.put("trade_type", TRADETYPE);//支付方式

           String prestr = PayUtil.createLinkString(packageParams); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
           //MD5运算生成签名，这里是第一次签名，用于调用统一下单接口
           String mysign = PayUtil.sign(prestr, key, "utf-8").toUpperCase();

           //拼接统一下单接口使用的xml数据，要将上一步生成的签名一起拼接进去
           String xml = "<xml>" + "<appid>" + appid + "</appid>"
                   + "<body>" + body + "</body>"
                   + "<mch_id>" + mch_id + "</mch_id>"
                   + "<nonce_str>" + nonce_str + "</nonce_str>"
                   + "<notify_url>" + notify_url + "</notify_url>"
                   + "<openid>" + openid + "</openid>"
                   + "<out_trade_no>" + out_trade_no + "</out_trade_no>"
                   + "<spbill_create_ip>" + spbill_create_ip + "</spbill_create_ip>"
                   + "<total_fee>" + total_fee + "</total_fee>"
                   + "<trade_type>" + TRADETYPE + "</trade_type>"
                   + "<sign>" + mysign + "</sign>"
                   + "</xml>";

           System.out.println("调试模式_统一下单接口 请求XML数据：" + xml);

           //调用统一下单接口，并接受返回的结果
           String result =new  PayUtil().httpRequest(pay_url, "POST", xml);

           System.out.println("调试模式_统一下单接口 返回XML数据：" + result);

           // 将解析结果存储在HashMap中
           Map map = PayUtil.doXMLParse(result);

           String return_code = (String) map.get("return_code");//返回状态码

           Map<String, Object> response = new HashMap<String, Object>();//返回给小程序端需要的参数
           if(return_code.equals("SUCCESS")){
               String prepay_id = (String) map.get("prepay_id");//返回的预付单信息
               response.put("nonceStr", nonce_str);
               response.put("package", "prepay_id=" + prepay_id);
               Long timeStamp = System.currentTimeMillis() / 1000;
               response.put("timeStamp", timeStamp + "");//这边要将返回的时间戳转化成字符串，不然小程序端调用wx.requestPayment方法会报签名错误
               //拼接签名需要的参数
               String stringSignTemp = "appId=" + appid + "&nonceStr=" + nonce_str + "&package=prepay_id=" + prepay_id+ "&signType=MD5&timeStamp=" + timeStamp;
               //再次签名，这个签名用于小程序端调用wx.requesetPayment方法
               String paySign = PayUtil.sign(stringSignTemp, key, "utf-8").toUpperCase();

               response.put("paySign", paySign);
           }

           response.put("appid", appid);
            if (weixinPayMapper.select_order(out_trade_no,"微信")==null)//判断数据库是否有此订单号
            {
                pui_supplier_deposit supplier_deposit=new pui_supplier_deposit();
                supplier_deposit.setOrderId(out_trade_no);
                supplier_deposit.setSupplierId(SupplierId);//供应商id
                supplier_deposit.setMoney("未支付");
                supplier_deposit.setPayType("微信");
                weixinPayMapper.add_order(supplier_deposit);
            }
           return response;
       }catch(Exception e){
           e.printStackTrace();
       }
       return null;
   }

   public boolean Refund(String out_trade_no ,String total_fee,String refund_fee) //参数 退款的商户单号,原订单金额，本次退款金额
   {
       try
       {
           String out_refund_no=new SimpleDateFormat("yyyyMMddHHmmssSSS") .format(new Date());
           //生成的随机字符串
           String nonce_str = PayUtil.getRandomStringByLength(32);

           //组装参数，用户生成统一下单接口的签名
           Map<String, String> packageParams = new HashMap<String, String>();
           packageParams.put("appid", appid);
           packageParams.put("mch_id", mch_id);
           packageParams.put("nonce_str", nonce_str);
           packageParams.put("out_trade_no", out_trade_no);//商户订单号
           packageParams.put("out_refund_no", out_refund_no);
           packageParams.put("total_fee", total_fee);//订单金额
           packageParams.put("refund_fee", refund_fee);//退款金额

           String prestr = PayUtil.createLinkString(packageParams); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
           //MD5运算生成签名，这里是第一次签名，用于调用统一下单接口
           String mysign = PayUtil.sign(prestr, key, "utf-8").toUpperCase();

           //拼接统一下单接口使用的xml数据，要将上一步生成的签名一起拼接进去
           String xml = "<xml>" + "<appid>" + appid + "</appid>"
                   + "<mch_id>" + mch_id + "</mch_id>"
                   + "<nonce_str>" + nonce_str + "</nonce_str>"
                   + "<out_trade_no>" + out_trade_no + "</out_trade_no>"
                   + "<out_refund_no>" + out_refund_no + "</out_refund_no>"
                   + "<total_fee>" + total_fee + "</total_fee>"//订单金额
                   + "<refund_fee>" + refund_fee + "</refund_fee>"//退款金额
                   + "<sign>" + mysign + "</sign>"
                   + "</xml>";

           System.out.println("调试模式_统一下单接口 请求XML数据：" + xml);

           //调用统一下单接口，并接受返回的结果
           String result=PayUtil.doRefund(mch_id,Retrun_money_url,"C:\\Users\\41419\\Desktop\\123\\apiclient_cert.p12",xml);
           String bbb="1";
           return true;
       }
       catch (Exception e)
       {
           System.out.println(e.toString());
           return false;
       }
   }

   public String Select_Pay_rest(HttpServletRequest httpServletRequest)//商户订单号
   {
       try {
           BufferedReader reader =  httpServletRequest.getReader();
           String line = "";
           String xmlString = null;
           StringBuffer inputString = new StringBuffer();
           while ((line = reader.readLine()) != null) {
               inputString.append(line);
           }
           xmlString = inputString.toString();
           httpServletRequest.getReader().close();
           System.out.println("----接收到的数据如下：---" + xmlString);

           Map map= PayUtil.doXMLParse(xmlString);//解析微信传来的xml为map值
           //组装参数，用户生成统一下单接口的签名
           String nonce_str = PayUtil.getRandomStringByLength(32);
           Map<String, String> packageParams = new HashMap<String, String>();
           packageParams.put("appid", appid);
           packageParams.put("mch_id", mch_id);
           packageParams.put("nonce_str", nonce_str);
           packageParams.put("out_trade_no", map.get("out_trade_no").toString());//商户订单号

           String prestr = PayUtil.createLinkString(packageParams); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
           //MD5运算生成签名，这里是第一次签名，用于调用统一下单接口
           String mysign = PayUtil.sign(prestr, key, "utf-8").toUpperCase();
           String xml="<xml>\n" +
                   "   <appid>"+appid+"</appid>\n" +
                   "   <mch_id>"+mch_id+"</mch_id>\n" +
                   "   <nonce_str>"+nonce_str+"</nonce_str>\n" +
                   "   <out_trade_no>"+map.get("out_trade_no").toString()+"</out_trade_no>\n" +
                   "   <sign>"+mysign+"</sign>\n" +
                   "</xml>";
           String result =new  PayUtil().httpRequest(Select_Pay_rest_url, "POST", xml);
           Map select_rest_map=PayUtil.doXMLParse(result);//微信返回的xml结果进行解析
           System.out.println(select_rest_map.get("out_trade_no").toString());
           if (select_rest_map.get("return_code").toString().equals("SUCCESS") && select_rest_map.get("trade_state").toString().equals("SUCCESS"))//支付成功
           {
               if (weixinPayMapper.select_order(select_rest_map.get(("out_trade_no")).toString(),"微信")!=null)//查看数据库订单是否存在
               {
                   pui_supplier_deposit supplier=new pui_supplier_deposit();
                   supplier.setOrderId(select_rest_map.get(("out_trade_no")).toString());
                   supplier.setMoney(String.valueOf(Double.parseDouble(select_rest_map.get("cash_fee").toString())/100));
                   supplier.setCreadTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") .format(new Date()));
                   supplier.setReturnMoney("否");
                   supplier.setPayType("微信");
                   supplier.setTradeNo(select_rest_map.get(("transaction_id")).toString());
                   weixinPayMapper.update_order(supplier);//更新表单状态
                   String supplier_id=weixinPayMapper.select_order(select_rest_map.get(("out_trade_no")).toString(),"微信").getSupplierId();//获取完成支付的供应商id
                   weixinPayMapper.Delete_Pay(supplier_id,"未支付");//删除此供应商未支付成功的订单
               }
               return "<xml><return_code>SUCCESS</return_code><return_msg>OK</return_msg></xml>";
           }
           else
           {
               return "";
           }

       }
       catch (Exception e)
       {
           return "errer";
       }
   }
}
