package com.xinyunlian.jinfu.user.controller;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.xinyunlian.jinfu.bank.dto.BankCardDto;
import com.xinyunlian.jinfu.bank.service.BankService;
import com.xinyunlian.jinfu.clerk.dto.ClerkAuthDto;
import com.xinyunlian.jinfu.clerk.dto.ClerkInfDto;
import com.xinyunlian.jinfu.clerk.service.ClerkAuthService;
import com.xinyunlian.jinfu.clerk.service.ClerkService;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.fileStore.FileStoreService;
import com.xinyunlian.jinfu.common.fileStore.enums.EFileType;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.util.*;
import com.xinyunlian.jinfu.common.yitu.YituService;
import com.xinyunlian.jinfu.industry.dto.IndustryDto;
import com.xinyunlian.jinfu.industry.service.IndustryService;
import com.xinyunlian.jinfu.picture.dto.PictureDto;
import com.xinyunlian.jinfu.picture.enums.EPictureType;
import com.xinyunlian.jinfu.picture.service.PictureService;
import com.xinyunlian.jinfu.store.dto.FranchiseDto;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.store.service.FranchiseService;
import com.xinyunlian.jinfu.store.service.StoreService;
import com.xinyunlian.jinfu.user.dto.*;
import com.xinyunlian.jinfu.user.dto.req.CertifyInfoDto;
import com.xinyunlian.jinfu.user.dto.req.IDCardDto;
import com.xinyunlian.jinfu.user.dto.resp.UserDetailDto;
import com.xinyunlian.jinfu.user.enums.ELabelType;
import com.xinyunlian.jinfu.user.enums.ESmsSendType;
import com.xinyunlian.jinfu.user.enums.ESource;
import com.xinyunlian.jinfu.user.service.DealerUserService;
import com.xinyunlian.jinfu.user.service.SmsService;
import com.xinyunlian.jinfu.user.service.UserService;
import com.xinyunlian.jinfu.yunma.dto.YMMemberDto;
import com.xinyunlian.jinfu.yunma.dto.YMUserInfoDto;
import com.xinyunlian.jinfu.yunma.dto.YmUserDetailDto;
import com.xinyunlian.jinfu.yunma.enums.EMemberAuditStatus;
import com.xinyunlian.jinfu.yunma.enums.EUserRoleType;
import com.xinyunlian.jinfu.yunma.service.YMMemberService;
import com.xinyunlian.jinfu.yunma.service.YMUserInfoService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by menglei on 2017/01/04.
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private SmsService smsService;
    @Autowired
    private YMUserInfoService yMUserInfoService;
    @Autowired
    private YMMemberService yMMemberService;
    @Autowired
    private BankService bankService;
    @Autowired
    private ClerkService clerkService;
    @Autowired
    private ClerkAuthService clerkAuthService;
    @Autowired
    private YituService yituService;
    @Autowired
    private FileStoreService fileStoreService;
    @Autowired
    private PictureService pictureService;
    @Autowired
    private FranchiseService franchiseService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private IndustryService industryService;
    @Autowired
    private DealerUserService dealerUserService;

