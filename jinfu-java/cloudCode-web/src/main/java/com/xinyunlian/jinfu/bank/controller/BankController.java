package com.xinyunlian.jinfu.bank.controller;

import com.xinyunlian.jinfu.bank.dto.*;
import com.xinyunlian.jinfu.bank.enums.ECardType;
import com.xinyunlian.jinfu.bank.service.BankService;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.fileStore.FileStoreService;
import com.xinyunlian.jinfu.common.fileStore.enums.EFileType;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.security.StaticResourceSecurity;
import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.common.util.ImageUtils;
import com.xinyunlian.jinfu.common.yitu.YituService;
import com.xinyunlian.jinfu.dto.UserRealAuthDto;
import com.xinyunlian.jinfu.picture.dto.PictureDto;
import com.xinyunlian.jinfu.picture.enums.EPictureType;
import com.xinyunlian.jinfu.picture.service.PictureService;
import com.xinyunlian.jinfu.service.UserRealAuthService;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.store.service.StoreService;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.dto.req.IDCardDto;
import com.xinyunlian.jinfu.user.enums.ESmsSendType;
import com.xinyunlian.jinfu.user.service.SmsService;
import com.xinyunlian.jinfu.user.service.UserService;
import com.xinyunlian.jinfu.yunma.dto.YMUserInfoDto;
import com.xinyunlian.jinfu.yunma.service.YMUserInfoService;
import io.swagger.annotations.ApiOperation;
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
 * Created by menglei on 2017/1/5.
 */
