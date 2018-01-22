package com.xinyunlian.jinfu.loan.service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.loan.dto.loan.LoanAllCouponDto;
import com.xinyunlian.jinfu.loan.dto.resp.LoanApplDto;
import com.xinyunlian.jinfu.loan.dto.resp.LoanDtlDto;
import com.xinyunlian.jinfu.loan.enums.ELoanStat;
import com.xinyunlian.jinfu.promo.dto.*;
import com.xinyunlian.jinfu.promo.enmus.EPromoType;
import com.xinyunlian.jinfu.promo.enums.EProperty;
import com.xinyunlian.jinfu.promo.service.CouponService;
import com.xinyunlian.jinfu.promo.service.LoanPromoService;
import com.xinyunlian.jinfu.promo.service.PromoApplyService;
import com.xinyunlian.jinfu.promo.service.UserCouponService;
import com.xinyunlian.jinfu.rule.enums.EOffType;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.store.service.StoreService;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by King on 2016/11/24.
 */
@Service
public class PrivatePromoService {

    @Autowired
    private StoreService storeService;

    @Autowired
    private LoanApplService loanApplService;

    @Autowired
    private LoanService loanService;

    @Autowired
    private PromoApplyService promoApplyService;

    @Autowired
    private UserCouponService userCouponService;

    @Autowired
    private UserService userService;

    @Autowired
    private LoanPromoService loanPromoService;

    @Autowired
    private CouponService couponService;

    private static final Logger LOGGER = LoggerFactory.getLogger(PrivatePromoService.class);

    /**
     * 参加活动接口
     * @param userInfoDto
     * @param applId
     * @return
     */
    public PromoDto gain(UserInfoDto userInfoDto, String applId) {
        List<StoreDto> storeDtos = this.getStores(userInfoDto.getUserId());

        LoanApplDto loanApplDto = loanApplService.get(applId);

        Long loanCount = loanService.count(userInfoDto.getUserId(), loanApplDto.getProductId());
        UserDto userDto = ConverterService.convert(userInfoDto, UserDto.class);
        OrderDto orderDto = new OrderDto();
        orderDto.setPlatform("loan");
        orderDto.setProdId(loanApplDto.getProductId());
        orderDto.setOrderTotal(loanCount);
        orderDto.setAmount(loanApplDto.getApprAmt());
        PromoRuleDto promoRuleDto = promoApplyService.gain(userDto, storeDtos, orderDto);
        return converPromoRuleDto(promoRuleDto);
    }

    /**
     * 获取小贷可用的优惠券列表
     */
    public AllCouponDto listLoanCoupons(String userId, String loanId){
        List<StoreDto> storeDtos = this.getStores(userId);
        UserInfoDto userInfoDto = userService.findUserByUserId(userId);
        UserDto userDto = ConverterService.convert(userInfoDto, UserDto.class);

        AllCouponDto allCouponDto = new AllCouponDto();
        allCouponDto.setUnusedCoupons(new ArrayList<>());
        allCouponDto.setUsedCoupons(new ArrayList<>());
        allCouponDto.setOverdueCoupons(new ArrayList<>());

        //可用
        List<UserCouponDto> used = new ArrayList<>();
        //不可用
        List<UserCouponDto> unused = new ArrayList<>();

        LoanDtlDto loan = loanService.get(loanId);

        assertYours(userId, loan);

        //3代表融资
        List<UserCouponDto> couponDtos = userCouponService.getUsable(userDto, storeDtos, 3);

        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("user's coupons, user_id:{}, coupons:{}", userId, couponDtos);
        }

        //小贷可用优惠券为0，直接返回原始结构
        if(CollectionUtils.isEmpty(couponDtos)){
            return this.completeCount(allCouponDto);
        }

        //如果是逾期或结清贷款，所有券都不可用
        if(!ELoanStat.NORMAL.equals(loan.getLoanStat())){
            allCouponDto.setUnusedCoupons(couponService.setCouponDto(couponDtos));
            if(LOGGER.isDebugEnabled()){
                LOGGER.debug("user's loan isn't normal, user_id:{}, loan_id:{}", userId, loan.getLoanId());
            }
            return this.completeCount(allCouponDto);
        }

        //找出该笔贷款参加过的活动
        PromoDto attend = loanPromoService.get(loanId);

