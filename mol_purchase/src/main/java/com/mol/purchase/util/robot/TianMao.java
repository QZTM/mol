package com.mol.purchase.util.robot;

import com.mol.purchase.entity.dingding.purchase.onlinePurchaseEntity.SellerArray;
import com.mol.purchase.service.dingding.reptile.ReptileService;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 爬取天猫商城商品数据
 */
public class TianMao {


    public static List<SellerArray> getGoods(String goodsName, String itemType) throws IOException {
        //goodName ="毛巾";
        // 需要爬取商品信息的网站地址
        String url = "https://list.tmall.com/search_product.htm?q=" + goodsName;
        //String url = "https://list.tmall.com/search_product.htm?q="+goodName+"&sort=d";
        // 动态模拟请求数据
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        // 模拟浏览器浏览（user-agent的值可以通过浏览器浏览，查看发出请求的头文件获取）
        httpGet.setHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");
        CloseableHttpResponse response = httpclient.execute(httpGet);
        // 获取响应状态码
        int statusCode = response.getStatusLine().getStatusCode();
        //System.out.println("statusCode:"+statusCode);
        List<SellerArray> goodsList = new ArrayList<>();
        try {
            HttpEntity entity = response.getEntity();
            // 如果状态响应码为200，则获取html实体内容或者json文件
            if(statusCode == 200){
                String html = EntityUtils.toString(entity, Consts.UTF_8);
                // 提取HTML得到商品信息结果
                Document doc = null;
                // doc获取整个页面的所有数据
                doc = Jsoup.parse(html);
                //输出doc可以看到所获取到的页面源代码
                //System.out.println("doc:");
                //System.out.println(doc);
                // 通过浏览器查看商品页面的源代码，找到信息所在的div标签，再对其进行一步一步地解析
                Elements ulList = doc.select("div[class='view grid-nosku view-noCom']");

                //System.out.println("ulList.size:");
                //System.out.println(ulList.size());
                Elements liList = ulList.select("div[class='product']");
                // 循环liList的数据（具体获取的数据值还得看doc的页面源代码来获取，可能稍有变动）
                //System.out.println("liList.size:");
                //System.out.println(liList.size());

                for (Element item : liList) {
                    SellerArray sellerArray = new SellerArray();
                    String shopName = item.select("div[class='productShop']").select("a[class='productShop-name']").text();
                    //System.out.println("shopName:");
                    //System.out.println(shopName);
                    sellerArray.setName(shopName);
                    //System.out.println("店铺名称:"+shopName);

                    String salesVolume = item.select("p[class='productStatus']").select("span").select("em").text();
                    //System.out.println("销量:"+salesVolume);
                    //salesVolume = salesVolume.substring(0,salesVolume.lastIndexOf("笔")).trim();
                    if (salesVolume.length() != 0) {
                        if(salesVolume.contains("万")){
                            Double salesVolume1 = Double.valueOf(salesVolume.substring(0,salesVolume.lastIndexOf("万")).trim());
                            //System.out.println(salesVolume.substring(0,salesVolume.lastIndexOf("万")).trim());
                            Double salesVolume2 = salesVolume1*10000;
                            //System.out.println("销量1:"+salesVolume2);
                            sellerArray.setSalesVolume(salesVolume2);
                        }else{
                            Double salesVolume1 = Double.valueOf(salesVolume.substring(0,salesVolume.length() -1).trim());
                            //System.out.println("销量2:"+salesVolume1);
                            sellerArray.setSalesVolume(salesVolume1);
                        }
                    }else {
                        sellerArray.setSalesVolume((double)0);
                    }


                    // 商品ID
                    String id = item.select("div[class='product']").select("p[class='productStatus']").select("span[class='ww-light ww-small m_wangwang J_WangWang']").attr("data-item");
                    //System.out.println("商品ID："+id);
                    // 商品名称
                    String name = item.select("p[class='productTitle']").select("a").attr("title");
                    //System.out.println("商品名称："+name);
                    // 商品价格
                    String price = item.select("p[class='productPrice']").select("em").attr("title");
                    sellerArray.setQuote(Double.valueOf(price));
                    //System.out.println("商品价格："+price);
                    // 商品网址
                    String goodsUrl = item.select("p[class='productTitle']").select("a").attr("href");
                    sellerArray.setLink("https:"+goodsUrl);
                    //System.out.println("商品网址："+goodsUrl);
                    // 商品图片网址
                    String imgUrl = item.select("div[class='productImg-wrap']").select("a").select("img").attr("data-ks-lazyload");
                    if (imgUrl.length() == 0){
                        imgUrl = item.select("div[class='productImg-wrap']").select("a").select("img").attr("src");
                    }
                    //System.out.println("商品图片网址："+imgUrl);
                    List<String> goodList1 = new ArrayList<>();
                    goodList1.add("https:"+ imgUrl);
                    sellerArray.setInquiryUrl(goodList1);
                    sellerArray.setDataChannel(ReptileService.dataFromTianMao);
                    goodsList.add(sellerArray);

                    //System.out.println("------------------------------------");
                }
                // 消耗掉实体

                EntityUtils.consume(response.getEntity());
            } else {
                // 消耗掉实体
                EntityUtils.consume(response.getEntity());
            }
        } finally {
            response.close();
        }
       return goodsList;
    }



}

