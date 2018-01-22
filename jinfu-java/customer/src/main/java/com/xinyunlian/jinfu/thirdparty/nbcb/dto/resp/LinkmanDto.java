package com.xinyunlian.jinfu.thirdparty.nbcb.dto.resp;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by bright on 2017/5/26.
 */
public class LinkmanDto implements Serializable{
    private static final long serialVersionUID = 6932969624496254091L;

    @JsonProperty("name")
    private String name;

    @JsonProperty("relationship")
    private String relationship;

    @JsonProperty("mobile")
    private String mobile;

    public LinkmanDto(){}

    public LinkmanDto(String name, String relationship, String mobile) {
        this.name = name;
        this.relationship = relationship;
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
