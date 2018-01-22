package com.xinyunlian.jinfu.dealer.dao;

import com.xinyunlian.jinfu.dealer.entity.DealerUserSubscribeViewPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by menglei on 2017年08月03日.
 */
public interface DealerUserSubscribeViewDao extends JpaRepository<DealerUserSubscribeViewPo, Long>, JpaSpecificationExecutor<DealerUserSubscribeViewPo> {

}
