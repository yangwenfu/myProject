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
public class RedeemNormRespBody implements Serializable{

    private static final long serialVersionUID = -4796031139919905258L;
    @XmlElement(name = "Response")
    private RedeemNormResp redeemNormResp;

    public RedeemNormResp getRedeemNormResp() {
        return redeemNormResp;
    }

    public void setRedeemNormResp(RedeemNormResp redeemNormResp) {
        this.redeemNormResp = redeemNormResp;
    }
}
