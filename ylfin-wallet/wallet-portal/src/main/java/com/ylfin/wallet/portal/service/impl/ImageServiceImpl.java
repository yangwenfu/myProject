package com.ylfin.wallet.portal.service.impl;

import java.io.InputStream;
import java.nio.file.Paths;
import java.util.UUID;

import com.xinyunlian.jinfu.picture.dto.PictureDto;
import com.xinyunlian.jinfu.picture.enums.EPictureType;
import com.xinyunlian.jinfu.picture.service.PictureService;
import com.ylfin.core.ftp.FileStoreOperations;
import com.ylfin.wallet.portal.service.ImageInfo;
import com.ylfin.wallet.portal.service.ImageService;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {

	private static final String TMP_PATH = "/tmp/";

	@Autowired
	private PictureService pictureService;

	@Autowired
	private FileStoreOperations fileStoreOperations;


	@Override
	public String uploadTmpImg(InputStream inputStream, String extension) {
		// 先上传到 ftp 临时目录, 后续业务使用时需要将文件通过 rename 方式转移到正确的目录
		String tmpPath = generateTmpName(extension);
		fileStoreOperations.upload(inputStream, tmpPath);
		return tmpPath;
	}

	@Override
	public String uploadTmpImg(byte[] bytes, String extension) {
		// 先上传到 ftp 临时目录, 后续业务使用时需要将文件通过 rename 方式转移到正确的目录
		String tmpPath = generateTmpName(extension);
		fileStoreOperations.upload(bytes, tmpPath);
		return tmpPath;
	}

	private String generateTmpName(String extension) {
		return new StringBuilder(TMP_PATH).append(DateTime.now().toString("yyyyMMdd"))
				.append(UUID.randomUUID().toString().replace("-", ""))
				.append(".").append(extension).toString();
	}

	@Override
	public ImageInfo saveIdCardFrontImg(String tmpPath, String userId) {
		return savePicture(tmpPath, userId, EPictureType.CARD_FRONT);
	}

	@Override
	public ImageInfo saveIdCardBackImg(String tmpPath, String userId) {
		return savePicture(tmpPath, userId, EPictureType.CARD_BACK);
	}

	@Override
	public ImageInfo saveIdCardHeldImg(String tmpPath, String userId) {
		return savePicture(tmpPath, userId, EPictureType.HELD_ID_CARD);
	}

	@Override
	public ImageInfo saveTobaccoLicenseImg(String tmpPath, String storeId) {
		return savePicture(tmpPath, storeId, EPictureType.STORE_TOBACCO);
	}

	@Override
	public ImageInfo saveHouseholdRegisterMainImg(String tmpPath, String userId) {
		return savePicture(tmpPath, userId, EPictureType.HOUSEHOLDER_BOOKLET);
	}

	@Override
	public ImageInfo saveHouseholdRegisterOwnerPic(String tmpPath, String userId) {
		return savePicture(tmpPath, userId, EPictureType.RESIDENCE_BOOKLET);
	}

	@Override
	public ImageInfo saveStoreLicencPic(String tmpPath, String storeId) {
		return savePicture(tmpPath, storeId, EPictureType.STORE_LICENCE);
	}


	private ImageInfo savePicture(String tmpPath, String parentId, EPictureType pictureType) {
		String targetPath = getTargetPath(tmpPath, pictureType);
		fileStoreOperations.rename(tmpPath, targetPath);
		PictureDto pictureDto = new PictureDto();
		pictureDto.setParentId(parentId);
		pictureDto.setPictureType(pictureType);
		pictureDto.setPicturePath(targetPath);
		pictureDto = pictureService.savePicture(pictureDto);
		ImageInfo imageInfo = new ImageInfo();
		imageInfo.setPicId(pictureDto.getPictureId());
		imageInfo.setImgPath(targetPath);
		return imageInfo;
	}

	private String getTargetPath(String tmpPath, EPictureType pictureType) {
		return Paths.get("/", pictureType.getCode(), StringUtils.substringAfter(tmpPath, TMP_PATH)).toString();
	}
}
