package entity.dd;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class DDUser implements Serializable {
    private Boolean active;
    private String avatar;
    private List<Long> department;
    private String dingId;
    private String email;
    private Long errcode;
    private String errmsg;
    private String extattr;
    private Date hiredDate;
    private String inviteMobile;
    private Boolean isAdmin;
    private Boolean isBoss;
    private Boolean isCustomizedPortal;
    private Boolean isHide;
    private String isLeaderInDepts;
    private Boolean isLimited;
    private Boolean isSenior;
    private String jobnumber;
    private String managerUserId;
    private Boolean memberView;
    private String mobile;
    private String mobileHash;
    private String name;
    private String nickname;
    private String openId;
    private String orderInDepts;
    private String orgEmail;
    private String position;
    private String remark;
    private List<Roles> roles;
    private String stateCode;
    private String tel;
    private String unionid;
    private String userid;
    private String workPlace;


    @Data
    public static class Roles {
        private String groupName;
        private Long id;
        private String name;
        private Long type;
    }

}
