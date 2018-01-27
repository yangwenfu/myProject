package com.xinyunlian.jinfu.carbank.api.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/7/18/0018.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VehicleModelCntResponse implements Serializable {
    private static final long serialVersionUID = 2199195396560722707L;

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("seriesId")
    private Integer seriesId;

    @JsonProperty("shortName")
    private String shortName;

    @JsonProperty("year")
    private String year;

    @JsonProperty("maxRegisteYear")
    private String maxRegisteYear;

    @JsonProperty("minRegisteYear")
    private String minRegisteYear;

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

    public Integer getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(Integer seriesId) {
        this.seriesId = seriesId;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMaxRegisteYear() {
        return maxRegisteYear;
    }

    public void setMaxRegisteYear(String maxRegisteYear) {
        this.maxRegisteYear = maxRegisteYear;
    }

    public String getMinRegisteYear() {
        return minRegisteYear;
    }

    public void setMinRegisteYear(String minRegisteYear) {
        this.minRegisteYear = minRegisteYear;
    }
}
