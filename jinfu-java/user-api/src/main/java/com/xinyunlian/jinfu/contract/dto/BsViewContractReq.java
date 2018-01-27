package com.xinyunlian.jinfu.contract.dto;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/2/8/0008.
 */
public class BsViewContractReq implements Serializable {
    private static final long serialVersionUID = 5214212373348090587L;

    private String fsdid;

    private String docid;

    public String getFsdid() {
        return fsdid;
    }

    public void setFsdid(String fsdid) {
        this.fsdid = fsdid;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }
}
