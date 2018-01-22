package com.xinyunlian.jinfu.bank.dao;

import com.xinyunlian.jinfu.bank.entity.CorpBankPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by dongfangchao on 2017/5/18/0018.
 */
public interface CorpBankDao extends JpaRepository<CorpBankPo, Long>, JpaSpecificationExecutor<CorpBankPo> {

    CorpBankPo findByUserId(String userId);

}
