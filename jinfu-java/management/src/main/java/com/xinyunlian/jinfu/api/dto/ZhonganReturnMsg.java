package com.xinyunlian.jinfu.api.dto;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2016/12/5/0005.
 */
public class ZhonganReturnMsg implements Serializable{

    private static final long serialVersionUID = -3458435355720887031L;

    private String success;

    private String message;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
