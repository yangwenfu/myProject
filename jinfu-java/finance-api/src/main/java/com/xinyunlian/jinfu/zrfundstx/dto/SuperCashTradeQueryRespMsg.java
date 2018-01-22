package com.xinyunlian.jinfu.zrfundstx.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by dongfangchao on 2017/1/3/0003.
 */
@XmlRootElement(name = "Message")
@XmlAccessorType(XmlAccessType.FIELD)
public class SuperCashTradeQueryRespMsg extends BaseMsg {
    private static final long serialVersionUID = -2426627058588010084L;

    @XmlElement(name = "Responsebody")
    private SuperCashTradeQueryRespBody superCashTradeQueryRespBody;

    public SuperCashTradeQueryRespBody getSuperCashTradeQueryRespBody() {
        return superCashTradeQueryRespBody;
    }

    public void setSuperCashTradeQueryRespBody(SuperCashTradeQueryRespBody superCashTradeQueryRespBody) {
        this.superCashTradeQueryRespBody = superCashTradeQueryRespBody;
    }
}
