package com.xinyunlian.jinfu.user.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.common.entity.id.IdInjectionEntityListener;
import com.xinyunlian.jinfu.dealer.entity.DealerPo;
import com.xinyunlian.jinfu.user.enums.EDealerUserStatus;
import com.xinyunlian.jinfu.user.enums.converter.EDealerUserStatusConverter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by menglei on 2016年08月26日.
 */
@Entity
@Table(name = "dealer_user")
@EntityListeners(IdInjectionEntityListener.class)
public class DealerUserPo extends BaseMaintainablePo {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "DEALER_ID")
    private String dealerId;

    @Column(name = "GROUP_ID")
    private String groupId;

    @Column(name = "MOBILE")
    private String mobile;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "NAME")
    private String name;

    @Column(name = "IS_ADMIN")
    private Boolean isAdmin;

    @Column(name = "STATUS")
    @Convert(converter = EDealerUserStatusConverter.class)
    private EDealerUserStatus status;

    @Column(name = "IDENTITY_AUTH")
    private Boolean identityAuth;

    @Column(name = "IDENTITY_AUTH_DATE")
    private Date identityAuthDate;

    @Column(name = "ID_CARD_NO")
    private String idCardNo;

    @Column(name = "LIVED")
    private Boolean lived;

    @Column(name = "YITU_SIMILARITY")
    private Double yituSimilarity;

    @Column(name = "YITU_IS_PASS")
    private Boolean yituIsPass;

    @Column(name = "ID_FRONT_PIC")
    private String idFrontPic;

    @Column(name = "ID_BACK_PIC")
    private String idBackPic;

    @Column(name = "LINE_PIC")
    private String linePic;

    @Column(name = "LIVE_PIC")
    private String livePic;

    @Column(name = "IS_PASS")
    private Boolean Passed;

    @Column(name = "QR_CODE")
    private String qrCode;

    @Column(name = "QR_CODE_KEY")
    private String qrCodeKey;

    @Column(name = "EXAM_IS_PASS")
    private Boolean examPassed;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "DEALER_ID", insertable = false, updatable = false)
    private DealerPo dealerPo;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public DealerPo getDealerPo() {
        return dealerPo;
    }

    public void setDealerPo(DealerPo dealerPo) {
        this.dealerPo = dealerPo;
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

    public Boolean getYituIsPass() {
        return yituIsPass;
    }

    public void setYituIsPass(Boolean yituIsPass) {
        this.yituIsPass = yituIsPass;
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
