package com.xinyunlian.jinfu.store.dao;

import com.xinyunlian.jinfu.store.entity.StoreInfPo;
import com.xinyunlian.jinfu.store.enums.EStoreStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

/**
 * 用户DAO接口
 * @author KimLL
 * @version 
 */
public interface StoreDao extends JpaRepository<StoreInfPo, Long>, JpaSpecificationExecutor<StoreInfPo> {
    List<StoreInfPo> findByUserId(String userId);

    StoreInfPo findByTobaccoCertificateNoAndStatus(String tobaccoCertificateNo,EStoreStatus status);

    List<StoreInfPo> findByStatusAndTobaccoCertificateNoLike(EStoreStatus status, String tobaccoCertificateNo);

    List<StoreInfPo> findByUserIdAndStatus(String userId,EStoreStatus status);

    List<StoreInfPo> findByStoreNameLike(String storeName);

    @Query("from StoreInfPo i where i.districtId in ?1 and i.status='0' and i.userId is not null")
    List<StoreInfPo> findByDistrictIds(List<String> ids);

    @Query("from StoreInfPo i where i.storeId in ?1 and i.status='0'")
    List<StoreInfPo> findByStoreIds(List<Long> ids);

    @Query("from StoreInfPo i where i.userId in ?1 and i.status='0'")
    List<StoreInfPo> findByUserIds(Set<String> userIds);

    @Query("select i.storeId,i.userId from StoreInfPo i where i.districtId in ?1 and i.status='0'")
    List<Object[]> findStoreIdByDistrictIds(List<String> ids);

    @Query(nativeQuery = true, value = "select *,ifnull(sqrt((LAT-?4)*111111*(LAT-?4)*111111+(LNG-?3)*95057*(LNG-?3)*95057), 0) as distance " +
            "from store_inf where CITY=?2 and STATUS='0' and USER_ID is not null and USER_ID <> '' and LAT is not null and LNG is not null and LAT <> '' and LNG <> '' " +
            "and SCORE>=?7 and (PROVINCE_ID IN ?1 or CITY_ID IN ?1 or AREA_ID IN ?1 or STREET_ID IN ?1 and 1=1) " +
            "order by distance asc limit ?5,?6")
    List<StoreInfPo> findByAreaIdsAndCity(List<String> areaIds, String city, String lng, String lat, Integer startRow, Integer pageSize, Integer score);

    @Query("select distinct i.userId from StoreInfPo i WHERE  i.userId is not null and i.userId !='' and (i.provinceId in ?1 OR i.cityId in ?1 OR i.areaId in ( ?1 ))")
    List<String> findUsersWithAddressIds(List<String> ids);
    
    @Query("select distinct i.userId from StoreInfPo i WHERE i.userId is not null and i.userId !=''")
    List<String> findAllUsersByStore();

    @Query("select storeId from StoreInfPo where storeId > ?1 ORDER BY storeId")
    List<Object> findStoreIds(Long minStoreId, Pageable pageable);

    @Query("from StoreInfPo i where i.status='0' and i.suid = ?1")
    StoreInfPo findBySuid(String suid);
}
