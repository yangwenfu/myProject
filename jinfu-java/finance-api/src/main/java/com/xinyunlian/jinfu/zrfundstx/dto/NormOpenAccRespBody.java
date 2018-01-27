package com.xinyunlian.jinfu.zrfundstx.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by dongfangchao on 2016/11/22.
 */
@XmlRootElement(name = "Responsebody")
@XmlAccessorType(XmlAccessType.FIELD)
public class NormOpenAccRespBody implements Serializable{

    private static final long serialVersionUID = -4796031139919905258L;
    @XmlElement(name = "Response")
    private NormOpenAccResp normOpenAccResp;

    public NormOpenAccResp getNormOpenAccResp() {
        return normOpenAccResp;
    }

    public void setNormOpenAccResp(NormOpenAccResp normOpenAccResp) {
        this.normOpenAccResp = normOpenAccResp;
    }
}
