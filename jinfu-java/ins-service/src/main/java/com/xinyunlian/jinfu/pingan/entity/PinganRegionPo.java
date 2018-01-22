package com.xinyunlian.jinfu.pingan.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/6/12/0012.
 */
@Entity
@Table(name = "pingan_region")
public class PinganRegionPo implements Serializable {
    private static final long serialVersionUID = -8267263146156919972L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "REGION_NAME")
    private String regionName;

    @Column(name = "GB_CODE")
    private String gbCode;

    @Column(name = "PARENT")
    private Long parent;

    @Column(name = "TREE_PATH")
    private String treePath;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getGbCode() {
        return gbCode;
    }

    public void setGbCode(String gbCode) {
        this.gbCode = gbCode;
    }

    public String getTreePath() {
        return treePath;
    }

    public void setTreePath(String treePath) {
        this.treePath = treePath;
    }

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }
}
