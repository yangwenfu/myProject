package com.xinyunlian.jinfu.user.dto.req;

import com.xinyunlian.jinfu.user.dto.OperationLogDto;
import com.xinyunlian.jinfu.user.dto.RiskControlDto;

/**
 * 密码Dto
 * Created by KimLl on 2016/8/23.
 */
public class PasswordDto extends OperationLogDto<RiskControlDto> {
    private static final long serialVersionUID = 1L;

    private String mobile;

    private String oldPassword;

    private String newPassword;

    private String dealPassword;

    private String verifyCode;

    private String recUser;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getDealPassword() {
        return dealPassword;
    }

    public void setDealPassword(String dealPassword) {
        this.dealPassword = dealPassword;
    }

    public String getRecUser() {
        return recUser;
    }

    public void setRecUser(String recUser) {
        this.recUser = recUser;
    }
}
