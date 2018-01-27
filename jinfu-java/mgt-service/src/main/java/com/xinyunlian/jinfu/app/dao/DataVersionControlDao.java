package com.xinyunlian.jinfu.app.dao;

import com.xinyunlian.jinfu.app.entity.DataVersionControlPo;
import com.xinyunlian.jinfu.app.enums.EDataType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by menglei on 2016-12-14.
 */
public interface DataVersionControlDao extends JpaRepository<DataVersionControlPo, Long>, JpaSpecificationExecutor<DataVersionControlPo> {

    @Query(nativeQuery = true, value = "select ID, VERSION_NAME, VERSION_CODE, DATA_FORCE_UPDATE, DATA_TYPE, DATA_PATH, APP_SOURCE, OPERATING_SYSTEM, CREATE_OPID, CREATE_TS, " +
            "LAST_MNT_OPID, LAST_MNT_TS, VERSION_CT from data_version_control WHERE APP_SOURCE = ?1 AND OPERATING_SYSTEM = ?2 AND DATA_TYPE = ?3")
    DataVersionControlPo findLatestDataInfo(String appSource, String operatingSystem, String dataType);

    List<DataVersionControlPo> findByDataType(EDataType dataType);

}
