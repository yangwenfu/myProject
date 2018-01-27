package com.xinyunlian.jinfu.finfunddetail.dao;

import com.xinyunlian.jinfu.finaccbankcard.enums.EFinOrg;
import com.xinyunlian.jinfu.finfunddetail.entity.FinFundDetailPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by dongfangchao on 2016/11/21.
 */
public interface FinFundDetailDao extends JpaRepository<FinFundDetailPo, Long>, JpaSpecificationExecutor<FinFundDetailPo> {

    FinFundDetailPo findByFinFundCodeAndFinOrg(String finFundCode, EFinOrg finOrg);

}