//    /**
//     * 验证手机号
//     *
//     * @param mobileDto
//     * @return
//     */
//    @RequestMapping(value = "/mobile/verify", method = RequestMethod.GET)
//    @ResponseBody
//    public ResultDto<Object> verifyMobile(@RequestBody MobileDto mobileDto) {
//        UserInfoDto userInfoDto = userService.findUserByMobile(mobileDto.getMobile());
//        if (userInfoDto == null) {
//            return ResultDtoFactory.toNack("手机号不存在");
//        }
//        YMUserInfoDto yMUserInfoDto = yMUserInfoService.findByUserId(userInfoDto.getUserId());
//        if (yMUserInfoDto != null) {
//            return ResultDtoFactory.toNack("该手机号已绑定");
//        }
//        return ResultDtoFactory.toAck("手机验证通过");
//    }

    /**
     * 获取短信验证码
     *
     * @param mobileDto codeType:wechat,bankcard
     * @return
     */
    @RequestMapping(value = "/mobile/getCode", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getVerifyCode(MobileDto mobileDto) {
        String verifyCode;
        if (StringUtils.equals(mobileDto.getCodeType(), "wechat")) {
            verifyCode = smsService.getVerifyCode(mobileDto.getMobile(), ESmsSendType.WECHAT_BIND);
        } else if (StringUtils.equals(mobileDto.getCodeType(), "bankcard")) {
            verifyCode = smsService.getVerifyCode(mobileDto.getMobile(), ESmsSendType.BANKCARD);
        } else if (StringUtils.equals(mobileDto.getCodeType(), "register")) {
            verifyCode = smsService.getVerifyCode(mobileDto.getMobile(), ESmsSendType.REGISTER);
        } else {
            return ResultDtoFactory.toNack("参数不正确");
        }
        System.out.println(verifyCode);
        return ResultDtoFactory.toAck("短信发送成功");
    }

    /**
     * 绑定微信 步骤1
     *
     * @param mobileDto
     * @return
     */
    @RequestMapping(value = "/bindWeChat/confirmVerifyCode", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> confirmVerifyCode(@RequestBody MobileDto mobileDto) {
        boolean flag = smsService.confirmVerifyCode(mobileDto.getMobile(), mobileDto.getVerifyCode(), ESmsSendType.WECHAT_BIND);
        if (!flag) {
            return ResultDtoFactory.toNack("短信验证错误");
        }
        UserInfoDto userInfoDto = userService.findUserByMobile(mobileDto.getMobile());
        if (userInfoDto == null) {
            return ResultDtoFactory.toNack("用户不存在");
        }
        YMUserInfoDto yMUserInfoDto = yMUserInfoService.findByUserId(userInfoDto.getUserId());
        if (yMUserInfoDto != null) {
            return ResultDtoFactory.toNack("该手机号已绑定");
        }
        //清除验证码
        smsService.clearVerifyCode(mobileDto.getMobile(), ESmsSendType.WECHAT_BIND);
        return ResultDtoFactory.toAck("验证成功", userInfoDto);
    }

    /**
     * 绑定微信 步骤2
     *
     * @param mobileDto
     * @return
     */
    @RequestMapping(value = "/bindWeChat", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> bindWeChat(@RequestBody MobileDto mobileDto) {
        UserInfoDto userInfoDto = userService.findUserByMobile(mobileDto.getMobile());
        if (userInfoDto == null) {
            return ResultDtoFactory.toNack("手机号不存在");
        }
        YMUserInfoDto yMUserInfoDto = yMUserInfoService.findByUserId(userInfoDto.getUserId());
        if (yMUserInfoDto != null) {
            return ResultDtoFactory.toNack("该手机号已绑定");
        }
        YMUserInfoDto dto = yMUserInfoService.findByYmUserId(SecurityContext.getCurrentUserId());
        if (dto == null) {
            SecurityContext.logout();
            return ResultDtoFactory.toNack("非法操作");
        }
        dto.setUserId(userInfoDto.getUserId());
        yMUserInfoService.updateUserInfo(dto);
        return ResultDtoFactory.toAck("绑定成功");
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getDetail() {
        YMUserInfoDto yMUserInfoDto = yMUserInfoService.findByYmUserId(SecurityContext.getCurrentUserId());
        if (yMUserInfoDto == null || StringUtils.isEmpty(yMUserInfoDto.getUserId())) {
            return ResultDtoFactory.toAck("获取成功", null);//用户没有绑定微信，需先绑定微信
        }
        UserDetailDto userDetailDto = userService.findUserDetailByUserId(yMUserInfoDto.getUserId());
        List<YMMemberDto> members = yMMemberService.getMemberListByUserId(yMUserInfoDto.getUserId());//已绑云码店铺
        Map<Long, YMMemberDto> memberMap = new HashMap<>();
        for (YMMemberDto dto : members) {
            memberMap.put(dto.getStoreId(), dto);
        }
        Map<Long, BankCardDto> bankCardMap = new HashMap<>();
        for (BankCardDto dto : userDetailDto.getBankCardPoList()) {
            bankCardMap.put(dto.getBankCardId(), dto);
        }
        List<StoreInfDto> storeInfPoList = new ArrayList<>();
        for (StoreInfDto dto : userDetailDto.getStoreInfPoList()) {//云码，银行卡号信息添加
            if (memberMap.get(dto.getStoreId()) != null) {
                YMMemberDto memberDto = memberMap.get(dto.getStoreId());
                dto.setQrCodeUrl(memberDto.getQrcodeUrl());
                if (bankCardMap.get(memberDto.getBankCardId()) != null) {
                    BankCardDto bankCardDto = bankCardMap.get(memberDto.getBankCardId());
                    dto.setBankCardNo(bankCardDto.getBankCardNo());
                } else {
                    dto.setBankCardNo(null);
                }
            } else {
                dto.setQrCodeUrl(null);
                dto.setBankCardNo(null);
            }
            storeInfPoList.add(dto);
        }
        UserDetailDto detailDto = new UserDetailDto();
        detailDto.setUserDto(userDetailDto.getUserDto());
        detailDto.setUserExtDto(userDetailDto.getUserExtDto());
        detailDto.setBankCardPoList(userDetailDto.getBankCardPoList());
        Collections.sort(storeInfPoList, (arg0, arg1) -> arg1.getStoreId().compareTo(arg0.getStoreId()));
        detailDto.setStoreInfPoList(storeInfPoList);
        return ResultDtoFactory.toAck("获取成功", userDetailDto);
    }

    /**
     * 实名认证
     *
     * @param certifyInfoDto
     * @return
     */
    @RequestMapping(value = "/certify", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> certify(@RequestBody CertifyInfoDto certifyInfoDto) {
        if (StringUtils.isEmpty(certifyInfoDto.getUserName()) || StringUtils.isEmpty(certifyInfoDto.getIdCardNo())) {
            return ResultDtoFactory.toNack("姓名和身份证号必填");
        }
        YMUserInfoDto yMUserInfoDto = yMUserInfoService.findByYmUserId(SecurityContext.getCurrentUserId());
        if (yMUserInfoDto == null || StringUtils.isEmpty(yMUserInfoDto.getUserId())) {
            return ResultDtoFactory.toNack("用户未绑定");//用户没有绑定微信，需先绑定微信
        }
        UserInfoDto userInfoDto = userService.findUserByUserId(yMUserInfoDto.getUserId());
        if (userInfoDto == null) {
            return ResultDtoFactory.toNack("用户不存在");
        }
        if (userInfoDto.getIdentityAuth()) {
            return ResultDtoFactory.toNack("实名认证已通过");
        }
        certifyInfoDto.setUserId(userInfoDto.getUserId());
        userService.certify(certifyInfoDto);
        return ResultDtoFactory.toAck("认证成功");
    }

    /**
     * 验证用户是否已绑定微信
     *
     * @return
     */
    @RequestMapping(value = "/checkBind", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> checkBind() {
        YMUserInfoDto yMUserInfoDto = yMUserInfoService.findByYmUserId(SecurityContext.getCurrentUserId());
        if (yMUserInfoDto == null) {
            return ResultDtoFactory.toAck("获取成功", null);//用户没有绑定微信
        }
        YMUserInfoDto loginUserInfo = (YMUserInfoDto) SecurityContext.getCurrentUser().getParamMap().get("ymUserInfoDto");
        boolean flag = StringUtils.isNotEmpty(loginUserInfo.getUserId()) && StringUtils.isEmpty(yMUserInfoDto.getUserId());
        if (flag) {
            SecurityContext.logout();
            return ResultDtoFactory.toAck("获取成功", null);//用户没有绑定微信
        }
        //遍历行业名称
        List<IndustryDto> industryList = industryService.getAllIndustry();
        Map<String, String> industryMap = new HashMap<>();
        for (IndustryDto industryDto : industryList) {
            industryMap.put(industryDto.getMcc(), industryDto.getIndName());
        }

        if (StringUtils.isNotEmpty(yMUserInfoDto.getUserId())) {//商户
            UserDetailDto userDetailDto = userService.findUserDetailByUserId(yMUserInfoDto.getUserId());
            YmUserDetailDto ymUserDetailDto = new YmUserDetailDto();
            ymUserDetailDto.setType(EUserRoleType.BOSS);
            ymUserDetailDto.setName(userDetailDto.getUserDto().getUserName());
            ymUserDetailDto.setBankCardCount(userDetailDto.getBankCardPoList().size());
            ymUserDetailDto.setStoreCount(userDetailDto.getStoreInfPoList().size());
            List<YMMemberDto> memberList = yMMemberService.getMemberListByUserId(yMUserInfoDto.getUserId());
            ymUserDetailDto.setQrCodeCount(memberList.size());
            ymUserDetailDto.setIdentityAuth(userDetailDto.getUserDto().getIdentityAuth());
            //是否上传身份证照
            PictureDto idCardFrontPicDto = pictureService.get(yMUserInfoDto.getUserId(), EPictureType.CARD_FRONT);
            PictureDto idCardBackPicDto = pictureService.get(yMUserInfoDto.getUserId(), EPictureType.CARD_BACK);
            if (idCardFrontPicDto != null && idCardBackPicDto != null) {
                ymUserDetailDto.setUploadIdCard(true);
            }
            //未审核的云码店铺
            List<String> unAuditQrCodeNoList = new ArrayList<>();
            List<YMMemberDto> unAuditMemberList = yMMemberService.getMemberListByUserIdAndMemberAuditStatus(yMUserInfoDto.getUserId(), EMemberAuditStatus.UNAUDIT);
            for (YMMemberDto memberDto : unAuditMemberList) {
                unAuditQrCodeNoList.add(memberDto.getQrcodeNo());
            }
            ymUserDetailDto.setUnAuditQrCodeNoList(unAuditQrCodeNoList);
            //加盟店判断
            List<StoreInfDto> storeList = storeService.findByUserId(yMUserInfoDto.getUserId());
            List<Long> storeIds = new ArrayList<>();
            for (StoreInfDto storeInfDto : storeList) {
                storeIds.add(storeInfDto.getStoreId());
            }
            if (!storeIds.isEmpty()) {
                List<FranchiseDto> franchises = franchiseService.getByStoreIds(storeIds);
                if (!franchises.isEmpty()) {
                    ymUserDetailDto.setFranchise(true);
                }
            }
            //所属行业
            if (CollectionUtils.isNotEmpty(storeList)) {
                String mcc = storeList.get(0).getIndustryMcc();
                ymUserDetailDto.setIndustryMcc(mcc);
                ymUserDetailDto.setIndustryName(ELabelType.valueOf("MCC" + mcc).getText());
            }
            return ResultDtoFactory.toAck("获取成功", ymUserDetailDto);
        }
        ClerkInfDto clerkInfDto = clerkService.findByOpenId(yMUserInfoDto.getOpenId());
        if (clerkInfDto != null) {//店员
            YmUserDetailDto ymUserDetailDto = new YmUserDetailDto();
            ymUserDetailDto.setType(EUserRoleType.CLERK);
            ymUserDetailDto.setName(clerkInfDto.getName());
            List<ClerkAuthDto> list = clerkAuthService.findByClerkId(clerkInfDto.getClerkId());
            ymUserDetailDto.setQrCodeCount(list.size());
            //所属行业
            if (CollectionUtils.isNotEmpty(list)) {
                StoreInfDto storeInfDto = storeService.findByStoreId(Long.valueOf(list.get(0).getStoreId()));
                if (storeInfDto != null) {
                    ymUserDetailDto.setIndustryMcc(storeInfDto.getIndustryMcc());
                    ymUserDetailDto.setIndustryName(industryMap.get(storeInfDto.getIndustryMcc()) + "店员认证");
                }
            }
            return ResultDtoFactory.toAck("获取成功", ymUserDetailDto);
        }
        return ResultDtoFactory.toAck("获取成功", null);//用户没有绑定微信
    }

    /**
     * 注册用户，绑定微信
     *
     * @param mobileDto
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> register(@RequestBody MobileDto mobileDto) {
        UserInfoDto userInfo = userService.findUserByMobile(mobileDto.getMobile());
        if (userInfo != null) {
            return ResultDtoFactory.toNack("该手机号已注册");
        }
        Boolean flag = smsService.confirmVerifyCode(mobileDto.getMobile(), mobileDto.getVerifyCode(), ESmsSendType.REGISTER);
        if (!flag) {
            return ResultDtoFactory.toNack("验证码错误");
        }
        String encryptPwd = StringUtils.EMPTY;
        try {
            encryptPwd = EncryptUtil.encryptMd5(RandomUtil.getNumberStr(6));//初始密码随机6位数字
        } catch (Exception e) {
            return ResultDtoFactory.toNack("注册失败", e);
        }
        UserInfoDto userInfoDto = ConverterService.convert(mobileDto, UserInfoDto.class);
        userInfoDto.setSource(ESource.REGISTER);
        userInfoDto.setLoginPassword(encryptPwd);
        userInfoDto = userService.saveUser(userInfoDto);
        //清除验证码
        smsService.clearVerifyCode(mobileDto.getMobile(), ESmsSendType.REGISTER);
        //绑定微信
        YMUserInfoDto yMUserInfoDto = yMUserInfoService.findByUserId(userInfoDto.getUserId());
        if (yMUserInfoDto != null) {
            return ResultDtoFactory.toNack("该手机号已绑定");
        }
        YMUserInfoDto dto = yMUserInfoService.findByYmUserId(SecurityContext.getCurrentUserId());
        if (dto == null) {
            SecurityContext.logout();
            return ResultDtoFactory.toNack("非法操作");
        }
        dto.setUserId(userInfoDto.getUserId());
        yMUserInfoService.updateUserInfo(dto);
        return ResultDtoFactory.toAck("注册成功", userInfoDto);
    }

    /**
     * 身份证OCR接口
     *
     * @return
     */
    @RequestMapping(value = "/ocr", method = RequestMethod.POST)
    @ResponseBody
    public Object ocr(@RequestBody OCRDto data) {

        try {
            com.xinyunlian.jinfu.common.yitu.dto.resp.OCRDto ocrResult = yituService.ocr(data.getIdCardFrontPicBase64());

            CertificationDto certificationDto = new CertificationDto();
            certificationDto.setUserName(ocrResult.getName());
            certificationDto.setIdCardNo(ocrResult.getIdCard());

            return ResultDtoFactory.toAck("获取成功", certificationDto);

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return ResultDtoFactory.toNack("身份证照片识别失败，请重新尝试", e);
        }
    }

    /**
     * 实名认证,上传身份证
     *
     * @param certifyInfoDto
     * @return
     */
    @RequestMapping(value = "/certifyAndUpload", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> certifyAndUpload(@RequestBody CertifyInfoDto certifyInfoDto) {
        if (StringUtils.isEmpty(certifyInfoDto.getIdCardBackPicBase64()) || StringUtils.isEmpty(certifyInfoDto.getIdCardFrontPicBase64())) {
            return ResultDtoFactory.toNack("身份证照片必传");
        }
        if (StringUtils.isEmpty(certifyInfoDto.getUserName()) || StringUtils.isEmpty(certifyInfoDto.getIdCardNo())) {
            return ResultDtoFactory.toNack("姓名和身份证号必填");
        }
        YMUserInfoDto yMUserInfoDto = yMUserInfoService.findByYmUserId(SecurityContext.getCurrentUserId());
        if (yMUserInfoDto == null || StringUtils.isEmpty(yMUserInfoDto.getUserId())) {
            return ResultDtoFactory.toNack("用户未绑定");//用户没有绑定微信，需先绑定微信
        }
        UserInfoDto userInfoDto = userService.findUserByUserId(yMUserInfoDto.getUserId());
        if (userInfoDto == null) {
            return ResultDtoFactory.toNack("用户不存在");
        }
        if (userInfoDto.getIdentityAuth()) {
            return ResultDtoFactory.toNack("实名认证已通过");
        }

        //身份证上传
        String idCardPic1Path;
        String idCardPic2Path;

        IDCardDto idCardDto = new IDCardDto();
        idCardDto.setUserId(userInfoDto.getUserId());
        idCardDto.setUserName(certifyInfoDto.getUserName());
        idCardDto.setIdCardNo(certifyInfoDto.getIdCardNo());
        try {
            File idCardPic1File = ImageUtils.GenerateImageByBase64(certifyInfoDto.getIdCardFrontPicBase64());
            idCardPic1Path = fileStoreService.upload(EFileType.USER_INFO_IMG, idCardPic1File, idCardPic1File.getName());

            com.xinyunlian.jinfu.common.yitu.dto.resp.OCRDto ocrDto = yituService.ocr(certifyInfoDto.getIdCardFrontPicBase64(), 1);
            idCardDto.setNationAddress(ocrDto.getAddress());
            idCardDto.setSex(ocrDto.getGender());
            idCardDto.setNation(ocrDto.getNation());
            idCardDto.setBirthDate(DateHelper.getDate(ocrDto.getBirthday(), "yyyy.MM.dd"));
            idCardDto.setCardFrontFilePath(idCardPic1Path);
        } catch (IOException e) {
            LOGGER.error("身份证正面图片上传失败", e);
            return ResultDtoFactory.toNack("实名认证失败", e);
        } catch (Exception e) {
            LOGGER.error("读取身份证正面ocr错误", e);
            return ResultDtoFactory.toNack("实名认证失败", e);
        }
        try {
            File idCardPic2File = ImageUtils.GenerateImageByBase64(certifyInfoDto.getIdCardBackPicBase64());
            idCardPic2Path = fileStoreService.upload(EFileType.USER_INFO_IMG, idCardPic2File, idCardPic2File.getName());

            com.xinyunlian.jinfu.common.yitu.dto.resp.OCRDto ocrDto = yituService.ocr(certifyInfoDto.getIdCardBackPicBase64(), 2);
            idCardDto.setIdAuthority(ocrDto.getAgency());
            idCardDto.setIdExpiredBegin(DateHelper.getDate(ocrDto.getValidDateBegin(), DateHelper.SIMPLE_DATE_YMD));
            idCardDto.setIdExpiredEnd(DateHelper.getDate(ocrDto.getValidDateEnd(), DateHelper.SIMPLE_DATE_YMD));
            idCardDto.setCardBackFilePath(idCardPic2Path);
        } catch (IOException e) {
            LOGGER.error("身份证反面图片上传失败", e);
            return ResultDtoFactory.toNack("实名认证失败", e);
        } catch (Exception e) {
            LOGGER.error("读取身份证反面ocr错误", e);
            return ResultDtoFactory.toNack("实名认证失败", e);
        }
        boolean flag = userService.certifyWithCard(idCardDto);
        if (!flag) {
            return ResultDtoFactory.toNack("实名认证失败");
        }
        return ResultDtoFactory.toAck("认证成功");
    }

    /**
     * 实名认证,上传身份证
     *
     * @param certifyInfoDto
     * @return
     */
    @RequestMapping(value = "/uploadIdCard", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> uploadIdCard(@RequestBody CertifyInfoDto certifyInfoDto) {
        if (StringUtils.isEmpty(certifyInfoDto.getIdCardBackPicBase64()) || StringUtils.isEmpty(certifyInfoDto.getIdCardFrontPicBase64())) {
            return ResultDtoFactory.toNack("身份证照片必传");
        }
        YMUserInfoDto yMUserInfoDto = yMUserInfoService.findByYmUserId(SecurityContext.getCurrentUserId());
        if (yMUserInfoDto == null || StringUtils.isEmpty(yMUserInfoDto.getUserId())) {
            return ResultDtoFactory.toNack("用户未绑定");//用户没有绑定微信，需先绑定微信
        }
        UserInfoDto userInfoDto = userService.findUserByUserId(yMUserInfoDto.getUserId());
        if (userInfoDto == null) {
            return ResultDtoFactory.toNack("用户不存在");
        }
        try {
            com.xinyunlian.jinfu.common.yitu.dto.resp.OCRDto ocrResult = yituService.ocr(certifyInfoDto.getIdCardFrontPicBase64().replace("data:image/png;base64,", StringUtils.EMPTY));
            //比对上传身份证和实名认证身份证信息是否一致
            if (!StringUtils.equals(ocrResult.getName(), userInfoDto.getUserName()) || !StringUtils.equals(ocrResult.getIdCard(), userInfoDto.getIdCardNo())) {
                return ResultDtoFactory.toNack("上传身份证照片与实名认证身份证信息不一致");
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return ResultDtoFactory.toNack("身份证照片识别失败，请重新上传", e);
        }
        //身份证上传
        String idCardPic1Path;
        String idCardPic2Path;

        IDCardDto idCardDto = new IDCardDto();
        idCardDto.setUserId(userInfoDto.getUserId());
        idCardDto.setUserName(userInfoDto.getUserName());
        idCardDto.setIdCardNo(userInfoDto.getIdCardNo());
        try {
            File idCardPic1File = ImageUtils.GenerateImageByBase64(certifyInfoDto.getIdCardFrontPicBase64());
            idCardPic1Path = fileStoreService.upload(EFileType.USER_INFO_IMG, idCardPic1File, idCardPic1File.getName());

            com.xinyunlian.jinfu.common.yitu.dto.resp.OCRDto ocrDto = yituService.ocr(certifyInfoDto.getIdCardFrontPicBase64(), 1);
            idCardDto.setNationAddress(ocrDto.getAddress());
            idCardDto.setSex(ocrDto.getGender());
            idCardDto.setNation(ocrDto.getNation());
            idCardDto.setBirthDate(DateHelper.getDate(ocrDto.getBirthday(), "yyyy.MM.dd"));
            idCardDto.setCardFrontFilePath(idCardPic1Path);
        } catch (IOException e) {
            LOGGER.error("身份证正面图片上传失败", e);
            return ResultDtoFactory.toNack("实名认证失败");
        } catch (Exception e) {
            LOGGER.error("读取身份证正面ocr错误", e);
            return ResultDtoFactory.toNack("实名认证失败");
        }
        try {
            File idCardPic2File = ImageUtils.GenerateImageByBase64(certifyInfoDto.getIdCardBackPicBase64());
            idCardPic2Path = fileStoreService.upload(EFileType.USER_INFO_IMG, idCardPic2File, idCardPic2File.getName());

            com.xinyunlian.jinfu.common.yitu.dto.resp.OCRDto ocrDto = yituService.ocr(certifyInfoDto.getIdCardBackPicBase64(), 2);
            idCardDto.setIdAuthority(ocrDto.getAgency());
            idCardDto.setIdExpiredBegin(DateHelper.getDate(ocrDto.getValidDateBegin(), DateHelper.SIMPLE_DATE_YMD));
            idCardDto.setIdExpiredEnd(DateHelper.getDate(ocrDto.getValidDateEnd(), DateHelper.SIMPLE_DATE_YMD));
            idCardDto.setCardBackFilePath(idCardPic2Path);
        } catch (IOException e) {
            LOGGER.error("身份证反面图片上传失败", e);
            return ResultDtoFactory.toNack("实名认证失败", e);
        } catch (Exception e) {
            LOGGER.error("读取身份证反面ocr错误", e);
            return ResultDtoFactory.toNack("实名认证失败", e);
        }
        userService.saveIDCard(idCardDto);
        return ResultDtoFactory.toAck("上传成功");
    }

    /**
     * 扫一扫获取分销员信息
     * @return
     */
    @RequestMapping(value = "/dealerUserInfo", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> scan(@RequestParam String qrcode, @RequestParam String qrcodekey,
                                  @RequestParam String createtime) {
        DealerUserQRInfo qrInfo = new DealerUserQRInfo();
        qrInfo.setQrCode(qrcode);
        qrInfo.setQrType("partner");
        qrInfo.setQrKey(qrcodekey);
        qrInfo.setCreateTime(createtime);
        qrInfo = dealerUserService.checkQrInfo(qrInfo);
        if(!qrInfo.isQrStatus()){
            return ResultDtoFactory.toNack("该二维码已失效请重新扫码！");
        }

        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"),qrInfo);

    }

}
