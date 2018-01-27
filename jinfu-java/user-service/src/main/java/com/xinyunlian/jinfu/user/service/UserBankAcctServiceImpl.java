package com.xinyunlian.jinfu.user.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.user.dao.UserBankAcctDao;
import com.xinyunlian.jinfu.user.dto.UserBankAcctDto;
import com.xinyunlian.jinfu.user.entity.UserBankAcctPo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 银行流水账户ServiceImpl
 * @author jll
 * @version 
 */

@Service
public class UserBankAcctServiceImpl implements UserBankAcctService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserBankAcctServiceImpl.class);

	@Autowired
	private UserBankAcctDao userBankAcctDao;

	@Transactional
	@Override
	public UserBankAcctDto save(UserBankAcctDto userBankAcctDto) {
		if (null != userBankAcctDto) {
			UserBankAcctPo po = new UserBankAcctPo();
			if(null != userBankAcctDto.getBankAccountId()){
				po = userBankAcctDao.findOne(userBankAcctDto.getBankAccountId());
			}
			ConverterService.convert(userBankAcctDto, po);
			userBankAcctDao.save(po);
			userBankAcctDto.setBankAccountId(po.getBankAccountId());
		}
		return userBankAcctDto;
	}

	@Override
	@Transactional
	public void delete(Long id) {
		if (null != id) {
			userBankAcctDao.delete(id);
		}
	}

	@Override
	public UserBankAcctDto get(Long id) {
		UserBankAcctPo po = userBankAcctDao.findOne(id);
		UserBankAcctDto dto = ConverterService.convert(po, UserBankAcctDto.class);
		return dto;
	}

	@Override
	public List<UserBankAcctDto> list(String userId){
		List<UserBankAcctDto> userBankAcctDtos = new ArrayList<>();
		List<UserBankAcctPo> userBankAcctPos = userBankAcctDao.findByUserId(userId);
		if(!CollectionUtils.isEmpty(userBankAcctPos)){
			userBankAcctPos.forEach(userBankAcctPo -> {
				UserBankAcctDto userBankAcctDto = ConverterService.convert(userBankAcctPo,UserBankAcctDto.class);
				userBankAcctDtos.add(userBankAcctDto);
			});
		}
		return userBankAcctDtos;
	}
}
