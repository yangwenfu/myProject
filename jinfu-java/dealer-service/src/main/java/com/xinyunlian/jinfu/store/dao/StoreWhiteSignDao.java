package com.xinyunlian.jinfu.store.dao;

import com.xinyunlian.jinfu.store.entity.StoreWhiteSignPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by menglei on 2017年06月20日.
 */
public interface StoreWhiteSignDao extends JpaRepository<StoreWhiteSignPo, Long>, JpaSpecificationExecutor<StoreWhiteSignPo> {

}
