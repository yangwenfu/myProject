package com.xinyunlian.jinfu.api.controller;

import com.xinyunlian.jinfu.api.dto.OrderProdOpenApiDto;
import com.xinyunlian.jinfu.api.dto.ProdCodeOpenApiDto;
import com.xinyunlian.jinfu.api.dto.ThirdOrderOpenApiDto;
import com.xinyunlian.jinfu.api.validate.OpenApi;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.order.dto.ThirdOrderInfDto;
import com.xinyunlian.jinfu.order.dto.ThirdOrderProdDto;
import com.xinyunlian.jinfu.order.service.ThirdOrderInfService;
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
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by menglei on 2016年12月13日.
 */
@RestController
@RequestMapping(value = "open-api")
public class ApiOpenController {

    @Autowired
    private ThirdOrderInfService thirdOrderInfService;
    @Autowired
    private ThirdOrderProdService thirdOrderProdService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private ProdInfService prodInfService;
    @Autowired
    private ProdCodeService prodCodeService;
    @Autowired
    private FranchiseService franchiseService;

    /**
     * 保存订单
     *
     * @param thirdOrderOpenApiDto
     * @return
     */
    @OpenApi
    @ResponseBody
    @RequestMapping(value = "/thirdOrder/save", method = RequestMethod.POST)
    public ResultDto<Object> saveThirdOrder(@RequestBody ThirdOrderOpenApiDto thirdOrderOpenApiDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResultDtoFactory.toNack(result.getFieldError().getDefaultMessage());
        }
        StoreInfDto storeInfDto = storeService.findByStoreId(thirdOrderOpenApiDto.getStoreId());
        if (storeInfDto == null) {
            return ResultDtoFactory.toNack("商铺不存在");
        }
        //这里需要判断商铺是不是加盟店
        FranchiseDto franchiseDto = franchiseService.getByStoreId(storeInfDto.getStoreId());
        if (franchiseDto == null) {
            return ResultDtoFactory.toNack("店铺不是加盟店");
        }
        ThirdOrderInfDto thirdOrderInfDto = thirdOrderInfService.getByOrderNo(thirdOrderOpenApiDto.getOrderNo());
        if (thirdOrderInfDto != null) {
            return ResultDtoFactory.toNack("订单号已存在");
        }
        if (thirdOrderOpenApiDto.getOrderProdList().isEmpty()) {
            return ResultDtoFactory.toNack("订单商品不能为空");
        }
        List<ProdInfDto> prodInfList = prodInfService.getAll();
        Map<String, ProdInfDto> prodMap = new HashMap<>();
        for (ProdInfDto prodInfDto : prodInfList) {
            prodMap.put(prodInfDto.getSku(), prodInfDto);
        }
        List<ThirdOrderProdDto> prodDtoList = new ArrayList<>();
        for (OrderProdOpenApiDto orderProdOpenApiDto : thirdOrderOpenApiDto.getOrderProdList()) {
            boolean flag = StringUtils.isEmpty(orderProdOpenApiDto.getProdName()) || StringUtils.isEmpty(orderProdOpenApiDto.getSku())
                    || StringUtils.isEmpty(orderProdOpenApiDto.getBoxCount()) || StringUtils.isEmpty(orderProdOpenApiDto.getProdCount())
                    || orderProdOpenApiDto.getBindCount() == null || orderProdOpenApiDto.getBindCount() < 1;
            if (flag) {
                return ResultDtoFactory.toNack("订单商品参数不能为空");
            }
            ThirdOrderProdDto prodDto = ConverterService.convert(orderProdOpenApiDto, ThirdOrderProdDto.class);
            prodDto.setStoreId(thirdOrderOpenApiDto.getStoreId());
            prodDto.setProdId(0L);
            ProdInfDto prodInfDto = prodMap.get(prodDto.getSku());
            if (prodInfDto != null) {
                prodDto.setProdId(prodInfDto.getProdId());
                prodDto.setProdName(prodInfDto.getName());
            }
            prodDtoList.add(prodDto);
        }
        ThirdOrderInfDto orderDto = ConverterService.convert(thirdOrderOpenApiDto, ThirdOrderInfDto.class);

        thirdOrderInfService.addOrderAndProd(orderDto, prodDtoList);

        return ResultDtoFactory.toAck("保存成功");
    }

    /**
     * 更新商品码状态已售
     *
     * @param prodCodeOpenApiDto
     * @param result
     * @return
     */
    @OpenApi
    @ResponseBody
    @RequestMapping(value = "/prodCode/update", method = RequestMethod.POST)
    public ResultDto<Object> updateProdCode(@RequestBody ProdCodeOpenApiDto prodCodeOpenApiDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResultDtoFactory.toNack(result.getFieldError().getDefaultMessage());
        }
        StoreInfDto storeInfDto = storeService.findByStoreId(prodCodeOpenApiDto.getStoreId());
        if (storeInfDto == null) {
            return ResultDtoFactory.toNack("商铺不存在");
        }
        //这里需要判断商铺是不是加盟店
        FranchiseDto franchiseDto = franchiseService.getByStoreId(storeInfDto.getStoreId());
        if (franchiseDto == null) {
            return ResultDtoFactory.toNack("店铺不是加盟店");
        }
        ProdCodeDto prodCodeDto = prodCodeService.getByQrCodeUrl(prodCodeOpenApiDto.getQrCodeUrl());
        if (prodCodeDto == null) {
            return ResultDtoFactory.toNack("商品码不存在");
        } else if (!EProdCodeStatus.SALE.equals(prodCodeDto.getStatus())) {
            return ResultDtoFactory.toNack("商品码状态不正确");
        } else if (prodCodeDto.getFrozen()) {
            return ResultDtoFactory.toNack("商品码已被冻结");
        }
        prodCodeService.updateStatusSold(prodCodeDto.getProdCodeId(), prodCodeOpenApiDto.getOrderNo());
        return ResultDtoFactory.toAck("保存成功");
    }

    /**
     * 获取商品码 sku
     *
     * @return
     */
    @OpenApi
    @ResponseBody
    @RequestMapping(value = "/prodCode", method = RequestMethod.POST)
    public ResultDto<Object> getProdCode(@RequestBody ProdCodeOpenApiDto prodCodeOpenApiDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResultDtoFactory.toNack(result.getFieldError().getDefaultMessage());
        }
        StoreInfDto storeInfDto = storeService.findByStoreId(prodCodeOpenApiDto.getStoreId());
        if (storeInfDto == null) {
            return ResultDtoFactory.toNack("商铺不存在");
        }
        //这里需要判断商铺是不是加盟店
        FranchiseDto franchiseDto = franchiseService.getByStoreId(storeInfDto.getStoreId());
        if (franchiseDto == null) {
            return ResultDtoFactory.toNack("店铺不是加盟店");
        }
        ProdCodeDto prodCodeDto = prodCodeService.getByQrCodeUrl(prodCodeOpenApiDto.getQrCodeUrl());
        if (prodCodeDto == null) {
            return ResultDtoFactory.toNack("商品码不存在");
        } else if (!EProdCodeStatus.SALE.equals(prodCodeDto.getStatus())) {
            return ResultDtoFactory.toNack("商品码状态不正确");
        } else if (prodCodeDto.getFrozen()) {
            return ResultDtoFactory.toNack("商品码已被冻结");
        }
        ProdInfDto prodInfDto = prodInfService.getOne(prodCodeDto.getProdId());
        if (prodInfDto == null) {
            return ResultDtoFactory.toNack("商品不存在");
        }
        return ResultDtoFactory.toAck("获取成功", prodInfDto.getSku());
    }

}
