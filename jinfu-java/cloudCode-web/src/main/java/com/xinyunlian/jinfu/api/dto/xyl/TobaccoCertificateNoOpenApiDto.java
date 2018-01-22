package com.xinyunlian.jinfu.api.dto.xyl;

import com.xinyunlian.jinfu.api.validate.dto.OpenApiBaseDto;

import javax.validation.constraints.Pattern;

/**
 * Created by menglei on 2017年02月07日.
 */
public class TobaccoCertificateNoOpenApiDto extends OpenApiBaseDto {

    private static final long serialVersionUID = -1L;

    @Pattern(regexp = "[0-9]{12}", message = "请输入正确的烟草证号")
    private String tobaccoCertificateNo;//烟草证

    public String getTobaccoCertificateNo() {
        return tobaccoCertificateNo;
    }

    public void setTobaccoCertificateNo(String tobaccoCertificateNo) {
        this.tobaccoCertificateNo = tobaccoCertificateNo;
    }
}
