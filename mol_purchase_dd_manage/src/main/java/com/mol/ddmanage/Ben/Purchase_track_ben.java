package com.mol.ddmanage.Ben;


public class Purchase_track_ben//订单实时跟踪用到的ben
{
   private String id;
    private  String buy_channel_id;//渠道ID 3=战略采购  4=询价采购 5=单一采购 6=加工，维修
    private String goods_name;//名称
    private  String goods_type;//类型
    private  String create_time;//创建时间
    private  String staff_id;//申请人id
    private String status;//0代表新建，1代表正常进行，2代表完成，3代表中断,4代表最终被拒绝
    private   String order_number;//单号/订单编号


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

    public String getGoods_type() {
        return goods_type;
    }

    public void setGoods_type(String goods_type) {
        this.goods_type = goods_type;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(String staff_id) {
        this.staff_id = staff_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }
}
