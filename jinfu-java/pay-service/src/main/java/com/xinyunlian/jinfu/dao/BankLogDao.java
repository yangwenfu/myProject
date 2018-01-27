package com.xinyunlian.jinfu.dao;

import com.xinyunlian.jinfu.entity.BankLogPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


/**
 * 
 * @author cheng
 *
 */
public interface BankLogDao extends JpaRepository<BankLogPo, String>, JpaSpecificationExecutor<BankLogPo> {

}
