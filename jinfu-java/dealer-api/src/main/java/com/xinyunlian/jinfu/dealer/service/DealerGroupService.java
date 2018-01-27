package com.xinyunlian.jinfu.dealer.service;

import com.xinyunlian.jinfu.dealer.dto.DealerGroupDto;
import com.xinyunlian.jinfu.dealer.dto.DealerGroupSearchDto;

import java.util.List;

/**
 * Created by menglei on 2016年08月26日.
 */
public interface DealerGroupService {

    List<DealerGroupDto> getGroupList(DealerGroupSearchDto dealerGroupSearchDto);

    void createDealerGroup(DealerGroupDto dealerGroupDto);

    void updateDealerGroup(DealerGroupDto dealerGroupDto);

    void deleteDealerGroup(String groupId);

}
