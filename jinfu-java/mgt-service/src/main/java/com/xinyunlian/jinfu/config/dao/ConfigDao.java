package com.xinyunlian.jinfu.config.dao;

import com.xinyunlian.jinfu.config.entity.ConfigPo;
import com.xinyunlian.jinfu.config.enums.ECategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by bright on 2017/1/6.
 */
public interface ConfigDao extends JpaRepository<ConfigPo, Long>, JpaSpecificationExecutor<ConfigPo> {
    List<ConfigPo> getByCategory(ECategory category);

    ConfigPo getByCategoryAndKey(ECategory category, String key);

    List<ConfigPo> getByCached(Boolean cached);
}
