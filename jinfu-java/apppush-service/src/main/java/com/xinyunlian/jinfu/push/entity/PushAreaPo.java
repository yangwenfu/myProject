package com.xinyunlian.jinfu.push.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;

import javax.persistence.*;

/**
 * Created by apple on 2017/1/10.
 */
@Entity
@Table(name = "PUSH_AREA")
public class PushAreaPo extends BaseMaintainablePo {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "MESSAGE_ID")
    private Long messageId;

    @Column(name = "PROVINCE_ID")
    private String provinceId;

    @Column(name = "PROVINCE")
    private String province;

    @Column(name = "CITY_ID")
    private String cityId;

    @Column(name = "CITY")
    private String city;

    @Column(name = "AREA_ID")
    private String areaId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "MESSAGE_ID", insertable = false, updatable = false)
    private PushMessagePo pushMessagePo;

    @Column(name = "AREA")
    private String area;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public PushMessagePo getPushMessagePo() {
        return pushMessagePo;
    }

    public void setPushMessagePo(PushMessagePo pushMessagePo) {
        this.pushMessagePo = pushMessagePo;
    }
}
