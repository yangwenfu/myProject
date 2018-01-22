package com.xinyunlian.jinfu.user.dto.req;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by KimLL on 2016/8/18.
 */

public class IDCardDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userId;

    private String userName;

    private String idCardNo;

    private Date idExpiredBegin;

    private Date idExpiredEnd;

    private String idAuthority;

    private Date birthDate;

    private String sex;

    private String nation;

    private String nationAddress;

    private String cardFrontFilePath;

    private String cardBackFilePath;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getCardFrontFilePath() {
        return cardFrontFilePath;
    }

    public void setCardFrontFilePath(String cardFrontFilePath) {
        this.cardFrontFilePath = cardFrontFilePath;
    }

    public String getCardBackFilePath() {
        return cardBackFilePath;
    }

    public void setCardBackFilePath(String cardBackFilePath) {
        this.cardBackFilePath = cardBackFilePath;
    }

    public Date getIdExpiredBegin() {
        return idExpiredBegin;
    }

    public void setIdExpiredBegin(Date idExpiredBegin) {
        this.idExpiredBegin = idExpiredBegin;
    }

    public Date getIdExpiredEnd() {
        return idExpiredEnd;
    }

    public void setIdExpiredEnd(Date idExpiredEnd) {
        this.idExpiredEnd = idExpiredEnd;
    }

    public String getIdAuthority() {
        return idAuthority;
    }

    public void setIdAuthority(String idAuthority) {
        this.idAuthority = idAuthority;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getNationAddress() {
        return nationAddress;
    }

    public void setNationAddress(String nationAddress) {
        this.nationAddress = nationAddress;
    }
}
