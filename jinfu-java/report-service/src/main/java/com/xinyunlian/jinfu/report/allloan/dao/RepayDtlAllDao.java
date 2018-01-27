package com.xinyunlian.jinfu.report.allloan.dao;

import com.xinyunlian.jinfu.report.allloan.entity.RepayDtlAllPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by dell on 2016/11/7.
 */
public interface RepayDtlAllDao extends JpaRepository<RepayDtlAllPo, String>, JpaSpecificationExecutor<RepayDtlAllPo> {

}
