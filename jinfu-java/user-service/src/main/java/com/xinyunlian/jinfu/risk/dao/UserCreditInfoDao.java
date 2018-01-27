package com.xinyunlian.jinfu.risk.dao;

import com.xinyunlian.jinfu.risk.entity.UserCreditInfoPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by bright on 2016/11/16.
 */
public interface UserCreditInfoDao extends JpaRepository<UserCreditInfoPo, Long>, JpaSpecificationExecutor<UserCreditInfoPo>{
    public Long getVersionCtByUserIdOrderByVersionCtDesc(String userId);
}
