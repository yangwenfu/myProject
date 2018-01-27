package com.ylfin.wallet.portal.controller.req;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@Data
public class StoreInfoReq {

    // 行业
    @NotBlank
    private String industryMcc;

    @NotBlank
    private String storeName;
    @NotBlank
    private String districtId;
    @NotBlank
    private String provinceId;
    @NotBlank
    private String cityId;
    @NotBlank
    private String areaId;
    @NotBlank
    private String streetId;
    @NotBlank
    private String province = "";
    @NotBlank
    private String city = "";
    @NotBlank
    private String area = "";
    @NotBlank
    private String street = "";
    private String address = "";
    @NotBlank
    private String tel;
    //烟草许可证号
    private String tobaccoCertificateNo;
    //烟草许可证号图片
    private String tobaccoCertPic;
    //许可证号
    private String bizLicence;
    //许可证图片
    private String bizLicencePic;
}
