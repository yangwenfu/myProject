package com.xinyunlian.jinfu.crm.entity;

import com.xinyunlian.jinfu.common.entity.BasePo;

import javax.persistence.*;

/**
 * 客户通话类型Entity
 *
 * @author jll
 */
@Entity
@Table(name = "CRM_CALL_TYPE")
public class CrmCallTypePo extends BasePo {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CALL_TYPE_ID")
    private Long callTypeId;

    @Column(name = "CALL_TYPE_CODE")
    private String callTypeCode;

    @Column(name = "CALL_TYPE_NAME")
    private String callTypeName;

    @Column(name = "PARENT")
    private Long parent;

    @Column(name = "CALL_TYPE_PATH")
    private String callTypePath;

    @Column(name = "ORDERS")
    private Integer orders;

    @Column(name = "DISPLAY")
    private Boolean display;

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

}


