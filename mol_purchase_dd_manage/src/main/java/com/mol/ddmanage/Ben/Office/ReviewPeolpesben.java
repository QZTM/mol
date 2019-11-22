package com.mol.ddmanage.Ben.Office;

public class ReviewPeolpesben
{
    private String name;//人员姓名
    private String time;//审批时间
    private String Remarks;//备注
    private byte[] image;//人员头像
    private String ApprovalStatus="0";//审核状态 0未审核 1通过 2拒绝

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getApprovalStatus() {
        return ApprovalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        ApprovalStatus = approvalStatus;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }
}
