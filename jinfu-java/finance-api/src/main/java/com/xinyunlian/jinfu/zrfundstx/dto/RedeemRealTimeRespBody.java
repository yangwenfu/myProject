package com.xinyunlian.jinfu.zrfundstx.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by dongfangchao on 2016/11/22.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Responsebody")
public class RedeemRealTimeRespBody implements Serializable{

    private static final long serialVersionUID = -4796031139919905258L;
    @XmlElement(name = "Response")
    private RedeemRealTimeResp redeemRealTimeResp;

    public RedeemRealTimeResp getRedeemRealTimeResp() {
        return redeemRealTimeResp;
    }

    public void setRedeemRealTimeResp(RedeemRealTimeResp redeemRealTimeResp) {
        this.redeemRealTimeResp = redeemRealTimeResp;
    }
}
