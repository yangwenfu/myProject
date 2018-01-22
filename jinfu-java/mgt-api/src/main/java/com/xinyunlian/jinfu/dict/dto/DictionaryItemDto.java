package com.xinyunlian.jinfu.dict.dto;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/1/22/0022.
 */
public class DictionaryItemDto implements Serializable {
    private static final long serialVersionUID = 2630560476973678743L;

    private Long id;

    private Long typeId;

    private String name;

    private String code;

    private String text;

    private Integer sort;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
