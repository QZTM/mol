package com.mol.supplier.controller.microApp;

import com.alibaba.fastjson.JSON;
import com.mol.sms.SendMsmHandler;
import com.mol.supplier.config.MicroAttr;
import com.mol.supplier.entity.MicroApp.Salesman;
import com.mol.supplier.entity.MicroApp.Supplier;
import com.mol.supplier.mapper.newMysql.microApp.MicroSupplierMapper;
import com.mol.supplier.service.microApp.MicroAuthService;
import com.mol.supplier.service.microApp.MicroUserService;
import entity.ServiceResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

/**
 * 供应商认证控制器
 */
@Controller
@CrossOrigin
@RequestMapping("/auth")
public class MicroAuthController {

    /**
     * 营业执照
     */
    private static final String WHICHIMAGE_BUSSINESSLICENCEIMG = "bussinessLicence";
    /**
     * 身份证正面
     */
    private static final String WHICHIMAGE_LEGALBODYIDFRONTIMG = "legalBodyIdFront";
    /**
     * 身份证反面
     */
    private static final String WHICHIMAGE_LEGALBODYIDBACKIMG = "legalBodyIdBack";


    /**
     * 文件常量
     */
    /**
     * 协议书
     */
    private static final String WHICHFILE_PROTOCOL = "protocolFile";

    /**
     * 专利文件
     */
    private static final String WHICHFILE_PATENT = "patentFile";

    /**
     * 代理商资质证明文件1
     */
    private static final String WHICHFILE_AGENTQUALIFICATION1 = "agentQualificationFile1";

    /**
     * 代理商资质证明文件2
     */
    private static final String WHICHFILE_AGENTQUALIFICATION2 = "agentQualificationFile2";

    /**
     * 代理商资质证明文件3
     */
    private static final String WHICHFILE_AGENTQUALIFICATION3 = "agentQualificationFile3";


    @Autowired
    private MicroAuthService microAuthService;

    @Autowired
    private MicroSupplierMapper microSupplierMapper;

    @Autowired
    private MicroUserService microUserService;

    private SendMsmHandler sendMsmHandler = SendMsmHandler.getSendMsmHandler();


    @RequestMapping("/attr")
    public String showAuthChoosePage() {
        return "authenticate";
    }

