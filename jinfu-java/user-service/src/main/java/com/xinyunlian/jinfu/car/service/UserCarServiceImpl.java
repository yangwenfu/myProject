package com.xinyunlian.jinfu.car.service;

import com.xinyunlian.jinfu.car.dao.UserCarDao;
import com.xinyunlian.jinfu.car.dto.UserCarDto;
import com.xinyunlian.jinfu.car.entity.UserCarPo;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户车辆信息ServiceImpl
 * @author jll
 * @version 
 */

@Service
public class UserCarServiceImpl implements UserCarService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserCarServiceImpl.class);

	@Autowired
	private UserCarDao userCarDao;

	@Transactional
	@Override
	public UserCarDto save(UserCarDto userCarDto) {
		if (null != userCarDto) {
			UserCarPo po = new UserCarPo();
			if(null != userCarDto.getId()){
				po = userCarDao.findOne(userCarDto.getId());
			}
			ConverterService.convert(userCarDto, po);
			userCarDao.save(po);
			userCarDto.setId(po.getId());
		}
		return userCarDto;
	}

	@Override
	@Transactional
	public void delete(Long id) {
		if (null != id) {
			userCarDao.delete(id);
		}
	}

	@Override
	public UserCarDto get(Long id) {
		UserCarPo po = userCarDao.findOne(id);
		UserCarDto dto = ConverterService.convert(po, UserCarDto.class);
		return dto;
	}

	@Override
	public List<UserCarDto> list(String userId){
		List<UserCarDto> userCarDtos = new ArrayList<>();
		List<UserCarPo> userCarPos = userCarDao.findByUserId(userId);
		if(!CollectionUtils.isEmpty(userCarPos)){
			userCarPos.forEach(userCarPo -> {
				UserCarDto userCarDto = ConverterService.convert(userCarPo,UserCarDto.class);
				userCarDtos.add(userCarDto);
			});
		}
		return userCarDtos;
	}
	
}
