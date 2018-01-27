package com.xinyunlian.jinfu.carbank.dto;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/7/18/0018.
 */
public class VehicleModelDto implements Serializable {
    private static final long serialVersionUID = 2199195396560722707L;

    private Integer id;

    private String name;

    private Integer seriesId;

    private String shortName;

    private String year;

    private String maxRegisteYear;

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
