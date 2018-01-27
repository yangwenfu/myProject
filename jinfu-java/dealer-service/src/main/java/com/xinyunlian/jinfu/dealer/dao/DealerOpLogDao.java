package com.xinyunlian.jinfu.dealer.dao;

import com.xinyunlian.jinfu.dealer.entity.DealerOpLogPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by menglei on 2017年05月09日.
 */
public interface DealerOpLogDao extends JpaRepository<DealerOpLogPo, Long>, JpaSpecificationExecutor<DealerOpLogPo> {

    List<DealerOpLogPo> findByDealerIdOrderByIdDesc(String dealerId);

}
