package com.xinyunlian.jinfu.api.dto;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * Created by DongFC on 2016-11-03.
 */
public class ClerkOpenApiDto implements Serializable {

    private static final long serialVersionUID = 506057658733074423L;

    private String clerkId;

    @NotBlank
    private String name;

    @NotBlank
    private String mobile;

    public String getClerkId() {
        return clerkId;
    }

    public void setClerkId(String clerkId) {
        this.clerkId = clerkId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
