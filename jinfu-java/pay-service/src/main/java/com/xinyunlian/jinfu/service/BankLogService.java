package com.xinyunlian.jinfu.service;

import com.xinyunlian.jinfu.dao.BankLogDao;
import com.xinyunlian.jinfu.domain.CommandRequest;
import com.xinyunlian.jinfu.domain.CommandResponse;
import com.xinyunlian.jinfu.entity.BankLogPo;
import com.xinyunlian.jinfu.enums.EMsgType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BankLogService {

    @Autowired
    private BankLogDao bankLogDao;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void save(CommandRequest commandRequest, String msgBody) {
        BankLogPo bankLogPo = new BankLogPo();
        bankLogPo.setTrxType(commandRequest.getTrxType());
        bankLogPo.setMsgType(EMsgType.REQ);
        bankLogPo.setMsgBody(msgBody);
        bankLogDao.save(bankLogPo);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void save(CommandResponse commandResponse, String msgBody) {
        BankLogPo bankLogPo = new BankLogPo();
        bankLogPo.setTrxType(commandResponse.getTrxType());
        bankLogPo.setMsgType(EMsgType.RESP);
        bankLogPo.setMsgBody(msgBody);
        bankLogPo.setRetCode(commandResponse.getRetCode());
        bankLogPo.setRetMsg(commandResponse.getRetMsg());
        bankLogDao.save(bankLogPo);
    }
}
