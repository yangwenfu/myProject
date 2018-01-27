package com.xinyunlian.jinfu.user.dto;

import com.xinyunlian.jinfu.picture.enums.EPictureType;

import java.io.Serializable;

/**
 * Created by JL on 2016/9/21.
 */
public class PictureUploadDto implements Serializable{

    private String pictureBase64;

    private EPictureType pictureType;

    private String parentId;

    public String getPictureBase64() {
        return pictureBase64;
    }

    public void setPictureBase64(String pictureBase64) {
        this.pictureBase64 = pictureBase64;
    }

    public EPictureType getPictureType() {
        return pictureType;
    }

    public void setPictureType(EPictureType pictureType) {
        this.pictureType = pictureType;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
