package com.xinyunlian.jinfu.user.service;

import com.xinyunlian.jinfu.common.util.ThreadContextUtils;
import com.xinyunlian.jinfu.user.dao.OperationLogDao;
import com.xinyunlian.jinfu.user.dto.OperationLogDto;
import com.xinyunlian.jinfu.user.entity.OperationLogPo;
import com.xinyunlian.jinfu.user.enums.EOperationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by JL on 2016/9/5.
 */
@Service
public class OperationLogServiceImpl implements OperationLogService {

    @Autowired
    private OperationLogDao operationLogDao;


    @Override
    public void saveLog(OperationLogDto operationLogDto, EOperationType operationType) {
        OperationLogPo logPo = new OperationLogPo();
        String userId = operationLogDto.getUserId();
        if (userId != null) {
            logPo.setUserId(userId);
        } else {
            logPo.setUserId(ThreadContextUtils.getCurrentUserId());
        }
        logPo.setTraceId(ThreadContextUtils.getTraceId());
        logPo.setOperationType(operationType);
        logPo.setContentObject(operationLogDto.getContent());
        operationLogDao.save(logPo);

    }
}
