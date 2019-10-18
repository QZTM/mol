package com.mol.ddmanage.Ben.Office;

public class Purchase_Grogress_list_ben//采购合同管理列表实体类
{
    private String number;//序号
    private String goods_name;//名称
    private String id;//订单编号
    private String buy_channel_id;//采购类型 渠道ID 1=线下，2=线上，3=战略采购  4=询价采购 5=单一采购 6=加工,维修
    private String create_time;//创建时间
    private String user_name;//申请人
    private String goods_type;//物品类型
    private String status;//审批状态  //0代表新建，1代表正常进行，2代表完成，3代表中断,4代表最终被拒绝

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBuy_channel_id() {
        return buy_channel_id;
    }

    public void setBuy_channel_id(String buy_channel_id) {
        this.buy_channel_id = buy_channel_id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getGoods_type() {
        return goods_type;
    }

    public void setGoods_type(String goods_type) {
        this.goods_type = goods_type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
