package com.xinyunlian.jinfu.rule.dao;

import com.xinyunlian.jinfu.rule.entity.RuleFullOffGradPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 满减梯度DAO接口
 * @author jll
 * @version 
 */
public interface RuleFullOffGradDao extends JpaRepository<RuleFullOffGradPo, Long>, JpaSpecificationExecutor<RuleFullOffGradPo> {

    @Query("from RuleFullOffGradPo where offId = ?1 order by amount")
    List<RuleFullOffGradPo> findByOffId(Long offId);

    @Query(nativeQuery = true, value = "DELETE  FROM  RULE_FULL_OFF_GRAD WHERE OFF_ID = ?1")
    @Modifying
    void deleteByOffId(Long offId);
}
