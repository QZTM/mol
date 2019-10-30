package com.mol.ddmanage.Ben.PurchasOrderManagement;

public class PurchasOrderinforben //供应商单个商品报价实体
{
    private String number;//序号
    private String material_name;//物料名称
    private String materialspec;//规格
    private String unit;//单位
    private String corp_name;//公司名称
    private String quote;//报价

    public String getMaterial_name() {
        return material_name;
    }

    public void setMaterial_name(String material_name) {
        this.material_name = material_name;
    }

    public String getMaterialspec() {
        return materialspec;
    }

    public void setMaterialspec(String materialspec) {
        this.materialspec = materialspec;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCorp_name() {
        return corp_name;
    }

    public void setCorp_name(String corp_name) {
        this.corp_name = corp_name;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
