package com.mol.supplier.service.itemAnditemType;

import com.mol.supplier.entity.itemAndItemType.Item;
import com.mol.supplier.entity.itemAndItemType.ItemForApp;
import com.mol.supplier.entity.itemAndItemType.ItemType;
import com.mol.supplier.mapper.itemAndItemType.ItemMapper;
import com.mol.supplier.mapper.itemAndItemType.ItemTypeMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import util.TimeUtil;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.Future;


@Service
@Transactional
public class ItemAndTypeService {

    @Resource
    private ItemTypeMapper itemTypeMapper;

    @Resource
    private ItemMapper itemMapper;

    @Cacheable(value = "item",key = "'itemTypeFirst'")
    public List<ItemType> getItemTypeFirst(){
        return itemTypeMapper.selectItemTypeListOfFirst();
    }

    @Cacheable(value = "item",key = "'itemType-'+#id")
    public List<ItemType> getItemTypeByParentId(String id) {
        Map selectMap = new HashMap();
        selectMap.put("id",id);
        return itemTypeMapper.getItemTypeByParentId(selectMap);
    }

   @Cacheable(value = "item",key = "'item-'+#id")
    public List<Item> getItemByTypeId(String id) {
        Map paraMap = new HashMap();
        paraMap.put("itemTypeId",id);
        paraMap.put("status",2);
       paraMap.put("sameDayOfLastYear", TimeUtil.get_last_year_date());
        List<Item> items=itemMapper.selectItemsByTypeId(paraMap);//查询历史价格
        return items;
    }

    @CacheEvict(value = "item",allEntries = true)
    public void clearCache(){

    }

    public ItemType getTopType(String typeId) {
        Map selectMap = new HashMap();
        selectMap.put("id",typeId);
        ItemType thisItemType = itemTypeMapper.getItemTypeByMap(selectMap);
        if(!thisItemType.getPkParent().equals("~")){
            thisItemType = getTopType(thisItemType.getPkParent());
        }
        return thisItemType;
    }


    public ItemType getItenTypeByMap(Map map){
        return itemTypeMapper.getItemTypeByMap(map);
    }


    /**
     * 把（根据物料类型id获取的）物料结合根据名称汇总为ItemForApp数据类型
     * @param itemList
     * @return
     */
    public List<ItemForApp> changeItemToItemForApp(List<Item> itemList){
        if(itemList == null || itemList.size() == 0){
            throw new RuntimeException("需要转换的集合没有内容");
        }
        List<ItemForApp> itemForApps = new ArrayList<>();
        Map<String,ItemForApp> nameMap = new HashMap<String,ItemForApp>();

        for(Item item:itemList){
            String itemName = item.getName();
            if(nameMap.containsKey(itemName)){
                nameMap.get(itemName).getItemList().add(item);
            }else{
                ItemForApp itemForApp = new ItemForApp();
                itemForApp.setName(itemName);
                List<Item> newItemList = new ArrayList<>();
                newItemList.add(item);
                itemForApp.setItemList(newItemList);
                nameMap.put(itemName,itemForApp);
            }
        }
        itemForApps = new ArrayList<ItemForApp>(nameMap.values());
        return itemForApps;
    }

    public List<ItemType> searchItemTypeByKey(String key) {

        Map allAboutItemTypeMap = new HashMap();
        Map resultMap = new HashMap();
        //把所有的第一级物料类型维护为一个list
        //查找符合条件的全部物料类型
        //把所有的物料类型id放到一个map里面
        //遍历这些物料类型，每一个进行处理，查找其父类id,设置父类id的open为true,canOpen为true,把自己放入父类的list里面
        Example example = new Example(ItemType.class);
        example.and().andLike("name","%"+key+"%");
        List<ItemType> allItemTypeOk = itemTypeMapper.selectByExample(example);
        System.out.println("allItemTypeOk.size:"+allItemTypeOk.size());
        for(ItemType itemType:allItemTypeOk){
            allAboutItemTypeMap.put(itemType.getId(),itemType);
            getParentItemTypeBySon(resultMap,allAboutItemTypeMap,itemType);
        }
        System.out.println("resultMap.size:"+resultMap.size());
        List<ItemType> resultItemTypeList = new ArrayList<>();

        for(Object obj:resultMap.values()){
            resultItemTypeList.add((ItemType)obj);
        }
        return resultItemTypeList;
    }



    public void getParentItemTypeBySon(Map resultMap,Map allAboutItemTypeMap,ItemType son){
        ItemType theFather = new ItemType();
        if(!"~".equals(son.getPkParent())){
            Example example = new Example(ItemType.class);
            example.and().andEqualTo("id",son.getPkParent());
            theFather =  itemTypeMapper.selectOneByExample(example);
            theFather.setCanOpen(true);
            theFather.setOpen(true);
            Object ifAlreadyHave = allAboutItemTypeMap.get(theFather.getId());
            //System.out.println("ifAlreadyHave..."+(ifAlreadyHave == null)+"父,id:"+theFather.getId()+",本id:"+son.getId());
            if(ifAlreadyHave != null){
                ItemType hasFather = (ItemType)ifAlreadyHave;
                hasFather.setOpen(true);
                hasFather.setCanOpen(true);
                hasFather.getList().add(son);
                allAboutItemTypeMap.put(theFather.getId(),hasFather);
                return;
            }else{
                theFather.getList().add(son);
                allAboutItemTypeMap.put(theFather.getId(),theFather);
            }
                 getParentItemTypeBySon(resultMap,allAboutItemTypeMap,(ItemType) allAboutItemTypeMap.get(theFather.getId()));

        }else{
            resultMap.put(son.getId(),son);
        }
    }


    @Async
    public Future<String> getItemTypeNameByKey(String key){
        System.out.println("getItemTypeNameByKey..."+key);
        String name = itemTypeMapper.getItemTypeNameByKey(key);
        return new AsyncResult<>(name);
    }
}
