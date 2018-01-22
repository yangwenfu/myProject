package com.xinyunlian.jinfu.rule.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.promo.dto.PromoRuleDto;
import com.xinyunlian.jinfu.promo.enums.EProperty;
import com.xinyunlian.jinfu.rule.dao.RuleFullOffDao;
import com.xinyunlian.jinfu.rule.dao.RuleFullOffGradDao;
import com.xinyunlian.jinfu.rule.dto.RuleFullOffDto;
import com.xinyunlian.jinfu.rule.dto.RuleFullOffGradDto;
import com.xinyunlian.jinfu.rule.entity.RuleFullOffGradPo;
import com.xinyunlian.jinfu.rule.entity.RuleFullOffPo;
import com.xinyunlian.jinfu.rule.enums.EOffType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 满减ServiceImpl
 * @author jll
 * @version 
 */

@Service
public class RuleFullOffServiceImpl implements RuleFullOffService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RuleFullOffServiceImpl.class);

	@Autowired
	private RuleFullOffDao ruleFullOffDao;
	@Autowired
	private RuleFullOffGradDao ruleFullOffGradDao;


	@Override
    @Transactional
	public void save(RuleFullOffDto ruleFullOffDto) throws BizServiceException {
		if(ruleFullOffDto.getRuleFullOffGradDtos() == null
				|| ruleFullOffDto.getRuleFullOffGradDtos().size() == 0){
			throw new BizServiceException(EErrorCode.PROMO_FULL_OFF_GRAD_IS_NULL,"满减梯度不可为空");
		}

        RuleFullOffPo ruleFullOffPo = new RuleFullOffPo();

		if(ruleFullOffDto.getOffId() != null) {
            ruleFullOffPo = ruleFullOffDao.findOne(ruleFullOffDto.getOffId());

		}

        ConverterService.convert(ruleFullOffDto,ruleFullOffPo);
		ruleFullOffDao.save(ruleFullOffPo);

		List<RuleFullOffGradPo> ruleFullOffGradPos = new ArrayList<>();
        for (RuleFullOffGradDto ruleFullOffGradDto : ruleFullOffDto.getRuleFullOffGradDtos()) {
            RuleFullOffGradPo ruleFullOffGradPo = ConverterService.convert(ruleFullOffGradDto, RuleFullOffGradPo.class);
            ruleFullOffGradPo.setOffId(ruleFullOffPo.getOffId());
            ruleFullOffGradPos.add(ruleFullOffGradPo);
        }
        if(ruleFullOffGradPos.get(0).getOffId() != null){
			ruleFullOffGradDao.deleteByOffId(ruleFullOffGradPos.get(0).getOffId());
		}
		ruleFullOffGradDao.save(ruleFullOffGradPos);

	}

	@Override
	public RuleFullOffDto findByPromoId(Long promoId) {
		RuleFullOffPo ruleFullOffPo = ruleFullOffDao.findByPromoId(promoId);
		if(ruleFullOffPo == null){
			return null;
		}
		RuleFullOffDto ruleFullOffDto = ConverterService.convert(ruleFullOffPo,RuleFullOffDto.class);
		List<RuleFullOffGradPo> ruleFullOffGradPos = ruleFullOffGradDao.findByOffId(ruleFullOffPo.getOffId());
		if(ruleFullOffGradPos != null ){
			List<RuleFullOffGradDto> ruleFullOffGradDtos = new ArrayList<>();
			ruleFullOffGradPos.forEach(ruleFullOffGradPo -> {
				RuleFullOffGradDto ruleFullOffGradDto = ConverterService.convert(ruleFullOffGradPo,RuleFullOffGradDto.class);
				ruleFullOffGradDtos.add(ruleFullOffGradDto);
			});
			ruleFullOffDto.setRuleFullOffGradDtos(ruleFullOffGradDtos);
		}
		return ruleFullOffDto;
	}

	@Override
	public PromoRuleDto getRuleDto(Long promoId, BigDecimal amount) {
        RuleFullOffDto ruleFullOffDto = findByPromoId(promoId);
        PromoRuleDto ruleDto = new PromoRuleDto();
        ruleDto.setPromoId(promoId);
        ruleDto.setProperty(EProperty.DISCOUNT);
        ruleDto.setOffType(ruleFullOffDto.getOffType());
        ruleDto.setTerm(ruleFullOffDto.getTerm());

        if(ruleFullOffDto.getOffType() == EOffType.MONEY){//按金额
			if(ruleFullOffDto.getRuleFullOffGradDtos().size() == 1){
				//无梯度
				RuleFullOffGradDto ruleFullOffGradDto = ruleFullOffDto.getRuleFullOffGradDtos().get(0);
				if (ruleFullOffDto.getCap() == true) {
					//累计
					Double discount = accumulateDiscount(ruleFullOffGradDto.getAmount().doubleValue(),
									ruleFullOffGradDto.getDiscount().doubleValue(),amount.doubleValue());
					ruleDto.setDiscount(new BigDecimal(discount));
				}else{
					ruleDto.setDiscount(ruleFullOffGradDto.getDiscount());
				}
			}else{
				//有梯度
				ruleFullOffDto.getRuleFullOffGradDtos().stream().filter(ruleFullOffGradDto -> amount.compareTo(ruleFullOffGradDto.getAmount()) >= 0).forEach(ruleFullOffGradDto -> {
					ruleDto.setDiscount(ruleFullOffGradDto.getDiscount());
				});
			}
        }else if(ruleFullOffDto.getOffType() == EOffType.RATE){//按利率
			//整期按利率首期按金额折扣
			ruleFullOffDto.getRuleFullOffGradDtos().stream().filter(ruleFullOffGradDto -> amount.compareTo(ruleFullOffGradDto.getAmount()) >= 0).forEach(ruleFullOffGradDto -> {
				ruleDto.setDiscount(ruleFullOffGradDto.getDiscount());
			});
		}
		return ruleDto;
	}

	private double accumulateDiscount(double moneyCondition,double discount, double money)
	{
		double result = 0;
		if (money >= moneyCondition)
		{
			result = ((int)(money / moneyCondition)) * discount;
		}
		return result;
	}

	public static void main(String[] args) {
		//System.out.println(String.valueOf(accumulateDiscount(300,35.23,100)));
	}
}
