package com.xinyunlian.jinfu.ad.dao;

import com.xinyunlian.jinfu.ad.entity.AdPicPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by dongfangchao on 2017/1/6/0006.
 */
public interface AdPicDao extends JpaRepository<AdPicPo, Long>, JpaSpecificationExecutor<AdPicPo> {

    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM ad_pic WHERE AD_POS_SIZE_ID IN ?1")
    void deleteBatchByAdPosSizeIds(List<Long> list);

    List<AdPicPo> findByAdId(Long adId);

    @Query(nativeQuery = true, value = "SELECT * FROM ad_pic WHERE AD_POS_SIZE_ID=?1 AND AD_ID=?2")
    AdPicPo findByUnindex(Long adPosSizeId, Long adId);

    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM ad_pic WHERE AD_ID=?1")
    void deleteByAdId(Long adId);

    @Query(nativeQuery = true, value = "select t2.* from ad_inf t1 INNER JOIN ad_pic t2 on t1.AD_ID = t2.AD_ID WHERE t1.DISPLAY = 1 AND t1.AD_STATUS = 'NORMAL' AND t2.AD_POS_SIZE_ID = ?1 AND t1.END_DATE >= ?2 AND t1.START_DATE <= ?3")
    List<AdPicPo> findByAdPosSizeId(Long adPosSizeId, Date now, Date now2);

    @Query(nativeQuery = true, value = "SELECT * FROM ad_pic where PIC_WIDTH*PIC_HEIGHT=(SELECT MAX(PIC_HEIGHT*PIC_WIDTH) FROM ad_pic where AD_ID = ?1) AND AD_ID = ?2 LIMIT 1")
    AdPicPo findMaxPicByAdId(Long adId, Long adId2);

}
