package com.xinyunlian.jinfu.config.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.config.enums.ECategory;
import com.xinyunlian.jinfu.config.enums.converter.ECategoryConverter;

import javax.persistence.*;

/**
 * Created by bright on 2017/1/6.
 */
@Entity
@Table( name = "gl_config")
public class ConfigPo extends BaseMaintainablePo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "category")
    @Convert(converter = ECategoryConverter.class)
    private ECategory category;

    @Column(name = "`key`")
    private String key;

    @Column(name = "value")
    private String value;

    @Column(name = "cached")
    private Boolean cached;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ECategory getCategory() {
        return category;
    }

    public void setCategory(ECategory category) {
        this.category = category;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getCached() {
        return cached;
    }

    public void setCached(Boolean cached) {
        this.cached = cached;
    }
}
