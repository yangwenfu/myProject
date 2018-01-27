package com.xinyunlian.jinfu.external.dao;

import com.xinyunlian.jinfu.external.entity.LoanApplOutUserPo;
import com.xinyunlian.jinfu.loan.enums.EApplOutType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author willwang
 */
public interface LoanApplOutUserDao extends JpaRepository<LoanApplOutUserPo, String>, JpaSpecificationExecutor<LoanApplOutUserPo> {


    LoanApplOutUserPo findByUserIdAndType(String userId, EApplOutType type);

    LoanApplOutUserPo findByOutUserIdAndType(String outUserId, EApplOutType type);

}
