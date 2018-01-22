package com.xinyunlian.jinfu.dict.dao;

import com.xinyunlian.jinfu.dict.entity.ScoreDictPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by dongfangchao on 2017/7/3/0003.
 */
public interface ScoreDictDao extends JpaRepository<ScoreDictPo, Long>, JpaSpecificationExecutor<ScoreDictPo> {

    ScoreDictPo findByScoreCode(String scoreCode);

}
