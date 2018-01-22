package com.xinyunlian.jinfu.creditline.dao;

import com.xinyunlian.jinfu.creditline.entity.LoanUserCreditLinePo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

/**
 * @author willwang
 */
public interface LoanUserCreditLineDao extends JpaRepository<LoanUserCreditLinePo, String> {

    List<LoanUserCreditLinePo> findByUserIdIn(Collection<String> userIds);

    LoanUserCreditLinePo findByUserId(String userId);

}
