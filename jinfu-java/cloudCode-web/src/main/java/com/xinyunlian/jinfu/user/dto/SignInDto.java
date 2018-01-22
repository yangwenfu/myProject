package com.xinyunlian.jinfu.user.dto;

import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;


/**
* Class Name: SignInDto
* @author SC
*
*/
public class SignInDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
