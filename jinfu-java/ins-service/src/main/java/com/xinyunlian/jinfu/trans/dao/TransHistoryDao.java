package com.xinyunlian.jinfu.trans.dao;

import com.xinyunlian.jinfu.trans.entity.TransHistoryPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by dongfangchao on 2017/6/12/0012.
 */
public interface TransHistoryDao extends JpaRepository<TransHistoryPo, String>, JpaSpecificationExecutor<TransHistoryPo>{
}
