package com.mol.supplier.controller.Weixin.Pay;


import com.mol.supplier.service.Weixin.Pay.WeixinPay_service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RequestMapping("WeixinPay")//微信支付Controller层
@RestController
public class WeixinPay
{
    @Resource
    WeixinPay_service weixinPay_service;
    @RequestMapping("View_JudgePay")
    public boolean View_JudgePay(Map map)//查询该供应商是否已经缴纳押金 map必须包含SupplierId供应商id
    {
        return weixinPay_service.View_JudgePay_Service(map);
    }

    @RequestMapping("Pay_app")//支付
    public Map Pay_app(@RequestParam Map map1)
    {
       return weixinPay_service.Pay_service(map1,"1");//参数 map自带openid ，支付金额 1代表0.01元
    }

    @RequestMapping("Refund")//退款
    public boolean Refund(@RequestParam Map map)
    {
      return   weixinPay_service.Refund(map.get("out_trade_no").toString(),map.get("total_fee").toString(),map.get("refund_fee").toString());//参数 退款的商户单号,原订单金额，本次退款金额
    }

    @RequestMapping( value = "notify_url" ,produces = "application/json;charset=UTF-8")//支付成功的回调微信服务端调用
    @ResponseBody
    public String Pay_notify(HttpServletRequest request)
    {
       return weixinPay_service.Select_Pay_rest(request);
    }
}

//前端代码调用支付实例
/*// 登录
    wx.login({
            success(res) {
            var that = this;
            if (res.code) {
            app.globalData.codes=res.code
            console.log(app.globalData.codes)//显示code
            wx.request({
            url: 'http://fyycg66.vaiwan.com/WeixinLogin/login',  //获取openid
            data: {code:res.code},
            header: {
            'content-type': 'application/json' // 默认值
            },
            success(res)
            {
            console.log(res.data.openid);//获取到openid
            wx.request({
            url: 'http://fyycg66.vaiwan.com/WeixinPay/View_JudgePay', //判断订单是否重复
            data: { SupplierId: 1122334445566789},
            header: {
            'content-type': 'application/json' // 默认值
            },
            success(res)
            {
            if(res.data==true)
            {
            wx.request({
            url: 'http://fyycg66.vaiwan.com/WeixinPay/Pay_app', //支付
            data: {
            openid: res.data.openid,
            SupplierId: '1122334445566789'//供应商id
            },
            header: {
            'content-type': 'application/json' // 默认值
            },
            success(res) {
            console.log("fffff")

            wx.requestPayment({//吊起手机支付
            timeStamp: res.data.timeStamp,
            nonceStr: res.data.nonceStr,
            package: res.data.package,
            signType: 'MD5',
            paySign: res.data.paySign,
            success(res) { console.log('chenggong') },
            fail(res) { console.log('shibie') }
            })
            }
            })
            }
            else//当发现已经支付时提示用户不能再付钱了
            {
            wx.showModal({
            title: '提示',
            content: '您已经缴纳完押金不需要再次缴纳！',
            success: function (res) {
            if (res.confirm) {//这里是点击了确定以后
            console.log('用户点击确定')
            } else {//这里是点击了取消以后
            console.log('用户点击取消')
            }
            }
            })
            }
            }
            })

            }
           })*/
