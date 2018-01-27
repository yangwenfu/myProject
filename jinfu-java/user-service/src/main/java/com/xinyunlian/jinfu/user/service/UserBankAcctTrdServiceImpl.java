package com.xinyunlian.jinfu.user.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.user.dao.UserBankAcctTrdDao;
import com.xinyunlian.jinfu.user.dto.UserBankAcctTrdDto;
import com.xinyunlian.jinfu.user.entity.UserBankAcctTrdPo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 银行流水ServiceImpl
 * @author jll
 * @version 
 */

@Service
public class UserBankAcctTrdServiceImpl implements UserBankAcctTrdService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserBankAcctTrdServiceImpl.class);

	@Autowired
	private UserBankAcctTrdDao userBankAcctTrdDao;

	@Transactional
	@Override
	public UserBankAcctTrdDto save(UserBankAcctTrdDto userBankAcctTrdDto) {
		if (null != userBankAcctTrdDto) {
			UserBankAcctTrdPo po = new UserBankAcctTrdPo();
			if(null != userBankAcctTrdDto.getId()){
				po = userBankAcctTrdDao.findOne(userBankAcctTrdDto.getId());
			}
			ConverterService.convert(userBankAcctTrdDto, po);
			userBankAcctTrdDao.save(po);
			userBankAcctTrdDto.setBankAccountId(po.getBankAccountId());
		}
		return userBankAcctTrdDto;
	}

	@Override
	@Transactional
	public void delete(Long id) {
		if (null != id) {
			userBankAcctTrdDao.delete(id);
		}
	}

	@Override
	public UserBankAcctTrdDto get(Long id) {
		UserBankAcctTrdPo po = userBankAcctTrdDao.findOne(id);
		UserBankAcctTrdDto dto = ConverterService.convert(po, UserBankAcctTrdDto.class);
		return dto;
	}

	@Override
	public List<UserBankAcctTrdDto> list(Long bankAccountId){
		List<UserBankAcctTrdDto> dtos = new ArrayList<>();
		List<UserBankAcctTrdPo> pos = userBankAcctTrdDao.findByBankAccountId(bankAccountId);
		if(!CollectionUtils.isEmpty(pos)){
			pos.forEach(po -> {
				UserBankAcctTrdDto dto = ConverterService.convert(po,UserBankAcctTrdDto.class);
				dtos.add(dto);
			});
		}
		return dtos;
	}
	
}
