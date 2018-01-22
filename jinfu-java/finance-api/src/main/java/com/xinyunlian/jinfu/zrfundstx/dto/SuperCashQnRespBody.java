package com.xinyunlian.jinfu.zrfundstx.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by dongfangchao on 2016/11/30/0030.
 */
@XmlRootElement(name = "Responsebody")
@XmlAccessorType(XmlAccessType.FIELD)
public class SuperCashQnRespBody implements Serializable{

    private static final long serialVersionUID = -4796031139919905258L;
    @XmlElement(name = "Response")
    private SuperCashQnResp superCashQnResp;

    public SuperCashQnResp getSuperCashQnResp() {
        return superCashQnResp;
    }

    public void setSuperCashQnResp(SuperCashQnResp superCashQnResp) {
        this.superCashQnResp = superCashQnResp;
    }
}
