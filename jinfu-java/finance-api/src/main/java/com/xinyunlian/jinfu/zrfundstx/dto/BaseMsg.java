package com.xinyunlian.jinfu.zrfundstx.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

/**
 * Created by dongfangchao on 2016/11/22.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class BaseMsg implements Serializable{

    private static final long serialVersionUID = -4586465113132294065L;

    @XmlElement(name = "Signature")
    private String signature;

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
