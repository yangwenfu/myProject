package com.xinyunlian.jinfu.paycode.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.payCode.enums.PayCodeBalanceType;
import com.xinyunlian.jinfu.paycode.enums.converters.PayCodeBalanceTypeConverter;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by carrot on 2017/8/28.
 */
@Entity
@Table(name = "pay_code_balance_log")
public class PayCodeBalanceLogPo extends BaseMaintainablePo {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 支付码编码
     **/
    @Column(name = "PAY_CODE_NO")
    private String payCodeNo;

    /**
     * 手机号
     **/
    @Column(name = "MOBILE")
    private String mobile;

    /**
     * 操作类型
     */
    @Column(name = "TYPE")
    @Convert(converter = PayCodeBalanceTypeConverter.class)
    private PayCodeBalanceType type;

    /**
     * 操作金额
     */
    @Column(name = "AMOUNT")
    private BigDecimal amount;

    /**
     * 流水号
     */
    @Column(name = "SERIAL_NUMBER")
    private String serialNumber;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public PayCodeBalanceType getType() {
        return type;
    }

    public void setType(PayCodeBalanceType type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}
