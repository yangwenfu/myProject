package com.xinyunlian.jinfu.bank.controller;

import com.xinyunlian.jinfu.bank.dto.*;
import com.xinyunlian.jinfu.bank.enums.ECardType;
import com.xinyunlian.jinfu.bank.service.BankService;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.dealer.enums.EDealerUserLogType;
import com.xinyunlian.jinfu.dealer.service.DealerUserLogService;
import com.xinyunlian.jinfu.dto.UserRealAuthDto;
import com.xinyunlian.jinfu.picture.dto.PictureDto;
import com.xinyunlian.jinfu.picture.enums.EPictureType;
import com.xinyunlian.jinfu.picture.service.PictureService;
import com.xinyunlian.jinfu.service.UserRealAuthService;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.store.service.StoreService;
import com.xinyunlian.jinfu.user.dto.DealerUserDto;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.enums.ESmsSendType;
import com.xinyunlian.jinfu.user.service.SmsService;
import com.xinyunlian.jinfu.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by menglei on 2017/1/5.
 */
@Controller
@RequestMapping(value = "bank")
public class BankController {
    @Autowired
    private BankService bankService;
    @Autowired
    private UserService userService;
    @Autowired
    private PictureService pictureService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private SmsService smsService;
    @Autowired
    private UserRealAuthService userRealAuthService;
    @Autowired
    private DealerUserLogService dealerUserLogService;

    /**
     * 获取个人银行卡信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/bankCardList", method = RequestMethod.GET)
    @ApiOperation(value = "获取个人银行卡信息")
    public ResultDto<List<BankCardInfoDto>> getBankCardList(@RequestParam Long storeId) {
        StoreInfDto storeInfDto = storeService.findByStoreId(storeId);
        if (storeInfDto == null) {
            return ResultDtoFactory.toNack("店铺不存在");
        }
        UserInfoDto userInfoDto = userService.findUserByUserId(storeInfDto.getUserId());
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
    @ResponseBody
    @RequestMapping(value = "/getCardBin", method = RequestMethod.GET)
    @ApiOperation(value = "通过银行卡获取银行卡所属银行")
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
     * 判断银行卡是否已上传图片 true：已上传，false：未上传
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/verifyBankCardPic", method = RequestMethod.GET)
    @ApiOperation(value = "判断银行卡是否已上传图片 true：已上传，false：未上传")
    public ResultDto<Object> getBankCardPic(@RequestParam String bankCardId) {
        //银行卡照
        PictureDto bankCardPicDto = pictureService.get(bankCardId, EPictureType.BANK_CARD_FRONT);
        if (bankCardPicDto == null) {
            return ResultDtoFactory.toAck("获取成功", false);
        }
        return ResultDtoFactory.toAck("获取成功", true);
    }

    /**
     * 判断银行卡是否已上传图片 true：已上传，false：未上传 bankType 银行卡=1 对公账号=2
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/v2/verifyBankCardPic", method = RequestMethod.GET)
    @ApiOperation(value = "判断银行卡是否已上传图片 true：已上传，false：未上传 bankCardType 银行卡=1 对公账号=2")
    public ResultDto<Object> getV2BankCardPic(@RequestParam String bankCardId, @RequestParam Integer bankCardType) {
        if (bankCardType == null) {
            return ResultDtoFactory.toNack("结算卡类型不正确");
        }
        EPictureType bankPictureType;
        if (bankCardType == 1) {
            bankPictureType = EPictureType.BANK_CARD_FRONT;
        } else if (bankCardType == 2) {
            bankPictureType = EPictureType.ACCOUNT_LICENCE;
        } else {
            return ResultDtoFactory.toNack("结算卡类型不正确");
        }
        //银行卡照
        PictureDto bankCardPicDto = pictureService.get(bankCardId, bankPictureType);
        if (bankCardPicDto == null) {
            return ResultDtoFactory.toAck("获取成功", false);
        }
        return ResultDtoFactory.toAck("获取成功", true);
    }

    /**
     * 添加银行卡
     *
     * @param bankInfoDto
     * @return
     */
    @RequestMapping(value = "/saveBankCard", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "添加银行卡")
    public ResultDto<Object> saveBankCard(@RequestBody BankInfoDto bankInfoDto) {
        UserInfoDto userInfoDto = userService.findUserByUserId(bankInfoDto.getUserId());
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
        bankCardDto.setUserId(bankInfoDto.getUserId());

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

        //插入分销员操作日志
        DealerUserDto dealerUserDto = (DealerUserDto) SecurityContext.getCurrentUser().getParamMap().get("dealerUserDto");
        dealerUserLogService.createDealerUserLog(dealerUserDto, bankInfoDto.getLogLng(), bankInfoDto.getLogLat(), bankInfoDto.getLogAddress(), bankInfoDto.getUserId(), null, "银行卡添加", EDealerUserLogType.ADDCARD);
        return ResultDtoFactory.toAck("添加成功");
    }

}