    /**
     * 显示认证的表单页面
     * @param authType  danyi   zhanlve    jichu
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("/attr/{authType}")
    public String showAuthPage(@PathVariable String authType, HttpSession session, Model model) {
        String pageName = "";
        System.out.println("authType:");
        System.out.println(authType);
        Supplier supplier = microUserService.getSupplierFromSession(session);
        if (supplier == null) {
            throw new RuntimeException("请先注册后再试");
        }
        if ("jichu".equals(authType)) {
            model.addAttribute("pagenametitlefront", "基础");
            pageName = "authenticate_jichu2";

            System.out.println("supplier.getIfAttrNormal()..."+supplier.getIfAttrNormal());
            System.out.println("supplier.getSupstateNormal()..."+supplier.getSupstateNormal()+",MicroAttr.SUPSTATE_LOADING:"+MicroAttr.SUPSTATE_LOADING);
            //如果正在认证中显示认证中页面
            if(supplier.getIfAttrNormal() == 1 && supplier.getSupstateNormal() == MicroAttr.SUPSTATE_LOADING){
                pageName = "authenticate_shenhe";
            }

        } else if ("zhanlve".equals(authType)) {
            model.addAttribute("pagenametitlefront", "战略");
            pageName = "authenticate_zhanlueyudanyi";

            if(supplier.getIfAttrStrategy() == 1 && supplier.getSupstateStrategy() == MicroAttr.SUPSTATE_LOADING){
                pageName = "authenticate_shenhe";
            }

        } else if ("danyi".equals(authType)) {
            model.addAttribute("pagenametitlefront", "单一");
            pageName = "authenticate_zhanlueyudanyi";

            if(supplier.getIfAttrSingle() == 1 && supplier.getSupstateSingle() == MicroAttr.SUPSTATE_LOADING){
                pageName = "authenticate_shenhe";
            }

        }
        return pageName;
    }


    /**
     * 认证相关图片上传并保存入数据库
     *
     * @param file     图片二进制文件（blob)
     * @param whichImg 图片说明，bussinessLicence为营业执照；legalBodyIdFront法人身份证正面；legalBodyIdBack法人身份证反面；
     * @return
     */
    @RequestMapping(value = "/upload/img", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResult saveImage(MultipartFile file, String whichImg, HttpSession session) {
//       for(Object obj:map.keySet()){
////           System.out.println(obj.toString());
////       }
        if (file == null) {
            throw new RuntimeException("没有需要保存的图片");
        }
        Object userObj = session.getAttribute("user");
        if (userObj == null) {
            throw new RuntimeException("从session中获取用户失败");
        }
        Salesman salesman = (Salesman) userObj;
        String orgId = salesman.getPkSupplier();
        if (StringUtils.isEmpty(orgId)) {
            throw new RuntimeException("从session中未获取到供应商ID");
        }
        Supplier supplier = new Supplier();
        supplier.setPkSupplier(orgId);
        switch (whichImg) {
            case WHICHIMAGE_BUSSINESSLICENCEIMG:
                try {
                    supplier.setBusinessLicenceImg(file.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case WHICHIMAGE_LEGALBODYIDFRONTIMG:
                try {
                    supplier.setLegalbodyCardFrontImg(file.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case WHICHIMAGE_LEGALBODYIDBACKIMG:
                try {
                    supplier.setLegalbodyCardBackImg(file.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }

        //存入数据库：
        int saveResult = microSupplierMapper.updateByPrimaryKeySelective(supplier);
        if (saveResult != 1) {
            throw new RuntimeException("保存照片时出错");
        }

        return ServiceResult.success("保存成功");
    }


    @RequestMapping("/upload/file")
    @ResponseBody
    public ServiceResult uploadAuthFile(MultipartFile fileToUpload, String whichFile, HttpSession session) {
        System.out.println("whichFile:" + whichFile);

        if (fileToUpload == null) {
            throw new RuntimeException("没有需要保存的文件");
        }
        Salesman salesman = microUserService.getUserFromSession(session);
        String orgId = salesman.getPkSupplier();
        if (StringUtils.isEmpty(orgId)) {
            throw new RuntimeException("从session中未获取到供应商ID");
        }
        Supplier supplier = new Supplier();
        supplier.setPkSupplier(orgId);
        switch (whichFile) {
            case WHICHFILE_PROTOCOL:
                try {
                    supplier.setStrategySupplierProtocol(fileToUpload.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case WHICHFILE_AGENTQUALIFICATION1:
                try {
                    supplier.setAdditionalOne(fileToUpload.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case WHICHFILE_AGENTQUALIFICATION2:
                try {
                    supplier.setAdditionalTwo(fileToUpload.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case WHICHFILE_AGENTQUALIFICATION3:
                try {
                    supplier.setAdditionalThree(fileToUpload.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
        //存入数据库：
        int saveResult = microSupplierMapper.updateByPrimaryKeySelective(supplier);
        if (saveResult != 1) {
            throw new RuntimeException("保存文件时出错");
        }

        Supplier newSupplier = microSupplierMapper.selectByPrimaryKey(supplier.getPkSupplier());
        System.out.println("newSupplier:");
        System.out.println(newSupplier);
        session.setAttribute("supplier",newSupplier);
        return ServiceResult.success("保存成功");

    }


    @RequestMapping("/subData")
    @ResponseBody
    public ServiceResult auth(String supplier, @RequestParam String phoneCheckCode, HttpSession session) {

        Supplier supplierAlready = microUserService.getSupplierFromSession(session);
        if (supplierAlready == null) {
            throw new RuntimeException("请先注册后再试");
        }

        Enumeration<String> attributeNames = session.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String sessionName = attributeNames.nextElement();
            System.out.println("sessionName:" + sessionName);
        }


        //验证手机验证码
        System.out.println(supplier);
        Supplier supplier1 = JSON.parseObject(supplier, Supplier.class);
        System.out.println(supplier1);
        String phoneNum = supplier1.getAuthPhone();
        boolean phoneCheckResult = sendMsmHandler.checkPhoneCode(phoneNum, phoneCheckCode);
        if (!phoneCheckResult) {
            return ServiceResult.failureMsg("验证码不正确");
        }

        //存入数据库

        Salesman salesman = microUserService.getUserFromSession(session);

        supplier1.setPkSupplier(supplierAlready.getPkSupplier());
        supplier1.setLastUpdateUser(salesman.getId());
        int updated = microSupplierMapper.updateByPrimaryKeySelective(supplier1);
        if (updated == 1) {

            Supplier newSupplier = microSupplierMapper.selectByPrimaryKey(supplierAlready.getPkSupplier());
            session.setAttribute("supplier",newSupplier);

            return ServiceResult.success("提交成功", null);
        } else {
            return ServiceResult.failure("提交失败");
        }

    }
}
