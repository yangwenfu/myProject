package com.xinyunlian.jinfu.product.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;
import org.apache.commons.lang.time.DateUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by JL on 2016/9/1.
 */
public enum EIntrRateType implements PageEnum {

    ALL("ALL", "全部"){
        public BigDecimal getDay(BigDecimal interest){
            throw new UnsupportedOperationException();
        }
        @Override
        public BigDecimal getMonth(BigDecimal interest) {
            throw new UnsupportedOperationException();
        }

        @Override
        public BigDecimal getYear(BigDecimal interest) {
            throw new UnsupportedOperationException();
        }
    },
    DAY("DAY", "日利率"){
        public BigDecimal getDay(BigDecimal interest){
            return interest;
        }

        @Override
        public BigDecimal getMonth(BigDecimal interest) {
            return interest.multiply(new BigDecimal(30));
        }

        @Override
        public BigDecimal getYear(BigDecimal interest) {
            return interest.multiply(new BigDecimal(360));
        }
    },
    MONTH("MONTH", "月利率"){
        public BigDecimal getDay(BigDecimal interest){
            return interest.divide(new BigDecimal(30), BigDecimal.ROUND_HALF_DOWN);
        }

        @Override
        public BigDecimal getMonth(BigDecimal interest) {
            return interest;
        }

        @Override
        public BigDecimal getYear(BigDecimal interest) {
            return interest.multiply(new BigDecimal(12));
        }
    },
    YEAR("YEAR", "年利率"){
        public BigDecimal getDay(BigDecimal interest){
            return interest.divide(new BigDecimal(360), BigDecimal.ROUND_HALF_DOWN);
        }

        @Override
        public BigDecimal getMonth(BigDecimal interest) {
            return interest.divide(new BigDecimal(12), BigDecimal.ROUND_HALF_DOWN);
        }

        @Override
        public BigDecimal getYear(BigDecimal interest) {
            return interest;
        }
    },
    NO_INTR("NO_INTR", "不计息"){
        public BigDecimal getDay(BigDecimal interest){
            return BigDecimal.ZERO;
        }

        @Override
        public BigDecimal getMonth(BigDecimal interest) {
            return BigDecimal.ZERO;
        }

        @Override
        public BigDecimal getYear(BigDecimal interest) {
            return BigDecimal.ZERO;
        }
    };

    private String code;

    private String text;

    EIntrRateType(String code, String text) {
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

    public abstract BigDecimal getDay(BigDecimal interest);
    public abstract BigDecimal getMonth(BigDecimal interest);
    public abstract BigDecimal getYear(BigDecimal interest);

}
