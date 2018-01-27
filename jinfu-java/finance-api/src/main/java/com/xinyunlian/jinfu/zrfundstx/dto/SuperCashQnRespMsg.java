package com.xinyunlian.jinfu.zrfundstx.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by dongfangchao on 2016/11/30/0030.
 */
@XmlRootElement(name = "Message")
@XmlAccessorType(XmlAccessType.FIELD)
public class SuperCashQnRespMsg extends BaseMsg {

    private static final long serialVersionUID = 2606073511384455584L;
    @XmlElement(name = "Responsebody")
    private SuperCashQnRespBody superCashQnRespBody;

    public SuperCashQnRespBody getSuperCashQnRespBody() {
        return superCashQnRespBody;
    }

    public void setSuperCashQnRespBody(SuperCashQnRespBody superCashQnRespBody) {
        this.superCashQnRespBody = superCashQnRespBody;
    }
}
