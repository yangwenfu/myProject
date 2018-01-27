package com.xinyunlian.jinfu.product.dao;

import com.xinyunlian.jinfu.product.entity.LoanProductInfoPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author willwang
 */
public interface ProductInfoDao extends JpaRepository<LoanProductInfoPo, String>, JpaSpecificationExecutor<LoanProductInfoPo> {

}
