package com.xinyunlian.jinfu.loan.dao;

import com.xinyunlian.jinfu.loan.entity.LinkRepaySchdPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Collection;
import java.util.List;

/**
 * @author willwang
 */
public interface LinkRepaySchdDao extends JpaRepository<LinkRepaySchdPo, String>, JpaSpecificationExecutor<LinkRepaySchdPo> {

    List<LinkRepaySchdPo> findByRepayId(String repayId);

    List<LinkRepaySchdPo> findByRepayIdIn(Collection<String> repayIds);

    List<LinkRepaySchdPo> findBySchdId(String scheduleId);

    List<LinkRepaySchdPo> findBySchdIdIn(Collection<String> scheduleIds);

    LinkRepaySchdPo findByRepayIdAndSchdId(String repayId, String scheduleId);

}
