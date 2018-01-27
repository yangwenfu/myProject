package com.xinyunlian.jinfu.loan.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dao.ConfigDao;
import com.xinyunlian.jinfu.common.entity.ConfigPo;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.loan.dto.resp.credit.LoanApplCredit;
import com.xinyunlian.jinfu.loan.enums.ERepayMode;
import com.xinyunlian.jinfu.product.dao.CreditProductDao;
import com.xinyunlian.jinfu.product.dao.ProductInfoDao;
import com.xinyunlian.jinfu.product.dto.LoanProductDetailDto;
import com.xinyunlian.jinfu.product.entity.CreditTypeProductPo;
import com.xinyunlian.jinfu.product.entity.LoanProductInfoPo;
import com.xinyunlian.jinfu.product.enums.ELoanProductType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author willwang
 */
@Service
public class InnerProductServiceImpl implements InnerProductService {

    @Autowired
    private ProductInfoDao productInfoDao;

    @Autowired
    private CreditProductDao creditProductDao;

    @Override
    public LoanProductDetailDto getProdDtl(String prodId) {
        LoanProductInfoPo productInfoPo = productInfoDao.findOne(prodId);

        LoanProductDetailDto detailDto = ConverterService.convert(productInfoPo, LoanProductDetailDto.class);

        if(null != productInfoPo){
            ELoanProductType productType = productInfoPo.getProductType();
            switch (productType) {
                case CREDIT:
                    CreditTypeProductPo creditTypeProductPo = creditProductDao.findOne(prodId);
                    ConverterService.convert(creditTypeProductPo, detailDto);
                    break;
            }
        }

        return detailDto;
    }

    @Override
    public List<LoanProductDetailDto> list() {

        List<LoanProductDetailDto> list = new ArrayList<>();

        List<LoanProductInfoPo> loanProductInfoPos = productInfoDao.findAll();

        for (LoanProductInfoPo loanProductInfoPo : loanProductInfoPos) {
            LoanProductDetailDto detail = ConverterService.convert(loanProductInfoPo, LoanProductDetailDto.class);
            if(null != detail){
                ELoanProductType productType = detail.getProductType();
                switch (productType) {
                    case CREDIT:
                        CreditTypeProductPo creditTypeProductPo = creditProductDao.findOne(detail.getProductId());
                        ConverterService.convert(creditTypeProductPo, detail);
                        break;
                }
            }

            list.add(detail);
        }

        return list;
    }
}
