package com.xinyunlian.jinfu.finprofithistory.dao;

import com.xinyunlian.jinfu.finprofithistory.entity.FinProfitHistorySumPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;

/**
 * Created by dongfangchao on 2016/11/21.
 */
public interface FinProfitHistorySumDao extends JpaRepository<FinProfitHistorySumPo, Long>, JpaSpecificationExecutor<FinProfitHistorySumPo> {

    FinProfitHistorySumPo findByUserIdAndProfitDate(String userId, Date profitDate);

}
