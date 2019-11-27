package com.mol.ddmanage.Ben.Office;

public class AnnouncementEditPageben
{
    private String number;//序号
    private String announcemenId;//公告id
    private String messageType;//消息通知类型
    private String userids;//员工id
    private String alluser;//是否所有人员可见 1所有可见 0指定人员可见
    private String htmltext;//包含格式的内容
    private String text;//内容
    private String titl;//标题
    private String creadtime;//创建时间
    private String creaduserid;//创建人id
    private String creadName;//创建人名子

    public String getAnnouncemenId() {
        return announcemenId;
    }

    public void setAnnouncemenId(String announcemenId) {
        this.announcemenId = announcemenId;
    }

    public String getUserids() {
        return userids;
    }

    public void setUserids(String userids) {
        this.userids = userids;
    }

    public String getAlluser() {
        return alluser;
    }

    public void setAlluser(String alluser) {
        this.alluser = alluser;
    }

    public String getHtmltext() {
        return htmltext;
    }

    public void setHtmltext(String htmltext) {
        this.htmltext = htmltext;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitl() {
        return titl;
    }

    public void setTitl(String titl) {
        this.titl = titl;
    }

    public String getCreadtime() {
        return creadtime;
    }

    public void setCreadtime(String creadtime) {
        this.creadtime = creadtime;
    }

    public String getCreaduserid() {
        return creaduserid;
    }

    public void setCreaduserid(String creaduserid) {
        this.creaduserid = creaduserid;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCreadName() {
        return creadName;
    }

    public void setCreadName(String creadName) {
        this.creadName = creadName;
    }
}
