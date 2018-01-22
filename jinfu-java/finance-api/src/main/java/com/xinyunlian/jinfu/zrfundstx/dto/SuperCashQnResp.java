package com.xinyunlian.jinfu.zrfundstx.dto;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by dongfangchao on 2016/11/30/0030.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Response")
public class SuperCashQnResp extends BaseResp {

    private static final long serialVersionUID = -7361408990012619348L;

    @XmlElement(name = "ApplicationNo")
    private String applicationNo;

    @XmlElement(name = "TotalRecord")
    private Long totalRecord;

    @XmlElementWrapper(name = "assetList")
    @XmlElement(name = "asset")
    private List<SuperCashQnSubResp> list;

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

    public List<SuperCashQnSubResp> getList() {
        return list;
    }

    public void setList(List<SuperCashQnSubResp> list) {
        this.list = list;
    }
}
