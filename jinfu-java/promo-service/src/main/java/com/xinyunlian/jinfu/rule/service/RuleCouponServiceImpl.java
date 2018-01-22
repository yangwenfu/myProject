package com.xinyunlian.jinfu.rule.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.rule.dao.RuleCouponDao;
import com.xinyunlian.jinfu.rule.dto.RuleCouponDto;
import com.xinyunlian.jinfu.rule.entity.RuleCouponPo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 优惠券ServiceImpl
 * @author jll
 * @version 
 */

@Service
public class RuleCouponServiceImpl implements RuleCouponService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RuleCouponServiceImpl.class);

	@Autowired
	private RuleCouponDao ruleCouponDao;

    @Transactional
    @Override
    public void save(RuleCouponDto ruleCouponDto) {
        if(!StringUtils.isEmpty(ruleCouponDto.getCouponCode())) {
            RuleCouponPo couponPo = ruleCouponDao.findActiveByCouponCode(ruleCouponDto.getCouponCode());
            if(couponPo != null){
                if(null == ruleCouponDto.getId() || ruleCouponDto.getId() != couponPo.getId()) {
                    throw new BizServiceException(EErrorCode.PROMO_COUPON_CODE_IS_EXISTS, "优惠券活动编号已存在");
                }
            }
        }

        RuleCouponPo ruleCouponPo = new RuleCouponPo();
        if (null != ruleCouponDto.getId()){
            ruleCouponPo = ruleCouponDao.findOne(ruleCouponDto.getId());
        }
        ConverterService.convert(ruleCouponDto, ruleCouponPo);
        ruleCouponDao.save(ruleCouponPo);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (null != id){
            ruleCouponDao.delete(id);
        }
    }

    @Override
    @Transactional
    public void deleteByPromoId(Long promoId) {
        ruleCouponDao.deleteByPromoId(promoId);
    }

    @Override
    public RuleCouponDto findByPromoId(Long promoId) {
        RuleCouponPo ruleCouponPo = ruleCouponDao.findByPromoId(promoId);
        if(ruleCouponPo == null){
            return null;
        }
        RuleCouponDto ruleCouponDto = ConverterService.convert(ruleCouponPo,RuleCouponDto.class);
        return ruleCouponDto;
    }
	
}
