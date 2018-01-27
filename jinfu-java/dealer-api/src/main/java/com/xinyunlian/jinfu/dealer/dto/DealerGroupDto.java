package com.xinyunlian.jinfu.dealer.dto;

import java.io.Serializable;

/**
 * Created by menglei on 2016年08月29日.
 */
public class DealerGroupDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long groupId;

    private String name;

    private String treePath;

    private String parent;

    private String orders;

    private String dealerId;

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTreePath() {
        return treePath;
    }

    public void setTreePath(String treePath) {
        this.treePath = treePath;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }
}
