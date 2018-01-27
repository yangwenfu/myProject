package com.xinyunlian.jinfu.area.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by DongFC on 2016-08-18.
 */
@Entity
@Table(name = "sys_area_inf")
public class SysAreaInfPo implements Serializable {

    private static final long serialVersionUID = -4505738495276722694L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "FULL_NAME")
    private String fullName;

    @Column(name = "TREE_PATH")
    private String treePath;

    @Column(name = "PARENT")
    private Long parent;

    @Column(name = "GB_CODE")
    private String gbCode;

    @Column(name = "ORDERS")
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
