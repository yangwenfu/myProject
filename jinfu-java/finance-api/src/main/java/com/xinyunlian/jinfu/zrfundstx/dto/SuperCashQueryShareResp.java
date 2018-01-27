package com.xinyunlian.jinfu.zrfundstx.dto;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by dongfangchao on 2016/11/29/0029.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Response")
public class SuperCashQueryShareResp extends BaseResp  {

    private static final long serialVersionUID = -3345591579124708110L;

    @XmlElement(name = "ApplicationNo")
    private String applicationNo;
    @XmlElement(name = "TotalRecord")
    private Long totalRecord;
    @XmlElementWrapper(name = "assetList")
    @XmlElement(name = "asset")
    private List<SuperCashQueryShareSubResp> list;

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

    public List<SuperCashQueryShareSubResp> getList() {
        return list;
    }

    public void setList(List<SuperCashQueryShareSubResp> list) {
        this.list = list;
    }
}
