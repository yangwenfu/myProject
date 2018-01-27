package com.xinyunlian.jinfu.shopkeeper.dto.card;

import com.xinyunlian.jinfu.user.enums.ERelationship;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by King on 2017/2/14.
 */
public class StoreExtDto implements Serializable{
    private static final long serialVersionUID = -3448281291619587264L;
    private String userId;

    private Long storeId;

    //与证照登记人关系
    @NotNull
    private ERelationship relationship;

    //员工人数

    @NotNull
    private Integer employeeNum;

    //经营面积
    @NotNull
    private BigDecimal businessArea;

    //电话
    @NotEmpty
    private String tel = "";

    /**
     * 店铺门头照
     */
    @NotEmpty
    private String storeHeaderPic = "";

    /**
     * 店铺内部照片
     */
    @NotEmpty
    private String storeInnerPic = "";

    /**
     * 结婚证照片
     */
    private String marryCertificatePic = "";

    /**
     * 结婚证照片Id
     */
    private Long marryCertificatePicId = 0L;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public ERelationship getRelationship() {
        return relationship;
    }

    public void setRelationship(ERelationship relationship) {
        this.relationship = relationship;
    }

    public Integer getEmployeeNum() {
        return employeeNum;
    }

    public void setEmployeeNum(Integer employeeNum) {
        this.employeeNum = employeeNum;
    }

    public BigDecimal getBusinessArea() {
        return businessArea;
    }

    public void setBusinessArea(BigDecimal businessArea) {
        this.businessArea = businessArea;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getStoreHeaderPic() {
        return storeHeaderPic;
    }

    public void setStoreHeaderPic(String storeHeaderPic) {
        this.storeHeaderPic = storeHeaderPic;
    }

    public String getStoreInnerPic() {
        return storeInnerPic;
    }

    public void setStoreInnerPic(String storeInnerPic) {
        this.storeInnerPic = storeInnerPic;
    }

    public String getMarryCertificatePic() {
        return marryCertificatePic;
    }

    public void setMarryCertificatePic(String marryCertificatePic) {
        this.marryCertificatePic = marryCertificatePic;
    }

    public Long getMarryCertificatePicId() {
        return marryCertificatePicId;
    }

    public void setMarryCertificatePicId(Long marryCertificatePicId) {
        this.marryCertificatePicId = marryCertificatePicId;
    }
}
