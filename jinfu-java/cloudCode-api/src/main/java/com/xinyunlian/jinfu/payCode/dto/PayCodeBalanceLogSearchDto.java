package com.xinyunlian.jinfu.payCode.dto;

import com.xinyunlian.jinfu.common.dto.PagingDto;
import com.xinyunlian.jinfu.payCode.enums.PayCodeBalanceType;

/**
 * Created by carrot on 2017/8/28.
 */
public class PayCodeBalanceLogSearchDto extends PagingDto<PayCodeBalanceLogDto> {
    private String payCodeNo;
    private String mobile;
    private PayCodeBalanceType type;

    public String getPayCodeNo() {
        return payCodeNo;
    }

    public void setPayCodeNo(String payCodeNo) {
        this.payCodeNo = payCodeNo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public PayCodeBalanceType getType() {
        return type;
    }

    public void setType(PayCodeBalanceType type) {
        this.type = type;
    }
}
