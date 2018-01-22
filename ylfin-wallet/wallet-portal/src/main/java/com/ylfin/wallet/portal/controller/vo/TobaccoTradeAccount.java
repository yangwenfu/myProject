package com.ylfin.wallet.portal.controller.vo;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class TobaccoTradeAccount {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
