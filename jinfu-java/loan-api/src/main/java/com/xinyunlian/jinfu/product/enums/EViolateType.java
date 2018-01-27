package com.xinyunlian.jinfu.product.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

import java.math.BigDecimal;

/**
 * Created by JL on 2016/9/1.
 */
public enum EViolateType implements PageEnum{

    ALL("ALL", "全部") {
        @Override
        public BigDecimal get(BigDecimal base, BigDecimal fineValue) {
            throw new UnsupportedOperationException();
        }
    },
    NONE("01", "无违约金") {
        @Override
        public BigDecimal get(BigDecimal base, BigDecimal fineValue) {
            return BigDecimal.ZERO;
        }
    },
    CAPITAL("02", "所有本金的") {
        @Override
        public BigDecimal get(BigDecimal base, BigDecimal fineValue) {
            return base.multiply(fineValue);
        }
    },
    REMAINDER("03", "剩余本金的") {
        @Override
        public BigDecimal get(BigDecimal base, BigDecimal fineValue) {
            return base.multiply(fineValue);
        }
    },
    FIXED("04", "固定本金") {
        @Override
        public BigDecimal get(BigDecimal base, BigDecimal fineValue) {
            return fineValue;
        }
    },
    ONE_MONTH_INTEREST("05", "加收一个月利息") {
        @Override
        public BigDecimal get(BigDecimal base, BigDecimal fineValue) {
            return fineValue;
        }
    };

    private String code;

    private String text;

    EViolateType(String code, String text) {
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

    public abstract BigDecimal get(BigDecimal base, BigDecimal fineValue);
}
