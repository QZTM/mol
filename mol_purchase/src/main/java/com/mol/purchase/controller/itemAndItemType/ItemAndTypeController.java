package com.mol.purchase.controller.itemAndItemType;

import entity.ServiceResult;
import com.mol.purchase.entity.itemAndItemType.Item;
import com.mol.purchase.entity.itemAndItemType.ItemForApp;
import com.mol.purchase.entity.itemAndItemType.ItemType;
import com.mol.purchase.service.itemAnditemType.ItemAndTypeService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/item")
public class ItemAndTypeController {

    @Resource
    private ItemAndTypeService itemAndTypeService;

    @RequestMapping("/getTypeFirst")
    public ServiceResult<List> getItemTypeFirst(){
        List<ItemType> itemTypeList = itemAndTypeService.getItemTypeFirst();
        return ServiceResult.success(itemTypeList);
    }

    @RequestMapping("/getTopType")
    public ServiceResult<ItemType> getTopType(String typeId){
        ItemType clickedTopType = itemAndTypeService.getTopType(typeId);
        return ServiceResult.success(clickedTopType);
    }

    @RequestMapping("/getTypeByParentId")
    public ServiceResult getTypeByParentId(String id){
        System.out.println("getTypeByParentId"+id);
        List<ItemType> itemTypeList = itemAndTypeService.getItemTypeByParentId(id);
        return ServiceResult.success(itemTypeList);
    }

    @RequestMapping("/getItemByTypeId")
    public ServiceResult getItemByTypeId(String id){
        System.out.println("getItemByTypeId"+id);
        List<Item> itemList = itemAndTypeService.getItemByTypeId(id);
        //转换为钉钉小程序需要的数据类型ItemForApp
        List<ItemForApp> itemForApps = itemAndTypeService.changeItemToItemForApp(itemList);
        return ServiceResult.success(itemForApps);
    }

    @RequestMapping(value = "/clearItemCache",method = RequestMethod.DELETE)
    public ServiceResult clearItemCache(){
        itemAndTypeService.clearCache();
        return ServiceResult.success("清理缓存成功");
    }

    @RequestMapping(value = "/searchItemTypeByKey",method = RequestMethod.GET)
    public ServiceResult searchItemTypeByKey(String key){
        System.out.println("searchItemTypeByKey...key:"+key);
        List<ItemType> itemTypes = itemAndTypeService.searchItemTypeByKey(key);
        return ServiceResult.success(itemTypes);
    }

}
