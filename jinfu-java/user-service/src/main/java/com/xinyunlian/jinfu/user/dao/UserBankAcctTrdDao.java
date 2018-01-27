package com.xinyunlian.jinfu.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.xinyunlian.jinfu.user.entity.UserBankAcctTrdPo;

import java.util.List;

/**
 * 银行流水账户DAO接口
 * @author jll
 * @version 
 */
public interface UserBankAcctTrdDao extends JpaRepository<UserBankAcctTrdPo, Long>, JpaSpecificationExecutor<UserBankAcctTrdPo> {

    List<UserBankAcctTrdPo> findByBankAccountId(Long bankAccountId);
}
