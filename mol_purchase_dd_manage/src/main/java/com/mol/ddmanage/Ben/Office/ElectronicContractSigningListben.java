package com.mol.ddmanage.Ben.Office;

public class ElectronicContractSigningListben//合同签署列表展示页实体类
{
    private String number;//序号
    private String buy_channel_id;//类型如战略采购 渠道ID 1=线下，2=线上，3=战略采购  4=询价采购 5=单一采购 6=加工,维修
    private String goods_name;//对应标题
    private String id;//订单编号
    private String suplier_name;//确认供应商
    private String create_time;//创建时间
    private String user_name;//申请人
    private String contract_statu;//合同签署状态 签署状态：1：采购员、供应商都未签署   2：采购员已签署，供应商未签署   3：签署完成

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSuplier_name() {
        return suplier_name;
    }

    public void setSuplier_name(String suplier_name) {
        this.suplier_name = suplier_name;
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

    public String getContract_statu() {
        return contract_statu;
    }

    public void setContract_statu(String contract_statu) {
        this.contract_statu = contract_statu;
    }
}
