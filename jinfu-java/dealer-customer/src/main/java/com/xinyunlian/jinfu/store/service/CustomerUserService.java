package com.xinyunlian.jinfu.store.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.fileStore.FileStoreService;
import com.xinyunlian.jinfu.common.fileStore.enums.EFileType;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.common.util.ImageUtils;
import com.xinyunlian.jinfu.common.yitu.YituService;
import com.xinyunlian.jinfu.common.yitu.dto.resp.CertificationDto;
import com.xinyunlian.jinfu.dto.UserRealAuthDto;
import com.xinyunlian.jinfu.picture.dto.PictureDto;
import com.xinyunlian.jinfu.picture.enums.EPictureType;
import com.xinyunlian.jinfu.picture.service.PictureService;
import com.xinyunlian.jinfu.service.UserRealAuthService;
import com.xinyunlian.jinfu.store.dto.req.CertifyDto;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.dto.req.CertifyInfoDto;
import com.xinyunlian.jinfu.user.dto.req.IDCardDto;
import com.xinyunlian.jinfu.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

import static com.xinyunlian.jinfu.common.util.DateHelper.SIMPLE_DATE_YMD;

/**
 * Created by menglei on 2016年09月22日.
 */
@Service
public class CustomerUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerUserService.class);

    @Autowired
    private UserService userService;
    @Autowired
    private FileStoreService fileStoreService;
    @Autowired
    private PictureService pictureService;
    @Autowired
    private UserRealAuthService userRealAuthService;

    @Autowired
    private YituService yituService;

    /**
     * 实名认证
     * @param certifyDto
     * @return
     */
    public CertifyInfoDto certify(CertifyDto certifyDto) throws BizServiceException {
        UserInfoDto userInfoDto = userService.findUserByUserId(certifyDto.getUserId());
        if (userInfoDto == null) {
            throw new BizServiceException(EErrorCode.USER_NOT_EXIST);
        }
        if (userInfoDto.getIdentityAuth()) {
            throw new BizServiceException(EErrorCode.USER_IDENTITY_AUTH_IS_OK);
        }
        CertifyInfoDto certifyInfoDto = ConverterService.convert(certifyDto, CertifyInfoDto.class);
        UserRealAuthDto userRealAuthDto = new UserRealAuthDto();
        userRealAuthDto.setIdCardNo(certifyInfoDto.getIdCardNo());
        userRealAuthDto.setBankCardNo(certifyInfoDto.getBankCardNo());
        userRealAuthDto.setName(certifyInfoDto.getUserName());
        userRealAuthDto.setPhone(certifyInfoDto.getMobile());
        boolean real = userRealAuthService.realAuth(userRealAuthDto);
        if (real == false) {
            throw new BizServiceException(EErrorCode.USER_IDENTITY_AUTH_ERROR);
        }
        PictureDto pictureDto = new PictureDto();
        pictureDto.setParentId(certifyDto.getUserId());
        if (certifyDto.getIdCardPic1Base64() != null) {
            try {
                File idCardPic1File = ImageUtils.GenerateImageByBase64(certifyDto.getIdCardPic1Base64());
                String picPath =
                        fileStoreService.upload(EFileType.USER_INFO_IMG, idCardPic1File, idCardPic1File.getName());
                pictureDto.setPictureType(EPictureType.CARD_FRONT);
                pictureDto.setPicturePath(picPath);
                pictureService.savePicture(pictureDto);
            } catch (IOException e) {
                LOGGER.error("身份证正面图片上传失败", e);
                throw new BizServiceException(EErrorCode.USER_IDENTITY_AUTH_ERROR);
            }
        }
        if (certifyDto.getIdCardPic2Base64() != null) {
            try {
                File idCardPic2File = ImageUtils.GenerateImageByBase64(certifyDto.getIdCardPic2Base64());
                String picPath =
                        fileStoreService.upload(EFileType.USER_INFO_IMG, idCardPic2File, idCardPic2File.getName());
                pictureDto.setPictureType(EPictureType.CARD_BACK);
                pictureDto.setPicturePath(picPath);
                pictureService.savePicture(pictureDto);
            } catch (IOException e) {
                LOGGER.error("身份证反面图片上传失败", e);
                throw new BizServiceException(EErrorCode.USER_IDENTITY_AUTH_ERROR);
            }
        }
        userService.certify(certifyInfoDto);
        return certifyInfoDto;
    }

    public CertificationDto realAuth(String name,String idCardNo,String frontImage){
        try {
            CertificationDto certificationDto = yituService.certification(name, idCardNo,frontImage);
            if(certificationDto.isValid() == false){
                throw new BizServiceException(EErrorCode.USER_IDENTITY_AUTH_ERROR);
            }
            return certificationDto;
        } catch (Exception e) {
            LOGGER.error("实名认证失败", e);
            throw new BizServiceException(EErrorCode.USER_IDENTITY_AUTH_ERROR);
        }
    }

    public CertifyInfoDto certifyV2(CertifyDto certifyDto) throws BizServiceException {
        UserInfoDto userInfoDto = userService.findUserByUserId(certifyDto.getUserId());
        if (userInfoDto == null) {
            throw new BizServiceException(EErrorCode.USER_NOT_EXIST);
        }
        if (userInfoDto.getIdentityAuth()) {
            throw new BizServiceException(EErrorCode.USER_IDENTITY_AUTH_IS_OK);
        }

        CertifyInfoDto certifyInfoDto = ConverterService.convert(certifyDto, CertifyInfoDto.class);
        userService.certify(certifyInfoDto);
        return certifyInfoDto;
    }

    public IDCardDto getIDCardByOCR(CertifyDto certifyDto){
        //身份证上传
        String idCardPic1Path = null;
        String idCardPic2Path = null;

        IDCardDto idCardDto = new IDCardDto();
        idCardDto.setUserId(certifyDto.getUserId());
        idCardDto.setUserName(certifyDto.getUserName());
        idCardDto.setIdCardNo(certifyDto.getIdCardNo());

         try {
            File idCardPic1File = ImageUtils.GenerateImageByBase64(certifyDto.getIdCardPic1Base64());
            idCardPic1Path = fileStoreService.upload(EFileType.USER_INFO_IMG, idCardPic1File, idCardPic1File.getName());

            com.xinyunlian.jinfu.common.yitu.dto.resp.OCRDto ocrDto = yituService.ocr(certifyDto.getIdCardPic1Base64(),1);
            idCardDto.setNationAddress(ocrDto.getAddress());
            idCardDto.setSex(ocrDto.getGender());
            idCardDto.setNation(ocrDto.getNation());
            idCardDto.setBirthDate(DateHelper.getDate(ocrDto.getBirthday(),"yyyy.MM.dd"));
            idCardDto.setCardFrontFilePath(idCardPic1Path);
        } catch (IOException e) {
            LOGGER.error("身份证正面图片上传失败", e);
        } catch (Exception e) {
            LOGGER.error("读取身份证正面ocr错误",e);
        }
            try {
            File idCardPic2File = ImageUtils.GenerateImageByBase64(certifyDto.getIdCardPic2Base64());
            idCardPic2Path = fileStoreService.upload(EFileType.USER_INFO_IMG, idCardPic2File, idCardPic2File.getName());

            com.xinyunlian.jinfu.common.yitu.dto.resp.OCRDto ocrDto = yituService.ocr(certifyDto.getIdCardPic2Base64(),2);
            idCardDto.setIdAuthority(ocrDto.getAgency());
            idCardDto.setIdExpiredBegin(DateHelper.getDate(ocrDto.getValidDateBegin(),SIMPLE_DATE_YMD));
            idCardDto.setIdExpiredEnd(DateHelper.getDate(ocrDto.getValidDateEnd(),SIMPLE_DATE_YMD));
            idCardDto.setCardBackFilePath(idCardPic2Path);
        } catch (IOException e) {
            LOGGER.error("身份证反面图片上传失败", e);
        } catch (Exception e) {
            LOGGER.error("读取身份证反面ocr错误",e);
        }
        return idCardDto;
    }

}
