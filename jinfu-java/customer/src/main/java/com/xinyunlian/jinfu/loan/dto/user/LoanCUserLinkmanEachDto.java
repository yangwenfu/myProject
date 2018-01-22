package com.xinyunlian.jinfu.loan.dto.user;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author willwang
 */
public class LoanCUserLinkmanEachDto implements Serializable {

    private long linkmanId;

    @NotEmpty
    private String name;

    @NotEmpty
    private String relationship;

    @NotEmpty
    private String mobile;

    private String address;

    public long getLinkmanId() {
        return linkmanId;
    }

    public void setLinkmanId(long linkmanId) {
        this.linkmanId = linkmanId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }
}
