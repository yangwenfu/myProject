package com.xinyunlian.jinfu.trans.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.trans.dao.TransHistoryDao;
import com.xinyunlian.jinfu.trans.dto.TransHistoryDto;
import com.xinyunlian.jinfu.trans.entity.TransHistoryPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by dongfangchao on 2017/6/12/0012.
 */
@Service
public class TransHistoryInnerServiceImpl implements TransHistoryInnerHistoryService{

    @Autowired
    private TransHistoryDao transHistoryDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public TransHistoryDto addTransHistory(TransHistoryDto transHistoryDto) throws BizServiceException {
        TransHistoryPo po = ConverterService.convert(transHistoryDto, TransHistoryPo.class);
        TransHistoryPo dbPo = transHistoryDao.save(po);
        return ConverterService.convert(dbPo, TransHistoryDto.class);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateTransHistory(TransHistoryDto transHistoryDto) throws BizServiceException {
        TransHistoryPo po = transHistoryDao.findOne(transHistoryDto.getTransSerialNo());
        ConverterService.convert(transHistoryDto, po);
    }
}
