package com.xinyunlian.jinfu.insurance.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.insurance.dto.InsureCallBackRespDto;
import com.xinyunlian.jinfu.insurance.dto.PerInsInfoSearchDto;
import com.xinyunlian.jinfu.insurance.dto.PerInsuranceInfoDto;
import com.xinyunlian.jinfu.insurance.dto.PerInsuranceInfoPageDto;
import com.xinyunlian.jinfu.insurance.enums.EInvokerType;
import com.xinyunlian.jinfu.pingan.dto.PolicyDto;
import com.xinyunlian.jinfu.zhongan.dto.VehInsNotifyDto;
import com.xinyunlian.jinfu.zhongan.dto.ZhongAnRequestDto;

import java.util.List;
import java.util.Map;

/**
 * Created by DongFC on 2016-09-21.
 */
public interface InsuranceOrderService {

    /**
     * 加密用户信息并返回url
     * @param policyDto
     * @param invokerType
     * @return 跳转平安的url地址
     */
    String getPinganUrl(PolicyDto policyDto, EInvokerType invokerType) throws BizServiceException;

    /**
     * 新增保险订单记录
     * @param perInsuranceInfoDto
     * @return 订单号
     * @throws BizServiceException
     */
    String addInsOrderInfo(PerInsuranceInfoDto perInsuranceInfoDto) throws BizServiceException;

    /**
     * 根据条件获取订单列表
     * @param perInsInfoSearchDto
     * @return
     * @throws BizServiceException
     */
    List<PerInsuranceInfoDto> getInsOrder(PerInsInfoSearchDto perInsInfoSearchDto) throws BizServiceException;

    /**
     * 更新保单记录
     * @param dto
     * @return
     * @throws BizServiceException
     */
    InsureCallBackRespDto updateInsOrderByOrderId(PerInsuranceInfoDto dto) throws BizServiceException;

    /**
     * 分页查询保单记录
     * @param perInsInfoSearchDto
     * @return
     * @throws BizServiceException
     */
    PerInsuranceInfoPageDto getInsOrderPage(PerInsInfoSearchDto perInsInfoSearchDto) throws BizServiceException;

    /**
     * 根据订单编号查订单
     * @param orderId
     * @return
     * @throws BizServiceException
     */
    PerInsuranceInfoDto getInsOrderByOrderId(String orderId) throws BizServiceException;

    /**
     * 超过七个工作日的保单置为失败，为定时任务服务
     * @throws BizServiceException
     */
    void updateExpiryInsOrder() throws BizServiceException;

    /**
     * 获取众安跳转url
     * @param reqDto
     * @param invokerType
     * @return
     */
    String getZhonganUrl(ZhongAnRequestDto reqDto, EInvokerType invokerType);

    /**
     * 更新众安的保单信息
     * @param infoDto
     * @throws BizServiceException
     */
    boolean updateVehInsOrderByOrderId(PerInsuranceInfoDto infoDto) throws BizServiceException;

    /**
     * 解析众安回传
     * @param map
     * @return
     * @throws BizServiceException
     */
    VehInsNotifyDto getZhonganData(Map<String, String[]> map) throws BizServiceException;

    /**
     * 更新保单信息
     * @param infoDto
     * @throws BizServiceException
     */
    PerInsuranceInfoDto updateInsOrder(PerInsuranceInfoDto infoDto) throws BizServiceException;

    /**
     * 新增保险订单记录
     * @param perInsuranceInfoDto
     * @return 订单对象
     * @throws BizServiceException
     */
    PerInsuranceInfoDto addAndReturnInsOrderInfo(PerInsuranceInfoDto perInsuranceInfoDto) throws BizServiceException;
}
