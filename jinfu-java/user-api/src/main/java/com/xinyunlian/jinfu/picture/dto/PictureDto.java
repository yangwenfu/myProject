package com.xinyunlian.jinfu.picture.dto;

import com.xinyunlian.jinfu.common.dto.BaseMaintainableDto;
import com.xinyunlian.jinfu.picture.enums.EPictureType;

/**
 * 图片Entity
 * @author KimLL
 * @version 
 */

public class PictureDto extends BaseMaintainableDto {
	
	private static final long serialVersionUID = 1L;

	private Long pictureId;
	private String parentId;
	private EPictureType pictureType;
	private String picturePath;
	private String pictureFullPath;
	private String pictureName;
	private String title;
	private int sizes;
	private String status;
	public void setPictureId(Long pictureId){
		this.pictureId=pictureId;
	}
	public Long getPictureId(){
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

	public String getPictureFullPath() {
		return pictureFullPath;
	}

	public void setPictureFullPath(String pictureFullPath) {
		this.pictureFullPath = pictureFullPath;
	}

	public void setPicturePath(String picturePath){
		this.picturePath=picturePath;
	}
	public String getPicturePath(){
		return picturePath;
	}
	public void setPictureName(String pictureName){
		this.pictureName=pictureName;
	}
	public String getPictureName(){
		return pictureName;
	}
	public void setTitle(String title){
		this.title=title;
	}
	public String getTitle(){
		return title;
	}
	public void setSizes(int sizes){
		this.sizes=sizes;
	}
	public int getSizes(){
		return sizes;
	}
	public void setStatus(String status){
		this.status=status;
	}
	public String getStatus(){
		return status;
	}

	@Override
	public String toString() {
		return "PictureDto{" +
				"pictureId=" + pictureId +
				", parentId='" + parentId + '\'' +
				", pictureType=" + pictureType +
				", picturePath='" + picturePath + '\'' +
				", pictureFullPath='" + pictureFullPath + '\'' +
				", pictureName='" + pictureName + '\'' +
				", title='" + title + '\'' +
				", sizes=" + sizes +
				", status='" + status + '\'' +
				'}';
	}
}


