package com.xinyunlian.jinfu.external.service;

import com.xinyunlian.jinfu.external.dto.LoanApplOutAuditDto;

import java.util.Collection;
import java.util.List;

public interface LoanApplOutAuditService {

    void save(LoanApplOutAuditDto loanApplOutAuditDto);

    List<LoanApplOutAuditDto> findByApplIdIn(Collection<String> applIds);

    /**
     * 根据申请找到最初的一条外部审核记录
     * @param applId
     * @return
     */
    LoanApplOutAuditDto findByApplId(String applId);

}