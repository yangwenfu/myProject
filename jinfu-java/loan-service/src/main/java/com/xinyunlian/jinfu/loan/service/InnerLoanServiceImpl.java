package com.xinyunlian.jinfu.loan.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.loan.dao.LoanDtlDao;
import com.xinyunlian.jinfu.loan.dto.resp.LoanDtlDto;
import com.xinyunlian.jinfu.loan.entity.LoanDtlPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author willwang
 */
@Service
public class InnerLoanServiceImpl implements InnerLoanService {
    @Autowired
    private LoanDtlDao loanDtlDao;

    @Override
    @Transactional
    public void save(LoanDtlDto loanDtlDto) {
        LoanDtlPo po;
        if (loanDtlDto.getLoanId() == null) {
            po = new LoanDtlPo();
        } else {
            po = loanDtlDao.findOne(loanDtlDto.getLoanId());
        }

        if (po.getCreateTs() != null) {
            po.setVersionCt(0L);
        }
        ConverterService.convert(loanDtlDto, po);
        loanDtlDao.save(po);
    }

    @Override
    public LoanDtlDto get(String loanId) {
        LoanDtlPo loanDtlPo = loanDtlDao.findOne(loanId);

        return ConverterService.convert(loanDtlPo, LoanDtlDto.class);
    }
}
