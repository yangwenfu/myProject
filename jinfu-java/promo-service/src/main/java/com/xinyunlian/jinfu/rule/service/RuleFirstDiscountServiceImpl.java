package com.xinyunlian.jinfu.rule.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.promo.dto.PromoRuleDto;
import com.xinyunlian.jinfu.promo.enums.EProperty;
import com.xinyunlian.jinfu.rule.dao.RuleFirstDiscountDao;
import com.xinyunlian.jinfu.rule.dto.RuleFirstDiscountDto;
import com.xinyunlian.jinfu.rule.entity.RuleFirstDiscountPo;
import com.xinyunlian.jinfu.rule.enums.EOffType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 首单折扣ServiceImpl
 * @author jll
 * @version 
 */

@Service
public class RuleFirstDiscountServiceImpl implements RuleFirstDiscountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RuleFirstDiscountServiceImpl.class);

	@Autowired
	private RuleFirstDiscountDao ruleFirstDiscountDao;

	@Override
	public RuleFirstDiscountDto findByPromoId(Long promoId) {
		RuleFirstDiscountPo ruleFirstDiscountPo = ruleFirstDiscountDao.findByPromoId(promoId);
		if(ruleFirstDiscountPo == null){
			return null;
		}
		RuleFirstDiscountDto ruleFirstDiscountDto = ConverterService.convert(ruleFirstDiscountPo,RuleFirstDiscountDto.class);
		return ruleFirstDiscountDto;
	}

	@Override
	public void save(RuleFirstDiscountDto ruleFirstDiscountDto){
		RuleFirstDiscountPo ruleFirstDiscountPo = new RuleFirstDiscountPo();
		if(ruleFirstDiscountDto.getId() != null){
			ruleFirstDiscountPo = ruleFirstDiscountDao.findOne(ruleFirstDiscountDto.getId());
		}
		ConverterService.convert(ruleFirstDiscountDto,ruleFirstDiscountPo);
		ruleFirstDiscountDao.save(ruleFirstDiscountPo);
	}

	@Override
	public PromoRuleDto getRuleDto(Long promoId) {
		RuleFirstDiscountPo ruleFirstDiscountPo = ruleFirstDiscountDao.findByPromoId(promoId);
		PromoRuleDto ruleDto = new PromoRuleDto();
		ruleDto.setPromoId(promoId);
		ruleDto.setProperty(EProperty.DISCOUNT);
		ruleDto.setOffType(EOffType.RATE);
		ruleDto.setDiscount(ruleFirstDiscountPo.getDiscount());
		ruleDto.setTerm(ruleFirstDiscountPo.getTerm());
		return ruleDto;
	}

	@Override
	public void deleteByPromoId(Long promoId) {
		ruleFirstDiscountDao.deleteByPromoId(promoId);
	}
}
