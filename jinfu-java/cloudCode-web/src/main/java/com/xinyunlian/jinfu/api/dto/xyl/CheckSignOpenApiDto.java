package com.xinyunlian.jinfu.api.dto.xyl;

import com.xinyunlian.jinfu.api.validate.dto.OpenApiBaseDto;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by menglei on 2017年05月31日.
 */
public class CheckSignOpenApiDto extends OpenApiBaseDto {

    private static final long serialVersionUID = -1L;

    @NotBlank(message = "手机号不能为空")
    private String mobile;//手机

    @NotBlank(message = "outId不能为空")
    private String outId;//外部关联id

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOutId() {
        return outId;
    }

    public void setOutId(String outId) {
        this.outId = outId;
    }
}
