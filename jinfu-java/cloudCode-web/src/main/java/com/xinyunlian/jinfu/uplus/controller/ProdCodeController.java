package com.xinyunlian.jinfu.uplus.controller;

import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.order.dto.ThirdOrderProdDto;
import com.xinyunlian.jinfu.order.service.ThirdOrderProdService;
import com.xinyunlian.jinfu.qrcode.dto.ProdCodeDto;
import com.xinyunlian.jinfu.qrcode.dto.ProdInfDto;
import com.xinyunlian.jinfu.qrcode.enums.EProdCodeStatus;
import com.xinyunlian.jinfu.qrcode.service.ProdCodeService;
import com.xinyunlian.jinfu.qrcode.service.ProdInfService;
import com.xinyunlian.jinfu.store.dto.FranchiseDto;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.store.service.FranchiseService;
import com.xinyunlian.jinfu.store.service.StoreService;
import com.xinyunlian.jinfu.uplus.dto.ProdCodeBindDto;
import com.xinyunlian.jinfu.yunma.dto.YMUserInfoDto;
import com.xinyunlian.jinfu.yunma.service.YMUserInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by menglei on 2017/3/20.
 */
@Controller
@RequestMapping(value = "uplus/prodCode")
public class ProdCodeController {

    @Autowired
    private ThirdOrderProdService thirdOrderProdService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private ProdInfService prodInfService;
    @Autowired
    private ProdCodeService prodCodeService;
    @Autowired
    private YMUserInfoService yMUserInfoService;
    @Autowired
    private FranchiseService franchiseService;

    /**
     * 验证商品码
     *
     * @return
     */
    @RequestMapping(value = "/checkCode", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> checkCode(@RequestParam String qrCodeUrl) {

        if (StringUtils.isEmpty(qrCodeUrl)) {
            return ResultDtoFactory.toNack("商品码地址必传");
        }
        YMUserInfoDto yMUserInfoDto = yMUserInfoService.findByYmUserId(SecurityContext.getCurrentUserId());
        if (yMUserInfoDto == null || StringUtils.isEmpty(yMUserInfoDto.getUserId())) {
            return ResultDtoFactory.toNack("用户未绑定");//用户没有绑定微信，需先绑定微信
        }
        List<StoreInfDto> storeList = storeService.findByUserId(yMUserInfoDto.getUserId());
        List<Long> storeIds = new ArrayList<>();
        for (StoreInfDto storeInfDto : storeList) {
            storeIds.add(storeInfDto.getStoreId());
        }
        if (storeIds.isEmpty()) {
            return ResultDtoFactory.toNack("店铺不存在");
        }
        //这里需要判断商铺是不是加盟店
        List<FranchiseDto> franchises = franchiseService.getByStoreIds(storeIds);
        if (franchises == null || franchises.isEmpty()) {
            return ResultDtoFactory.toNack("店铺不是加盟店");
        }

        ProdCodeDto prodCodeDto = prodCodeService.getByQrCodeUrl(qrCodeUrl);
        if (prodCodeDto == null) {
            return ResultDtoFactory.toNack("商品码不存在");
        } else if (!EProdCodeStatus.UNUSED.equals(prodCodeDto.getStatus())) {
            return ResultDtoFactory.toNack("商品码已使用");
        }
        return ResultDtoFactory.toAck("获取成功", prodCodeDto);
    }

    /**
     * 绑定商品码
     *
     * @param prodCodeBindDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/bind", method = RequestMethod.POST)
    public ResultDto<Object> bindProdCode(@RequestBody ProdCodeBindDto prodCodeBindDto) {
        if (prodCodeBindDto.getOrderProdId() == null) {
            return ResultDtoFactory.toNack("商品id必传");
        }
        if (StringUtils.isEmpty(prodCodeBindDto.getQrCodeUrl())) {
            return ResultDtoFactory.toNack("二维码地址必传");
        }
        ThirdOrderProdDto thirdOrderProdDto = thirdOrderProdService.getOne(prodCodeBindDto.getOrderProdId());
        if (thirdOrderProdDto == null) {
            return ResultDtoFactory.toNack("订单商品不存在");
        } else if (thirdOrderProdDto.getProdId() == null || thirdOrderProdDto.getProdId() == 0) {
            return ResultDtoFactory.toNack("该商品暂不支持绑定商品码");
        } else if (thirdOrderProdDto.getBindCount() < 1) {
            return ResultDtoFactory.toNack("订单商品无可绑数量");
        }
        ProdInfDto prodInfDto = prodInfService.getOne(thirdOrderProdDto.getProdId());
        if (prodInfDto == null) {
            return ResultDtoFactory.toNack("商品不存在");
        }
        YMUserInfoDto yMUserInfoDto = yMUserInfoService.findByYmUserId(SecurityContext.getCurrentUserId());
        if (yMUserInfoDto == null || StringUtils.isEmpty(yMUserInfoDto.getUserId())) {
            return ResultDtoFactory.toNack("用户未绑定");//用户没有绑定微信，需先绑定微信
        }
        StoreInfDto storeInfDto = storeService.findByStoreId(thirdOrderProdDto.getStoreId());
        if (storeInfDto == null) {
            return ResultDtoFactory.toNack("店铺不存在");
        }
        if (!StringUtils.equals(storeInfDto.getUserId(), yMUserInfoDto.getUserId())) {
            return ResultDtoFactory.toNack("店铺信息不正确");
        }
        //这里需要判断商铺是不是加盟店
        FranchiseDto franchiseDto = franchiseService.getByStoreId(storeInfDto.getStoreId());
        if (franchiseDto == null) {
            return ResultDtoFactory.toNack("店铺不是加盟店");
        }
        ProdCodeDto prodCodeDto = prodCodeService.getByQrCodeUrl(prodCodeBindDto.getQrCodeUrl());
        if (prodCodeDto == null) {
            return ResultDtoFactory.toNack("商品码不存在");
        } else if (!EProdCodeStatus.UNUSED.equals(prodCodeDto.getStatus())) {
            return ResultDtoFactory.toNack("商品码已使用");
        }
        prodCodeDto.setOrderProdId(prodCodeBindDto.getOrderProdId());
        prodCodeDto.setProdId(thirdOrderProdDto.getProdId());
        prodCodeDto.setStoreId(thirdOrderProdDto.getStoreId());
        prodCodeDto.setOrderId(thirdOrderProdDto.getOrderId());
        prodCodeService.bindProdCode(prodCodeDto);
        return ResultDtoFactory.toAck("绑定成功");
    }

}
