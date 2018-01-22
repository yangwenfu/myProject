package com.xinyunlian.jinfu.finbank.dao;

import com.xinyunlian.jinfu.finbank.entity.FinBankPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by dongfangchao on 2016/11/24/0024.
 */
public interface FinBankDao extends JpaRepository<FinBankPo, Long>, JpaSpecificationExecutor<FinBankPo> {
}
