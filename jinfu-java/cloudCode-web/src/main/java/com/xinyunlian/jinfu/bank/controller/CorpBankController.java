package com.xinyunlian.jinfu.bank.controller;

import com.xinyunlian.jinfu.bank.dto.CorpBankCardDto;
import com.xinyunlian.jinfu.bank.dto.CorpBankDto;
import com.xinyunlian.jinfu.bank.service.CorpBankService;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.picture.dto.PictureDto;
import com.xinyunlian.jinfu.picture.enums.EPictureType;
import com.xinyunlian.jinfu.picture.service.PictureService;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.service.UserService;
import com.xinyunlian.jinfu.yunma.dto.YMUserInfoDto;
import com.xinyunlian.jinfu.yunma.service.YMUserInfoService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by menglei on 2017年05月19日.
 */
@Controller
@RequestMapping(value = "corpbank")
public class CorpBankController {

    @Autowired
    private CorpBankService corpBankService;
    @Autowired
    private YMUserInfoService yMUserInfoService;
    @Autowired
    private UserService userService;
    @Autowired
    private PictureService pictureService;

    /**
     * 对公户银行卡添加
     *
     * @param corpBankDto
     * @return
     */
    @RequestMapping(value = "/saveCorpBankCard", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("对公户银行卡添加")
    public ResultDto<CorpBankDto> saveCorpBankCard(@RequestBody CorpBankDto corpBankDto) {
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
        corpBankDto.setUserId(userInfoDto.getUserId());
        CorpBankDto dto = corpBankService.addCorpBank(corpBankDto);
        return ResultDtoFactory.toAck("添加成功", dto);
    }

    /**
     * 获取对公户银行卡
     *
     * @return
     */
    @RequestMapping(value = "/corpBankCard", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<CorpBankCardDto> getCorpBankCard() {
        YMUserInfoDto yMUserInfoDto = yMUserInfoService.findByYmUserId(SecurityContext.getCurrentUserId());
        if (yMUserInfoDto == null || StringUtils.isEmpty(yMUserInfoDto.getUserId())) {
            return ResultDtoFactory.toNack("用户未绑定");//用户没有绑定微信，需先绑定微信
        }
        UserInfoDto userInfoDto = userService.findUserByUserId(yMUserInfoDto.getUserId());
        if (userInfoDto == null) {
            return ResultDtoFactory.toNack("用户不存在");
        }
        CorpBankDto corpBankDto = corpBankService.getCorpBankByUserId(userInfoDto.getUserId());
        CorpBankCardDto corpBankCardDto = ConverterService.convert(corpBankDto, CorpBankCardDto.class);
        if (corpBankDto != null) {
            PictureDto pictureDto = pictureService.get(String.valueOf(corpBankDto.getId()), EPictureType.ACCOUNT_LICENCE);
            if (pictureDto != null) {
                corpBankCardDto.setUploadPic(true);
            }
        }
        return ResultDtoFactory.toAck("获取成功", corpBankCardDto);
    }

}
