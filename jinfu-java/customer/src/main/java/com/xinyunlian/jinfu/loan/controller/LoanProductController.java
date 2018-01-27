package com.xinyunlian.jinfu.loan.controller;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.util.MessageUtil;
import com.xinyunlian.jinfu.common.util.NumberUtil;
import com.xinyunlian.jinfu.loan.dto.rec.RecUserDto;
import com.xinyunlian.jinfu.loan.dto.resp.credit.LoanApplCredit;
import com.xinyunlian.jinfu.loan.enums.ERepayMode;
import com.xinyunlian.jinfu.loan.service.PrivateLoanService;
import com.xinyunlian.jinfu.prod.dto.ProductDto;
import com.xinyunlian.jinfu.prod.service.ProdService;
import com.xinyunlian.jinfu.product.dto.LoanProductDetailDto;
import com.xinyunlian.jinfu.product.service.LoanProductService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

/**
 * @author willwang
 */

@Controller
@RequestMapping(value = "loan/product")
public class LoanProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoanProductController.class);

    @Autowired
    private LoanProductService loanProductService;

    @Autowired
    private ProdService prodService;

    @Autowired
    private PrivateLoanService privateLoanService;

    /**
     * 产品详情
     * @return
     */
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto detail(@RequestParam String id) {

        String userId = SecurityContext.getCurrentUserId();

        LoanProductDetailDto product = loanProductService.getProdDtl(id);

        if(product == null){
            LOGGER.warn("user:{} visit unSupport poroduct:{}", userId, id);
            return ResultDtoFactory.toNack(MessageUtil.getMessage("common.operate.fail"));
        }

        LoanApplCredit loanApplCredit = loanProductService.format(product);

        com.xinyunlian.jinfu.loan.dto.product.LoanProductDetailDto rs = ConverterService.convert(
            product, com.xinyunlian.jinfu.loan.dto.product.LoanProductDetailDto.class
        );

        rs.setId(product.getProductId());
        rs.setName(product.getProductName());
        rs.setPeriods(loanApplCredit.getTermLenList());
        rs.setUnit(loanApplCredit.getTermType().getUnit());
        rs.setRepayMode(product.getRepayMode());
        //根据还款方式决定要返回的利率类型是什么
        rs.setRate(product.getRepayMode().getRate(
            product.getIntrRateType(), product.getIntrRate()
        ));

        //月综合服务费
        if(!StringUtils.isEmpty(product.getServiceFeeMonthRt())){
            for (String s : product.getServiceFeeMonthRt().split(",")) {
                rs.getServiceFeeMonthRts()
                        .add(NumberUtil.roundTwo(new BigDecimal(s).add(rs.getRate())));
            }
        }

        rs = this.compelteCustom(rs);

        RecUserDto recUserDto = privateLoanService.getRecUser(userId);
        ConverterService.convert(recUserDto, rs);

        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), rs);
    }

    /**
     * 从内容管理里取数据进行补充
     * @param loanProductDetailDto
     * @return
     */
    private com.xinyunlian.jinfu.loan.dto.product.LoanProductDetailDto compelteCustom(com.xinyunlian.jinfu.loan.dto.product.LoanProductDetailDto loanProductDetailDto){

        ProductDto productDto = prodService.getProdById(loanProductDetailDto.getId());

        String customInterestType = "月利率";
        if(loanProductDetailDto.getRepayMode() == ERepayMode.INTR_PER_DIEM){
            customInterestType = "日利率";
        }

        String customInterest =
            NumberUtil.roundTwo(loanProductDetailDto.getRate().multiply(BigDecimal.valueOf(100))).toString() + "%";

        if(productDto != null && productDto.getProdAppDetailDto() != null){

            String t1 = productDto.getProdAppDetailDto().getProdSubTitleLeft();
            String t2 = productDto.getProdAppDetailDto().getProdTitle();

            if(StringUtils.isNotEmpty(t1)){
                customInterestType = t1;
            }

            if(StringUtils.isNotEmpty(t2)){
                customInterest = t2;
            }

        }

        loanProductDetailDto.setCustomInterestType(customInterestType);
        loanProductDetailDto.setCustomInterest(customInterest);

        return loanProductDetailDto;
    }

}
