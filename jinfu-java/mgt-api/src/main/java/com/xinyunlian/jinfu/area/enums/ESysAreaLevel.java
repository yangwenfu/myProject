package com.xinyunlian.jinfu.area.enums;

/**
 * Created by DongFC on 2016-09-20.
 */
public enum ESysAreaLevel {

    PROVINCE(1), CITY(2), COUNTY(3), STREET(4);

    private Integer level;

    ESysAreaLevel() {
    }

    ESysAreaLevel(Integer level) {
        this.level = level;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
