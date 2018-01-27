package com.ylfin.wallet.portal.controller.req;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class UserRegisterReq {

    @NotEmpty
    private String mobile;

    @NotEmpty
    private String verifyCode;

    @NotEmpty
    private String password;
}
