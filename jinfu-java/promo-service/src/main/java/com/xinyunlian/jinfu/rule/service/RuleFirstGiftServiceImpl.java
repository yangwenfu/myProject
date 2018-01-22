package com.xinyunlian.jinfu.rule.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.rule.dto.RuleFirstGiftDto;
import com.xinyunlian.jinfu.rule.entity.RuleFirstGiftPo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinyunlian.jinfu.rule.dao.RuleFirstGiftDao;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 首单有礼ServiceImpl
 * @author jll
 * @version 
 */

@Service
public class RuleFirstGiftServiceImpl implements RuleFirstGiftService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RuleFirstGiftServiceImpl.class);

	@Autowired
	private RuleFirstGiftDao ruleFirstGiftDao;

	@Override
	@Transactional
	public void save(List<RuleFirstGiftDto> ruleFirstGiftDtos){
		List<RuleFirstGiftPo> ruleFirstGiftPos = new ArrayList<>();
		if(ruleFirstGiftDtos != null && ruleFirstGiftDtos.size()>0){
			ruleFirstGiftDtos.forEach(ruleFirstGiftDto -> {
				RuleFirstGiftPo ruleFirstGiftPo = ConverterService.convert(ruleFirstGiftDto,RuleFirstGiftPo.class);
				ruleFirstGiftPos.add(ruleFirstGiftPo);
			});
			if(ruleFirstGiftDtos.get(0).getPromoId() != null){
				ruleFirstGiftDao.deleteByPromoId(ruleFirstGiftDtos.get(0).getPromoId());
			}
			ruleFirstGiftDao.save(ruleFirstGiftPos);
		}
	}

	@Override
	public List<RuleFirstGiftDto> findByPromoId(Long promoId){
		List<RuleFirstGiftPo> ruleFirstGiftPos = ruleFirstGiftDao.findByPromoId(promoId);
		if(ruleFirstGiftPos == null){
			return null;
		}
		List<RuleFirstGiftDto> ruleFirstGiftDtos = new ArrayList<>();
		ruleFirstGiftPos.forEach(ruleFirstGiftPo -> {
			RuleFirstGiftDto ruleFirstGiftDto = ConverterService.convert(ruleFirstGiftPo,RuleFirstGiftDto.class);
			ruleFirstGiftDtos.add(ruleFirstGiftDto);
		});
		return ruleFirstGiftDtos;
	}

	@Override
	public void deleteByPromoId(Long promoId) {
		ruleFirstGiftDao.deleteByPromoId(promoId);
	}
}
