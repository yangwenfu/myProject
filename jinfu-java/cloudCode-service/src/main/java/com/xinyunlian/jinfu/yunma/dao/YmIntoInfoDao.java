package com.xinyunlian.jinfu.yunma.dao;

import com.xinyunlian.jinfu.yunma.entity.YmIntoInfoPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by menglei on 2017-07-19.
 */
public interface YmIntoInfoDao extends JpaRepository<YmIntoInfoPo, String>, JpaSpecificationExecutor<YmIntoInfoPo> {

    YmIntoInfoPo findByMemberNoAndChannel(String memberNo, String channel);

}
