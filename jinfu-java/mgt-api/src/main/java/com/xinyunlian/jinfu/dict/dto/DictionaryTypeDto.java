package com.xinyunlian.jinfu.dict.dto;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/1/22/0022.
 */
public class DictionaryTypeDto implements Serializable {
    private static final long serialVersionUID = 848512586954043686L;

    private Long id;

    private String code;

    private String text;

    private Boolean status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
