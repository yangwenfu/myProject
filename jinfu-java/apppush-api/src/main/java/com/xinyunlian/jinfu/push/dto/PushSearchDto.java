package com.xinyunlian.jinfu.push.dto;

import com.xinyunlian.jinfu.common.dto.PagingDto;

/**
 * Created by apple on 2017/1/5.
 */
public class PushSearchDto extends PagingDto<PushSearchDto> {

    private static final long serialVersionUID = 1501192083269135956L;

    private String lastId;

    private String pushStates;

    private String pushObject;

    private String keyword;

    private String provinceId;

    private String beginTime;

    private String endTime;

    private String cityId;

    private String areaId;

    private String platform;

    private String type;

    public String getLastId() {
        return lastId;
    }

    public void setLastId(String lastId) {
        this.lastId = lastId;
    }

    public String getPushStates() {
        return pushStates;
    }

    public void setPushStates(String pushStates) {
        this.pushStates = pushStates;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getPushObject() {
        return pushObject;
    }

    public void setPushObject(String pushObject) {
        this.pushObject = pushObject;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
