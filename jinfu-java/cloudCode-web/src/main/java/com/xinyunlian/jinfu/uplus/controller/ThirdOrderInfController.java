package com.xinyunlian.jinfu.uplus.controller;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.order.dto.ThirdOrderInfDto;
import com.xinyunlian.jinfu.order.dto.ThirdOrderInfSearchDto;
import com.xinyunlian.jinfu.order.dto.ThirdOrderProdCountDto;
import com.xinyunlian.jinfu.order.dto.ThirdOrderProdDto;
import com.xinyunlian.jinfu.order.service.ThirdOrderInfService;
import com.xinyunlian.jinfu.order.service.ThirdOrderProdService;
import com.xinyunlian.jinfu.qrcode.dto.ProdCodeCountDto;
import com.xinyunlian.jinfu.qrcode.dto.ProdCodeDto;
import com.xinyunlian.jinfu.qrcode.service.ProdCodeService;
import com.xinyunlian.jinfu.store.dto.FranchiseDto;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.store.service.FranchiseService;
import com.xinyunlian.jinfu.store.service.StoreService;
import com.xinyunlian.jinfu.uplus.dto.ThirdOrderInfoDto;
import com.xinyunlian.jinfu.uplus.dto.ThirdOrderInfoSearchDto;
import com.xinyunlian.jinfu.yunma.dto.YMUserInfoDto;
import com.xinyunlian.jinfu.yunma.service.YMUserInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by menglei on 2017/3/20.
 */
@Controller
@RequestMapping(value = "uplus/order")
public class ThirdOrderInfController {

    @Autowired
    private ThirdOrderInfService thirdOrderInfService;
    @Autowired
    private ThirdOrderProdService thirdOrderProdService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private ProdCodeService prodCodeService;
    @Autowired
    private YMUserInfoService yMUserInfoService;
    @Autowired
    private FranchiseService franchiseService;

