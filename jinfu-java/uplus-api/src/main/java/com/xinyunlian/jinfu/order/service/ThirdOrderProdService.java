package com.xinyunlian.jinfu.order.service;

import com.xinyunlian.jinfu.order.dto.ThirdOrderProdCountDto;
import com.xinyunlian.jinfu.order.dto.ThirdOrderProdDto;

import java.util.List;

/**
 * Created by menglei on 2017年03月08日.
 */
public interface ThirdOrderProdService {

    List<ThirdOrderProdDto> getListByOrderId(Long orderId);

    ThirdOrderProdDto getOne(Long orderProdId);

    List<ThirdOrderProdCountDto> getCountByOrderId(List<Long> orderIds);

}
