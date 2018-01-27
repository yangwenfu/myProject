package com.xinyunlian.jinfu.loan.product.controller;

import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.util.MessageUtil;
import com.xinyunlian.jinfu.product.dto.LoanProdSearchDto;
import com.xinyunlian.jinfu.product.dto.LoanProductDetailDto;
import com.xinyunlian.jinfu.product.service.LoanProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by JL on 2016/9/2.
 */
@Controller
@RequestMapping(value = "/loan/product")
public class LoanProductController {

    @Autowired
    private LoanProductService loanProductService;

    /**
     * 获得贷款产品列表
     *
     * @param prodSearchDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getProdList", method = RequestMethod.POST)
    private ResultDto<LoanProdSearchDto> getProdList(@RequestBody LoanProdSearchDto prodSearchDto) {
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), loanProductService.getProdList(prodSearchDto));
    }


    /**
     * 获得贷款产品详情
     *
     * @param prodId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/prodDtl", method = RequestMethod.GET)
    private ResultDto<LoanProductDetailDto> getProdDtl(@RequestParam String prodId) {
        LoanProductDetailDto loanProductDetailDto = loanProductService.getProdDtl(prodId);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), loanProductDetailDto);
    }


    /**
     * 设置贷款详情
     *
     * @param detailDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/prodDtl", method = RequestMethod.POST)
    private ResultDto<Object> setProdDtl(@RequestBody LoanProductDetailDto detailDto) {
        loanProductService.setProdInfo(detailDto);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }


}
