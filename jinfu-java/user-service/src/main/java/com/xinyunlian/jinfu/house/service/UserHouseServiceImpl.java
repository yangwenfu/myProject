package com.xinyunlian.jinfu.house.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.house.dao.UserHouseDao;
import com.xinyunlian.jinfu.house.dto.UserHouseDto;
import com.xinyunlian.jinfu.house.entity.UserHousePo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户房产信息ServiceImpl
 * @author jll
 * @version 
 */

@Service
public class UserHouseServiceImpl implements UserHouseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserHouseServiceImpl.class);

	@Autowired
	private UserHouseDao userHouseDao;

	@Transactional
	@Override
	public UserHouseDto save(UserHouseDto userHouseDto) {
		if (null != userHouseDto) {
			UserHousePo po = new UserHousePo();
			if(null != userHouseDto.getId()){
				po = userHouseDao.findOne(userHouseDto.getId());
			}
			ConverterService.convert(userHouseDto, po);
			userHouseDao.save(po);
			userHouseDto.setId(po.getId());
		}
		return userHouseDto;
	}

	@Override
	@Transactional
	public void delete(Long id) {
		if (null != id) {
			userHouseDao.delete(id);
		}
	}

	@Override
	public UserHouseDto get(Long id) {
		UserHousePo po = userHouseDao.findOne(id);
		UserHouseDto dto = ConverterService.convert(po, UserHouseDto.class);
		return dto;
	}

	@Override
	public List<UserHouseDto> list(String userId){
		List<UserHouseDto> userHouseDtos = new ArrayList<>();
		List<UserHousePo> userHousePos = userHouseDao.findByUserId(userId);
		if(!CollectionUtils.isEmpty(userHousePos)){
			userHousePos.forEach(userHousePo -> {
				UserHouseDto userHouseDto = ConverterService.convert(userHousePo,UserHouseDto.class);
				userHouseDtos.add(userHouseDto);
			});
		}
		return userHouseDtos;
	}
	
}
