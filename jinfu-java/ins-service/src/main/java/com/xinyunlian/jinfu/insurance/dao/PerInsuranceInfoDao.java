package com.xinyunlian.jinfu.insurance.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.xinyunlian.jinfu.insurance.entity.PerInsuranceInfoPo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by DongFC on 2016-09-21.
 */
public interface PerInsuranceInfoDao extends JpaRepository<PerInsuranceInfoPo, String>, JpaSpecificationExecutor<PerInsuranceInfoPo> {

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE PER_INSURANCE_INFO SET ORDER_STATUS = 'FAILURE' WHERE ORDER_STATUS = 'INPROCESS' AND" +
            " (CASE WHEN DAYOFWEEK(ORDER_DATE) in (7, 1) THEN TIMESTAMPDIFF(DAY, ORDER_DATE, NOW()) >= 9" +
            " ELSE TIMESTAMPDIFF(DAY, ORDER_DATE, NOW()) >= 10 END)")
    void updateExpiryInsOrder();

    @Query(nativeQuery = true, value = "SELECT t1.* FROM per_insurance_info t1 INNER JOIN pre_order_user_info t2 ON t1.PER_INSURANCE_ORDER_NO = t2.PER_INSURANCE_ORDER_NO " +
            "WHERE t1.ORDER_STATUS = 'SUCCESS' AND (t2.POLICY_FILE_DOWNLOADED <> '1' OR t2.POLICY_FILE_DOWNLOADED IS NULL)")
    List<PerInsuranceInfoPo> findNoPolicyFileRecord();

}
