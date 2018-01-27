package com.xinyunlian.jinfu.yunma.dao;

import com.xinyunlian.jinfu.yunma.entity.YMProductPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by menglei on 2017-01-05.
 */
public interface YMProductDao extends JpaRepository<YMProductPo, String>, JpaSpecificationExecutor<YMProductPo> {

}
