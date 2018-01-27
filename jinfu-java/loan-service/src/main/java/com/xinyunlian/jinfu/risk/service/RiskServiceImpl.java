package com.xinyunlian.jinfu.risk.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.risk.dao.RiskUserInfoDao;
import com.xinyunlian.jinfu.risk.dao.RiskUserOrderDao;
import com.xinyunlian.jinfu.risk.dto.RiskUserInfoDto;
import com.xinyunlian.jinfu.risk.dto.RiskUserOrderDto;
import com.xinyunlian.jinfu.risk.entity.RiskUserInfoPo;
import com.xinyunlian.jinfu.risk.entity.RiskUserOrderPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author willwang
 */
@Service
public class RiskServiceImpl implements RiskService {

    @Autowired
    private RiskUserInfoDao riskUserInfoDao;

    @Autowired
    private RiskUserOrderDao riskUserOrderDao;

    @Override
    @Transactional
    public void addUser(RiskUserInfoDto riskUserInfoDto) {
        riskUserInfoDao.deleteByUserId(riskUserInfoDto.getUserId());

        RiskUserInfoPo riskUserInfoPoNew = ConverterService.convert(riskUserInfoDto, RiskUserInfoPo.class);

        riskUserInfoDao.save(riskUserInfoPoNew);
    }

    @Override
    @Transactional
    public void addOrder(List<RiskUserOrderDto> riskUserOrderDtos) {
        if(riskUserOrderDtos.size() > 0){

            String userId = riskUserOrderDtos.get(0).getUserId();

            riskUserOrderDao.deleteByUserId(userId);

            for (RiskUserOrderDto riskUserOrderDto : riskUserOrderDtos) {
                RiskUserOrderPo riskUserOrderPo = ConverterService.convert(riskUserOrderDto, RiskUserOrderPo.class);
                riskUserOrderDao.save(riskUserOrderPo);
            }

        }
    }

    @Override
    public boolean isAuth(String userId) {
        return riskUserInfoDao.countByUserId(userId) > 0 && riskUserOrderDao.countByUserId(userId) > 0;
    }
}
