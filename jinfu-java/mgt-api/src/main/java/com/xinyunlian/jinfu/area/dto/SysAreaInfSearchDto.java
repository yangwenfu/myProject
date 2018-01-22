package com.xinyunlian.jinfu.area.dto;

import java.io.Serializable;

/**
 * Created by DongFC on 2016-08-22.
 */
public class SysAreaInfSearchDto implements Serializable {
    private static final long serialVersionUID = -7155666485388748584L;

    private Long id;

    private String name;

    private String fullName;

    private String treePath;

    private Long parent;

    private String gbCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGbCode() {
        return gbCode;
    }

    public void setGbCode(String gbCode) {
        this.gbCode = gbCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }

    public String getTreePath() {
        return treePath;
    }

    public void setTreePath(String treePath) {
        this.treePath = treePath;
    }
}
