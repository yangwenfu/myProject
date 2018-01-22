package com.xinyunlian.jinfu.report.dealer.dao;

import com.xinyunlian.jinfu.report.dealer.entity.InsurancePo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by bright on 2016/11/29.
 */
public interface InsuranceDao extends JpaRepository<InsurancePo, String>, JpaSpecificationExecutor<InsurancePo> {
}
