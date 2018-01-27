package com.xinyunlian.jinfu.bscontract.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dongfangchao on 2017/2/22/0022.
 */
public class YmMemberSignDto implements Serializable {
    private static final long serialVersionUID = -8790590372981630997L;

    private Long id;

    private Long storeId;

    private String qrcodeNo;

    private String firstPageFilePath;

    private String lastPageFilePath;

    private Boolean signed;

    private String createOpId;

    private Date createTs;

    private String lastMntOpId;

    private Date lastMntTs;

    private Long versionCt;

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

    public String getCreateOpId() {
        return createOpId;
    }

    public void setCreateOpId(String createOpId) {
        this.createOpId = createOpId;
    }

    public Date getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }

    public String getLastMntOpId() {
        return lastMntOpId;
    }

    public void setLastMntOpId(String lastMntOpId) {
        this.lastMntOpId = lastMntOpId;
    }

    public Date getLastMntTs() {
        return lastMntTs;
    }

    public void setLastMntTs(Date lastMntTs) {
        this.lastMntTs = lastMntTs;
    }

    public Long getVersionCt() {
        return versionCt;
    }

    public void setVersionCt(Long versionCt) {
        this.versionCt = versionCt;
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
