package com.xinyunlian.jinfu.report.dealer.dao;

import com.xinyunlian.jinfu.report.dealer.entity.OldUserReportPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by bright on 2016/11/29.
 */
public interface OldUserReportDao extends JpaRepository<OldUserReportPo, String>, JpaSpecificationExecutor<OldUserReportPo> {
}
