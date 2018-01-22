package com.xinyunlian.jinfu.promo.service;

import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.promo.dto.*;
import com.xinyunlian.jinfu.promo.enums.EPromoInfStatus;
import com.xinyunlian.jinfu.promo.enums.EProperty;
import com.xinyunlian.jinfu.promo.enums.ERecordType;
import com.xinyunlian.jinfu.rule.dto.RuleCouponDto;
import com.xinyunlian.jinfu.rule.dto.RuleFirstDiscountDto;
import com.xinyunlian.jinfu.rule.dto.RuleFirstGiftDto;
import com.xinyunlian.jinfu.rule.dto.RuleFullOffDto;
import com.xinyunlian.jinfu.rule.service.RuleCouponService;
import com.xinyunlian.jinfu.rule.service.RuleFirstDiscountService;
import com.xinyunlian.jinfu.rule.service.RuleFirstGiftService;
import com.xinyunlian.jinfu.rule.service.RuleFullOffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by bright on 2016/11/22.
 */
@Service
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    private PromoInfService promoInfService;

    @Autowired
    private CompanyCostService companyCostService;

    @Autowired
    private PromoAreaService promoAreaService;

    @Autowired
    private WhiteBlackUserService whiteBlackUserService;

    @Autowired
    private RuleFirstDiscountService ruleFirstDiscountService;

    @Autowired
    private RuleFirstGiftService ruleFirstGiftService;

    @Autowired
    private RuleFullOffService ruleFullOffService;

    @Autowired
    private PromoStatusChangeService promoStatusChangeService;

    @Autowired
    private RuleCouponService ruleCouponService;

    @Override
    @Transactional
    public void savePromotion(PromotionDto promotion) throws BizServiceException{
        Long promoId = promotion.getPromoInfDto().getPromoId();
        if(null != promoId){
            PromoInfDto promoInf = promoInfService.getByPromoId(promoId);
            if(promoInf != null && promoInf.getStatus() != EPromoInfStatus.INACTIVE){
                throw new BizServiceException(EErrorCode.PROMO_OPERATION_NOT_ALLOW);
            }
            // 清空区域配置
            promoAreaService.deleteByPromotionId(promoId);
        }

        //保存促销主体
        PromoInfDto promoInfDto = promotion.getPromoInfDto();
        promoInfDto.setProperty(EProperty.DISCOUNT);
        if(promotion.getRuleFirstGiftDto() != null && promotion.getRuleFirstGiftDto().size() > 0){
            promoInfDto.setProperty(EProperty.GIT);
        }
        Long promotionId = promoInfService.savePromotion(promoInfDto);

        //保存区域设置
        List<PromoAreaDto> areas = promotion.getPromoAreaDto();
        if(areas != null && areas.size() > 0){
            areas.forEach(promoAreaDto -> {
                promoAreaDto.setPromoId(promotionId);
            });
            promoAreaService.save(areas);
        }

        //保存黑白名单
        if(!promotion.getBlackListExists()){
            whiteBlackUserService.deleteByPromoIdAndType(promotionId, ERecordType.BLACK_RECORD);
        }
        List<WhiteBlackUserDto> blackList = promotion.getBlackList();
        if(blackList != null && blackList.size()>0){
            whiteBlackUserService.deleteByPromoIdAndType(promotionId, ERecordType.BLACK_RECORD);
            blackList.forEach(whiteBlackUserDto -> {
                whiteBlackUserDto.setPromoId(promotionId);
            });
            whiteBlackUserService.save(blackList);
        }

        if(!promotion.getWhiteListExists()){
            whiteBlackUserService.deleteByPromoIdAndType(promotionId, ERecordType.WHITE_RECORD);
        }
        List<WhiteBlackUserDto> whiteList = promotion.getWhiteList();
        if(whiteList != null && whiteList.size()>0){
            whiteBlackUserService.deleteByPromoIdAndType(promotionId, ERecordType.WHITE_RECORD);
            whiteList.forEach(whiteBlackUserDto -> {
                whiteBlackUserDto.setPromoId(promotionId);
            });
            whiteBlackUserService.save(whiteList);
        }

        //保存成本比例
        List<CompanyCostDto> costsPlan = promotion.getCompanyCostDto();
        if(costsPlan != null && costsPlan.size()>0){
            costsPlan.forEach(companyCostDto -> {
                companyCostDto.setPromoId(promotionId);
            });
            companyCostService.saveCostsPlan(costsPlan);
        }

        //保存首单折扣
        RuleFirstDiscountDto ruleFirstDiscountDto = promotion.getRuleFirstDiscountDto();
        if(ruleFirstDiscountDto != null){
            ruleFirstGiftService.deleteByPromoId(promotionId);
            ruleFirstDiscountDto.setPromoId(promotionId);
            ruleFirstDiscountService.save(ruleFirstDiscountDto);
        }

        //保存首单有礼
        List<RuleFirstGiftDto> ruleFirstGiftDtos = promotion.getRuleFirstGiftDto();
        if (ruleFirstGiftDtos != null && ruleFirstGiftDtos.size() > 0 ) {
            ruleFirstDiscountService.deleteByPromoId(promotionId);
            ruleFirstGiftDtos.forEach(ruleFirstGiftDto -> {
                ruleFirstGiftDto.setPromoId(promotionId);
            });
            ruleFirstGiftService.save(ruleFirstGiftDtos);
        }

        //保存满减
        RuleFullOffDto ruleFullOffDto = promotion.getRuleFullOffDto();
        if(ruleFullOffDto != null){
            ruleFullOffDto.setPromoId(promotionId);
            ruleFullOffService.save(ruleFullOffDto);
        }

        //保存优惠券
        RuleCouponDto ruleCouponDto = promotion.getRuleCouponDto();
        if(ruleCouponDto != null){
            ruleCouponDto.setPromoId(promotionId);
            ruleCouponService.save(ruleCouponDto);
        }
    }

    @Override
    public PromoInfSearchDto search(PromoInfSearchDto searchDto){
        return promoInfService.findPromoInf(searchDto);
    }

    @Override
    public PromotionDto getByPromotionId(Long promotionId){
        PromoInfDto promoInfDto = promoInfService.getByPromoId(promotionId);
        List<PromoAreaDto> areas = promoAreaService.getAreasByPromoId(promotionId);
        List<CompanyCostDto> costsPlan = companyCostService.getCostsPlanByPromoId(promotionId);
        RuleFirstDiscountDto ruleFirstDiscountDto = ruleFirstDiscountService.findByPromoId(promotionId);
        List<RuleFirstGiftDto> ruleFirstGiftDto = ruleFirstGiftService.findByPromoId(promotionId);
        RuleFullOffDto ruleFullOffDto = ruleFullOffService.findByPromoId(promotionId);
        RuleCouponDto ruleCouponDto = ruleCouponService.findByPromoId(promotionId);
        List<WhiteBlackUserDto> whiteUserList = whiteBlackUserService.findByPromoIdAndType(promotionId, ERecordType.WHITE_RECORD);
        List<WhiteBlackUserDto> blackUserList = whiteBlackUserService.findByPromoIdAndType(promotionId, ERecordType.BLACK_RECORD);

        PromotionDto promotion = new PromotionDto();
        promotion.setPromoInfDto(promoInfDto);
        promotion.setCompanyCostDto(costsPlan);
        promotion.setPromoAreaDto(areas);
        promotion.setRuleFirstDiscountDto(ruleFirstDiscountDto);
        promotion.setRuleFullOffDto(ruleFullOffDto);
        promotion.setRuleFirstGiftDto(ruleFirstGiftDto);
        promotion.setRuleCouponDto(ruleCouponDto);

        if (whiteUserList.size() > 0) {
            promotion.setWhiteListExists(true);
        }

        if (blackUserList.size() > 0) {
            promotion.setBlackListExists(true);
        }

        return promotion;
    }

    @Override
    public void activePromotion(Long promotionId) throws BizServiceException{
        PromoInfDto dto = promoInfService.getByPromoId(promotionId);
        promoStatusChangeService.active(dto);
    }

    @Override
    public void invalidPromotion(Long promotionId) throws BizServiceException{
        PromoInfDto dto = promoInfService.getByPromoId(promotionId);
        promoStatusChangeService.invalid(dto);
    }

    @Override
    public void finishPromotion(Long promotionId) throws BizServiceException{
        PromoInfDto dto = promoInfService.getByPromoId(promotionId);
        promoStatusChangeService.finish(dto);
    }

    @Override
    public void deletePromotion(Long promotionId) throws BizServiceException{
        PromoInfDto dto = promoInfService.getByPromoId(promotionId);
        promoStatusChangeService.delete(dto);
    }

    @Override
    public List<WhiteBlackUserDto> getWhiteBlackRecords(Long promotionId, ERecordType type){
        return whiteBlackUserService.findByPromoIdAndType(promotionId, type);
    }

    @Override
    public void finishJob(){
        promoInfService.finishJob();
    }
}
