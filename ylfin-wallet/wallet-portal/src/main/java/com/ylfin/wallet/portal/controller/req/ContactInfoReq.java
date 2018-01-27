package com.ylfin.wallet.portal.controller.req;

import com.xinyunlian.jinfu.user.enums.ERelationship;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class ContactInfoReq {

    @NotBlank
    private String contactName;

    @NotBlank
    private String contactMobile;

    @NotBlank
    private String relationship;
}
