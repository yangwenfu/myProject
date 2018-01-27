package com.xinyunlian.jinfu.external.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.external.dao.LoanApplOutUserDao;
import com.xinyunlian.jinfu.external.dto.LoanApplOutUserDto;
import com.xinyunlian.jinfu.external.entity.LoanApplOutUserPo;
import com.xinyunlian.jinfu.loan.entity.LoanApplUserPo;
import com.xinyunlian.jinfu.loan.enums.EApplOutType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author willwang
 */
@Service
public class LoanApplOutUserServiceImpl implements LoanApplOutUserService{

    @Autowired
    private LoanApplOutUserDao loanApplOutUserDao;

    @Override
    public LoanApplOutUserDto save(LoanApplOutUserDto loanApplOutUserDto) {
        LoanApplOutUserPo po = ConverterService.convert(loanApplOutUserDto, LoanApplOutUserPo.class);
        po = loanApplOutUserDao.save(po);

        return ConverterService.convert(po, LoanApplOutUserDto.class);
    }

    @Override
    public LoanApplOutUserDto findByUserIdAndType(String userId, EApplOutType type) {
        LoanApplOutUserPo po = loanApplOutUserDao.findByUserIdAndType(userId, type);
        if(po == null){
            return null;
        }
        return ConverterService.convert(po, LoanApplOutUserDto.class);
    }

    @Override
    public LoanApplOutUserDto findByOutUserIdAndType(String outUserId, EApplOutType type) {
        LoanApplOutUserPo po = loanApplOutUserDao.findByOutUserIdAndType(outUserId, type);
        if(po == null){
            return null;
        }
        return ConverterService.convert(po, LoanApplOutUserDto.class);
    }
}
