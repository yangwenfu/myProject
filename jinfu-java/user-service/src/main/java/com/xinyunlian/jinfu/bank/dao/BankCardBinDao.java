package com.xinyunlian.jinfu.bank.dao;

import com.xinyunlian.jinfu.bank.entity.BankCardBinPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 卡BinDAO接口
 * @author jll
 * @version 
 */
public interface BankCardBinDao extends JpaRepository<BankCardBinPo, String>, JpaSpecificationExecutor<BankCardBinPo> {
    @Query(" from BankCardBinPo where numLength = length(?1) " +
            "and BIN = substring(?1, 1, BIN_LENGTH) order by BIN desc")
    List<BankCardBinPo> findByCardNo(String cardNo);
}
