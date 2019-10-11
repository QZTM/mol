package com.mol.ddmanage.Ben.Office;

public class Title_Details_Material//审核议价采购物料表用到的实体类
{
    private  String number;

    private String name;//名称
    private String materialspec;//规格
    private String measdoc;//单位
    private String goods_quantity;//数量


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


    public String getGoods_quantity() {
        return goods_quantity;
    }

    public void setGoods_quantity(String goods_quantity) {
        this.goods_quantity = goods_quantity;
    }

    public String getMeasdoc() {
        return measdoc;
    }

    public void setMeasdoc(String measdoc) {
        this.measdoc = measdoc;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
