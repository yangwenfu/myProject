package com.xinyunlian.jinfu.loan.dto.aitz;

import java.io.Serializable;

/**
 * @author willwang
 */
public class AitouziContractDto implements Serializable{

    private String name;

    private String type;

    private String url;

    private Boolean signed;

    public AitouziContractDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getSigned() {
        return signed;
    }

    public void setSigned(Boolean signed) {
        this.signed = signed;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
