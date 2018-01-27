package com.xinyunlian.jinfu.report.dealer.entity;

import com.xinyunlian.jinfu.report.dealer.enums.EDealerUserLogType;
import com.xinyunlian.jinfu.report.dealer.enums.converter.EDealerUserLogTypeEnumConverter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by bright on 2016/11/29.
 */
@Entity
@Table(name = "dealer_log")
public class DealerLogPo {
    @Id
    @Column(name = "log_id")
    private Long logId;

    @Column(name = "dealer_user_name")
    private String dealerUserName;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "dealer_name")
    private String dealerName;

    @Column(name = "store_name")
    private String storeName;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "create_ts")
    private Date createTs;

    @Column(name = "type")
    @Convert(converter = EDealerUserLogTypeEnumConverter.class)
    private EDealerUserLogType type;

    @Column(name = "remark")
    private String remark;

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public String getDealerUserName() {
        return dealerUserName;
    }

    public void setDealerUserName(String dealerUserName) {
        this.dealerUserName = dealerUserName;
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

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }

    public EDealerUserLogType getType() {
        return type;
    }

    public void setType(EDealerUserLogType type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
