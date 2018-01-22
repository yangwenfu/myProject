package com.xinyunlian.jinfu.domain.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xinyunlian.jinfu.common.entity.id.IdUtil;
import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.domain.CommandRequest;
import com.xinyunlian.jinfu.domain.CommandResponse;
import com.xinyunlian.jinfu.domain.SignatureIgnore;
import com.xinyunlian.jinfu.domain.resp.NYRealAuthResponse;
import com.xinyunlian.jinfu.enums.ETrxType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created by dell on 2016/11/2.
 */
public class NYRealAuthRequest extends CommandRequest<NYRealAuthResponse> {
    @SignatureIgnore
    public static final String AUTH_WITH_3ELS = "11";
    @SignatureIgnore
    public static final String AUTH_WITH_4ELS = "10";

    @SignatureIgnore
    private static final Logger LOGGER = LoggerFactory.getLogger(NYRealAuthRequest.class);

    @JsonProperty("version")
    private String version;

    @JsonProperty("merId")
    private String merId;

    @JsonProperty("transDate")
    private String transDate;

    @JsonProperty("transType")
    private String transType;

    @JsonProperty("seqId")
    private String seqId;

    @JsonProperty("transTime")
    private String transTime;

    @JsonProperty("cardNo")
    private String cardNo;

    @JsonProperty("name")
    private String name;

    @JsonProperty("certNo")
    private String certNo;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("sign")
    @SignatureIgnore
    private String sign;

    @JsonIgnore
    @SignatureIgnore
    private String executeUrl;

    public NYRealAuthRequest() {
        version = "20160819";
        transDate = DateHelper.formatDate(new Date(), DateHelper.SIMPLE_DATE_YMD);
        transType = "10";
        seqId = IdUtil.produce();
        transTime = DateHelper.formatDate(new Date(), "HHmmss");
        merId = AppConfigUtil.getConfig("ny.merid");
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId;
    }

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getSeqId() {
        return seqId;
    }

    public void setSeqId(String seqId) {
        this.seqId = seqId;
    }

    public String getTransTime() {
        return transTime;
    }

    public void setTransTime(String transTime) {
        this.transTime = transTime;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getExecuteUrl() {
        return executeUrl;
    }

    public void setExecuteUrl(String executeUrl) {
        this.executeUrl = executeUrl;
    }

    @Override
    public NYRealAuthResponse execute() {
        try {
            return super.execute();
        } catch (Exception e){
            LOGGER.error("execute real_auth failed", e);
            return NYRealAuthResponse.ofError(e.getMessage());
        }
    }

    @Override
    @JsonIgnore
    public ETrxType getTrxType() {
        return ETrxType.REAL_AUTH_NY;
    }
}
