package com.xinyunlian.jinfu.yunma.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.yunma.dto.YMProdAreaDto;
import com.xinyunlian.jinfu.yunma.dto.YMProductDto;
import com.xinyunlian.jinfu.yunma.dto.YmAreaDetailDto;

import java.util.List;

/**
 * Created by menglei on 2017-01-06.
 */
public interface YMProdService {

    /**
     * 获取产品信息
     * @param prodId
     * @return
     */
    YMProductDto getProduct(String prodId);

    /**
     * 修改成品信息
     * @param dto
     */
    void updateProduct(YMProductDto dto);

    /**
     * 保存产品
     * @param productDto
     * @return
     */
    void saveProd(YMProductDto productDto) throws BizServiceException;
    /**
     * 获取产品列表
     * @param yMProductDto
     * @return
     */
    List<YMProductDto> getProdList(YMProductDto yMProductDto) throws BizServiceException;

    /**
     * 判断产品是否配置在指定地区
     * @param prodId
     * @param areaId
     * @param areaTreePath
     * @return
     */
    Boolean checkProdArea(String prodId, Long areaId, String areaTreePath) throws BizServiceException;

    /**
     * 获取产品的地区
     * @param prodId
     * @return
     */
    List<YmAreaDetailDto> getAreaByProd(String prodId);

    /**
     * 保存产品地区配置信息
     * @param prodAreaDto
     * @return
     */
    YMProdAreaDto saveProdArea(YMProdAreaDto prodAreaDto);

    /**
     *批量保存产品地区配置信息
     * @param prodAreaDtos
     * @return
     */
    List<YMProdAreaDto> saveProdAreaList(List<YMProdAreaDto> prodAreaDtos);

    void deleteProdArea(Long id);

}
