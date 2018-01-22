package com.xinyunlian.jinfu.coupon.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.AmtUtils;
import com.xinyunlian.jinfu.coupon.dao.LoanCouponDao;
import com.xinyunlian.jinfu.coupon.dto.LoanCouponDto;
import com.xinyunlian.jinfu.coupon.dto.LoanCouponRepayDto;
import com.xinyunlian.jinfu.coupon.entity.LoanCouponPo;
import com.xinyunlian.jinfu.loan.entity.RepayDtlPo;
import com.xinyunlian.jinfu.repay.dao.RepayDao;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Willwang on 2017/3/27.
 */
@Service
public class LoanCouponServiceImpl implements LoanCouponService {

    @Autowired
    private LoanCouponDao loanCouponDao;

    @Autowired
    private RepayDao repayDao;

    @Override
    @Transactional
    public void save(LoanCouponDto loanCouponDto) {
        LoanCouponPo loanCouponPo = ConverterService.convert(loanCouponDto, LoanCouponPo.class);
        loanCouponDao.save(loanCouponPo);
    }

    @Override
    public List<LoanCouponDto> listByRepayId(String repayId) {
        List<LoanCouponPo> list = loanCouponDao.findByRepayId(repayId);
        return ConverterService.convertToList(list, LoanCouponDto.class);
    }

    @Override
    public List<LoanCouponDto> listByRepayIds(Collection<String> repayIds) {
        if(repayIds.isEmpty()){
            return new ArrayList<>();
        }
        List<LoanCouponPo> list = loanCouponDao.findByRepayIdIn(repayIds);
        return ConverterService.convertToList(list, LoanCouponDto.class);
    }

    @Override
    public LoanCouponRepayDto calcPrice(String repayId) throws BizServiceException {
        LoanCouponRepayDto loanCouponRepayDto = new LoanCouponRepayDto();

        List<LoanCouponDto> coupons = this.listByRepayId(repayId);
        RepayDtlPo repayDtlPo = repayDao.findOne(repayId);

        if(CollectionUtils.isEmpty(coupons)){
            return loanCouponRepayDto;
        }
        LoanCouponDto coupon = coupons.get(0);
        if(coupon == null){
            return loanCouponRepayDto;
        }

        BigDecimal price = AmtUtils.min(repayDtlPo.getRepayIntr(), coupon.getPrice());
        loanCouponRepayDto.setPrice(price);
        loanCouponRepayDto.setCouponType(coupon.getCouponType());
        return loanCouponRepayDto;
    }
}
