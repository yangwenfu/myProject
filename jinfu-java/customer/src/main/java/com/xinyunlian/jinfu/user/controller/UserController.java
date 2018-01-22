package com.xinyunlian.jinfu.user.controller;

import com.xinyunlian.jinfu.bank.dto.BankCardDto;
import com.xinyunlian.jinfu.bank.service.BankService;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.fileStore.FileStoreService;
import com.xinyunlian.jinfu.common.fileStore.enums.EFileType;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.common.util.ImageUtils;
import com.xinyunlian.jinfu.common.util.MessageUtil;
import com.xinyunlian.jinfu.common.yitu.YituService;
import com.xinyunlian.jinfu.common.yitu.helper.FileHelper;
import com.xinyunlian.jinfu.picture.dto.PictureDto;
import com.xinyunlian.jinfu.picture.enums.EPictureType;
import com.xinyunlian.jinfu.picture.service.PictureService;
import com.xinyunlian.jinfu.service.UserRealAuthService;
import com.xinyunlian.jinfu.user.dto.*;
import com.xinyunlian.jinfu.user.dto.req.CertifyInfoDto;
import com.xinyunlian.jinfu.user.dto.req.IDCardDto;
import com.xinyunlian.jinfu.user.dto.req.PasswordDto;
import com.xinyunlian.jinfu.user.enums.ELinkDateType;
import com.xinyunlian.jinfu.user.enums.ESmsSendType;
import com.xinyunlian.jinfu.user.service.SmsService;
import com.xinyunlian.jinfu.user.service.UserExtService;
import com.xinyunlian.jinfu.user.service.UserLinkmanService;
import com.xinyunlian.jinfu.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.xinyunlian.jinfu.common.util.DateHelper.SIMPLE_DATE_YMD;

/**
 * Created by JL on 2016/8/19.
 */
