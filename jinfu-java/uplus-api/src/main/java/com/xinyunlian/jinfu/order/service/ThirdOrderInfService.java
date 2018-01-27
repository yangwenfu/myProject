package com.xinyunlian.jinfu.order.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.order.dto.ThirdOrderInfDto;
import com.xinyunlian.jinfu.order.dto.ThirdOrderInfSearchDto;
import com.xinyunlian.jinfu.order.dto.ThirdOrderProdDto;

import java.util.List;

/**
 * Created by menglei on 2017年03月08日.
 */
public interface ThirdOrderInfService {

    ThirdOrderInfSearchDto getThirdOrderInfPage(ThirdOrderInfSearchDto searchDto);

    ThirdOrderInfDto getOne(Long orderId);

    ThirdOrderInfDto getByOrderNo(String orderNo);

    void addOrderAndProd(ThirdOrderInfDto thirdOrderInfDto, List<ThirdOrderProdDto> orderProdList) throws BizServiceException;

}
