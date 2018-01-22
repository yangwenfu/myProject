package com.xinyunlian.jinfu.carbank.api.dto.request;

import com.xinyunlian.jinfu.carbank.api.dto.response.WishOrderResponse;

/**
 * Created by dongfangchao on 2017/7/18/0018.
 */
public class WishOrderRequest extends CarBankTemplate<WishOrderResponse> {

    private static final String url = "/resource/fcar/partner/createWishOrder";

    private Integer cityId;

    private String mobile;

    private Integer vehicleModelId;

    private String registerDate;

    private String loanAmount;

    private String term;

    private String invitationCode;

    private String dealerName;

    public static String getUrl() {
        return url;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getVehicleModelId() {
        return vehicleModelId;
    }

    public void setVehicleModelId(Integer vehicleModelId) {
        this.vehicleModelId = vehicleModelId;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    @Override
    public String getRequestUrl() {
        return url;
    }
}
