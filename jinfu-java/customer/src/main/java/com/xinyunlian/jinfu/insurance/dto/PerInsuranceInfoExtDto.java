package com.xinyunlian.jinfu.insurance.dto;

/**
 * Created by DongFC on 2016-11-03.
 */
public class PerInsuranceInfoExtDto extends PerInsuranceInfoDto {
    private static final long serialVersionUID = -6246709644514055420L;

    private String prodTypeName;

    public String getProdTypeName() {
        return prodTypeName;
    }

    public void setProdTypeName(String prodTypeName) {
        this.prodTypeName = prodTypeName;
    }
}
