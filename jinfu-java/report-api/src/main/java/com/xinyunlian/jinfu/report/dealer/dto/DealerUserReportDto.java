package com.xinyunlian.jinfu.report.dealer.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by bright on 2016/11/30.
 */
public class DealerUserReportDto implements Serializable{
    private String userName;

    private String mobile;

    private String dealerName;

    private String administrator;

    private Date createTs;

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

    public Date getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }

    public String getAdministrator() {
        return administrator;
    }

    public void setAdministrator(String administrator) {
        this.administrator = administrator;
    }
}
