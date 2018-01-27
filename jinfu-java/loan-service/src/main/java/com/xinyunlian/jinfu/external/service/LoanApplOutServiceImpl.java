package com.xinyunlian.jinfu.external.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.external.dao.LoanApplOutDao;
import com.xinyunlian.jinfu.external.dto.LoanApplOutDto;
import com.xinyunlian.jinfu.external.entity.LoanApplOutPo;
import com.xinyunlian.jinfu.loan.enums.EApplOutType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Convert;
import java.util.ArrayList;
import java.util.List;

/**
 * @author willwang
 */
@Service
public class LoanApplOutServiceImpl implements LoanApplOutService{

    @Autowired
    private LoanApplOutDao loanApplOutDao;

    @Override
    public void save(LoanApplOutDto loanApplOutDto) {
        LoanApplOutPo po = ConverterService.convert(loanApplOutDto, LoanApplOutPo.class);
        loanApplOutDao.save(po);
    }

    @Override
    public List<LoanApplOutDto> findByApplId(String applId) {
        List<LoanApplOutPo> loanApplOutPos = loanApplOutDao.findByApplId(applId);

        if(loanApplOutPos == null || loanApplOutPos.size() <= 0){
            return new ArrayList<>();
        }

        return ConverterService.convertToList(loanApplOutPos, LoanApplOutDto.class);
    }

    @Override
    public LoanApplOutDto findByApplIdAndType(String applId, EApplOutType type) {
        List<LoanApplOutPo> loanApplOutPos = loanApplOutDao.findByApplIdAndType(applId, type);

        if(!loanApplOutPos.isEmpty()){
            return ConverterService.convert(loanApplOutPos.get(0), LoanApplOutDto.class);
        }

        return null;
    }

    @Override
    public LoanApplOutDto findByOutTradeNoAndType(String outTradeNo, EApplOutType type) {
        List<LoanApplOutPo> loanApplOutPos = loanApplOutDao.findByOutTradeNoAndType(outTradeNo, type);

        if(!loanApplOutPos.isEmpty()){
            return ConverterService.convert(loanApplOutPos.get(0), LoanApplOutDto.class);
        }

        return null;
    }
}
