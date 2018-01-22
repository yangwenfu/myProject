package com.xinyunlian.jinfu.external.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.external.dao.LoanApplOutAuditDao;
import com.xinyunlian.jinfu.external.dto.LoanApplOutAuditDto;
import com.xinyunlian.jinfu.external.entity.LoanApplOutAuditPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;


/**
 * @author willwang
 */
@Service
public class LoanApplOutAuditServiceImpl implements LoanApplOutAuditService {

    @Autowired
    private LoanApplOutAuditDao loanApplOutAuditDao;

    @Override
    public void save(LoanApplOutAuditDto loanApplOutAuditDto) {
        LoanApplOutAuditPo po = ConverterService.convert(loanApplOutAuditDto, LoanApplOutAuditPo.class);
        loanApplOutAuditDao.save(po);
    }

    @Override
    public List<LoanApplOutAuditDto> findByApplIdIn(Collection<String> applIds) {
        List<LoanApplOutAuditPo> loanApplOutAuditPos = loanApplOutAuditDao.findByApplIdIn(applIds);
        return ConverterService.convertToList(loanApplOutAuditPos, LoanApplOutAuditDto.class);
    }

    public LoanApplOutAuditDto findByApplId(String applId) {
        LoanApplOutAuditPo po = loanApplOutAuditDao.findByApplId(applId);
        if (po == null) {
            return null;
        }
        return ConverterService.convert(po, LoanApplOutAuditDto.class);
    }

}
