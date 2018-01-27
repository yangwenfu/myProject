package com.xinyunlian.jinfu.app.service;

import com.xinyunlian.jinfu.app.dao.DataVersionControlDao;
import com.xinyunlian.jinfu.app.dto.DataVersionControlDto;
import com.xinyunlian.jinfu.app.entity.DataVersionControlPo;
import com.xinyunlian.jinfu.app.enums.EAppSource;
import com.xinyunlian.jinfu.app.enums.EDataType;
import com.xinyunlian.jinfu.app.enums.EOperatingSystem;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.security.StaticResourceSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by DongFC on 2016-10-08.
 */
@Service
public class DataVersionControlServiceImpl implements DataVersionControlService {

    @Autowired
    private DataVersionControlDao dataVersionControlDao;

    @Value("${file.addr}")
    private String fileAddr;

    @Override
    public DataVersionControlDto getLatestDataInfo(EAppSource appSource, EOperatingSystem operatingSystem, EDataType dataType) {

        DataVersionControlPo po = dataVersionControlDao.findLatestDataInfo(appSource.getCode(), operatingSystem.getCode(), dataType.getCode());
        DataVersionControlDto dto = ConverterService.convert(po, DataVersionControlDto.class);

        if (dto != null && !StringUtils.isEmpty(dto.getDataPath())){
            if (!dto.getDataPath().startsWith("http")){
                dto.setDataPath(fileAddr + StaticResourceSecurity.getSecurityURI(dto.getDataPath()));
            }
        }

        return dto;
    }

    @Transactional
    @Override
    public void updateDataPath(String dataPath, EDataType dataType) {
        List<DataVersionControlPo> list = dataVersionControlDao.findByDataType(dataType);
        for (DataVersionControlPo dataVersionControlPo : list) {
            dataVersionControlPo.setVersionCode(dataVersionControlPo.getVersionCode() + 1);
            dataVersionControlPo.setDataPath(dataPath);
            dataVersionControlDao.save(dataVersionControlPo);
        }
    }

}
