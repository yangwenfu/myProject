package com.xinyunlian.jinfu.app.dao;

import com.xinyunlian.jinfu.app.entity.AppVersionControlPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by DongFC on 2016-10-08.
 */
public interface AppVersionControlDao extends JpaRepository<AppVersionControlPo, Long>, JpaSpecificationExecutor<AppVersionControlPo> {

    @Query(nativeQuery = true, value = "select * from app_version_control WHERE APP_SOURCE = ?1 AND OPERATING_SYSTEM = ?2")
    AppVersionControlPo findByAppSourceAndOperatingSystem(String appSource, String operatingSystem);

}
