package com.xinyunlian.jinfu.user.dao;

import com.xinyunlian.jinfu.user.entity.UserBankAcctPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 银行流水账户DAO接口
 * @author jll
 * @version 
 */
public interface UserBankAcctDao extends JpaRepository<UserBankAcctPo, Long>, JpaSpecificationExecutor<UserBankAcctPo> {

    List<UserBankAcctPo> findByUserId(String userId);
	
}
