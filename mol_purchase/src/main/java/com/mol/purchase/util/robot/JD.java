package com.mol.purchase.util.robot;

import com.mol.purchase.entity.dingding.purchase.onlinePurchaseEntity.SellerArray;
import com.mol.purchase.service.dingding.reptile.ReptileService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JD {
    public static List<SellerArray> getGoods(String goodName, String itemType, int sort) throws IOException{
        List<SellerArray> goodsList = new ArrayList<>();
        String keyWord = goodName;
        String url = "https://search.jd.com/Search?keyword="+keyWord+"&enc=utf-8&wq=%E8%80%B3%E6%9C%BA&pvid=4b34b686025345888ccc1fade5566451&psort=" + sort ;
        //网址分析
        /*keyword:关键词（京东搜索框输入的信息）
         * enc：编码方式（可改动:默认UTF-8）
         * psort //搜索方式  默认按综合查询 不给psort值 0=综合排序，1=价格降序，2=价格升序，3=销量排序，4=评论数排序，5=新品排序，
         * page=分页（不考虑动态加载时按照基数分页，每一页30条，这里就不演示动态加载）
         * 注意：受京东商品个性化影响，准确率无法保障
         * */
        Document doc = Jsoup.connect(url).maxBodySize(0).get();
        //doc获取整个页面的所有数据
        Elements ulList = doc.select("ul[class='gl-warp clearfix']");
        Elements liList = ulList.select("li[class='gl-item']");

        //循环liList的数据
        for (Element item : liList) {
            //排除广告位置
            SellerArray sellerArray = new SellerArray();
            if (!item.select("span[class='p-promo-flag']").text().trim().equals("广告")) {

                String name =  item.select("div[class='p-name p-name-type-2']").select("em").text();
                sellerArray.setGoodsName(name);

                String shopName = item.select("div[class='p-shop']").select("a").attr("title");
                sellerArray.setName(shopName);

                String goodsUrl = "https:" + item.select("div[class='p-img']").select("a").attr("href");

                sellerArray.setLink(goodsUrl);

                String imgUrl = "https:" + item.select("div[class='p-img']").select("a").select("img").attr("source-data-lazy-img");
                List<String> goodList1 = new ArrayList<>();
                goodList1.add(imgUrl);

                sellerArray.setInquiryUrl(goodList1);

                String price = item.select("div[class='p-price']").select("i").text();
                if (price.length() == 0){
                    price =  item.select("div[class='p-price']").select("strong").attr("data-price");
                }

                sellerArray.setQuote(Double.valueOf(price));

                sellerArray.setDataChannel(ReptileService.dataFromJD);

                goodsList.add(sellerArray);
            }
        }
        return goodsList;
    }

}
