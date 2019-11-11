package com.mol.supplier.controller.microApp;

import com.mol.pay.Alipay;
import com.mol.pay.entity.AlipayCreateInfo;
import com.mol.supplier.config.Constant;
import com.mol.supplier.config.MicroAttr;
import com.mol.supplier.entity.MicroApp.AlipayTemplates;
import com.mol.supplier.entity.MicroApp.Supplier;
import com.mol.supplier.entity.dingding.Pay.PuiSupplierDeposit;
import com.mol.supplier.mapper.dingding.Pay.PayMapper;
import com.mol.supplier.mapper.microApp.MicroSupplierMapper;
import com.mol.supplier.service.microApp.MicroAlipayService;
import com.mol.supplier.service.microApp.MicroUserService;
import entity.ServiceResult;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 处理前端页面请求的支付宝支付
 */
@RequestMapping("/pay/alipay")
@Controller
@Log
public class MicroAlipayController {


    @Autowired
    private MicroAlipayService microAlipayService;

    @Autowired
    private PayMapper payMapper;

    @Autowired
    private MicroUserService microUserService;

    @Autowired
    private MicroSupplierMapper microSupplierMapper;

    private static final String payCallBackUrl = Constant.domain+"/pay/alipay/callback";


    /**
     * 钉钉微应用获取支付字符串用于发起支付宝支付
     * @param paramap
     * @param request
     * @param session
     * @return
     */
    @RequestMapping("/getCreateInfo")
    @ResponseBody
    public ServiceResult getAlipayInfo(@RequestParam Map paramap, HttpServletRequest request,HttpSession session){
        log.info("/getCreateInfo....");
        System.out.println(paramap.toString());
        String payFor = (String)paramap.get("payfor");
        AlipayCreateInfo createPayInfo = null;
        //根据前端传过来的参数确定支付订单模板
        switch(payFor){
            case PuiSupplierDeposit.ORDER_PAY_FOR_STRATEGY_SUPPLIER_SERVICE:
                createPayInfo = AlipayTemplates.TemplateOfStrategySupplierService();
                break;
            case PuiSupplierDeposit.ORDER_PAY_FOR_SINGON_SUPPLIER_SERVICE:
                createPayInfo = AlipayTemplates.TemplateOfSingleSupplierService();
                break;
        }
        createPayInfo.setCallbackUrl(payCallBackUrl);
        try {
            Map payInfoMap = microAlipayService.getAlipayInfo(createPayInfo).get();
            log.info("/getCreateInfo:"+payInfoMap.get(Alipay.MAPKEY_PAYINFO));
            //存入数据库
            microAlipayService.savePayOrder(createPayInfo,(String)microUserService.getSupplierFromSession(session).getPkSupplier(),PuiSupplierDeposit.ORDER_PAY_TYPE_ALIPAY,payFor);
            return ServiceResult.success((String)payInfoMap.get(Alipay.MAPKEY_OUTTRADENO),(String)payInfoMap.get(Alipay.MAPKEY_PAYINFO));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 根据自定义订单id获取支付状态，(走的是阿里的查询订单支付状态接口)
     * @param orderid           自定义支付订单id
     * @return
     * 2019-11-11测试调用时报错：
     * ErrorScene^_^40006^_^isv.app-api-punished^_^null^_^Windows 10^_^2019-11-11 14:00:33^_^ProtocalMustParams:charset=utf-8&method=alipay.trade.query&sign=H1YO43mnqwP6RGOMlkEJoHFPiMxAhaSrlqoiWH7CY/9E7l+YVg3vQpGBnjo8ot5/5cZX63h3CSmF7oIyQ1PsC8nAYd2gbnr7RW8i3Sj4Jhq7bWnX51qyPGUqhC5SeHn9suRZxIywfD9WwkuTvql2tvbsl/rWbyreyBFanFNCwr9uR8M31q7TKVtphiFd1Nbdymaneg9XAKY+BaH/rHTbYUxpdCRLrjc0ZtYnIVJE0spy1adGz2rXJzkixnSwrM02AW+/pnWOPlr7PRDv2mSWqrBT8TGVnC4Y+E7p+kqgVSQkw25SZQ0KZwXWKg1X+YbNilAWpI1tkS5+uInmGj1Llg==&version=1.0&app_id=2019072665956811&sign_type=RSA2&timestamp=2019-11-11 14:00:33^_^ProtocalOptParams:alipay_sdk=alipay-sdk-java-3.7.110.ALL&format=json^_^ApplicationParams:biz_content={"out_trade_no":"20191111135829080"}^_^Body:{"alipay_trade_query_response":{"code":"40006","msg":"Insufficient Permissions","sub_code":"isv.app-api-punished","sub_msg":"该接口已被处罚，不可调用"},"sign":"DGblIy8jXdCJVS3rM0AIhtOQOeAnRNWbTkFKaoX3qJU2sMO8qGm/Z86K3c/d6UnVPBrvBmKylI1+DMl00m2isrQbp3V4krRkNCBFaosw8d60KTwaLHWA07i0dPZ8ygRCpj19izXBVMJUFy56GtcS43DTjq/8RicdzE/5KXNFI75iryKllqOizQ2zTT1GmU1xTekPEMuXjaEmduPHcvRlt5JvAPouDvLrlCCcFmFZiL5k9ycTISoCMbzQBKTe4+zk5flerrtUGryLX4Ng9BTVsLLZrStwa4pdD5O98jzmk4tMkKcCCf787/EZu8/ULKimG95YYqu+Ea0dBPiI+kaQLA=="}^_^39ms,84ms,4ms
     * checkOrderStatus....response.getbody:{"alipay_trade_query_response":{"code":"40006","msg":"Insufficient Permissions","sub_code":"isv.app-api-punished","sub_msg":"该接口已被处罚，不可调用"},"sign":"DGblIy8jXdCJVS3rM0AIhtOQOeAnRNWbTkFKaoX3qJU2sMO8qGm/Z86K3c/d6UnVPBrvBmKylI1+DMl00m2isrQbp3V4krRkNCBFaosw8d60KTwaLHWA07i0dPZ8ygRCpj19izXBVMJUFy56GtcS43DTjq/8RicdzE/5KXNFI75iryKllqOizQ2zTT1GmU1xTekPEMuXjaEmduPHcvRlt5JvAPouDvLrlCCcFmFZiL5k9ycTISoCMbzQBKTe4+zk5flerrtUGryLX4Ng9BTVsLLZrStwa4pdD5O98jzmk4tMkKcCCf787/EZu8/ULKimG95YYqu+Ea0dBPiI+kaQLA=="}
     */
//    @RequestMapping("/getOrderStatus")
//    @ResponseBody
//    public ServiceResult getOrderStatus(String orderid){
//        log.info("/getOrderStatus.......orderId:"+orderid);
//        boolean ifSuccess = false;
//        try {
//            ifSuccess = microAlipayService.getOrderStatus(orderid).get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//        if(ifSuccess){
//            return ServiceResult.successMsg("支付成功");
//        }
//        return ServiceResult.failureMsg("支付失败");
//    }

    /**
     * 根据自定义订单id获取支付状态，（走的是查询本地数据库的订单状态）
     * @param orderid           自定义支付订单id
     * @return
     */
    @RequestMapping("/getOrderStatus")
    @ResponseBody
    public ServiceResult getOrderStatus2(String orderid){
        Example example = new Example(PuiSupplierDeposit.class);
        example.and().andEqualTo("orderId",orderid);
        PuiSupplierDeposit puiSupplierDeposit = payMapper.selectOneByExample(example);
        System.out.println(puiSupplierDeposit);
        if(puiSupplierDeposit != null && (puiSupplierDeposit.getStatus().equals(PuiSupplierDeposit.ORDER_STATUS_SUCCESS))){
            log.info("/getOrderStatus......返回支付成功");
            return ServiceResult.successMsg("支付成功");
        }
        return ServiceResult.failureMsg("支付失败");
    }


    /**
     * 支付宝支付后的回调事件：
     * @param map
     * @param session
     * @return
     */
    @RequestMapping(value = "/callback",method = RequestMethod.POST)
    @ResponseBody
    public String payCallback(@RequestParam Map map, HttpSession session){
        log.info("********支付宝回调事件********");
        log.info("参数："+map.toString());
        String orderStatus = (String)map.get("trade_status");
        String orderId = (String)map.get("out_trade_no");
        String payFor = (String)map.get("passback_params");
        log.info("payFor:"+payFor);
        //订单支付成功事件：
        if("TRADE_SUCCESS".equals(orderStatus)){
            //根据自定义id去数据库查询，如有有数据则更改状态
            log.info("支付宝支付回调事件——支付成功--orderId:"+orderId);
            PuiSupplierDeposit puiSupplierDeposit = new PuiSupplierDeposit();
            puiSupplierDeposit.setOrderId(orderId);
            PuiSupplierDeposit puiSupplierDepositGet = payMapper.selectByPrimaryKey(puiSupplierDeposit);
            puiSupplierDepositGet.setStatus(PuiSupplierDeposit.ORDER_STATUS_SUCCESS);
            puiSupplierDepositGet.setTradeNo((String)map.get("trade_no"));
            payMapper.updateByPrimaryKeySelective(puiSupplierDepositGet);
            //根据payFor修改业务上需要修改的状态，例如，战略供应商支付完成后需要修改供应商表中的供应商认证的状态：
            switch(payFor){
                case PuiSupplierDeposit.ORDER_PAY_FOR_STRATEGY_SUPPLIER_SERVICE:
                    //修改战略供应商认证状态为成功：
                    Supplier supplier = new Supplier();
                    supplier.setPkSupplier(puiSupplierDepositGet.getSupplierId());
                    supplier.setSupstateStrategy(MicroAttr.SUPSTATE_SUCCESS);
                    supplier.setSupstateNormal(MicroAttr.SUPSTATE_SUCCESS);
                    microSupplierMapper.updateByPrimaryKeySelective(supplier);
                    break;
                case PuiSupplierDeposit.ORDER_PAY_FOR_SINGON_SUPPLIER_SERVICE:
                    Supplier supplier2 = new Supplier();
                    supplier2.setPkSupplier(puiSupplierDepositGet.getSupplierId());
                    supplier2.setSupstateSingle(MicroAttr.SUPSTATE_SUCCESS);
                    supplier2.setSupstateNormal(MicroAttr.SUPSTATE_SUCCESS);
                    microSupplierMapper.updateByPrimaryKeySelective(supplier2);
                    break;
                case PuiSupplierDeposit.ORDER_PAY_FOR_EXPERT_REVIEW_FEE:
                    //todo 专家审核费用支付成功回调事件
                    break;
                case PuiSupplierDeposit.ORDER_PAY_FOR_CONTRACT_FEE:
                    //todo 合同费用支付成功后的回调事件
                    break;

            }

        }

        System.out.println("success");
        return "success";
    }

}
