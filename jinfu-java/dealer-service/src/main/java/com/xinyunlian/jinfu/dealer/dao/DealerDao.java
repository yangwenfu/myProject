package com.xinyunlian.jinfu.dealer.dao;

import com.xinyunlian.jinfu.dealer.entity.DealerPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by menglei on 2016年08月26日.
 */
public interface DealerDao extends JpaRepository<DealerPo, String>, JpaSpecificationExecutor<DealerPo> {

    List<DealerPo> findByDealerIdIn(List<String> dealerIds);

    List<DealerPo> findByDealerName(String dealerName);

    @Query(nativeQuery = true, value = "select * from dealer where DEALER_NAME LIKE CONCAT('%',?1, '%')")
    List<DealerPo> findByDealerNameLike(String dealerName);

    @Query("from DealerPo d, DealerUserPo  du where d.dealerId = du.dealerId and du.mobile = ?1")
    DealerPo findByDealerUserMobile(String dealerUserMobile);
}
