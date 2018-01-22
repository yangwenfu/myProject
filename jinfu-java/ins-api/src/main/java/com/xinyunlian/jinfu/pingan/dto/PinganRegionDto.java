package com.xinyunlian.jinfu.pingan.dto;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/6/12/0012.
 */
public class PinganRegionDto implements Serializable {
    private static final long serialVersionUID = -7290771656051444880L;

    private Long id;

    private String regionName;

    private String gbCode;

    private Long parent;

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
