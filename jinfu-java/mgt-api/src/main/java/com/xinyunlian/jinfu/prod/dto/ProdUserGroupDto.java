package com.xinyunlian.jinfu.prod.dto;

import com.xinyunlian.jinfu.prod.enums.EUserGroup;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/1/22/0022.
 */
public class ProdUserGroupDto implements Serializable{
    private static final long serialVersionUID = -7262986269683691077L;

    private Long id;

    private String prodId;

    private EUserGroup userGroup;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public EUserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(EUserGroup userGroup) {
        this.userGroup = userGroup;
    }
}
