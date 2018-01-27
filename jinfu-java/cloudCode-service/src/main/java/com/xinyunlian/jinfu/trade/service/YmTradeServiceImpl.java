package com.xinyunlian.jinfu.trade.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.entity.id.Context;
import com.xinyunlian.jinfu.common.entity.id.IdUtil;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.ApplicationContextUtil;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.trade.dao.YmTradeDao;
import com.xinyunlian.jinfu.trade.dto.TradeTotal;
import com.xinyunlian.jinfu.trade.dto.YmTradeDayDto;
import com.xinyunlian.jinfu.trade.dto.YmTradeDto;
import com.xinyunlian.jinfu.trade.dto.YmTradeSearchDto;
import com.xinyunlian.jinfu.trade.entity.YmTradePo;
import com.xinyunlian.jinfu.trade.enums.EBizCode;
import com.xinyunlian.jinfu.trade.enums.ETradeStatus;
import com.xinyunlian.jinfu.trade.seqProducer.PartnerOrderNoSeqProducer;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 云码流水表ServiceImpl
 *
 * @author jll
 */

@Service
public class YmTradeServiceImpl implements YmTradeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YmTradeServiceImpl.class);

    @Autowired
    private YmTradeDao ymTradeDao;
    @PersistenceContext
    private EntityManager em;


    @Override
    @Transactional(readOnly = true)
    public TradeTotal getTradeTotal(YmTradeSearchDto searchDto) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<TradeTotal> query = cb.createQuery(TradeTotal.class);
        Root<YmTradePo> root = query.from(YmTradePo.class);
        query.select(cb.construct(TradeTotal.class
                , cb.count(root.<Long>get("id")).alias("tradeCount")
                , cb.sum(root.<BigDecimal>get("transAmt")).alias("transAmtSum")
                , cb.sum(root.<BigDecimal>get("transFee")).alias("transFeeSum")
        ));

        List<Predicate> ps = new ArrayList<>();
        if (null != searchDto) {
            if (!StringUtils.isEmpty(searchDto.getStartCreateTs())) {
                ps.add(cb.greaterThanOrEqualTo(root.get("createTs"), DateHelper.getStartDate(searchDto.getStartCreateTs())));
            }
            if (!StringUtils.isEmpty(searchDto.getEndCreateTs())) {
                ps.add(cb.lessThanOrEqualTo(root.get("createTs"), DateHelper.getEndDate(searchDto.getEndCreateTs())));
            }
            if(!StringUtils.isEmpty(searchDto.getMemberNo())){
                ps.add(cb.equal(root.get("memberNo"), searchDto.getMemberNo()));
            }
            if (null != searchDto.getBizCode()) {
                ps.add(cb.equal(root.get("bizCode"), searchDto.getBizCode()));
            }
            ps.add(cb.equal(root.get("status"), ETradeStatus.SUCCESS));
        }

        query.where(ps.toArray(new Predicate[ps.size()]));
        return em.createQuery(query).getSingleResult();
    }

    @Override
    @Transactional(readOnly = true)
    public YmTradeSearchDto getTradePage(YmTradeSearchDto searchDto) {
        Specification<YmTradePo> spec = (Root<YmTradePo> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (null != searchDto) {

                if (!StringUtils.isEmpty(searchDto.getStartCreateTs())) {
                    expressions.add(cb.greaterThanOrEqualTo(root.get("createTs"), DateHelper.getStartDate(searchDto.getStartCreateTs())));
                }
                if (!StringUtils.isEmpty(searchDto.getEndCreateTs())) {
                    expressions.add(cb.lessThanOrEqualTo(root.get("createTs"), DateHelper.getEndDate(searchDto.getEndCreateTs())));
                }
                if(!StringUtils.isEmpty(searchDto.getMemberNo())){
                    expressions.add(cb.equal(root.get("memberNo"), searchDto.getMemberNo()));
                }
                if (null != searchDto.getBizCode()) {
                    expressions.add(cb.equal(root.get("bizCode"), searchDto.getBizCode()));
                }
                if (null != searchDto.getStatus()) {
                    expressions.add(cb.equal(root.get("status"), searchDto.getStatus()));
                }
            }
            return predicate;
        };

        Pageable pageable = new PageRequest(searchDto.getCurrentPage() - 1,
                searchDto.getPageSize(), Sort.Direction.DESC, "id");
        Page<YmTradePo> page = ymTradeDao.findAll(spec, pageable);

        List<YmTradeDto> data = new ArrayList<>();
        for (YmTradePo po : page.getContent()) {
            YmTradeDto dto = ConverterService.convert(po, YmTradeDto.class);
            data.add(dto);
        }
        searchDto.setList(data);
        searchDto.setTotalPages(page.getTotalPages());
        searchDto.setTotalRecord(page.getTotalElements());
        return searchDto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<YmTradeDto> getTradeExportList(YmTradeSearchDto searchDto) {
        Specification<YmTradePo> spec = (Root<YmTradePo> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (null != searchDto) {

                if (!StringUtils.isEmpty(searchDto.getStartCreateTs())) {
                    expressions.add(cb.greaterThanOrEqualTo(root.get("createTs"), DateHelper.getStartDate(searchDto.getStartCreateTs())));
                }
                if (!StringUtils.isEmpty(searchDto.getEndCreateTs())) {
                    expressions.add(cb.lessThanOrEqualTo(root.get("createTs"), DateHelper.getEndDate(searchDto.getEndCreateTs())));
                }
                if(!StringUtils.isEmpty(searchDto.getMemberNo())){
                    expressions.add(cb.equal(root.get("memberNo"), searchDto.getMemberNo()));
                }
                if (null != searchDto.getBizCode()) {
                    expressions.add(cb.equal(root.get("bizCode"), searchDto.getBizCode()));
                }
                if (null != searchDto.getStatus()) {
                    expressions.add(cb.equal(root.get("status"), searchDto.getStatus()));
                }
            }
            return predicate;
        };

        List<YmTradePo> list = ymTradeDao.findAll(spec, new Sort(Sort.Direction.DESC, "id"));

        List<YmTradeDto> data = new ArrayList<>();
        for (YmTradePo po : list) {
            YmTradeDto dto = ConverterService.convert(po, YmTradeDto.class);
            data.add(dto);
        }
        return data;
    }

    /**
     * 生成，更新订单
     *
     * @param ymTradeDto
     * @return
     * @throws BizServiceException
     */
    @Transactional
    @Override
    public YmTradeDto saveTrade(YmTradeDto ymTradeDto) throws BizServiceException {
        YmTradePo po = ConverterService.convert(ymTradeDto, YmTradePo.class);
        ymTradeDao.save(po);
        YmTradeDto dto = ConverterService.convert(po, YmTradeDto.class);
        return dto;
    }

    /**
     * 按日统计查询流水列表
     *
     * @param memberNo
     * @return
     */
    @Override
    public List<YmTradeDayDto> findDayByMemberNo(String memberNo) {
        List<Object[]> list = ymTradeDao.findDayByMemberNo(memberNo);
        List<YmTradeDayDto> dtoList = new ArrayList<>();
        YmTradeDayDto ymTradeDayDto;
        for (Object[] object : list) {
            ymTradeDayDto = new YmTradeDayDto();
            ymTradeDayDto.setDates(object[0].toString());
            ymTradeDayDto.setDays(object[1].toString());
            ymTradeDayDto.setWeeks(object[2].toString());
            EBizCode eBizCode = EBizCode.UNKNOW;
            if (StringUtils.equals(EBizCode.ALLIPAY.getCode(), object[3].toString())) {
                eBizCode = EBizCode.ALLIPAY;
            } else if (StringUtils.equals(EBizCode.WECHAT.getCode(), object[3].toString())) {
                eBizCode = EBizCode.WECHAT;
            } else if (StringUtils.equals(EBizCode.CMCC.getCode(), object[3].toString())) {
                eBizCode = EBizCode.CMCC;
            } else if (StringUtils.equals(EBizCode.JDPAY.getCode(), object[3].toString())) {
                eBizCode = EBizCode.JDPAY;
            } else if (StringUtils.equals(EBizCode.BESTPAY.getCode(), object[3].toString())) {
                eBizCode = EBizCode.BESTPAY;
            } else if (StringUtils.equals(EBizCode.BAIDUPAY.getCode(), object[3].toString())) {
                eBizCode = EBizCode.BAIDUPAY;
            } else if (StringUtils.equals(EBizCode.QQPAY.getCode(), object[3].toString())) {
                eBizCode = EBizCode.QQPAY;
            } else if (StringUtils.equals(EBizCode.PAYCODE.getCode(), object[3].toString())) {
                eBizCode = EBizCode.PAYCODE;
            }
            ymTradeDayDto.setBizCode(eBizCode);
            ymTradeDayDto.setTransAmt(new BigDecimal(object[4].toString()));
            ymTradeDayDto.setTransFee(new BigDecimal(object[5].toString()));
            ymTradeDayDto.setCount(Long.valueOf(object[6].toString()));
            dtoList.add(ymTradeDayDto);
        }
        return dtoList;
    }

    /**
     * 按日期和商户号查询流水列表
     *
     * @param memberNo
     * @param dates
     * @return
     */
    @Override
    public List<YmTradeDto> findByMemberNoAndDates(String memberNo, String dates) {
        List<YmTradePo> poList = ymTradeDao.findByMemberNoAndDates(memberNo, dates);
        List<YmTradeDto> dtoList = new ArrayList<>();
        for (YmTradePo po : poList) {
            YmTradeDto dto = ConverterService.convert(po, YmTradeDto.class);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public List<YmTradeDto> findByMemberNo(String memberNo) {
        List<YmTradePo> poList = ymTradeDao.findByMemberNo(memberNo);
        List<YmTradeDto> dtoList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(poList)) {
            for (YmTradePo po : poList) {
                YmTradeDto dto = ConverterService.convert(po, YmTradeDto.class);
                dtoList.add(dto);
            }
        }
        return dtoList;
    }

    /**
     * 更新订单
     *
     * @param ymTradeDto
     * @return
     * @throws BizServiceException
     */
    @Transactional
    @Override
    public YmTradeDto updateTrade(YmTradeDto ymTradeDto) throws BizServiceException {
        YmTradePo po = ymTradeDao.findByTradeNo(ymTradeDto.getTradeNo());
        if (po == null) {
            return null;
        }
        po.setStatus(ymTradeDto.getStatus());
        po.setRespCode(ymTradeDto.getRespCode());
        po.setRespInfo(ymTradeDto.getRespInfo());
        ymTradeDao.save(po);
        YmTradeDto dto = ConverterService.convert(po, YmTradeDto.class);
        return dto;
    }

    @Override
    public YmTradeDto findByTradeNo(String tradeNo) {
        YmTradePo po = ymTradeDao.findByTradeNo(tradeNo);
        YmTradeDto dto = ConverterService.convert(po, YmTradeDto.class);
        return dto;
    }

    /**
     * 更新外部订单
     *
     * @param ymTradeDto
     * @return
     * @throws BizServiceException
     */
    @Transactional
    @Override
    public YmTradeDto updateOutTradeNo(YmTradeDto ymTradeDto) throws BizServiceException {
        YmTradePo po = ymTradeDao.findByTradeNo(ymTradeDto.getTradeNo());
        if (po == null) {
            return null;
        }
        po.setOutTradeNo(ymTradeDto.getOutTradeNo());
        ymTradeDao.save(po);
        YmTradeDto dto = ConverterService.convert(po, YmTradeDto.class);
        return dto;
    }

    /**
     * 生成订单号
     * @param
     * @return
     */
    @Override
    public String getOrderNo() {
        Context context = new Context("CLOUDCODE_ORDER_NO");
        return IdUtil.produce(ApplicationContextUtil.getBean(PartnerOrderNoSeqProducer.class), context);
    }

}
