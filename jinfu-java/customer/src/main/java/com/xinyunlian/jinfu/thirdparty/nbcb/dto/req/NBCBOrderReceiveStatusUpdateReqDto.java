package com.xinyunlian.jinfu.thirdparty.nbcb.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bright on 2017/5/18.
 */
public class NBCBOrderReceiveStatusUpdateReqDto extends CommonReqDto {
    @JsonProperty("name")
    private String name;

    @JsonProperty("id_no")
    private String idNo;

    @JsonProperty("order_no")
    private String orderNo;

    @JsonProperty("order_status")
    private String orderStatus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
