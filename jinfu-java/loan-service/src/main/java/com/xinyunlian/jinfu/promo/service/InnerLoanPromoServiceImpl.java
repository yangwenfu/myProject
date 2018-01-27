package com.xinyunlian.jinfu.promo.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.promo.dao.PromoDao;
import com.xinyunlian.jinfu.promo.dto.PromoDto;
import com.xinyunlian.jinfu.promo.entity.PromoPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Willwang on 2016/11/29.
 */
@Service
public class InnerLoanPromoServiceImpl implements InnerLoanPromoService{
    @Autowired
    private PromoDao promoDao;

    @Override
    public PromoDto get(String loanId) {
        PromoDto promoDto = null;
        List<PromoPo> promos = promoDao.findByLoanId(loanId);
        if (!promos.isEmpty()) {
            //暂时不叠加活动，取第一个
            promoDto = ConverterService.convert(promos.get(0), PromoDto.class);
        }

        return promoDto;
    }
}
