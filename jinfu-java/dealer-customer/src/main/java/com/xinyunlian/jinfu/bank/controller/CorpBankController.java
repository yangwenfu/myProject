package com.xinyunlian.jinfu.bank.controller;

import com.xinyunlian.jinfu.bank.dto.CorpBankCardDto;
import com.xinyunlian.jinfu.bank.dto.CorpBankDto;
import com.xinyunlian.jinfu.bank.service.CorpBankService;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.picture.dto.PictureDto;
import com.xinyunlian.jinfu.picture.enums.EPictureType;
import com.xinyunlian.jinfu.picture.service.PictureService;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by menglei on 2017年05月19日.
 */
@Controller
@RequestMapping(value = "corpbank")
public class CorpBankController {

    @Autowired
    private CorpBankService corpBankService;
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
        UserInfoDto userInfoDto = userService.findUserByUserId(corpBankDto.getUserId());
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
    public ResultDto<CorpBankCardDto> getCorpBankCard(@RequestParam String userId) {
        UserInfoDto userInfoDto = userService.findUserByUserId(userId);
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