@Controller
@RequestMapping(value = "bank")
public class BankController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BankController.class);

    @Autowired
    private BankService bankService;
    @Autowired
    private UserService userService;
    @Autowired
    private YMUserInfoService yMUserInfoService;
    @Autowired
    private SmsService smsService;
    @Autowired
    private UserRealAuthService userRealAuthService;
    @Autowired
    private PictureService pictureService;
    @Autowired
    private FileStoreService fileStoreService;
    @Autowired
    private YituService yituService;
    @Autowired
    private StoreService storeService;

    /**
     * 获取银行列表
     *
     * @return
     */
    @RequestMapping(value = "/bankList", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<List<BankInfDto>> getBankList() {
        List<BankInfDto> bankInfDtoList = bankService.findBankInfAll();
        return ResultDtoFactory.toAck("获取成功", bankInfDtoList);
    }

    /**
     * 添加银行卡
     *
     * @param bankInfoDto
     * @return
     */
    @RequestMapping(value = "/saveBankCard", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> saveBankCard(@RequestBody BankInfoDto bankInfoDto) {
        YMUserInfoDto yMUserInfoDto = yMUserInfoService.findByYmUserId(SecurityContext.getCurrentUserId());
        if (yMUserInfoDto == null || StringUtils.isEmpty(yMUserInfoDto.getUserId())) {
            return ResultDtoFactory.toNack("用户未绑定");//用户没有绑定微信，需先绑定微信
        }
        UserInfoDto userInfoDto = userService.findUserByUserId(yMUserInfoDto.getUserId());
        if (userInfoDto == null) {
            return ResultDtoFactory.toNack("用户不存在");
        }
        if (!userInfoDto.getIdentityAuth()) {
            return ResultDtoFactory.toNack("用户没有实名认证");
        }
        bankInfoDto.setUserName(userInfoDto.getUserName());
        bankInfoDto.setIdCardNo(userInfoDto.getIdCardNo());
        BankCardDto bankCardDto = ConverterService.convert(bankInfoDto, BankCardDto.class);
        bankCardDto.setBankCardName(bankInfoDto.getUserName());
        bankCardDto.setMobileNo(bankInfoDto.getMobile());
        bankCardDto.setUserId(yMUserInfoDto.getUserId());

        boolean flag = smsService.confirmVerifyCode(bankCardDto.getMobileNo(), bankCardDto.getVerifyCode(), ESmsSendType.BANKCARD);
        if (!flag) {
            return ResultDtoFactory.toNack("验证码错误");
        }
        UserRealAuthDto userRealAuthDto = new UserRealAuthDto();
        userRealAuthDto.setIdCardNo(bankCardDto.getIdCardNo());
        userRealAuthDto.setBankCardNo(bankCardDto.getBankCardNo());
        userRealAuthDto.setName(bankCardDto.getBankCardName());
        userRealAuthDto.setPhone(bankCardDto.getMobileNo());
        boolean real = userRealAuthService.realAuth(userRealAuthDto);
        if (real == false) {
            return ResultDtoFactory.toNack("添加银行卡失败");
        }
        bankService.saveBankCard(bankCardDto);
        smsService.clearVerifyCode(bankCardDto.getMobileNo(), ESmsSendType.BANKCARD);
        return ResultDtoFactory.toAck("添加成功");
    }

    /**
     * 获取个人银行卡信息
     *
     * @return
     */
    @RequestMapping(value = "/bankCardList", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getBankCardList() {
        YMUserInfoDto yMUserInfoDto = yMUserInfoService.findByYmUserId(SecurityContext.getCurrentUserId());
        if (yMUserInfoDto == null || StringUtils.isEmpty(yMUserInfoDto.getUserId())) {
            return ResultDtoFactory.toNack("用户未绑定");//用户没有绑定微信，需先绑定微信
        }
        UserInfoDto userInfoDto = userService.findUserByUserId(yMUserInfoDto.getUserId());
        if (userInfoDto == null) {
            return ResultDtoFactory.toNack("用户不存在");
        }
        List<BankCardDto> bankCardDtoList = bankService.findByUserId(userInfoDto.getUserId());
        List<BankCardInfoDto> list = new ArrayList<>();
        for (BankCardDto bankCardDto : bankCardDtoList) {
            BankCardInfoDto bankCardInfoDto = ConverterService.convert(bankCardDto, BankCardInfoDto.class);
            list.add(bankCardInfoDto);
        }
        Collections.sort(list, (arg0, arg1) -> arg1.getBankCardId().compareTo(arg0.getBankCardId()));
        return ResultDtoFactory.toAck("获取成功", list);
    }

    /**
     * 通过银行卡获取银行卡所属银行
     *
     * @return
     */
    @RequestMapping(value = "/getCardBin", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getCardBin(@RequestParam String cardNo) {
        BankCardBinDto bankCardBinDto = bankService.findByNumLengthAndBin(cardNo);
        CardBinDto cardBinDto = ConverterService.convert(bankCardBinDto, CardBinDto.class);
        if (bankCardBinDto != null) {
            if (null == bankCardBinDto.getBankId()) {
                return ResultDtoFactory.toNack("对不起，暂不支持该银行卡，请更换银行卡");
            }
            if (!StringUtils.equals("借记卡", bankCardBinDto.getCardType())) {
                return ResultDtoFactory.toNack("对不起，暂仅支持借记卡，请更换银行卡");
            }
            BankInfDto bankInfDto = bankService.getBank(bankCardBinDto.getBankId());
            cardBinDto.setBankShortName(bankInfDto.getBankCode());
            cardBinDto.setCardType(ECardType.DEBIT);
            cardBinDto.setBankLogo(bankInfDto.getBankLogo());
            return ResultDtoFactory.toAck("获取成功", cardBinDto);
        } else {
            return ResultDtoFactory.toNack("卡号输入错误，请核对卡号");
        }
    }

    /**
     * 获取银行卡照片
     *
     * @return
     */
    @RequestMapping(value = "/bankCardPic", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getBankCardPic(@RequestParam String bankCardId) {
        YMUserInfoDto yMUserInfoDto = yMUserInfoService.findByYmUserId(SecurityContext.getCurrentUserId());
        if (yMUserInfoDto == null || StringUtils.isEmpty(yMUserInfoDto.getUserId())) {
            return ResultDtoFactory.toNack("用户未绑定");//用户没有绑定微信，需先绑定微信
        }
        UserInfoDto userInfoDto = userService.findUserByUserId(yMUserInfoDto.getUserId());
        if (userInfoDto == null) {
            return ResultDtoFactory.toNack("用户不存在");
        }
        //银行卡照
        PictureDto bankCardPicDto = pictureService.get(bankCardId, EPictureType.BANK_CARD_FRONT);
        if (bankCardPicDto == null) {
            return ResultDtoFactory.toAck("获取成功", null);
        }
        return ResultDtoFactory.toAck("获取成功", AppConfigUtil.getConfig("file.addr") + StaticResourceSecurity.getSecurityURI(bankCardPicDto.getPicturePath()));
    }

    /**
     * 快速添加银行卡
     *
     * @param fastSaveBankCardDto
     * @return
     */
    @RequestMapping(value = "/fastSaveBankCard", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("快速添加银行卡")
    public ResultDto<Object> fastSaveBankCard(@RequestBody FastSaveBankCardDto fastSaveBankCardDto) {
        YMUserInfoDto yMUserInfoDto = yMUserInfoService.findByYmUserId(SecurityContext.getCurrentUserId());
        if (yMUserInfoDto == null || StringUtils.isEmpty(yMUserInfoDto.getUserId())) {
            return ResultDtoFactory.toNack("用户未绑定");//用户没有绑定微信，需先绑定微信
        }
        UserInfoDto userInfoDto = userService.findUserByUserId(yMUserInfoDto.getUserId());
        if (userInfoDto == null) {
            return ResultDtoFactory.toNack("用户不存在");
        }
        StoreInfDto storeInfDto = storeService.findByStoreId(fastSaveBankCardDto.getStoreId());
        if (storeInfDto == null) {
            return ResultDtoFactory.toNack("店铺不存在");
        }
        boolean flag = smsService.confirmVerifyCode(fastSaveBankCardDto.getBankMobile(), fastSaveBankCardDto.getVerifyCode(), ESmsSendType.BANKCARD);
        if (!flag) {
            return ResultDtoFactory.toNack("验证码错误");
        }
        if (!userInfoDto.getIdentityAuth()) {
            //实名认证
            //身份证上传
            IDCardDto idCardDto = new IDCardDto();
            idCardDto.setUserId(userInfoDto.getUserId());
            idCardDto.setUserName(fastSaveBankCardDto.getUserName());
            idCardDto.setIdCardNo(fastSaveBankCardDto.getIdCardNo());
            try {
                File idCardPic1File = ImageUtils.GenerateImageByBase64(fastSaveBankCardDto.getIdCardFrontBase64());
                String idCardPic1Path = fileStoreService.upload(EFileType.USER_INFO_IMG, idCardPic1File, idCardPic1File.getName());

                com.xinyunlian.jinfu.common.yitu.dto.resp.OCRDto ocrDto = yituService.ocr(fastSaveBankCardDto.getIdCardFrontBase64(), 1);
                idCardDto.setNationAddress(ocrDto.getAddress());
                idCardDto.setSex(ocrDto.getGender());
                idCardDto.setNation(ocrDto.getNation());
                idCardDto.setBirthDate(DateHelper.getDate(ocrDto.getBirthday(), "yyyy.MM.dd"));
                idCardDto.setCardFrontFilePath(idCardPic1Path);
            } catch (IOException e) {
                LOGGER.error("身份证图片上传失败", e);
                return ResultDtoFactory.toNack("实名认证失败");
            } catch (Exception e) {
                LOGGER.error("读取身份证正面ocr错误", e);
                return ResultDtoFactory.toNack("实名认证失败");
            }
            boolean flags = userService.certifyWithCard(idCardDto);
            if (!flags) {
                return ResultDtoFactory.toNack("实名认证失败");
            }
        }
        //添加银行卡
        BankCardDto bankCardDto = new BankCardDto();
        if (userInfoDto.getIdentityAuth()) {//已实名认证
            bankCardDto.setIdCardNo(userInfoDto.getIdCardNo());
        } else {
            bankCardDto.setIdCardNo(fastSaveBankCardDto.getIdCardNo());
        }
        bankCardDto.setBankCardName(fastSaveBankCardDto.getUserName());
        bankCardDto.setMobileNo(fastSaveBankCardDto.getBankMobile());
        bankCardDto.setUserId(yMUserInfoDto.getUserId());
        bankCardDto.setBankCardNo(fastSaveBankCardDto.getBankCardNo());
        bankCardDto.setBankId(fastSaveBankCardDto.getBankId());

        UserRealAuthDto userRealAuthDto = new UserRealAuthDto();
        userRealAuthDto.setIdCardNo(bankCardDto.getIdCardNo());
        userRealAuthDto.setBankCardNo(bankCardDto.getBankCardNo());
        userRealAuthDto.setName(bankCardDto.getBankCardName());
        userRealAuthDto.setPhone(bankCardDto.getMobileNo());
        boolean real = userRealAuthService.realAuth(userRealAuthDto);
        userInfoDto = userService.findUserByUserId(yMUserInfoDto.getUserId());//用户信息
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("userInfoDto", userInfoDto);
        resultMap.put("storeInfDto", storeInfDto);
        if (real == false) {
            return ResultDtoFactory.toNack("添加银行卡失败", userInfoDto);
        }
        BankCardDto bankCard = bankService.saveBankCard(bankCardDto);
        smsService.clearVerifyCode(bankCardDto.getMobileNo(), ESmsSendType.BANKCARD);
        resultMap.put("bankCardDto", bankCard);
        resultMap.put("bankCardType", 1);//1=银行卡 2=结算卡
        resultMap.put("referee", fastSaveBankCardDto.getReferee());
        return ResultDtoFactory.toAck("添加成功", resultMap);
    }

}
