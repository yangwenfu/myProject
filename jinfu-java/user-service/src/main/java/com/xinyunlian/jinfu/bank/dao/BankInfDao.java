package com.xinyunlian.jinfu.bank.dao;

import com.xinyunlian.jinfu.bank.entity.BankInfPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 银行DAO接口
 * @author KimLL
 * @version 
 */
public interface BankInfDao extends JpaRepository<BankInfPo, Long>, JpaSpecificationExecutor<BankInfPo> {
    List<BankInfPo> findBySupport(boolean support);

    @Query(nativeQuery = true, value = "select * from sys_bank_inf where upper(bank_code) = upper(?1)")
    List<BankInfPo> findByBankCode(String bankCode);

}
