package com.xinyunlian.jinfu.ad.dao;

import com.xinyunlian.jinfu.ad.entity.AdPosSizePo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by dongfangchao on 2017/1/6/0006.
 */
public interface AdPosSizeDao extends JpaRepository<AdPosSizePo, Long>, JpaSpecificationExecutor<AdPosSizePo> {

    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM ad_pos_size WHERE ID IN ?1")
    void deleteBatchByIds(List<Long> list);

    List<AdPosSizePo> findByAdPosId(Long adPosId);

}
