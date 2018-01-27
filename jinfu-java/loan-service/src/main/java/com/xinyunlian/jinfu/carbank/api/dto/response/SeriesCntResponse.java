package com.xinyunlian.jinfu.carbank.api.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @author willwang
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SeriesCntResponse implements Serializable{

    @JsonProperty("brandId")
    private String brandId;

    @JsonProperty("groupName")
    private String groupName;

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
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
