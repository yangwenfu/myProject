package com.ylfin.wallet.portal.controller.req;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class CertificationReq {

    @NotBlank
    private String idCardFrontPic;

    @NotBlank
    private String idCardBackPic;

    @NotBlank
    private String realName;

    @NotBlank
    private String idCardNo;
}
