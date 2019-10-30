package com.mol.ddmanage.Ben.SupplierManagement;

public class SetSupplierinforben//供应商详细资料实体类
{
    private String pk_supplier;
    private String supprop;//供应商类型  供应商类型0=外部单位，1=内部单位，
    private String name;//供应商名称
    private String buslicensenum;//营业执照号
    private String business_scope;//经营范围

    private String location_province_js_index;//所在地所在省编码
    private String location_city_js_index;//所在地所在市编码
    private String location_area_js_index;//所在地所在区县编码
    private String Location;//所在地

    private String registered_address;//注册地
    private String industry_first;//行业类别
    private String legalbody_name;//法人姓名
    private String legalbody_idcard_number;//法人身份证号
    private String telephone;//电话
    private String money;//保证金 在pui_supplier_deposit表中
    private byte[] business_licence_img;//营业执照照片
    private byte[] legalbody_card_front_img;//身份证正面
    private byte[] legalbody_card_back_img;//身份证反面
    private byte[] additional_one;//补充资料1
    private byte[] additional_two;//补充资料1
    private byte[] additional_three;//补充资料1
    private byte[] additional_four;//补充资料1


    public String getPk_supplier() {
        return pk_supplier;
    }

    public void setPk_supplier(String pk_supplier) {
        this.pk_supplier = pk_supplier;
    }

    public String getSupprop() {
        return supprop;
    }

    public void setSupprop(String supprop) {
        this.supprop = supprop;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBuslicensenum() {
        return buslicensenum;
    }

    public void setBuslicensenum(String buslicensenum) {
        this.buslicensenum = buslicensenum;
    }

    public String getBusiness_scope() {
        return business_scope;
    }

    public void setBusiness_scope(String business_scope) {
        this.business_scope = business_scope;
    }

    public String getLocation_province_js_index() {
        return location_province_js_index;
    }

    public void setLocation_province_js_index(String location_province_js_index) {
        this.location_province_js_index = location_province_js_index;
    }

    public String getLocation_city_js_index() {
        return location_city_js_index;
    }

    public void setLocation_city_js_index(String location_city_js_index) {
        this.location_city_js_index = location_city_js_index;
    }

    public String getLocation_area_js_index() {
        return location_area_js_index;
    }

    public void setLocation_area_js_index(String location_area_js_index) {
        this.location_area_js_index = location_area_js_index;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getRegistered_address() {
        return registered_address;
    }

    public void setRegistered_address(String registered_address) {
        this.registered_address = registered_address;
    }

    public String getIndustry_first() {
        return industry_first;
    }

    public void setIndustry_first(String industry_first) {
        this.industry_first = industry_first;
    }

    public String getLegalbody_name() {
        return legalbody_name;
    }

    public void setLegalbody_name(String legalbody_name) {
        this.legalbody_name = legalbody_name;
    }

    public String getLegalbody_idcard_number() {
        return legalbody_idcard_number;
    }

    public void setLegalbody_idcard_number(String legalbody_idcard_number) {
        this.legalbody_idcard_number = legalbody_idcard_number;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public byte[] getBusiness_licence_img() {
        return business_licence_img;
    }

    public void setBusiness_licence_img(byte[] business_licence_img) {
        this.business_licence_img = business_licence_img;
    }

    public byte[] getLegalbody_card_front_img() {
        return legalbody_card_front_img;
    }

    public void setLegalbody_card_front_img(byte[] legalbody_card_front_img) {
        this.legalbody_card_front_img = legalbody_card_front_img;
    }

    public byte[] getLegalbody_card_back_img() {
        return legalbody_card_back_img;
    }

    public void setLegalbody_card_back_img(byte[] legalbody_card_back_img) {
        this.legalbody_card_back_img = legalbody_card_back_img;
    }

    public byte[] getAdditional_one() {
        return additional_one;
    }

    public void setAdditional_one(byte[] additional_one) {
        this.additional_one = additional_one;
    }

    public byte[] getAdditional_two() {
        return additional_two;
    }

    public void setAdditional_two(byte[] additional_two) {
        this.additional_two = additional_two;
    }

    public byte[] getAdditional_three() {
        return additional_three;
    }

    public void setAdditional_three(byte[] additional_three) {
        this.additional_three = additional_three;
    }

    public byte[] getAdditional_four() {
        return additional_four;
    }

    public void setAdditional_four(byte[] additional_four) {
        this.additional_four = additional_four;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
