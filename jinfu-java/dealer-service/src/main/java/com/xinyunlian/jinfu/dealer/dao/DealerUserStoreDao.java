package com.xinyunlian.jinfu.dealer.dao;

import com.xinyunlian.jinfu.dealer.entity.DealerUserStorePo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by menglei on 2016年08月26日.
 */
public interface DealerUserStoreDao extends JpaRepository<DealerUserStorePo, Long>, JpaSpecificationExecutor<DealerUserStorePo> {

    List<DealerUserStorePo> findByStoreId(String storeId);

}
