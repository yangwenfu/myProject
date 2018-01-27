package com.xinyunlian.jinfu.shopkeeper.dto.card;

import com.xinyunlian.jinfu.user.enums.ERelationship;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by King on 2017/2/14.
 */
public class LinkmanDto implements Serializable{
    private static final long serialVersionUID = 1405577580227319688L;
    private long linkmanId;
    @NotEmpty
    private String name;

    @NotNull
    private ERelationship relationship;

    @NotEmpty
    private String mobile;

    private String dateType;

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

    public ERelationship getRelationship() {
        return relationship;
    }

    public void setRelationship(ERelationship relationship) {
        this.relationship = relationship;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }
}
