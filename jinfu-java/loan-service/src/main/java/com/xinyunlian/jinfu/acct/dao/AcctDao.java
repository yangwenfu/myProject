package com.xinyunlian.jinfu.acct.dao;

import com.xinyunlian.jinfu.acct.entity.AcctPo;
import com.xinyunlian.jinfu.loan.entity.LoanApplPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author willwang
 */
public interface AcctDao extends JpaRepository<AcctPo, String>, JpaSpecificationExecutor<AcctPo> {

    AcctPo findByUserIdAndProductId(String userId, String productId);

}
