package com.xinyunlian.jinfu.app.service;

import com.xinyunlian.jinfu.app.dto.DataVersionControlDto;
import com.xinyunlian.jinfu.app.enums.EAppSource;
import com.xinyunlian.jinfu.app.enums.EDataType;
import com.xinyunlian.jinfu.app.enums.EOperatingSystem;

/**
 * Created by menglei on 2016-12-14.
 */
public interface DataVersionControlService {

    /**
     * 获取最新的data版本信息
     * @param appSource
     * @param operatingSystem
     * @return
     */
    DataVersionControlDto getLatestDataInfo(EAppSource appSource, EOperatingSystem operatingSystem, EDataType dataType);

    void updateDataPath(String dataPath, EDataType dataType);

}
