package com.xinyunlian.jinfu.router.dto;

import com.xinyunlian.jinfu.router.enums.EFinanceSourceType;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/16 0016.
 */
public class FinanceSourceDto implements Serializable{

    private Integer id;

    private EFinanceSourceType type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EFinanceSourceType getType() {
        return type;
    }

    public void setType(EFinanceSourceType type) {
        this.type = type;
    }

}
