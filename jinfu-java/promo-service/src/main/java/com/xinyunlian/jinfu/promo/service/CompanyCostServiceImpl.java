package com.xinyunlian.jinfu.promo.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.promo.dao.CompanyCostDao;
import com.xinyunlian.jinfu.promo.dto.CompanyCostDto;
import com.xinyunlian.jinfu.promo.entity.CompanyCostPo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 主体信息ServiceImpl
 * @author jll
 * @version 
 */

@Service
public class CompanyCostServiceImpl implements CompanyCostService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyCostServiceImpl.class);

	@Autowired
	private CompanyCostDao companyCostDao;

	@Override
	public void saveCostsPlan(List<CompanyCostDto> costsPlan){
		List<CompanyCostPo> pos = new ArrayList<>();
		costsPlan.forEach(companyCostDto -> {
			CompanyCostPo po = ConverterService.convert(companyCostDto, CompanyCostPo.class);
			pos.add(po);
		});

		if(costsPlan.get(0).getPromoId() != null){
			companyCostDao.deleteByPromoId(costsPlan.get(0).getPromoId());
		}
		companyCostDao.save(pos);
	}

	@Override
	public List<CompanyCostDto> getCostsPlanByPromoId(Long promoId){
		List<CompanyCostPo> pos = companyCostDao.findByPromoId(promoId);
		List<CompanyCostDto> costsPlan = new ArrayList<>(pos.size());
		pos.forEach(companyCostPo -> {
			CompanyCostDto dto = ConverterService.convert(companyCostPo, CompanyCostDto.class);
			costsPlan.add(dto);
		});
		return costsPlan;
	}
}
