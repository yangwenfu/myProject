package com.xinyunlian.jinfu.crm.dto;

import com.xinyunlian.jinfu.common.dto.BaseDto;

import java.util.List;

/**
 * 客户通话类型Entity
 *
 * @author jll
 */

public class CrmCallTypeDto extends BaseDto {

    private static final long serialVersionUID = 1L;

    private Long callTypeId;
    private String callTypeCode;
    private String callTypeName;
    private Long parent;
    private String callTypePath;
    private Integer orders;
    private Boolean display;

    private List<Long> parents;

    public void setCallTypeId(Long callTypeId) {
        this.callTypeId = callTypeId;
    }

    public Long getCallTypeId() {
        return callTypeId;
    }

    public void setCallTypeCode(String callTypeCode) {
        this.callTypeCode = callTypeCode;
    }

    public String getCallTypeCode() {
        return callTypeCode;
    }

    public void setCallTypeName(String callTypeName) {
        this.callTypeName = callTypeName;
    }

    public String getCallTypeName() {
        return callTypeName;
    }

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }

    public void setCallTypePath(String callTypePath) {
        this.callTypePath = callTypePath;
    }

    public String getCallTypePath() {
        return callTypePath;
    }

    public void setOrders(Integer orders) {
        this.orders = orders;
    }

    public Integer getOrders() {
        return orders;
    }

    public void setDisplay(Boolean display) {
        this.display = display;
    }

    public Boolean getDisplay() {
        return display;
    }

    public List<Long> getParents() {
        return parents;
    }

    public void setParents(List<Long> parents) {
        this.parents = parents;
    }
}


