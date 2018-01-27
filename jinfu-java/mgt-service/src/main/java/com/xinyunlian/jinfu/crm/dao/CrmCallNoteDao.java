package com.xinyunlian.jinfu.crm.dao;

import com.xinyunlian.jinfu.crm.entity.CrmCallNotePo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 来访电话处理记录DAO接口
 * @author jll
 * @version 
 */
public interface CrmCallNoteDao extends JpaRepository<CrmCallNotePo, Long>, JpaSpecificationExecutor<CrmCallNotePo> {
	List<CrmCallNotePo> findByCallId(Long callId);
}
