package com.ylfin.wallet.portal.controller.req;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Create by yangwenfu on 2018/1/16
 */
@Data
public class ForgetPasswordReq {
    @NotEmpty
    private String mobile;

    @NotEmpty
    private String verifyCode;

    @NotEmpty
    private String newPassword;
}
