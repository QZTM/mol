package com.mol.ddmanage.Ben;

public class Supplier_Review_ben
{
   private String name;//供应商名字
    private String supstate;//供应商状态0=潜在，1=核准，2=正在审批，4=审批被拒绝
    private String supplier_attr;//供应商属性，0：暂未设置；1：基础供应商；2：战略采购供应商；3：单一供应商
    private String last_update_time;//最后修改日期
    private String regist_time;//注册时间

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSupstate() {
        return supstate;
    }

    public void setSupstate(String supstate) {
        this.supstate = supstate;
    }

    public String getSupplier_attr() {
        return supplier_attr;
    }

    public void setSupplier_attr(String supplier_attr) {
        this.supplier_attr = supplier_attr;
    }

    public String getRegist_time() {
        return regist_time;
    }

    public void setRegist_time(String regist_time) {
        this.regist_time = regist_time;
    }

    public String getLast_update_time() {
        return last_update_time;
    }

    public void setLast_update_time(String last_update_time) {
        this.last_update_time = last_update_time;
    }
}
