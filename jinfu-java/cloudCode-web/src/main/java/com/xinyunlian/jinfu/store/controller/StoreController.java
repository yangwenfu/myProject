package com.xinyunlian.jinfu.store.controller;

import com.xinyunlian.jinfu.api.service.ApiBaiduService;
import com.xinyunlian.jinfu.api.service.ApiThirdService;
import com.xinyunlian.jinfu.api.validate.util.SignUtil;
import com.xinyunlian.jinfu.area.dto.SysAreaInfDto;
import com.xinyunlian.jinfu.area.service.SysAreaInfService;
import com.xinyunlian.jinfu.bank.dto.BankCardDto;
import com.xinyunlian.jinfu.bank.dto.BankCardInfoDto;
import com.xinyunlian.jinfu.bank.dto.CorpBankDto;
import com.xinyunlian.jinfu.bank.service.BankService;
import com.xinyunlian.jinfu.bank.service.CorpBankService;
import com.xinyunlian.jinfu.bscontract.dto.YmMemberSignDto;
import com.xinyunlian.jinfu.bscontract.service.YmMemberSignService;
import com.xinyunlian.jinfu.cashier.dto.BankMappingDto;
import com.xinyunlian.jinfu.cashier.dto.CloudCodeMerchantDto;
import com.xinyunlian.jinfu.cashier.service.BankMappingService;
import com.xinyunlian.jinfu.cashier.service.CloudCodeService;
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
import com.xinyunlian.jinfu.common.security.StaticResourceSecurity;
import com.xinyunlian.jinfu.common.util.*;
import com.xinyunlian.jinfu.common.yitu.YituService;
import com.xinyunlian.jinfu.common.yitu.dto.resp.OCRDto;
import com.xinyunlian.jinfu.industry.dto.IndustryDto;
import com.xinyunlian.jinfu.industry.service.IndustryService;
import com.xinyunlian.jinfu.picture.dto.PictureDto;
import com.xinyunlian.jinfu.picture.enums.EPictureType;
import com.xinyunlian.jinfu.picture.service.PictureService;
import com.xinyunlian.jinfu.store.dto.*;
import com.xinyunlian.jinfu.store.service.StoreService;
import com.xinyunlian.jinfu.trade.dto.YmBizDto;
import com.xinyunlian.jinfu.trade.enums.EBizCode;
import com.xinyunlian.jinfu.trade.service.YmBizService;
import com.xinyunlian.jinfu.user.dto.DealerUserDto;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.dto.req.IDCardDto;
import com.xinyunlian.jinfu.user.enums.ESmsSendType;
import com.xinyunlian.jinfu.user.enums.ESource;
import com.xinyunlian.jinfu.user.service.DealerUserService;
import com.xinyunlian.jinfu.user.service.SmsService;
import com.xinyunlian.jinfu.user.service.UserService;
import com.xinyunlian.jinfu.yunma.dto.*;
import com.xinyunlian.jinfu.yunma.enums.EDepotReceiveStatus;
import com.xinyunlian.jinfu.yunma.enums.EDepotStatus;
import com.xinyunlian.jinfu.yunma.service.*;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by menglei on 2017/01/04.
 */
