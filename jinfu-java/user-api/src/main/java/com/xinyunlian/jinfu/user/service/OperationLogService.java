package com.xinyunlian.jinfu.user.service;

import com.xinyunlian.jinfu.user.dto.OperationLogDto;
import com.xinyunlian.jinfu.user.enums.EOperationType;

/**
 * Created by JL on 2016/9/5.
 */
public interface OperationLogService {


    void saveLog(OperationLogDto operationLogDto, EOperationType operationType);
}
