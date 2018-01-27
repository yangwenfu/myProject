package com.xinyunlian.jinfu.log.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.log.dao.LoanAuditLogDao;
import com.xinyunlian.jinfu.log.dto.LoanAuditLogDto;
import com.xinyunlian.jinfu.log.entity.LoanAuditLogPo;

/**
 * Created by jl062 on 2017/2/20.
 */
@Service
public class LoanAuditLogServiceImpl implements LoanAuditLogService {

    @Autowired
    private LoanAuditLogDao logDao;


    @Override
    public void save(LoanAuditLogDto logs) {
        if (logs != null) {
            logs.setOperateDate(new Date());
            logDao.save(ConverterService.convert(logs, LoanAuditLogPo.class));
        }
    }

    @Override
    public List<LoanAuditLogDto> getByApplNo(String applNo) {
        List<LoanAuditLogPo> logPos = logDao.findByApplId(applNo);
        return ConverterService.convertToList(logPos, LoanAuditLogDto.class);
//        return logPos.stream()
//                .map(po -> ConverterService.convert(po, LoanAuditLogDto.class))
//                .collect(Collectors.toList());
    }
}
