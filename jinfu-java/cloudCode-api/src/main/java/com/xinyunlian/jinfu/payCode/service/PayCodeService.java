package com.xinyunlian.jinfu.payCode.service;

import com.xinyunlian.jinfu.payCode.dto.PayCodeBalanceLogSearchDto;
import com.xinyunlian.jinfu.payCode.dto.PayCodeDto;
import com.xinyunlian.jinfu.payCode.dto.PayCodeSearchDto;
import com.xinyunlian.jinfu.payCode.enums.PayCodeBalanceType;
import com.xinyunlian.jinfu.payCode.enums.PayCodeStatus;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;

/**
 * Created by carrot on 2017/8/28.
 */
public interface PayCodeService {

    /**
     * 新增支付编码
     *
     * @param payCodeDto
     * @return
     */
    PayCodeDto save(PayCodeDto payCodeDto) throws Exception;

    /**
     * 支付编码查询
     *
     * @param payCodeNo
     * @return
     */
    PayCodeDto get(String payCodeNo);


    /**
     * 支付编码消费/充值
     *
     * @param payCodeNo
     * @param amount
     * @return
     */
    PayCodeDto balance(String payCodeNo, BigDecimal amount, PayCodeBalanceType type, String serialNumber) throws Exception;

    /**
     * 支付编码查询
     *
     * @param searchDto
     * @return
     */
    PayCodeSearchDto list(PayCodeSearchDto searchDto);

    /**
     * 支付编码操作日志查询
     *
     * @param searchDto
     * @return
     */
    PayCodeBalanceLogSearchDto logList(PayCodeBalanceLogSearchDto searchDto);


    /**
     * 更新支付编码状态（冻结，启用，退卡）
     *
     * @param payCodeNo
     * @param status
     * @return
     */
    PayCodeDto updateStatus(String payCodeNo, PayCodeStatus status);

}
