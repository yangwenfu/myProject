package com.xinyunlian.jinfu.api.dto.xyl;

import com.xinyunlian.jinfu.api.validate.dto.OpenApiBaseDto;
import org.hibernate.validator.constraints.NotBlank;

import java.math.BigDecimal;

/**
 * Created by menglei on 2017-08-25.
 */
public class UserScanOpenApiDto extends OpenApiBaseDto {

    private static final long serialVersionUID = 506057658733074423L;

    //二维码
    @NotBlank(message = "二维码编号不能为空")
    private String qrCodeNo;
    //支付类型 1支付宝 2微信
    @NotBlank(message = "支付类型不能为空")
    private String type;
    //消费金额
    @NotBlank(message = "支付金额不能为空")
    private BigDecimal amount;

    public String getQrCodeNo() {
        return qrCodeNo;
    }

    public void setQrCodeNo(String qrCodeNo) {
        this.qrCodeNo = qrCodeNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
