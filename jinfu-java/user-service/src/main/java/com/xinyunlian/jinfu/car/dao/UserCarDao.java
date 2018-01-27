package com.xinyunlian.jinfu.car.dao;

import com.xinyunlian.jinfu.car.entity.UserCarPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 用户车辆信息DAO接口
 * @author jll
 * @version 
 */
public interface UserCarDao extends JpaRepository<UserCarPo, Long>, JpaSpecificationExecutor<UserCarPo> {

    List<UserCarPo> findByUserId(String userId);
	
}
