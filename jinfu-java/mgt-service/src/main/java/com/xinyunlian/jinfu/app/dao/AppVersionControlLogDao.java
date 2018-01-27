package com.xinyunlian.jinfu.app.dao;

import com.xinyunlian.jinfu.app.entity.AppVersionControlLogPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by dongfangchao on 2017/7/5/0005.
 */
public interface AppVersionControlLogDao extends JpaRepository<AppVersionControlLogPo, Long>, JpaSpecificationExecutor<AppVersionControlLogPo> {

    @Query(nativeQuery = true, value = "SELECT * FROM app_version_control_log WHERE APP_SOURCE=?1 AND OPERATING_SYSTEM=?2 AND VERSION_NAME=?3")
    AppVersionControlLogPo findByAppSourceAndOperatingSystemAndVersionName(String appSource, String operatingSystem, String versionName);

    @Query(nativeQuery = true, value = "SELECT * FROM app_version_control_log WHERE APP_SOURCE=?1 AND OPERATING_SYSTEM=?2 ORDER BY APP_LOG_ID DESC LIMIT 1, 1 ")
    AppVersionControlLogPo findThePreviousRelease(String appSource, String operatingSystem);

    @Query(nativeQuery = true, value = "SELECT * FROM app_version_control_log WHERE APP_SOURCE=?1 AND OPERATING_SYSTEM=?2 ORDER BY APP_LOG_ID DESC LIMIT 1 ")
    AppVersionControlLogPo findTheCurrentRelease(String appSource, String operatingSystem);

    @Modifying
    @Query(nativeQuery = true, value = "delete FROM app_version_control_log WHERE APP_SOURCE=?1 AND OPERATING_SYSTEM=?2 AND VERSION_NAME=?3")
    void delete(String appSource, String operatingSystem, String versionName);

    @Query(nativeQuery = true, value = "SELECT * FROM app_version_control_log WHERE APP_SOURCE=?1 AND OPERATING_SYSTEM=?2 AND APP_LOG_ID > ?3 AND APP_FORCE_UPDATE = 1")
    List<AppVersionControlLogPo> findLaterForceUpdateApp(String appSource, String operatingSystem, Long appLogId);

}
