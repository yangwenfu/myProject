package com.xinyunlian.jinfu.loan.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.loan.dao.LoanDtlDao;
import com.xinyunlian.jinfu.loan.dto.RepayDtlDto;
import com.xinyunlian.jinfu.loan.dto.resp.LoanDtlDto;
import com.xinyunlian.jinfu.loan.entity.LoanDtlPo;
import com.xinyunlian.jinfu.loan.entity.RepayDtlPo;
import com.xinyunlian.jinfu.repay.dao.RepayDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author willwang
 */
@Service
public class InnerRepayServiceImpl implements InnerRepayService {

    @Autowired
    private RepayDao repayDao;

    @Override
    @Transactional
    public RepayDtlDto save(RepayDtlDto repayDtlDto) {
        RepayDtlPo po;
        if (repayDtlDto.getRepayId() == null) {
            po = new RepayDtlPo();
        } else {
            po = repayDao.findOne(repayDtlDto.getRepayId());
        }

        if (po.getCreateTs() != null) {
            po.setVersionCt(0L);
        }
        ConverterService.convert(repayDtlDto, po);
        po = repayDao.save(po);
        return ConverterService.convert(po, RepayDtlDto.class);
    }
}

