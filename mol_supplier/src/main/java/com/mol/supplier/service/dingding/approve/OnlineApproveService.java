package com.mol.supplier.service.dingding.approve;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.dingtalk.api.request.OapiProcessinstanceCreateRequest;
import com.mol.supplier.config.Constant;
import com.mol.supplier.entity.dingding.login.DDUser;
import com.mol.supplier.entity.dingding.approve.PurchaseApprove;
import com.mol.supplier.entity.dingding.purchase.onlinePurchaseEntity.PageContentOnline;
import com.mol.supplier.entity.dingding.purchase.onlinePurchaseEntity.PageObj;
import com.mol.supplier.entity.dingding.purchase.onlinePurchaseEntity.PurTopArray;
import com.mol.supplier.entity.dingding.purchase.onlinePurchaseEntity.SellerArray;
import entity.ServiceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class OnlineApproveService {

    private static final Logger loggerOnline= LoggerFactory.getLogger(OnlineApproveService.class);

    @Autowired
    private ApproveService approveService;

    public ServiceResult<String> starApprove(Object obj, String userId){
        PageContentOnline pageObj=(PageContentOnline) obj;
        //获取传递过来的三个值
        List<PageObj> objs = pageObj.getPageObj();
        String remarks = pageObj.getRemarks();
        List<DDUser> approveList = pageObj.getApproves();

        //申请明细
        String applyCause = pageObj.getApplyCause();
        //审批实例发起人的userid(必须)
        String approveAtr="";
        for(int i=0;i<approveList.size();i++){
            String userid = approveList.get(i).getUserid();
            approveAtr += userid;
            if (i!=approveList.size()-1){
                approveAtr +=',';
            }
        }

        List<OapiProcessinstanceCreateRequest.FormComponentValueVo> list3 = new ArrayList<>();
        //总价，先写死，--------------------------------------------！！
        Double totlePrice=0.0;
        for (int i=0;i<objs.size();i++){
            Integer count = objs.get(i).getPurTopArray().get(0).getCount();
            if (count<=0){
                count=1;
            }
            List<SellerArray> sellerArray = objs.get(i).getSellerArray();
            if (sellerArray!=null){
                for (int j=0;j<sellerArray.size();j++){
                    if (sellerArray.get(j).getChecked()){
                        Double quote = sellerArray.get(j).getQuote();
                        totlePrice+=quote*count;
                    }
                }
            }
        }




        //遍历商品
        //将i=0的商品封装为一个对象，将其保存在数据库中
        PurchaseApprove pa = new PurchaseApprove();

        //申请事由
        OapiProcessinstanceCreateRequest.FormComponentValueVo  applyCauseVo = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        applyCauseVo.setName("申请事由");
        applyCauseVo.setValue(pageObj.getApplyCause());
        list3.add(applyCauseVo);

        for(int i =0;i<objs.size();i++){
            //该商品人为选中的商家
            SellerArray checkedSeller = new SellerArray();

            //智能推荐商家
            SellerArray autoRecSeller = new SellerArray();

            //获取采购类型集合：purTopArray
            List<PurTopArray> ptas = objs.get(i).getPurTopArray();
            //将i=0的值保存在pa对象中
            if (i==0){
                pa = getFirstItemDetail(ptas);
            }
            //获取商家集合：
            final List<SellerArray> sells = objs.get(i).getSellerArray();
            //采购明细对象
            OapiProcessinstanceCreateRequest.FormComponentValueVo purDetail = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            purDetail.setName("采购明细"+ StringUtil.toChinese(i+1+""));
            String testString = handlePurTypArrayToJson(ptas);
            //("采购明细String:"+testString);
            purDetail.setValue(testString);
            //新建商家明细对象
            OapiProcessinstanceCreateRequest.FormComponentValueVo sellerDetail = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            sellerDetail.setName("询价商家明细"+StringUtil.toChinese(i+1+""));
            String sellString = handleSellerArrayToJson(sells);
            System.out.println("商家明细String:"+sellString);
            sellerDetail.setValue(sellString);


            //获取已确认商家
            for(SellerArray sell:sells){
                if (sell.getChecked()!=null){
                    if(sell.getChecked()){
                        checkedSeller = sell;
                    }
                }if (sell.getAutoRec()!=null){
                    if(sell.getAutoRec()){
                        autoRecSeller = sell;
                    }
                }

            }

            //新建已确认商家明细对象
            OapiProcessinstanceCreateRequest.FormComponentValueVo checkedSellerDetail = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            checkedSellerDetail.setName("已确认商家明细"+ StringUtil.toChinese(i+1+""));
            String checkedSellerString = handleCheckedSellerToJson(checkedSeller,ptas,autoRecSeller);
            //System.out.println("已确认商家明细String:"+checkedSellerString);
            checkedSellerDetail.setValue(checkedSellerString);

            //ERP历史数据对比：
            OapiProcessinstanceCreateRequest.FormComponentValueVo compares = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            compares.setName("ERP历史数据对比"+StringUtil.toChinese(i+1+""));
            String compareString = handlerCompareToJson(ptas,sells);
            compares.setValue(compareString);

            list3.add(purDetail);
            list3.add(sellerDetail);
            list3.add(checkedSellerDetail);
            list3.add(compares);

        }

        //备注说明
        OapiProcessinstanceCreateRequest.FormComponentValueVo remarksVo = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        remarksVo.setName("备注说明");
        remarksVo.setValue(remarks);
        list3.add(remarksVo);

        //总价
        OapiProcessinstanceCreateRequest.FormComponentValueVo titlePrice = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        titlePrice.setName("总价");
        titlePrice.setValue(totlePrice+"");
        list3.add(titlePrice);

        //意见
        OapiProcessinstanceCreateRequest.FormComponentValueVo revieison = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        revieison.setName("意见");
        revieison.setValue("能手写否？");
        list3.add(revieison);

       System.out.println("list3"+JSON.toJSONString(list3));
        pa.setApplyCause(applyCause);
        //最后返回result
        ServiceResult sr = approveService.approve(Constant.ONLINEPUR_PROCESS_CODE, userId, -1L, JSON.toJSONString(list3), approveAtr, null,JSON.toJSONString(pageObj),pa);
        return sr;
    }

    /**
     * 将采购类型集合中的数封装起来
     * @param ptas
     * @return
     */
    private PurchaseApprove getFirstItemDetail(List<PurTopArray> ptas) {
        PurchaseApprove pa = new PurchaseApprove();
        if(ptas.size()!=0){
            pa.setGoodsType(ptas.get(0).getTypeName());
            pa.setGoodsName(ptas.get(0).getItemName());
            pa.setGoodsBranch(ptas.get(0).getUnit());
            pa.setGoodsSpecs(ptas.get(0).getNorms());
            pa.setGoodsQuantity(ptas.get(0).getCount()+"");
        }
        return pa;
    }

    /**
     * 处理与erp历史数据的对比
     * @param ptas
     * @param sells
     * @return
     */
    private String handlerCompareToJson(List<PurTopArray> ptas, List<SellerArray> sells) {
        int size = ptas.size();
        String testResult = "";
        for(int i=0;i<size;i++){
            //历史采购最低价
            OapiProcessinstanceCreateRequest.FormComponentValueVo hisLowPrice = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            hisLowPrice.setName("历史采购最低价");
            hisLowPrice.setValue(ptas.get(i).getHisPurLow()+"");
            //历史采购最高价
            OapiProcessinstanceCreateRequest.FormComponentValueVo hisHighPrice = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            hisHighPrice.setName("历史采购最高价");
            hisHighPrice.setValue(ptas.get(i).getHisPurHigh()+"");
            //物品采购平均价
            OapiProcessinstanceCreateRequest.FormComponentValueVo purAvgPrice = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            purAvgPrice.setName("物品采购平均价");
            purAvgPrice.setValue(ptas.get(i).getHisPurAvg()+"");

            //物品采购平均价
            OapiProcessinstanceCreateRequest.FormComponentValueVo empty = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            empty.setName("┠");
            empty.setValue("商家1报价&emsp;&nbsp;商家2报价&emsp;&nbsp;商家3报价");

            //最低价
            OapiProcessinstanceCreateRequest.FormComponentValueVo lowest = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            lowest.setName("最低价");
            String compareResultString = comparePrice(ptas.get(i).getHisPurLow(),sells);
            //System.out.println("compareResultString:"+compareResultString);
            lowest.setValue(compareResultString);

            //最高价
            OapiProcessinstanceCreateRequest.FormComponentValueVo highest = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            highest.setName("最高价");
            String compareResultString2 = comparePrice(ptas.get(i).getHisPurHigh(),sells);
            highest.setValue(compareResultString2);

            //平均价
            OapiProcessinstanceCreateRequest.FormComponentValueVo avg = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            avg.setName("平均价");
            String compareResultString3 = comparePrice(ptas.get(i).getHisPurAvg(),sells);
            avg.setValue(compareResultString3);
            String aresult = JSON.toJSONString(Arrays.asList(hisLowPrice, hisHighPrice,purAvgPrice,empty,lowest,highest,avg));
            //System.out.println("aresult:"+aresult);
            testResult += aresult;
            if(i != size-1){
                testResult += ",";
            }
        }
        String result = JSON.toJSONString(Arrays.asList(testResult));
        //去掉斜杠和最前后的引号
        result = delMark(result);
        System.out.println("handlerCompareToJson()......result:"+result);

        return result;
    }

    private String handleCheckedSellerToJson(SellerArray checkedSeller, List<PurTopArray> items, SellerArray autoRecSeller) {
        //已确认商家
        OapiProcessinstanceCreateRequest.FormComponentValueVo checkedSellerVo = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        checkedSellerVo.setName("已确认商家");
        checkedSellerVo.setValue(checkedSeller.getName());
        //提示
        OapiProcessinstanceCreateRequest.FormComponentValueVo hint = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        hint.setName("提示");
        String hintStr = getHintString(checkedSeller,items,autoRecSeller);
        hint.setValue(hintStr);

        String aresult = JSON.toJSONString(Arrays.asList(checkedSellerVo, hint));


        String result = JSON.toJSONString(Arrays.asList(aresult));
        //去掉斜杠和最前后的引号
        result = delMark(result);
        return result;
    }

    /**
     * 自定义 已确定商家与采购商品提示语句
     * @param seller    已确定商家
     * @param items     采购商品信息
     * @return
     */
    public static String getHintString(SellerArray seller, List<PurTopArray> items, SellerArray autoRecSeller){
        String resultStr = "";
        //去数据库获取在该商铺采购的次数：!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
        //int purCount = getPurCountBySellerIdAndOrgId(String sellerId,String orgId);
        int purCount = 10;
        resultStr +="该店铺采购次数 &nbsp;"+purCount+"\n";

        for(PurTopArray item:items){
            //去数据库获取在该商铺采购此商品的次数：!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
            //int purCount = getPurCountBySellerIdAndOrgId(String sellerId,String orgId,String itemId);
            int purItemCount = 2;
            resultStr +="该店铺采购"+item.getItemName()+"次数 &nbsp;"+purItemCount+"\n";

            if(purItemCount >0){
                //跟上次的购买价格作比较：
                //Double lastPrice = getLastPriceByItemIdAndSellerIdAndOrgId();
                Double lastPrice = 100.0;
                String compareStr = "";
                //if(lastPrice>)比较
                compareStr +="上涨100元";
                resultStr += item.getItemName()+"较往期采购价格"+compareStr+"\n";
            }

            if(seller == autoRecSeller){
                resultStr += "智能推荐商家";
            }else{
                resultStr += "非智能推荐商家";
            }

        }
        return resultStr;
    }
    /**
     * 把采购类型集合转换为适用于传参的json格式字符串
     * @param ptas
     * @return
     */
    public static String handlePurTypArrayToJson(List<PurTopArray> ptas){
        //获取该集合的长度：
        int size = ptas.size();
        String testResult = "";
        for(int i=0;i<size;i++){
            //采购类型
            OapiProcessinstanceCreateRequest.FormComponentValueVo purType = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            purType.setName("类型");
            purType.setValue(ptas.get(i).getTypeName());
            //采购名称
            OapiProcessinstanceCreateRequest.FormComponentValueVo purName = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            purName.setName("名称");
            purName.setValue(ptas.get(i).getItemName());
            //规格
            OapiProcessinstanceCreateRequest.FormComponentValueVo norms = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            norms.setName("规格");
            norms.setValue(ptas.get(i).getNorms());
            //数量
            OapiProcessinstanceCreateRequest.FormComponentValueVo count = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            count.setName("数量");
            count.setValue(ptas.get(i).getCount()+"");
            //物品来源
            OapiProcessinstanceCreateRequest.FormComponentValueVo platform=new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            platform.setName("物品信息来源");
            platform.setValue(ptas.get(i).getPlatForm());
            //单位
            OapiProcessinstanceCreateRequest.FormComponentValueVo unit = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            unit.setName("单位");
            unit.setValue(ptas.get(i).getUnit());
            String aresult = JSON.toJSONString(Arrays.asList(purType, purName,norms,count,platform,unit));
            testResult += aresult;
            if(i != size-1){
                testResult += ",";
            }
        }
        String result = JSON.toJSONString(Arrays.asList(testResult));
        //去掉斜杠和最前后的引号
        result = delMark(result);
        return result;
    }
    private String handleSellerArrayToJson(List<SellerArray> sells) {
        //获取该集合的长度：
        int size = sells.size();
        String testResult = "";
        for(int i=0;i<size;i++){

            //名称
            OapiProcessinstanceCreateRequest.FormComponentValueVo sellerName = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            sellerName.setName("名称");
            sellerName.setValue(sells.get(i).getName());
            //价格
            OapiProcessinstanceCreateRequest.FormComponentValueVo sellerPrice = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            sellerPrice.setName("价格");
            sellerPrice.setValue(Double.valueOf(sells.get(i).getQuote() == 0?0:sells.get(i).getQuote())+"");
            //商家电话
//            OapiProcessinstanceCreateRequest.FormComponentValueVo sellerPhone = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
//            sellerPhone.setName("商家电话");
//            sellerPhone.setValue(sells.get(i).getPhone()==null?"":sells.get(i).getPhone());
            //商家链接
            OapiProcessinstanceCreateRequest.FormComponentValueVo sellerLink=new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            sellerLink.setName("链接");
            sellerLink.setValue(sells.get(i).getLink());

            //图片

            OapiProcessinstanceCreateRequest.FormComponentValueVo images = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            images.setName("图片");
            List<String> urlList = sells.get(i).getInquiryUrl();
//            String urlStr = "";
//            if(urlList.size()>0){
//                for(int j=0;j<urlList.size();j++){
//                    urlStr += urlList.get(j);
//                    if(j!=urlList.size()-1){
//                        urlStr += ",";
//                    }
//                }
//            }
            String urlStr = JSONArray.toJSONString(urlList);
            images.setValue(urlStr);
            String aresult = "";
            //是否系统推荐
            boolean autoRec=false;
            if (sells.get(i).getAutoRec()!=null){
                autoRec=true;
            }
            //boolean autoRec = sells.get(i).getAutoRec();
            if(autoRec){
                //商家电话
                OapiProcessinstanceCreateRequest.FormComponentValueVo autoRecVo = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
                autoRecVo.setName("系统推荐");
                autoRecVo.setValue("是");
                aresult = JSON.toJSONString(Arrays.asList(sellerName, sellerPrice,sellerLink/*,sellerPhone*/,images,autoRecVo));
            }else{
                aresult = JSON.toJSONString(Arrays.asList(sellerName, sellerPrice,sellerLink/*,sellerPhone*/,images));
            }
            testResult += aresult;
            if(i != size-1){
                testResult += ",";
            }
        }
        String result = JSON.toJSONString(Arrays.asList(testResult));
        //去掉斜杠和最前后的引号
        result = delMark(result);
        result = StringUtil.madandd(result);
        System.out.println("result:"+result);
        return result;
    }
    /**
     * 去掉前后的第一个引号和斜线
     * @param result
     * @return
     */
    public static String delMark(String result){
        result = result.replace("\\","");
        int firstInd = result.indexOf("\"");
        String str1 = result.substring(0,firstInd);;//通过截取逗号前的字符串
        String str2 = result.substring(firstInd+1,result.length());//截取逗号后的字符串
        result = str1 + str2;
        //去掉最后一个引号
        int idx = result.lastIndexOf("\"");
        String str3 = result.substring(0,idx);;//通过截取逗号前的字符串
        String str4 = result.substring(idx+1,result.length());//截取逗号后的字符串
        result = str3 + str4;
        return result;
    }
    /**
     * 两个浮点数之间的对比
     * @param dou1
     * @param dou2
     * @return
     */
    public static String compareTwo(Double dou1,Double dou2){
        String result = "";
        if(dou1 !=null && dou2 !=null){
            Double dou3 =  dou1-dou2;

            if(dou3 > 0){
                result = "▲&nbsp;"+dou3;
            }else if(dou3 < 0){
                result = "▼&nbsp;"+(dou2-dou1);
            }else{
                result = "〓&nbsp;"+0;
            }
        }

        while (result.length()<20){
            result = result+"&nbsp;";
        }
        //System.out.println(result.length());
        return result;
    }

    /**
     * 某一数（例如最小数)与商家报价的对比
     * @param compareValue
     * @param sells
     * @return
     */
    public static String comparePrice(Double compareValue,List<SellerArray> sells){
        StringBuilder resultString = new StringBuilder();
        for(SellerArray seller:sells){
            String aCompareResult = compareTwo(compareValue,seller.getQuote());
            resultString.append("&nbsp;"+aCompareResult+"&emsp;&nbsp;&nbsp;");
        }
        return resultString.toString();
    }


}
