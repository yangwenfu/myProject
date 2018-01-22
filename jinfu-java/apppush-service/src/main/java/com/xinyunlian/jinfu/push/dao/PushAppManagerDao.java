package com.xinyunlian.jinfu.push.dao;

import com.xinyunlian.jinfu.push.entity.PushAppManagerPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by apple on 2017/3/8.
 */
public interface PushAppManagerDao extends JpaRepository<PushAppManagerPo, String>, JpaSpecificationExecutor<PushAppManagerPo> {

    PushAppManagerPo findByAppType(int appType);
}
