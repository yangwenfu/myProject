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
public class NormApplyPurRespMsg extends BaseMsg{

    private static final long serialVersionUID = 2606073511384455584L;
    @XmlElement(name = "Responsebody")
    private NormApplyPurRespBody normApplyPurRespBody;

    public NormApplyPurRespBody getNormApplyPurRespBody() {
        return normApplyPurRespBody;
    }

    public void setNormApplyPurRespBody(NormApplyPurRespBody normApplyPurRespBody) {
        this.normApplyPurRespBody = normApplyPurRespBody;
    }
}
