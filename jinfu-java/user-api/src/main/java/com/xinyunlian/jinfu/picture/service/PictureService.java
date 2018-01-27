package com.xinyunlian.jinfu.picture.service;

import com.xinyunlian.jinfu.picture.dto.PictureDto;
import com.xinyunlian.jinfu.picture.enums.EPictureType;

import java.util.List;

/**
 * 图片Service
 * @author KimLL
 * @version 
 */

public interface PictureService {

    PictureDto savePicture(PictureDto pictureDto);

    List<PictureDto> savePicture(List<PictureDto> pictureDtos);

    PictureDto get(String parentId, EPictureType pictureType);

    List<PictureDto> list(String parentId);

    List<PictureDto> list(String parentId,EPictureType pictureType);

    void deletePicture(Long pictureId);

    void deleteByParentId(String parentId);

    void deleteByParentId(String parentId,String pictureType);

}
