package com.xinyunlian.jinfu.industry.dao;

import com.xinyunlian.jinfu.industry.entity.IndustryPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by dongfangchao on 2017/5/17/0017.
 */
public interface IndustryDao extends JpaRepository<IndustryPo, Long>, JpaSpecificationExecutor<IndustryPo> {

    IndustryPo findByMcc(String mcc);

}
