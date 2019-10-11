package com.mol.ddmanage.Ben.Office;

public class Push_history_list_ben
{
    private String number;//排序
    private String goods_type;//类型
    private String title;//标题
    private String id;//订单编号
    private String pk_supplier;//供应商
    private String create_time;//创建时间

    private String staff_id;//申请人
    private String user_name;//申请人姓名

    private String status;//状态 4议价  6审批 7通过 8拒绝

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPk_supplier() {
        return pk_supplier;
    }

    public void setPk_supplier(String pk_supplier) {
        this.pk_supplier = pk_supplier;
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


    public String getGoods_type() {
        return goods_type;
    }

    public void setGoods_type(String goods_type) {
        this.goods_type = goods_type;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
