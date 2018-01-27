package com.xinyunlian.jinfu.report.allloan.dao;

import com.xinyunlian.jinfu.report.allloan.entity.RepayScheduleAllPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by dell on 2016/11/7.
 */
public interface RepayScheduleAllDao extends JpaRepository<RepayScheduleAllPo, String>, JpaSpecificationExecutor<RepayScheduleAllPo> {
}
