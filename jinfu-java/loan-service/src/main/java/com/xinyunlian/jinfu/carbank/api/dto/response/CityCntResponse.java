package com.xinyunlian.jinfu.carbank.api.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/16 0016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CityCntResponse implements Serializable {

    @JsonProperty("enName")
    private String enName;

    @JsonProperty("firstEnName")
    private String firstEnName;

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("isHot")
    private Integer isHot;

    @JsonProperty("name")
    private String name;

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getFirstEnName() {
        return firstEnName;
    }

    public void setFirstEnName(String firstEnName) {
        this.firstEnName = firstEnName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIsHot() {
        return isHot;
    }

    public void setIsHot(Integer isHot) {
        this.isHot = isHot;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CityCntResponse{" +
                "enName='" + enName + '\'' +
                ", firstEnName='" + firstEnName + '\'' +
                ", id=" + id +
                ", isHot=" + isHot +
                ", name='" + name + '\'' +
                '}';
    }
}
