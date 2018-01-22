package com.xinyunlian.jinfu.loan.service;

import com.xinyunlian.jinfu.product.dto.LoanProductDetailDto;

import java.util.List;

/**
 * @author willwang
 */
public interface InnerProductService {

    /**
     * 获得小贷产品详情
     *
     * @param prodId
     * @return
     */
    LoanProductDetailDto getProdDtl(String prodId);

    /**
     * 获得产品列表
     * @return
     */
    List<LoanProductDetailDto> list();
}
