package com.xinyunlian.jinfu.qrcode.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.qrcode.dto.ProdInfDto;
import com.xinyunlian.jinfu.qrcode.dto.ProdInfSearchDto;

import java.util.List;

/**
 * Created by menglei on 2017年03月08日.
 */
public interface ProdInfService {

    /**
     * 分页获取产品列表
     *
     * @param prodInfSearchDto
     * @return
     */
    ProdInfSearchDto getProdInfPage(ProdInfSearchDto prodInfSearchDto);

    /**
     * 商品详情
     *
     * @param prodId
     * @return
     */
    ProdInfDto getOne(Long prodId);

    /**
     * 通过sku查商品
     *
     * @param sku
     * @return
     */
    ProdInfDto getBySku(String sku);

    /**
     * 全部商品
     *
     * @return
     */
    List<ProdInfDto> getAll();

    /**
     * 商品新增
     *
     * @param prodInfDto
     * @throws BizServiceException
     */
    void addProdInf(ProdInfDto prodInfDto) throws BizServiceException;

    /**
     * 商品更新
     *
     * @param prodInfDto
     * @throws BizServiceException
     */
    void updateProdInf(ProdInfDto prodInfDto) throws BizServiceException;

}
