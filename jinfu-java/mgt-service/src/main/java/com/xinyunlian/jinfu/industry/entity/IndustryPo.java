package com.xinyunlian.jinfu.industry.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/5/17/0017.
 */
@Entity
@Table(name = "industry")
public class IndustryPo implements Serializable {
    private static final long serialVersionUID = -6357339070514330905L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "IND_NAME")
    private String indName;

    @Column(name = "IND_TYPE")
    private String indType;

    @Column(name = "IND_DESC")
    private String indDesc;

    @Column(name = "BIZ_LICENSE")
    private Boolean bizLicense;

    @Column(name = "STORE_LICENCE")
    private Boolean storeLicence;

    @Column(name = "LICENCE_NAME")
    private String licenceName;

    @Column(name = "MCC")
    private String mcc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIndName() {
        return indName;
    }

    public void setIndName(String indName) {
        this.indName = indName;
    }

    public Boolean getBizLicense() {
        return bizLicense;
    }

    public void setBizLicense(Boolean bizLicense) {
        this.bizLicense = bizLicense;
    }

    public String getLicenceName() {
        return licenceName;
    }

    public void setLicenceName(String licenceName) {
        this.licenceName = licenceName;
    }

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public String getIndType() {
        return indType;
    }

    public void setIndType(String indType) {
        this.indType = indType;
    }

    public String getIndDesc() {
        return indDesc;
    }

    public void setIndDesc(String indDesc) {
        this.indDesc = indDesc;
    }

    public Boolean getStoreLicence() {
        return storeLicence;
    }

    public void setStoreLicence(Boolean storeLicence) {
        this.storeLicence = storeLicence;
    }
}
