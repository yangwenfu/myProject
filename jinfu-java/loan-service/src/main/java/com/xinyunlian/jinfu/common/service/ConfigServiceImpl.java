package com.xinyunlian.jinfu.common.service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dao.ConfigDao;
import com.xinyunlian.jinfu.common.dto.ConfigDto;
import com.xinyunlian.jinfu.common.entity.ConfigPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConfigServiceImpl implements ConfigService{

	@Autowired
	private ConfigDao configDao;

	@Override
	@Transactional
	public void update(ConfigDto configDto){
		ConfigPo configPo = configDao.findOne(configDto.getCfgKey());

		if(null == configPo){
			configPo = new ConfigPo();
			configPo.setCfgKey(configDto.getCfgKey());
		}

		configPo.setCfgValue(configDto.getCfgValue());
		if(!StringUtils.isBlank(configDto.getMemo())){
			configPo.setMemo(configDto.getMemo());
		}

		configDao.save(configPo);
	}

	@Override
	@Transactional
	public ConfigDto get(String key) {
		ConfigPo configPo = configDao.findOne(key);
		return ConverterService.convert(configPo, ConfigDto.class);
	}
}
