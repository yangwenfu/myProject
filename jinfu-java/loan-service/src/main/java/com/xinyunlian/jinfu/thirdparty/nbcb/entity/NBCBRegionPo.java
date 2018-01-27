package com.xinyunlian.jinfu.thirdparty.nbcb.entity;

import javax.persistence.*;

/**
 * Created by bright on 2017/7/10.
 */
@Entity
@Table(name = "NBCB_REGION")
public class NBCBRegionPo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CITY_ID")
    private String cityId;

    @Column(name = "CITY_NAME")
    public String cityName;

    @Column(name = "ENABLED")
    public Boolean enabled;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
