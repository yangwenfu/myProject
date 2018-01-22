package com.xinyunlian.jinfu.store.dao;

import com.xinyunlian.jinfu.store.entity.FranchisePo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 加盟店DAO接口
 *
 * @author menglei
 */
public interface FranchiseDao extends JpaRepository<FranchisePo, Long>, JpaSpecificationExecutor<FranchisePo> {

    FranchisePo findByStoreId(Long storeId);

    List<FranchisePo> findByStoreIdIn(List<Long> storeIds);

}
