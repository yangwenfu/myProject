package com.xinyunlian.jinfu.yunma.dto;

import com.xinyunlian.jinfu.common.dto.PagingDto;
import com.xinyunlian.jinfu.yunma.enums.EMemberStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 云码商铺Entity
 *
 * @author jll
 */

public class YMMemberSearchDto extends PagingDto<YMMemberSearchDto> {
    private Long id;
    private Long storeId;
    private String mobile;
    private String userName;
    private Long areaId;
    private Long provinceId;
    private Long cityId;
    private String province;
    private String city;
    private String area;
    private String storeName;
    private String userId;
    private String memberNo;
    private String qrcodeNo;
    private String qrcodeUrl;
    private String openId;
    private Date bindTime;
    private String startBindTime;
    private String endBindTime;
    private EMemberStatus memberStatus;
    private String dealerUserId;
    private String dealerUserName;
    private String dealerId;
    private Set<Long> storeIds;
    private List<Long> ids = new ArrayList<>();
    private String createOpId;
    private List<String> createOpIds = new ArrayList<>();
    private String lastId;//最大id,下拉加载用

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(String memberNo) {
        this.memberNo = memberNo;
    }

    public String getQrcodeNo() {
        return qrcodeNo;
    }

    public void setQrcodeNo(String qrcodeNo) {
        this.qrcodeNo = qrcodeNo;
    }

    public String getQrcodeUrl() {
        return qrcodeUrl;
    }

    public void setQrcodeUrl(String qrcodeUrl) {
        this.qrcodeUrl = qrcodeUrl;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Date getBindTime() {
        return bindTime;
    }

    public void setBindTime(Date bindTime) {
        this.bindTime = bindTime;
    }

    public String getStartBindTime() {
        return startBindTime;
    }

    public void setStartBindTime(String startBindTime) {
        this.startBindTime = startBindTime;
    }

    public String getEndBindTime() {
        return endBindTime;
    }

    public void setEndBindTime(String endBindTime) {
        this.endBindTime = endBindTime;
    }

    public EMemberStatus getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(EMemberStatus memberStatus) {
        this.memberStatus = memberStatus;
    }

    public Set<Long> getStoreIds() {
        return storeIds;
    }

    public void setStoreIds(Set<Long> storeIds) {
        this.storeIds = storeIds;
    }

    public String getCreateOpId() {
        return createOpId;
    }

    public void setCreateOpId(String createOpId) {
        this.createOpId = createOpId;
    }

    public String getLastId() {
        return lastId;
    }

    public void setLastId(String lastId) {
        this.lastId = lastId;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public List<String> getCreateOpIds() {
        return createOpIds;
    }

    public void setCreateOpIds(List<String> createOpIds) {
        this.createOpIds = createOpIds;
    }

    public String getDealerUserId() {
        return dealerUserId;
    }

    public void setDealerUserId(String dealerUserId) {
        this.dealerUserId = dealerUserId;
    }

    public String getDealerUserName() {
        return dealerUserName;
    }

    public void setDealerUserName(String dealerUserName) {
        this.dealerUserName = dealerUserName;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }
}


