package com.xinyunlian.jinfu.feedback.dao;

import com.xinyunlian.jinfu.feedback.entity.LoanFeedbackPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


/**
 * @author willwang
 */
public interface LoanFeedbackDao extends JpaRepository<LoanFeedbackPo, Long>, JpaSpecificationExecutor<LoanFeedbackPo> {
}