@Controller
@RequestMapping(value = "user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private UserExtService userExtService;

    @Autowired
    private UserLinkmanService userLinkmanService;

    @Autowired
    private FileStoreService fileStoreService;

    @Autowired
    private PictureService pictureService;

    @Autowired
    private UserRealAuthService userRealAuthService;

    @Autowired
    private BankService bankService;

    @Autowired
    private YituService yituService;

//    /**
//     * 通过手机号查询用户详细信息
//     * @param mobile
//     * @return
//     */
//    @RequestMapping(value = "/detail", method = RequestMethod.GET)
//    @ResponseBody
//    public ResultDto<Object> detail(@RequestParam String mobile) {
//        UserInfoDto userInfoDto = userService.findUserByMobile(mobile);
//        if(userInfoDto == null){
//            return ResultDtoFactory.toNack("该用户不存在");
//        }
//        UserDetailDto userDetailDto = userService.findUserDetailByUserId(userInfoDto.getUserId());
//        ResultDto<Object> result = new ResultDto<Object>();
//        result.setData(userDetailDto);
//        result.setCode(ResultCode.ACK);
//        return result;
//    }

    /**
     * 实名认证接口
     *
     * @param certifyInfoDto
     * @return
     */
    @RequestMapping(value = "/certify", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> certify(@RequestBody CertifyInfoDto certifyInfoDto) {
        certifyInfoDto.setUserId(SecurityContext.getCurrentUserId());
        try {
            userService.certify(certifyInfoDto);
        }catch (Exception e) {
            LOGGER.error("实名认证失败", e);
            return ResultDtoFactory.toNack(MessageUtil.getMessage("user.certify.fail"));
        }
        return ResultDtoFactory.toAck(MessageUtil.getMessage("user.certify.success"));
    }

    /**
     * 实名认证接口
     *
     * @param certifyInfoDto
     * @return
     */
    @RequestMapping(value = "/shop/certify", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> shopCertify(@RequestBody CertifyInfoDto certifyInfoDto) {
        certifyInfoDto.setUserId(SecurityContext.getCurrentUserId());
        try {
            userService.certify(certifyInfoDto);
        }catch (Exception e) {
            LOGGER.error("实名认证失败", e);
            return ResultDtoFactory.toNack(MessageUtil.getMessage("user.certify.fail"));
        }
        return ResultDtoFactory.toAck(MessageUtil.getMessage("user.certify.success"));
    }


    /**
     * 获得用户信息-小贷
     *
     * @return
     */
    // FIXME: 2017/2/20 老小贷接口 2.0上了后可删除 by King
    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getUserInfo() {
        throw new BizServiceException(EErrorCode.VERSION_ERROR);

//        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), userExtService.findUserByUserId(SecurityContext.getCurrentUserId()));
    }

    /**
     * 获得用户信息-小贷 20161115版本
     *
     * @return
     */
    // FIXME: 2017/2/20 老小贷接口 2.0上了后可删除 by King
    @RequestMapping(value = "/userInfoV2", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getUserInfoV2() {

        String userId = SecurityContext.getCurrentUserId();

        UserExtDto userExtDto = userExtService.findUserByUserId(userId);

        //取图片信息
        PictureDto linePicture = pictureService.get(userId, EPictureType.LINE);
        PictureDto livePicture = pictureService.get(userId, EPictureType.LIVE);
        if(null != userExtDto.getLived() && null != livePicture){
            userExtDto.setLived(true);
        }else{
            userExtDto.setLived(false);
        }
        if(null != userExtDto.getIdentityAuth() && null != linePicture){
            userExtDto.setIdentityAuth(true);
        }else{
            userExtDto.setIdentityAuth(false);
        }

        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"),
            ConverterService.convert(userExtDto, UserBaseExtDto.class));
    }

    /**
     * 获得用户信息-商城
     *
     * @return
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getUser() {
        UserInfoDto userInfoDto = userService.findUserByUserId(SecurityContext.getCurrentUserId());
       /* userInfoDto.setUserName(MaskUtil.maskFirstName(userInfoDto.getUserName()));
        userInfoDto.setMobile(MaskUtil.maskMiddleValue(3, 4, userInfoDto.getMobile()));
        userInfoDto.setIdCardNo(MaskUtil.maskMiddleValue(3, 2, userInfoDto.getIdCardNo()));*/
       //是否已绑卡
        List<BankCardDto> bankCardList = bankService.findByUserId(SecurityContext.getCurrentUserId());
        if (!bankCardList.isEmpty()) {
            userInfoDto.setBankAuth(true);
        } else {
            userInfoDto.setBankAuth(false);
        }
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"),userInfoDto);
    }

    /**
     * 保存用户信息
     *
     * @param userExtDto
     * @return
     */
    @RequestMapping(value = "/userInfo", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> setUserInfo(@RequestBody UserExtDto userExtDto) {
        userExtDto.setUserId(SecurityContext.getCurrentUserId());
        userExtService.saveUserExt(userExtDto);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }


    /**
     * 获得联系人信息
     *
     * @return
     */
    @RequestMapping(value = "/linkman", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getLinkman() {
        List<UserLinkmanDto> ownLinkmanDtos = new ArrayList<>();
        List<UserLinkmanDto> linkmanDtos = userLinkmanService.findByUserId(SecurityContext.getCurrentUserId());
        if(!CollectionUtils.isEmpty(linkmanDtos)){
            linkmanDtos.forEach(userLinkmanDto -> {
                if(userLinkmanDto.getDateType() == ELinkDateType.OWN){
                    ownLinkmanDtos.add(userLinkmanDto);
                }
            });
        }
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), ownLinkmanDtos);
    }


    /**
     * 保存联系人信息
     *
     * @return
     */
    @RequestMapping(value = "/linkman", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> setLinkman(@RequestBody List<UserLinkmanDto> userLinkmanDtos) {
        for (UserLinkmanDto dto :
                userLinkmanDtos) {
            dto.setUserId(SecurityContext.getCurrentUserId());
        }
        userLinkmanService.saveUserLinkman(userLinkmanDtos);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }


    /**
     * 上传用户图片
     *
     * @param picture
     * @param pictureType
     * @return
     */
    @RequestMapping(value = "/picture", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> uploadPicture(@RequestParam MultipartFile picture, @RequestParam EPictureType pictureType) {
        PictureDto pictureDto = new PictureDto();
        pictureDto.setPictureType(pictureType);
        try {
            String picPath =
                    fileStoreService.upload(EFileType.USER_INFO_IMG, picture.getInputStream(), picture.getOriginalFilename().replace(" ", ""));
            pictureDto.setPicturePath(picPath);
        } catch (IOException e) {
            LOGGER.error("用户图片上传失败", e);
            return ResultDtoFactory.toNack("图片上传失败");
        }
        pictureDto = pictureService.savePicture(pictureDto);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), pictureDto.getPictureId());
    }


    /**
     * 上传用户图片
     *
     * @return
     */
    @RequestMapping(value = "/pictureByBase64", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> uploadPicture(@RequestBody PictureUploadDto uploadDto) {
        PictureDto pictureDto = new PictureDto();
        pictureDto.setPictureType(uploadDto.getPictureType());
        try {
            File idCardPic2File = ImageUtils.GenerateImageByBase64(uploadDto.getPictureBase64());
            String picPath = fileStoreService.upload(EFileType.USER_INFO_IMG, idCardPic2File);
            pictureDto.setPicturePath(picPath);
        } catch (IOException e) {
            LOGGER.error("用户图片上传失败", e);
            return ResultDtoFactory.toNack("图片上传失败");
        }
        pictureDto = pictureService.savePicture(pictureDto);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), pictureDto.getPictureId());
    }

    /**
     * 上传图片
     *
     * @return
     */
    @ApiOperation(value = "图片上传，返回图片相对路径")
    @RequestMapping(value = "/picture/uploadByBase64", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> uploadByBase64(@RequestBody PictureUploadDto uploadDto) {
        try {
            File idCardPic2File = ImageUtils.GenerateImageByBase64(uploadDto.getPictureBase64());
            String picPath = fileStoreService.upload(EFileType.USER_INFO_IMG, idCardPic2File);
            return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), picPath);
        } catch (IOException e) {
            LOGGER.error("用户图片上传失败", e);
            return ResultDtoFactory.toNack("图片上传失败");
        }
    }

    /**
     * 批量上传用户图片
     *
     * @return
     */
    @RequestMapping(value = "/pictures/base64", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> uploadPicture(@RequestBody List<PictureUploadDto> uploadDtos) {
        if(!CollectionUtils.isEmpty(uploadDtos)) {
            List<PictureDto> pictureDtos = new ArrayList<>();
            try {
                for (PictureUploadDto uploadDto : uploadDtos){
                    PictureDto pictureDto = new PictureDto();
                    pictureDto.setParentId(uploadDto.getParentId());
                    pictureDto.setPictureType(uploadDto.getPictureType());
                    File file = ImageUtils.GenerateImageByBase64(uploadDto.getPictureBase64());
                    String picPath = fileStoreService.upload(EFileType.USER_INFO_IMG, file);
                    pictureDto.setPicturePath(picPath);
                    pictureDtos.add(pictureDto);
                }
                pictureService.savePicture(pictureDtos);
            } catch (IOException e) {
                LOGGER.error("图片上传失败", e);
                return ResultDtoFactory.toNack("图片上传失败");
            }
        }

        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }

    /**
     * 登入后获取短信验证码
     *
     * @return
     */
    @RequestMapping(value = "/mobile/code", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getVerifyCode(@RequestParam ESmsSendType type) {
        UserInfoDto userInfoDto = userService.findUserByUserId(SecurityContext.getCurrentUserId());
        smsService.getVerifyCode(userInfoDto.getMobile(), type);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("sms.send.success"));
    }

    /**
     * 忘记密码验证手机短信验证码
     *
     * @param verifyCode
     * @return
     */
    @RequestMapping(value = "/mobile/confirm", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> confirmMobile(@RequestParam String verifyCode,@RequestParam ESmsSendType type) {
        UserInfoDto userInfoDto = userService.findUserByUserId(SecurityContext.getCurrentUserId());
        if (userInfoDto == null) {
            return ResultDtoFactory.toNack(MessageUtil.getMessage("user.not.exist"));
        }
        boolean flag = smsService.confirmVerifyCode(userInfoDto.getMobile(), verifyCode, type);
        if (!flag) {
            return ResultDtoFactory.toNack(MessageUtil.getMessage("sms.code.error"));
        }
        return ResultDtoFactory.toAck(MessageUtil.getMessage("sms.code.right"));
    }

    /**
     * 修改密码
     *
     * @param passwordDto
     * @return
     */
    @RequestMapping(value = "/password/update", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> updatePassword(@RequestBody PasswordDto passwordDto) {
        UserInfoDto userInfoDto = userService.findUserByUserId(SecurityContext.getCurrentUserId());
        if (userInfoDto == null) {
            return ResultDtoFactory.toNack(MessageUtil.getMessage("user.not.exist"));
        }

        boolean flag = smsService.confirmVerifyCode(userInfoDto.getMobile(), passwordDto.getVerifyCode(), ESmsSendType.LOGIN_PWD);
        if (!flag) {
            return ResultDtoFactory.toNack(MessageUtil.getMessage("sms.code.error"));
        }

        passwordDto.setUserId(userInfoDto.getUserId());
        userService.updatePassword(passwordDto);
        smsService.clearVerifyCode(passwordDto.getMobile(), ESmsSendType.LOGIN_PWD);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }

    /**
     * 修改交易密码
     * @param passwordDto
     * @return
     */
    @RequestMapping(value = "/dealPassword/update", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> updateDealPassword(@RequestBody PasswordDto passwordDto) {
        UserInfoDto userInfoDto = userService.findUserByUserId(SecurityContext.getCurrentUserId());
        if (userInfoDto == null) {
            return ResultDtoFactory.toNack(MessageUtil.getMessage("user.not.exist"));
        }

        boolean flag = smsService.confirmVerifyCode(userInfoDto.getMobile(), passwordDto.getVerifyCode(), ESmsSendType.DEAL_PWD);
        if (!flag) {
            return ResultDtoFactory.toNack(MessageUtil.getMessage("sms.code.error"));
        }

        passwordDto.setUserId(userInfoDto.getUserId());
        userService.updateDealPassword(passwordDto);
        smsService.clearVerifyCode(passwordDto.getMobile(), ESmsSendType.DEAL_PWD);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }

    /**
     * 身份证OCR接口
     *
     * @return
     */
    @RequestMapping(value = "/ocr", method = RequestMethod.POST)
    @ResponseBody
    public Object ocr(@RequestParam MultipartFile frontFile,@RequestParam MultipartFile backFile) {
        String frontFilePath;
        String backFilePath;
        try {
            frontFilePath =
                    fileStoreService.upload(EFileType.USER_INFO_IMG, frontFile.getInputStream(), frontFile.getOriginalFilename().replace(" ", ""));

            backFilePath =
                    fileStoreService.upload(EFileType.USER_INFO_IMG, backFile.getInputStream(), backFile.getOriginalFilename().replace(" ", ""));
        } catch (IOException e) {
            LOGGER.error("用户图片上传失败", e);
            return ResultDtoFactory.toNack("图片上传失败");
        }

        try {
            String cardFrontBase64 = FileHelper.getBase64FromInputStream(frontFile.getInputStream());
            com.xinyunlian.jinfu.common.yitu.dto.resp.OCRDto ocrResult = yituService.ocr(cardFrontBase64);

            IDCardInfoDto idCardInfoDto = new IDCardInfoDto();
            idCardInfoDto.setName(ocrResult.getName());
            idCardInfoDto.setIdCard(ocrResult.getIdCard());
            idCardInfoDto.setFrontFilePath(frontFilePath);
            idCardInfoDto.setBackFilePath(backFilePath);

            return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), idCardInfoDto);

        } catch (Exception e) {
            LOGGER.error("OCR读取失败", e);
            return ResultDtoFactory.toNack(MessageUtil.getMessage("loan.user.ocr.error"));
        }

    }

    /**
     * 实名认证 带身份证正反面
     * @param idCardInfoDto
     * @return
     */
    @RequestMapping(value = "/certify/v3", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> certifyV3(@RequestBody IDCardInfoDto idCardInfoDto) {

        if (StringUtils.isEmpty(idCardInfoDto.getFrontFilePath()) || StringUtils.isEmpty(idCardInfoDto.getBackFilePath())) {
            return ResultDtoFactory.toNack("身份证照片必传");
        }
        if (StringUtils.isEmpty(idCardInfoDto.getName()) || StringUtils.isEmpty(idCardInfoDto.getIdCard())) {
            return ResultDtoFactory.toNack("姓名和身份证号必填");
        }
        UserInfoDto userInfoDto = userService.findUserByUserId(SecurityContext.getCurrentUserId());
        if (userInfoDto == null) {
            return ResultDtoFactory.toNack("用户不存在");
        }
        if (userInfoDto.getIdentityAuth()) {
            return ResultDtoFactory.toNack("实名认证已通过");
        }

        IDCardDto idCardDto = new IDCardDto();
        idCardDto.setUserId(userInfoDto.getUserId());
        idCardDto.setUserName(idCardInfoDto.getName());
        idCardDto.setIdCardNo(idCardInfoDto.getIdCard());
        idCardDto.setCardFrontFilePath(idCardInfoDto.getFrontFilePath());
        idCardDto.setCardBackFilePath(idCardInfoDto.getBackFilePath());

        try {
            String frontFilePath = idCardInfoDto.getFrontFilePath();
            File file = fileStoreService.download(frontFilePath.substring(0,frontFilePath.lastIndexOf("/")),
                    frontFilePath.substring(frontFilePath.lastIndexOf("/"),frontFilePath.length()));
            String cardFrontBase64 = FileHelper.getBase64FromInputStream(new FileInputStream(file));

            com.xinyunlian.jinfu.common.yitu.dto.resp.OCRDto ocrDto = yituService.ocr(cardFrontBase64,1);
            idCardDto.setNationAddress(ocrDto.getAddress());
            idCardDto.setSex(ocrDto.getGender());
            idCardDto.setNation(ocrDto.getNation());
            idCardDto.setBirthDate(DateHelper.getDate(ocrDto.getBirthday(),"yyyy.MM.dd"));
        } catch (Exception e) {
            LOGGER.error("读取身份证正面ocr错误",e);
        }

        try {
            String backFilePath = idCardDto.getCardBackFilePath();
            File file = fileStoreService.download(backFilePath.substring(0,backFilePath.lastIndexOf("/")),
                    backFilePath.substring(backFilePath.lastIndexOf("/"),backFilePath.length()));
            String cardBackBase64 = FileHelper.getBase64FromInputStream(new FileInputStream(file));

            com.xinyunlian.jinfu.common.yitu.dto.resp.OCRDto ocrDto = yituService.ocr(cardBackBase64,2);
            idCardDto.setIdAuthority(ocrDto.getAgency());
            idCardDto.setIdExpiredBegin(DateHelper.getDate(ocrDto.getValidDateBegin(),SIMPLE_DATE_YMD));
            idCardDto.setIdExpiredEnd(DateHelper.getDate(ocrDto.getValidDateEnd(),SIMPLE_DATE_YMD));
        } catch (Exception e) {
            LOGGER.error("读取身份证反面ocr错误",e);
        }

        boolean flag = userService.certifyWithCard(idCardDto);
        if (!flag) {
            return ResultDtoFactory.toNack("实名认证失败");
        }

        return ResultDtoFactory.toAck("实名认证成功");
    }

}
