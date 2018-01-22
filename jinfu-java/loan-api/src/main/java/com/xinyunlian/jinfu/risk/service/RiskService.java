package com.xinyunlian.jinfu.risk.service;


import com.xinyunlian.jinfu.risk.dto.RiskUserInfoDto;
import com.xinyunlian.jinfu.risk.dto.RiskUserOrderDto;

import java.util.List;

/**
 * @author willwang
 */
public interface RiskService {

    /**
     * 新增风险查询中的商户信息
     * @param riskUserInfoDto
     */
    void addUser(RiskUserInfoDto riskUserInfoDto);

    /**
     * 新增风险查询中的订单信息
     * @param riskUserOrderDtos
     */
    void addOrder(List<RiskUserOrderDto> riskUserOrderDtos);

    boolean isAuth(String userId);

}
