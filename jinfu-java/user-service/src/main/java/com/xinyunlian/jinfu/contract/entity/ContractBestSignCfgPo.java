package com.xinyunlian.jinfu.contract.entity;

import com.xinyunlian.jinfu.contract.enums.EBsSignType;
import com.xinyunlian.jinfu.contract.enums.ECntrctTmpltType;
import com.xinyunlian.jinfu.contract.enums.converter.EBsSignTypeConverter;
import com.xinyunlian.jinfu.contract.enums.converter.ECntrctTmpltTypeConverter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/2/14/0014.
 */
@Entity
@Table(name = "contract_best_sign_cfg")
public class ContractBestSignCfgPo implements Serializable {
    private static final long serialVersionUID = -3017153730217397766L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CNTRCT_TMPLT_TYPE")
    @Convert(converter = ECntrctTmpltTypeConverter.class)
    private ECntrctTmpltType cntrctTmpltType;

    @Column(name = "SIGN_TYPE")
    @Convert(converter = EBsSignTypeConverter.class)
    private EBsSignType signType;

    @Column(name = "SEAL_NAME")
    private String sealName;

    @Column(name = "PAGENUM")
    private String pagenum;

    @Column(name = "SIGNX")
    private String signx;

    @Column(name = "SIGNY")
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
