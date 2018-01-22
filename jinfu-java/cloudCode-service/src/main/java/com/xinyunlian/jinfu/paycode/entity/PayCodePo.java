package com.xinyunlian.jinfu.paycode.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.common.entity.id.IdInjectionEntityListener;
import com.xinyunlian.jinfu.order.enums.converters.ECmccOrderPayStatusConverter;
import com.xinyunlian.jinfu.payCode.enums.PayCodeStatus;
import com.xinyunlian.jinfu.paycode.enums.converters.PayCodeStatusConverter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by carrot on 2017/8/28.
 */
@Entity
@Table(name = "pay_code")
public class PayCodePo extends BaseMaintainablePo {

    private static final long serialVersionUID = 1L;

    /**
     * 支付码编码
     **/
    @Id
    @Column(name = "PAY_CODE_NO")
    private String payCodeNo;

    /**
     * 手机号
     **/
    @Column(name = "MOBILE")
    private String mobile;

    /**
     * 余额
     **/
    @Column(name = "BALANCE")
    private BigDecimal balance;

    /**
     * 支付码URL
     */
    @Column(name = "PAY_CODE_URL")
    private String payCodeUrl;

    /**
     * 支付码状态
     */
    @Column(name = "STATUS")
    @Convert(converter = PayCodeStatusConverter.class)
    private PayCodeStatus status;

    public String getPayCodeNo() {
        return payCodeNo;
    }

    public void setPayCodeNo(String payCodeNo) {
        this.payCodeNo = payCodeNo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public PayCodeStatus getStatus() {
        return status;
    }

    public void setStatus(PayCodeStatus status) {
        this.status = status;
    }

    public String getPayCodeUrl() {
        return payCodeUrl;
    }

    public void setPayCodeUrl(String payCodeUrl) {
        this.payCodeUrl = payCodeUrl;
    }
}
