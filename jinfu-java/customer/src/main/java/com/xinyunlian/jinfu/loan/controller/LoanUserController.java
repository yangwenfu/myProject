package com.xinyunlian.jinfu.loan.controller;

import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.fileStore.FileStoreService;
import com.xinyunlian.jinfu.common.fileStore.enums.EFileType;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.security.StaticResourceSecurity;
import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.common.util.ImageUtils;
import com.xinyunlian.jinfu.common.util.MessageUtil;
import com.xinyunlian.jinfu.common.yitu.YituService;
import com.xinyunlian.jinfu.common.yitu.dto.req.VerifyReqDto;
import com.xinyunlian.jinfu.common.yitu.helper.FileHelper;
import com.xinyunlian.jinfu.loan.dto.user.CertificationDto;
import com.xinyunlian.jinfu.loan.dto.user.OCRDto;
import com.xinyunlian.jinfu.loan.dto.user.VerifyDto;
import com.xinyunlian.jinfu.picture.dto.PictureDto;
import com.xinyunlian.jinfu.picture.enums.EPictureType;
import com.xinyunlian.jinfu.picture.service.PictureService;
import com.xinyunlian.jinfu.user.dto.UserExtDto;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.dto.req.CertifyInfoDto;
import com.xinyunlian.jinfu.user.service.UserExtService;
import com.xinyunlian.jinfu.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.util.List;

/**
 * @author willwang
 */
@Controller
@RequestMapping(value = "loan/user")
public class LoanUserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoanUserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private PictureService pictureService;

    @Autowired
    private UserExtService userExtService;

    @Autowired
    private FileStoreService fileStoreService;

    @Autowired
    private YituService yituService;

    /**
     * 身份证OCR接口
     *
     * @return
     */
    @RequestMapping(value = "/ocr", method = RequestMethod.POST)
    @ResponseBody
    public Object ocr(@RequestBody OCRDto data) {
        try {
            com.xinyunlian.jinfu.common.yitu.dto.resp.OCRDto ocrResult = yituService.ocr(data.getImageContent());

            CertificationDto certificationDto = new CertificationDto();
            certificationDto.setName(ocrResult.getName());
            certificationDto.setIdCard(ocrResult.getIdCard());

            return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), certificationDto);

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return ResultDtoFactory.toNack(MessageUtil.getMessage("loan.user.ocr.error"));
        }
    }

    /**
     * 实名认证接口
     */
    @RequestMapping(value = "/certification", method = RequestMethod.POST)
    @ResponseBody
    public Object certification(@RequestBody CertificationDto data) {
        try {
            String userId = SecurityContext.getCurrentUserId();

            CertifyInfoDto certifyInfoDto = new CertifyInfoDto();

            certifyInfoDto.setUserId(userId);
            certifyInfoDto.setUserName(data.getName());
            certifyInfoDto.setIdCardNo(data.getIdCard());

            boolean rs = userService.certify(certifyInfoDto);

            if (rs) {
                return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
            } else {
                return ResultDtoFactory.toNack(MessageUtil.getMessage("loan.user.certification.error"));
            }

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return ResultDtoFactory.toNack(MessageUtil.getMessage("loan.user.certification.error"));
        }
    }

    /**
     * 大礼包比对接口
     */
    @RequestMapping(value = "/verify", method = RequestMethod.POST)
    @ResponseBody
    public Object verify(@RequestBody VerifyDto data) {
        try {
            String userId = SecurityContext.getCurrentUserId();

            UserInfoDto userInfoDto = userService.findUserByUserId(userId);

            VerifyReqDto verifyReqDto = new VerifyReqDto();

            //构造对比请求
            verifyReqDto.setName(userInfoDto.getUserName());
            verifyReqDto.setIdCard(userInfoDto.getIdCardNo());
            verifyReqDto.setImagePacket(data.getVerificationPackage());

            com.xinyunlian.jinfu.common.yitu.dto.resp.CertificationDto certificationDto = yituService.verify(verifyReqDto);

            if (certificationDto.getRtn() != 0) {
                return ResultDtoFactory.toNack(MessageUtil.getMessage("loan.user.verify.error"));
            }

            List<String> liveImages = certificationDto.getImages();

            if (liveImages.size() <= 0) {
                LOGGER.info(String.format("%s verify error no live image", userId));
                return ResultDtoFactory.toNack(MessageUtil.getMessage("loan.user.verify.error"));
            }

            //保存活体照片
            PictureDto livePictureDto = new PictureDto();
            File image = ImageUtils.GenerateImageByBase64(liveImages.get(0));
            String picPath = fileStoreService.upload(EFileType.USER_INFO_IMG, image, image.getName());
            livePictureDto.setPictureType(EPictureType.LIVE);
            livePictureDto.setPicturePath(picPath);
            livePictureDto.setParentId(userId);
            pictureService.savePicture(livePictureDto);

            //保存大礼包比对的结果
            UserExtDto userExtDto = userExtService.findUserByUserId(userId);
            userExtDto.setUserId(userId);
            userExtDto.setLived(true);
            userExtDto.setYituPass(certificationDto.isPass());
            userExtDto.setYituSimilarity(certificationDto.getFinalVerifyScore());
            userExtService.saveUserExt(userExtDto);

            return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
        } catch (Exception e) {
            LOGGER.warn("user verify error", e);
            return ResultDtoFactory.toNack(MessageUtil.getMessage("loan.user.verify.error"));
        }

    }

}
