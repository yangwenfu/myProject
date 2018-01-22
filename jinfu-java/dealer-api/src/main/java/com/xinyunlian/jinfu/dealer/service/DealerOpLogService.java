package com.xinyunlian.jinfu.dealer.service;

import com.xinyunlian.jinfu.dealer.dto.DealerOpLogDto;

import java.util.List;

/**
 * Created by menglei on 2017年05月09日.
 */
public interface DealerOpLogService {

    List<DealerOpLogDto> getByDealerId(String dealerId);

    void createDealerOpLog(DealerOpLogDto dealerOpLogDto);

    DealerOpLogDto getById(Long id);

}
