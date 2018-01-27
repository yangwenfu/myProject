package com.xinyunlian.jinfu.store.controller;

import com.xinyunlian.jinfu.bank.dto.BankCardDto;
import com.xinyunlian.jinfu.bank.service.BankService;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.fileStore.FileStoreService;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.util.EncryptUtil;
import com.xinyunlian.jinfu.common.util.MessageUtil;
import com.xinyunlian.jinfu.common.util.RandomUtil;
import com.xinyunlian.jinfu.common.yitu.YituService;
import com.xinyunlian.jinfu.common.yitu.dto.resp.CertificationDto;
import com.xinyunlian.jinfu.dealer.enums.EDealerUserLogType;
import com.xinyunlian.jinfu.dealer.service.DealerService;
import com.xinyunlian.jinfu.dealer.service.DealerUserLogService;
import com.xinyunlian.jinfu.dto.UserRealAuthDto;
import com.xinyunlian.jinfu.picture.service.PictureService;
import com.xinyunlian.jinfu.service.UserRealAuthService;
import com.xinyunlian.jinfu.store.dto.req.CertifyDto;
import com.xinyunlian.jinfu.store.dto.req.OCRReqDto;
import com.xinyunlian.jinfu.store.dto.req.RegisterDto;
import com.xinyunlian.jinfu.store.dto.resp.OCRRespDto;
import com.xinyunlian.jinfu.store.service.CustomerUserService;
import com.xinyunlian.jinfu.user.dto.DealerUserDto;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.dto.req.CertifyInfoDto;
import com.xinyunlian.jinfu.user.dto.req.IDCardDto;
import com.xinyunlian.jinfu.user.enums.ESmsSendType;
import com.xinyunlian.jinfu.user.enums.ESource;
import com.xinyunlian.jinfu.user.service.DealerUserService;
import com.xinyunlian.jinfu.user.service.SmsService;
import com.xinyunlian.jinfu.user.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

/**
 * Created by menglei on 2016年08月30日.
 */
