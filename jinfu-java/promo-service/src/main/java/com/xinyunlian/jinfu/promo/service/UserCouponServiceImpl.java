package com.xinyunlian.jinfu.promo.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.entity.id.Context;
import com.xinyunlian.jinfu.common.entity.id.IdUtil;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.ApplicationContextUtil;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.mq.DestinationDefine;
import com.xinyunlian.jinfu.promo.dao.PromoInfDao;
import com.xinyunlian.jinfu.promo.dao.UserCouponDao;
import com.xinyunlian.jinfu.promo.dao.UserUsageDao;
import com.xinyunlian.jinfu.promo.dto.CouponGiftDto;
import com.xinyunlian.jinfu.promo.dto.StoreDto;
import com.xinyunlian.jinfu.promo.dto.UserCouponDto;
import com.xinyunlian.jinfu.promo.dto.UserDto;
import com.xinyunlian.jinfu.promo.entity.PromoInfPo;
import com.xinyunlian.jinfu.promo.entity.UserCouponPo;
import com.xinyunlian.jinfu.promo.entity.UserUsagePo;
import com.xinyunlian.jinfu.promo.enums.EPromoInfStatus;
import com.xinyunlian.jinfu.promo.enums.EUserCouponStatus;
import com.xinyunlian.jinfu.promo.seqProducer.CouponCodeSeqProducer;
import com.xinyunlian.jinfu.rule.dao.RuleCouponDao;
import com.xinyunlian.jinfu.rule.entity.RuleCouponPo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 个人优惠券ServiceImpl
 *
 * @author jll
 */

