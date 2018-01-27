package com.xinyunlian.jinfu.product.dao;

import com.xinyunlian.jinfu.product.entity.CreditTypeProductPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CreditProductDao extends JpaRepository<CreditTypeProductPo, String>, JpaSpecificationExecutor<CreditTypeProductPo> {

}
