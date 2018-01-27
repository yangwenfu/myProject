package com.xinyunlian.jinfu.store.dto;

import com.xinyunlian.jinfu.bank.dto.BankCardInfoDto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 云码商铺Entity
 *
 * @author menglei
 */

public class BindDetailDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long storeId;

    private String storeName;

    private String qrCodeUrl;

    private String storeHeaderPic;//门头照

    private String storeInnerPic;//店内照

    private String bizLicencePic;//营业执照

    private Boolean uploadIdCard = false;//是否上传身份证，false=未传 true=已传

    private Boolean uploadIdCardFront = false;//是否上传正面身份证，false=未传 true=已传

    private Boolean uploadIdCardBack = false;//是否上传反面身份证，false=未传 true=已传

    private Boolean uploadSign = false;//是否上传签名，false=未传 true=已传

    private Date bizEndDate;//营业执照到期日

    private List<BankCardInfoDto> bankList = new ArrayList<>();

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    public List<BankCardInfoDto> getBankList() {
        return bankList;
    }

    public void setBankList(List<BankCardInfoDto> bankList) {
        this.bankList = bankList;
    }

    public String getStoreHeaderPic() {
        return storeHeaderPic;
    }

    public void setStoreHeaderPic(String storeHeaderPic) {
        this.storeHeaderPic = storeHeaderPic;
    }

    public Boolean getUploadSign() {
        return uploadSign;
    }

    public void setUploadSign(Boolean uploadSign) {
        this.uploadSign = uploadSign;
    }

    public String getBizLicencePic() {
        return bizLicencePic;
    }

    public void setBizLicencePic(String bizLicencePic) {
        this.bizLicencePic = bizLicencePic;
    }

    public Date getBizEndDate() {
        return bizEndDate;
    }

    public void setBizEndDate(Date bizEndDate) {
        this.bizEndDate = bizEndDate;
    }

    public Boolean getUploadIdCard() {
        return uploadIdCard;
    }

    public void setUploadIdCard(Boolean uploadIdCard) {
        this.uploadIdCard = uploadIdCard;
    }

    public String getStoreInnerPic() {
        return storeInnerPic;
    }

    public void setStoreInnerPic(String storeInnerPic) {
        this.storeInnerPic = storeInnerPic;
    }

    public Boolean getUploadIdCardFront() {
        return uploadIdCardFront;
    }

    public void setUploadIdCardFront(Boolean uploadIdCardFront) {
        this.uploadIdCardFront = uploadIdCardFront;
    }

    public Boolean getUploadIdCardBack() {
        return uploadIdCardBack;
    }

    public void setUploadIdCardBack(Boolean uploadIdCardBack) {
        this.uploadIdCardBack = uploadIdCardBack;
    }
}


