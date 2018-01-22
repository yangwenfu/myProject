package com.xinyunlian.jinfu.prod.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.prod.dto.*;
import com.xinyunlian.jinfu.prod.enums.EProdAppDetailCfg;
import com.xinyunlian.jinfu.prod.enums.EShelfPlatform;

import java.util.List;

/**
 * Created by DongFC on 2016-08-22.
 */
public interface ProdService {

    /**
     * 获取产品列表
     * @param productSearchDto
     * @return
     */
    List<ProductDto> getProdList(ProductSearchDto productSearchDto) throws BizServiceException;

    /**
     * 获取单个产品的地区
     * @param prodId
     * @return
     */
    List<AreaDetailLvlDto> getAreaByProd(String prodId) throws BizServiceException;

    /**
     * 更新产品地区，全删全插
     * @param prodAreaDto
     */
    void updateProdArea(ProdAreaDto prodAreaDto) throws BizServiceException;

    /**
     * 新增产品
     * @param productDto
     */
    void saveProduct(ProductDto productDto) throws BizServiceException;

    /**
     * 根据产品id list获取产品详细信息
     * @param ids
     * @return
     */
    List<ProductDto> getProdListByIds(List<String> ids) throws BizServiceException;

    /**
     * 根据产品id获取产品信息
     * @param prodId
     * @return
     */
    ProductDto getProdById(String prodId) throws BizServiceException;

    /**
     * 判断产品是否配置在指定地区
     * @param prodId
     * @param areaId
     * @return
     */
    Boolean checkProdArea(String prodId, Long areaId) throws BizServiceException;

    /**
     * 删除产品-地区关系
     * @param id
     */
    void deleteProdArea(Long id) throws BizServiceException;

    /**
     * 新增产品地区
     * @param prodAreaDto
     * @throws BizServiceException
     */
    ProdAreaDto saveProdArea(ProdAreaDto prodAreaDto) throws BizServiceException;

    /**
     * 更新产品信息
     * @param dto
     * @throws BizServiceException
     */
    void updateProduct(ProductDto dto) throws BizServiceException;

    /**
     * 根据产品id和上架平台获取产品列表
     * @param ids
     * @param shelfPlatform
     * @return
     * @throws BizServiceException
     */
    List<ProductDto> getProdListByIdsAndPlatform(List<String> ids, EShelfPlatform shelfPlatform) throws BizServiceException;

    /**
     * 根据产品平台获取产品列表
     * @param shelfPlatform
     * @return
     * @throws BizServiceException
     */
    List<ProductDto> getProdListByPlatform(EShelfPlatform shelfPlatform) throws BizServiceException;

    /**
     * 根据产品平台和产品分类获取产品列表
     * @param prodTypePath
     * @param shelfPlatform
     * @return
     * @throws BizServiceException
     */
    List<ProductDto> getProdListByTypeAndPlatform(String prodTypePath, EShelfPlatform shelfPlatform) throws BizServiceException;

    /**
     * 根据配置（热卖、新品、推荐）和平台获取产品列表
     * @param shelfPlatform
     * @param prodAppDetailCfg
     * @return
     * @throws BizServiceException
     */
    List<ProductDto> getProdListByPlatformAndCfg(EShelfPlatform shelfPlatform, EProdAppDetailCfg prodAppDetailCfg,Integer limit) throws BizServiceException;

    /**
     * 根据产品id、上架平台和行业码获取产品列表
     * @param ids
     * @param shelfPlatform
     * @param indMcc
     * @return
     * @throws BizServiceException
     */
    List<ProductDto> getProdListByIdPlatformInd(List<String> ids, EShelfPlatform shelfPlatform, String indMcc) throws BizServiceException;

    /**
     * 根据产品平台、产品分类和行业获取产品列表
     * @param prodTypePath
     * @param shelfPlatform
     * @param indMccList
     * @return
     * @throws BizServiceException
     */
    List<ProductDto> getProdListByTypePlatformInd(String prodTypePath, EShelfPlatform shelfPlatform, List<String> indMccList) throws BizServiceException;

    /**
     * 根据配置（热卖、新品、推荐）、平台和行业获取产品列表
     * @param shelfPlatform
     * @param prodAppDetailCfg
     * @param indMcc
     * @param limit
     * @return
     * @throws BizServiceException
     */
    List<ProductDto> getProdListByPlatformCfgInd(EShelfPlatform shelfPlatform, EProdAppDetailCfg prodAppDetailCfg, String indMcc, Integer limit) throws BizServiceException;

    /**
     * 根据地区和行业，判断产品是否配置在指定地区
     * @param prodId
     * @param areaId
     * @return
     */
    Boolean checkProdArea(String prodId, Long areaId, String indMcc) throws BizServiceException;

}
