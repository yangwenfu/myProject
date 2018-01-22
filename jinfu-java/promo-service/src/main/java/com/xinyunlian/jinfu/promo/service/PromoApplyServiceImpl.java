package com.xinyunlian.jinfu.promo.service;

import com.xinyunlian.jinfu.common.util.BizUtil;
import com.xinyunlian.jinfu.promo.dao.PromoAreaDao;
import com.xinyunlian.jinfu.promo.dao.PromoInfDao;
import com.xinyunlian.jinfu.promo.dao.UserUsageDao;
import com.xinyunlian.jinfu.promo.dao.WhiteBlackUserDao;
import com.xinyunlian.jinfu.promo.dto.OrderDto;
import com.xinyunlian.jinfu.promo.dto.PromoRuleDto;
import com.xinyunlian.jinfu.promo.dto.StoreDto;
import com.xinyunlian.jinfu.promo.dto.UserDto;
import com.xinyunlian.jinfu.promo.entity.PromoAreaPo;
import com.xinyunlian.jinfu.promo.entity.PromoInfPo;
import com.xinyunlian.jinfu.promo.entity.UserUsagePo;
import com.xinyunlian.jinfu.promo.entity.WhiteBlackUserPo;
import com.xinyunlian.jinfu.promo.enums.EPromoInfType;
import com.xinyunlian.jinfu.promo.enums.EProperty;
import com.xinyunlian.jinfu.promo.enums.ERecordType;
import com.xinyunlian.jinfu.rule.service.RuleFirstDiscountService;
import com.xinyunlian.jinfu.rule.service.RuleFullOffService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by King on 2016/11/21.
 */