@Controller
@RequestMapping(value = "/store")
public class StoreController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StoreController.class);

    @Autowired
    private StoreService storeService;
    @Autowired
    private YMUserInfoService yMUserInfoService;
    @Autowired
    private UserService userService;
    @Autowired
    private BankService bankService;
    @Autowired
    private YmBizService ymBizService;
    @Autowired
    private YMMemberService yMMemberService;
    @Autowired
    private ApiBaiduService apiBaiduService;
    @Autowired
    private PictureService pictureService;
    @Autowired
    private FileStoreService fileStoreService;
    @Autowired
    private ClerkService clerkService;
    @Autowired
    private ClerkAuthService clerkAuthService;
    @Autowired
    private CloudCodeService cloudCodeService;
    @Autowired
    private SysAreaInfService sysAreaInfService;
    @Autowired
    private YituService yituService;
    @Autowired
    private YmMemberSignService ymMemberSignService;
    @Autowired
    private BankMappingService bankMappingService;
    @Autowired
    private CorpBankService corpBankService;
    @Autowired
    private IndustryService industryService;
    @Autowired
    private YmThirdUserService ymThirdUserService;
    @Autowired
    private YmThirdConfigService ymThirdConfigService;
    @Autowired
    private ApiThirdService apiThirdService;
    @Autowired
    private SmsService smsService;
    @Autowired
    private YmDepotService ymDepotService;
    @Autowired
    private DealerUserService dealerUserService;

    /**
     * 云码绑定
     *
     * @param storeDto
     * @return
     */
    @RequestMapping(value = "/bindYunMa", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> bindYunMa(@RequestBody StoreDto storeDto) {
        YMUserInfoDto yMUserInfoDto = yMUserInfoService.findByYmUserId(SecurityContext.getCurrentUserId());
        if (yMUserInfoDto == null || StringUtils.isEmpty(yMUserInfoDto.getUserId())) {
            return ResultDtoFactory.toNack("用户未绑定");//用户没有绑定微信，需先绑定微信
        }
        StoreInfDto storeInfDto = storeService.findByStoreId(storeDto.getStoreId());
        if (storeInfDto == null) {
            return ResultDtoFactory.toNack("店铺不存在");
        }
        IndustryDto industryDto = industryService.getByMcc(storeInfDto.getIndustryMcc());
        if (industryDto == null) {
            return ResultDtoFactory.toNack("行业不存在");
        }
        if (!StringUtils.equals(storeInfDto.getUserId(), yMUserInfoDto.getUserId())) {
            return ResultDtoFactory.toNack("店铺信息不正确");
        }
        UserInfoDto userInfoDto = userService.findUserByUserId(storeInfDto.getUserId());
        if (userInfoDto == null) {
            return ResultDtoFactory.toNack("用户不存在");
        }
        //结算卡类型判断
        BankCardDto bankCardDto = new BankCardDto();
        if (storeDto.getBankCardType() == null || storeDto.getBankCardType() == 1) {//银行卡
            bankCardDto = bankService.getBankCard(storeDto.getBankCardId());
            if (bankCardDto == null) {
                return ResultDtoFactory.toNack("银行卡不存在");
            }
            bankCardDto.setSubbranchName(bankCardDto.getBankName());
            bankCardDto.setBankCardName(userInfoDto.getUserName());
        } else if (storeDto.getBankCardType() == 2) {//结算卡
            CorpBankDto corpBankDto = corpBankService.getCorpBankByUserId(userInfoDto.getUserId());
            if (corpBankDto == null) {
                return ResultDtoFactory.toNack("对公账号不存在");
            }
            bankCardDto.setBankName(corpBankDto.getOpeningBank());
            bankCardDto.setSubbranchName(corpBankDto.getBankBranch());
            bankCardDto.setBankCardNo(corpBankDto.getAccount());
            bankCardDto.setMobileNo(userInfoDto.getMobile());
            bankCardDto.setBankCardId(corpBankDto.getId());
            bankCardDto.setBankCode(corpBankDto.getBankShortName());
            bankCardDto.setBankCardName(corpBankDto.getAcctName());
        } else {
            return ResultDtoFactory.toNack("结算卡类型不正确");
        }
        BankMappingDto bankMappingDto = bankMappingService.getBankMappingByShortName(bankCardDto.getBankCode());
        if (bankMappingDto == null) {
            return ResultDtoFactory.toNack("该银行不支持");
        }
        YMMemberDto yMMemberDto = yMMemberService.getMemberByStoreId(storeDto.getStoreId());
        if (yMMemberDto != null) {
            return ResultDtoFactory.toNack("该店铺已绑定云码");
        }
        List<YmBizDto> bizList = ymBizService.findAll();
        if (bizList.isEmpty()) {
            return ResultDtoFactory.toNack("默认费率未设置");
        }
        SysAreaInfDto provinceArea = sysAreaInfService.getSysAreaInfById(Long.valueOf(storeInfDto.getProvinceId()));
        if (provinceArea == null) {
            return ResultDtoFactory.toNack("省未找到");
        }
        SysAreaInfDto cityArea = sysAreaInfService.getSysAreaInfById(Long.valueOf(storeInfDto.getCityId()));
        if (cityArea == null) {
            return ResultDtoFactory.toNack("市未找到");
        }
        SysAreaInfDto areaArea = sysAreaInfService.getSysAreaInfById(Long.valueOf(storeInfDto.getAreaId()));
        if (areaArea == null) {
            return ResultDtoFactory.toNack("区未找到");
        }
        //营业执照到期日
        if (storeInfDto.getBizEndDate() == null) {
            if (storeDto.getBizEndDate() == null) {
                return ResultDtoFactory.toNack("营业执照到期日必传");
            } else {
                storeInfDto.setBizEndDate(storeDto.getBizEndDate());
                storeService.updateBizEndDate(storeInfDto);
            }
        }
        //身份证匹配
        PictureDto idCardFrontPicDto = pictureService.get(userInfoDto.getUserId(), EPictureType.CARD_FRONT);
        PictureDto idCardBackPicDto = pictureService.get(userInfoDto.getUserId(), EPictureType.CARD_BACK);
        if (idCardFrontPicDto == null || idCardBackPicDto == null) {
            if (idCardFrontPicDto == null && storeDto.getIdCardFrontPicBase64() == null) {
                return ResultDtoFactory.toNack("正面身份证照必传");
            }
            if (idCardBackPicDto == null && storeDto.getIdCardBackPicBase64() == null) {
                return ResultDtoFactory.toNack("反面身份证照必传");
            }
            if (StringUtils.isNotEmpty(storeDto.getIdCardFrontPicBase64())) {
                try {
                    OCRDto ocrResult = yituService.ocr(storeDto.getIdCardFrontPicBase64().replace("data:image/png;base64,", StringUtils.EMPTY));
                    //比对上传身份证和实名认证身份证信息是否一致
                    if (!StringUtils.equals(ocrResult.getName(), userInfoDto.getUserName()) || !StringUtils.equals(ocrResult.getIdCard(), userInfoDto.getIdCardNo())) {
                        return ResultDtoFactory.toNack("上传身份证照片与实名认证身份证信息不一致");
                    }
                } catch (Exception e) {
                    LOGGER.error(e.getMessage());
                    return ResultDtoFactory.toNack("身份证照片识别失败，请重新上传", e);
                }
            }

            //身份证上传
            String idCardPic1Path;
            String idCardPic2Path;

            IDCardDto idCardDto = new IDCardDto();
            idCardDto.setUserId(userInfoDto.getUserId());
            idCardDto.setUserName(userInfoDto.getUserName());
            idCardDto.setIdCardNo(userInfoDto.getIdCardNo());
            if (StringUtils.isNotEmpty(storeDto.getIdCardFrontPicBase64())) {
                try {
                    File idCardPic1File = ImageUtils.GenerateImageByBase64(storeDto.getIdCardFrontPicBase64());
                    idCardPic1Path = fileStoreService.upload(EFileType.USER_INFO_IMG, idCardPic1File, idCardPic1File.getName());

                    if (!userInfoDto.getIdentityAuth()) {
                        com.xinyunlian.jinfu.common.yitu.dto.resp.OCRDto ocrDto = yituService.ocr(storeDto.getIdCardFrontPicBase64(), 1);
                        idCardDto.setNationAddress(ocrDto.getAddress());
                        idCardDto.setSex(ocrDto.getGender());
                        idCardDto.setNation(ocrDto.getNation());
                        idCardDto.setBirthDate(DateHelper.getDate(ocrDto.getBirthday(), "yyyy.MM.dd"));
                        idCardDto.setCardFrontFilePath(idCardPic1Path);
                    } else if (idCardFrontPicDto == null) {
                        PictureDto pictureDto = new PictureDto();
                        pictureDto.setParentId(userInfoDto.getUserId());
                        pictureDto.setPictureType(EPictureType.CARD_FRONT);
                        pictureDto.setPicturePath(idCardPic1Path);
                        pictureService.savePicture(pictureDto);
                    }
                } catch (IOException e) {
                    LOGGER.error("身份证正面图片上传失败", e);
                    return ResultDtoFactory.toNack("实名认证失败");
                } catch (Exception e) {
                    LOGGER.error("读取身份证正面ocr错误", e);
                    return ResultDtoFactory.toNack("实名认证失败");
                }
            }
            if (StringUtils.isNotEmpty(storeDto.getIdCardBackPicBase64())) {
                try {
                    File idCardPic2File = ImageUtils.GenerateImageByBase64(storeDto.getIdCardBackPicBase64());
                    idCardPic2Path = fileStoreService.upload(EFileType.USER_INFO_IMG, idCardPic2File, idCardPic2File.getName());

                    if (!userInfoDto.getIdentityAuth()) {
                        com.xinyunlian.jinfu.common.yitu.dto.resp.OCRDto ocrDto = yituService.ocr(storeDto.getIdCardBackPicBase64(), 2);
                        idCardDto.setIdAuthority(ocrDto.getAgency());
                        idCardDto.setIdExpiredBegin(DateHelper.getDate(ocrDto.getValidDateBegin(), DateHelper.SIMPLE_DATE_YMD));
                        idCardDto.setIdExpiredEnd(DateHelper.getDate(ocrDto.getValidDateEnd(), DateHelper.SIMPLE_DATE_YMD));
                        idCardDto.setCardBackFilePath(idCardPic2Path);
                    } else if (idCardBackPicDto == null) {
                        PictureDto pictureDto = new PictureDto();
                        pictureDto.setParentId(userInfoDto.getUserId());
                        pictureDto.setPictureType(EPictureType.CARD_BACK);
                        pictureDto.setPicturePath(idCardPic2Path);
                        pictureService.savePicture(pictureDto);
                    }
                } catch (IOException e) {
                    LOGGER.error("身份证反面图片上传失败", e);
                    return ResultDtoFactory.toNack("实名认证失败");
                } catch (Exception e) {
                    LOGGER.error("读取身份证反面ocr错误", e);
                    return ResultDtoFactory.toNack("实名认证失败");
                }
            }
            if (!userInfoDto.getIdentityAuth()) {
                userService.saveIDCard(idCardDto);
            }
        }
        PictureDto pictureDto = pictureService.get(String.valueOf(storeInfDto.getStoreId()), EPictureType.STORE_HEADER);
        if (pictureDto == null) {//没上传门头照需上传门头照
            if (StringUtils.isEmpty(storeDto.getStoreHeaderPicBase64())) {
                return ResultDtoFactory.toNack("门头照必传");
            }
            try {
                File storeHeaderFile = ImageUtils.GenerateImageByBase64(storeDto.getStoreHeaderPicBase64());
                String picPath =
                        fileStoreService.upload(EFileType.STORE_INFO_IMG, storeHeaderFile, storeHeaderFile.getName());
                pictureDto = new PictureDto();
                pictureDto.setParentId(storeInfDto.getStoreId() + StringUtils.EMPTY);
                pictureDto.setPictureType(EPictureType.STORE_HEADER);
                pictureDto.setPicturePath(picPath);
                pictureService.savePicture(pictureDto);
            } catch (IOException e) {
                LOGGER.error("门头照图片上传失败", e);
                return ResultDtoFactory.toNack("绑码失败");
            }
        }
        PictureDto storeInnerDto = pictureService.get(String.valueOf(storeInfDto.getStoreId()), EPictureType.STORE_INNER);
        if (storeInnerDto == null) {//没上传店内照需上传店内照
            if (StringUtils.isEmpty(storeDto.getStoreInnerPicBase64())) {
                return ResultDtoFactory.toNack("店内照必传");
            }
            try {
                File storeInnerFile = ImageUtils.GenerateImageByBase64(storeDto.getStoreInnerPicBase64());
                String picPath =
                        fileStoreService.upload(EFileType.STORE_INFO_IMG, storeInnerFile, storeInnerFile.getName());
                pictureDto = new PictureDto();
                pictureDto.setParentId(storeInfDto.getStoreId() + StringUtils.EMPTY);
                pictureDto.setPictureType(EPictureType.STORE_INNER);
                pictureDto.setPicturePath(picPath);
                pictureService.savePicture(pictureDto);
            } catch (IOException e) {
                LOGGER.error("店内照图片上传失败", e);
                return ResultDtoFactory.toNack("绑码失败");
            }
        }
        PictureDto bizLicencePicDto = pictureService.get(String.valueOf(storeInfDto.getStoreId()), EPictureType.STORE_LICENCE);
        if (bizLicencePicDto == null) {//没上传营业执照需上传营业执照
            if (StringUtils.isEmpty(storeDto.getBizLicencePicBase64())) {
                return ResultDtoFactory.toNack("营业执照必传");
            }
            try {
                File storeLicenceFile = ImageUtils.GenerateImageByBase64(storeDto.getBizLicencePicBase64());
                String picPath =
                        fileStoreService.upload(EFileType.STORE_INFO_IMG, storeLicenceFile, storeLicenceFile.getName());
                pictureDto = new PictureDto();
                pictureDto.setParentId(storeInfDto.getStoreId() + StringUtils.EMPTY);
                pictureDto.setPictureType(EPictureType.STORE_LICENCE);
                pictureDto.setPicturePath(picPath);
                pictureService.savePicture(pictureDto);
            } catch (IOException e) {
                LOGGER.error("营业执照上传失败", e);
                return ResultDtoFactory.toNack("绑码失败");
            }
        }
        //结算卡图片判断
        EPictureType bankPictureType = EPictureType.BANK_CARD_FRONT;
        if (storeDto.getBankCardType() != null && storeDto.getBankCardType() == 2) {//对公账号
            bankPictureType = EPictureType.ACCOUNT_LICENCE;
        }
        //银行卡照
        PictureDto bankCardPicDto = pictureService.get(String.valueOf(bankCardDto.getBankCardId()), bankPictureType);
        if (bankCardPicDto == null) {//没上传结算卡照需上传结算卡照
            if (StringUtils.isEmpty(storeDto.getBankCardPicBase64())) {
                return ResultDtoFactory.toNack("结算卡照必传");
            }
            try {
                File bankCardFile = ImageUtils.GenerateImageByBase64(storeDto.getBankCardPicBase64());
                String picPath = fileStoreService.upload(EFileType.BANK_CARD_IMG, bankCardFile, bankCardFile.getName());
                pictureDto = new PictureDto();
                pictureDto.setParentId(bankCardDto.getBankCardId() + StringUtils.EMPTY);
                pictureDto.setPictureType(bankPictureType);
                pictureDto.setPicturePath(picPath);
                pictureService.savePicture(pictureDto);
            } catch (IOException e) {
                LOGGER.error("结算卡照上传失败", e);
                return ResultDtoFactory.toNack("绑码失败");
            }
        }
        //身份证
        PictureDto idCardFrontPic = pictureService.get(userInfoDto.getUserId(), EPictureType.CARD_FRONT);
        PictureDto idCardBackPic = pictureService.get(userInfoDto.getUserId(), EPictureType.CARD_BACK);
        if (idCardFrontPic == null || idCardBackPic == null) {
            return ResultDtoFactory.toNack("请先上传身份证照");
        }
        //营业执照
        PictureDto bizLicensePicDto = pictureService.get(String.valueOf(storeInfDto.getStoreId()), EPictureType.STORE_LICENCE);
        if (bizLicensePicDto == null) {
            return ResultDtoFactory.toNack("请先上传营业执照");
        }
        //门头照
        PictureDto bizPlaceSnapshot1PicDto = pictureService.get(String.valueOf(storeInfDto.getStoreId()), EPictureType.STORE_HEADER);
        if (bizPlaceSnapshot1PicDto == null) {
            return ResultDtoFactory.toNack("请先上传门头照");
        }
        //门头照
        PictureDto storeInnerPic = pictureService.get(String.valueOf(storeInfDto.getStoreId()), EPictureType.STORE_INNER);
        if (storeInnerPic == null) {
            return ResultDtoFactory.toNack("请先上传店内照");
        }
        //结算卡照
        PictureDto bankCardPic = pictureService.get(String.valueOf(storeDto.getBankCardId()), bankPictureType);
        if (bankCardPic == null) {
            return ResultDtoFactory.toNack("请先上传结算卡照");
        }
        //众码2.0进件
        //店铺进件
        CloudCodeMerchantDto cloudCodeMerchantDto = new CloudCodeMerchantDto();
        cloudCodeMerchantDto.setPartnerId(AppConfigUtil.getConfig("cashier.partnerid"));//partnerid
        cloudCodeMerchantDto.setOutMerchantId(String.valueOf(storeInfDto.getStoreId()));//storeId
        //cloudCodeMerchantDto.setEmail();//空
        cloudCodeMerchantDto.setMerchantName(storeInfDto.getStoreName());//店铺名
        //cloudCodeMerchantDto.setMerchantShortName();//不传
        cloudCodeMerchantDto.setAddress(storeInfDto.getProvince() + storeInfDto.getCity());
        cloudCodeMerchantDto.setLegalPerson(userInfoDto.getUserName());//姓名
        cloudCodeMerchantDto.setLegalPersonID(userInfoDto.getIdCardNo());//身份证
        cloudCodeMerchantDto.setFirstName(userInfoDto.getUserName());//姓名
        cloudCodeMerchantDto.setCellPhone(userInfoDto.getMobile());//手机
        cloudCodeMerchantDto.setBankCode(bankMappingDto.getBankCnapsCode());
        cloudCodeMerchantDto.setBankName(bankCardDto.getBankName());
        cloudCodeMerchantDto.setSubbranchName(bankCardDto.getSubbranchName());//支行名 空
        cloudCodeMerchantDto.setBankProvinceCode(provinceArea.getGbCode());//省 code
        cloudCodeMerchantDto.setBankProvince(storeInfDto.getProvince());//
        cloudCodeMerchantDto.setBankCityCode(cityArea.getGbCode());
        cloudCodeMerchantDto.setBankCity(storeInfDto.getCity());
        cloudCodeMerchantDto.setCardName(bankCardDto.getBankCardName());//银行卡姓名
        cloudCodeMerchantDto.setIdNo(userInfoDto.getIdCardNo());//身份证
        cloudCodeMerchantDto.setCardNo(bankCardDto.getBankCardNo());
        cloudCodeMerchantDto.setBankCardPhone(bankCardDto.getMobileNo());//预留手机号
        for (YmBizDto ymBizDto : bizList) {
            if (EBizCode.ALLIPAY.equals(ymBizDto.getCode())) {
                cloudCodeMerchantDto.setAlipayRate(ymBizDto.getRate());
            } else if (EBizCode.WECHAT.equals(ymBizDto.getCode())) {
                cloudCodeMerchantDto.setWechatRate(ymBizDto.getRate());
            } else if (EBizCode.JDPAY.equals(ymBizDto.getCode())) {
                cloudCodeMerchantDto.setJdpayRate(ymBizDto.getRate());
            } else if (EBizCode.BESTPAY.equals(ymBizDto.getCode())) {
                cloudCodeMerchantDto.setBestpayRate(ymBizDto.getRate());
            } else if (EBizCode.QQPAY.equals(ymBizDto.getCode())) {
                cloudCodeMerchantDto.setQqpayRate(ymBizDto.getRate());
            } else if (EBizCode.BAIDUPAY.equals(ymBizDto.getCode())) {
                cloudCodeMerchantDto.setBaidupayRate(ymBizDto.getRate());
            }
        }
        cloudCodeMerchantDto.setSettleDays(Integer.valueOf(bizList.get(0).getSettlement().getCode()));//t+1=1 d+0=0
        cloudCodeMerchantDto.setBizLicenseCode(storeInfDto.getBizLicence());
        cloudCodeMerchantDto.setLegalPersonIDImgFront(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(idCardFrontPic.getPicturePath()));//身份证
        cloudCodeMerchantDto.setLegalPersonIDImgBack(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(idCardBackPic.getPicturePath()));
        cloudCodeMerchantDto.setBizLicenseImage(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(bizLicensePicDto.getPicturePath()));//营业执照
        cloudCodeMerchantDto.setBizPlaceSnapshot1Image(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(bizPlaceSnapshot1PicDto.getPicturePath()));//门头照
        cloudCodeMerchantDto.setBizPlaceSnapshot2Image(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(storeInnerPic.getPicturePath()));//店内照
        cloudCodeMerchantDto.setHoldCardImage(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(idCardFrontPic.getPicturePath()));//手持身份证
        cloudCodeMerchantDto.setBankCardImage(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(bankCardPic.getPicturePath()));//银行卡照
        cloudCodeMerchantDto.setLicenseDate(DateHelper.formatDate(storeDto.getBizEndDate()));//营业执照到期日
        cloudCodeMerchantDto.setAreaCode(areaArea.getGbCode());
        cloudCodeMerchantDto.setProvinceCode(provinceArea.getGbCode());
        cloudCodeMerchantDto.setCityCode(cityArea.getGbCode());
        cloudCodeMerchantDto.setProvince(storeInfDto.getProvince());
        cloudCodeMerchantDto.setCity(storeInfDto.getCity());
        cloudCodeMerchantDto.setArea(storeInfDto.getArea());
        //cloudCodeMerchantDto.setBizPlaceSnapshot2Image(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(ymMemberSignDto.getFirstPageFilePath()));//合同
        //cloudCodeMerchantDto.setBizPlaceSnapshot3Image(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(ymMemberSignDto.getLastPageFilePath()));//合同
        //cloudCodeMerchantDto.setOtherCertImage();
        cloudCodeMerchantDto.setAppId(AppConfigUtil.getConfig("cashier.appid"));
        cloudCodeMerchantDto.setBusinessCatagory1(industryDto.getIndType());
        cloudCodeMerchantDto.setBusinessCatagory2(industryDto.getIndDesc());
        cloudCodeMerchantDto.setMcc(industryDto.getMcc());
        if (storeDto.getBankCardType() != null && storeDto.getBankCardType() == 2) {
            cloudCodeMerchantDto.setPublickAccount(true);//对公账号
        }
        String memberNo = cloudCodeService.saveMerchant(cloudCodeMerchantDto);
        //云码店铺添加处理
        YMMemberDto dto = new YMMemberDto();
        dto.setStoreId(storeInfDto.getStoreId());
        dto.setUserId(storeInfDto.getUserId());
        dto.setMemberNo(memberNo);
        dto.setQrcodeNo(storeDto.getQrCodeNo());
        dto.setQrcodeUrl(storeDto.getQrCodeUrl());
        if (storeDto.getBankCardType() != null && storeDto.getBankCardType() == 2) {//对公账号
            dto.setCorpBankId(bankCardDto.getBankCardId());
        } else {
            dto.setBankCardId(bankCardDto.getBankCardId());
        }
        dto.setSettlement(bizList.get(0).getSettlement());
        dto.setActivate(true);
        yMMemberService.addMember(dto);
        //更新码库状态
        YmDepotDto ymDepotDto = new YmDepotDto();
        ymDepotDto.setQrCodeNo(dto.getQrcodeNo());
        ymDepotDto.setStatus(EDepotStatus.BIND_USE);
        ymDepotDto.setReceiveStatus(EDepotReceiveStatus.RECEIVE);
        ymDepotService.updateStatusAndReceiveStatus(ymDepotDto);
        return ResultDtoFactory.toAck("资料提交中，请稍等片刻");
    }

    /**
     * 云码绑定
     *
     * @param storeDto
     * @return
     */
    /** 暂时无用，先注释
     @RequestMapping(value = "/saveBindMember", method = RequestMethod.POST)
     @ResponseBody public ResultDto<Object> saveBindMember(@RequestBody StoreDto storeDto) {
     YMUserInfoDto yMUserInfoDto = yMUserInfoService.findByYmUserId(SecurityContext.getCurrentUserId());
     if (yMUserInfoDto == null || StringUtils.isEmpty(yMUserInfoDto.getUserId())) {
     return ResultDtoFactory.toNack("用户未绑定");//用户没有绑定微信，需先绑定微信
     }
     StoreInfDto storeInfDto = storeService.findByStoreId(storeDto.getStoreId());
     if (storeInfDto == null) {
     return ResultDtoFactory.toNack("店铺不存在");
     }
     if (!StringUtils.equals(storeInfDto.getUserId(), yMUserInfoDto.getUserId())) {
     return ResultDtoFactory.toNack("店铺信息不正确");
     }
     UserInfoDto userInfoDto = userService.findUserByUserId(storeInfDto.getUserId());
     if (userInfoDto == null) {
     return ResultDtoFactory.toNack("用户不存在");
     }
     BankCardDto bankCardDto = bankService.getBankCard(storeDto.getBankCardId());
     if (bankCardDto == null) {
     return ResultDtoFactory.toNack("银行卡不存在");
     }
     BankMappingDto bankMappingDto = bankMappingService.getBankMappingByShortName(bankCardDto.getBankCode());
     if (bankMappingDto == null) {
     return ResultDtoFactory.toNack("该银行不支持");
     }
     YMMemberDto yMMemberDto = yMMemberService.getMemberByStoreId(storeDto.getStoreId());
     if (yMMemberDto != null) {
     return ResultDtoFactory.toNack("该店铺已绑定云码");
     }
     //        YmMemberSignDto ymMemberSignDto = ymMemberSignService.getYmMemberSignByStoreId(storeDto.getStoreId());
     //        if (ymMemberSignDto == null || StringUtils.isEmpty(ymMemberSignDto.getFirstPageFilePath()) || StringUtils.isEmpty(ymMemberSignDto.getLastPageFilePath()) || !ymMemberSignDto.getSigned()) {
     //            return ResultDtoFactory.toNack("请先上传合同");
     //        }
     //身份证
     PictureDto idCardFrontPicDto = pictureService.get(userInfoDto.getUserId(), EPictureType.CARD_FRONT);
     PictureDto idCardBackPicDto = pictureService.get(userInfoDto.getUserId(), EPictureType.CARD_BACK);
     if (idCardFrontPicDto == null || idCardBackPicDto == null) {
     return ResultDtoFactory.toNack("请先上传身份证照");
     }
     //营业执照
     PictureDto bizLicensePicDto = pictureService.get(String.valueOf(storeInfDto.getStoreId()), EPictureType.STORE_LICENCE);
     if (bizLicensePicDto == null) {
     return ResultDtoFactory.toNack("请先上传营业执照");
     }
     //门头照
     PictureDto bizPlaceSnapshot1PicDto = pictureService.get(String.valueOf(storeInfDto.getStoreId()), EPictureType.STORE_HEADER);
     if (bizPlaceSnapshot1PicDto == null) {
     return ResultDtoFactory.toNack("请先上传门头照");
     }
     //店内照
     PictureDto storeInnerPicDto = pictureService.get(String.valueOf(storeInfDto.getStoreId()), EPictureType.STORE_INNER);
     if (storeInnerPicDto == null) {
     return ResultDtoFactory.toNack("请先上传店内照");
     }
     //银行卡照
     PictureDto bankCardPicDto = pictureService.get(String.valueOf(storeDto.getBankCardId()), EPictureType.BANK_CARD_FRONT);
     if (bankCardPicDto == null) {
     return ResultDtoFactory.toNack("银行卡照必传");
     }
     //营业执照到期日
     if (storeInfDto.getBizEndDate() == null) {
     return ResultDtoFactory.toNack("营业执照到期日必传");
     }
     //店铺进件
     List<YmBizDto> bizList = ymBizService.findAll();
     SysAreaInfDto provinceArea = sysAreaInfService.getSysAreaInfById(Long.valueOf(storeInfDto.getProvinceId()));
     SysAreaInfDto cityArea = sysAreaInfService.getSysAreaInfById(Long.valueOf(storeInfDto.getCityId()));
     SysAreaInfDto areaArea = sysAreaInfService.getSysAreaInfById(Long.valueOf(storeInfDto.getAreaId()));
     CloudCodeMerchantDto cloudCodeMerchantDto = new CloudCodeMerchantDto();
     cloudCodeMerchantDto.setPartnerId(AppConfigUtil.getConfig("cashier.partnerid"));//partnerid
     cloudCodeMerchantDto.setOutMerchantId(String.valueOf(storeInfDto.getStoreId()));//storeId
     //cloudCodeMerchantDto.setEmail();//空
     cloudCodeMerchantDto.setMerchantName(storeInfDto.getStoreName());//店铺名
     //cloudCodeMerchantDto.setMerchantShortName();//不传
     cloudCodeMerchantDto.setAddress(storeInfDto.getProvince() + storeInfDto.getCity());
     cloudCodeMerchantDto.setLegalPerson(userInfoDto.getUserName());//姓名
     cloudCodeMerchantDto.setLegalPersonID(userInfoDto.getIdCardNo());//身份证
     cloudCodeMerchantDto.setFirstName(userInfoDto.getUserName());//姓名
     cloudCodeMerchantDto.setCellPhone(userInfoDto.getMobile());//手机
     cloudCodeMerchantDto.setBankCode(bankMappingDto.getBankCnapsCode());
     cloudCodeMerchantDto.setBankName(bankCardDto.getBankName());
     //cloudCodeMerchantDto.setSubbranchName();//支行名 空
     cloudCodeMerchantDto.setBankProvinceCode(provinceArea.getGbCode());//省 code
     cloudCodeMerchantDto.setBankProvince(storeInfDto.getProvince());//
     cloudCodeMerchantDto.setBankCityCode(cityArea.getGbCode());
     cloudCodeMerchantDto.setBankCity(storeInfDto.getCity());
     cloudCodeMerchantDto.setCardName(userInfoDto.getUserName());//银行卡姓名
     cloudCodeMerchantDto.setIdNo(userInfoDto.getIdCardNo());//身份证
     cloudCodeMerchantDto.setCardNo(bankCardDto.getBankCardNo());
     cloudCodeMerchantDto.setBankCardPhone(bankCardDto.getMobileNo());//预留手机号
     cloudCodeMerchantDto.setAlipayRate(bizList.get(1).getRate());
     cloudCodeMerchantDto.setWechatRate(bizList.get(0).getRate());
     cloudCodeMerchantDto.setJdpayRate(bizList.get(2).getRate());
     cloudCodeMerchantDto.setBestpayRate(bizList.get(3).getRate());
     cloudCodeMerchantDto.setSettleDays(Integer.valueOf(bizList.get(0).getSettlement().getCode()));//t+1=1 d+0=0
     cloudCodeMerchantDto.setBizLicenseCode(storeInfDto.getBizLicence());
     cloudCodeMerchantDto.setLegalPersonIDImgFront(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(idCardFrontPicDto.getPicturePath()));//身份证
     cloudCodeMerchantDto.setLegalPersonIDImgBack(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(idCardBackPicDto.getPicturePath()));
     cloudCodeMerchantDto.setBizLicenseImage(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(bizLicensePicDto.getPicturePath()));//营业执照
     cloudCodeMerchantDto.setBizPlaceSnapshot1Image(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(bizPlaceSnapshot1PicDto.getPicturePath()));//门头照
     cloudCodeMerchantDto.setBizPlaceSnapshot2Image(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(storeInnerPicDto.getPicturePath()));//店内照
     cloudCodeMerchantDto.setHoldCardImage(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(idCardFrontPicDto.getPicturePath()));//手持身份证
     cloudCodeMerchantDto.setBankCardImage(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(bankCardPicDto.getPicturePath()));//银行卡照
     cloudCodeMerchantDto.setLicenseDate(DateHelper.formatDate(storeInfDto.getBizEndDate()));//营业执照到期日
     cloudCodeMerchantDto.setAreaCode(areaArea.getGbCode());
     cloudCodeMerchantDto.setProvinceCode(provinceArea.getGbCode());
     cloudCodeMerchantDto.setCityCode(cityArea.getGbCode());
     cloudCodeMerchantDto.setProvince(storeInfDto.getProvince());
     cloudCodeMerchantDto.setCity(storeInfDto.getCity());
     cloudCodeMerchantDto.setArea(storeInfDto.getArea());
     //        cloudCodeMerchantDto.setBizPlaceSnapshot2Image(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(ymMemberSignDto.getFirstPageFilePath()));//合同
     //        cloudCodeMerchantDto.setBizPlaceSnapshot3Image(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(ymMemberSignDto.getLastPageFilePath()));//合同
     //cloudCodeMerchantDto.setOtherCertImage();
     cloudCodeMerchantDto.setAppId(AppConfigUtil.getConfig("cashier.appid"));
     String memberNo = cloudCodeService.saveMerchant(cloudCodeMerchantDto);
     //云码店铺添加处理
     YMMemberDto dto = new YMMemberDto();
     dto.setStoreId(storeInfDto.getStoreId());
     dto.setUserId(storeInfDto.getUserId());
     dto.setMemberNo(memberNo);
     dto.setQrcodeNo(storeDto.getQrCodeNo());
     dto.setQrcodeUrl(storeDto.getQrCodeUrl());
     dto.setBankCardId(bankCardDto.getBankCardId());
     dto.setSettlement(bizList.get(0).getSettlement());
     dto.setActivate(true);
     yMMemberService.addMember(dto);
     return ResultDtoFactory.toAck("云码绑定成功");
     }
     **/

    /**
     * 判断云码是否已使用 true：已使用，false：未使用
     *
     * @param qrCodeNo
     * @return
     */
    @RequestMapping(value = "/verifyQrCode", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> verifyQrCode(@RequestParam String qrCodeNo) {
        if (StringUtils.isEmpty(qrCodeNo)) {
            return ResultDtoFactory.toNack("参数必传");
        }
        YMMemberDto yMMemberDto = yMMemberService.getMemberByQrCodeNo(qrCodeNo);
        boolean flag = false;
        if (yMMemberDto != null) {
            flag = true;
        }
        return ResultDtoFactory.toAck("获取成功", flag);
    }

    /**
     * 店铺列表
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getList() {
        YMUserInfoDto yMUserInfoDto = yMUserInfoService.findByYmUserId(SecurityContext.getCurrentUserId());
        if (yMUserInfoDto == null || StringUtils.isEmpty(yMUserInfoDto.getUserId())) {
            return ResultDtoFactory.toNack("用户未绑定");//用户没有绑定微信，需先绑定微信
        }
        List<StoreInfDto> storeList = storeService.findByUserId(yMUserInfoDto.getUserId());
        List<YMMemberDto> members = yMMemberService.getMemberListByUserId(yMUserInfoDto.getUserId());//已绑云码店铺
        Map<Long, YMMemberDto> memberMap = new HashMap<>();
        for (YMMemberDto dto : members) {
            memberMap.put(dto.getStoreId(), dto);
        }
        List<StoreInfDto> list = new ArrayList<>();
        for (StoreInfDto dto : storeList) {//云码
            if (memberMap.get(dto.getStoreId()) != null) {
                YMMemberDto memberDto = memberMap.get(dto.getStoreId());
                dto.setQrCodeUrl(memberDto.getQrcodeUrl());
            } else {
                dto.setQrCodeUrl(null);
            }
            list.add(dto);
        }
        Collections.sort(list, (arg0, arg1) -> arg1.getStoreId().compareTo(arg0.getStoreId()));
        return ResultDtoFactory.toAck("获取成功", list);
    }

    /**
     * 添加店铺
     *
     * @param storeDto
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> save(@RequestBody @Valid StoreDto storeDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResultDtoFactory.toNack(result.getFieldError().getDefaultMessage());
        }
        YMUserInfoDto yMUserInfoDto = yMUserInfoService.findByYmUserId(SecurityContext.getCurrentUserId());
        if (yMUserInfoDto == null || StringUtils.isEmpty(yMUserInfoDto.getUserId())) {
            return ResultDtoFactory.toNack("用户未绑定");//用户没有绑定微信，需先绑定微信
        }
        //烟草证号判断
        if (!storeDto.getTobaccoCertificateNo().matches("[0-9]{12}")) {
            return ResultDtoFactory.toNack("请输入正确的烟草证号");
        }
        storeDto.setUserId(yMUserInfoDto.getUserId());
        StoreInfDto storeInfDto = ConverterService.convert(storeDto, StoreInfDto.class);
        storeInfDto.setSource(ESource.REGISTER);
        StoreInfDto storeInf = storeService.saveStore(storeInfDto);
        apiBaiduService.updatePoint(storeInf);
        //图片处理
        PictureDto pictureDto = new PictureDto();
        pictureDto.setParentId(storeInf.getStoreId() + StringUtils.EMPTY);
        if (storeDto.getTobaccoCertificatePicBase64() != null) {
            try {
                File tobaccoCertificateFile = ImageUtils.GenerateImageByBase64(storeDto.getTobaccoCertificatePicBase64());
                String picPath =
                        fileStoreService.upload(EFileType.STORE_INFO_IMG, tobaccoCertificateFile, tobaccoCertificateFile.getName());
                pictureDto.setPictureType(EPictureType.STORE_TOBACCO);
                pictureDto.setPicturePath(picPath);
                pictureService.savePicture(pictureDto);
            } catch (IOException e) {
                LOGGER.error("烟草许可证图片上传失败", e);
                return ResultDtoFactory.toNack("添加店铺失败");
            }
        }
        if (storeDto.getBizLicencePicBase64() != null) {
            try {
                File bizLicencePicFile = ImageUtils.GenerateImageByBase64(storeDto.getBizLicencePicBase64());
                String picPath =
                        fileStoreService.upload(EFileType.STORE_INFO_IMG, bizLicencePicFile, bizLicencePicFile.getName());
                pictureDto.setPictureType(EPictureType.STORE_LICENCE);
                pictureDto.setPicturePath(picPath);
                pictureService.savePicture(pictureDto);
            } catch (IOException e) {
                LOGGER.error("营业执照图片上传失败", e);
                return ResultDtoFactory.toNack("添加店铺失败");
            }
        }
        return ResultDtoFactory.toAck("添加成功");
    }

    /**
     * 添加店铺
     *
     * @param storeDto
     * @return
     */
    @RequestMapping(value = "/v2/save", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("行业添加店铺")
    public ResultDto<Object> v2save(@RequestBody @Valid StoreDto storeDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResultDtoFactory.toNack(result.getFieldError().getDefaultMessage());
        }
        YMUserInfoDto yMUserInfoDto = yMUserInfoService.findByYmUserId(SecurityContext.getCurrentUserId());
        if (yMUserInfoDto == null || StringUtils.isEmpty(yMUserInfoDto.getUserId())) {
            return ResultDtoFactory.toNack("用户未绑定");//用户没有绑定微信，需先绑定微信
        }
        //判断商户是否有店铺，新增店铺行业是否和原店铺行业相同
        List<StoreInfDto> storeList = storeService.findByUserId(yMUserInfoDto.getUserId());
        if (!com.alibaba.dubbo.common.utils.CollectionUtils.isEmpty(storeList)) {
            if (!storeList.get(0).getIndustryMcc().equals(storeDto.getIndustryMcc())) {
                return ResultDtoFactory.toNack("行业必须和已有店铺相同");
            }
        }
        storeDto.setUserId(yMUserInfoDto.getUserId());
        StoreInfDto storeInfDto = ConverterService.convert(storeDto, StoreInfDto.class);
        storeInfDto.setSource(ESource.REGISTER);
        StoreInfDto storeInf = storeService.saveSupportAll(storeInfDto);
        apiBaiduService.updatePoint(storeInf);
        //图片处理
        PictureDto pictureDto = new PictureDto();
        pictureDto.setParentId(storeInf.getStoreId() + StringUtils.EMPTY);
        if (storeDto.getLicencePicBase64() != null) {
            try {
                File licenceFile = ImageUtils.GenerateImageByBase64(storeDto.getLicencePicBase64());
                String picPath = fileStoreService.upload(EFileType.STORE_INFO_IMG, licenceFile, licenceFile.getName());
                EPictureType type = EPictureType.STORE_TOBACCO;
                if (!"5227".equals(storeDto.getIndustryMcc())) {
                    type = EPictureType.STORE_NO_TOBACCO_LICENCE;
                }
                pictureDto.setPictureType(type);
                pictureDto.setPicturePath(picPath);
                pictureService.savePicture(pictureDto);
            } catch (IOException e) {
                LOGGER.error("许可证图片上传失败", e);
                return ResultDtoFactory.toNack("添加店铺失败");
            }
        }
        if (storeDto.getBizLicencePicBase64() != null) {
            try {
                File bizLicencePicFile = ImageUtils.GenerateImageByBase64(storeDto.getBizLicencePicBase64());
                String picPath = fileStoreService.upload(EFileType.STORE_INFO_IMG, bizLicencePicFile, bizLicencePicFile.getName());
                pictureDto.setPictureType(EPictureType.STORE_LICENCE);
                pictureDto.setPicturePath(picPath);
                pictureService.savePicture(pictureDto);
            } catch (IOException e) {
                LOGGER.error("营业执照图片上传失败", e);
                return ResultDtoFactory.toNack("添加店铺失败");
            }
        }
        return ResultDtoFactory.toAck("添加成功");
    }

    /**
     * 云码列表
     *
     * @return
     */
    @RequestMapping(value = "/yunmaList", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getYunmaList() {
        YMUserInfoDto yMUserInfoDto = yMUserInfoService.findByYmUserId(SecurityContext.getCurrentUserId());
        if (yMUserInfoDto == null) {
            return ResultDtoFactory.toNack("用户未绑定");//用户没有绑定微信，需先绑定微信
        }
        List<YMMemberDto> members;
        if (StringUtils.isNotEmpty(yMUserInfoDto.getUserId())) {//店主
            members = yMMemberService.getMemberListByUserId(yMUserInfoDto.getUserId());//已绑云码店铺
        } else {
            ClerkInfDto clerkInfDto = clerkService.findByOpenId(yMUserInfoDto.getOpenId());
            if (clerkInfDto == null) {//店员
                return ResultDtoFactory.toNack("用户未绑定");//用户没有绑定微信，需先绑定微信
            }
            List<ClerkAuthDto> clerkAuthList = clerkAuthService.findByClerkId(clerkInfDto.getClerkId());
            if (clerkAuthList.isEmpty()) {
                return ResultDtoFactory.toAck("获取成功", null);
            }
            List<Long> storeIds = new ArrayList<>();
            for (ClerkAuthDto clerkAuthDto : clerkAuthList) {
                storeIds.add(Long.valueOf(clerkAuthDto.getStoreId()));
            }
            members = yMMemberService.getMemberByStoreIds(storeIds);
        }
        List<Long> storeIds = new ArrayList<>();
        List<String> ymIds = new ArrayList<>();
        for (YMMemberDto yMMemberDto : members) {
            storeIds.add(yMMemberDto.getStoreId());
            ymIds.add(String.valueOf(yMMemberDto.getId()));
        }
        if (CollectionUtils.isEmpty(storeIds)) {
            return ResultDtoFactory.toAck("获取成功", null);
        }
        //云码库
        List<YmDepotViewDto> ymDepotViewDtos = ymDepotService.findByYmIds(ymIds);
        Map<Long, YmDepotViewDto> ymDepotMap = new HashMap<>();
        for (YmDepotViewDto ymDepotViewDto : ymDepotViewDtos) {
            ymDepotMap.put(Long.valueOf(ymDepotViewDto.getYmId()), ymDepotViewDto);
        }

        List<StoreInfDto> storeList = storeService.findByStoreIds(storeIds);
        Map<Long, StoreInfDto> storeMap = new HashMap<>();
        for (StoreInfDto dto : storeList) {
            storeMap.put(dto.getStoreId(), dto);
        }
        List<BankCardDto> bankList = new ArrayList<>();
        if (!members.isEmpty()) {
            bankList = bankService.findByUserId(members.get(0).getUserId());
        }
        Map<Long, BankCardDto> bankMap = new HashMap<>();
        for (BankCardDto dto : bankList) {
            bankMap.put(dto.getBankCardId(), dto);
        }
        List<MemberInfoDto> list = new ArrayList<>();
        for (YMMemberDto dto : members) {
            if (storeMap.get(dto.getStoreId()) != null) {
                StoreInfDto storeInfDto = storeMap.get(dto.getStoreId());
                MemberInfoDto memberInfoDto = ConverterService.convert(dto, MemberInfoDto.class);
                memberInfoDto.setStoreName(storeInfDto.getStoreName());
                if (bankMap.get(dto.getBankCardId()) != null) {
                    BankCardDto bankCardDto = bankMap.get(dto.getBankCardId());
                    memberInfoDto.setBankCardNo(bankCardDto.getBankCardNo());
                    memberInfoDto.setBankName(bankCardDto.getBankName());
                }
                YmDepotViewDto ymDepotViewDto = ymDepotMap.get(dto.getId());
                if (ymDepotViewDto != null
                        && EDepotStatus.BIND_UNUSE.equals(ymDepotViewDto.getStatus())
                        && EDepotReceiveStatus.UNRECEIVE.equals(ymDepotViewDto.getReceiveStatus())
                        && StringUtils.isEmpty(ymDepotViewDto.getMailName())) {
                    memberInfoDto.setApplyed(false);
                }
                list.add(memberInfoDto);
            }
        }
        Collections.sort(list, (arg0, arg1) -> arg1.getStoreId().compareTo(arg0.getStoreId()));
        return ResultDtoFactory.toAck("获取成功", list);
    }

    /**
     * 已选云码列表
     *
     * @return
     */
    @RequestMapping(value = "/chooseYunmaList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("已选云码列表")
    public ResultDto<Map<String, Object>> getChooseYunmaList() {
        YMUserInfoDto yMUserInfoDto = yMUserInfoService.findByYmUserId(SecurityContext.getCurrentUserId());
        if (yMUserInfoDto == null) {
            return ResultDtoFactory.toNack("用户未绑定");//用户没有绑定微信，需先绑定微信
        }
        if (StringUtils.isEmpty(yMUserInfoDto.getUserId())) {
            return ResultDtoFactory.toNack("只有店主才能进行操作");
        }
        List<YMMemberDto> members = yMMemberService.getMemberListByUserId(yMUserInfoDto.getUserId());//已绑云码店铺
        List<Long> storeIds = new ArrayList<>();
        for (YMMemberDto yMMemberDto : members) {
            storeIds.add(yMMemberDto.getStoreId());
        }
        if (CollectionUtils.isEmpty(storeIds)) {
            return ResultDtoFactory.toAck("获取成功", null);
        }
        List<StoreInfDto> storeList = storeService.findByStoreIds(storeIds);
        Map<Long, StoreInfDto> storeMap = new HashMap<>();
        for (StoreInfDto dto : storeList) {
            storeMap.put(dto.getStoreId(), dto);
        }
        YmThirdUserDto ymThirdUserDto = ymThirdUserService.findByUserId(yMUserInfoDto.getUserId());
        List<MemberInfoDto> list = new ArrayList<>();
        Boolean selected = false;
        for (YMMemberDto dto : members) {
            if (storeMap.get(dto.getStoreId()) != null) {
                StoreInfDto storeInfDto = storeMap.get(dto.getStoreId());
                MemberInfoDto memberInfoDto = ConverterService.convert(dto, MemberInfoDto.class);
                memberInfoDto.setStoreName(storeInfDto.getStoreName());
                memberInfoDto.setProvince(storeInfDto.getProvince());
                memberInfoDto.setCity(storeInfDto.getCity());
                memberInfoDto.setArea(storeInfDto.getArea());
                memberInfoDto.setStreet(storeInfDto.getStreet());
                if (ymThirdUserDto != null && dto.getId().equals(ymThirdUserDto.getMemberId())) {
                    memberInfoDto.setSelected(true);
                    selected = true;
                }
                list.add(memberInfoDto);
            }
        }
        Collections.sort(list, (arg0, arg1) -> arg1.getStoreId().compareTo(arg0.getStoreId()));
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("list", list);
        resultMap.put("selected", selected);
        return ResultDtoFactory.toAck("获取成功", resultMap);
    }

    /**
     * 是否显示选择云码按钮
     *
     * @return
     */
    @RequestMapping(value = "/showChooseButton", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("是否显示选择云码按钮")
    public ResultDto<Map<String, Object>> getShowChooseButton() {
        YMUserInfoDto yMUserInfoDto = yMUserInfoService.findByYmUserId(SecurityContext.getCurrentUserId());
        if (yMUserInfoDto == null) {
            return ResultDtoFactory.toNack("用户未绑定");//用户没有绑定微信，需先绑定微信
        }
        boolean flag = false;
        if (StringUtils.isNotEmpty(yMUserInfoDto.getUserId())) {
            YmThirdUserDto ymThirdUserDto = ymThirdUserService.findByUserId(yMUserInfoDto.getUserId());
            List<YMMemberDto> members = yMMemberService.getMemberListByUserId(yMUserInfoDto.getUserId());//已绑云码店铺
            if (ymThirdUserDto != null && CollectionUtils.isNotEmpty(members)) {
                flag = true;
            }
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("flag", flag);
        return ResultDtoFactory.toAck("获取成功", resultMap);
    }

    /**
     * 选择云码
     *
     * @param yMMemberDto
     * @return
     */
    @RequestMapping(value = "/chooseYunma", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("选择云码")
    public ResultDto<Object> chooseYunma(@RequestBody YMMemberDto yMMemberDto) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        YMUserInfoDto yMUserInfoDto = yMUserInfoService.findByYmUserId(SecurityContext.getCurrentUserId());
        if (yMUserInfoDto == null || StringUtils.isEmpty(yMUserInfoDto.getUserId())) {
            return ResultDtoFactory.toNack("用户未绑定");//用户没有绑定微信，需先绑定微信
        }
        if (StringUtils.isEmpty(yMUserInfoDto.getUserId())) {
            return ResultDtoFactory.toNack("只有店主才能进行操作");
        }
        YmThirdUserDto ymThirdUserDto = ymThirdUserService.findByUserId(yMUserInfoDto.getUserId());
        if (ymThirdUserDto == null) {
            return ResultDtoFactory.toNack("未来自第三方数据");
        } else if (ymThirdUserDto.getMemberId() != null) {
            return ResultDtoFactory.toNack("云码店铺已被选中");
        }
        YMMemberDto dto = yMMemberService.getMemberByMemberNo(yMMemberDto.getMemberNo());
        if (dto == null || !dto.getUserId().equals(yMUserInfoDto.getUserId())) {
            return ResultDtoFactory.toNack("云码店铺不存在");
        }
        ymThirdUserDto.setMemberId(dto.getId());
        ymThirdUserService.updateMemberId(ymThirdUserDto);
        //推送通知给合作方
        YmThirdConfigDto ymThirdConfigDto = ymThirdConfigService.findById(ymThirdUserDto.getThirdConfigId());
        if (ymThirdConfigDto == null) {
            return ResultDtoFactory.toNack("appId不存在");
        }
        SortedMap<String, String> sortedMap = new TreeMap<>();
        sortedMap.put("mobile", ymThirdUserDto.getMobile());
        sortedMap.put("outId", ymThirdUserDto.getOutId());
        sortedMap.put("qrCodeNo", dto.getQrcodeNo());
        sortedMap.put("qrCodeUrl", dto.getQrcodeUrl());
        String sign = SignUtil.createSign(sortedMap, ymThirdConfigDto.getKey());
        sortedMap.put("sign", sign);
        apiThirdService.thirdNotify(sortedMap, ymThirdConfigDto.getBindNotifyUrl());
        return ResultDtoFactory.toAck("操作成功");
    }

    /**
     * 绑码页信息获取
     *
     * @return
     */
    @RequestMapping(value = "/getBindDetail", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getBindDetail(@RequestParam Long storeId) {
        YMUserInfoDto yMUserInfoDto = yMUserInfoService.findByYmUserId(SecurityContext.getCurrentUserId());
        if (yMUserInfoDto == null || StringUtils.isEmpty(yMUserInfoDto.getUserId())) {
            return ResultDtoFactory.toNack("用户未绑定");//用户没有绑定微信，需先绑定微信
        }
        UserInfoDto userInfoDto = userService.findUserByUserId(yMUserInfoDto.getUserId());
        if (userInfoDto == null) {
            return ResultDtoFactory.toNack("用户不存在");
        }

        //店铺信息
        StoreInfDto storeInfDto = storeService.findByStoreId(storeId);
        if (storeInfDto == null) {
            return ResultDtoFactory.toNack("店铺不存在");
        }
        //银行卡列表
        List<BankCardDto> bankCardDtoList = bankService.findByUserId(userInfoDto.getUserId());
        List<BankCardInfoDto> bankList = new ArrayList<>();
        for (BankCardDto bankCardDto : bankCardDtoList) {
            BankCardInfoDto bankCardInfoDto = ConverterService.convert(bankCardDto, BankCardInfoDto.class);
            bankList.add(bankCardInfoDto);
        }
        Collections.sort(bankList, (arg0, arg1) -> arg1.getBankCardId().compareTo(arg0.getBankCardId()));

        BindDetailDto bindDetailDto = new BindDetailDto();
        bindDetailDto.setStoreId(storeInfDto.getStoreId());
        bindDetailDto.setStoreName(storeInfDto.getStoreName());
        //门头照
        PictureDto storeHeaderPicDto = pictureService.get(String.valueOf(storeInfDto.getStoreId()), EPictureType.STORE_HEADER);
        if (storeHeaderPicDto != null) {
            bindDetailDto.setStoreHeaderPic(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(storeHeaderPicDto.getPicturePath()));
        }
        //店内照
        PictureDto storeInnerPicDto = pictureService.get(String.valueOf(storeInfDto.getStoreId()), EPictureType.STORE_INNER);
        if (storeInnerPicDto != null) {
            bindDetailDto.setStoreInnerPic(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(storeInnerPicDto.getPicturePath()));
        }
        //营业执照
        PictureDto bizLicencePicDto = pictureService.get(String.valueOf(storeInfDto.getStoreId()), EPictureType.STORE_LICENCE);
        if (bizLicencePicDto != null) {
            bindDetailDto.setBizLicencePic(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(bizLicencePicDto.getPicturePath()));
        }
        //营业执照开始日期
        bindDetailDto.setBizEndDate(storeInfDto.getBizEndDate());
        //银行列表
        bindDetailDto.setBankList(bankList);
        //身份证照
        PictureDto idCardFrontPicDto = pictureService.get(userInfoDto.getUserId(), EPictureType.CARD_FRONT);
        PictureDto idCardBackPicDto = pictureService.get(userInfoDto.getUserId(), EPictureType.CARD_BACK);
        if (idCardFrontPicDto != null) {
            bindDetailDto.setUploadIdCardFront(true);
        }
        if (idCardBackPicDto != null){
            bindDetailDto.setUploadIdCardBack(true);
        }
        //判断合同是否已上传
        YmMemberSignDto ymMemberSignDto = ymMemberSignService.getYmMemberSignByStoreId(storeInfDto.getStoreId());
        if (ymMemberSignDto != null && StringUtils.isNotEmpty(ymMemberSignDto.getFirstPageFilePath()) && StringUtils.isNotEmpty(ymMemberSignDto.getLastPageFilePath()) && ymMemberSignDto.getSigned()) {
            bindDetailDto.setUploadSign(true);
        }
        return ResultDtoFactory.toAck("获取成功", bindDetailDto);
    }

    /**
     * 获取资料补全页信息
     *
     * @return
     */
    @RequestMapping(value = "/getSupply", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getSupply(@RequestParam String qrCodeNo) {
        YMUserInfoDto yMUserInfoDto = yMUserInfoService.findByYmUserId(SecurityContext.getCurrentUserId());
        if (yMUserInfoDto == null || StringUtils.isEmpty(yMUserInfoDto.getUserId())) {
            return ResultDtoFactory.toNack("用户未绑定");//用户没有绑定微信，需先绑定微信
        }
        UserInfoDto userInfoDto = userService.findUserByUserId(yMUserInfoDto.getUserId());
        if (userInfoDto == null) {
            return ResultDtoFactory.toNack("用户不存在");
        }
        //云码店铺
        YMMemberDto memberDto = yMMemberService.getMemberByQrCodeNoAndUserId(qrCodeNo, userInfoDto.getUserId());
        if (memberDto == null) {
            return ResultDtoFactory.toNack("云码店铺不存在");
        }
        //店铺信息
        StoreInfDto storeInfDto = storeService.findByStoreId(memberDto.getStoreId());
        if (storeInfDto == null) {
            return ResultDtoFactory.toNack("店铺不存在");
        }
        //查银行卡
        BankCardDto bankCardDto = new BankCardDto();
        Integer bankCardType = 1;
        if (memberDto.getBankCardId() != null) {
            bankCardDto = bankService.getBankCard(memberDto.getBankCardId());
            if (bankCardDto == null) {
                return ResultDtoFactory.toNack("银行卡不存在");
            }
            bankCardType = 1;
        } else if (memberDto.getCorpBankId() != null) {
            CorpBankDto corpBankDto = corpBankService.getCorpBankById(memberDto.getCorpBankId());
            if (corpBankDto == null) {
                return ResultDtoFactory.toNack("对公账号不存在");
            }
            bankCardDto.setBankName(corpBankDto.getOpeningBank());
            bankCardDto.setBankCardNo(corpBankDto.getAccount());
            bankCardType = 2;
        }
        SupplyDto supplyDto = new SupplyDto();
        supplyDto.setStoreId(storeInfDto.getStoreId());
        supplyDto.setStoreName(storeInfDto.getStoreName());
        supplyDto.setQrCodeNo(qrCodeNo);
        supplyDto.setBankName(bankCardDto.getBankName());
        supplyDto.setBankCardNo(bankCardDto.getBankCardNo());
        supplyDto.setBankCardType(bankCardType);
        //门头照
        PictureDto storeHeaderPicDto = pictureService.get(String.valueOf(storeInfDto.getStoreId()), EPictureType.STORE_HEADER);
        if (storeHeaderPicDto != null) {
            supplyDto.setStoreHeaderPic(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(storeHeaderPicDto.getPicturePath()));
        }
        //店内照
        PictureDto storeInnerPicDto = pictureService.get(String.valueOf(storeInfDto.getStoreId()), EPictureType.STORE_INNER);
        if (storeInnerPicDto != null) {
            supplyDto.setStoreInnerPic(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(storeInnerPicDto.getPicturePath()));
        }
        //营业执照
        PictureDto bizLicencePicDto = pictureService.get(String.valueOf(storeInfDto.getStoreId()), EPictureType.STORE_LICENCE);
        if (bizLicencePicDto != null) {
            supplyDto.setBizLicencePic(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(bizLicencePicDto.getPicturePath()));
        }
        //银行卡照
        EPictureType bankPictureType = EPictureType.BANK_CARD_FRONT;
        String bankCardId = String.valueOf(memberDto.getBankCardId());
        if (memberDto.getBankCardId() == null) {
            bankPictureType = EPictureType.ACCOUNT_LICENCE;
            bankCardId = String.valueOf(memberDto.getCorpBankId());
        }
        PictureDto bankCardPicDto = pictureService.get(bankCardId, bankPictureType);
        if (bankCardPicDto != null) {
            supplyDto.setBankCardPic(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(bankCardPicDto.getPicturePath()));
        }
        //营业执照开始日期
        supplyDto.setBizEndDate(storeInfDto.getBizEndDate());
        //身份证照
        PictureDto idCardFrontPicDto = pictureService.get(userInfoDto.getUserId(), EPictureType.CARD_FRONT);
        PictureDto idCardBackPicDto = pictureService.get(userInfoDto.getUserId(), EPictureType.CARD_BACK);
        if (idCardFrontPicDto != null && idCardBackPicDto != null) {
            supplyDto.setUploadIdCard(true);
        }
        boolean flag = (supplyDto.getUploadIdCard() && bankCardPicDto != null && bizLicencePicDto != null
                && storeHeaderPicDto != null && storeInfDto.getBizEndDate() != null && storeInnerPicDto != null);
        if (flag) {
            supplyDto.setCompleteInfo(true);
        }
//        //判断合同是否已上传
//        YmMemberSignDto ymMemberSignDto = ymMemberSignService.getYmMemberSignByStoreId(storeInfDto.getStoreId());
//        if (ymMemberSignDto != null && StringUtils.isNotEmpty(ymMemberSignDto.getFirstPageFilePath()) && StringUtils.isNotEmpty(ymMemberSignDto.getLastPageFilePath()) && ymMemberSignDto.getSigned()) {
//            supplyDto.setUploadSign(true);
//        }
        return ResultDtoFactory.toAck("获取成功", supplyDto);
    }

    /**
     * 资料补全提交
     *
     * @return
     */
    @RequestMapping(value = "/saveSupply", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> saveSupply(@RequestBody SupplyDto supplyDto) {
        YMUserInfoDto yMUserInfoDto = yMUserInfoService.findByYmUserId(SecurityContext.getCurrentUserId());
        if (yMUserInfoDto == null || StringUtils.isEmpty(yMUserInfoDto.getUserId())) {
            return ResultDtoFactory.toNack("用户未绑定");//用户没有绑定微信，需先绑定微信
        }
        UserInfoDto userInfoDto = userService.findUserByUserId(yMUserInfoDto.getUserId());
        if (userInfoDto == null) {
            return ResultDtoFactory.toNack("用户不存在");
        } else if (!userInfoDto.getIdentityAuth()) {
            return ResultDtoFactory.toNack("未实名认证");
        }
        //云码店铺
        YMMemberDto memberDto = yMMemberService.getMemberByQrCodeNoAndUserId(supplyDto.getQrCodeNo(), userInfoDto.getUserId());
        if (memberDto == null) {
            return ResultDtoFactory.toNack("云码店铺不存在");
        }
        //店铺信息
        StoreInfDto storeInfDto = storeService.findByStoreId(memberDto.getStoreId());
        if (storeInfDto == null) {
            return ResultDtoFactory.toNack("店铺不存在");
        }
        IndustryDto industryDto = industryService.getByMcc(storeInfDto.getIndustryMcc());
        if (industryDto == null) {
            return ResultDtoFactory.toNack("行业不存在");
        }
        //查银行卡
        BankCardDto bankCardDto = new BankCardDto();
        if (memberDto.getBankCardId() != null) {
            bankCardDto = bankService.getBankCard(memberDto.getBankCardId());
            if (bankCardDto == null) {
                return ResultDtoFactory.toNack("银行卡不存在");
            }
            bankCardDto.setSubbranchName(bankCardDto.getBankName());
            bankCardDto.setBankCardName(userInfoDto.getUserName());
        } else if (memberDto.getCorpBankId() != null) {
            CorpBankDto corpBankDto = corpBankService.getCorpBankById(memberDto.getCorpBankId());
            if (corpBankDto == null) {
                return ResultDtoFactory.toNack("对公账号不存在");
            }
            bankCardDto.setBankName(corpBankDto.getOpeningBank());
            bankCardDto.setSubbranchName(corpBankDto.getBankBranch());
            bankCardDto.setBankCardNo(corpBankDto.getAccount());
            bankCardDto.setMobileNo(userInfoDto.getMobile());
            bankCardDto.setBankCardId(corpBankDto.getId());
            bankCardDto.setBankCode(corpBankDto.getBankShortName());
            bankCardDto.setBankCardName(corpBankDto.getAcctName());
        } else {
            return ResultDtoFactory.toNack("结算卡不存在");
        }
        BankMappingDto bankMappingDto = bankMappingService.getBankMappingByShortName(bankCardDto.getBankCode());
        if (bankMappingDto == null) {
            return ResultDtoFactory.toNack("该银行不支持");
        }
        //查费率
        YmMemberBizDto alipayBiz = yMMemberService.getMemberBizByMemberNoAndBizCode(memberDto.getMemberNo(), EBizCode.ALLIPAY);
        YmMemberBizDto whchatBiz = yMMemberService.getMemberBizByMemberNoAndBizCode(memberDto.getMemberNo(), EBizCode.WECHAT);
        YmMemberBizDto jdpayBiz = yMMemberService.getMemberBizByMemberNoAndBizCode(memberDto.getMemberNo(), EBizCode.JDPAY);
        YmMemberBizDto bestpayBiz = yMMemberService.getMemberBizByMemberNoAndBizCode(memberDto.getMemberNo(), EBizCode.BESTPAY);
        if (alipayBiz == null || whchatBiz == null || jdpayBiz == null || bestpayBiz == null) {
            return ResultDtoFactory.toNack("该店铺费率未设置");
        }
        SysAreaInfDto provinceArea = sysAreaInfService.getSysAreaInfById(Long.valueOf(storeInfDto.getProvinceId()));
        if (provinceArea == null) {
            return ResultDtoFactory.toNack("省未找到");
        }
        SysAreaInfDto cityArea = sysAreaInfService.getSysAreaInfById(Long.valueOf(storeInfDto.getCityId()));
        if (cityArea == null) {
            return ResultDtoFactory.toNack("市未找到");
        }
        SysAreaInfDto areaArea = sysAreaInfService.getSysAreaInfById(Long.valueOf(storeInfDto.getAreaId()));
        if (areaArea == null) {
            return ResultDtoFactory.toNack("区未找到");
        }
        //营业执照到期日
        if (storeInfDto.getBizEndDate() == null) {
            if (supplyDto.getBizEndDate() == null) {
                return ResultDtoFactory.toNack("营业执照开始日期必传");
            } else {
                storeInfDto.setBizEndDate(supplyDto.getBizEndDate());
                storeService.updateBizEndDate(storeInfDto);
            }

        }
        //身份证匹配
        PictureDto idCardFrontPicDto = pictureService.get(userInfoDto.getUserId(), EPictureType.CARD_FRONT);
        PictureDto idCardBackPicDto = pictureService.get(userInfoDto.getUserId(), EPictureType.CARD_BACK);
        if (idCardFrontPicDto == null || idCardBackPicDto == null) {
            if (supplyDto.getIdCardFrontPicBase64() == null || supplyDto.getIdCardBackPicBase64() == null) {
                return ResultDtoFactory.toNack("身份证照必传");
            }
            try {
                OCRDto ocrResult = yituService.ocr(supplyDto.getIdCardFrontPicBase64().replace("data:image/png;base64,", StringUtils.EMPTY));
                //比对上传身份证和实名认证身份证信息是否一致
                if (!StringUtils.equals(ocrResult.getName(), userInfoDto.getUserName()) || !StringUtils.equals(ocrResult.getIdCard(), userInfoDto.getIdCardNo())) {
                    return ResultDtoFactory.toNack("上传身份证照片与实名认证身份证信息不一致");
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
                return ResultDtoFactory.toNack("身份证照片识别失败，请重新上传", e);
            }
        }
        //营业执照
        PictureDto bizLicencePicDto = pictureService.get(String.valueOf(storeInfDto.getStoreId()), EPictureType.STORE_LICENCE);
        if (bizLicencePicDto == null && supplyDto.getBizLicencePicBase64() == null) {
            return ResultDtoFactory.toNack("营业执照必传");
        }
        //门头照
        PictureDto storeHeaderPicDto = pictureService.get(String.valueOf(storeInfDto.getStoreId()), EPictureType.STORE_HEADER);
        if (storeHeaderPicDto == null && supplyDto.getStoreHeaderPicBase64() == null) {
            return ResultDtoFactory.toNack("门头照必传");
        }
        //店内照
        PictureDto storeInnerPicDto = pictureService.get(String.valueOf(storeInfDto.getStoreId()), EPictureType.STORE_INNER);
        if (storeInnerPicDto == null && supplyDto.getStoreInnerPicBase64() == null) {
            return ResultDtoFactory.toNack("店内照必传");
        }
        //结算卡照获取
        PictureDto bankCardPicDto;
        EPictureType bankPictureType = EPictureType.BANK_CARD_FRONT;
        Long bankCardId = memberDto.getBankCardId();
        if (memberDto.getBankCardId() == null) {
            bankPictureType = EPictureType.ACCOUNT_LICENCE;
            bankCardId = memberDto.getCorpBankId();
        }
        //银行卡照
        bankCardPicDto = pictureService.get(String.valueOf(bankCardId), bankPictureType);
        if (bankCardPicDto == null && supplyDto.getBankCardPicBase64() == null) {
            return ResultDtoFactory.toNack("结算卡照必传");
        }
        //上传照片
        if (idCardFrontPicDto == null || idCardBackPicDto == null) {
            //身份证上传
            String idCardPic1Path;
            String idCardPic2Path;

            IDCardDto idCardDto = new IDCardDto();
            idCardDto.setUserId(userInfoDto.getUserId());
            idCardDto.setUserName(userInfoDto.getUserName());
            idCardDto.setIdCardNo(userInfoDto.getIdCardNo());
            try {
                File idCardPic1File = ImageUtils.GenerateImageByBase64(supplyDto.getIdCardFrontPicBase64());
                idCardPic1Path = fileStoreService.upload(EFileType.USER_INFO_IMG, idCardPic1File, idCardPic1File.getName());

                com.xinyunlian.jinfu.common.yitu.dto.resp.OCRDto ocrDto = yituService.ocr(supplyDto.getIdCardFrontPicBase64(), 1);
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
                File idCardPic2File = ImageUtils.GenerateImageByBase64(supplyDto.getIdCardBackPicBase64());
                idCardPic2Path = fileStoreService.upload(EFileType.USER_INFO_IMG, idCardPic2File, idCardPic2File.getName());

                com.xinyunlian.jinfu.common.yitu.dto.resp.OCRDto ocrDto = yituService.ocr(supplyDto.getIdCardBackPicBase64(), 2);
                idCardDto.setIdAuthority(ocrDto.getAgency());
                idCardDto.setIdExpiredBegin(DateHelper.getDate(ocrDto.getValidDateBegin(), DateHelper.SIMPLE_DATE_YMD));
                idCardDto.setIdExpiredEnd(DateHelper.getDate(ocrDto.getValidDateEnd(), DateHelper.SIMPLE_DATE_YMD));
                idCardDto.setCardBackFilePath(idCardPic2Path);
            } catch (IOException e) {
                LOGGER.error("身份证反面图片上传失败", e);
                return ResultDtoFactory.toNack("实名认证失败");
            } catch (Exception e) {
                LOGGER.error("读取身份证反面ocr错误", e);
                return ResultDtoFactory.toNack("实名认证失败");
            }
            userService.saveIDCard(idCardDto);
        }
        if (bizLicencePicDto == null) {
            try {
                PictureDto pictureDto = new PictureDto();
                File bizLicencePicFile = ImageUtils.GenerateImageByBase64(supplyDto.getBizLicencePicBase64());
                String picPath =
                        fileStoreService.upload(EFileType.STORE_INFO_IMG, bizLicencePicFile, bizLicencePicFile.getName());
                pictureDto.setPictureType(EPictureType.STORE_LICENCE);
                pictureDto.setPicturePath(picPath);
                pictureDto.setParentId(storeInfDto.getStoreId() + StringUtils.EMPTY);
                pictureService.savePicture(pictureDto);
            } catch (IOException e) {
                LOGGER.error("营业执照上传失败", e);
                return ResultDtoFactory.toNack("补全信息失败");
            }
        }
        if (storeHeaderPicDto == null) {
            try {
                PictureDto pictureDto = new PictureDto();
                File storeHeaderPicFile = ImageUtils.GenerateImageByBase64(supplyDto.getStoreHeaderPicBase64());
                String picPath =
                        fileStoreService.upload(EFileType.STORE_INFO_IMG, storeHeaderPicFile, storeHeaderPicFile.getName());
                pictureDto.setPictureType(EPictureType.STORE_HEADER);
                pictureDto.setPicturePath(picPath);
                pictureDto.setParentId(storeInfDto.getStoreId() + StringUtils.EMPTY);
                pictureService.savePicture(pictureDto);
            } catch (IOException e) {
                LOGGER.error("门头照上传失败", e);
                return ResultDtoFactory.toNack("补全信息失败");
            }
        }
        if (storeInnerPicDto == null) {
            try {
                PictureDto pictureDto = new PictureDto();
                File storeInnerPicFile = ImageUtils.GenerateImageByBase64(supplyDto.getStoreInnerPicBase64());
                String picPath =
                        fileStoreService.upload(EFileType.STORE_INFO_IMG, storeInnerPicFile, storeInnerPicFile.getName());
                pictureDto.setPictureType(EPictureType.STORE_INNER);
                pictureDto.setPicturePath(picPath);
                pictureDto.setParentId(storeInfDto.getStoreId() + StringUtils.EMPTY);
                pictureService.savePicture(pictureDto);
            } catch (IOException e) {
                LOGGER.error("门头照上传失败", e);
                return ResultDtoFactory.toNack("补全信息失败");
            }
        }
        //结算卡照
        if (bankCardPicDto == null) {
            try {
                PictureDto pictureDto = new PictureDto();
                File bankCardPicFile = ImageUtils.GenerateImageByBase64(supplyDto.getBankCardPicBase64());
                String picPath = fileStoreService.upload(EFileType.BANK_CARD_IMG, bankCardPicFile, bankCardPicFile.getName());
                pictureDto.setPictureType(bankPictureType);
                pictureDto.setPicturePath(picPath);
                pictureDto.setParentId(bankCardId + StringUtils.EMPTY);
                pictureService.savePicture(pictureDto);
            } catch (IOException e) {
                LOGGER.error("结算卡照上传失败", e);
                return ResultDtoFactory.toNack("补全信息失败");
            }
        }
        //身份证
        PictureDto idCardFrontPic = pictureService.get(userInfoDto.getUserId(), EPictureType.CARD_FRONT);
        PictureDto idCardBackPic = pictureService.get(userInfoDto.getUserId(), EPictureType.CARD_BACK);
        if (idCardFrontPic == null || idCardBackPic == null) {
            return ResultDtoFactory.toNack("请先上传身份证照");
        }
        //营业执照
        PictureDto bizLicensePicDto = pictureService.get(String.valueOf(storeInfDto.getStoreId()), EPictureType.STORE_LICENCE);
        if (bizLicensePicDto == null) {
            return ResultDtoFactory.toNack("请先上传营业执照");
        }
        //门头照
        PictureDto bizPlaceSnapshot1PicDto = pictureService.get(String.valueOf(storeInfDto.getStoreId()), EPictureType.STORE_HEADER);
        if (bizPlaceSnapshot1PicDto == null) {
            return ResultDtoFactory.toNack("请先上传门头照");
        }
        //店内照
        PictureDto bizPlaceSnapshot2PicDto = pictureService.get(String.valueOf(storeInfDto.getStoreId()), EPictureType.STORE_INNER);
        if (bizPlaceSnapshot2PicDto == null) {
            return ResultDtoFactory.toNack("请先上传店内照");
        }
        //结算卡照
        PictureDto bankCardPic = pictureService.get(String.valueOf(bankCardId), bankPictureType);
        if (bankCardPic == null) {
            return ResultDtoFactory.toNack("请先上传结算卡照");
        }
        //众码2.0进件
        //店铺进件
        CloudCodeMerchantDto cloudCodeMerchantDto = new CloudCodeMerchantDto();
        cloudCodeMerchantDto.setPartnerId(AppConfigUtil.getConfig("cashier.partnerid"));//partnerid
        cloudCodeMerchantDto.setOutMerchantId(String.valueOf(storeInfDto.getStoreId()));//storeId
        //cloudCodeMerchantDto.setEmail();//空
        cloudCodeMerchantDto.setMerchantName(storeInfDto.getStoreName());//店铺名
        //cloudCodeMerchantDto.setMerchantShortName();//不传
        cloudCodeMerchantDto.setAddress(storeInfDto.getProvince() + storeInfDto.getCity());
        cloudCodeMerchantDto.setLegalPerson(userInfoDto.getUserName());//姓名
        cloudCodeMerchantDto.setLegalPersonID(userInfoDto.getIdCardNo());//身份证
        cloudCodeMerchantDto.setFirstName(userInfoDto.getUserName());//姓名
        cloudCodeMerchantDto.setCellPhone(userInfoDto.getMobile());//手机
        cloudCodeMerchantDto.setBankCode(bankMappingDto.getBankCnapsCode());
        cloudCodeMerchantDto.setBankName(bankCardDto.getBankName());
        cloudCodeMerchantDto.setSubbranchName(bankCardDto.getBankName());//支行名 空
        cloudCodeMerchantDto.setBankProvinceCode(provinceArea.getGbCode());//省 code
        cloudCodeMerchantDto.setBankProvince(storeInfDto.getProvince());//
        cloudCodeMerchantDto.setBankCityCode(cityArea.getGbCode());
        cloudCodeMerchantDto.setBankCity(storeInfDto.getCity());
        cloudCodeMerchantDto.setCardName(bankCardDto.getBankCardName());//银行卡姓名
        cloudCodeMerchantDto.setIdNo(userInfoDto.getIdCardNo());//身份证
        cloudCodeMerchantDto.setCardNo(bankCardDto.getBankCardNo());
        cloudCodeMerchantDto.setBankCardPhone(bankCardDto.getMobileNo());//预留手机号
        cloudCodeMerchantDto.setAlipayRate(alipayBiz.getRate());
        cloudCodeMerchantDto.setWechatRate(whchatBiz.getRate());
        cloudCodeMerchantDto.setJdpayRate(jdpayBiz.getRate());
        cloudCodeMerchantDto.setBestpayRate(bestpayBiz.getRate());
        cloudCodeMerchantDto.setSettleDays(Integer.valueOf(memberDto.getSettlement().getCode()));//t+1=1 d+0=0
        cloudCodeMerchantDto.setBizLicenseCode(storeInfDto.getBizLicence());
        cloudCodeMerchantDto.setLegalPersonIDImgFront(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(idCardFrontPic.getPicturePath()));
        cloudCodeMerchantDto.setLegalPersonIDImgBack(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(idCardBackPic.getPicturePath()));
        cloudCodeMerchantDto.setBizLicenseImage(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(bizLicensePicDto.getPicturePath()));//营业执照
        cloudCodeMerchantDto.setBizPlaceSnapshot1Image(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(bizPlaceSnapshot1PicDto.getPicturePath()));//门头照
        cloudCodeMerchantDto.setBizPlaceSnapshot2Image(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(bizPlaceSnapshot2PicDto.getPicturePath()));//店内照
        cloudCodeMerchantDto.setHoldCardImage(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(idCardFrontPic.getPicturePath()));//手持身份证
        cloudCodeMerchantDto.setBankCardImage(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(bankCardPic.getPicturePath()));//银行卡照
        cloudCodeMerchantDto.setLicenseDate(DateHelper.formatDate(supplyDto.getBizEndDate()));//营业执照到期日
        cloudCodeMerchantDto.setAreaCode(areaArea.getGbCode());
        cloudCodeMerchantDto.setProvinceCode(provinceArea.getGbCode());
        cloudCodeMerchantDto.setCityCode(cityArea.getGbCode());
        cloudCodeMerchantDto.setProvince(storeInfDto.getProvince());
        cloudCodeMerchantDto.setCity(storeInfDto.getCity());
        cloudCodeMerchantDto.setArea(storeInfDto.getArea());
//        cloudCodeMerchantDto.setBizPlaceSnapshot2Image(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(ymMemberSignDto.getFirstPageFilePath()));//合同
//        cloudCodeMerchantDto.setBizPlaceSnapshot3Image(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(ymMemberSignDto.getLastPageFilePath()));//合同
        //cloudCodeMerchantDto.setOtherCertImage();
        cloudCodeMerchantDto.setAppId(AppConfigUtil.getConfig("cashier.appid"));
        cloudCodeMerchantDto.setBusinessCatagory1(industryDto.getIndType());
        cloudCodeMerchantDto.setBusinessCatagory2(industryDto.getIndDesc());
        cloudCodeMerchantDto.setMcc(industryDto.getMcc());
        Boolean flag = cloudCodeService.updateMerchant(cloudCodeMerchantDto);
        if (!flag) {
            return ResultDtoFactory.toNack("补全失败");
        }
//        YMMemberDto dto = new YMMemberDto();
//        dto.setId(memberDto.getId());
//        dto.setMemberNo(memberNo);
//        //民生银行
//        //dto.setMemberAuditStatus(EMemberAuditStatus.AUDITING);
//        //众码2.0
//        dto.setMemberAuditStatus(EMemberAuditStatus.SUCCESS);
//        yMMemberService.updateMemberNoAndAuditStatus(dto);
        return ResultDtoFactory.toAck("补全成功");
    }

    /**
     * 商户进件
     *
     * @return
     */
    /**
     * 暂时无用，先注释
     *
     * @return
     * @RequestMapping(value = "/saveSupplyMember", method = RequestMethod.POST)
     * @ResponseBody public ResultDto<Object> saveSupplyMember(@RequestBody SupplyDto supplyDto) {
     * YMUserInfoDto yMUserInfoDto = yMUserInfoService.findByYmUserId(SecurityContext.getCurrentUserId());
     * if (yMUserInfoDto == null || StringUtils.isEmpty(yMUserInfoDto.getUserId())) {
     * return ResultDtoFactory.toNack("用户未绑定");//用户没有绑定微信，需先绑定微信
     * }
     * UserInfoDto userInfoDto = userService.findUserByUserId(yMUserInfoDto.getUserId());
     * if (userInfoDto == null) {
     * return ResultDtoFactory.toNack("用户不存在");
     * } else if (!userInfoDto.getIdentityAuth()) {
     * return ResultDtoFactory.toNack("未实名认证");
     * }
     * //云码店铺
     * YMMemberDto memberDto = yMMemberService.getMemberByQrCodeNoAndUserId(supplyDto.getQrCodeNo(), userInfoDto.getUserId());
     * if (memberDto == null) {
     * return ResultDtoFactory.toNack("云码店铺不存在");
     * }
     * //店铺信息
     * StoreInfDto storeInfDto = storeService.findByStoreId(memberDto.getStoreId());
     * if (storeInfDto == null) {
     * return ResultDtoFactory.toNack("店铺不存在");
     * }
     * //查银行卡
     * BankCardDto bankCardDto = bankService.getBankCard(memberDto.getBankCardId());
     * if (bankCardDto == null) {
     * return ResultDtoFactory.toNack("银行卡不存在");
     * }
     * BankMappingDto bankMappingDto = bankMappingService.getBankMappingByShortName(bankCardDto.getBankCode());
     * if (bankMappingDto == null) {
     * return ResultDtoFactory.toNack("该银行不支持");
     * }
     * //查费率
     * YmMemberBizDto alipayBiz = yMMemberService.getMemberBizByMemberNoAndBizCode(memberDto.getMemberNo(), EBizCode.ALLIPAY);
     * YmMemberBizDto whchatBiz = yMMemberService.getMemberBizByMemberNoAndBizCode(memberDto.getMemberNo(), EBizCode.WECHAT);
     * if (alipayBiz == null || whchatBiz == null) {
     * return ResultDtoFactory.toNack("该店铺费率未设置");
     * }
     * //身份证
     * PictureDto idCardFrontPic = pictureService.get(userInfoDto.getUserId(), EPictureType.CARD_FRONT);
     * PictureDto idCardBackPic = pictureService.get(userInfoDto.getUserId(), EPictureType.CARD_BACK);
     * if (idCardFrontPic == null || idCardBackPic == null) {
     * return ResultDtoFactory.toNack("请先上传身份证照");
     * }
     * //门头照
     * PictureDto storeHeaderPicDto = pictureService.get(String.valueOf(storeInfDto.getStoreId()), EPictureType.STORE_HEADER);
     * if (storeHeaderPicDto == null) {
     * return ResultDtoFactory.toNack("门头照必传");
     * }
     * //门头照
     * PictureDto storeInnerPicDto = pictureService.get(String.valueOf(storeInfDto.getStoreId()), EPictureType.STORE_INNER);
     * if (storeInnerPicDto == null) {
     * return ResultDtoFactory.toNack("店内照必传");
     * }
     * //营业执照
     * PictureDto bizLicencePicDto = pictureService.get(String.valueOf(storeInfDto.getStoreId()), EPictureType.STORE_LICENCE);
     * if (bizLicencePicDto == null) {
     * return ResultDtoFactory.toNack("营业执照必传");
     * }
     * //银行卡照
     * PictureDto bankCardPicDto = pictureService.get(String.valueOf(memberDto.getBankCardId()), EPictureType.BANK_CARD_FRONT);
     * if (bankCardPicDto == null) {
     * return ResultDtoFactory.toNack("银行卡照必传");
     * }
     * //营业执照到期日
     * if (storeInfDto.getBizEndDate() == null) {
     * return ResultDtoFactory.toNack("营业执照到期日必传");
     * }
     * //        //判断合同是否已上传
     * //        YmMemberSignDto ymMemberSignDto = ymMemberSignService.getYmMemberSignByStoreId(storeInfDto.getStoreId());
     * //        if (ymMemberSignDto == null || StringUtils.isEmpty(ymMemberSignDto.getFirstPageFilePath()) || StringUtils.isEmpty(ymMemberSignDto.getLastPageFilePath()) || !ymMemberSignDto.getSigned()) {
     * //            return ResultDtoFactory.toNack("请先上传合同");
     * //        }
     * //店铺进件
     * SysAreaInfDto provinceArea = sysAreaInfService.getSysAreaInfById(Long.valueOf(storeInfDto.getProvinceId()));
     * SysAreaInfDto cityArea = sysAreaInfService.getSysAreaInfById(Long.valueOf(storeInfDto.getCityId()));
     * SysAreaInfDto areaArea = sysAreaInfService.getSysAreaInfById(Long.valueOf(storeInfDto.getAreaId()));
     * CloudCodeMerchantDto cloudCodeMerchantDto = new CloudCodeMerchantDto();
     * cloudCodeMerchantDto.setPartnerId(AppConfigUtil.getConfig("cashier.partnerid"));//partnerid
     * cloudCodeMerchantDto.setOutMerchantId(String.valueOf(storeInfDto.getStoreId()));//storeId
     * //cloudCodeMerchantDto.setEmail();//空
     * cloudCodeMerchantDto.setMerchantName(storeInfDto.getStoreName());//店铺名
     * //cloudCodeMerchantDto.setMerchantShortName();//不传
     * cloudCodeMerchantDto.setAddress(storeInfDto.getProvince() + storeInfDto.getCity());
     * cloudCodeMerchantDto.setLegalPerson(userInfoDto.getUserName());//姓名
     * cloudCodeMerchantDto.setLegalPersonID(userInfoDto.getIdCardNo());//身份证
     * cloudCodeMerchantDto.setFirstName(userInfoDto.getUserName());//姓名
     * cloudCodeMerchantDto.setCellPhone(userInfoDto.getMobile());//手机
     * cloudCodeMerchantDto.setBankCode(bankMappingDto.getBankCnapsCode());
     * cloudCodeMerchantDto.setBankName(bankCardDto.getBankName());
     * //cloudCodeMerchantDto.setSubbranchName();//支行名 空
     * cloudCodeMerchantDto.setBankProvinceCode(provinceArea.getGbCode());//省 code
     * cloudCodeMerchantDto.setBankProvince(storeInfDto.getProvince());//
     * cloudCodeMerchantDto.setBankCityCode(cityArea.getGbCode());
     * cloudCodeMerchantDto.setBankCity(storeInfDto.getCity());
     * cloudCodeMerchantDto.setCardName(userInfoDto.getUserName());//银行卡姓名
     * cloudCodeMerchantDto.setIdNo(userInfoDto.getIdCardNo());//身份证
     * cloudCodeMerchantDto.setCardNo(bankCardDto.getBankCardNo());
     * cloudCodeMerchantDto.setBankCardPhone(bankCardDto.getMobileNo());//预留手机号
     * cloudCodeMerchantDto.setAlipayRate(alipayBiz.getRate());
     * cloudCodeMerchantDto.setWechatRate(whchatBiz.getRate());
     * cloudCodeMerchantDto.setSettleDays(Integer.valueOf(memberDto.getSettlement().getCode()));//t+1=1 d+0=0
     * cloudCodeMerchantDto.setBizLicenseCode(storeInfDto.getBizLicence());
     * cloudCodeMerchantDto.setLegalPersonIDImgFront(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(idCardFrontPic.getPicturePath()));
     * cloudCodeMerchantDto.setLegalPersonIDImgBack(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(idCardBackPic.getPicturePath()));
     * cloudCodeMerchantDto.setBizLicenseImage(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(bizLicencePicDto.getPicturePath()));//营业执照
     * cloudCodeMerchantDto.setBizPlaceSnapshot1Image(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(storeHeaderPicDto.getPicturePath()));//门头照
     * cloudCodeMerchantDto.setBizPlaceSnapshot2Image(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(storeInnerPicDto.getPicturePath()));//店内照
     * cloudCodeMerchantDto.setHoldCardImage(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(idCardFrontPic.getPicturePath()));//手持身份证
     * cloudCodeMerchantDto.setBankCardImage(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(bankCardPicDto.getPicturePath()));//银行卡照
     * cloudCodeMerchantDto.setLicenseDate(DateHelper.formatDate(storeInfDto.getBizEndDate()));//营业执照到期日
     * cloudCodeMerchantDto.setAreaCode(areaArea.getGbCode());
     * cloudCodeMerchantDto.setProvinceCode(provinceArea.getGbCode());
     * cloudCodeMerchantDto.setCityCode(cityArea.getGbCode());
     * cloudCodeMerchantDto.setProvince(storeInfDto.getProvince());
     * cloudCodeMerchantDto.setCity(storeInfDto.getCity());
     * cloudCodeMerchantDto.setArea(storeInfDto.getArea());
     * //        cloudCodeMerchantDto.setBizPlaceSnapshot2Image(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(ymMemberSignDto.getFirstPageFilePath()));//合同
     * //        cloudCodeMerchantDto.setBizPlaceSnapshot3Image(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(ymMemberSignDto.getLastPageFilePath()));//合同
     * //cloudCodeMerchantDto.setOtherCertImage();
     * cloudCodeMerchantDto.setAppId(AppConfigUtil.getConfig("cashier.appid"));
     * String memberNo = cloudCodeService.saveMerchant(cloudCodeMerchantDto);
     * YMMemberDto dto = new YMMemberDto();
     * dto.setId(memberDto.getId());
     * dto.setMemberNo(memberNo);
     * //民生银行
     * //dto.setMemberAuditStatus(EMemberAuditStatus.AUDITING);
     * //众码2.0
     * dto.setMemberAuditStatus(EMemberAuditStatus.SUCCESS);
     * dto.setActivate(true);
     * yMMemberService.updateMemberNoAndAuditStatus(dto);
     * return ResultDtoFactory.toAck("补全信息成功");
     * }
     * *／
     * <p>
     * /**
     * 云码激活
     */
    @RequestMapping(value = "/activateYunMa", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "云码激活")
    public ResultDto<Object> activateYunMa(@RequestBody SupplyDto supplyDto) {
        YMUserInfoDto yMUserInfoDto = yMUserInfoService.findByYmUserId(SecurityContext.getCurrentUserId());
        if (yMUserInfoDto == null || StringUtils.isEmpty(yMUserInfoDto.getUserId())) {
            return ResultDtoFactory.toNack("用户未绑定");//用户没有绑定微信，需先绑定微信
        }
        UserInfoDto userInfoDto = userService.findUserByUserId(yMUserInfoDto.getUserId());
        if (userInfoDto == null) {
            return ResultDtoFactory.toNack("用户不存在");
        } else if (!userInfoDto.getIdentityAuth()) {
            return ResultDtoFactory.toNack("未实名认证");
        }
        //云码店铺
        YMMemberDto memberDto = yMMemberService.getMemberByQrCodeNoAndUserId(supplyDto.getQrCodeNo(), userInfoDto.getUserId());
        if (memberDto == null) {
            return ResultDtoFactory.toNack("云码店铺不存在");
        } else if (memberDto.getActivate()) {
            return ResultDtoFactory.toNack("云码店铺已激活");
        }
        yMMemberService.updateMemberActivate(memberDto);
        return ResultDtoFactory.toAck("激活成功");
    }

    /**
     * 快速注册（添加店铺）
     *
     * @param fastSaveStoreDto
     * @return
     */
    @RequestMapping(value = "/fastSaveStore", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("快速注册（添加店铺）")
    public ResultDto<Object> fastSaveStore(@RequestBody @Valid FastSaveStoreDto fastSaveStoreDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResultDtoFactory.toNack(result.getFieldError().getDefaultMessage());
        }
        YMUserInfoDto yMUserInfoDto = yMUserInfoService.findByYmUserId(SecurityContext.getCurrentUserId());
        if (yMUserInfoDto != null && !StringUtils.isEmpty(yMUserInfoDto.getUserId())) {
            return ResultDtoFactory.toNack("该用户已绑定");//用户已绑定微信
        }
        //用户注册
        UserInfoDto userInfo = userService.findUserByMobile(fastSaveStoreDto.getMobile());
        if (userInfo != null) {
            return ResultDtoFactory.toNack("该手机号已注册");
        }
        Boolean flag = smsService.confirmVerifyCode(fastSaveStoreDto.getMobile(), fastSaveStoreDto.getVerifyCode(), ESmsSendType.REGISTER);
        if (!flag) {
            return ResultDtoFactory.toNack("验证码错误");
        }
        if (StringUtils.isNotEmpty(fastSaveStoreDto.getTobaccoCertificateNo())) {
            //烟草证判断
            StoreInfDto store = storeService.findByTobaccoCertificateNo(fastSaveStoreDto.getTobaccoCertificateNo());
            if (store != null) {
                return ResultDtoFactory.toNack("烟草证号已存在");
            }
        }
        String encryptPwd = StringUtils.EMPTY;
        try {
            encryptPwd = EncryptUtil.encryptMd5(RandomUtil.getNumberStr(6));//初始密码随机6位数字
        } catch (Exception e) {
            return ResultDtoFactory.toNack("注册失败", e);
        }
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setMobile(fastSaveStoreDto.getMobile());
        userInfoDto.setSource(ESource.REGISTER);
        userInfoDto.setLoginPassword(encryptPwd);
        userInfoDto = userService.saveUser(userInfoDto);
        //清除验证码
        smsService.clearVerifyCode(fastSaveStoreDto.getMobile(), ESmsSendType.REGISTER);
        //绑定微信
        yMUserInfoDto = yMUserInfoService.findByUserId(userInfoDto.getUserId());
        if (yMUserInfoDto != null) {
            return ResultDtoFactory.toNack("该手机号已绑定");
        }
        yMUserInfoDto = yMUserInfoService.findByYmUserId(SecurityContext.getCurrentUserId());
        if (yMUserInfoDto == null) {
            SecurityContext.logout();
            return ResultDtoFactory.toNack("非法操作");
        }
        yMUserInfoDto.setUserId(userInfoDto.getUserId());
        yMUserInfoService.updateUserInfo(yMUserInfoDto);
        //添加店铺
        //判断商户是否有店铺，新增店铺行业是否和原店铺行业相同
        List<StoreInfDto> storeList = storeService.findByUserId(yMUserInfoDto.getUserId());
        if (!CollectionUtils.isEmpty(storeList)) {
            return ResultDtoFactory.toNack("该用户已存在店铺");
        }
        StoreInfDto storeInfDto = ConverterService.convert(fastSaveStoreDto, StoreInfDto.class);
        storeInfDto.setUserId(yMUserInfoDto.getUserId());
        storeInfDto.setSource(ESource.REGISTER);
        StoreInfDto storeInf = storeService.saveSupportAll(storeInfDto);
        apiBaiduService.updatePoint(storeInf);
        //图片处理
        try {
            File bizLicencePicFile = ImageUtils.GenerateImageByBase64(fastSaveStoreDto.getBizLicencePicBase64());
            String picPath1 = fileStoreService.upload(EFileType.STORE_INFO_IMG, bizLicencePicFile, bizLicencePicFile.getName());
            PictureDto pictureDto1 = new PictureDto();
            pictureDto1.setPictureType(EPictureType.STORE_LICENCE);
            pictureDto1.setPicturePath(picPath1);
            pictureDto1.setParentId(storeInf.getStoreId() + StringUtils.EMPTY);
            pictureService.savePicture(pictureDto1);

            File storeHeaderPicFile = ImageUtils.GenerateImageByBase64(fastSaveStoreDto.getStoreHeaderPicBase64());
            String picPath2 = fileStoreService.upload(EFileType.STORE_INFO_IMG, storeHeaderPicFile, storeHeaderPicFile.getName());
            PictureDto pictureDto2 = new PictureDto();
            pictureDto2.setPictureType(EPictureType.STORE_HEADER);
            pictureDto2.setPicturePath(picPath2);
            pictureDto2.setParentId(storeInf.getStoreId() + StringUtils.EMPTY);
            pictureService.savePicture(pictureDto2);
        } catch (IOException e) {
            LOGGER.error("图片上传失败", e);
            return ResultDtoFactory.toNack("添加店铺失败");
        }
        return ResultDtoFactory.toAck("添加成功", storeInf);
    }

    /**
     * 快速云码绑定
     *
     * @param fastBindYunmaDto
     * @return
     */
    @RequestMapping(value = "/fastBindYunMa", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("快速云码绑定")
    public ResultDto<Object> fastBindYunMa(@RequestBody FastBindYunmaDto fastBindYunmaDto) {
        YMUserInfoDto yMUserInfoDto = yMUserInfoService.findByYmUserId(SecurityContext.getCurrentUserId());
        if (yMUserInfoDto == null || StringUtils.isEmpty(yMUserInfoDto.getUserId())) {
            return ResultDtoFactory.toNack("用户未绑定");//用户没有绑定微信，需先绑定微信
        }
        StoreInfDto storeInfDto = storeService.findByStoreId(fastBindYunmaDto.getStoreId());
        if (storeInfDto == null) {
            return ResultDtoFactory.toNack("店铺不存在");
        }
        IndustryDto industryDto = industryService.getByMcc(storeInfDto.getIndustryMcc());
        if (industryDto == null) {
            return ResultDtoFactory.toNack("行业不存在");
        }
        if (!StringUtils.equals(storeInfDto.getUserId(), yMUserInfoDto.getUserId())) {
            return ResultDtoFactory.toNack("店铺信息不正确");
        }
        UserInfoDto userInfoDto = userService.findUserByUserId(storeInfDto.getUserId());
        if (userInfoDto == null) {
            return ResultDtoFactory.toNack("用户不存在");
        }
        //结算卡类型判断
        BankCardDto bankCardDto = new BankCardDto();
        if (fastBindYunmaDto.getBankCardType() == null || fastBindYunmaDto.getBankCardType() == 1) {//银行卡
            bankCardDto = bankService.getBankCard(fastBindYunmaDto.getBankCardId());
            if (bankCardDto == null) {
                return ResultDtoFactory.toNack("银行卡不存在");
            }
            bankCardDto.setSubbranchName(bankCardDto.getBankName());
            bankCardDto.setBankCardName(userInfoDto.getUserName());
        } else if (fastBindYunmaDto.getBankCardType() == 2) {//结算卡
            CorpBankDto corpBankDto = corpBankService.getCorpBankByUserId(userInfoDto.getUserId());
            if (corpBankDto == null) {
                return ResultDtoFactory.toNack("对公账号不存在");
            }
            bankCardDto.setBankName(corpBankDto.getOpeningBank());
            bankCardDto.setSubbranchName(corpBankDto.getBankBranch());
            bankCardDto.setBankCardNo(corpBankDto.getAccount());
            bankCardDto.setMobileNo(userInfoDto.getMobile());
            bankCardDto.setBankCardId(corpBankDto.getId());
            bankCardDto.setBankCode(corpBankDto.getBankShortName());
            bankCardDto.setBankCardName(corpBankDto.getAcctName());
        } else {
            return ResultDtoFactory.toNack("结算卡类型不正确");
        }
        BankMappingDto bankMappingDto = bankMappingService.getBankMappingByShortName(bankCardDto.getBankCode());
        if (bankMappingDto == null) {
            return ResultDtoFactory.toNack("该银行不支持");
        }
        YMMemberDto yMMemberDto = yMMemberService.getMemberByStoreId(fastBindYunmaDto.getStoreId());
        if (yMMemberDto != null) {
            return ResultDtoFactory.toNack("该店铺已绑定云码");
        }
        List<YmBizDto> bizList = ymBizService.findAll();
        if (bizList.isEmpty()) {
            return ResultDtoFactory.toNack("默认费率未设置");
        }
        SysAreaInfDto provinceArea = sysAreaInfService.getSysAreaInfById(Long.valueOf(storeInfDto.getProvinceId()));
        if (provinceArea == null) {
            return ResultDtoFactory.toNack("省未找到");
        }
        SysAreaInfDto cityArea = sysAreaInfService.getSysAreaInfById(Long.valueOf(storeInfDto.getCityId()));
        if (cityArea == null) {
            return ResultDtoFactory.toNack("市未找到");
        }
        SysAreaInfDto areaArea = sysAreaInfService.getSysAreaInfById(Long.valueOf(storeInfDto.getAreaId()));
        if (areaArea == null) {
            return ResultDtoFactory.toNack("区未找到");
        }
        //身份证
        PictureDto idCardFrontPic = pictureService.get(userInfoDto.getUserId(), EPictureType.CARD_FRONT);
        if (idCardFrontPic == null) {
            return ResultDtoFactory.toNack("请先上传身份证照");
        }
        //营业执照
        PictureDto bizLicensePicDto = pictureService.get(String.valueOf(storeInfDto.getStoreId()), EPictureType.STORE_LICENCE);
        if (bizLicensePicDto == null) {
            return ResultDtoFactory.toNack("请先上传营业执照");
        }
        //门头照
        PictureDto bizPlaceSnapshot1PicDto = pictureService.get(String.valueOf(storeInfDto.getStoreId()), EPictureType.STORE_HEADER);
        if (bizPlaceSnapshot1PicDto == null) {
            return ResultDtoFactory.toNack("请先上传门头照");
        }
        YmDepotDto dianziCode = new YmDepotDto();
        if (StringUtils.isEmpty(fastBindYunmaDto.getQrCodeNo()) || StringUtils.isEmpty(fastBindYunmaDto.getQrCodeUrl())) {
            //云码库数据处理
            dianziCode = ymDepotService.findNewBind();
            if (dianziCode == null) {
                return ResultDtoFactory.toNack("绑定失败，请联系客服");
            }
        }
        //众码2.0进件
        //店铺进件
        CloudCodeMerchantDto cloudCodeMerchantDto = new CloudCodeMerchantDto();
        cloudCodeMerchantDto.setPartnerId(AppConfigUtil.getConfig("cashier.partnerid"));//partnerid
        cloudCodeMerchantDto.setOutMerchantId(String.valueOf(storeInfDto.getStoreId()));//storeId
        //cloudCodeMerchantDto.setEmail();//空
        cloudCodeMerchantDto.setMerchantName(storeInfDto.getStoreName());//店铺名
        //cloudCodeMerchantDto.setMerchantShortName();//不传
        cloudCodeMerchantDto.setAddress(storeInfDto.getProvince() + storeInfDto.getCity());
        cloudCodeMerchantDto.setLegalPerson(userInfoDto.getUserName());//姓名
        cloudCodeMerchantDto.setLegalPersonID(userInfoDto.getIdCardNo());//身份证
        cloudCodeMerchantDto.setFirstName(userInfoDto.getUserName());//姓名
        cloudCodeMerchantDto.setCellPhone(userInfoDto.getMobile());//手机
        cloudCodeMerchantDto.setBankCode(bankMappingDto.getBankCnapsCode());
        cloudCodeMerchantDto.setBankName(bankCardDto.getBankName());
        cloudCodeMerchantDto.setSubbranchName(bankCardDto.getSubbranchName());//支行名 空
        cloudCodeMerchantDto.setBankProvinceCode(provinceArea.getGbCode());//省 code
        cloudCodeMerchantDto.setBankProvince(storeInfDto.getProvince());//
        cloudCodeMerchantDto.setBankCityCode(cityArea.getGbCode());
        cloudCodeMerchantDto.setBankCity(storeInfDto.getCity());
        cloudCodeMerchantDto.setCardName(bankCardDto.getBankCardName());//银行卡姓名
        cloudCodeMerchantDto.setIdNo(userInfoDto.getIdCardNo());//身份证
        cloudCodeMerchantDto.setCardNo(bankCardDto.getBankCardNo());
        cloudCodeMerchantDto.setBankCardPhone(bankCardDto.getMobileNo());//预留手机号
        for (YmBizDto ymBizDto : bizList) {
            if (EBizCode.ALLIPAY.equals(ymBizDto.getCode())) {
                cloudCodeMerchantDto.setAlipayRate(ymBizDto.getRate());
            } else if (EBizCode.WECHAT.equals(ymBizDto.getCode())) {
                cloudCodeMerchantDto.setWechatRate(ymBizDto.getRate());
            } else if (EBizCode.JDPAY.equals(ymBizDto.getCode())) {
                cloudCodeMerchantDto.setJdpayRate(ymBizDto.getRate());
            } else if (EBizCode.BESTPAY.equals(ymBizDto.getCode())) {
                cloudCodeMerchantDto.setBestpayRate(ymBizDto.getRate());
            } else if (EBizCode.QQPAY.equals(ymBizDto.getCode())) {
                cloudCodeMerchantDto.setQqpayRate(ymBizDto.getRate());
            } else if (EBizCode.BAIDUPAY.equals(ymBizDto.getCode())) {
                cloudCodeMerchantDto.setBaidupayRate(ymBizDto.getRate());
            }
        }
        cloudCodeMerchantDto.setSettleDays(Integer.valueOf(bizList.get(0).getSettlement().getCode()));//t+1=1 d+0=0
        cloudCodeMerchantDto.setBizLicenseCode(storeInfDto.getBizLicence());
        cloudCodeMerchantDto.setLegalPersonIDImgFront(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(idCardFrontPic.getPicturePath()));//身份证
        cloudCodeMerchantDto.setLegalPersonIDImgBack(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(idCardFrontPic.getPicturePath()));
        cloudCodeMerchantDto.setBizLicenseImage(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(bizLicensePicDto.getPicturePath()));//营业执照
        cloudCodeMerchantDto.setBizPlaceSnapshot1Image(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(bizPlaceSnapshot1PicDto.getPicturePath()));//门头照
        cloudCodeMerchantDto.setBizPlaceSnapshot2Image(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(bizPlaceSnapshot1PicDto.getPicturePath()));//店内照
        cloudCodeMerchantDto.setHoldCardImage(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(idCardFrontPic.getPicturePath()));//手持身份证
        cloudCodeMerchantDto.setBankCardImage(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(idCardFrontPic.getPicturePath()));//银行卡照
        cloudCodeMerchantDto.setLicenseDate("2099-01-01");//营业执照到期日
        cloudCodeMerchantDto.setAreaCode(areaArea.getGbCode());
        cloudCodeMerchantDto.setProvinceCode(provinceArea.getGbCode());
        cloudCodeMerchantDto.setCityCode(cityArea.getGbCode());
        cloudCodeMerchantDto.setProvince(storeInfDto.getProvince());
        cloudCodeMerchantDto.setCity(storeInfDto.getCity());
        cloudCodeMerchantDto.setArea(storeInfDto.getArea());
        //cloudCodeMerchantDto.setBizPlaceSnapshot2Image(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(ymMemberSignDto.getFirstPageFilePath()));//合同
        //cloudCodeMerchantDto.setBizPlaceSnapshot3Image(AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(ymMemberSignDto.getLastPageFilePath()));//合同
        //cloudCodeMerchantDto.setOtherCertImage();
        cloudCodeMerchantDto.setAppId(AppConfigUtil.getConfig("cashier.appid"));
        cloudCodeMerchantDto.setBusinessCatagory1(industryDto.getIndType());
        cloudCodeMerchantDto.setBusinessCatagory2(industryDto.getIndDesc());
        cloudCodeMerchantDto.setMcc(industryDto.getMcc());
        if (fastBindYunmaDto.getBankCardType() != null && fastBindYunmaDto.getBankCardType() == 2) {
            cloudCodeMerchantDto.setPublickAccount(true);//对公账号
        }
        String memberNo = cloudCodeService.saveMerchant(cloudCodeMerchantDto);
        Boolean applyed = true;
        //云码店铺添加处理
        YMMemberDto dto = new YMMemberDto();
        dto.setStoreId(storeInfDto.getStoreId());
        dto.setUserId(storeInfDto.getUserId());
        dto.setMemberNo(memberNo);
        if (StringUtils.isNotEmpty(fastBindYunmaDto.getQrCodeNo()) && StringUtils.isNotEmpty(fastBindYunmaDto.getQrCodeUrl())) {
            dto.setQrcodeNo(fastBindYunmaDto.getQrCodeNo());
            dto.setQrcodeUrl(fastBindYunmaDto.getQrCodeUrl());
            //更新码库状态
            YmDepotDto ymDepotDto = new YmDepotDto();
            ymDepotDto.setQrCodeNo(fastBindYunmaDto.getQrCodeNo());
            ymDepotDto.setStatus(EDepotStatus.BIND_USE);
            ymDepotDto.setReceiveStatus(EDepotReceiveStatus.RECEIVE);
            ymDepotService.updateStatusAndReceiveStatus(ymDepotDto);
        } else {
            if (dianziCode == null) {
                return ResultDtoFactory.toNack("绑定失败，请联系客服");
            }
            dto.setQrcodeNo(dianziCode.getQrCodeNo());
            dto.setQrcodeUrl(dianziCode.getQrCodeUrl());
            applyed = false;
        }
        if (fastBindYunmaDto.getBankCardType() != null && fastBindYunmaDto.getBankCardType() == 2) {//对公账号
            dto.setCorpBankId(bankCardDto.getBankCardId());
        } else {
            dto.setBankCardId(bankCardDto.getBankCardId());
        }
        dto.setSettlement(bizList.get(0).getSettlement());
        dto.setActivate(true);
        yMMemberService.addMember(dto);

        //推荐人处理
        if (StringUtils.isNotEmpty(fastBindYunmaDto.getReferee())) {
            DealerUserDto dealerUserDto = dealerUserService.findDealerUserByMobile(fastBindYunmaDto.getReferee());
            if (dealerUserDto != null) {
                yMMemberService.updateMemberDealerUserId(dto.getQrcodeNo(), dealerUserDto.getUserId());
            }
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("storeName", storeInfDto.getStoreName());
        resultMap.put("qrcodeUrl", dto.getQrcodeUrl());
        resultMap.put("applyed", applyed);
        return ResultDtoFactory.toAck("资料提交中，请稍等片刻", resultMap);
    }

}