@Service
public class UserCouponServiceImpl implements UserCouponService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserCouponServiceImpl.class);

    @Autowired
    private UserCouponDao userCouponDao;
    @Autowired
    private RuleCouponDao ruleCouponDao;
    @Autowired
    private PromoInfDao promoInfDao;
    @Autowired
    private UserUsageDao userUsageDao;
    @Autowired
    private PromoApplyService promoApplyService;

    @Override
    public UserCouponDto getUserCoupon(Long couponId) {
        UserCouponPo userCouponPo = userCouponDao.findOne(couponId);
        if(userCouponPo == null){
            return null;
        }
        UserCouponDto userCouponDto = ConverterService.convert(userCouponPo,UserCouponDto.class);
        return userCouponDto;
    }

    @Override
    public Long countUserCoupon(String userId, EUserCouponStatus status){
        Long userCount = userCouponDao.countByUserIdAndStatus(userId,status);
        if(userCount == null){
            userCount = 0L;
        }
        return userCount;
    }

    @Transactional
    @Override
    public void activeCoupon(String userId, String couponCode,String platform) throws BizServiceException{
        RuleCouponPo ruleCouponPo = ruleCouponDao.findActiveByCouponCode(couponCode);
        if(null == ruleCouponPo){
            throw new BizServiceException(EErrorCode.PROMO_COUPON_CODE_NOT_EXISTS,"优惠券激活码不存在");
        }

        PromoInfPo promoInfPo = promoInfDao.findOne(ruleCouponPo.getPromoId());

        if(!promoInfPo.getPlatform().contains(platform)){
            throw new BizServiceException(EErrorCode.PROMO_COUPON_CODE_NOT_EXISTS,"优惠券激活码不存在");
        }

        if(promoInfPo.getStatus() != EPromoInfStatus.ACTIVE){
            throw new BizServiceException(EErrorCode.PROMO_USER_COUPON_INVALID,"活动还未生效");
        }

        Date now = new Date();
        if(now.compareTo(promoInfPo.getStartDate()) <= 0
                || now.compareTo(promoInfPo.getEndDate()) > 0){
            throw new BizServiceException(EErrorCode.PROMO_USER_COUPON_INVALID_DATE,"活动还未开始或已结束");
        }

        if(promoInfPo.getCurNum().compareTo(promoInfPo.getTotalLimit()) >= 0){
            throw new BizServiceException(EErrorCode.PROMO_USER_COUPON_LIMIT,"优惠券已领完");
        }

        Long userCount = userCouponDao.countByUserIdAndPromoId(userId,promoInfPo.getPromoId());
        if(userCount == null){
            userCount = 0L;
        }
        if(userCount.longValue() >= promoInfPo.getPerLimit()){
            throw new BizServiceException(EErrorCode.PROMO_USER_COUPON_PER_LIMIT,"个人优惠券领取超限");
        }

        UserCouponPo userCouponPo = new UserCouponPo();
        userCouponPo.setPromoId(ruleCouponPo.getPromoId());
        userCouponPo.setUserId(userId);
        userCouponPo.setAdded(ruleCouponPo.getAdded());
        userCouponPo.setCouponName(ruleCouponPo.getCouponName());
        userCouponPo.setCouponType(ruleCouponPo.getCouponType());
        userCouponPo.setPrice(ruleCouponPo.getPrice());
        userCouponPo.setStatus(EUserCouponStatus.UNUSED);

        userCouponPo.setProdTypeId(promoInfPo.getProdTypeId().intValue());
        userCouponPo.setProdId(promoInfPo.getProdId());
        userCouponPo.setMinimum(promoInfPo.getMinimum());
        userCouponPo.setValidBeginDate(ruleCouponPo.getValidBeginDate());
        userCouponPo.setValidEndDate(ruleCouponPo.getValidEndDate());
        if(null != ruleCouponPo.getValidDays()){
            userCouponPo.setValidBeginDate(DateHelper.getStartDate(new Date()));
            userCouponPo.setValidEndDate(DateHelper.getEndDate(DateHelper
                    .add(new Date(), Calendar.DATE, ruleCouponPo.getValidDays()-1)));
        }

        promoInfPo.setCurNum(promoInfPo.getCurNum() + 1);

        Context context = new Context("PROMO_COUPON_CODE");
        context.setParam(promoInfPo.getProdId());
        //产品编号 + 年-2015 +月日时分秒 + 自增三位
        String code = IdUtil.produce(ApplicationContextUtil.getBean(CouponCodeSeqProducer.class), context);
        userCouponPo.setCouponCode(code);

        userCouponDao.save(userCouponPo);

        LOGGER.info(""+userCouponPo.getValidEndDate());
        promoInfDao.save(promoInfPo);
    }

    @Override
    public List<UserCouponDto> findByUserId(String userId, EUserCouponStatus status){
        List<UserCouponDto> userCouponDtos = new ArrayList<>();

        List<UserCouponPo> userCouponPos = userCouponDao.findByUserIdAndStatus(userId,status);
        if(!CollectionUtils.isEmpty(userCouponPos)){
            userCouponPos.forEach(userCouponPo -> {
                UserCouponDto userCouponDto = ConverterService.convert(userCouponPo,UserCouponDto.class);
                userCouponDtos.add(userCouponDto);
            });
        }

        return userCouponDtos;
    }

    @Override
    public List<UserCouponDto> findByUserId(String userId){
        List<UserCouponDto> userCouponDtos = new ArrayList<>();

        List<UserCouponPo> userCouponPos = userCouponDao.findByUserId(userId);
        if(!CollectionUtils.isEmpty(userCouponPos)){
            userCouponPos.forEach(userCouponPo -> {
                UserCouponDto userCouponDto = ConverterService.convert(userCouponPo,UserCouponDto.class);
                userCouponDtos.add(userCouponDto);
            });
        }

        return userCouponDtos;
    }

    @Override
    public List<UserCouponDto> getUsable(UserDto user,List<StoreDto> stores,int prodTypeId){
        List<UserCouponDto> userCouponDtos = new ArrayList<>();

        Specification<UserCouponPo> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
                expressions.add(cb.equal(root.get("userId"), user.getUserId()));
                expressions.add(cb.equal(root.get("prodTypeId"), prodTypeId));
                expressions.add(cb.equal(root.get("status"), EUserCouponStatus.UNUSED));

            return predicate;
        };

        List<UserCouponPo> userCouponPos = userCouponDao.findAll(spec);
        if (!CollectionUtils.isEmpty(userCouponPos)){
            userCouponPos.forEach(userCouponPo -> {
                UserCouponDto userCouponDto = ConverterService.convert(userCouponPo,UserCouponDto.class);
                //在黑名单内
                if(promoApplyService.checkBlackList(userCouponPo.getPromoId(),user,stores) == 1){
                    userCouponDto.setOnBlack(true);
                }
                //不在白名单内
                if(promoApplyService.checkWhiteList(userCouponPo.getPromoId(),user,stores) == 0){
                    //不在可售地区
                    if(promoApplyService.checkArea(userCouponPo.getPromoId(),stores) == 0){
                        userCouponDto.setOnBlack(true);
                    }
                }
                userCouponDtos.add(userCouponDto);
            });
        }

        return userCouponDtos;
    }

    @Transactional
    @Override
    public void useCoupon(UserDto user, Long couponId) throws BizServiceException{
        UserCouponPo userCouponPo = userCouponDao.findOne(couponId);
        Date now = new Date();

        if(!StringUtils.equals(user.getUserId(),userCouponPo.getUserId())){
            throw new BizServiceException(EErrorCode.PROMO_USER_COUPON_ERROR,"无效优惠券");
        }

        if(userCouponPo.getStatus() == EUserCouponStatus.USED){
            throw new BizServiceException(EErrorCode.PROMO_USER_COUPON_USED,"优惠券已使用");
        }

        if(now.compareTo(userCouponPo.getValidBeginDate()) <= 0
                || now.compareTo(userCouponPo.getValidEndDate()) > 0){
            throw new BizServiceException(EErrorCode.PROMO_USER_COUPON_OVERDUE,"优惠券不在有效期内");
        }

        userCouponPo.setStatus(EUserCouponStatus.USED);

        UserUsagePo userUsagePo = new UserUsagePo();
        userUsagePo.setPromoId(userCouponPo.getPromoId());
        userUsagePo.setIdCardNo(user.getIdCardNo());
        userUsagePo.setUserId(user.getUserId());
        userUsageDao.save(userUsagePo);
        userCouponDao.save(userCouponPo);
    }

    private List<UserCouponPo> getOverdueList(Date nowDate) {

        Specification<UserCouponPo> spec = (Root<UserCouponPo> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            expressions.add(cb.equal(root.get("status"), EUserCouponStatus.UNUSED));
            expressions.add(cb.lessThanOrEqualTo(root.get("validEndDate"), nowDate));
            return predicate;
        };

        List<UserCouponPo> userCouponPos = userCouponDao.findAll(spec);

        return userCouponPos;
    }

    @Transactional
    @Override
    public void overdueJob(){
        List<UserCouponPo> userCouponPos = this.getOverdueList(new Date());

        if(!CollectionUtils.isEmpty(userCouponPos)) {
            userCouponPos.forEach(userCouponPo -> {
                userCouponPo.setStatus(EUserCouponStatus.OVERDUE);
            });
        }
        userCouponDao.save(userCouponPos);
    }

    @JmsListener(destination = DestinationDefine.SEND_COUPON_QUEUE)
    public void giveCoupon(String value) {
        try {
            CouponGiftDto couponGiftDto = JsonUtil.toObject(CouponGiftDto.class,value);
            this.activeCoupon(couponGiftDto.getUserId(),couponGiftDto.getCouponCode(),"loan,");

        } catch (Exception e) {
            LOGGER.error("发放优惠券失败",e);
        }

    }

}
