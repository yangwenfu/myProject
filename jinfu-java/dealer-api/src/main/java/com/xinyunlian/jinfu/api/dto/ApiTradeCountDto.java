package com.xinyunlian.jinfu.api.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by menglei on 2016年09月23日.
 */
public class ApiTradeCountDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String error;

    private String msg;

    private Long total;

    private Integer pagetotal;

    private List<TradeCount> tradeList;

    public class TradeCount implements Serializable {

        private String date;

        private List<Trade> list;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public List<Trade> getList() {
            return list;
        }

        public void setList(List<Trade> list) {
            this.list = list;
        }

        public class Trade implements Serializable {

            private String bizCode;

            private String date;

            private String transAmt;

            private String count;

            public String getBizCode() {
                return bizCode;
            }

            public void setBizCode(String bizCode) {
                this.bizCode = bizCode;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getTransAmt() {
                return transAmt;
            }

            public void setTransAmt(String transAmt) {
                this.transAmt = transAmt;
            }

            public String getCount() {
                return count;
            }

            public void setCount(String count) {
                this.count = count;
            }
        }
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<TradeCount> getTradeList() {
        return tradeList;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getPagetotal() {
        return pagetotal;
    }

    public void setPagetotal(Integer pagetotal) {
        this.pagetotal = pagetotal;
    }

    public void setTradeList(List<TradeCount> tradeList) {
        this.tradeList = tradeList;
    }
}
