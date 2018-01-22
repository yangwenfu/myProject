package com.xinyunlian.jinfu.fintxhistory.dao;

import com.xinyunlian.jinfu.fintxhistory.entity.FinTxHistoryPo;
import com.xinyunlian.jinfu.fintxhistory.enums.ETxStatus;
import com.xinyunlian.jinfu.fintxhistory.enums.ETxType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by dongfangchao on 2016/11/21.
 */
public interface FinTxHistoryDao extends JpaRepository<FinTxHistoryPo, String>, JpaSpecificationExecutor<FinTxHistoryPo> {

    List<FinTxHistoryPo> findByTxTypeAndTxStatus(ETxType txType, ETxStatus txStatus);

}
