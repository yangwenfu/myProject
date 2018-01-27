package com.xinyunlian.jinfu.paycode.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.common.util.BizUtil;
import com.xinyunlian.jinfu.common.util.EncryptUtil;
import com.xinyunlian.jinfu.payCode.dto.PayCodeBalanceLogDto;
import com.xinyunlian.jinfu.payCode.dto.PayCodeBalanceLogSearchDto;
import com.xinyunlian.jinfu.payCode.dto.PayCodeDto;
import com.xinyunlian.jinfu.payCode.dto.PayCodeSearchDto;
import com.xinyunlian.jinfu.payCode.enums.PayCodeBalanceType;
import com.xinyunlian.jinfu.payCode.enums.PayCodeStatus;
import com.xinyunlian.jinfu.payCode.service.PayCodeService;
import com.xinyunlian.jinfu.paycode.dao.PayCodeBalanceLogDao;
import com.xinyunlian.jinfu.paycode.dao.PayCodeDao;
import com.xinyunlian.jinfu.paycode.entity.PayCodeBalanceLogPo;
import com.xinyunlian.jinfu.paycode.entity.PayCodePo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by carrot on 2017/8/28.
 */
@Service
public class PayCodeServiceImpl implements PayCodeService {

    @Autowired
    PayCodeDao payCodeDao;

    @Autowired
    PayCodeBalanceLogDao payCodeBalanceLogDao;

    @Transactional
    @Override
    public PayCodeDto save(PayCodeDto payCodeDto) throws Exception {
        if (payCodeDto == null)
            return null;
        Long count = payCodeDao.countByPayCodeNoOrMobile(payCodeDto.getPayCodeNo(), payCodeDto.getMobile());
        if (count > 0)
            return null;
        String payCodeNo = payCodeDto.getPayCodeNo();
        PayCodePo payCodePo = payCodeDao.findByPayCodeNo(payCodeNo);
        if (payCodePo == null) {
            payCodePo = new PayCodePo();
            payCodePo.setCreateTs(new Date());
        } else if (payCodePo.getStatus() != PayCodeStatus.DISABLE)
            throw new Exception("支付码编号已被使用");
        ConverterService.convert(payCodeDto, payCodePo);
        payCodePo.setBalance(BigDecimal.ZERO);
        payCodePo.setStatus(PayCodeStatus.ENABLE);
        payCodePo.setPayCodeUrl(
                AppConfigUtil.getConfig("paycode.qrcode.url") + "/pay/" + payCodeNo + "/" + EncryptUtil.encryptMd5("pay" + payCodeNo).substring(0, 3));
        payCodeDao.save(payCodePo);
        return ConverterService.convert(payCodePo, PayCodeDto.class);
    }

    @Override
    public PayCodeDto get(String payCodeNo) {
        PayCodePo payCodePo = payCodeDao.findByPayCodeNo(payCodeNo);
        PayCodeDto payCodeDto = ConverterService.convert(payCodePo, PayCodeDto.class);
        if (payCodeDto != null)
            payCodeDto.setTotalPay(payCodeBalanceLogDao.sumByPayCodeNo(payCodeNo));
        return payCodeDto;
    }


    @Transactional
    @Override
    public PayCodeDto balance(String payCodeNo, BigDecimal amount, PayCodeBalanceType type, String serialNumber) throws Exception {
        //防止支付金额小于等于0
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new Exception("金额不合法");

        if (type == null)
            throw new Exception("未设置现金流操作类型");

        PayCodePo payCodePo = payCodeDao.findByPayCodeNo(payCodeNo);
        if (payCodePo == null) {
            payCodePo = payCodeDao.findByPayCodeUrl(payCodeNo);
        }

        if (payCodePo == null)
            throw new Exception("不存在的支付码");

        if (!payCodePo.getStatus().equals(PayCodeStatus.ENABLE))
            throw new Exception(String.format("支付码状态异常：%s，请联系客服", payCodePo.getStatus().getText()));

        if (type == PayCodeBalanceType.PAY) {
            if (payCodePo.getBalance().compareTo(amount) < 0)
                throw new Exception("支付金额超出余额");
            payCodePo.setBalance(payCodePo.getBalance().subtract(amount));
        } else if (type == PayCodeBalanceType.RECHARGE)
            payCodePo.setBalance(payCodePo.getBalance().add(amount));

            //保存日志
        PayCodeBalanceLogPo logPo = new PayCodeBalanceLogPo();
        logPo.setPayCodeNo(payCodeNo);
        logPo.setMobile(payCodePo.getMobile());
        logPo.setAmount(amount);
        logPo.setType(type);
        logPo.setSerialNumber(serialNumber);
        payCodeBalanceLogDao.save(logPo);
        return ConverterService.convert(payCodePo, PayCodeDto.class);
    }

