package com.xinyunlian.jinfu.carbank.dto;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/7/18/0018.
 */
public class OrderOverDueDto implements Serializable {
    private static final long serialVersionUID = 9022671595078593665L;

    private Boolean status;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
