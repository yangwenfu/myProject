package com.xinyunlian.jinfu.dealer.dao;

import com.xinyunlian.jinfu.dealer.entity.DealerExtraPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by menglei on 2016年08月26日.
 */
public interface DealerExtraDao extends JpaRepository<DealerExtraPo, String>, JpaSpecificationExecutor<DealerExtraPo> {
}
