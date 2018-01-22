package com.xinyunlian.jinfu.contract.entity;

import com.xinyunlian.jinfu.contract.enums.EBsSignType;
import com.xinyunlian.jinfu.contract.enums.ECntrctTmpltType;
import com.xinyunlian.jinfu.contract.enums.converter.EBsSignTypeConverter;
import com.xinyunlian.jinfu.contract.enums.converter.ECntrctTmpltTypeConverter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by jll on 2017/2/8/0008.
 */
@Entity
@Table(name = "contract_best_company")
public class ContractBestCompanyPo implements Serializable {
    private static final long serialVersionUID = -1437359567370165022L;
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CNTRCT_TMPLT_TYPE")
    @Convert(converter = ECntrctTmpltTypeConverter.class)
    private ECntrctTmpltType cntrctTmpltType;

    @Column(name = "NAME")
    private String name;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "MOBILE")
    private String mobile;

    @Column(name = "SIGN_TYPE")
    @Convert(converter = EBsSignTypeConverter.class)
    private EBsSignType signType;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public EBsSignType getSignType() {
        return signType;
    }

    public void setSignType(EBsSignType signType) {
        this.signType = signType;
    }
}