package com.xinyunlian.jinfu.order.service;

import com.xinyunlian.jinfu.cmcc.service.MskjService;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.order.dao.CmccOrderInfoDao;
import com.xinyunlian.jinfu.order.dto.CmccOrderDto;
import com.xinyunlian.jinfu.order.dto.CmccOrderInfoDto;
import com.xinyunlian.jinfu.order.entity.CmccOrderInfoPo;
import com.xinyunlian.jinfu.order.enums.ECmccOrderSource;
import com.xinyunlian.jinfu.order.enums.ECmccOrderPayStatus;
import com.xinyunlian.jinfu.order.enums.ECmccOrderTradeStatus;
import com.xinyunlian.jinfu.user.dao.CmccUserInfoDao;
import com.xinyunlian.jinfu.user.entity.CmccUserInfoPo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by menglei on 2016年11月20日.
 */
@Service
public class CmccOrderServiceImpl implements CmccOrderService {

    @Autowired
    private MskjService mskjService;
    @Autowired
    private CmccOrderInfoDao cmccOrderInfoDao;
    @Autowired
    private CmccUserInfoDao cmccUserInfoDao;

    /**
     * 查询手机号电子券余额，发送余额短信
     *
     * @param cmccOrderDto
     * @return
     * @throws BizServiceException
     */
    @Override
    public Map<String, String> queryBalance(CmccOrderDto cmccOrderDto) throws BizServiceException {
        if (cmccOrderDto.getProvinceId().equals("2279")) {//四川省
            Map<String, String> resultMap = mskjService.getQueryBalance(cmccOrderDto.getMobile());
            return resultMap;
        } else {
            throw new BizServiceException(EErrorCode.CLOUDCODE_CMCC_DISTRICT_ERROR);
        }
    }

    /**
     * 下单，发验证码
     *
     * @param cmccOrderDto
     * @return
     */
    @Transactional
    @Override
    public Map<String, String> saveOrder(CmccOrderDto cmccOrderDto) throws BizServiceException {
        if (cmccOrderDto.getProvinceId().equals("2279")) {//四川省
            //生成订单
            CmccOrderInfoDto cmccOrderInfoDto = new CmccOrderInfoDto();
            cmccOrderInfoDto.setAmount(BigDecimal.valueOf(Double.valueOf(cmccOrderDto.getAmount())));
            cmccOrderInfoDto.setCouponAmount(BigDecimal.valueOf(Double.valueOf(cmccOrderDto.getCouponAmount())));
            cmccOrderInfoDto.setCoupon(BigDecimal.valueOf(Double.valueOf(cmccOrderDto.getCoupon())));
            cmccOrderInfoDto.setPayStatus(ECmccOrderPayStatus.NOPAYMENT);
            cmccOrderInfoDto.setTradeStatus(ECmccOrderTradeStatus.NOPAYMENT);
            cmccOrderInfoDto.setSource(ECmccOrderSource.MSKJ);
            cmccOrderInfoDto.setMobile(cmccOrderDto.getMobile());
            cmccOrderInfoDto.setStoreId(Long.valueOf(cmccOrderDto.getStoreId()));
            cmccOrderInfoDto = saveCmccOrderInfo(cmccOrderInfoDto);
            //发送请求
            Map<String, String> resultMap = mskjService.getDirectPay(cmccOrderInfoDto.getCmccOrderNo(), cmccOrderDto.getMobile(), cmccOrderDto.getCoupon(), cmccOrderDto.getStoreId(), cmccOrderDto.getStoreName(), cmccOrderDto.getStoreAddress());//总金额需转格式
            if (!resultMap.get("retCode").equals("0000")) {//接口出错
                return resultMap;
            }
            //更新订单
            CmccOrderInfoPo cmccOrderInfoPo = cmccOrderInfoDao.findOne(cmccOrderInfoDto.getCmccOrderNo());
            cmccOrderInfoPo.setOutTradeNo(resultMap.get("tradeNo"));
            cmccOrderInfoDao.save(cmccOrderInfoPo);
            return resultMap;
        } else {
            throw new BizServiceException(EErrorCode.CLOUDCODE_CMCC_DISTRICT_ERROR);
        }
    }

