package com.xinyunlian.jinfu.yunma.dao;

import com.xinyunlian.jinfu.yunma.entity.YmPayChannelPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by menglei on 2017-07-19.
 */
public interface YmPayChannelDao extends JpaRepository<YmPayChannelPo, String>, JpaSpecificationExecutor<YmPayChannelPo> {

    @Query(nativeQuery = true, value = "SELECT * FROM ym_pay_channel WHERE ?1 LIKE concat('%',AREA_TREE_PATH,'%') AND CHANNEL IN ?2")
    List<YmPayChannelPo> findByChannelAndAreaTreePath(String areaTreePath, List<String> channel);

    @Query(nativeQuery = true, value = "SELECT * FROM ym_pay_channel WHERE AREA_ID=?1 AND CHANNEL IN ?2")
    List<YmPayChannelPo> findByChannelAndAreaId(Long areaId, List<String> channel);



}