    /**
     * 获取订单列表
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getOrderListPage(ThirdOrderInfSearchDto thirdOrderInfSearchDto) {
        if (thirdOrderInfSearchDto.getStoreId() == null) {
            return ResultDtoFactory.toNack("店铺id必传");
        }
        YMUserInfoDto yMUserInfoDto = yMUserInfoService.findByYmUserId(SecurityContext.getCurrentUserId());
        if (yMUserInfoDto == null || StringUtils.isEmpty(yMUserInfoDto.getUserId())) {
            return ResultDtoFactory.toNack("用户未绑定");//用户没有绑定微信，需先绑定微信
        }
        StoreInfDto storeInfDto = storeService.findByStoreId(thirdOrderInfSearchDto.getStoreId());
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
        ThirdOrderInfSearchDto page = thirdOrderInfService.getThirdOrderInfPage(thirdOrderInfSearchDto);

        List<Long> orderIds = new ArrayList<>();
        for (ThirdOrderInfDto thirdOrderInfDto : page.getList()) {
            orderIds.add(thirdOrderInfDto.getOrderId());
        }

        List<ThirdOrderInfoDto> list = new ArrayList<>();
        ThirdOrderInfoSearchDto thirdOrderInfoSearchDto = new ThirdOrderInfoSearchDto();
        thirdOrderInfoSearchDto.setPageSize(page.getPageSize());
        thirdOrderInfoSearchDto.setStoreId(page.getStoreId());
        thirdOrderInfoSearchDto.setOrderNo(page.getOrderNo());
        thirdOrderInfoSearchDto.setCurrentPage(page.getCurrentPage());
        thirdOrderInfoSearchDto.setTotalPages(page.getTotalPages());
        thirdOrderInfoSearchDto.setTotalRecord(page.getTotalRecord());

        if (orderIds.isEmpty()) {
            thirdOrderInfoSearchDto.setList(list);
            return ResultDtoFactory.toAck("获取成功", thirdOrderInfoSearchDto);
        }
        //可绑数量
        List<ThirdOrderProdCountDto> thirdOrderProdCounts = thirdOrderProdService.getCountByOrderId(orderIds);
        Map<Long, Integer> bindCountMap = new HashMap<>();
        for (ThirdOrderProdCountDto thirdOrderProdCountDto : thirdOrderProdCounts) {
            bindCountMap.put(thirdOrderProdCountDto.getOrderId(), thirdOrderProdCountDto.getBindCount());
        }
        //已绑数量
        List<ProdCodeCountDto> prodCodeCounts = prodCodeService.getCountByOrderId(orderIds);
        Map<Long, Integer> bindingCountMap = new HashMap<>();
        for (ProdCodeCountDto prodCodeCountDto : prodCodeCounts) {
            bindingCountMap.put(prodCodeCountDto.getOrderId(), prodCodeCountDto.getBindCount());
        }
        ThirdOrderInfoDto dto;
        for (ThirdOrderInfDto thirdOrderInfDto : page.getList()) {
            dto = ConverterService.convert(thirdOrderInfDto, ThirdOrderInfoDto.class);
            Integer bindCount = bindCountMap.get(thirdOrderInfDto.getOrderId());
            if (bindCount != null) {
                dto.setBindCount(bindCount);
            }
            Integer bindingCount = bindingCountMap.get(thirdOrderInfDto.getOrderId());
            if (bindingCount != null) {
                dto.setBindingCount(bindingCount);
            }
            list.add(dto);
        }
        thirdOrderInfoSearchDto.setList(list);
        return ResultDtoFactory.toAck("获取成功", thirdOrderInfoSearchDto);
    }

    /**
     * 获取订单详情
     *
     * @param orderId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public ResultDto<Object> getOrderDetail(@RequestParam Long orderId) {
        if (orderId == null) {
            return ResultDtoFactory.toNack("订单id必传");
        }
        ThirdOrderInfDto thirdOrderInfDto = thirdOrderInfService.getOne(orderId);
        if (thirdOrderInfDto == null) {
            return ResultDtoFactory.toNack("订单不存在");
        }
        YMUserInfoDto yMUserInfoDto = yMUserInfoService.findByYmUserId(SecurityContext.getCurrentUserId());
        if (yMUserInfoDto == null || StringUtils.isEmpty(yMUserInfoDto.getUserId())) {
            return ResultDtoFactory.toNack("用户未绑定");//用户没有绑定微信，需先绑定微信
        }
        StoreInfDto storeInfDto = storeService.findByStoreId(thirdOrderInfDto.getStoreId());
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
        //查商品码
        List<ProdCodeDto> prodCodeList = prodCodeService.getListByOrderId(orderId);
        Map<Long, List<ProdCodeDto>> map = new HashMap<>();
        for (ProdCodeDto prodCodeDto : prodCodeList) {
            List<ProdCodeDto> prodCodes = map.get(prodCodeDto.getProdId());
            if (prodCodes == null || prodCodes.isEmpty()) {
                prodCodes = new ArrayList<>();
            }
            prodCodes.add(prodCodeDto);
            map.put(prodCodeDto.getProdId(), prodCodes);
        }
        //订单详情
        List<ThirdOrderProdDto> list = thirdOrderProdService.getListByOrderId(orderId);
        List<ThirdOrderProdDto> resultList = new ArrayList<>();
        for (ThirdOrderProdDto thirdOrderProdDto : list) {
            List<ProdCodeDto> prodCodes = map.get(thirdOrderProdDto.getProdId());
            thirdOrderProdDto.setProdCodeList(prodCodes);
            resultList.add(thirdOrderProdDto);
        }
        return ResultDtoFactory.toAck("获取成功", resultList);
    }

    /**
     * 获取加盟店列表
     *
     * @return
     */
    @RequestMapping(value = "/storeList", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getStoreList() {
        YMUserInfoDto yMUserInfoDto = yMUserInfoService.findByYmUserId(SecurityContext.getCurrentUserId());
        if (yMUserInfoDto == null || StringUtils.isEmpty(yMUserInfoDto.getUserId())) {
            return ResultDtoFactory.toNack("用户未绑定");//用户没有绑定微信，需先绑定微信
        }
        List<StoreInfDto> storeInfList = storeService.findByUserId(yMUserInfoDto.getUserId());
        List<Long> storeIds = new ArrayList<>();
        Map<Long, StoreInfDto> storeMap = new HashMap<>();
        for (StoreInfDto storeInfDto : storeInfList) {
            storeIds.add(storeInfDto.getStoreId());
            storeMap.put(storeInfDto.getStoreId(), storeInfDto);
        }
        if (storeIds.isEmpty()) {
            return ResultDtoFactory.toNack("店铺不是加盟店");
        }
        List<FranchiseDto> franchiseList = franchiseService.getByStoreIds(storeIds);
        List<StoreInfDto> list = new ArrayList<>();
        for (FranchiseDto franchiseDto : franchiseList) {
            StoreInfDto storeInfDto = storeMap.get(franchiseDto.getStoreId());
            if (storeInfDto != null) {
                list.add(storeInfDto);
            }
        }
        return ResultDtoFactory.toAck("获取成功", list);
    }

}
