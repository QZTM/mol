package com.mol.ddmanage.Ben.PurchasOrderManagement;

public class PlannedPurchaseben//采购订单计划采购的实体类
{
    private String number;

    private String name;
    private String materialspec;//规格
    private String pk_measdoc;//单位
    private String goods_quantity;//数量

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaterialspec() {
        return materialspec;
    }

    public void setMaterialspec(String materialspec) {
        this.materialspec = materialspec;
    }

    public String getPk_measdoc() {
        return pk_measdoc;
    }

    public void setPk_measdoc(String pk_measdoc) {
        this.pk_measdoc = pk_measdoc;
    }

    public String getGoods_quantity() {
        return goods_quantity;
    }

    public void setGoods_quantity(String goods_quantity) {
        this.goods_quantity = goods_quantity;
    }
}
