package com.xinyunlian.jinfu.loan.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.loan.dao.LoanApplUserDao;
import com.xinyunlian.jinfu.loan.dto.LoanApplUserDto;
import com.xinyunlian.jinfu.loan.entity.LoanApplUserPo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author willwang
 */
@Service
public class LoanApplUserServiceImpl implements LoanApplUserService {

    @Autowired
    private LoanApplUserDao loanApplUserDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoanApplUserService.class);

    @Override
    public LoanApplUserDto findByApplId(String applId) {
        LoanApplUserPo po = loanApplUserDao.findByApplId(applId);
        return ConverterService.convert(po, LoanApplUserDto.class);
    }

    @Override
    @Transactional
    public void save(LoanApplUserDto dto) {
        LoanApplUserPo po;
        if(StringUtils.isNotEmpty(dto.getApplId())){
            po = loanApplUserDao.findByApplId(dto.getApplId());
            if(po == null){
                po = new LoanApplUserPo();
            }
            ConverterService.convert(dto, po);
        }else{
            po = ConverterService.convert(dto, LoanApplUserPo.class);
        }
        loanApplUserDao.save(po);
    }

}
