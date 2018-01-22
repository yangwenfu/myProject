package com.xinyunlian.jinfu.external.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.external.dao.LoanApplOutRefundsAdvanceDao;
import com.xinyunlian.jinfu.external.dto.RefundsAdvanceDto;
import com.xinyunlian.jinfu.external.entity.LoanApplOutRefundsAdvancePo;
import org.jboss.netty.util.internal.ConversionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * Created by godslhand on 2017/7/14.
 */
@Service(value = "loanApplOutRefundsAdvanceServiceImpl")
public class LoanApplOutRefundsAdvanceServiceImpl implements LoanApplOutRefundsAdvanceService {

    @Autowired
    LoanApplOutRefundsAdvanceDao loanApplOutRefundsAdvanceDao ;
    @Override
    public RefundsAdvanceDto findByApplId(String applId) {
        List<LoanApplOutRefundsAdvancePo> refundsAdvancePos =
                loanApplOutRefundsAdvanceDao.findByApplyIdAndDisabledOrderByIdDesc(applId,false);
        if(CollectionUtils.isEmpty(refundsAdvancePos)){
            return null ;
        }

        RefundsAdvanceDto dto = ConverterService.convert(refundsAdvancePos.get(0),RefundsAdvanceDto.class);
        dto.setLoanRefuandInAdvanceId(refundsAdvancePos.get(0).getOutRefundsAdvanceId());
         return dto;
    }

    @Override
    @Transactional
    public void updateDisableTag(String applId,String outRefundsId) {
        loanApplOutRefundsAdvanceDao.updateDisableTag(applId,outRefundsId);
    }

    @Override
    public void save(RefundsAdvanceDto refundsAdvanceDto) {
        List<LoanApplOutRefundsAdvancePo> list =loanApplOutRefundsAdvanceDao.findByApplyIdAndOutId(
                refundsAdvanceDto.getApplyId(),refundsAdvanceDto.getLoanRefuandInAdvanceId());
        if(!CollectionUtils.isEmpty(list)) //重复记录不插
            return;
        LoanApplOutRefundsAdvancePo po= ConverterService.convert(refundsAdvanceDto,LoanApplOutRefundsAdvancePo.class);
        po.setOutRefundsAdvanceId(refundsAdvanceDto.getLoanRefuandInAdvanceId());
        loanApplOutRefundsAdvanceDao.save(po);
    }
}
