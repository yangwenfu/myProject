package com.xinyunlian.jinfu.dealer.dealer.dto.resp;

import java.io.Serializable;

/**
 * Created by menglei on 2017年08月03日.
 */
public class DealerUserSubscribeReportDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String createTime;

    private String userName;

    private String mobile;

    private String dealerName;

    private String belongName;

    private String openId;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getBelongName() {
        return belongName;
    }

    public void setBelongName(String belongName) {
        this.belongName = belongName;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
