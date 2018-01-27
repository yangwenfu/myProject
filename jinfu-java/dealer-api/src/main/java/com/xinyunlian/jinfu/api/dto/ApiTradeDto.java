package com.xinyunlian.jinfu.api.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by menglei on 2016年09月23日.
 */
public class ApiTradeDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String error;

    private String msg;

    private Long total;

    private Integer pagetotal;

    private List<Trade> data;

    public class Trade implements Serializable {

        private String id;

        private String memberNo;

        private String bizCode;

        private String time;

        private String transTime;

        private String transAmt;

        private String partnerTranxSN;

        private String state;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMemberNo() {
            return memberNo;
        }

        public void setMemberNo(String memberNo) {
            this.memberNo = memberNo;
        }

        public String getBizCode() {
            return bizCode;
        }

        public void setBizCode(String bizCode) {
            this.bizCode = bizCode;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getTransTime() {
            return transTime;
        }

        public void setTransTime(String transTime) {
            this.transTime = transTime;
        }

        public String getTransAmt() {
            return transAmt;
        }

        public void setTransAmt(String transAmt) {
            this.transAmt = transAmt;
        }

        public String getPartnerTranxSN() {
            return partnerTranxSN;
        }

        public void setPartnerTranxSN(String partnerTranxSN) {
            this.partnerTranxSN = partnerTranxSN;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
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

    public List<Trade> getData() {
        return data;
    }

    public void setData(List<Trade> data) {
        this.data = data;
    }
}
