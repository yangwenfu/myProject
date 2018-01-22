package com.xinyunlian.jinfu.loan.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;
import com.xinyunlian.jinfu.product.enums.EIntrRateType;

import java.math.BigDecimal;

public enum ERepayMode implements PageEnum {

    ONCE_PAY("01", "一次性还本付息", "还本付息") {
        @Override
        public BigDecimal getRate(EIntrRateType rateType, BigDecimal interest) {
            return rateType.getDay(interest);
        }

        @Override
        public EIntrRateType getRateType() {
            throw new UnsupportedOperationException();
        }
    },

    INTR_PER_DIEM("02", "随借随还，按日计息", "随借随还") {
        @Override
        public BigDecimal getRate(EIntrRateType rateType, BigDecimal interest) {
            return rateType.getDay(interest);
        }

        @Override
        public EIntrRateType getRateType() {
            return EIntrRateType.DAY;
        }
    },

    INTR_PER_MENSEM("03", "按月付息，到期还本", "按月付息") {
        @Override
        public BigDecimal getRate(EIntrRateType rateType, BigDecimal interest) {
            throw new UnsupportedOperationException();
        }

        @Override
        public EIntrRateType getRateType() {
            throw new UnsupportedOperationException();
        }
    },

    MONTH_AVE_CAP("04", "按月等额本金", "按月等额本金") {
        @Override
        public BigDecimal getRate(EIntrRateType rateType, BigDecimal interest) {
            throw new UnsupportedOperationException();
        }

        @Override
        public EIntrRateType getRateType() {
            throw new UnsupportedOperationException();
        }
    },

    MONTH_AVE_CAP_PLUS_INTR("05", "按月等额本息", "等额本息") {
        @Override
        public BigDecimal getRate(EIntrRateType rateType, BigDecimal interest) {
            return rateType.getMonth(interest);
        }

        @Override
        public EIntrRateType getRateType() {
            return EIntrRateType.MONTH;
        }
    },

    QUARTER_AVE_CAP("06", "按季等额本金", "按季等额本金") {
        @Override
        public BigDecimal getRate(EIntrRateType rateType, BigDecimal interest) {
            throw new UnsupportedOperationException();
        }

        @Override
        public EIntrRateType getRateType() {
            throw new UnsupportedOperationException();
        }
    },

    QUARTER_AVE_CAP_PLUS_INTR("07", "按季等额本息", "按季等额本息") {
        @Override
        public BigDecimal getRate(EIntrRateType rateType, BigDecimal interest) {
            throw new UnsupportedOperationException();
        }

        @Override
        public EIntrRateType getRateType() {
            throw new UnsupportedOperationException();
        }
    },

    MONTH_AVE_CAP_AVG_INTR("08", "按月等本等息", "等本等息") {
        @Override
        public BigDecimal getRate(EIntrRateType rateType, BigDecimal interest) {
            return rateType.getMonth(interest);
        }

        @Override
        public EIntrRateType getRateType() {
            return EIntrRateType.MONTH;
        }
    },

    ALL("ALL", "全部", "全部") {
        @Override
        public BigDecimal getRate(EIntrRateType rateType, BigDecimal interest) {
            throw new UnsupportedOperationException();
        }

        @Override
        public EIntrRateType getRateType() {
            throw new UnsupportedOperationException();
        }
    };

    private String code;

    private String text;

    private String alias;

    ERepayMode(String code, String text, String alias) {
        this.code = code;
        this.text = text;
        this.alias = alias;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * @param interest 利率
     * @return
     */
    public abstract BigDecimal getRate(EIntrRateType rateType, BigDecimal interest);

    public abstract EIntrRateType getRateType();
}