@Controller
@RequestMapping(value = "store/user")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    /**
     * 正则表达式：验证身份证
     */
    public static final String REGEX_ID_CARD = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X|x)$";

    @Autowired
    private UserService userService;
    @Autowired
    private SmsService smsService;
    @Autowired
    private DealerUserLogService dealerUserLogService;
    @Autowired
    private CustomerUserService customerUserService;
    @Autowired
    private UserRealAuthService userRealAuthService;
    @Autowired
    private BankService bankService;
    @Autowired
    private FileStoreService fileStoreService;
    @Autowired
    private PictureService pictureService;
    @Autowired
    private YituService yituService;
    @Autowired
    private DealerUserService dealerUserService;
    @Autowired
    private DealerService dealerService;

    /**
     * 获取短信验证码
     *
     * @param mobile
     * @param type   register=普通,forget=忘记密码 bankcard=银行卡
     * @return
     */
    @RequestMapping(value = "/mobile/code/get", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getVerifyCode(@RequestParam String mobile, @RequestParam String type) {
        String verifyCode = "";
        if (type.equals("register")) {
            verifyCode = smsService.getVerifyCode(mobile, ESmsSendType.REGISTER);
        } else if (type.equals("forget")) {
            verifyCode = smsService.getVerifyCode(mobile, ESmsSendType.FORGET);
        } else if (type.equals("bankcard")) {
            verifyCode = smsService.getVerifyCode(mobile, ESmsSendType.BANKCARD);
        }
        System.out.println(verifyCode);
        return ResultDtoFactory.toAck("验证码发送成功");
    }

    /**
     * 验证普通短信验证码
     *
     * @param mobile
     * @param verifyCode
     * @return
     */
    @RequestMapping(value = "/mobile/confirm", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> confirmMobile(@RequestParam String mobile, @RequestParam String verifyCode) {
        Boolean flag = smsService.confirmVerifyCode(mobile, verifyCode, ESmsSendType.REGISTER);
        if (!flag) {
            return ResultDtoFactory.toNack("验证码错误");
        }
        UserInfoDto userInfoDto = userService.findUserByMobile(mobile);
        return ResultDtoFactory.toAck("验证码正确", userInfoDto);
    }

    /**
     * 商户注册
     *
     * @param registerDto
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> register(@RequestBody RegisterDto registerDto) {
        DealerUserDto dealerUserDto = (DealerUserDto) SecurityContext.getCurrentUser().getParamMap().get("dealerUserDto");

        if (!Pattern.matches(REGEX_ID_CARD, registerDto.getIdCardNo())) {
            return ResultDtoFactory.toNack("身份证格式不正确");
        }
        UserInfoDto userInfo = userService.findUserByMobile(registerDto.getMobile());
        if (userInfo != null) {
            return ResultDtoFactory.toNack("该手机号已注册");
        }
        Boolean flag = smsService.confirmVerifyCode(registerDto.getMobile(), registerDto.getVerifyCode(), ESmsSendType.REGISTER);
        if (!flag) {
            return ResultDtoFactory.toNack("验证码错误");
        }

        if (StringUtils.isEmpty(registerDto.getIdCardBackBase64()) || StringUtils.isEmpty(registerDto.getIdCardFrontBase64())) {
            return ResultDtoFactory.toNack("身份证照片必传");
        }
        if (StringUtils.isEmpty(registerDto.getUserName()) || StringUtils.isEmpty(registerDto.getIdCardNo())) {
            return ResultDtoFactory.toNack("姓名和身份证号必填");
        }

        //实名认证
        CertificationDto certificationDto = customerUserService.realAuth(registerDto.getUserName()
                , registerDto.getIdCardNo(),registerDto.getIdCardFrontBase64());

        String encryptPwd = StringUtils.EMPTY;
        try {
            encryptPwd = EncryptUtil.encryptMd5(RandomUtil.getNumberStr(6));//初始密码随机6位数字
        } catch (Exception e) {
            return ResultDtoFactory.toNack("注册失败");
        }
        registerDto.setLoginPassword(encryptPwd);
        UserInfoDto userInfoDto = ConverterService.convert(registerDto, UserInfoDto.class);
        userInfoDto.setSource(ESource.DEALER);
        UserInfoDto user = userService.saveUser(userInfoDto);

        CertifyInfoDto certifyInfoDto = ConverterService.convert(user, CertifyInfoDto.class);
        userService.updateIdentityAuth(certifyInfoDto);
        user.setIdentityAuth(true);

      /*  if (certificationDto.getOriginImageBase64() != null) {
            try {
                PictureDto pictureDto = new PictureDto();
                File idCardPic1File = ImageUtils.GenerateImageByBase64(certificationDto.getOriginImageBase64());
                String picPath =
                        fileStoreService.upload(EFileType.USER_INFO_IMG, idCardPic1File, idCardPic1File.getName());
                pictureDto.setPictureType(EPictureType.LINE);
                pictureDto.setPicturePath(picPath);
                pictureDto.setParentId(user.getUserId());
                pictureService.savePicture(pictureDto);
            } catch (IOException e) {
                LOGGER.error("身份证正面图片上传失败", e);
                throw new BizServiceException(EErrorCode.USER_IDENTITY_AUTH_ERROR);
            }
        }*/

        CertifyDto certifyDto = new CertifyDto();
        certifyDto.setUserId(user.getUserId());
        certifyDto.setUserName(registerDto.getUserName());
        certifyDto.setIdCardNo(registerDto.getIdCardNo());
        certifyDto.setIdCardPic1Base64(registerDto.getIdCardFrontBase64());
        certifyDto.setIdCardPic2Base64(registerDto.getIdCardBackBase64());

        IDCardDto idCardDto = customerUserService.getIDCardByOCR(certifyDto);

        if (StringUtils.isEmpty(idCardDto.getCardFrontFilePath()) || StringUtils.isEmpty(idCardDto.getCardBackFilePath())) {
            return ResultDtoFactory.toNack("身份证上传失败");
        }

        userService.saveIDCard(idCardDto);

        //清除验证码
        smsService.clearVerifyCode(registerDto.getMobile(), ESmsSendType.REGISTER);

        //插入分销员操作日志
        dealerUserLogService.createDealerUserLog(dealerUserDto, registerDto.getLogLng(), registerDto.getLogLat(), registerDto.getLogAddress(), user.getUserId(), null, "商户注册", EDealerUserLogType.REGISTER);
        return ResultDtoFactory.toAck("注册成功", user);
    }

    /**
     * 根据手机号获取用户信息
     *
     * @param mobile
     * @return
     */
    @RequestMapping(value = "/getByMoible", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getByMoible(@RequestParam String mobile) {
        UserInfoDto userInfoDto = userService.findUserByMobile(mobile);
        if (userInfoDto == null) {
            return ResultDtoFactory.toNack("用户不存在");
        }
        return ResultDtoFactory.toAck("获取成功", userInfoDto);
    }

    /**
     * 实名认证
     *
     * @param certifyDto
     * @return
     */
    @RequestMapping(value = "/certify", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> certify(@RequestBody CertifyDto certifyDto) {
        DealerUserDto dealerUserDto = (DealerUserDto) SecurityContext.getCurrentUser().getParamMap().get("dealerUserDto");
        CertifyInfoDto certifyInfoDto = customerUserService.certify(certifyDto);
        //插入分销员操作日志
        dealerUserLogService.createDealerUserLog(dealerUserDto, certifyDto.getLogLng(), certifyDto.getLogLat(), certifyDto.getLogAddress(), certifyInfoDto.getUserId(), null, "实名认证", EDealerUserLogType.CERTIFY);
        return ResultDtoFactory.toAck("实名认证成功");
    }

    /**
     * 新实名认证
     * @param certifyDto
     * @return
     */
    @RequestMapping(value = "/v2/certify", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> certifyV2(@RequestBody CertifyDto certifyDto) {
        DealerUserDto dealerUserDto = (DealerUserDto) SecurityContext.getCurrentUser().getParamMap().get("dealerUserDto");
        CertifyInfoDto certifyInfoDto = customerUserService.certifyV2(certifyDto);
        //插入分销员操作日志
        dealerUserLogService.createDealerUserLog(dealerUserDto, certifyDto.getLogLng(), certifyDto.getLogLat(), certifyDto.getLogAddress(), certifyInfoDto.getUserId(), null, "实名认证", EDealerUserLogType.CERTIFY);
        return ResultDtoFactory.toAck("实名认证成功");
    }

    /**
     * 实名认证V3 带身份证正反面
     * @param certifyDto
     * @return
     */
    @RequestMapping(value = "/certify/v3", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> certifyV3(@RequestBody CertifyDto certifyDto) {
        DealerUserDto dealerUserDto = (DealerUserDto) SecurityContext.getCurrentUser().getParamMap().get("dealerUserDto");

        if (StringUtils.isEmpty(certifyDto.getIdCardPic1Base64()) || StringUtils.isEmpty(certifyDto.getIdCardPic2Base64())) {
            return ResultDtoFactory.toNack("身份证照片必传");
        }
        if (StringUtils.isEmpty(certifyDto.getUserName()) || StringUtils.isEmpty(certifyDto.getIdCardNo())) {
            return ResultDtoFactory.toNack("姓名和身份证号必填");
        }
        UserInfoDto userInfoDto = userService.findUserByUserId(certifyDto.getUserId());
        if (userInfoDto == null) {
            return ResultDtoFactory.toNack("用户不存在");
        }
        if (userInfoDto.getIdentityAuth()) {
            return ResultDtoFactory.toNack("实名认证已通过");
        }

        IDCardDto idCardDto = customerUserService.getIDCardByOCR(certifyDto);

        if (StringUtils.isEmpty(idCardDto.getCardFrontFilePath()) || StringUtils.isEmpty(idCardDto.getCardBackFilePath())) {
            return ResultDtoFactory.toNack("身份证上传失败");
        }

        boolean flag = userService.certifyWithCard(idCardDto);
        if (!flag) {
            return ResultDtoFactory.toNack("实名认证失败");
        }

        //插入分销员操作日志
        dealerUserLogService.createDealerUserLog(dealerUserDto, certifyDto.getLogLng(), certifyDto.getLogLat(), certifyDto.getLogAddress(), certifyDto.getUserId(), null, "实名认证", EDealerUserLogType.CERTIFY);
        return ResultDtoFactory.toAck("实名认证成功");
    }

    /**
     * 添加银行卡
     * @param certifyDto
     * @return
     */
    @RequestMapping(value = "/saveBankCard", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> saveBankCard(@RequestBody CertifyDto certifyDto) {
        DealerUserDto dealerUserDto = (DealerUserDto) SecurityContext.getCurrentUser().getParamMap().get("dealerUserDto");

        BankCardDto bankCardDto = ConverterService.convert(certifyDto, BankCardDto.class);
        bankCardDto.setBankCardName(certifyDto.getUserName());
        bankCardDto.setMobileNo(certifyDto.getMobile());

        if (StringUtils.isNotEmpty(bankCardDto.getVerifyCode())) {
            boolean flag = smsService.confirmVerifyCode(bankCardDto.getMobileNo(), bankCardDto.getVerifyCode(), ESmsSendType.BANKCARD);
            if(!flag){
                return ResultDtoFactory.toNack("验证码错误");
            }
        }

        UserRealAuthDto userRealAuthDto = new UserRealAuthDto();
        userRealAuthDto.setIdCardNo(bankCardDto.getIdCardNo());
        userRealAuthDto.setBankCardNo(bankCardDto.getBankCardNo());
        userRealAuthDto.setName(bankCardDto.getBankCardName());
        userRealAuthDto.setPhone(bankCardDto.getMobileNo());
        boolean real = userRealAuthService.realAuth(userRealAuthDto);
        if(real == false){
            return ResultDtoFactory.toNack("添加银行卡失败");
        }
        bankService.saveBankCard(bankCardDto);
        smsService.clearVerifyCode(bankCardDto.getMobileNo(), ESmsSendType.BANKCARD);

        //插入分销员操作日志
        dealerUserLogService.createDealerUserLog(dealerUserDto, certifyDto.getLogLng(), certifyDto.getLogLat(), certifyDto.getLogAddress(), certifyDto.getUserId(), null, "银行卡添加", EDealerUserLogType.ADDCARD);

        return ResultDtoFactory.toAck("添加成功");
    }

    /**
     * 身份证OCR接口
     *
     * @return
     */
    @RequestMapping(value = "/ocr", method = RequestMethod.POST)
    @ResponseBody
    public Object ocr(@RequestBody OCRReqDto data) {
        try {
            com.xinyunlian.jinfu.common.yitu.dto.resp.OCRDto ocrResult = yituService.ocr(data.getImageContent());

            OCRRespDto ocrRespDto = new OCRRespDto();
            ocrRespDto.setName(ocrResult.getName());
            ocrRespDto.setIdCard(ocrResult.getIdCard());

            return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), ocrRespDto);

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return ResultDtoFactory.toNack(MessageUtil.getMessage("loan.user.ocr.error"));
        }
    }


}
