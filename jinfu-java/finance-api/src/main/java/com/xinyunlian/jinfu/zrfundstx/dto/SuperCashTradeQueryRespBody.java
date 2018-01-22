package com.xinyunlian.jinfu.zrfundstx.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/1/3/0003.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Responsebody")
public class SuperCashTradeQueryRespBody implements Serializable {

    private static final long serialVersionUID = 9176421081705467697L;

    @XmlElement(name = "Response")
    private SuperCashTradeQueryResp superCashTradeQueryResp;

    public SuperCashTradeQueryResp getSuperCashTradeQueryResp() {
        return superCashTradeQueryResp;
    }

    public void setSuperCashTradeQueryResp(SuperCashTradeQueryResp superCashTradeQueryResp) {
        this.superCashTradeQueryResp = superCashTradeQueryResp;
    }
}
