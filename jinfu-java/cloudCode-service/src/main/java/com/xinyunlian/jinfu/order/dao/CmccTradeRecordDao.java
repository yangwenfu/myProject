package com.xinyunlian.jinfu.order.dao;

import com.xinyunlian.jinfu.order.entity.CmccTradeRecordPo;
import com.xinyunlian.jinfu.order.enums.ECmccOrderTradeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by menglei on 2016年11月20日.
 */
public interface CmccTradeRecordDao extends JpaRepository<CmccTradeRecordPo, String>, JpaSpecificationExecutor<CmccTradeRecordPo> {

    List<CmccTradeRecordPo> findByTradeStatus(ECmccOrderTradeStatus tradeStatus);

}
