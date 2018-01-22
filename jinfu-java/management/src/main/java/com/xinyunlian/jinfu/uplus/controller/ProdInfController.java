package com.xinyunlian.jinfu.uplus.controller;

import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.qrcode.dto.*;
import com.xinyunlian.jinfu.qrcode.service.ProdInfService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by menglei on 2017/3/16.
 */
@Controller
@RequestMapping(value = "uplus/prodInf")
public class ProdInfController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProdInfController.class);

    @Autowired
    private ProdInfService prodInfService;

    /**
     * 分页查询新商品
     *
     * @param prodInfSearchDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultDto<ProdInfSearchDto> getListPage(ProdInfSearchDto prodInfSearchDto) {
        ProdInfSearchDto page = prodInfService.getProdInfPage(prodInfSearchDto);
        return ResultDtoFactory.toAck("获取成功", page);
    }

    /**
     * 商品详情
     *
     * @param prodId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public ResultDto<Object> getDetail(@RequestParam Long prodId) {
        ProdInfDto dto = prodInfService.getOne(prodId);
        return ResultDtoFactory.toAck("获取成功", dto);
    }

    /**
     * 新增产品
     *
     * @param prodInfDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultDto<Object> addProduct(@RequestBody ProdInfDto prodInfDto) {
        ProdInfDto dto = prodInfService.getBySku(prodInfDto.getSku());
        if (dto != null) {
            return ResultDtoFactory.toNack("SKU码已存在");
        }
        prodInfService.addProdInf(prodInfDto);
        return ResultDtoFactory.toAck("新增成功");
    }

    /**
     * 更新产品
     *
     * @param prodInfDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultDto<Object> updateProduct(@RequestBody ProdInfDto prodInfDto) {
        prodInfService.updateProdInf(prodInfDto);
        return ResultDtoFactory.toAck("修改成功");
    }

}
