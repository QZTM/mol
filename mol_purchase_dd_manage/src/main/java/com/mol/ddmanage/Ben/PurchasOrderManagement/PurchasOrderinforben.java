package com.mol.ddmanage.Ben.PurchasOrderManagement;

public class PurchasOrderinforben //供应商单个商品报价实体
{
    private String number;//序号
    private String material_name;//物料名称
    private String materialspec;//规格
    private String unit;//单位
    private String corp_name;//公司名称
    private String quote;//报价
    private String pk_supplier_id;//供应商id
    private String sign_status;//供应商的合同签署 签署状态：1：采购员、供应商都未签署   2：采购员已签署，供应商未签署   3：签署完成

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

    public String getPk_supplier_id() {
        return pk_supplier_id;
    }

    public void setPk_supplier_id(String pk_supplier_id) {
        this.pk_supplier_id = pk_supplier_id;
    }

    public String getSign_status() {
        return sign_status;
    }

    public void setSign_status(String sign_status) {
        this.sign_status = sign_status;
    }
}
