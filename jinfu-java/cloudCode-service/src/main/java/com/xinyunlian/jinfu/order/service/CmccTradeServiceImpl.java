package com.xinyunlian.jinfu.order.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.order.dao.CmccOrderInfoDao;
import com.xinyunlian.jinfu.order.dao.CmccTradeRecordDao;
import com.xinyunlian.jinfu.order.dto.CmccTradeRecordDto;
import com.xinyunlian.jinfu.order.entity.CmccOrderInfoPo;
import com.xinyunlian.jinfu.order.entity.CmccTradeRecordPo;
import com.xinyunlian.jinfu.order.enums.ECmccOrderTradeStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by menglei on 2016年11月22日.
 */
@Service
public class CmccTradeServiceImpl implements CmccTradeService {

    @Autowired
    private CmccTradeRecordDao cmccTradeRecordDao;
    @Autowired
    private CmccOrderInfoDao cmccOrderInfoDao;

    /**
     * @param storeList
     * 发起打款
     */
    @Transactional
    @Override
    public List<CmccTradeRecordDto> startTrade(List<Object[]> storeList, List<Object[]> bankList) throws BizServiceException {
        List<CmccTradeRecordDto> tradeList = new ArrayList<>();
        if (storeList.size() <= 0 || bankList.size() <=0) {
            return tradeList;
        }
        Map<String, CmccTradeRecordPo> bankMap = new HashMap<>();
        CmccTradeRecordPo bank;
        for (Object[] object : bankList) {
            bank = new CmccTradeRecordPo();
            bank.setStoreId(Long.valueOf(object[4].toString()));
            bank.setIdCardNo(object[0].toString());
            bank.setBankCardNo(object[1].toString());
            bank.setBankCardName(object[2].toString());
            bank.setBankCode(object[3].toString());
            bankMap.put(object[4].toString(), bank);
        }
        for (Object[] object : storeList) {
            //插入打款流水表
            CmccTradeRecordPo cmccTradeRecordPo = new CmccTradeRecordPo();
            cmccTradeRecordPo.setStoreId(Long.valueOf(object[0].toString()));
            cmccTradeRecordPo.setAmount(BigDecimal.valueOf(Double.valueOf(object[1].toString())));
            cmccTradeRecordPo.setTradeStatus(ECmccOrderTradeStatus.FAILED);
            //获取商户银行卡信息存入流水表
            CmccTradeRecordPo bankInfo = bankMap.get(object[0].toString());
            if (bankInfo == null) {
                continue;
            }
            cmccTradeRecordPo.setIdCardNo(bankInfo.getIdCardNo());
            cmccTradeRecordPo.setBankCardNo(bankInfo.getBankCardNo());
            cmccTradeRecordPo.setBankCardName(bankInfo.getBankCardName());
            cmccTradeRecordPo.setBankCode(bankInfo.getBankCode());
            cmccTradeRecordPo = cmccTradeRecordDao.save(cmccTradeRecordPo);
            CmccTradeRecordDto cmccTradeRecordDto = ConverterService.convert(cmccTradeRecordPo, CmccTradeRecordDto.class);
            tradeList.add(cmccTradeRecordDto);
            //满足打款条件的订单
            List<CmccOrderInfoPo> orderInfoList = cmccOrderInfoDao.findByStoreId(Long.valueOf(object[0].toString()));
            List<CmccOrderInfoPo> orderList = new ArrayList<>();
            for (CmccOrderInfoPo po : orderInfoList) {
                po.setCmccTradeNo(cmccTradeRecordPo.getCmccTradeNo());
                po.setTradeStatus(ECmccOrderTradeStatus.FAILED);
                orderList.add(po);
            }
            //更新中移积分订单
            cmccOrderInfoDao.save(orderList);
        }
        //返回流水列表
        return tradeList;
    }

    /**
     * 更新流水状态
     *
     * @param cmccTradeRecordDto
     */
    @Transactional
    @Override
    public void updateTradeStatus(CmccTradeRecordDto cmccTradeRecordDto, ECmccOrderTradeStatus tradeStatus) throws BizServiceException {
        CmccTradeRecordPo cmccTradeRecordPo = cmccTradeRecordDao.findOne(cmccTradeRecordDto.getCmccTradeNo());
        //更新打款流水
        cmccTradeRecordPo.setTradeStatus(tradeStatus);
        cmccTradeRecordPo = cmccTradeRecordDao.save(cmccTradeRecordPo);
        //更新中移积分订单
        cmccOrderInfoDao.updateTradeStatus(tradeStatus.getCode(), cmccTradeRecordPo.getCmccTradeNo());
    }

    /**
     * 根据状态查流水
     *
     * @param tradeStatus
     * @return
     * @throws BizServiceException
     */
    @Override
    public List<CmccTradeRecordDto> findTradeList(ECmccOrderTradeStatus tradeStatus) throws BizServiceException {
        List<CmccTradeRecordPo> poList = cmccTradeRecordDao.findByTradeStatus(tradeStatus);
        List<CmccTradeRecordDto> dtoList = new ArrayList<>();
        for (CmccTradeRecordPo po : poList) {
            CmccTradeRecordDto dto = ConverterService.convert(po, CmccTradeRecordDto.class);
            dto.setCreateTime(DateHelper.formatDate(po.getCreateTs(), "yyyy-MM-dd HH:mm:ss"));
            dtoList.add(dto);
        }
        return dtoList;
    }
}
