package com.xinyunlian.jinfu.finaccbankcard.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.finaccbankcard.dto.FinAccBankCardDto;
import com.xinyunlian.jinfu.finaccbankcard.dto.FinAccBankCardSearchDto;
import com.xinyunlian.jinfu.finaccbankcard.enums.EFinOrg;

import java.util.List;

/**
 * Created by dongfangchao on 2016/11/21.
 */
public interface FinAccBankCardService {

    /**
     * 根据外部理财账号查询账号信息
     * @param extTxAccId
     * @return
     */
    FinAccBankCardDto getFinAccBankCardByExtTxAccId(String extTxAccId) throws BizServiceException;

    /**
     * 新增开户信息
     * @param dto
     */
    FinAccBankCardDto addFinAccBankCard(FinAccBankCardDto dto);

    /**
     * 根据银行卡号和用户id查找
     * @param bankCardNo
     * @param userId
     * @param finOrg
     * @return
     */
    FinAccBankCardDto getFinAccBankCardByBankCardNoUserId(String bankCardNo, String userId, EFinOrg finOrg);

    /**
     * 查询用户账号列表
     * @param searchDto
     * @return
     */
    List<FinAccBankCardDto> getFinAccBankCardList(FinAccBankCardSearchDto searchDto) throws BizServiceException;

    /**
     * 更新理财账号信息
     * @param dto
     */
    void updateFinAccBankCard(FinAccBankCardDto dto) throws BizServiceException;

    /**
     * 删除理财账号
     * @param id
     * @throws BizServiceException
     */
    void deleteFinAccBankCard(String id) throws BizServiceException;

    /**
     * 检查指定卡号的银行卡是否在理财账户中开户
     * @param bankCardNo
     * @return true：存在该开户卡，false：不存在该开户卡
     * @throws BizServiceException
     */
    Boolean checkBankCardExist(String bankCardNo) throws BizServiceException;

}
