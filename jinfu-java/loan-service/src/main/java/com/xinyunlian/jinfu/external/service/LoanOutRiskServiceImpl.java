package com.xinyunlian.jinfu.external.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.external.dao.LoanApplOutUserDao;
import com.xinyunlian.jinfu.external.dao.LoanOutRiskDao;
import com.xinyunlian.jinfu.external.dto.LoanApplOutUserDto;
import com.xinyunlian.jinfu.external.dto.LoanOutRiskDto;
import com.xinyunlian.jinfu.external.entity.LoanApplOutRiskPo;
import com.xinyunlian.jinfu.external.entity.LoanApplOutUserPo;
import com.xinyunlian.jinfu.loan.enums.EApplOutType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author willwang
 */
@Service
public class LoanOutRiskServiceImpl implements LoanOutRiskService{

    @Autowired
    private LoanOutRiskDao loanOutRiskDao;


    @Override
    public LoanOutRiskDto findByUserIdAndApplyId(String userId, String applyId) {
        LoanApplOutRiskPo po = loanOutRiskDao.findByUserIdAndApplyId(userId, applyId);
        if(po==null)
            return null;
        return  ConverterService.convert(po, LoanOutRiskDto.class);
    }

    @Override
    public LoanOutRiskDto save(LoanOutRiskDto loanOutRiskDto) {
        LoanApplOutRiskPo po = ConverterService.convert(loanOutRiskDto, LoanApplOutRiskPo.class);
        po = loanOutRiskDao.save(po);

        return ConverterService.convert(po, LoanOutRiskDto.class);
    }
}
