package com.xinyunlian.jinfu.report.dealer.dao;

import com.xinyunlian.jinfu.report.dealer.entity.DealerStatsStorePo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by menglei on 2016/12/07.
 */
public interface DealerStatsStoreDao extends JpaRepository<DealerStatsStorePo, Long>, JpaSpecificationExecutor<DealerStatsStorePo> {

}
