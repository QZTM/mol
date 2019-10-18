package com.mol.supplier.controller.third;

import com.alibaba.fastjson.JSON;
import com.mol.supplier.entity.MicroApp.DDUser;
import com.mol.supplier.entity.MicroApp.Salesman;
import com.mol.supplier.entity.MicroApp.Supplier;
import com.mol.supplier.entity.dingding.purchase.enquiryPurchaseEntity.PageArray;
import com.mol.supplier.entity.dingding.purchase.enquiryPurchaseEntity.PurchaseArray;
import com.mol.supplier.entity.dingding.solr.fyPurchase;
import com.mol.supplier.entity.thirdPlatform.*;
import com.mol.supplier.service.dingding.purchase.EsService;
import com.mol.supplier.service.microApp.MicroUserService;
import com.mol.supplier.service.third.ThirdPlatformService;
import entity.PageBean;
import entity.ServiceResult;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/index")
@CrossOrigin
@Log
public class ThirdPlatformController {

    @Autowired
    private ThirdPlatformService platformService;

    @Resource
    private EsService esService;

    @Autowired
    private MicroUserService microUserService;


    private String htmlName = null;

    @RequestMapping("/findAll")
    public String index(String pageName, ModelMap map,HttpSession session) {
        log.info(".../index/findAll");
        Object salesmanObj = session.getAttribute("user");
        if(salesmanObj == null){
            System.out.println("session中么有用户信息");
        }else{
            System.out.println("session中有用户信息");
        }


        //获取第三方轮播图信息
        log.info("获取第三方轮播图信息-------");
        List<Lunbo> lunboList = platformService.findLunBo();
        map.addAttribute("lunboList", lunboList);
        System.out.println(lunboList);
        log.info("获取企业排版信息-------");
        //获取企业排版信息
        List<Enter> enterList = platformService.findAll();
        System.out.println(enterList);
        map.addAttribute("enterList", enterList);
        //询价采购需求
        //采购渠道
        String buyId = "";
        //返回的页面
        if (pageName == null) {
            pageName = "询价采购";
        }
        switch (pageName) {
            //添加没个页面的序号和对应的页面
            case "询价采购":
                buyId = 4 + "";
                break;
            case "战略采购":
                buyId = 3 + "";
                break;
            case "单一来源":
                buyId = 5 + "";
                break;
            case "加工维修":
                buyId = 6 + "";
                break;
        }


        List<fyPurchase> orderList = platformService.findList(buyId, 1, 5);
        map.addAttribute("orderList", orderList);
        map.addAttribute("pageName", pageName);
        map.addAttribute("index", true);
        return "index";
    }

    /**
     * 获取首页的询价list
     */
    @RequestMapping("/getOrderList")
    @ResponseBody
    public List<fyPurchase> getOrderList(String buyId, String status) {
        //List<fyPurchase> orderList = platformService.findList(buyId, 1, 5);
        List<fyPurchase> orderList = platformService.findListByStatusAndId(buyId, 1, 8, status);
        return orderList;
    }

    /**
     * 获取采购内容List
     */
    @RequestMapping(value = "/findList", method = RequestMethod.GET)
    public String findList(String pageName, int pageNumber, int pageSize, String keyword, String status, String goodsType, String tickert, ModelMap map) {
        System.out.println("ti:" + tickert);
        //采购渠道
        String buyId = "";
        //返回的页面

        switch (pageName) {
            //添加没个页面的序号和对应的页面
            case "询价采购":
                buyId = 4 + "";
                htmlName = "index_xjcg";
                break;
            case "战略采购":
                buyId = 3 + "";
                htmlName = "index_zlcg";
                break;
            case "单一来源":
                buyId = 5 + "";
                htmlName = "index_dyly";
                break;
            case "加工维修":
                buyId = 6 + "";
                htmlName = "index_jgwx";
                break;
        }
        if (keyword == null || keyword == "") {
            keyword = "";
        }
        if (status == null || status == "") {
            status = "";
        }
        if (goodsType == null || goodsType == "") {
            goodsType = "";
        }

        PageBean pb = new PageBean();
        List<fyPurchase> list = null;
        int count = 0;
        if (keyword != null && keyword != "") {
            pb = esService.list(pageNumber, pageSize, keyword);
            list = pb.getList();
            count = pb.getTotalCount();
        } else {
//            //获取采购内容的list
            list = platformService.findLIstByStatusAndGoodsTypeAndBuyChannelId(buyId, status, goodsType, pageNumber, pageSize);
            count = platformService.findCount(buyId, status, goodsType);
        }
        map.addAttribute("list", list);

        //设置pageBean的值
//        int count=platformService.findCount(buyId);
        map.addAttribute("count", count);
        pb.setTotalCount(count);
        pb.setPageSize(pageSize);
        pb.setTotalPage(pb.getTotalCount() % pb.getPageSize() == 0 ? pb.getTotalCount() / pb.getPageSize() : pb.getTotalCount() / pb.getPageSize() + 1);
        pb.setCurrentPage(pageNumber);

        //获取物料第一级分类
        List<BdMarbasclass> marbasFirstList = platformService.findMarbasClassFirstList();
        map.addAttribute("marList", marbasFirstList);

        //设置pb页面的页码
        //pb.setList(platformService.getPageNumList(pb));
        //map.addAttribute("pb",pb);
        //System.out.println(pb);
        map.addAttribute("searchVal", "");
        map.addAttribute("keyword", keyword);
        map.addAttribute("status", status);

        return htmlName;
    }