    /**
     * 确认订单
     *
     * @return
     * @throws BizServiceException
     */
    @Transactional
    @Override
    public Map<String, String> confirmOrder(CmccOrderDto cmccOrderDto) throws BizServiceException {
        if (cmccOrderDto.getProvinceId().equals("2279")) {//四川省
            CmccOrderInfoPo cmccOrderInfoPo = cmccOrderInfoDao.findOne(cmccOrderDto.getOrderNo());
            if (cmccOrderInfoPo == null || StringUtils.isEmpty(cmccOrderInfoPo.getOutTradeNo())) {
                throw new BizServiceException(EErrorCode.CLOUDCODE_CMCC_ORDER_NOT_FOUND);
            } else if (!cmccOrderInfoPo.getPayStatus().equals(ECmccOrderPayStatus.NOPAYMENT)) {
                throw new BizServiceException(EErrorCode.CLOUDCODE_CMCC_ORDER_NOT_FOUND);
            }
            //发送请求
            Map<String, String> resultMap = mskjService.getDirectConf(cmccOrderInfoPo.getCmccOrderNo(), cmccOrderInfoPo.getOutTradeNo(), String.valueOf(cmccOrderInfoPo.getCoupon()), cmccOrderDto.getVerifyCode(), cmccOrderDto.getStoreId());//总金额需转格式
            if (!resultMap.get("retCode").equals("0000")) {//接口出错
                Map<String, String> rt = mskjService.getQuery(cmccOrderInfoPo.getCmccOrderNo(), cmccOrderInfoPo.getOutTradeNo());
                if (!rt.get("retCode").equals("0000") || !rt.get("orderState").equals("1")) {//接口出错
                    return resultMap;
                }
            }
            //用户处理
            CmccUserInfoPo cmccUserInfoPo = cmccUserInfoDao.findByMobile(cmccOrderInfoPo.getMobile());
            if (cmccUserInfoPo == null) {
                CmccUserInfoPo userPo = new CmccUserInfoPo();
                userPo.setMobile(cmccOrderInfoPo.getMobile());
                cmccUserInfoPo = cmccUserInfoDao.save(userPo);
            }
            cmccOrderInfoPo.setPayStatus(ECmccOrderPayStatus.SUCCESS);
            cmccOrderInfoPo.setUserId(cmccUserInfoPo.getUserId());
            cmccOrderInfoDao.save(cmccOrderInfoPo);
            return resultMap;
        } else {
            throw new BizServiceException(EErrorCode.CLOUDCODE_CMCC_DISTRICT_ERROR);
        }
    }

    @Override
    public Map<String, String> queryVouchers(CmccOrderDto cmccOrderDto) throws BizServiceException {
        if (cmccOrderDto.getProvinceId().equals("2279")) {//四川省
            //发送请求
            Map<String, String> resultMap = mskjService.getQueryVouchers(cmccOrderDto.getCouponAmount());//总金额需转格式
            return resultMap;
        } else {
            throw new BizServiceException(EErrorCode.CLOUDCODE_CMCC_DISTRICT_ERROR);
        }
    }

    /**
     * 生成，更新订单
     *
     * @param cmccOrderInfoDto
     * @return
     * @throws BizServiceException
     */
    @Transactional
    @Override
    public CmccOrderInfoDto saveCmccOrderInfo(CmccOrderInfoDto cmccOrderInfoDto) {
        CmccOrderInfoPo po = ConverterService.convert(cmccOrderInfoDto, CmccOrderInfoPo.class);
        cmccOrderInfoDao.save(po);
        CmccOrderInfoDto dto = ConverterService.convert(po, CmccOrderInfoDto.class);
        return dto;
    }

    /**
     *
     * @param type 成交额>500:turnover 所有成交额:month
     * @return
     */
    @Override
    public List<Object[]> getOrderList(String type) {
        List<Object[]> storeList = new ArrayList<>();
        if (type.equals("turnover")) {
            storeList = cmccOrderInfoDao.findByTurnover();
        } else if (type.equals("month")) {
            storeList = cmccOrderInfoDao.findByMonth();
        }
        return storeList;
    }

    @Override
    @Transactional
    public CmccOrderInfoDto getOrderByOrderNo(String orderNo) {
        CmccOrderInfoPo po = cmccOrderInfoDao.findOne(orderNo);
        if (po == null) {
            return null;
        }
        CmccOrderInfoDto dto = ConverterService.convert(po, CmccOrderInfoDto.class);
        return dto;
    }

}
