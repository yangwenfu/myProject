package com.xinyunlian.jinfu.prod.controller;

import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.prod.dto.ProdTypeInfDto;
import com.xinyunlian.jinfu.prod.dto.ProdTypeInfSerachDto;
import com.xinyunlian.jinfu.prod.dto.ProductDto;
import com.xinyunlian.jinfu.prod.dto.ProductSearchDto;
import com.xinyunlian.jinfu.prod.service.ProdService;
import com.xinyunlian.jinfu.prod.service.ProdTypeInfService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by DongFC on 2016-09-18.
 */
@RestController
@RequestMapping("prodType")
@RequiresPermissions({"PROD_TYPE_MGT"})
public class ProdTypeInfController {

    @Autowired
    private ProdTypeInfService prodTypeInfService;

    @Autowired
    private ProdService prodService;

    /**
     * 获取产品类型列表
     * @param prodTypeInfSerachDto
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultDto<List<ProdTypeInfDto>> getProdTypeList(ProdTypeInfSerachDto prodTypeInfSerachDto){
        List<ProdTypeInfDto> list = prodTypeInfService.getProdTypeList(prodTypeInfSerachDto);
        return ResultDtoFactory.toAck("获取成功", list);
    }

    /**
     * 更新产品类型
     * @param prodTypeInfDto
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultDto<String> updateProdType(@RequestBody ProdTypeInfDto prodTypeInfDto){
        boolean isSuccess = prodTypeInfService.updateProdTypeInf(prodTypeInfDto);
        if (!isSuccess){
            return ResultDtoFactory.toNack("分类编号已存在！");
        }
        return ResultDtoFactory.toAck("更新成功");
    }

    /**
     * 删除产品类型
     * @param prodTypeId
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResultDto<String> deleteProdType(@RequestParam Long prodTypeId){

        ProdTypeInfDto prodTypeInfDto = prodTypeInfService.getProdTypeById(prodTypeId);
        if (prodTypeInfDto != null){
            ProductSearchDto productSearchDto = new ProductSearchDto();
            productSearchDto.setProdTypePath(prodTypeInfDto.getProdTypePath());
            List<ProductDto> prodList = prodService.getProdList(productSearchDto);
            if (!CollectionUtils.isEmpty(prodList)){
                return ResultDtoFactory.toNack("当前分类下有产品，不支持删除");
            }else{
                boolean canRemove = prodTypeInfService.deleteProdTypeInf(prodTypeId);
                if (canRemove){
                    return ResultDtoFactory.toAck("删除成功");
                }else {
                    return ResultDtoFactory.toNack("当前大类下有小类数据，不支持删除");
                }
            }
        }
        return ResultDtoFactory.toNack("产品分类不存在");
    }

    /**
     * 新增产品类型
     * @param prodTypeInfDto
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultDto<String> addProdType(@RequestBody ProdTypeInfDto prodTypeInfDto){
        boolean isSuccess = prodTypeInfService.saveProdTypeInf(prodTypeInfDto);
        if (!isSuccess){
            return ResultDtoFactory.toNack("产品分类编号已存在！");
        }
        return ResultDtoFactory.toAck("新增成功");
    }

}