    /**
     * 采购类型 行业类别
     *
     * @param status
     * @param goodsType
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @RequestMapping("/getMarbasClassNameList")
    @ResponseBody
    public List<fyPurchase> getMarbasClassNameList(String buyChannelId, String status, String goodsType, Integer pageNumber, Integer pageSize) {


        //获取行业类别
        //行业类别
        List<fyPurchase> list = platformService.findLIstByStatusAndGoodsTypeAndBuyChannelId(buyChannelId, status, goodsType, pageNumber, pageSize);

        return list;
    }

    @RequestMapping("/getMarbasClassNameList/getCount")
    @ResponseBody
    public Integer getCount(String buyChannelId, String status, String goodsType) {
        return platformService.findCount(buyChannelId, status, goodsType);
    }


    /**
     * 询价采购，
     * 详情页
     *
     * @param id
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/selectOne", method = RequestMethod.GET)
    public String selectOne(String id, ModelMap modelMap, HttpSession session) {

        System.out.println(id);
        fyPurchase purchase = platformService.selectOneById(id);

        //查询报价商家的数量
        String quoteCounts = purchase.getQuoteCounts();
        modelMap.addAttribute("quoteCount", quoteCounts);
        //--------------------------------
        //定义最后要传递到页面的list
        List<PurchaseArray> purList = new ArrayList<>();
        //解析json字段
        PageArray pageArray = JSON.parseObject(purchase.getGoodsDetail(), PageArray.class);
        System.out.println("pagearrayObj:" + pageArray);
        //json字段中的详情集合
        List<PurchaseArray> purchaseArrayList = pageArray.getPurchaseArray();
        System.out.println("purchaseArray:" + purchaseArrayList);
        for (int i = 0; i < purchaseArrayList.size(); i++) {
            PurchaseArray pur = new PurchaseArray();
            pur.setMaterialId(purchaseArrayList.get(i).getMaterialId());
            pur.setCount(purchaseArrayList.get(i).getCount());
            pur.setItemName(purchaseArrayList.get(i).getItemName());
            pur.setTypeName(purchaseArrayList.get(i).getTypeName());
            pur.setUnit(purchaseArrayList.get(i).getUnit());
            pur.setNorms(purchaseArrayList.get(i).getNorms());
            purList.add(pur);
        }

        //判断登录人所在公司是否已经报价
        boolean quoteOrNot = false;
        Supplier supplier = microUserService.getSupplierFromSession(session);
        if (supplier != null) {

            String supplierId = supplier.getPkSupplier();
            quoteOrNot = platformService.isQuote(id, supplierId);
        } else {
            quoteOrNot = true;
        }


        modelMap.addAttribute("quoteOrNot", quoteOrNot);
        modelMap.addAttribute("pur", purchase);
        //json字符串
        modelMap.addAttribute("purList", purList);
        //规定申请人的个数
        System.out.println("人数" + pageArray.getQuoteSellerNum());
        modelMap.addAttribute("quoNum", pageArray.getQuoteSellerNum());
        //备注说明
        String remarks = pageArray.getRemarks();
        modelMap.addAttribute("remakes", remarks);
        //截止时间
        String deadLine = pageArray.getDeadLine();
        modelMap.addAttribute("dealLine", deadLine);
        //供货周期
        String supplyCycle = pageArray.getSupplyCycle();
        modelMap.addAttribute("supplyCycle", supplyCycle);
        //支付方式
        String payMent = pageArray.getPayMent();
        modelMap.addAttribute("payMent", payMent);
        //技术支持电话
        String technicalSupportTelephone = pageArray.getTechnicalSupportTelephone();
        modelMap.addAttribute("tst", technicalSupportTelephone);
        //专家评审
        String expertReview = pageArray.getExpertReview();
        modelMap.addAttribute("expertReview",expertReview);
        //专家评审费
        String expertReward = pageArray.getExpertReward();
        modelMap.addAttribute("expertReward",expertReward);


        if (purchase.getBuyChannelId() == 4) {
            htmlName = "xjcg_detail";
        }
        if (purchase.getBuyChannelId() == 3) {
            htmlName = "zlcg_detail";
        }
        if (purchase.getBuyChannelId() == 5) {
            htmlName = "dyly_detail";
        }
        if (purchase.getBuyChannelId() == 6) {
            htmlName = "jgwx_detail";
        }
        return htmlName;
    }

    /**
     * 前往我要报价页面，获取当前页面的物品id
     */
    @RequestMapping("/quote")
    public String toGetQuote(String id, ModelMap map, HttpSession session) {

        String supplierId = microUserService.getUserFromSession(session).getPkSupplier();
        DDUser ddUser = (DDUser)session.getAttribute("ddUser");
        String ddUserId = ddUser.getUserid();

        fyPurchase purchase = platformService.selectOneById(id);
        if (purchase.getBuyChannelId() == 3) {
            //判断供应商是不是战略供应商，不是则跳转报错页面；
        }
        if (purchase.getBuyChannelId() == 5) {
            //判断供应商是不是单一供应商，不是则跳转；
        }

        //定义最后要传递到页面的list
        List<PurchaseArray> purList = new ArrayList<>();
        //解析json字段
        PageArray pageArray = JSON.parseObject(purchase.getGoodsDetail(), PageArray.class);
        System.out.println("pagearrayObj:" + pageArray);
        //json字段中的详情集合
        List<PurchaseArray> purchaseArrayList = pageArray.getPurchaseArray();
        System.out.println("purchaseArray:" + purchaseArrayList);
        for (int i = 0; i < purchaseArrayList.size(); i++) {
            PurchaseArray pur = new PurchaseArray();
            pur.setFyPurchaseId(id);
            pur.setMaterialId(purchaseArrayList.get(i).getMaterialId());
            pur.setCount(purchaseArrayList.get(i).getCount());
            pur.setItemName(purchaseArrayList.get(i).getItemName());
            pur.setTypeName(purchaseArrayList.get(i).getTypeName());
            pur.setUnit(purchaseArrayList.get(i).getUnit());
            pur.setNorms(purchaseArrayList.get(i).getNorms());
            //上次价格
            pur.setAvgPriceHistory(platformService.getAvgPrice(supplierId,id));
            purList.add(pur);
        }
        map.addAttribute("purList", purList);
        map.addAttribute("purId", id);
        return "index_detail_quote";
    }

