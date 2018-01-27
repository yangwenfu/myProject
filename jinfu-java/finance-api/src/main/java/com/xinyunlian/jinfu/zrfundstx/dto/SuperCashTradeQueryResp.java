package com.xinyunlian.jinfu.zrfundstx.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

/**
 * Created by dongfangchao on 2017/1/3/0003.
 */
public class SuperCashTradeQueryResp extends BaseResp {
    private static final long serialVersionUID = -2499246346639281757L;

    @XmlElement(name = "ApplicationNo")
    private String applicationNo;

    @XmlElement(name = "TotalRecord")
    private Long totalRecord;

    @XmlElementWrapper(name = "assetList")
    @XmlElement(name = "asset")
    private List<SuperCashTradeQuerySubResp> list;

    public String getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
    }

    public Long getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(Long totalRecord) {
        this.totalRecord = totalRecord;
    }

    public List<SuperCashTradeQuerySubResp> getList() {
        return list;
    }

    public void setList(List<SuperCashTradeQuerySubResp> list) {
        this.list = list;
    }
}
