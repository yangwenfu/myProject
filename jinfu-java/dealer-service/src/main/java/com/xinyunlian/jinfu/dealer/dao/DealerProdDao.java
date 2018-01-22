package com.xinyunlian.jinfu.dealer.dao;

import com.xinyunlian.jinfu.dealer.entity.DealerProdPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by menglei on 2016年08月26日.
 */
public interface DealerProdDao extends JpaRepository<DealerProdPo, Long>, JpaSpecificationExecutor<DealerProdPo> {

    void deleteByDealerId(String dealerId);

    List<DealerProdPo> findByDistrictIdIn(List<String> districtIds);

    @Query(nativeQuery = true, value = "select * from dealer_prod where DEALER_ID=?1 and PROVINCE_ID=?2 " +
            "and (CITY_ID=?3 or CITY_ID='') and (AREA_ID=?4 or AREA_ID='') and (STREET_ID=?5 or STREET_ID='')")
    List<DealerProdPo> findByDealerIdAndArea(String dealerId, String provinceId, String cityId, String areaId, String streetId);

    @Query(nativeQuery = true, value = "select * from dealer_prod where DEALER_ID=?1 and PROVINCE_ID=?2 " +
            "and (CITY_ID=?3 or CITY_ID='') and (AREA_ID=?4 or AREA_ID='') and (STREET_ID=?5 or STREET_ID='') and PROD_ID=?6")
    List<DealerProdPo> findByDealerIdAndAreaAndProdId(String dealerId, String provinceId, String cityId, String areaId, String streetId, String prodId);

}
