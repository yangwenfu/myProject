package com.xinyunlian.jinfu.thirdparty.nbcb.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.thirdparty.nbcb.dto.NBCBOrderDto;
import com.xinyunlian.jinfu.thirdparty.nbcb.dto.NBCBOrderSearchDto;

import java.util.List;

/**
 * Created by bright on 2017/5/15.
 */
public interface NBCBOrderService {
    NBCBOrderDto findByOrderNo(String orderNo);

    String createOrder(NBCBOrderDto orderDto) throws BizServiceException;

    void updateOrder(NBCBOrderDto orderDto) throws BizServiceException;

    Boolean canApply(String userId);

    Boolean hasEntryPermission(String userId, List<String> cityIds);

    Boolean areaCovered(List<String> cityIds);

    List<String> getAllAppliedUserId();

    NBCBOrderSearchDto getPage(NBCBOrderSearchDto searchDto) throws BizServiceException;
}
