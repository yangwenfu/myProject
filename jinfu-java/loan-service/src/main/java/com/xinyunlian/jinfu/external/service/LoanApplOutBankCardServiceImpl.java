package com.xinyunlian.jinfu.external.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.external.dao.LoanApplOutBankCardDao;
import com.xinyunlian.jinfu.external.dao.LoanApplOutUserDao;
import com.xinyunlian.jinfu.external.dto.LoanApplOutBankCardDto;
import com.xinyunlian.jinfu.external.entity.LoanApplOutBankCardPo;
import com.xinyunlian.jinfu.external.entity.LoanApplOutUserPo;
import com.xinyunlian.jinfu.loan.dao.LoanApplDao;
import com.xinyunlian.jinfu.loan.entity.LoanApplPo;
import com.xinyunlian.jinfu.loan.enums.EApplOutType;
import com.xinyunlian.jinfu.router.dto.FinanceSourceDto;
import com.xinyunlian.jinfu.router.enums.EFinanceSourceType;
import com.xinyunlian.jinfu.router.service.FinanceSourceService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * @author willwang
 */
@Service
public class LoanApplOutBankCardServiceImpl implements LoanApplOutBankCardService {

    @Autowired
    private LoanApplOutBankCardDao outBankCardDao;

    @Autowired
    private LoanApplOutUserDao loanApplOutUserDao;

    @Autowired
    private LoanApplDao loanApplDao;

    @Autowired
    private LoanApplOutBankCardService loanApplOutBankCardService;

    @Autowired
    private FinanceSourceService financeSourceService;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoanApplOutBankCardServiceImpl.class);

    @Override
    @Transactional
    public void save(String idCardNo, String bankCardNo) {
        //todo 逻辑坑，爱投资没有银行卡返回，只能根据身份证去找用户然后找爱投资贷款

        //找到所有爱投资贷款，没有银行卡的

        LoanApplOutUserPo loanApplOutUserPo = loanApplOutUserDao.findByOutUserIdAndType(idCardNo, EApplOutType.AITOUZI);

        List<FinanceSourceDto> financeSourceDtos = financeSourceService.getAll();
        FinanceSourceDto atzFinanceSourceDto = null;
        if(CollectionUtils.isNotEmpty(financeSourceDtos)){
            for (FinanceSourceDto financeSourceDto : financeSourceDtos) {
                if(financeSourceDto.getType() == EFinanceSourceType.AITOUZI){
                    atzFinanceSourceDto = financeSourceDto;
                    break;
                }
            }
        }

        List<LoanApplPo> applPos = loanApplDao.findByUserIdAndFinanceSourceId(loanApplOutUserPo.getUserId(), atzFinanceSourceDto.getId());

        Map<String, LoanApplPo> map = new HashMap<>();

        if(!applPos.isEmpty()){
            applPos.forEach(applPo -> map.put(applPo.getApplId(), applPo));
        }

        Set<String> applIds = map.keySet();

        if(applIds.size() > 0){
            List<LoanApplOutBankCardDto> list = loanApplOutBankCardService.findByApplIdIn(applIds);
            Set<String> targets = new HashSet<>();
            if(!list.isEmpty()){
                list.forEach(item -> targets.add(item.getApplId()));
            }

            applIds.removeAll(targets);

            //剩下的就是存在的爱投资贷款，银行卡没有的，则更新银行卡信息
            if(applIds.size() > 0){
                applIds.forEach(applId -> {
                    LOGGER.info("appl out bankcard changed,applId:{},bankcardNo:{}", applId, bankCardNo);
                    LoanApplOutBankCardPo po = new LoanApplOutBankCardPo();
                    LoanApplPo t = map.get(applId);
                    if(t != null){
                        po.setUserId(t.getUserId());
                    }
                    po.setApplId(applId);
                    po.setBankCardNo(bankCardNo);
                    outBankCardDao.save(po);
                });
            }
        }


    }

    @Override
    @Transactional
    public void save(LoanApplOutBankCardDto dto) {
        LoanApplOutBankCardPo po = ConverterService.convert(dto, LoanApplOutBankCardPo.class);
        outBankCardDao.save(po);
    }

    @Override
    public LoanApplOutBankCardDto findByApplId(String applId) {
        LoanApplOutBankCardPo po = outBankCardDao.findByApplId(applId);
        if(po == null){
            return null;
        }
        return ConverterService.convert(po, LoanApplOutBankCardDto.class);
    }

    @Override
    public LoanApplOutBankCardDto findByUserLatest(String userId) {
        LoanApplOutBankCardPo po = outBankCardDao.findByUserLatest(userId);
        if(po == null){
            return null;
        }
        return ConverterService.convert(po, LoanApplOutBankCardDto.class);
    }

    @Override
    public List<LoanApplOutBankCardDto> findByApplIdIn(Set<String> applIds) {
        List<LoanApplOutBankCardPo> list = outBankCardDao.findByApplIdIn(applIds);
        return ConverterService.convertToList(list, LoanApplOutBankCardDto.class);
    }
}
