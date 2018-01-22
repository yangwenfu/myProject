package com.xinyunlian.jinfu.yunma.controller;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.yunma.dto.YMProdAreaDto;
import com.xinyunlian.jinfu.yunma.dto.YMProductDto;
import com.xinyunlian.jinfu.yunma.dto.YmAreaDetailDto;
import com.xinyunlian.jinfu.yunma.service.YMProdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by King on 2017-01-11.
 */
@RestController
@RequestMapping("yunma/prod")
public class YmProdController {

    private static final Logger LOGGER = LoggerFactory.getLogger(YmProdController.class);

    @Autowired
    private YMProdService prodService;

    /**
     * 获取产品列表
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResultDto<Object> getProdList(@RequestBody YMProductDto productDto){
        List<YMProductDto> list = prodService.getProdList(productDto);
        return ResultDtoFactory.toAck("获取成功", list);
    }

    /**
     * 获取产品的地区
     * @param prodId
     * @return
     */
    @RequestMapping(value = "/areas", method = RequestMethod.GET)
    public ResultDto<Object> getProdArea(@RequestParam String prodId){
        List<YmAreaDetailDto> list = prodService.getAreaByProd(prodId);
        return ResultDtoFactory.toAck("获取成功", list);
    }

    /**
     * 新增产品
     * @param productDto
     * @return
     */
    @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    public ResultDto<String> saveProduct(@RequestBody YMProductDto productDto){
        prodService.saveProd(productDto);
        return ResultDtoFactory.toAck("新增产品成功");
    }

    /**
     * 删除产品-地区关联关系
     * @param prodAreaId
     * @return
     */
    @RequestMapping(value = "/deleteProdArea", method = RequestMethod.POST)
    public ResultDto<String> deleteProdArea(@RequestParam Long prodAreaId){
        prodService.deleteProdArea(prodAreaId);
        return ResultDtoFactory.toAck("删除产品地区成功");
    }

    /**
     * 新增产品地区
     * @param prodAreaDto
     * @return
     */
    @RequestMapping(value = "/addAreas", method = RequestMethod.POST)
    public ResultDto<Object> addProdArea(@RequestBody YMProdAreaDto prodAreaDto){
        if (prodAreaDto != null){
            ResultDto<Object> result = null;
            try {
                StringBuilder stringBuilder = new StringBuilder();
                if (prodAreaDto.getProvinceId() != null){
                    stringBuilder.append(",").append(prodAreaDto.getProvinceId());
                    if (prodAreaDto.getCityId() != null){
                        stringBuilder.append(",").append(prodAreaDto.getCityId());
                        if (prodAreaDto.getCountyId() != null){
                            stringBuilder.append(",").append(prodAreaDto.getCountyId());
                            if (prodAreaDto.getStreetId() != null){
                                stringBuilder.append(",").append(prodAreaDto.getStreetId());
                            }
                        }
                    }
                }

                if (StringUtils.isEmpty(stringBuilder.toString())){
                    return ResultDtoFactory.toNack("地区配置不能为空");
                }
                stringBuilder.append(",");

                prodAreaDto.setAreaTreePath(stringBuilder.toString());
                String[] areaIds = stringBuilder.toString().split(",");
                prodAreaDto.setAreaId(Long.parseLong(areaIds[areaIds.length - 1]));

                YMProdAreaDto tmpDto = prodService.saveProdArea(prodAreaDto);
                YmAreaDetailDto ret = ConverterService.convert(tmpDto, YmAreaDetailDto.class);
                ret.setProdAreaId(tmpDto.getId());
                return ResultDtoFactory.toAck("新增地区成功",ret);
            } catch (Exception e) {
                return ResultDtoFactory.toNack("地区已存在！");
            }

        }else {
            return ResultDtoFactory.toNack("地区配置不能为空");
        }
    }

    /**
     * 根据产品ID获取唯一的产品信息
     * @param prodId
     * @return
     */
    @RequestMapping(value = "/getProductById", method = RequestMethod.GET)
    public ResultDto<Object> getProduct(@RequestParam String prodId){
        YMProductDto dto = prodService.getProduct(prodId);
        return ResultDtoFactory.toAck("查询成功", dto);
    }

    /**
     * 修改产品信息
     * @param productDto
     * @return
     */
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public ResultDto<String> modifyProduct(@RequestBody YMProductDto productDto){
        prodService.updateProduct(productDto);
        return ResultDtoFactory.toAck("修改产品信息成功");
    }

}
