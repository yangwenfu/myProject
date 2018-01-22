package com.xinyunlian.jinfu.api.controller;

import com.xinyunlian.jinfu.api.dto.ApiBaiduDto;
import com.xinyunlian.jinfu.api.dto.ApiProdCodeDto;
import com.xinyunlian.jinfu.api.service.ApiBaiduService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.order.dto.ThirdOrderInfDto;
import com.xinyunlian.jinfu.order.service.ThirdOrderInfService;
import com.xinyunlian.jinfu.qrcode.dto.ProdCodeDto;
import com.xinyunlian.jinfu.qrcode.dto.ProdInfDto;
import com.xinyunlian.jinfu.qrcode.dto.ScanCodeRecordDto;
import com.xinyunlian.jinfu.qrcode.dto.ScanUserAgentDto;
import com.xinyunlian.jinfu.qrcode.enums.EProdCodeStatus;
import com.xinyunlian.jinfu.qrcode.service.ProdCodeService;
import com.xinyunlian.jinfu.qrcode.service.ProdInfService;
import com.xinyunlian.jinfu.qrcode.service.ScanCodeRecordService;
import com.xinyunlian.jinfu.qrcode.service.ScanUserAgentService;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.store.service.StoreService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by menglei on 2017/3/22.
 */
@Controller
@RequestMapping(value = "open-api/uplus")
public class ApiProdCodeController {

    @Autowired
    private StoreService storeService;
    @Autowired
    private ProdInfService prodInfService;
    @Autowired
    private ProdCodeService prodCodeService;
    @Autowired
    private ApiBaiduService apiBaiduService;
    @Autowired
    private ScanUserAgentService scanUserAgentService;
    @Autowired
    private ThirdOrderInfService thirdOrderInfService;
    @Autowired
    private ScanCodeRecordService scanCodeRecordService;

    /**
     * 获取商品码
     *
     * @return
     */
    @RequestMapping(value = "/getProdCode", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> getProdCode(HttpServletRequest request, @RequestBody ApiProdCodeDto apiProdCodeDto) {
        if (StringUtils.isEmpty(apiProdCodeDto.getQrCodeUrl())) {
            return ResultDtoFactory.toNack("商品码地址必传");
        }
        //获取商品码
        ProdCodeDto prodCodeDto = prodCodeService.getByQrCodeUrl(apiProdCodeDto.getQrCodeUrl());
        if (prodCodeDto == null) {
            return ResultDtoFactory.toNack("商品码不存在");
        }
        if (EProdCodeStatus.UNUSED.equals(prodCodeDto.getStatus())) {
            return ResultDtoFactory.toNack("该商品码未绑定商品");
        }
        ProdInfDto prodInfDto = prodInfService.getOne(prodCodeDto.getProdId());
        if (prodInfDto == null) {
            return ResultDtoFactory.toNack("商品不存在");
        }
        StoreInfDto storeInfDto = storeService.findByStoreId(prodCodeDto.getStoreId());
        if (storeInfDto == null) {
            return ResultDtoFactory.toNack("店铺不存在");
        }
        ThirdOrderInfDto thirdOrderInfDto = thirdOrderInfService.getOne(prodCodeDto.getOrderId());
        ApiProdCodeDto resultDto = new ApiProdCodeDto();
        resultDto.setFrozen(prodCodeDto.getFrozen());
        resultDto.setProdName(prodInfDto.getName());
        resultDto.setStatus(prodCodeDto.getStatus());
        resultDto.setDetailText(prodInfDto.getDetailText());
        resultDto.setQrCodeNo(prodCodeDto.getQrCodeNo());
        resultDto.setQrCodeUrl(prodCodeDto.getQrCodeUrl());
        resultDto.setSellTime(prodCodeDto.getSellTime());
        resultDto.setStoreName(storeInfDto.getStoreName());
        resultDto.setSupplier(thirdOrderInfDto.getSupplier());
        //已售商品需记录扫码信息
        if (EProdCodeStatus.SOLD.equals(prodCodeDto.getStatus())) {
            //获取扫码工具
            ScanUserAgentDto scanUserAgentDto = scanUserAgentService.getByUserAgent(request.getHeader("User-Agent").toLowerCase());
            //获取省市区
            ApiBaiduDto apiBaiduDto = new ApiBaiduDto();
            if (StringUtils.isNotEmpty(apiProdCodeDto.getLat()) && StringUtils.isNotEmpty(apiProdCodeDto.getLng())) {
                apiBaiduDto = apiBaiduService.getArea(apiProdCodeDto.getLat() + "," + apiProdCodeDto.getLng());
            }
            ScanCodeRecordDto scanCodeRecordDto = new ScanCodeRecordDto();
            scanCodeRecordDto.setProdCodeId(prodCodeDto.getProdCodeId());
            scanCodeRecordDto.setLng(apiProdCodeDto.getLng());
            scanCodeRecordDto.setLat(apiProdCodeDto.getLat());
            if (scanUserAgentDto != null) {
                scanCodeRecordDto.setScanTool(scanUserAgentDto.getName());
            } else {
                scanCodeRecordDto.setScanTool("未知");
            }
            if (apiBaiduDto != null) {

                scanCodeRecordDto.setProvice(apiBaiduDto.getProvince());
                scanCodeRecordDto.setCity(apiBaiduDto.getCity());
                scanCodeRecordDto.setArea(apiBaiduDto.getArea());
            }
            scanCodeRecordService.addScanCodeRecord(scanCodeRecordDto);
        }
        return ResultDtoFactory.toAck("获取成功", resultDto);
    }

}
