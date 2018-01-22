package com.xinyunlian.jinfu.product.service;

import com.xinyunlian.jinfu.loan.dto.resp.credit.LoanApplCredit;
import com.xinyunlian.jinfu.product.dto.LoanProdSearchDto;
import com.xinyunlian.jinfu.product.dto.LoanProductDetailDto;

import java.util.List;

/**
 * Created by JL on 2016/9/2.
 */
public interface LoanProductService {

    /**
     * 获得产品列表-后台
     *
     * @param prodSearchDto
     * @return
     */
    LoanProdSearchDto getProdList(LoanProdSearchDto prodSearchDto);

    /**
     * 设置产品信息-后台
     *
     * @param loanProductDetailDto
     */
    void setProdInfo(LoanProductDetailDto loanProductDetailDto);

    /**
     * 获得小贷产品详情
     *
     * @param prodId
     * @return
     */
    LoanProductDetailDto getProdDtl(String prodId);

    LoanApplCredit format(LoanProductDetailDto product);

    /**
     * 获取所有产品的配置
     * @return
     */
    List<LoanProductDetailDto> list();

}
