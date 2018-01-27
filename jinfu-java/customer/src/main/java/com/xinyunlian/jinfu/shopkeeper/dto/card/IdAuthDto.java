package com.xinyunlian.jinfu.shopkeeper.dto.card;

import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * Created by King on 2017/2/14.
 */
public class IdAuthDto implements Serializable{
    private static final long serialVersionUID = 2033119985136148804L;

    private String userName;

    private String idCardNo;
    /**
     * 身份证正面
     */
    @NotEmpty
    private String idCardFrontPic;

    /**
     * 身份证正面id
     */
    private Long idCardFrontPicId;

    /**
     * 身份证背面
     */
    @NotEmpty
    private String idCardBackPic;

    /**
     * 身份证背面Id
     */
    private Long idCardBackPicId;

    /**
     * 手持身份证
     */
    @NotEmpty
    private String heldIdCardPic;

    /**
     * 手持身份证Id
     */
    private Long heldIdCardPicId;

    public String getIdCardFrontPic() {
        return idCardFrontPic;
    }

    public void setIdCardFrontPic(String idCardFrontPic) {
        this.idCardFrontPic = idCardFrontPic;
    }

    public Long getIdCardFrontPicId() {
        return idCardFrontPicId;
    }

    public void setIdCardFrontPicId(Long idCardFrontPicId) {
        this.idCardFrontPicId = idCardFrontPicId;
    }

    public String getIdCardBackPic() {
        return idCardBackPic;
    }

    public void setIdCardBackPic(String idCardBackPic) {
        this.idCardBackPic = idCardBackPic;
    }

    public Long getIdCardBackPicId() {
        return idCardBackPicId;
    }

    public void setIdCardBackPicId(Long idCardBackPicId) {
        this.idCardBackPicId = idCardBackPicId;
    }

    public String getHeldIdCardPic() {
        return heldIdCardPic;
    }

    public void setHeldIdCardPic(String heldIdCardPic) {
        this.heldIdCardPic = heldIdCardPic;
    }

    public Long getHeldIdCardPicId() {
        return heldIdCardPicId;
    }

    public void setHeldIdCardPicId(Long heldIdCardPicId) {
        this.heldIdCardPicId = heldIdCardPicId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }
}
