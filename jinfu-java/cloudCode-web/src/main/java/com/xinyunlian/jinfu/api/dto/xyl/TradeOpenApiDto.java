package com.xinyunlian.jinfu.api.dto.xyl;

import com.xinyunlian.jinfu.api.validate.dto.OpenApiBaseDto;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by menglei on 2017年05月31日.
 */
public class TradeOpenApiDto extends OpenApiBaseDto {

    private static final long serialVersionUID = -1L;

    @NotBlank(message = "订单号不能为空")
    private String tradeNo;//订单号

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }
}
