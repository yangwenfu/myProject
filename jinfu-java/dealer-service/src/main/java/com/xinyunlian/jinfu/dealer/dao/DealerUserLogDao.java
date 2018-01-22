package com.xinyunlian.jinfu.dealer.dao;

import com.xinyunlian.jinfu.dealer.entity.DealerUserLogPo;
import com.xinyunlian.jinfu.dealer.enums.EDealerUserLogType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by menglei on 2016年08月26日.
 */
public interface DealerUserLogDao extends JpaRepository<DealerUserLogPo, Long>, JpaSpecificationExecutor<DealerUserLogPo> {

    List<DealerUserLogPo> findByStoreUserIdAndType(String storeUserId,EDealerUserLogType type);
}
