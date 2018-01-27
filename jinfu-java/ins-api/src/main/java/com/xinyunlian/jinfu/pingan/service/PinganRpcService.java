package com.xinyunlian.jinfu.pingan.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.pingan.dto.*;

import java.util.List;

/**
 * Created by dongfangchao on 2017/6/12/0012.
 */
public interface PinganRpcService {

    /**
     * 获取所有的保额等级
     * @return
     * @throws BizServiceException
     */
    List<PinganInsuredGradeDto> getAllInsuredGrade() throws BizServiceException;

    /**
     * 查询指定地区是不是台风控制区
     * @param regionId
     * @return
     * @throws BizServiceException
     */
    Boolean isTyphoonControlRegion(Long regionId) throws BizServiceException;

    /**
     * 根据地区级别和国标码获取平安的地区信息
     * @param gbCode
     * @param level
     * @return
     * @throws BizServiceException
     */
    PinganRegionDto getPinganRegion(String gbCode, Integer level) throws BizServiceException;

    /**
     * 根据父节点获取所有子节点
     * @param parent
     * @return
     * @throws BizServiceException
     */
    List<PinganRegionDto> getPinganRegionList(Long parent) throws BizServiceException;

    /**
     * 获取平安报价
     * @param insuredGradeCode
     * @param regionId
     * @return
     * @throws BizServiceException
     */
    PinganQuotePriceRetDto getPinganQuotePrice(String insuredGradeCode, Long regionId) throws BizServiceException;

    /**
     * 保存订单用户信息
     * @param dto
     * @return
     * @throws BizServiceException
     */
    PreOrderUserInfoDto addPreOrderUserInfo(PreOrderUserInfoDto dto) throws BizServiceException;

    /**
     * 投保
     * @param preOrderUserInfoDto
     * @return 保单详情
     * @throws BizServiceException
     */
    QunarApplyResultDto qunarApply(PreOrderUserInfoDto preOrderUserInfoDto) throws BizServiceException;

    /**
     * 从平安拉取电子保单
     * @param perInsuranceOrderNo
     * @throws BizServiceException
     */
    void pasElectronicPolicy(String perInsuranceOrderNo) throws BizServiceException;

    /**
     * 获取下单信息
     * @param preInsOrderNo
     * @return
     * @throws BizServiceException
     */
    PreOrderUserInfoDto getPreOrderUserInfo(String preInsOrderNo) throws BizServiceException;

    /**
     * 获取平安token
     * @param refreshToken
     * @return
     * @throws BizServiceException
     */
    String getPinganOauthToken(Boolean refreshToken) throws BizServiceException;

    void pasElectronicPolicyAll() throws BizServiceException;

    /**
     * 更新用户订单详情
     * @param dto
     * @return
     * @throws BizServiceException
     */
    PreOrderUserInfoDto updatePreOrderUserInfo(PreOrderUserInfoDto dto) throws BizServiceException;
}
