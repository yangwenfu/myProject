package com.xinyunlian.jinfu.prod.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.prod.dto.ProdTypeInfDto;
import com.xinyunlian.jinfu.prod.dto.ProdTypeInfSerachDto;

import java.util.List;

/**
 * Created by DongFC on 2016-09-18.
 */
public interface ProdTypeInfService {

    /**
     * 查询产品列表
     * @param prodTypeInfSerachDto
     * @return
     */
    List<ProdTypeInfDto> getProdTypeList(ProdTypeInfSerachDto prodTypeInfSerachDto);

    /**
     * 更新产品类型的名称
     * @param prodTypeInfDto
     */
    boolean updateProdTypeInf(ProdTypeInfDto prodTypeInfDto) throws BizServiceException;

    /**
     * 删除产品类型
     * @param prodTypeId
     * @return
     */
    boolean deleteProdTypeInf(Long prodTypeId);

    /**
     * 新增产品类型
     * @param prodTypeInfDto
     */
    boolean saveProdTypeInf(ProdTypeInfDto prodTypeInfDto) throws BizServiceException;

    ProdTypeInfDto getProdTypeById(Long prodTypeId);

    ProdTypeInfDto getProdTypeByCode(String prodTypeCode);
}
