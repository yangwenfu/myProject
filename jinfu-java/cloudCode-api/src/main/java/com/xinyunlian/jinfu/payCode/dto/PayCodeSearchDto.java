package com.xinyunlian.jinfu.payCode.dto;

import com.xinyunlian.jinfu.common.dto.PagingDto;

/**
 * Created by carrot on 2017/8/28.
 */
public class PayCodeSearchDto extends PagingDto<PayCodeDto> {
    private String payCodeNo;
    private String mobile;
    private String payCodeUrl;

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

    public String getPayCodeUrl() {
        return payCodeUrl;
    }

    public void setPayCodeUrl(String payCodeUrl) {
        this.payCodeUrl = payCodeUrl;
    }
}
