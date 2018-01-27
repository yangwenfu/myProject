package com.xinyunlian.jinfu.zrfundstx.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by dongfangchao on 2016/12/22/0022.
 */
@XmlRootElement(name = "Message")
@XmlAccessorType(XmlAccessType.FIELD)
public class BankSignApplyRespMsg extends BaseMsg{

    private static final long serialVersionUID = -2310975741231130916L;

    @XmlElement(name = "Responsebody")
    private BankSignApplyRespBody bankSignApplyRespBody;

    public BankSignApplyRespBody getBankSignApplyRespBody() {
        return bankSignApplyRespBody;
    }

    public void setBankSignApplyRespBody(BankSignApplyRespBody bankSignApplyRespBody) {
        this.bankSignApplyRespBody = bankSignApplyRespBody;
    }
}