@Service
public class PromoApplyServiceImpl implements PromoApplyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PromoApplyServiceImpl.class);
    @Autowired
    private PromoInfDao promoInfDao;
    @Autowired
    private WhiteBlackUserDao whiteBlackUserDao;
    @Autowired
    private UserUsageDao userUsageDao;
    @Autowired
    private PromoAreaDao promoAreaDao;
    @Autowired
    private RuleFullOffService ruleFullOffService;
    @Autowired
    private RuleFirstDiscountService ruleFirstDiscountService;

    /**
     * 参加促销活动，如果没有满足的活动就返回null
     * @param user
     * @param stores
     * @param orderDto
     * @return
     */
    public PromoRuleDto gain(UserDto user, List<StoreDto> stores, OrderDto orderDto){
        LOGGER.info("userId:{},amount:{},orderCount:{}",user.getUserId(),orderDto.getAmount(),orderDto.getOrderTotal());
        //判断该产品有哪些活动
       List<PromoInfPo> promoInfPos = promoInfDao.findByProdIdAndPlatformLike(orderDto.getProdId(),
                                                            BizUtil.filterString(orderDto.getPlatform()));
        for (PromoInfPo promoInfPo : promoInfPos) {
            //金额不满足
            if(orderDto.getAmount().compareTo(promoInfPo.getMinimum()) == -1){
                LOGGER.info("金额不满足");
                continue;
            }
            //在黑名单内
            if(checkBlackList(promoInfPo.getPromoId(),user,stores) == 1){
                continue;
            }
            //不在白名单内
            if(checkWhiteList(promoInfPo.getPromoId(),user,stores) == 0){
                //不在可售地区
                if(checkArea(promoInfPo.getPromoId(),stores) == 0){
                    continue;
                }
            }

            PromoRuleDto ruleDto = null;

            if(promoInfPo.getType() == EPromoInfType.DISCOUNT_ON_FIRST_ORDER && orderDto.getOrderTotal() < 1){
                //首单促销
                if(promoInfPo.getProperty() == EProperty.GIT){
                    ruleDto = new PromoRuleDto();
                    ruleDto.setPromoId(promoInfPo.getPromoId());
                    ruleDto.setProperty(EProperty.GIT);
                }else if(promoInfPo.getProperty() == EProperty.DISCOUNT){
                    ruleDto = ruleFirstDiscountService.getRuleDto(promoInfPo.getPromoId());
                }
            }else if(promoInfPo.getType() == EPromoInfType.DISCOUNT_ON_TOTAL_AMT){
                //满减
                //个人次数限制
                List<UserUsagePo> userUsagePos = userUsageDao.findByIdCardNoAndPromoId(user.getIdCardNo(),promoInfPo.getPromoId());
                if(userUsagePos.size()>= promoInfPo.getPerLimit()){
                    LOGGER.info("个人名额超限");
                    continue;
                }
                ruleDto = ruleFullOffService.getRuleDto(promoInfPo.getPromoId(),orderDto.getAmount());
            }

            if(ruleDto != null) {
                UserUsagePo userUsagePo = new UserUsagePo();
                userUsagePo.setPromoId(promoInfPo.getPromoId());
                userUsagePo.setIdCardNo(user.getIdCardNo());
                userUsagePo.setUserId(user.getUserId());
                userUsageDao.save(userUsagePo);
                promoInfPo.setCurNum(promoInfPo.getCurNum() + 1);
                promoInfDao.save(promoInfPo);
                return ruleDto;
            }
        }
        return null;
    }

    /**
     * 判断是否在黑名单中 返回1在黑名单中
     * @param promoId
     * @param user
     * @param stores
     * @return
     */
    public int checkBlackList(Long promoId, UserDto user, List<StoreDto> stores){
        List<WhiteBlackUserPo> blacks = whiteBlackUserDao.findByPromoIdAndRecordType(promoId, ERecordType.BLACK_RECORD);
        if(blacks != null) {
            for (WhiteBlackUserPo blackUserPo : blacks) {
                if (StringUtils.equals(user.getIdCardNo(),blackUserPo.getIdCardNo())
                        || StringUtils.equals(user.getMobile(),blackUserPo.getMobile())
                        || StringUtils.equals(user.getUserName(),blackUserPo.getUserName())) {
                    LOGGER.info("用户存在黑名单中");
                    return 1;
                }
                for(StoreDto storeDto : stores){
                    if(StringUtils.equals(storeDto.getTobaccoCertificateNo(),blackUserPo.getTobaccoCertificateNo())){
                        LOGGER.info("用户存在黑名单中");
                        return 1;
                    }
                }
            }
        }
        return  0;
    }

    /**
     * 判断是否在黑名单中 返回1在黑名单中
     * @param promoId
     * @param user
     * @param stores
     * @return
     */
    public int checkWhiteList(Long promoId, UserDto user, List<StoreDto> stores){
        List<WhiteBlackUserPo> whites = whiteBlackUserDao.findByPromoIdAndRecordType(promoId, ERecordType.WHITE_RECORD);
        if(whites != null) {
            for (WhiteBlackUserPo whiteUserPo : whites) {
                if (StringUtils.equals(user.getIdCardNo(),whiteUserPo.getIdCardNo())
                        && StringUtils.equals(user.getMobile(),whiteUserPo.getMobile())
                        && StringUtils.equals(user.getUserName(),whiteUserPo.getUserName())) {
                    for(StoreDto storeDto : stores){
                        if(StringUtils.equals(storeDto.getTobaccoCertificateNo(),whiteUserPo.getTobaccoCertificateNo())){
                            LOGGER.info("用户存在白名单中");
                            return 1;
                        }
                    }
                }
            }
        }
        return  0;
    }

    public int checkArea(Long promoId, List<StoreDto> stores){
        List<PromoAreaPo> promoAreaPos  = promoAreaDao.getByPromoId(promoId);
        if(promoAreaPos != null){
            for(StoreDto storeDto : stores) {
                LOGGER.info("area:{}",storeDto.getProvinceId() + "," + storeDto.getCityId()
                        + "," + storeDto.getAreaId());
                for (PromoAreaPo promoAreaPo : promoAreaPos) {
                    if (promoAreaPo.getAreaId().toString().equals(storeDto.getProvinceId())
                            || promoAreaPo.getAreaId().toString().equals(storeDto.getCityId())
                            || promoAreaPo.getAreaId().toString().equals(storeDto.getAreaId())) {
                        return 1;
                    }
                }
            }
        }
        LOGGER.info("用户不在促销地区");
        return 0;
    }

}
