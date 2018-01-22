package com.xinyunlian.jinfu.pingan.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/6/12/0012.
 */
@Entity
@Table(name = "pingan_typhoon_control_region")
public class PinganTyphoonControlRegionPo implements Serializable {

    private static final long serialVersionUID = 2457253793941640359L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "REGION_ID")
    private Long regionId;

    @Column(name = "REGION_NAME")
    private String regionName;

    @Column(name = "TREE_PATH")
    private String treePath;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getTreePath() {
        return treePath;
    }

    public void setTreePath(String treePath) {
        this.treePath = treePath;
    }
}
