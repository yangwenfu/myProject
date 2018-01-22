package com.xinyunlian.jinfu.dealer.dao;

import com.xinyunlian.jinfu.dealer.entity.DealerSendPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by menglei on 2016年08月26日.
 */
public interface DealerSendDao extends JpaRepository<DealerSendPo, String>, JpaSpecificationExecutor<DealerSendPo> {
}
