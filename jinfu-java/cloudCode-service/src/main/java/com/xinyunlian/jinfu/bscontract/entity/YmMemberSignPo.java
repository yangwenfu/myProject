package com.xinyunlian.jinfu.bscontract.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;

import javax.persistence.*;

/**
 * Created by dongfangchao on 2017/2/22/0022.
 */
@Entity
@Table(name = "ym_member_sign")
public class YmMemberSignPo extends BaseMaintainablePo {
    private static final long serialVersionUID = 2799316336275750359L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "STORE_ID")
    private Long storeId;

    @Column(name = "QRCODE_NO")
    private String qrcodeNo;

    @Column(name = "FIRST_PAGE_FILE_PATH")
    private String firstPageFilePath;

    @Column(name = "LAST_PAGE_FILE_PATH")
    private String lastPageFilePath;

    @Column(name = "SIGNED")
    private Boolean signed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getQrcodeNo() {
        return qrcodeNo;
    }

    public void setQrcodeNo(String qrcodeNo) {
        this.qrcodeNo = qrcodeNo;
    }

    public Boolean getSigned() {
        return signed;
    }

    public void setSigned(Boolean signed) {
        this.signed = signed;
    }

    public String getFirstPageFilePath() {
        return firstPageFilePath;
    }

    public void setFirstPageFilePath(String firstPageFilePath) {
        this.firstPageFilePath = firstPageFilePath;
    }

    public String getLastPageFilePath() {
        return lastPageFilePath;
    }

    public void setLastPageFilePath(String lastPageFilePath) {
        this.lastPageFilePath = lastPageFilePath;
    }
}
