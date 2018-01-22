package com.xinyunlian.jinfu.dict.dao;

import com.xinyunlian.jinfu.dict.entity.ActivityDictPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by dongfangchao on 2017/7/3/0003.
 */
public interface ActivityDictDao extends JpaRepository<ActivityDictPo, Long>, JpaSpecificationExecutor<ActivityDictPo> {

    ActivityDictPo findByActivityCode(String activityCode);

}
