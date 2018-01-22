package com.xinyunlian.jinfu.zrfundstx.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by dongfangchao on 2016/12/22/0022.
 */
@XmlRootElement(name = "Responsebody")
@XmlAccessorType(XmlAccessType.FIELD)
public class BankSignApplyRespBody implements Serializable {

    private static final long serialVersionUID = 5374907164738413108L;
    @XmlElement(name = "Response")
    private BankSignApplyResp bankSignApplyResp;

    public BankSignApplyResp getBankSignApplyResp() {
        return bankSignApplyResp;
    }

    public void setBankSignApplyResp(BankSignApplyResp bankSignApplyResp) {
        this.bankSignApplyResp = bankSignApplyResp;
    }
}
