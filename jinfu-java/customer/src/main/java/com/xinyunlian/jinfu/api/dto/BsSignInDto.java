package com.xinyunlian.jinfu.api.dto;

import com.xinyunlian.jinfu.api.validate.dto.OpenApiBaseDto;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by dongfangchao on 2017/3/29/0029.
 */
public class BsSignInDto extends OpenApiBaseDto {
    private static final long serialVersionUID = -3189688384619567311L;

    @NotBlank(message = "jinfuUserId不能为空")
    private String jinfuUserId;

    public String getJinfuUserId() {
        return jinfuUserId;
    }

    public void setJinfuUserId(String jinfuUserId) {
        this.jinfuUserId = jinfuUserId;
    }
}
