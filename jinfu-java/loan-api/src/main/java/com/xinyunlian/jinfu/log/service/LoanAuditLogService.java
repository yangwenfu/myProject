package com.xinyunlian.jinfu.log.service;

import com.xinyunlian.jinfu.log.dto.LoanAuditLogDto;

import java.util.List;

/**
 * Created by jl062 on 2017/2/20.
 */
public interface LoanAuditLogService {

    void save(LoanAuditLogDto logs);

    List<LoanAuditLogDto> getByApplNo(String applNo);
}