    /**
     * 提交报价信息
     */
    @RequestMapping(value = "/getQuoteFromForm", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResult getQuoteFromForm(QuoteModel quotes, HttpSession session) {

        String supplierId = microUserService.getUserFromSession(session).getPkSupplier();
        DDUser ddUser = (DDUser)session.getAttribute("ddUser");
        String ddUserId = ddUser.getUserid();


        if (quotes == null) {
            return null;
        }
        //通过ddid查询对应人员id
        Salesman salesman=platformService.findSalesManId(ddUserId);

        platformService.saveQuote(quotes, supplierId, salesman);
        //添加报价记录
        platformService.addQuoteCountsByPkMaterialId(quotes.getQuotes().get(0).getFyPurchaseId());
        //return "redirect:/index/findAll";
        return ServiceResult.success("报价成功");

    }


    /**
     * 进度页面上拉刷新
     *
     * @param status
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    @ResponseBody
    public List<fyPurchase> refresh(String buyId, String status, int pageNum, int pageSize, String keyword, String goodsType) {
        System.out.println("刷新页面...");
        if (buyId == null && status == "" && buyId == "") {
            return null;
        }
        List<fyPurchase> fList = null;
        PageBean pb = null;
        if (keyword != null && keyword != "") {
            pb = esService.list(pageNum, pageSize, keyword);
            fList = pb.getList();
        } else {
            fList = platformService.findLIstByStatusAndGoodsTypeAndBuyChannelId(buyId, status, goodsType, pageNum, pageSize);

        }

        return fList;
    }


}
