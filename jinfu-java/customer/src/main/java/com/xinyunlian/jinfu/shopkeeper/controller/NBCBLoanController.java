package com.xinyunlian.jinfu.shopkeeper.controller;

import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.thirdparty.nbcb.dto.NBCBOrderDto;
import com.xinyunlian.jinfu.thirdparty.nbcb.dto.NBCBOrderSearchDto;
import com.xinyunlian.jinfu.thirdparty.nbcb.service.NBCBOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by dongfangchao on 2017/7/28/0028.
 */
@RestController
@RequestMapping("shopkeeper/nbcbLoan")
public class NBCBLoanController {

    @Autowired
    private NBCBOrderService nbcbOrderService;

    @GetMapping("getOrderPage")
    public ResultDto getOrderPage(NBCBOrderSearchDto searchDto){
        String userId = SecurityContext.getCurrentUserId();
        if (StringUtils.isEmpty(userId)){
            return ResultDtoFactory.toNack("用户未登录");
        }
        searchDto.setUserId(userId);
        NBCBOrderSearchDto page = nbcbOrderService.getPage(searchDto);
        return ResultDtoFactory.toAckData(page);
    }

    @GetMapping("getOrderDetail")
    public ResultDto getOrderDetail(String orderNo){
        NBCBOrderDto order = nbcbOrderService.findByOrderNo(orderNo);
        return ResultDtoFactory.toAckData(order);
    }

}
