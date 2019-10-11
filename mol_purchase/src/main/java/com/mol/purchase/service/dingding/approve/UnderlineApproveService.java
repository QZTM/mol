package com.mol.purchase.service.dingding.approve;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.request.OapiProcessinstanceCreateRequest;
import com.mol.purchase.config.Constant;
import com.mol.purchase.entity.dingding.purchase.underlinePurchasePageEntity.PageContent;
import com.mol.purchase.entity.dingding.purchase.underlinePurchasePageEntity.PageObj;
import com.mol.purchase.entity.dingding.purchase.underlinePurchasePageEntity.PurTopArray;
import com.mol.purchase.entity.dingding.purchase.underlinePurchasePageEntity.SellerArray;
import com.mol.purchase.entity.dingding.approve.PurchaseApprove;
import entity.ServiceResult;
import entity.dd.DDUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.StringUtil;

import java.util.*;

@Service
public class UnderlineApproveService {

    private static final Logger bizLogger = LoggerFactory.getLogger(UnderlineApproveService.class);


    @Autowired
    private ApproveService approveService;


    /**
     * 发起审批
     * @param pageObj   需要审批的数据
     * @param userId    审批人钉钉ID
     * @return
     */
    public ServiceResult<String> startApprove(Object pageObj, String userId) {
    //处理页面传过来的数据
        //System.out.println("UnderlineApproveService....startApprove.....");
        PurchaseApprove pa = new PurchaseApprove();
        PageContent pageObjs = (PageContent)pageObj;
        //System.out.println(pageObjs);
        List<PageObj> objs = pageObjs.getPageObj();
        String remarks = pageObjs.getRemarks();

        String applyCause = pageObjs.getApplyCause();

        //处理多个审批人参数
        List<DDUser> approveList = pageObjs.getApproves();
        String approveStr = "";
        for(int i=0;i<approveList.size();i++){
            approveStr += approveList.get(i).getUserid();
            if(i!=approveList.size()-1){
                approveStr +=",";
            }
        }
        //System.out.println("approveStr:"+approveStr);
        //System.out.println("遍历页面数据：");
        List<OapiProcessinstanceCreateRequest.FormComponentValueVo> list3 = new ArrayList<>();
        //总价，！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
        Double totlePrice = 0.0;
        //遍历一种商品：





        for(int j=0;j<objs.size();j++){

            //该商品人为选中的商家
            SellerArray checkedSeller = new SellerArray();
            //智能推荐商家
            SellerArray autoRecSeller = new SellerArray();

            //获取采购类型集合：
            List<PurTopArray> ptas = objs.get(j).getPurTopArray();

            //获取商家集合：
            List<SellerArray> sells = objs.get(j).getSellerArray();

            //获取第一个采购商品详情用于进度页面显示
            if(j == 0){
                pa = getFirstItemDetail(ptas);
            }



            //新建采购明细对象
            OapiProcessinstanceCreateRequest.FormComponentValueVo purDetail = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            purDetail.setName("采购明细"+ StringUtil.toChinese(j+1+""));
            String testString = handlePurTypArrayToJson(ptas);
            //("采购明细String:"+testString);
            purDetail.setValue(testString);


            //新建商家明细对象
            OapiProcessinstanceCreateRequest.FormComponentValueVo sellerDetail = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            sellerDetail.setName("询价商家明细"+StringUtil.toChinese(j+1+""));
            String sellString = handleSellerArrayToJson(sells);
            //System.out.println("商家明细String:"+sellString);
            sellerDetail.setValue(sellString);

            //获取已确认商家
            for(SellerArray sell:sells){
                if(sell.getChecked()){
                    checkedSeller = sell;
                    Double thisQuote = sell.getQuote();
                }
                if(sell.getAutoRec()){
                    autoRecSeller = sell;
                }
            }

            //新建已确认商家明细对象
            OapiProcessinstanceCreateRequest.FormComponentValueVo checkedSellerDetail = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            checkedSellerDetail.setName("已确认商家明细"+ StringUtil.toChinese(j+1+""));
            String checkedSellerString = handleCheckedSellerToJson(checkedSeller,ptas,autoRecSeller);
            //System.out.println("已确认商家明细String:"+checkedSellerString);
            checkedSellerDetail.setValue(checkedSellerString);

            //ERP历史数据对比：
            OapiProcessinstanceCreateRequest.FormComponentValueVo compares = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            compares.setName("ERP历史数据对比"+StringUtil.toChinese(j+1+""));
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
        pa.setBuyChannelId(Constant.线下采购);
        ServiceResult sr = approveService.approve(Constant.UNDERLINEPUR_PROCESS_CODE,userId,-1L,JSON.toJSONString(list3),approveStr,null, JSONObject.toJSONString(pageObjs),pa);
        return sr;
    }




    public static String handleCheckedSellerToJson(SellerArray checkedSeller,List<PurTopArray> items,SellerArray autoRecSeller){

        //已确认商家
        OapiProcessinstanceCreateRequest.FormComponentValueVo checkedSellerVo = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        checkedSellerVo.setName("已确认商家");
        checkedSellerVo.setValue(checkedSeller.getName());
        //提示     ①   ②   ③    ④
        Map hintStrMap = getHintString(checkedSeller,items,autoRecSeller);
        OapiProcessinstanceCreateRequest.FormComponentValueVo hint1 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        hint1.setName("①");
        hint1.setValue((String)hintStrMap.get(1));

        OapiProcessinstanceCreateRequest.FormComponentValueVo hint2 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        hint2.setName("②");
        hint2.setValue((String)hintStrMap.get(2));

        OapiProcessinstanceCreateRequest.FormComponentValueVo hint3 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        hint3.setName("③");
        hint3.setValue((String)hintStrMap.get(3));

        OapiProcessinstanceCreateRequest.FormComponentValueVo hint4 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        hint4.setName("④");
        hint4.setValue((String)hintStrMap.get(4));

        String aresult = JSON.toJSONString(Arrays.asList(checkedSellerVo, hint1,hint2,hint3,hint4));
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
    public static Map getHintString(SellerArray seller, List<PurTopArray> items, SellerArray autoRecSeller){
        Map resultMap = new HashMap();
        //String resultStr = "";
        //去数据库获取在该商铺采购的次数：!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
        //int purCount = getPurCountBySellerIdAndOrgId(String sellerId,String orgId);
        int purCount = 10;

        resultMap.put(1,"该店铺采购次数 &nbsp;"+purCount);
        //resultStr +="该店铺采购次数 &nbsp;"+purCount+"\n";

        for(PurTopArray item:items){
            //去数据库获取在该商铺采购此商品的次数：!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
            //int purItemCount = getPurCountBySellerIdAndOrgId(String sellerId,String orgId,String item.);
            int purItemCount = 2;
            resultMap.put(2,"该店铺采购"+item.getItemName()+"次数 &nbsp;"+purItemCount);
            //resultStr +="该店铺采购"+item.getItemName()+"次数 &nbsp;"+purItemCount+"\n";

            if(purItemCount >0){
                //跟上次的购买价格作比较：
                //Double lastPrice = getLastPriceByItemIdAndSellerIdAndOrgId();
                Double lastPrice = 100.0;
                String compareStr = "";
                //if(lastPrice>)比较
                compareStr +="上涨100元";
                resultMap.put(3,item.getItemName()+"较往期采购价格"+compareStr);
                //resultStr += item.getItemName()+"较往期采购价格"+compareStr+"\n";
            }
            String isAuto = "";
            if(seller == autoRecSeller){
                isAuto = "智能推荐商家";
            }else{
                isAuto = "非智能推荐商家";
            }
            resultMap.put(4,isAuto);

        }
        return resultMap;
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
            //单位
            OapiProcessinstanceCreateRequest.FormComponentValueVo unit = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            unit.setName("单位");
            unit.setValue(ptas.get(i).getUnit());
            String aresult = JSON.toJSONString(Arrays.asList(purType, purName,norms,count,unit));
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

    /**
     * 获取第一个商品的详情，用于存入数据库及进度页面的显示
     * @param ptas
     * @return
     */
    public static PurchaseApprove getFirstItemDetail(List<PurTopArray> ptas){
        PurchaseApprove pa = new PurchaseApprove();
        if(ptas.size() != 0){
            PurTopArray pta = ptas.get(0);
            pa.setGoodsType(pta.getTypeName());
            pa.setGoodsName(pta.getItemName());
            pa.setGoodsBranch(pta.getUnit());
            pa.setGoodsSpecs(pta.getNorms());
            pa.setGoodsQuantity(pta.getCount()+"");
        }
        return pa;

    }

    /**
     * 把商家集合转换为适用于传参的json格式字符串
     * @param sells
     * @return
     */
    public static String handleSellerArrayToJson(List<SellerArray> sells){
        //获取该集合的长度：
        int size = sells.size();
        String testResult = "";
        for(int i=0;i<size;i++){
            //名称
            if(sells.get(i).getName() == null){
                break;
            }
            OapiProcessinstanceCreateRequest.FormComponentValueVo sellerName = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            sellerName.setName("名称");
            sellerName.setValue(sells.get(i).getName());
            //价格
            OapiProcessinstanceCreateRequest.FormComponentValueVo sellerPrice = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            sellerPrice.setName("价格");
            System.out.println(sells.get(i).getQuote());
            //sellerPrice.setValue(Double.valueOf(sells.get(i).getQuote() == 0?0:sells.get(i).getQuote())+"");
            sellerPrice.setValue(Double.valueOf((sells.get(i).getQuote()==null || sells.get(i).getQuote() == 0)?0:sells.get(i).getQuote())+"");
            //商家电话
            OapiProcessinstanceCreateRequest.FormComponentValueVo sellerPhone = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            sellerPhone.setName("商家电话");
            sellerPhone.setValue(sells.get(i).getPhone()==null?"":sells.get(i).getPhone());


            //录像地址
            OapiProcessinstanceCreateRequest.FormComponentValueVo video = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            video.setName("录像");
            video.setValue(sells.get(i).getVideoUrl()==null?"":sells.get(i).getVideoUrl());

            OapiProcessinstanceCreateRequest.FormComponentValueVo images = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            images.setName("图片");

            List<String> urlList = sells.get(i).getInquiryUrl();

//            String[] imgUrls = new String[urlList.size()];
//
//            imgUrls = urlList.toArray(imgUrls);

            String urlStr = JSONArray.toJSONString(urlList);
//            if(urlStr.length()!=2){
//                urlStr = delMark(urlStr);
//            }
            images.setValue(urlStr);



//            String urlStr = "";
//            if(urlList.size()>0){
//                for(int j=0;j<urlList.size();j++){
//                    urlStr += urlList.get(j);
//                    if(j!=urlList.size()-1){
//                        urlStr += ",";
//                    }
//                }
//            }
            String aresult = "";
            //是否系统推荐
            boolean autoRec = sells.get(i).getAutoRec();
            if(autoRec){
                //商家电话
                OapiProcessinstanceCreateRequest.FormComponentValueVo autoRecVo = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
                autoRecVo.setName("系统推荐");
                autoRecVo.setValue("是");
                aresult = JSON.toJSONString(Arrays.asList(sellerName, sellerPrice,sellerPhone,images,video,autoRecVo));
            }else{
                aresult = JSON.toJSONString(Arrays.asList(sellerName, sellerPrice,sellerPhone,images,video));
            }
            testResult += aresult;
            if(i != size-1){
                testResult += ",";
            }
        }
        String result = JSON.toJSONString(Arrays.asList(testResult));
        //去掉斜杠和最前后的引号
        result = delMark(result);
        System.out.println("result;");
        result = StringUtil.madandd(result);
        System.out.println(result);
        return result;
    }


    /**
     * 处理与erp历史数据的对比
     * @param ptas
     * @param sells
     * @return
     */
    public static String handlerCompareToJson(List<PurTopArray> ptas,List<SellerArray> sells){
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
        //System.out.println("handlerCompareToJson()......result:"+result);
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

    /**
     * 两个浮点数之间的对比
     * @param dou1
     * @param dou2
     * @return
     */
    public static String compareTwo(Double dou1,Double dou2){
        String result = "";
        if(dou1 == null || dou2 == null){
            result = "暂无数据";
        }
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


}
