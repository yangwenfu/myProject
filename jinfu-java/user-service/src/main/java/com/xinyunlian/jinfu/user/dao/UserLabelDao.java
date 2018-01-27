package com.xinyunlian.jinfu.user.dao;

import com.xinyunlian.jinfu.user.entity.UserLabelPo;
import com.xinyunlian.jinfu.user.enums.ELabelType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 用户标签DAO接口
 * @author jll
 * @version 
 */
public interface UserLabelDao extends JpaRepository<UserLabelPo, Long>, JpaSpecificationExecutor<UserLabelPo> {
    List<UserLabelPo> findByUserId(String userId);

    List<UserLabelPo> findByUserIdAndLabelType(String userId, ELabelType labelType);

    void deleteByUserIdAndLabelType(String userId, ELabelType labelType);
}
