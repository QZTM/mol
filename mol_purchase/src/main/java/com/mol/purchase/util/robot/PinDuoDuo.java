package com.mol.purchase.util.robot;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mol.purchase.entity.dingding.purchase.onlinePurchaseEntity.SellerArray;
import com.mol.purchase.service.dingding.reptile.ReptileService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PinDuoDuo {

    public static List<SellerArray> getGoods(String goodsName, String itemType ){
        List<SellerArray> goodsList = new ArrayList<>();
        HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        String url = "http://api01.6bqb.com/pdd/search?apikey=D4B98E5DF182DB47CA607ECA743215A0&keyword=" + goodsName + "&page=1";
        String jsonStr = "{xxx}";
        try{
            httpClient = new DefaultHttpClient();
            httpPost = new HttpPost(url);
            httpPost.addHeader("Content-Type", "application/json");
            StringEntity se = new StringEntity(jsonStr);
            se.setContentType("text/json");
            se.setContentEncoding(new BasicHeader("Content-Type", "application/json"));
            httpPost.setEntity(se);
            HttpResponse response = httpClient.execute(httpPost);
            if(response != null){
                HttpEntity resEntity = response.getEntity();
                if(resEntity != null){
                    result = EntityUtils.toString(resEntity,"utf-8");
                    JSONObject jsStr1 = JSONObject.parseObject(result);
                    List<Map> resultMap = (ArrayList)JSONArray.parseArray(jsStr1.getString("data"),Map.class);

                    for (Map map: resultMap) {
                        SellerArray sellerArray = new SellerArray();
                        //店铺名称

                        if (!map.containsKey("shopName")){
                            String shopName = "暂无店铺名称";
                            sellerArray.setName(shopName);
                        }else {
                            String shopName = map.get("shopName").toString();
                            sellerArray.setName(shopName);
                        }

                        //商品图片地址
                        List<String> imgUrl = new ArrayList<String>();
                        imgUrl.add(map.get("thumb_url").toString());
                        sellerArray.setInquiryUrl(imgUrl);
                        //商品名称
                        sellerArray.setGoodsName(map.get("short_name").toString());
                        //商品详情
                        sellerArray.setLink(map.get("url").toString());
                        //销量
                        String salesVolume = map.get("sales_tip").toString();

                        if(salesVolume.contains("万")){
                            salesVolume = salesVolume.substring(2,salesVolume.lastIndexOf("万")).trim();
                            Double salesVolume1 = Double.parseDouble(salesVolume)*10000;
                            sellerArray.setSalesVolume(salesVolume1);
                        }else{
                            if(salesVolume.length() == 0){
                                sellerArray.setSalesVolume(0.00);
                            }else{
                                salesVolume = salesVolume.substring(2,salesVolume.lastIndexOf("件")).trim();
                                Double salesVolume1 = Double.parseDouble(salesVolume);
                                sellerArray.setSalesVolume(salesVolume1);
                            }

                        }

                        //价格
                        sellerArray.setQuote(Double.parseDouble(map.get("normal_price").toString()));
                        sellerArray.setDataChannel(ReptileService.dataFromPinDuoDuo);
                        goodsList.add(sellerArray);
                    }
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        //return result;
        return goodsList;
    }


}
