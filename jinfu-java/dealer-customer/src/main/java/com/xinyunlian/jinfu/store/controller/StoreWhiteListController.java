package com.xinyunlian.jinfu.store.controller;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.fileStore.FileStoreService;
import com.xinyunlian.jinfu.common.fileStore.enums.EFileType;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.util.ImageUtils;
import com.xinyunlian.jinfu.store.dto.StoreWhiteListDto;
import com.xinyunlian.jinfu.store.dto.StoreWhiteSignDto;
import com.xinyunlian.jinfu.store.dto.req.StoreWhiteSignInDto;
import com.xinyunlian.jinfu.store.dto.resp.StoreWhiteListRemarkDto;
import com.xinyunlian.jinfu.store.enums.EStoreWhiteListRemark;
import com.xinyunlian.jinfu.store.enums.EStoreWhiteListStatus;
import com.xinyunlian.jinfu.store.service.StoreWhiteListService;
import com.xinyunlian.jinfu.store.service.StoreWhiteSignService;
import com.xinyunlian.jinfu.user.dto.DealerUserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by menglei on 2017年07月02日.
 */
@Controller
@RequestMapping(value = "storeWhiteList")
@Api(description = "白名单店铺相关")
public class StoreWhiteListController {

    @Autowired
    private StoreWhiteListService storeWhiteListService;
    @Autowired
    private FileStoreService fileStoreService;
    @Autowired
    private StoreWhiteSignService storeWhiteSignService;

    @ResponseBody
    @RequestMapping(value = "/remarkList", method = RequestMethod.GET)
    @ApiOperation("白名单店铺拜访结果列表")
    public ResultDto<List<StoreWhiteListRemarkDto>> getRemarkList() {
        StoreWhiteListRemarkDto dto;
        List<StoreWhiteListRemarkDto> list = new ArrayList<>();
        dto = new StoreWhiteListRemarkDto();
        dto.setKey(EStoreWhiteListRemark.DEMAND.getCode());
        dto.setValue(EStoreWhiteListRemark.DEMAND.getText());
        list.add(dto);
        dto = new StoreWhiteListRemarkDto();
        dto.setKey(EStoreWhiteListRemark.UNWANTED.getCode());
        dto.setValue(EStoreWhiteListRemark.UNWANTED.getText());
        list.add(dto);
        dto = new StoreWhiteListRemarkDto();
        dto.setKey(EStoreWhiteListRemark.NOTONESELF.getCode());
        dto.setValue(EStoreWhiteListRemark.NOTONESELF.getText());
        list.add(dto);
        dto = new StoreWhiteListRemarkDto();
        dto.setKey(EStoreWhiteListRemark.MISSING.getCode());
        dto.setValue(EStoreWhiteListRemark.MISSING.getText());
        list.add(dto);
        dto = new StoreWhiteListRemarkDto();
        dto.setKey(EStoreWhiteListRemark.REGISTERED.getCode());
        dto.setValue(EStoreWhiteListRemark.REGISTERED.getText());
        list.add(dto);
        return ResultDtoFactory.toAck("获取成功", list);
    }

    @RequestMapping(value = "/reportRemark", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("白名单店铺拜访结果上报")
    public ResultDto<Object> reportRemark(@RequestBody StoreWhiteListDto storeWhiteListDto) {
        StoreWhiteListDto dto = storeWhiteListService.findOne(storeWhiteListDto.getId());
        if (dto == null || !SecurityContext.getCurrentUserId().equals(dto.getUserId())) {
            return ResultDtoFactory.toNack("店铺不存在");
        } else if (EStoreWhiteListStatus.NOSIGNIN.equals(dto.getStatus())) {
            return ResultDtoFactory.toNack("店铺未签到");
        }
        storeWhiteListService.updateRemark(storeWhiteListDto);
        return ResultDtoFactory.toAck("提交成功");
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("白名单店铺详情")
    public ResultDto<Object> detail(@RequestParam Long id) {
        StoreWhiteListDto dto = storeWhiteListService.findOne(id);
        if (dto == null || !SecurityContext.getCurrentUserId().equals(dto.getUserId())) {
            return ResultDtoFactory.toNack("店铺不存在");
        }
        return ResultDtoFactory.toAck("获取成功", dto);
    }

    /**
     * 签到
     *
     * @param storeWhiteSignInDto
     * @return
     */
    @RequestMapping(value = "/signIn", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "白名单店铺签到")
    public ResultDto<Object> signIn(@RequestBody StoreWhiteSignInDto storeWhiteSignInDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResultDtoFactory.toNack(result.getFieldError().getDefaultMessage());
        }
        //判断该店铺是否已签到
        StoreWhiteListDto storeWhiteListDto = storeWhiteListService.findOne(storeWhiteSignInDto.getStoreId());
        if (storeWhiteListDto == null) {
            return ResultDtoFactory.toNack("店铺不存在");
        } else if (EStoreWhiteListStatus.SIGNEDIN.equals(storeWhiteListDto.getStatus())) {
            return ResultDtoFactory.toNack("该店铺已签到");
        }
        String signInStoreHeader;
        try {
            File signInStoreHeaderFile = ImageUtils.GenerateImageByBase64(storeWhiteSignInDto.getSignInStoreHeaderBase64());
            signInStoreHeader = fileStoreService.upload(EFileType.DEALER_USER_IMG, signInStoreHeaderFile, signInStoreHeaderFile.getName());
        } catch (IOException e) {
            return ResultDtoFactory.toNack("签到失败", e);
        }
        StoreWhiteSignDto storeWhiteSignDto = ConverterService.convert(storeWhiteSignInDto, StoreWhiteSignDto.class);
        DealerUserDto dealerUserDto = (DealerUserDto) SecurityContext.getCurrentUser().getParamMap().get("dealerUserDto");
        storeWhiteSignDto.setDealerId(dealerUserDto.getDealerId());
        storeWhiteSignDto.setUserId(dealerUserDto.getUserId());
        storeWhiteSignDto.setSignInStoreHeader(signInStoreHeader);
        storeWhiteSignDto.setSignInTime(new Date());
        storeWhiteSignService.createSignIn(storeWhiteSignDto);
        return ResultDtoFactory.toAck("签到成功");
    }

}
