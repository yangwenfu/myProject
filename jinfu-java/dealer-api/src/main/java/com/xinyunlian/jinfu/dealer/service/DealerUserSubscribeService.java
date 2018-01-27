package com.xinyunlian.jinfu.dealer.service;


import com.xinyunlian.jinfu.dealer.dto.DealerUserSubscribeDto;
import com.xinyunlian.jinfu.dealer.dto.DealerUserSubscribeSearchDto;

import java.util.List;

/**
 * Created by menglei on 2017年08月03日.
 */
public interface DealerUserSubscribeService {

    DealerUserSubscribeSearchDto getPage(DealerUserSubscribeSearchDto dealerUserSubscribeSearchDto);

    List<DealerUserSubscribeDto> getReport(DealerUserSubscribeSearchDto dealerUserSubscribeSearchDto);

    void createDealerUserSubscribe(DealerUserSubscribeDto dealerUserSubscribeDto);

}
