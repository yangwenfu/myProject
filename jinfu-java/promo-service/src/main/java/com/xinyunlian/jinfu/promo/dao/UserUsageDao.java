package com.xinyunlian.jinfu.promo.dao;

import com.xinyunlian.jinfu.promo.entity.UserUsagePo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 用户使用记录DAO接口
 * @author jll
 * @version 
 */
public interface UserUsageDao extends JpaRepository<UserUsagePo, Long>, JpaSpecificationExecutor<UserUsagePo> {

    List<UserUsagePo> findByIdCardNoAndPromoId(String idCardNo,Long promoId);
	
}
