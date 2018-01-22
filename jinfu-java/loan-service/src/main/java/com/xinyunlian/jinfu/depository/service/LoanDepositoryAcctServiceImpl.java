package com.xinyunlian.jinfu.depository.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.depository.dao.LoanDepositoryAcctDao;
import com.xinyunlian.jinfu.depository.dto.LoanDepositoryAcctDto;
import com.xinyunlian.jinfu.depository.entity.LoanDepositoryAcctPo;
import com.ylfin.depository.dto.AcctInfoDto;
import com.ylfin.depository.service.AcctService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author willwang
 */
@Service
public class LoanDepositoryAcctServiceImpl implements LoanDepositoryAcctService{

    @Autowired
    private LoanDepositoryAcctDao loanDepositoryAcctDao;

    @Autowired
    private AcctService acctService;

    @Override
    public LoanDepositoryAcctDto findByBankCardId(Long bankCardId) {

        LoanDepositoryAcctPo po = loanDepositoryAcctDao.findByBankCardId(bankCardId);

        if (po == null) {
            return null;
        }

        return ConverterService.convert(po, LoanDepositoryAcctDto.class);
    }

    @Override
    @Transactional
    public LoanDepositoryAcctDto open(LoanDepositoryAcctDto loanDepositoryAcctDto) throws BizServiceException {

        LoanDepositoryAcctPo po = loanDepositoryAcctDao.findByBankCardId(loanDepositoryAcctDto.getBankCardId());

        if(po != null){
            return ConverterService.convert(po, LoanDepositoryAcctDto.class);
        }

        AcctInfoDto acctInfoDto = new AcctInfoDto();
        
        acctInfoDto.setMobile(loanDepositoryAcctDto.getMobile());
        acctInfoDto.setName(loanDepositoryAcctDto.getName());
        acctInfoDto.setIdCardNo(loanDepositoryAcctDto.getIdCardNo());
        acctInfoDto.setBankCardNo(loanDepositoryAcctDto.getBankCardNo());

        acctInfoDto = acctService.borrowerOpenAcct(acctInfoDto);

        loanDepositoryAcctDto.setAcctNo(acctInfoDto.getAcctNo());

        return this.save(loanDepositoryAcctDto);
    }
    
    @Transactional
    public LoanDepositoryAcctDto save(LoanDepositoryAcctDto loanDepositoryAcctDto) throws BizServiceException{
        LoanDepositoryAcctPo po;
        if (loanDepositoryAcctDto.getId() == null) {
            po = new LoanDepositoryAcctPo();
        } else {
            po = loanDepositoryAcctDao.findOne(loanDepositoryAcctDto.getId());
            if(po == null){
                po = new LoanDepositoryAcctPo();
            }
        }

        if (po.getCreateTs() != null) {
            po.setVersionCt(0L);
        }
        ConverterService.convert(loanDepositoryAcctDto, po);
        loanDepositoryAcctDao.save(po);
        loanDepositoryAcctDto.setId(po.getId());
        return loanDepositoryAcctDto;
        
    }
}
