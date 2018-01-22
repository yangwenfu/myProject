package com.xinyunlian.jinfu.router.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.router.dto.FinanceSourceConfigDto;
import com.xinyunlian.jinfu.router.dto.FinanceSourceDto;
import com.xinyunlian.jinfu.router.dto.FinanceSourceLoanDto;
import com.xinyunlian.jinfu.router.enums.EFinanceSourceType;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author willwang
 */
public interface FinanceSourceService {

    /**
     * 获得一个有效的资金路由配置
     * @return
     */
    FinanceSourceDto getActive(FinanceSourceLoanDto financeSourceLoanDto) throws BizServiceException;

    /**
     * 获取自有资金的路由配置
     * @return
     */
    FinanceSourceDto getOwn(FinanceSourceLoanDto financeSourceLoanDto) throws BizServiceException;

    /**
     * 根据资金路由编号获取资金路由信息
     * 如果编号为空，会默认返回自有资金类型
     */
    FinanceSourceDto findById(Integer id) throws BizServiceException;

    /**
     * 获取全部资金路由配置
     * @return
     */
    List<FinanceSourceDto> getAll() throws BizServiceException;


    Map<Integer, FinanceSourceDto> getAllMap()  throws BizServiceException;

    /**
     * 获取资金路由配置
     * @param id
     * @return
     * @throws BizServiceException
     */
    FinanceSourceConfigDto getConfig(Integer id) throws BizServiceException;

    /**
     * 通知资金路由端进行金额撤销
     * @param applId
     * @throws BizServiceException
     */
    void revertAmt(String applId) throws BizServiceException;
}
