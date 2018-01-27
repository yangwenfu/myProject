package com.xinyunlian.jinfu.zrfundstx.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by dongfangchao on 2016/11/29/0029.
 */
@XmlRootElement(name = "Message")
@XmlAccessorType(XmlAccessType.FIELD)
public class SuperCashQueryShareRespMsg extends BaseMsg{

    private static final long serialVersionUID = 896051008646556274L;

    @XmlElement(name = "Responsebody")
    private SuperCashQueryShareRespBody superCashQueryShareRespBody;

    public SuperCashQueryShareRespBody getSuperCashQueryShareRespBody() {
        return superCashQueryShareRespBody;
    }

    public void setSuperCashQueryShareRespBody(SuperCashQueryShareRespBody superCashQueryShareRespBody) {
        this.superCashQueryShareRespBody = superCashQueryShareRespBody;
    }
}
