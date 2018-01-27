package com.xinyunlian.jinfu.yunma.dao;

import com.xinyunlian.jinfu.trade.enums.EBizCode;
import com.xinyunlian.jinfu.yunma.entity.YmMemberBizPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 云码店铺费率配置DAO接口
 * @author jll
 * @version 
 */
public interface YmMemberBizDao extends JpaRepository<YmMemberBizPo, Long>, JpaSpecificationExecutor<YmMemberBizPo> {
	List<YmMemberBizPo> findByMemberNo(String memberNo);

	YmMemberBizPo findByMemberNoAndBizCode(String memberNo, EBizCode bizCode);
}
