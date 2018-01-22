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
public class NormOpenAccRespMsg extends BaseMsg{

    private static final long serialVersionUID = 2606073511384455584L;
    @XmlElement(name = "Responsebody")
    private NormOpenAccRespBody normOpenAccRespBody;

    public NormOpenAccRespBody getNormOpenAccRespBody() {
        return normOpenAccRespBody;
    }

    public void setNormOpenAccRespBody(NormOpenAccRespBody normOpenAccRespBody) {
        this.normOpenAccRespBody = normOpenAccRespBody;
    }
}
