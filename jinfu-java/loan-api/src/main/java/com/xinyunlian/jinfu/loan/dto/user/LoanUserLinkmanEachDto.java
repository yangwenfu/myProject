package com.xinyunlian.jinfu.loan.dto.user;


import java.io.Serializable;

/**
 * @author willwang
 */
@Deprecated
public class LoanUserLinkmanEachDto implements Serializable {

    private long linkmanId;

    private String name;

    private String relationship;

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
