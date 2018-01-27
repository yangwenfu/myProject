package com.xinyunlian.jinfu.ad.dao;

import com.xinyunlian.jinfu.ad.entity.AdInfPo;
import com.xinyunlian.jinfu.ad.enums.EAdStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by DongFC on 2016-08-18.
 */
public interface AdInfDao extends JpaRepository<AdInfPo, Long>, JpaSpecificationExecutor<AdInfPo> {

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE ad_inf SET AD_STATUS = ?1 where AD_ID IN ?2")
    void updateAdStatusByAdIds(String adStatus, List<Long> adIds);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE ad_inf SET AD_STATUS = ?1 where AD_POS_ID = ?2")
    void updateAdStatusByPosId(String adStatus, Long posId);

    List<AdInfPo> findByAdStatusAndAdPosId(EAdStatus adStatus, Long posId);

}
