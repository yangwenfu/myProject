package com.xinyunlian.jinfu.yunma.dto;

import com.xinyunlian.jinfu.trade.enums.EBizCode;
import com.xinyunlian.jinfu.trade.enums.ETradeStatus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 云码商铺Entity
 *
 * @author jll
 */

public class YMTradeReportDto implements Serializable {
    private static final long serialVersionUID = 1L;
    //创建时间
    private Date createTs;
    //业务编码
    private EBizCode bizCode;
    //消费金额
    private BigDecimal transAmt;
    //消费手续费
    private BigDecimal transFee;
    //通道流水
    private String outTradeNo;
    //0未支付，1支付成功,2支付失败
    private ETradeStatus status;

    public Date getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }

    public EBizCode getBizCode() {
        return bizCode;
    }

    public void setBizCode(EBizCode bizCode) {
        this.bizCode = bizCode;
    }

    public BigDecimal getTransAmt() {
        return transAmt;
    }

    public void setTransAmt(BigDecimal transAmt) {
        this.transAmt = transAmt;
    }

    public BigDecimal getTransFee() {
        return transFee;
    }

    public void setTransFee(BigDecimal transFee) {
        this.transFee = transFee;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public ETradeStatus getStatus() {
        return status;
    }

    public void setStatus(ETradeStatus status) {
        this.status = status;
    }
}


