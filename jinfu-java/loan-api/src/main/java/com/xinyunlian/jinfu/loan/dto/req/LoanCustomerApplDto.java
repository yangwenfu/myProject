package com.xinyunlian.jinfu.loan.dto.req;

import com.xinyunlian.jinfu.loan.enums.EApplChannel;
import com.xinyunlian.jinfu.product.dto.LoanProductDetailDto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 贷款申请DTO
 *
 * @author willwang
 */
public class LoanCustomerApplDto implements Serializable {

    /**
     * 贷款期限
     */
    private String termLen;

    /**
     * 申请金额
     */
    private BigDecimal applAmt;

    /**
     * 产品编号
     */
    private String productId;

    /**
     * 推荐人
     */
    private String recUser;

    /**
     * 贷款申请渠道来源
     */
    private EApplChannel channel;

    /**
     * 产品信息，由系统内部进行检测补全，不由外部传递
     */
    private LoanProductDetailDto product;

    /**
     * ABTest
     *
     * @return
     */
    private String testSource;

    /**
     * 利率
     */
    private BigDecimal feeRt;

    private BigDecimal dealerServiceFeeRt;

    public String getTestSource() {
        return testSource;
    }

    public void setTestSource(String testSource) {
        this.testSource = testSource;
    }

    public LoanProductDetailDto getProduct() {
        return product;
    }

    public void setProduct(LoanProductDetailDto product) {
        this.product = product;
    }

    public EApplChannel getChannel() {
        return channel;
    }

    public void setChannel(EApplChannel channel) {
        this.channel = channel;
    }

    public String getTermLen() {
        return termLen;
    }

    public void setTermLen(String termLen) {
        this.termLen = termLen;
    }

    public BigDecimal getApplAmt() {
        return applAmt;
    }

    public void setApplAmt(BigDecimal applAmt) {
        this.applAmt = applAmt;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getRecUser() {
        return recUser;
    }

    public void setRecUser(String recUser) {
        this.recUser = recUser;
    }

    public BigDecimal getFeeRt() {
        return feeRt;
    }

    public void setFeeRt(BigDecimal feeRt) {
        this.feeRt = feeRt;
    }

    public BigDecimal getDealerServiceFeeRt() {
        return dealerServiceFeeRt;
    }

    public void setDealerServiceFeeRt(BigDecimal dealerServiceFeeRt) {
        this.dealerServiceFeeRt = dealerServiceFeeRt;
    }
}
