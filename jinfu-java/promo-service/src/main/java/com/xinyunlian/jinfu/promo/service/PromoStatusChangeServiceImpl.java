package com.xinyunlian.jinfu.promo.service;

import com.xinyunlian.jinfu.promo.dao.PromoInfDao;
import com.xinyunlian.jinfu.promo.dto.PromoInfDto;
import com.xinyunlian.jinfu.promo.entity.PromoInfPo;
import com.xinyunlian.jinfu.promo.enums.EPromoInfStatus;
import com.xinyunlian.jinfu.promo.state.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by King on 2016/11/21.
 */
@Service
public class PromoStatusChangeServiceImpl implements PromoStatusChangeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PromoStatusChangeServiceImpl.class);
    private static final PromotionState ACTIVE_STATE = new ActiveState();
    private static final PromotionState FINISHED_STATE = new FinishedState();
    private static final PromotionState DELETED_STATE = new DeletedState();
    private static final PromotionState INVALID_STATE = new InvalidState();

    @Autowired
    private PromoInfDao promoInfDao;

    private void saveStatus(Long promotionId, EPromoInfStatus status){
        PromoInfPo po = promoInfDao.findOne(promotionId);
        po.setStatus(status);
        promoInfDao.save(po);
    }

    @Override
    public void active(PromoInfDto promoInfDto) {
        ACTIVE_STATE.on(promoInfDto);
        saveStatus(promoInfDto.getPromoId(), promoInfDto.getStatus());
    }

    @Override
    public void invalid(PromoInfDto promoInfDto) {
        INVALID_STATE.on(promoInfDto);
        saveStatus(promoInfDto.getPromoId(), promoInfDto.getStatus());
    }

    @Override
    public void finish(PromoInfDto promoInfDto) {
        FINISHED_STATE.on(promoInfDto);
        saveStatus(promoInfDto.getPromoId(), promoInfDto.getStatus());
    }

    @Override
    public void delete(PromoInfDto promoInfDto) {
        DELETED_STATE.on(promoInfDto);
        saveStatus(promoInfDto.getPromoId(), promoInfDto.getStatus());
    }
}
