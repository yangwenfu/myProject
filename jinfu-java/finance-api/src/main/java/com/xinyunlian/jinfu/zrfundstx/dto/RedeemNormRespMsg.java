package com.xinyunlian.jinfu.zrfundstx.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by dongfangchao on 2016/11/22.
 */
@XmlRootElement(name = "Message")
@XmlAccessorType(XmlAccessType.FIELD)
public class RedeemNormRespMsg extends BaseMsg{

    private static final long serialVersionUID = 2606073511384455584L;
    @XmlElement(name = "Responsebody")
    private RedeemNormRespBody redeemNormRespBody;

    public RedeemNormRespBody getRedeemNormRespBody() {
        return redeemNormRespBody;
    }

    public void setRedeemNormRespBody(RedeemNormRespBody redeemNormRespBody) {
        this.redeemNormRespBody = redeemNormRespBody;
    }
}
