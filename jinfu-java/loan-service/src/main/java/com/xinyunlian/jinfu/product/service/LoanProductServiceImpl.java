package com.xinyunlian.jinfu.product.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.BizUtil;
import com.xinyunlian.jinfu.loan.dto.resp.credit.LoanApplCredit;
import com.xinyunlian.jinfu.loan.service.InnerProductService;
import com.xinyunlian.jinfu.product.dao.CreditProductDao;
import com.xinyunlian.jinfu.product.dao.ProductInfoDao;
import com.xinyunlian.jinfu.product.dto.LoanProdSearchDto;
import com.xinyunlian.jinfu.product.dto.LoanProductDetailDto;
import com.xinyunlian.jinfu.product.dto.LoanProductInfoDto;
import com.xinyunlian.jinfu.product.entity.CreditTypeProductPo;
import com.xinyunlian.jinfu.product.entity.LoanProductInfoPo;
import com.xinyunlian.jinfu.product.enums.ELoanProductType;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class LoanProductServiceImpl implements LoanProductService {

    @Autowired
    private ProductInfoDao productInfoDao;

    @Autowired
    private CreditProductDao creditProductDao;

    @Autowired
    private InnerProductService innerProductService;

    @Override
    public LoanProdSearchDto getProdList(LoanProdSearchDto prodSearchDto) {
        PageRequest pageRequest = new PageRequest(prodSearchDto.getCurrentPage() - 1, prodSearchDto.getPageSize());
        Specification<LoanProductInfoPo> specification = (root, criteriaQuery, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (null != prodSearchDto) {
                if (StringUtils.isNotBlank(prodSearchDto.getProdId())) {
                    expressions.add(cb.like(root.get("productId"), BizUtil.filterStringRight(prodSearchDto.getProdId())));
                }
                if (StringUtils.isNotBlank(prodSearchDto.getProdName())) {
                    expressions.add(cb.like(root.get("productName"), BizUtil.filterStringRight(prodSearchDto.getProdName())));
                }
                if (null != prodSearchDto.getLoanProductType()) {
                    expressions.add(cb.equal(root.get("productType"), prodSearchDto.getLoanProductType()));
                }
            }
            return predicate;
        };
        Page<LoanProductInfoPo> page = productInfoDao.findAll(specification, pageRequest);
        List<LoanProductInfoPo> pos = page.getContent();
        List<LoanProductInfoDto> loanProductDtos = new ArrayList<>();
        for (LoanProductInfoPo po : pos
                ) {
            loanProductDtos.add(ConverterService.convert(po, LoanProductInfoDto.class));
        }
        prodSearchDto.setList(loanProductDtos);
        prodSearchDto.setTotalRecord(page.getTotalElements());
        prodSearchDto.setTotalPages(page.getTotalPages());
        return prodSearchDto;
    }

    @Override
    public LoanProductDetailDto getProdDtl(String prodId) {
        return innerProductService.getProdDtl(prodId);
    }

    @Override
    public LoanApplCredit format(LoanProductDetailDto product) {
        if (product.getProductType() != ELoanProductType.CREDIT) {
            throw new BizServiceException(EErrorCode.LOAN_PRODUCT_NOT_SUPPORT);
        }

        LoanApplCredit loanApplCredit = ConverterService.convert(product, LoanApplCredit.class);
        loanApplCredit.setTermLenList(this.split(product.getTermLen()));
        loanApplCredit.setPeriodUnit(product.getTermType().getUnit());

        return loanApplCredit;
    }

    private  List<Integer> split(String termLen) {
        List<Integer> rs = new ArrayList<>();
        if (termLen.contains(",")) {
            List<String> list = Arrays.asList(termLen.split(","));
            for (String s : list) {
                rs.add(Integer.valueOf(s));
            }
        } else {
            rs.add(Integer.valueOf(termLen));
        }
        return rs;
    }

    @Override
    public List<LoanProductDetailDto> list() {
        List<LoanProductDetailDto> rs = new ArrayList<>();
        List<LoanProductDetailDto> loanProductDetailDtos = innerProductService.list();
        for (LoanProductDetailDto loanProductDetailDto : loanProductDetailDtos) {
            if(this.isActivityProduct(loanProductDetailDto)){
                rs.add(loanProductDetailDto);
            }
        }
        return rs;
    }

    private boolean isActivityProduct(LoanProductDetailDto product){
        return "L01001".equals(product.getProductId()) || "L01002".equals(product.getProductId());
    }

    @Override
    @Transactional
    public void setProdInfo(LoanProductDetailDto loanProductDetailDto) {
        LoanProductInfoPo productInfoPo = ConverterService.convert(loanProductDetailDto, LoanProductInfoPo.class);
        productInfoDao.save(productInfoPo);
        ELoanProductType productType = productInfoPo.getProductType();
        switch (productType) {
            case CREDIT:
                CreditTypeProductPo creditTypeProductPo = ConverterService.convert(loanProductDetailDto, CreditTypeProductPo.class);
                creditTypeProductPo.setProductId(productInfoPo.getProductId());
                creditProductDao.save(creditTypeProductPo);
                break;
        }

    }

}
