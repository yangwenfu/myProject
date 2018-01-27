package com.xinyunlian.jinfu.promo.dao;

import com.xinyunlian.jinfu.promo.entity.PromoInfPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 促销活动信息DAO接口
 * @author jll
 * @version 
 */
public interface PromoInfDao extends JpaRepository<PromoInfPo, Long>, JpaSpecificationExecutor<PromoInfPo> {
    @Query("from PromoInfPo where sysdate() between startDate and endDate and status = 'active' " +
            " and curNum<totalLimit and platform like ?2 and prodId = ?1 ")
    List<PromoInfPo> findByProdIdAndPlatformLike(String prodId,String platform);
}
