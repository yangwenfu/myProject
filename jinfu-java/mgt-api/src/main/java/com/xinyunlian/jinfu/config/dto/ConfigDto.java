package com.xinyunlian.jinfu.config.dto;

import com.xinyunlian.jinfu.config.enums.ECategory;

import java.io.Serializable;

/**
 * Created by bright on 2017/1/6.
 */
public class ConfigDto implements Serializable {
    private Long id;
    private ECategory category;
    private String key;
    private String value;
    private Boolean cached;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ECategory getCategory() {
        return category;
    }

    public void setCategory(ECategory category) {
        this.category = category;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getCached() {
        return cached;
    }

    public void setCached(Boolean cached) {
        this.cached = cached;
    }
}
