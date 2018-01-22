package com.xinyunlian.jinfu.promo.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.util.BizUtil;
import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.promo.dao.PromoInfDao;
import com.xinyunlian.jinfu.promo.dto.PromoInfDto;
import com.xinyunlian.jinfu.promo.dto.PromoInfSearchDto;
import com.xinyunlian.jinfu.promo.entity.PromoInfPo;
import com.xinyunlian.jinfu.promo.enums.EPlatform;
import com.xinyunlian.jinfu.promo.enums.EPromoInfStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 促销活动信息ServiceImpl
 * @author jll
 * @version 
 */

@Service
public class PromoInfServiceImpl implements PromoInfService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PromoInfServiceImpl.class);

	@Autowired
	private PromoInfDao promoInfDao;

	@Override
	public Long savePromotion(PromoInfDto promotion){
		PromoInfPo po = new PromoInfPo();
		if(promotion.getPromoId() != null) {
			po = promoInfDao.findOne(promotion.getPromoId());
		}
		ConverterService.convert(promotion, po);
		StringBuilder platforms = new StringBuilder();
		for(EPlatform platform : promotion.getPlatform()){
			platforms.append(platform.getCode());
			platforms.append(",");
		}
		po.setPlatform(platforms.toString());
//        if(null == po.getPromoId()){
            po.setStatus(EPromoInfStatus.INACTIVE);
//        }
		promoInfDao.save(po);
        return po.getPromoId();
	}

	@Override
	public PromoInfDto getByPromoId(Long promoId){
		PromoInfPo po = promoInfDao.findOne(promoId);
		PromoInfDto dto = ConverterService.convert(po, PromoInfDto.class);
		String platforms = po.getPlatform();
		List<EPlatform> platformList = new ArrayList<EPlatform>();
		String[] platformCodes = platforms.split(",");
		for(String platformCode : platformCodes){
			EPlatform platform = EnumHelper.translate(EPlatform.class, platformCode);
			if(platform != null){
				platformList.add(platform);
			}
		}
		dto.setPlatform(platformList);
		return dto;
	}

	@Override
	public PromoInfSearchDto findPromoInf(PromoInfSearchDto searchDto){
		Specification<PromoInfPo> specification = (root, query, cb) -> {
			Predicate predicate = cb.conjunction();

			List<Expression<Boolean>> expressions = predicate.getExpressions();
			if(null != searchDto){

				expressions.add(cb.notEqual(root.get("status"), EPromoInfStatus.DELETED));

				if(!StringUtils.isEmpty(searchDto.getName())){
					expressions.add(cb.like(root.get("name"), BizUtil.filterString(searchDto.getName())));
				}

				if(null != searchDto.getPlatform()){
					expressions.add(cb.like(root.get("platform"), BizUtil.filterString(searchDto.getPlatform().getCode())));
				}

				if(!StringUtils.isEmpty(searchDto.getProdId())){
					expressions.add(cb.equal(root.get("prodId"), searchDto.getProdId()));
				}

				if(null != searchDto.getStatus()){
					expressions.add(cb.equal(root.get("status"), searchDto.getStatus()));
				}

				if(null != searchDto.getType()){
					expressions.add(cb.equal(root.get("type"), searchDto.getType()));
				}
			}

			return predicate;
		};

		Pageable pageable = new PageRequest(searchDto.getCurrentPage() - 1, searchDto.getPageSize(), Sort.Direction.DESC, "promoId");
		Page<PromoInfPo> page = promoInfDao.findAll(specification, pageable);

		List<PromoInfPo> data = page.getContent();
		List<PromoInfDto> promotions = new ArrayList<>(data.size());
		data.forEach(promoInfPo ->  {
			PromoInfDto promotion = ConverterService.convert(promoInfPo, PromoInfDto.class);
			String platforms = promoInfPo.getPlatform();
			List<EPlatform> platformList = new ArrayList<EPlatform>();
			String[] platformCodes = platforms.split(",");
			for(String platformCode : platformCodes){
				EPlatform platform = EnumHelper.translate(EPlatform.class, platformCode);
				if(platform != null){
					platformList.add(platform);
				}
			}
			promotion.setPlatform(platformList);
			promotions.add(promotion);
		});

		searchDto.setList(promotions);
		searchDto.setTotalPages(page.getTotalPages());
		searchDto.setTotalRecord(page.getTotalElements());
		return searchDto;
	}

	@Override
	public void setPromotionStatus(Long promotionId, EPromoInfStatus status){
		PromoInfPo promoInfPo = promoInfDao.findOne(promotionId);
		promoInfPo.setStatus(status);
		promoInfDao.save(promoInfPo);
	}

	private List<PromoInfPo> getPendingFinishedList(Date nowDate, int currentPage, int size){
		Specification specification = (root, query, cb) -> {
			Predicate predicate = cb.conjunction();
			List<Expression<Boolean>> expressions = predicate.getExpressions();

			expressions.add(cb.equal(root.<PromoInfPo>get("status"), EPromoInfStatus.ACTIVE));
			expressions.add(cb.lessThanOrEqualTo(root.<PromoInfPo>get("endDate"), nowDate));

			return predicate;
		};
		Pageable pageable = new PageRequest(currentPage - 1, size);
		Page<PromoInfPo> page = promoInfDao.findAll(specification, pageable);

		return page.getContent();
	}

	@Override
	public void finishJob() {
		int currentPage = 1;
		int size = 1000;

		List<PromoInfPo> promotions = null;

		promotions = this.getPendingFinishedList(new Date(), currentPage, size);

        //直接修改状态，不走状态机
		promotions.forEach(promoInfPo -> {
			promoInfPo.setStatus(EPromoInfStatus.FINISHED);
		});
		promoInfDao.save(promotions);
	}
}
