package com.xinyunlian.jinfu.user.dao;

import com.xinyunlian.jinfu.user.entity.OperationLogPo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by JL on 2016/9/5.
 */
public interface OperationLogDao extends JpaRepository<OperationLogPo, Long> {
}
