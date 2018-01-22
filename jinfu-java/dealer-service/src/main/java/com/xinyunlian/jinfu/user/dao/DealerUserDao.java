package com.xinyunlian.jinfu.user.dao;

import com.xinyunlian.jinfu.user.entity.DealerUserPo;
import com.xinyunlian.jinfu.user.enums.EDealerUserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by menglei on 2016年08月26日.
 */
public interface DealerUserDao extends JpaRepository<DealerUserPo, String>, JpaSpecificationExecutor<DealerUserPo> {

    DealerUserPo findByMobileAndPasswordAndStatus(String mobile, String password, EDealerUserStatus status);

    DealerUserPo findByMobileAndPassword(String mobile, String password);

    DealerUserPo findByMobile(String mobile);

    @Query(nativeQuery = true, value = "select * from dealer_user where DEALER_ID=?1 and STATUS!='DELETE'")
    List<DealerUserPo> findByDealerId(String dealerId);

    @Query("SELECT a.userId,a.mobile FROM DealerUserPo a, DealerPo b WHERE a.dealerId = b.dealerId AND (b.provinceId in ?1 OR b.cityId in ?1 OR b.areaId in ( ?1 ) )")
    List<Object[]> findUsersWithAddressIds(List<String> ids);

    @Query("SELECT a.userId,a.mobile FROM DealerUserPo a, DealerPo b WHERE a.dealerId = b.dealerId")
    List<Object[]> findAllDealerUsers();

    List<DealerUserPo> findByUserIdIn(List<String> dealerUserIds);

    List<DealerUserPo> findByMobileIn(List<String> mobiles);

    @Query(nativeQuery = true, value = "select * from dealer_user where NAME LIKE CONCAT('%',?1, '%')")
    List<DealerUserPo> findByNameLike(String name);

}