        for (UserCouponDto couponDto : couponDtos) {
            //如果不支持重复叠加的活动类型且已经参加过活动
            if(this.cantSupportComposite(couponDto) && attend != null){
                unused.add(couponDto);
                if(LOGGER.isDebugEnabled()){
                    LOGGER.debug("can't support composite, coupon_id:{}", couponDto.getId());
                }
                continue;
            }

            //不满足活动条件就干掉
            if(StringUtils.isNotEmpty(couponDto.getProdId()) && !couponDto.getProdId().equals(loan.getProdId())){
                unused.add(couponDto);
                if(LOGGER.isDebugEnabled()){
                    LOGGER.debug("product id mismatching, coupon_id:{}", couponDto.getId());
                }
                continue;
            }

            //如果不满足优惠券的最小金额
            if(loan.getLoanAmt() != null && loan.getLoanAmt().compareTo(couponDto.getMinimum()) < 0){
                unused.add(couponDto);
                if(LOGGER.isDebugEnabled()){
                    LOGGER.debug("amt is too low, coupon_id:{}", couponDto.getId());
                }
                continue;
            }

            //如果属于黑名单，就自动过滤掉
            if(couponDto.isOnBlack()){
                unused.add(couponDto);
                if(LOGGER.isDebugEnabled()){
                    LOGGER.debug("is on black, coupon_id:{}", couponDto.getId());
                }
                continue;
            }

            //不满足使用时间
            if(!DateHelper.between(new Date(), couponDto.getValidBeginDate(), couponDto.getValidEndDate())){
                unused.add(couponDto);
                if(LOGGER.isDebugEnabled()){
                    LOGGER.debug("date is mismatching, coupon_id:{}", couponDto.getId());
                }
                continue;
            }

            used.add(couponDto);
        }

        allCouponDto.setUsedCoupons(couponService.setCouponDto(used));
        allCouponDto.setUnusedCoupons(couponService.setCouponDto(unused));

        return this.completeCount(allCouponDto);
    }

    public LoanAllCouponDto convertLoanAllCouponDto(AllCouponDto allCouponDto){
        LoanAllCouponDto loanAllCouponDto = new LoanAllCouponDto();
        loanAllCouponDto.setUsableCnt(allCouponDto.getUsedCount());
        loanAllCouponDto.setDisabledCnt(allCouponDto.getUnusedCount());
        loanAllCouponDto.setUsableList(allCouponDto.getUsedCoupons());
        loanAllCouponDto.setDisabledList(allCouponDto.getUnusedCoupons());

        return loanAllCouponDto;
    }

    private AllCouponDto completeCount(AllCouponDto allCouponDto){
        allCouponDto.setUnusedCount(allCouponDto.getUnusedCoupons().size());
        allCouponDto.setUsedCount(allCouponDto.getUsedCoupons().size());
        allCouponDto.setOverdueCount(allCouponDto.getOverdueCoupons().size());
        return allCouponDto;
    }

    /**
     * 获取小贷可用的优惠券（单张）
     * @param userId
     * @param loanId
     * @param couponId
     * @return
     */
    public UserCouponDto getUserCoupon(String userId, String loanId, Long couponId){
        LoanDtlDto loan = loanService.get(loanId);
        assertYours(userId, loan);
        //如果是逾期或结清贷款，所有券都不可用
        if(!ELoanStat.NORMAL.equals(loan.getLoanStat())){
            return null;
        }

        UserCouponDto couponDto = userCouponService.getUserCoupon(couponId);

        //找出该笔贷款参加过的活动
        PromoDto attend = loanPromoService.get(loanId);

        if(this.cantSupportComposite(couponDto) && attend != null){
            return null;
        }

        //如果不满足优惠券的最小金额
        if(loan.getLoanAmt() != null && loan.getLoanAmt().compareTo(couponDto.getMinimum()) < 0){
            return null;
        }

        if(couponDto.isOnBlack()){
            return null;
        }

        if(StringUtils.isNotEmpty(couponDto.getProdId()) && !couponDto.getProdId().equals(loan.getProdId())){
            return null;
        }

        if(!DateHelper.between(new Date(), couponDto.getValidBeginDate(), couponDto.getValidEndDate())){
            return null;
        }

        return couponDto;
    }

    private void assertYours(String userId, LoanDtlDto loan){
        if(loan == null){
            throw new BizServiceException(EErrorCode.LOAN_PARAM_ERROR, "loan is null");
        }

        if(!userId.equals(loan.getUserId())){
            throw new BizServiceException(EErrorCode.LOAN_APPL_NOT_YOURS, "loan is not yours");
        }
    }

    private boolean cantSupportComposite(UserCouponDto couponDto){
        return couponDto.getAdded() == null || !couponDto.getAdded();
    }

    private PromoDto converPromoRuleDto(PromoRuleDto promoRuleDto) {
        if (promoRuleDto != null) {
            PromoDto promoDto = new PromoDto();
            promoDto.setPromoId(promoRuleDto.getPromoId());
            if (promoRuleDto.getProperty() == EProperty.GIT) {
                promoDto.setPromoType(EPromoType.OFFLINE);
            } else {
                if (promoRuleDto.getOffType() == EOffType.MONEY) {
                    promoDto.setPromoType(EPromoType.MONEY);
                } else {
                    promoDto.setPromoType(EPromoType.RATE);
                }
                promoDto.setPromoLen(promoRuleDto.getTerm());
                promoDto.setPromoValue(promoRuleDto.getDiscount());
            }
            return promoDto;
        } else {
            return null;
        }
    }

    private List<StoreDto> getStores(String userId){
        List<StoreInfDto> storeInfDtos = storeService.findByUserId(userId);
        List<StoreDto> storeDtos = new ArrayList<>();
        storeInfDtos.forEach(storeInfDto -> {
            StoreDto storeDto = ConverterService.convert(storeInfDto, StoreDto.class);
            storeDtos.add(storeDto);
        });
        return storeDtos;
    }
}
