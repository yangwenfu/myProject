package com.xinyunlian.jinfu.prod.entity;

import com.xinyunlian.jinfu.prod.enums.EShelfPlatform;
import com.xinyunlian.jinfu.prod.enums.converter.EShelfPlatformConverter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/1/22/0022.
 */
@Entity
@Table(name = "prod_shelf")
public class ProdShelfPo implements Serializable {
    private static final long serialVersionUID = -962002514812789806L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "PROD_ID")
    private String prodId;

    @Column(name = "SHELF_PLATFORM")
    @Convert(converter = EShelfPlatformConverter.class)
    private EShelfPlatform shelfPlatform;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public EShelfPlatform getShelfPlatform() {
        return shelfPlatform;
    }

    public void setShelfPlatform(EShelfPlatform shelfPlatform) {
        this.shelfPlatform = shelfPlatform;
    }
}
