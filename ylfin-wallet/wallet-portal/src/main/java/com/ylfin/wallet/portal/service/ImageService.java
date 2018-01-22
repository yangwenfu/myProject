package com.ylfin.wallet.portal.service;

import java.io.InputStream;

public interface ImageService {

	/**
	 * 上传临时图片
	 * @param inputStream
	 * @param extension
	 * @return
	 */
	String uploadTmpImg(InputStream inputStream, String extension);

	/**
	 * 上传临时图片
	 * @param bytes
	 * @param extension
	 * @return
	 */
	String uploadTmpImg(byte[] bytes, String extension);

	/**
	 * 保存用户身份证正面照片
	 * @param tmpPath
	 * @param userId
	 */
	ImageInfo saveIdCardFrontImg(String tmpPath, String userId);

	/**
	 * 保存用户身份证反面照片
	 * @param tmpPath
	 * @param userId
	 */
	ImageInfo saveIdCardBackImg(String tmpPath, String userId);

	/**
	 * 保存手持身份证图片
	 * @param tmpPath
	 * @param userId
	 * @return
	 */
	ImageInfo saveIdCardHeldImg(String tmpPath, String userId);
	/**
	 * 保存烟草证照片
	 * @param tmpPath
	 * @param storeId
	 */
	ImageInfo saveTobaccoLicenseImg(String tmpPath, String storeId);

	/**
	 * 保存户口本主页照片
	 * @param tmpPath
	 * @param userId
	 */
	ImageInfo saveHouseholdRegisterMainImg(String tmpPath, String userId);

	/**
	 * 保存户口本户主叶照片
	 * @param tmpPath
	 * @param userId
	 */
	ImageInfo saveHouseholdRegisterOwnerPic(String tmpPath, String userId);

	/**
	 * 保存许可证图片
	 * @param tmpPath
	 * @param storeId
	 * @return
	 */
	ImageInfo saveStoreLicencPic(String tmpPath, String storeId);
}
