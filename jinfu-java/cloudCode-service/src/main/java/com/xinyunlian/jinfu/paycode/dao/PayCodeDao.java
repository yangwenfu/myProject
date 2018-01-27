package com.xinyunlian.jinfu.paycode.dao;

import com.xinyunlian.jinfu.paycode.entity.PayCodePo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by carrot on 2017/8/28.
 */
public interface PayCodeDao extends JpaRepository<PayCodePo, String>, JpaSpecificationExecutor<PayCodePo> {

    PayCodePo findByPayCodeNo(String payCodeNo);

    PayCodePo findByPayCodeUrl(String payCodeUrl);

    @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM pay_code where STATUS != 'DISABLE' AND (PAY_CODE_NO = ?1 OR MOBILE = ?2)")
    Long countByPayCodeNoOrMobile(String payCodeNo, String mobile);

}
