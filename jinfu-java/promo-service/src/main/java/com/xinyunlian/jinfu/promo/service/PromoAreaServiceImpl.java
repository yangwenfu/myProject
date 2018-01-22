package com.xinyunlian.jinfu.promo.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.promo.dao.PromoAreaDao;
import com.xinyunlian.jinfu.promo.dto.PromoAreaDto;
import com.xinyunlian.jinfu.promo.entity.PromoAreaPo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 促销地区ServiceImpl
 * @author jll
 * @version 
 */

@Service
public class PromoAreaServiceImpl implements PromoAreaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PromoAreaServiceImpl.class);

	@Autowired
	private PromoAreaDao promoAreaDao;

	@Override
	public void save(List<PromoAreaDto> promoAreaDtos){
		List<PromoAreaPo> pos = new ArrayList<>();
		promoAreaDtos.forEach(promoAreaDto -> {
			PromoAreaPo po = ConverterService.convert(promoAreaDto, PromoAreaPo.class);
			pos.add(po);
		});
		promoAreaDao.save(pos);
	}

	@Override
	public List<PromoAreaDto> getAreasByPromoId(Long promoId) {
		List<PromoAreaPo> pos = promoAreaDao.getByPromoId(promoId);
		List<PromoAreaDto> dtos = new ArrayList<>();
		pos.forEach(promoAreaPo -> {
			PromoAreaDto dto = ConverterService.convert(promoAreaPo, PromoAreaDto.class);
			dtos.add(dto);
		});
		return dtos;
	}


	@Override
	public void deleteByPromotionId(Long promoId) {
		promoAreaDao.deleteByPromoId(promoId);
	}
}
