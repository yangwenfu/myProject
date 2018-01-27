package com.xinyunlian.jinfu.product.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

import java.math.BigDecimal;

/**
 * @author willwang
 */
public enum EFineType implements PageEnum{

    CAPITAL_EVERY_DAY("1", "逾期本金数单日计罚息") {
        @Override
        public BigDecimal getFine(BigDecimal surplus, int days, BigDecimal fineValue) {
            BigDecimal capital = BigDecimal.ZERO;
            if(surplus.compareTo(BigDecimal.ZERO) >= 0){
                capital = surplus;
            }

            return capital.multiply(fineValue).multiply(BigDecimal.valueOf(days));
        }
    };

    private String code;

    private String text;

    EFineType(String code, String text) {
        this.code = code;
        this.text = text;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    /**
     获取罚息金额
     * @param surplus 剩余本金
     * @param days 逾期天数
     * @param fineValue 逾期罚金设定值（有可能是百分比，也有可能是金额）
     * @return
     */
    public abstract BigDecimal getFine(BigDecimal surplus, int days, BigDecimal fineValue);
}
