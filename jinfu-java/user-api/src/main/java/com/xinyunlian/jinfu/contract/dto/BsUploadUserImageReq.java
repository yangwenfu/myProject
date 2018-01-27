package com.xinyunlian.jinfu.contract.dto;
import java.io.Serializable;

/**
 * Created by jll on 2017/2/7/0007.
 */
public class BsUploadUserImageReq implements Serializable {
    private static final long serialVersionUID = 431089375425870994L;
    private String image;
    private String mobile;
    private String name;
    private String imgType;
    private String imgName;
    private String sealName;
    private String useracount;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgType() {
        return imgType;
    }

    public void setImgType(String imgType) {
        this.imgType = imgType;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getSealName() {
        return sealName;
    }

    public void setSealName(String sealName) {
        this.sealName = sealName;
    }

    public String getUseracount() {
        return useracount;
    }

    public void setUseracount(String useracount) {
        this.useracount = useracount;
    }
}