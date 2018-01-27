package com.xinyunlian.jinfu.zrfundstx.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by dongfangchao on 2016/11/29/0029.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Responsebody")
public class SuperCashQueryShareRespBody implements Serializable {
    private static final long serialVersionUID = -8963265487278850378L;

    @XmlElement(name = "Response")
    private SuperCashQueryShareResp superCashQueryShareResp;

    public SuperCashQueryShareResp getSuperCashQueryShareResp() {
        return superCashQueryShareResp;
    }

    public void setSuperCashQueryShareResp(SuperCashQueryShareResp superCashQueryShareResp) {
        this.superCashQueryShareResp = superCashQueryShareResp;
    }
}
