package com.xinyunlian.jinfu.house.dao;

import com.xinyunlian.jinfu.house.entity.UserHousePo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 用户房产信息DAO接口
 * @author jll
 * @version 
 */
public interface UserHouseDao extends JpaRepository<UserHousePo, Long>, JpaSpecificationExecutor<UserHousePo> {

    List<UserHousePo> findByUserId(String userId);
	
}
