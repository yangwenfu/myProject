package com.xinyunlian.jinfu.share.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.mq.DestinationDefine;
import com.xinyunlian.jinfu.promo.service.UserCouponService;
import com.xinyunlian.jinfu.share.dao.RecommendDao;
import com.xinyunlian.jinfu.share.dto.RecommendDto;
import com.xinyunlian.jinfu.share.entity.RecommendPo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

/**
 * 推荐ServiceImpl
 *
 * @author jll
 */

@Service
public class RecommendServiceImpl implements RecommendService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecommendServiceImpl.class);

    @Autowired
    private RecommendDao recommendDao;
    @Autowired
    private UserCouponService userCouponService;
    @Value("${reg.coupon.loan01}")
    private String REG_COUPON_LOAN01;
    @Value("${reg.coupon.loan02}")
    private String REG_COUPON_LOAN02;
    @Value("${ref.coupon.loan01}")
    private String REF_COUPON_LOAN01;
    @Value("${ref.coupon.loan02}")
    private String REF_COUPON_LOAN02;

    @Transactional
    @Override
    public void add(RecommendDto recommendDto) throws BizServiceException {
        if (null != recommendDto) {
            RecommendPo po = ConverterService.convert(recommendDto, RecommendPo.class);
            recommendDao.save(po);

            //给注册的人发优惠劵
            userCouponService.activeCoupon(recommendDto.getUserId(),REG_COUPON_LOAN01,"loan,");
            userCouponService.activeCoupon(recommendDto.getUserId(),REG_COUPON_LOAN02,"loan,");
        }
    }


    @JmsListener(destination = DestinationDefine.LOAN_APPLY_PROMO_COUPON)
    public void giveRefereeCoupon(String value) {
        String userId = null;
        try {
            userId = JsonUtil.toMap(value).get("userId");
            RecommendPo recommendPo = recommendDao.findByUserId(userId);
            if(recommendPo.getOrder()){
                return;
            }
            //给推荐人发优惠劵
            userCouponService.activeCoupon(recommendPo.getRefereeUserId(),REF_COUPON_LOAN01,"loan,");
            userCouponService.activeCoupon(recommendPo.getRefereeUserId(),REF_COUPON_LOAN02,"loan,");

            recommendPo.setOrder(true);
            recommendDao.save(recommendPo);
        } catch (IOException e) {
            LOGGER.error(e.getLocalizedMessage());
        }

    }

}
