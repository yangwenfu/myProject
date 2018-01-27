package com.xinyunlian.jinfu.user.dto;

import com.xinyunlian.jinfu.dealer.dto.DealerDto;
import com.xinyunlian.jinfu.user.enums.EDealerUserStatus;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by menglei on 2016年08月26日.
 */
public class DealerUserDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;

    private String dealerId;

    private String groupId;

    private String mobile;

    private String name;

    private Boolean isAdmin;

    private EDealerUserStatus status;

    private Boolean identityAuth;

    private Date identityAuthDate;

    private String idCardNo;

    private Boolean lived;

    private Double yituSimilarity;

    private Boolean yituIsPass;

    private String idFrontPic;

    private String idBackPic;

    private String linePic;

    private String livePic;

    private Boolean Passed;

    private String qrCode;

    private String qrCodeKey;

    private Boolean examPassed;

    private DealerDto dealerDto;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public EDealerUserStatus getStatus() {
        return status;
    }

    public void setStatus(EDealerUserStatus status) {
        this.status = status;
    }

    public DealerDto getDealerDto() {
        return dealerDto;
    }

    public void setDealerDto(DealerDto dealerDto) {
        this.dealerDto = dealerDto;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public Boolean getIdentityAuth() {
        return identityAuth;
    }

    public void setIdentityAuth(Boolean identityAuth) {
        this.identityAuth = identityAuth;
    }

    public Boolean getLived() {
        return lived;
    }

    public void setLived(Boolean lived) {
        this.lived = lived;
    }

    public Date getIdentityAuthDate() {
        return identityAuthDate;
    }

    public void setIdentityAuthDate(Date identityAuthDate) {
        this.identityAuthDate = identityAuthDate;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public Double getYituSimilarity() {
        return yituSimilarity;
    }

    public void setYituSimilarity(Double yituSimilarity) {
        this.yituSimilarity = yituSimilarity;
    }

    public String getIdFrontPic() {
        return idFrontPic;
    }

    public void setIdFrontPic(String idFrontPic) {
        this.idFrontPic = idFrontPic;
    }

    public String getIdBackPic() {
        return idBackPic;
    }

    public void setIdBackPic(String idBackPic) {
        this.idBackPic = idBackPic;
    }

    public String getLinePic() {
        return linePic;
    }

    public void setLinePic(String linePic) {
        this.linePic = linePic;
    }

    public String getLivePic() {
        return livePic;
    }

    public void setLivePic(String livePic) {
        this.livePic = livePic;
    }

    public Boolean getYituIsPass() {
        return yituIsPass;
    }

    public void setYituIsPass(Boolean yituIsPass) {
        this.yituIsPass = yituIsPass;
    }


    public Boolean getPassed() {
        return Passed;
    }

    public void setPassed(Boolean passed) {
        Passed = passed;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getQrCodeKey() {
        return qrCodeKey;
    }

    public void setQrCodeKey(String qrCodeKey) {
        this.qrCodeKey = qrCodeKey;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Boolean getExamPassed() {
        return examPassed;
    }

    public void setExamPassed(Boolean examPassed) {
        this.examPassed = examPassed;
    }
}
