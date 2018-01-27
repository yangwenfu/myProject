package com.xinyunlian.jinfu.app.dao;

import com.xinyunlian.jinfu.app.entity.AppOpLogPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by dongfangchao on 2016/12/30/0030.
 */
public interface AppOpLogDao extends JpaRepository<AppOpLogPo, Long>, JpaSpecificationExecutor<AppOpLogPo> {

    @Query(nativeQuery = true, value = "SELECT * FROM `app_op_log` WHERE APP_ID = ?1 AND UPDATE_TIME > ?2 AND APP_FORCE_UPDATE = 1 AND VERSION_NAME > ?3")
    List<AppOpLogPo> findForceUpdateAppInfo(Long appId, Date updateTime, String versionName);

    @Query(nativeQuery = true, value = "SELECT * FROM `app_op_log` WHERE APP_ID = ?1 AND VERSION_NAME = ?2 ORDER BY UPDATE_TIME DESC LIMIT 1")
    AppOpLogPo findLatestAppOpLog(Long appId, String versionName);

}
