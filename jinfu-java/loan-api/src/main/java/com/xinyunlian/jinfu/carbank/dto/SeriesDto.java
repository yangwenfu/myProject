package com.xinyunlian.jinfu.carbank.dto;

import java.io.Serializable;

/**
 * @author willwang
 */
public class SeriesDto implements Serializable{

    private String brandId;

    private String groupName;

    private Integer id;

    private String name;

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
