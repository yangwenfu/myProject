package com.xinyunlian.jinfu.pingan.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/6/13/0013.
 */
public class QunarApplyMoreInfoReq implements Serializable {
    private static final long serialVersionUID = 1225817625968140100L;

    @JsonProperty("noticeDetail")
    private QunarApplyMoreInfoNoticeDetailReq noticeDetail;

    public QunarApplyMoreInfoNoticeDetailReq getNoticeDetail() {
        return noticeDetail;
    }

    public void setNoticeDetail(QunarApplyMoreInfoNoticeDetailReq noticeDetail) {
        this.noticeDetail = noticeDetail;
    }
}
