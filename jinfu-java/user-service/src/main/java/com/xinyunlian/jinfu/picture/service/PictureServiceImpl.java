package com.xinyunlian.jinfu.picture.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.security.StaticResourceSecurity;
import com.xinyunlian.jinfu.picture.dao.PictureDao;
import com.xinyunlian.jinfu.picture.dto.PictureDto;
import com.xinyunlian.jinfu.picture.entity.PicturePo;
import com.xinyunlian.jinfu.picture.enums.EPictureType;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片ServiceImpl
 * @author KimLL
 * @version 
 */

@Service
public class PictureServiceImpl implements PictureService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PictureServiceImpl.class);

	@Autowired
	private PictureDao pictureDao;
	@Value("${file.addr}")
	private String fileAddr;

	@Override
	@Transactional
	public PictureDto savePicture(PictureDto pictureDto){
		PicturePo picturePo = ConverterService.convert(pictureDto, PicturePo.class);
		pictureDao.save(picturePo);
		pictureDto.setPictureId(picturePo.getPictureId());
		return pictureDto;
	}

	@Override
	@Transactional
	public List<PictureDto>  savePicture(List<PictureDto> pictureDtos){
		if(CollectionUtils.isEmpty(pictureDtos)){
			return pictureDtos;
		}

		pictureDtos.forEach(pictureDto -> {
			PicturePo picturePo = new PicturePo();
			if(!StringUtils.isEmpty(pictureDto.getParentId())) {
				pictureDao.delete(pictureDto.getParentId(),pictureDto.getPictureType().getCode());
			}
			ConverterService.convert(pictureDto, picturePo);
			pictureDao.save(picturePo);
			pictureDto.setPictureId(picturePo.getPictureId());
		});

		return pictureDtos;
	}

	@Override
	public PictureDto get(String parentId, EPictureType pictureType) {
		List<PicturePo> picturePos = pictureDao.findByParentId(parentId);
		for (PicturePo picturePo : picturePos) {
			if(picturePo.getPictureType() == pictureType){
				return ConverterService.convert(picturePo, PictureDto.class);
			}
		}
		return null;
	}

	@Override
	public List<PictureDto> list(String parentId) {
		List<PictureDto> pictureDtos = new ArrayList<>();
		List<PicturePo> picturePos = pictureDao.findByParentId(parentId);
		if(!CollectionUtils.isEmpty(picturePos)){
			PictureDto tempLivePic = new PictureDto();
			for (PicturePo picturePo : picturePos) {
				PictureDto pictureDto = ConverterService.convert(picturePo, PictureDto.class);
				if(!StringUtils.isEmpty(picturePo.getPicturePath())){
					pictureDto.setPictureFullPath(fileAddr + StaticResourceSecurity.getSecurityURI(picturePo.getPicturePath()));
				}

				if(picturePo.getPictureType() == EPictureType.LIVE ){
					if(tempLivePic.getCreateTs() == null
							|| picturePo.getCreateTs().compareTo(tempLivePic.getCreateTs()) > 0) {
						tempLivePic = pictureDto;
					}
				}else {
					pictureDtos.add(pictureDto);
				}
			}
			if(tempLivePic.getPictureType() != null) {
				pictureDtos.add(tempLivePic);
			}
		}
		return pictureDtos;
	}

	@Override
	public List<PictureDto> list(String parentId,EPictureType pictureType) {
		List<PictureDto> pictureDtos = new ArrayList<>();
		List<PicturePo> picturePos = pictureDao.findByParentIdAndPictureType(parentId,pictureType);
		if(!CollectionUtils.isEmpty(picturePos)){
			for (PicturePo picturePo : picturePos) {
				PictureDto pictureDto = ConverterService.convert(picturePo, PictureDto.class);
				if(!StringUtils.isEmpty(picturePo.getPicturePath())){
					pictureDto.setPictureFullPath(fileAddr + StaticResourceSecurity.getSecurityURI(picturePo.getPicturePath()));
				}
				pictureDtos.add(pictureDto);
			}
		}
		return pictureDtos;
	}

	@Override
	@Transactional
	public void deletePicture(Long pictureId){
		pictureDao.delete(pictureId);
	}

	@Override
	@Transactional
	public void deleteByParentId(String parentId){
		pictureDao.deleteByParentId(parentId);
	}

	@Override
	@Transactional
	public void deleteByParentId(String parentId,String pictureType){
		pictureDao.delete(parentId,pictureType);
	}

}
