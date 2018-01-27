package com.xinyunlian.jinfu.dealer.service;


import com.xinyunlian.jinfu.dealer.dto.DealerUserLogDto;
import com.xinyunlian.jinfu.dealer.dto.DealerUserLogSearchDto;
import com.xinyunlian.jinfu.dealer.enums.EDealerUserLogType;
import com.xinyunlian.jinfu.user.dto.DealerUserDto;

import java.util.List;

/**
 * Created by menglei on 2016年09月02日.
 */
public interface DealerUserLogService {

    DealerUserLogSearchDto getUserLogPage(DealerUserLogSearchDto dealerUserLogSearchDto);

    void createDealerUserLog(DealerUserLogDto dealerUserLogDto);

    void createDealerUserLog(DealerUserDto dealerUserDto, String lng, String lat, String address,
                             String storeUserId, String storeId, String remark, EDealerUserLogType type);

    List<DealerUserLogDto> findByStoreUserIdAndType(String storeUserId,EDealerUserLogType type);

}
