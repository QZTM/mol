package com.mol.ddmanage.Ben.PurchasOrderManagement;

public class PurchasOrderListben
{
   private String number;
   private String id;//订单编号
    private String buy_channel_id;//渠道ID 1=线下，2=线上，3=战略采购  4=询价采购 5=单一采购 6=加工,维修
    private String goods_name;//标题
    private String marbasclass_name;//行业类别
    private String user_name;//申请人名称
    private String create_time;//创建时间

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getMarbasclass_name() {
        return marbasclass_name;
    }

    public void setMarbasclass_name(String marbasclass_name) {
        this.marbasclass_name = marbasclass_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
