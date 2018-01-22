package com.xinyunlian.jinfu.bank.service;

import com.xinyunlian.jinfu.bank.dto.BankCardBinDto;
import com.xinyunlian.jinfu.bank.dto.BankCardDto;
import com.xinyunlian.jinfu.bank.dto.BankInfDto;
import com.xinyunlian.jinfu.common.exception.BizServiceException;

import java.util.List;

/**
 * 银行Service
 * @author KimLL
 * @version 
 */

public interface BankService {

    /**
     * 保存银行卡
     * @param bankCardDto
     * @return
     */
    BankCardDto saveBankCard(BankCardDto bankCardDto) throws BizServiceException;

    /**
     * 添加银行卡（会更新用户姓名和身份证号）
     * @param bankCardDto
     * @return
     */
    BankCardDto saveWithUserName(BankCardDto bankCardDto) throws BizServiceException;

    BankCardDto save(BankCardDto bankCardDto) throws BizServiceException;

    /**
     * 获取银行卡信息
     * @param bankCardId
     * @return
     */
    BankCardDto getBankCard(Long bankCardId);

    /**
     * 删除银行卡
     * @param bankCardId
     */
    void deleteBankCard(Long bankCardId);

    /**
     * 查询某用户下的银行卡
     * @param userId
     * @return
     */
    List<BankCardDto> findByUserId(String userId);

    Long countByUserId(String userId);

    /**
     * 查询某用户下支持代扣的银行卡数量
     * @param userId
     * @return
     */
    Long countByUserIdSupport(String userId);

    /**
     * 银行信息
     * @return
     */
    List<BankInfDto> findBankInfAll();

    /**
     * 查询支持代收付的
     * @return
     */
    List<BankInfDto> findBySupport();

    void save(List<BankCardBinDto> bankCardBinDtos);

    /**
     * 匹配卡bin信息
     * @param cardNo
     * @return
     */
    BankCardBinDto findByNumLengthAndBin(String cardNo);

    /**
     * 通过店铺查找绑定的云码银行卡
     * @param storeIds
     * @return
     */
    List<Object[]> findByStoreIds(List<Long> storeIds);

    /**
     * 根据bank code获取银行信息
     * @param bankCode
     * @return
     */
    List<BankInfDto> findByBankCode(String bankCode);

    /**
     * 获取银行信息
     * @param bankId
     * @return
     */
    BankInfDto getBank(Long bankId);

    /**
     * 根据userId和银行卡号查询银行卡信息
     * @param userId
     * @param bankCardNo
     * @return
     */
    BankCardDto getBankCard(String userId, String bankCardNo);

}
