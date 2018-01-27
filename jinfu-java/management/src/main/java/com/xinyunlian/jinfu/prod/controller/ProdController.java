package com.xinyunlian.jinfu.prod.controller;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.prod.dto.AreaDetailLvlDto;
import com.xinyunlian.jinfu.prod.dto.ProdAreaDto;
import com.xinyunlian.jinfu.prod.dto.ProductDto;
import com.xinyunlian.jinfu.prod.dto.ProductSearchDto;
import com.xinyunlian.jinfu.prod.service.ProdService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by DongFC on 2016-08-24.
 */
@RestController
@RequestMapping("prod")
@RequiresPermissions({"PROD_MGT"})
public class ProdController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProdController.class);

    @Autowired
    private ProdService prodService;

    /**
     * 获取产品列表
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultDto<List<ProductDto>> getProdList(ProductSearchDto productSearchDto){
        List<ProductDto> list = prodService.getProdList(productSearchDto);
        return ResultDtoFactory.toAck("获取成功", list);
    }

    /**
     * 获取产品的地区
     * @param prodId
     * @return
     */
    @RequestMapping(value = "/areas", method = RequestMethod.GET)
    public ResultDto<List<AreaDetailLvlDto>> getProdArea(@RequestParam String prodId){
        List<AreaDetailLvlDto> list = prodService.getAreaByProd(prodId);
        return ResultDtoFactory.toAck("获取成功", list);
    }

    /**
     * 更新产品地区
     * @param prodAreaDto
     * @return
     */
    @RequestMapping(value = "/areas", method = RequestMethod.POST)
    public ResultDto<String> updateProdArea(@RequestBody ProdAreaDto prodAreaDto){

        try {
            Long areaId = null;
            if (prodAreaDto.getStreetId() != null){
                areaId = prodAreaDto.getStreetId();
            }else if (prodAreaDto.getCountyId() != null){
                areaId = prodAreaDto.getCountyId();
            }else if (prodAreaDto.getCityId() != null){
                areaId = prodAreaDto.getCityId();
            }else if (prodAreaDto.getProvinceId() != null){
                areaId = prodAreaDto.getProvinceId();
            }

            if (areaId == null){
                return ResultDtoFactory.toNack("地区不能为空！");
            }

            prodAreaDto.setAreaId(areaId);
            prodService.updateProdArea(prodAreaDto);

            return ResultDtoFactory.toAck("更新地区成功");
        } catch (Exception e) {
            return ResultDtoFactory.toNack("地区已存在！");
        }
    }

    /**
     * 新增产品
     * @param productDto
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultDto<String> saveProduct(@RequestBody ProductDto productDto){
        try {
            prodService.saveProduct(productDto);
        } catch (BizServiceException e) {
            LOGGER.error("新增产品失败", e);
            return ResultDtoFactory.toNack(e.getError());
        }

        return ResultDtoFactory.toAck("新增产品成功");
    }

    /**
     * 删除产品-地区关联关系
     * @param prodAreaId
     * @return
     */
    @RequestMapping(value = "/deleteProdArea", method = RequestMethod.DELETE)
    public ResultDto<String> deleteProdArea(@RequestParam Long prodAreaId){
        prodService.deleteProdArea(prodAreaId);
        return ResultDtoFactory.toAck("删除产品地区成功");
    }

    /**
     * 新增产品地区
     * @param prodAreaDto
     * @return
     */
    @RequestMapping(value = "/areas", method = RequestMethod.PUT)
    public ResultDto<Object> addProdArea(@RequestBody ProdAreaDto prodAreaDto){
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

                ProdAreaDto tmpDto = prodService.saveProdArea(prodAreaDto);
                AreaDetailLvlDto ret = ConverterService.convert(tmpDto, AreaDetailLvlDto.class);
                ret.setProdAreaId(tmpDto.getId());
                result = ResultDtoFactory.toAck("新增地区成功");
                result.setData(ret);
                return result;
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
    public ResultDto<Object> getProduct(String prodId){
        ProductDto dto = prodService.getProdById(prodId);
        return ResultDtoFactory.toAck("查询成功", dto);
    }

    /**
     * 修改产品信息
     * @param productDto
     * @return
     */
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public ResultDto<String> modifyProduct(@RequestBody ProductDto productDto){
        prodService.updateProduct(productDto);
        return ResultDtoFactory.toAck("修改产品信息成功");
    }

}