    @Override
    public PayCodeSearchDto list(PayCodeSearchDto searchDto) {
        Specification<PayCodePo> specification = (root, criteriaQuery, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();

            if (StringUtils.isNotEmpty(searchDto.getPayCodeNo())) {
                expressions.add(criteriaBuilder.like(root.get("payCodeNo"), BizUtil.filterString(searchDto.getPayCodeNo())));
            }

            if (StringUtils.isNotEmpty(searchDto.getMobile())) {
                expressions.add(criteriaBuilder.like(root.get("mobile"), BizUtil.filterString(searchDto.getMobile())));
            }

            if (StringUtils.isNotEmpty(searchDto.getPayCodeUrl())) {
                expressions.add(criteriaBuilder.equal(root.get("payCodeUrl"), searchDto.getPayCodeUrl()));
            }

            expressions.add(criteriaBuilder.notEqual(root.get("status"), PayCodeStatus.DISABLE));
            return predicate;
        };

        Pageable pageable = new PageRequest(searchDto.getCurrentPage() - 1, searchDto.getPageSize(), Sort.Direction.DESC, "createTs");
        Page<PayCodePo> page = payCodeDao.findAll(specification, pageable);

        searchDto.setTotalPages(page.getTotalPages());
        searchDto.setTotalRecord(page.getTotalElements());
        searchDto.setList(ConverterService.convertToList(page.getContent(), PayCodeDto.class));
        return searchDto;
    }

    @Override
    public PayCodeBalanceLogSearchDto logList(PayCodeBalanceLogSearchDto searchDto) {
        Specification<PayCodeBalanceLogPo> specification = (root, criteriaQuery, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();

            if (StringUtils.isNotEmpty(searchDto.getPayCodeNo())) {
                expressions.add(criteriaBuilder.equal(root.get("payCodeNo"), searchDto.getPayCodeNo()));
            }

            if (StringUtils.isNotEmpty(searchDto.getMobile())) {
                expressions.add(criteriaBuilder.equal(root.get("mobile"), searchDto.getMobile()));
            }

            if (searchDto.getType() != null) {
                expressions.add(criteriaBuilder.equal(root.get("type"), searchDto.getType()));
            }
            return predicate;
        };

        Pageable pageable = new PageRequest(searchDto.getCurrentPage() - 1, searchDto.getPageSize(), Sort.Direction.ASC, "lastMntTs");
        Page<PayCodeBalanceLogPo> page = payCodeBalanceLogDao.findAll(specification, pageable);

        searchDto.setTotalPages(page.getTotalPages());
        searchDto.setTotalRecord(page.getTotalElements());
        searchDto.setList(ConverterService.convertToList(page.getContent(), PayCodeBalanceLogDto.class));
        return searchDto;
    }

    @Transactional
    @Override
    public PayCodeDto updateStatus(String payCodeNo, PayCodeStatus status) {
        PayCodePo payCodePo = payCodeDao.findByPayCodeNo(payCodeNo);
        if (payCodePo != null)
            payCodePo.setStatus(status);
        if (status == PayCodeStatus.DISABLE)
            payCodePo.setBalance(BigDecimal.ZERO);
        return ConverterService.convert(payCodePo, PayCodeDto.class);
    }
}
