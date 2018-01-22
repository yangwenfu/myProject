package com.xinyunlian.jinfu.store.dto;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 资料补全
 *
 * @author menglei
 */

public class SupplyDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long storeId;

    private String storeName;

    private String qrCodeNo;

    private Boolean uploadIdCard = false;//是否上传身份证，false=未传 true=已传

    private Boolean uploadSign = false;//是否上传签名，false=未传 true=已传

    private String storeHeaderPic;//门头照

    private String storeInnerPic;//店内照

    private String bizLicencePic;//营业执照

    private String bankCardPic;//银行卡照

    private String idCardFrontPicBase64;//身份证正面

    private String idCardBackPicBase64;//身份证反面

    private String storeHeaderPicBase64;//门头照

    private String storeInnerPicBase64;//店内照

    private String bizLicencePicBase64;//营业执照

    private String bankCardPicBase64;//银行卡照

    private Date bizEndDate;//营业执照到期日

    private String bankCardNo;//银行卡号

    private String bankName;//银行名

    private Boolean completeInfo = false;//资料是否齐全 false=不全 true=全

    private Integer bankCardType;//银行卡类型 1=银行卡 2=对公账号许可证

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

    public String getQrCodeNo() {
        return qrCodeNo;
    }

    public void setQrCodeNo(String qrCodeNo) {
        this.qrCodeNo = qrCodeNo;
    }

    public Boolean getUploadIdCard() {
        return uploadIdCard;
    }

    public void setUploadIdCard(Boolean uploadIdCard) {
        this.uploadIdCard = uploadIdCard;
    }

    public String getStoreHeaderPic() {
        return storeHeaderPic;
    }

    public void setStoreHeaderPic(String storeHeaderPic) {
        this.storeHeaderPic = storeHeaderPic;
    }

    public String getIdCardFrontPicBase64() {
        return idCardFrontPicBase64;
    }

    public void setIdCardFrontPicBase64(String idCardFrontPicBase64) {
        this.idCardFrontPicBase64 = idCardFrontPicBase64;
    }

    public String getIdCardBackPicBase64() {
        return idCardBackPicBase64;
    }

    public void setIdCardBackPicBase64(String idCardBackPicBase64) {
        this.idCardBackPicBase64 = idCardBackPicBase64;
    }

    public String getStoreHeaderPicBase64() {
        return storeHeaderPicBase64;
    }

    public void setStoreHeaderPicBase64(String storeHeaderPicBase64) {
        this.storeHeaderPicBase64 = storeHeaderPicBase64;
    }

    public Boolean getUploadSign() {
        return uploadSign;
    }

    public void setUploadSign(Boolean uploadSign) {
        this.uploadSign = uploadSign;
    }

    public String getBizLicencePicBase64() {
        return bizLicencePicBase64;
    }

    public void setBizLicencePicBase64(String bizLicencePicBase64) {
        this.bizLicencePicBase64 = bizLicencePicBase64;
    }

    public String getBizLicencePic() {
        return bizLicencePic;
    }

    public void setBizLicencePic(String bizLicencePic) {
        this.bizLicencePic = bizLicencePic;
    }

    public String getBankCardPicBase64() {
        return bankCardPicBase64;
    }

    public void setBankCardPicBase64(String bankCardPicBase64) {
        this.bankCardPicBase64 = bankCardPicBase64;
    }

    public String getBankCardPic() {
        return bankCardPic;
    }

    public void setBankCardPic(String bankCardPic) {
        this.bankCardPic = bankCardPic;
    }

    public String getBankCardNo() {
        if (StringUtils.isEmpty(bankCardNo)) {
            return bankCardNo;
        }
        return bankName  + "(尾号" + bankCardNo.substring(bankCardNo.length() - 4, bankCardNo.length()) + ")";
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public Date getBizEndDate() {
        return bizEndDate;
    }

    public void setBizEndDate(Date bizEndDate) {
        this.bizEndDate = bizEndDate;
    }

    public Boolean getCompleteInfo() {
        return completeInfo;
    }

    public void setCompleteInfo(Boolean completeInfo) {
        this.completeInfo = completeInfo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getStoreInnerPic() {
        return storeInnerPic;
    }

    public void setStoreInnerPic(String storeInnerPic) {
        this.storeInnerPic = storeInnerPic;
    }

    public String getStoreInnerPicBase64() {
        return storeInnerPicBase64;
    }

    public void setStoreInnerPicBase64(String storeInnerPicBase64) {
        this.storeInnerPicBase64 = storeInnerPicBase64;
    }

    public Integer getBankCardType() {
        return bankCardType;
    }

    public void setBankCardType(Integer bankCardType) {
        this.bankCardType = bankCardType;
    }
}


