package com.xinyunlian.jinfu.loan.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by bright on 2017/5/26.
 */
public class UserRiskDto implements Serializable{
    private static final long serialVersionUID = 8890085373177177584L;
    @JsonProperty("name")
    private String name;

    @JsonProperty("id_no")
    private String idNo;

    @JsonProperty("risk_type")
    private String riskType;

    public UserRiskDto() {
    }

    public UserRiskDto(String name, String idNo, String riskType) {
        this.name = name;
        this.idNo = idNo;
        this.riskType = riskType;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getRiskType() {
        return riskType;
    }

    public void setRiskType(String riskType) {
        this.riskType = riskType;
    }
}
