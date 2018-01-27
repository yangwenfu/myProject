package com.xinyunlian.jinfu.trans.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.common.entity.id.IdInjectionEntityListener;
import com.xinyunlian.jinfu.trans.enums.EInsTransType;
import com.xinyunlian.jinfu.trans.enums.converters.EInsTransTypeConverter;

import javax.persistence.*;

/**
 * Created by dongfangchao on 2017/6/12/0012.
 */
@Entity
@Table(name = "trans_history")
@EntityListeners(IdInjectionEntityListener.class)
public class TransHistoryPo extends BaseMaintainablePo {
    private static final long serialVersionUID = -960466958433226657L;

    @Id
    @Column(name = "TRANS_SERIAL_NO")
    private String transSerialNo;

    @Column(name = "ORDER_NO")
    private String orderNo;

    @Column(name = "REQUEST_URL")
    private String requestUrl;

    @Column(name = "TRANS_TYPE")
    @Convert(converter = EInsTransTypeConverter.class)
    private EInsTransType transType;

    @Column(name = "TRANS_REQUEST")
    private String transRequest;

    @Column(name = "TRANS_RESPONSE")
    private String transResponse;

    public String getTransSerialNo() {
        return transSerialNo;
    }

    public void setTransSerialNo(String transSerialNo) {
        this.transSerialNo = transSerialNo;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public EInsTransType getTransType() {
        return transType;
    }

    public void setTransType(EInsTransType transType) {
        this.transType = transType;
    }

    public String getTransRequest() {
        return transRequest;
    }

    public void setTransRequest(String transRequest) {
        this.transRequest = transRequest;
    }

    public String getTransResponse() {
        return transResponse;
    }

    public void setTransResponse(String transResponse) {
        this.transResponse = transResponse;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
