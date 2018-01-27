package com.xinyunlian.jinfu.dealer.service;

import com.xinyunlian.jinfu.dealer.dto.DealerUserOrderDto;
import com.xinyunlian.jinfu.dealer.dto.DealerUserOrderSearchDto;

import java.util.List;

/**
 * Created by menglei on 2016年08月30日.
 */
public interface DealerUserOrderService {

    long getCount(DealerUserOrderDto dealerUserOrderDto);

    List<DealerUserOrderDto> getDealerUserOrderList(DealerUserOrderDto dealerUserOrderDto);

    List<DealerUserOrderDto> getOrderListByUserId(DealerUserOrderDto dealerUserOrderDto);

    DealerUserOrderSearchDto getOrderPage(DealerUserOrderSearchDto dealerUserOrderSearchDto);

    DealerUserOrderDto createDealerUserOrder(DealerUserOrderDto dealerUserOrderDto);

    void updateOrderStatus(DealerUserOrderDto dealerUserOrderDto);

    void updateExpireOrder();

}
