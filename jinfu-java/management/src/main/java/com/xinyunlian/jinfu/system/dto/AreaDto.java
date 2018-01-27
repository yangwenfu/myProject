package com.xinyunlian.jinfu.system.dto;

import java.io.Serializable;

/**
 * Created by menglei on 2016年09月02日.
 */
public class AreaDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String fullName;

    private String treePath;

    private Long parent;

    private String gbCode;

    private Integer orders;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrders() {
        return orders;
    }

    public void setOrders(Integer orders) {
        this.orders = orders;
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
