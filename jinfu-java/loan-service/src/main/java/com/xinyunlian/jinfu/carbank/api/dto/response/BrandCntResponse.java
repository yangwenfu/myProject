package com.xinyunlian.jinfu.carbank.api.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/16 0016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BrandCntResponse implements Serializable {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("initial")
    private String initial;

    @JsonProperty("name")
    private String name;

    @JsonProperty("isAllowLoan")
    private Integer isAllowLoan;

    public Integer getIsAllowLoan() {
        return isAllowLoan;
    }

    public void setIsAllowLoan(Integer isAllowLoan) {
        this.isAllowLoan = isAllowLoan;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "BrandCntResponse{" +
                "id=" + id +
                ", initial='" + initial + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
