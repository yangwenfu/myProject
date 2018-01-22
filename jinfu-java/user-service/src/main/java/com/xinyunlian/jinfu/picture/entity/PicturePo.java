package com.xinyunlian.jinfu.picture.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.picture.enums.EPictureType;
import com.xinyunlian.jinfu.picture.enums.converter.EPictureTypeConverter;

import javax.persistence.*;

/**
 * 图片Entity
 *
 * @author KimLL
 */
@Entity
@Table(name = "PICTURE")
public class PicturePo extends BaseMaintainablePo {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PICTURE_ID")
    private Long pictureId;

    @Column(name = "PARENT_ID")
    private String parentId;

    @Column(name = "PICTURE_TYPE")
    @Convert(converter = EPictureTypeConverter.class)
    private EPictureType pictureType;

    @Column(name = "PICTURE_PATH")
    private String picturePath;

    @Column(name = "PICTURE_NAME")
    private String pictureName;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "SIZES")
    private int sizes;

    @Column(name = "STATUS")
    private String status;

    public void setPictureId(Long pictureId) {
        this.pictureId = pictureId;
    }

    public Long getPictureId() {
        return pictureId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public EPictureType getPictureType() {
        return pictureType;
    }

    public void setPictureType(EPictureType pictureType) {
        this.pictureType = pictureType;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setSizes(int sizes) {
        this.sizes = sizes;
    }

    public int getSizes() {
        return sizes;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}


