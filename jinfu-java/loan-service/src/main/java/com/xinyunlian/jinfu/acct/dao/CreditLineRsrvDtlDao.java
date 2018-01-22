package com.xinyunlian.jinfu.acct.dao;

import com.xinyunlian.jinfu.acct.entity.CreditLineRsrvDtlPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 
 * @author willwang
 *
 */
public interface CreditLineRsrvDtlDao extends JpaRepository<CreditLineRsrvDtlPo, String>, JpaSpecificationExecutor<CreditLineRsrvDtlPo> {

}
