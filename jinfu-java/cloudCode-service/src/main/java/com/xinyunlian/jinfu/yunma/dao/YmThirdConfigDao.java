package com.xinyunlian.jinfu.yunma.dao;

import com.xinyunlian.jinfu.yunma.entity.YmThirdConfigPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by menglei on 2017年01月04日.
 */
public interface YmThirdConfigDao extends JpaRepository<YmThirdConfigPo, Long>, JpaSpecificationExecutor<YmThirdConfigPo> {

    YmThirdConfigPo findByAppId(String appId);

}
