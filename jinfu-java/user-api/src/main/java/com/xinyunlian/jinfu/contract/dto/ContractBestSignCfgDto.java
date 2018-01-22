package com.xinyunlian.jinfu.contract.dto;

import com.xinyunlian.jinfu.contract.enums.EBsSignType;
import com.xinyunlian.jinfu.contract.enums.ECntrctTmpltType;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/2/14/0014.
 */
public class ContractBestSignCfgDto implements Serializable {
    private static final long serialVersionUID = -8507682258343998374L;

    private Long id;

    private ECntrctTmpltType cntrctTmpltType;

    private EBsSignType signType;

    private String sealName;

    private String pagenum;

    private String signx;

    private String signy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ECntrctTmpltType getCntrctTmpltType() {
        return cntrctTmpltType;
    }

    public void setCntrctTmpltType(ECntrctTmpltType cntrctTmpltType) {
        this.cntrctTmpltType = cntrctTmpltType;
    }

    public EBsSignType getSignType() {
        return signType;
    }

    public void setSignType(EBsSignType signType) {
        this.signType = signType;
    }

    public String getPagenum() {
        return pagenum;
    }

    public void setPagenum(String pagenum) {
        this.pagenum = pagenum;
    }

    public String getSignx() {
        return signx;
    }

    public void setSignx(String signx) {
        this.signx = signx;
    }

    public String getSigny() {
        return signy;
    }

    public void setSigny(String signy) {
        this.signy = signy;
    }

    public String getSealName() {
        return sealName;
    }

    public void setSealName(String sealName) {
        this.sealName = sealName;
    }
}
