package com.xinyunlian.jinfu.bank.service;

import com.xinyunlian.jinfu.bank.dao.CorpBankDao;
import com.xinyunlian.jinfu.bank.dto.CorpBankDto;
import com.xinyunlian.jinfu.bank.entity.CorpBankPo;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by dongfangchao on 2017/5/18/0018.
 */
@Service
public class CorpBankServiceImpl implements CorpBankService {

    @Autowired
    private CorpBankDao corpBankDao;

    @Override
    public CorpBankDto getCorpBankByUserId(String userId) throws BizServiceException {
        CorpBankPo po = corpBankDao.findByUserId(userId);
        return ConverterService.convert(po, CorpBankDto.class);
    }

    @Override
    public CorpBankDto addCorpBank(CorpBankDto dto) throws BizServiceException {
        CorpBankPo po = ConverterService.convert(dto, CorpBankPo.class);
        CorpBankPo dbPo = corpBankDao.save(po);
        return ConverterService.convert(dbPo, CorpBankDto.class);
    }

    @Override
    public CorpBankDto getCorpBankById(Long id) throws BizServiceException {
        CorpBankPo po = corpBankDao.findOne(id);
        return ConverterService.convert(po, CorpBankDto.class);
    }
}
