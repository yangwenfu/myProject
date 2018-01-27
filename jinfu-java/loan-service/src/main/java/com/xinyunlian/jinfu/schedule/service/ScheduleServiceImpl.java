package com.xinyunlian.jinfu.schedule.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.AmtUtils;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.coupon.dto.LoanCouponDto;
import com.xinyunlian.jinfu.coupon.dto.LoanMCouponDto;
import com.xinyunlian.jinfu.coupon.service.LoanCouponService;
import com.xinyunlian.jinfu.loan.dao.LoanDtlDao;
import com.xinyunlian.jinfu.loan.dto.repay.LinkRepayDto;
import com.xinyunlian.jinfu.loan.dto.req.LoanCustomerApplDto;
import com.xinyunlian.jinfu.loan.dto.resp.LoanDtlDto;
import com.xinyunlian.jinfu.loan.entity.RepayDtlPo;
import com.xinyunlian.jinfu.loan.enums.ERepayMode;
import com.xinyunlian.jinfu.loan.enums.ERepayStatus;
import com.xinyunlian.jinfu.loan.service.InnerApplService;
import com.xinyunlian.jinfu.loan.service.InnerLinkRepayService;
import com.xinyunlian.jinfu.loan.service.InnerProductService;
import com.xinyunlian.jinfu.pay.dto.PayRecvOrdDto;
import com.xinyunlian.jinfu.pay.enums.EPrType;
import com.xinyunlian.jinfu.pay.service.InnerPayRecvOrdService;
import com.xinyunlian.jinfu.product.dto.LoanProductDetailDto;
import com.xinyunlian.jinfu.promo.dao.PromoDao;
import com.xinyunlian.jinfu.promo.dto.PromoDto;
import com.xinyunlian.jinfu.promo.entity.PromoPo;
import com.xinyunlian.jinfu.repay.dao.RepayDao;
import com.xinyunlian.jinfu.repay.domain.InterestPerDiem;
import com.xinyunlian.jinfu.repay.domain.MonthAverageCapitalPlusInterest;
import com.xinyunlian.jinfu.repay.domain.MonthAvgCapitalAvgInterest;
import com.xinyunlian.jinfu.repay.domain.RepayMethod;
import com.xinyunlian.jinfu.schedule.dao.ScheduleDao;
import com.xinyunlian.jinfu.schedule.dto.ScheduleDto;
import com.xinyunlian.jinfu.schedule.dto.management.MRepayDto;
import com.xinyunlian.jinfu.schedule.dto.management.MSContainerDto;
import com.xinyunlian.jinfu.schedule.dto.management.MScheduleDto;
import com.xinyunlian.jinfu.schedule.entity.SchedulePo;
import com.xinyunlian.jinfu.schedule.enums.EScheduleStatus;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;

/**
 * @author willwang
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private InnerApplService innerApplService;

    @Autowired
    private ScheduleDao scheduleDao;

    @Autowired
    private InnerScheduleService innerScheduleService;

    @Autowired
    private PromoDao promoDao;

    @Autowired
    private InnerLinkRepayService innerLinkRepayService;

    @Autowired
    private InnerPayRecvOrdService innerPayRecvOrdService;

    @Autowired
    private LoanDtlDao loanDtlDao;

    @Autowired
    private RepayDao repayDao;

    @Autowired
    private InnerProductService innerProductService;

    @Autowired
    private LoanCouponService loanCouponService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleServiceImpl.class);

    @Override
    @Transactional
    public void generate(String userId, String applId) {
        LoanDtlDto loan = innerApplService.getLoan(applId);

        if (loan == null) {
            throw new BizServiceException(EErrorCode.TECH_DATA_INVALID, "贷款单不存在");
        }

        if (!loan.getUserId().equals(userId)) {
            throw new BizServiceException(EErrorCode.TECH_DATA_INVALID, "贷款单和用户不一致");
        }
        //促销活动
        PromoDto promoDto = null;
        List<PromoPo> promos = promoDao.findByLoanId(loan.getLoanId());
        if (!promos.isEmpty()) {
            //暂时不叠加活动，取第一个
            promoDto = ConverterService.convert(promos.get(0), PromoDto.class);
        }

        LoanProductDetailDto product = innerApplService.getProduct(applId);

        RepayMethod repayMethod;
        switch (loan.getRepayMode()) {
            case INTR_PER_DIEM:
                //生成还款计划时。还款时间为截止日，最小还款天数为0，罚息为0
                repayMethod = new InterestPerDiem(loan, promoDto, loan.getDutDate(), product);
                break;
            case MONTH_AVE_CAP_PLUS_INTR:
                Date now = new Date();
                repayMethod = new MonthAverageCapitalPlusInterest(loan, promoDto, now);
                break;
            case MONTH_AVE_CAP_AVG_INTR:
                repayMethod = new MonthAvgCapitalAvgInterest(loan, null, new Date());
                break;
            default:
                throw new BizServiceException(EErrorCode.TECH_DATA_INVALID, "不支持的还款类型:" + loan.getRepayMode());
        }
        saveSchedule(repayMethod.getRepaySchedule());
    }

    @Override
    public List<ScheduleDto> preview(LoanCustomerApplDto apply) {
        LoanProductDetailDto product = innerProductService.getProdDtl(apply.getProductId());

//        if (!Arrays.asList(ERepayMode.MONTH_AVE_CAP_PLUS_INTR, ERepayMode.INTR_PER_DIEM).contains(product.getRepayMode())) {
//            throw new BizServiceException(EErrorCode.TECH_DATA_INVALID,
//                    String.format("productId:%s,repayMode:%s 不支持还款计划预览", apply.getProductId(), product.getRepayMode().getAlias()));
//        }

        //模拟一个虚拟的贷款单，以获得还款计划
        LoanDtlDto loan = ConverterService.convert(loanDtlDao.getFirst(), LoanDtlDto.class);
        loan.setLoanAmt(apply.getApplAmt());
        loan.setTermLen(Integer.parseInt(apply.getTermLen()));
        loan.setTermType(product.getTermType());
        loan.setLoanRt(product.getIntrRate());
        loan.setIntrRateType(product.getIntrRateType());
        loan.setTransferDate(new Date());
        loan.setDutDate(
                DateHelper.formatDate(product.getTermType().add(loan.getTransferDate(), Integer.parseInt(apply.getTermLen())))
        );

        RepayMethod repayMethod = null;
        switch (product.getRepayMode()) {
            case MONTH_AVE_CAP_PLUS_INTR:
                repayMethod = new MonthAverageCapitalPlusInterest(loan, null, new Date());
                break;
            case INTR_PER_DIEM:
                repayMethod = new InterestPerDiem(loan, null, loan.getDutDate(), product);
                break;
            case MONTH_AVE_CAP_AVG_INTR:
                if (apply.getFeeRt() != null) {
                    loan.setServiceFeeMonthRt(apply.getFeeRt().subtract(loan.getLoanRt()));
                }
                repayMethod = new MonthAvgCapitalAvgInterest(loan, null, new Date());
                break;
        }

        if (repayMethod == null) {
            throw new BizServiceException(EErrorCode.TECH_DATA_INVALID,
                    String.format("productId:%s,repayMode:%s 不支持还款计划预览", apply.getProductId(), product.getRepayMode().getAlias()));
        }

        return repayMethod.getRepaySchedule();
    }

    private void saveSchedule(List<ScheduleDto> scheduleDtos) {
        scheduleDtos.forEach(scheduleDto ->
                scheduleDao.save(ConverterService.convert(scheduleDto, SchedulePo.class))
        );
    }

    @Override
    public ScheduleDto get(String scheduleId) {
        SchedulePo po = scheduleDao.findOne(scheduleId);
        return ConverterService.convert(po, ScheduleDto.class);
    }

    @Override
    public ScheduleDto save(ScheduleDto scheduleDto) {
        return innerScheduleService.save(scheduleDto);
    }

    @Override
    @Transactional
    public void saveList(List<ScheduleDto> schedules) {
        for (ScheduleDto schedule : schedules) {
            innerScheduleService.save(schedule);
        }
    }

    @Override
    public List<ScheduleDto> list(String userId, String loanId) {
        return innerScheduleService.list(loanId);
    }

    @Override
    public List<MSContainerDto> getManagementSchedule(String applId) throws BizServiceException {
        LoanDtlDto loanDtlDto = innerApplService.getLoan(applId);
        if (loanDtlDto == null) {
            throw new BizServiceException(EErrorCode.LOAN_APPL_NOT_EXISTS);
        }
        return getManagementScheduleByLoanId(loanDtlDto.getLoanId());
    }


    @Override
    public List<MSContainerDto> getManagementScheduleByLoanId(String loanId) throws BizServiceException {
        List<MSContainerDto> list = new ArrayList<>();

        List<ScheduleDto> schedules = innerScheduleService.getLoanRepayList(loanId);

        Map<String, List<LinkRepayDto>> linkMap = new HashMap<>();
        //key: repayIds AND scheduleId as bizIds, 新贷款中payRecv中BIZID已经全部切换到repayId,但老数据部分scheduleId是作为bizId的
        //所以要做数据的兼容
        Set<String> bizIds = new HashSet<>();

        Set<String> repayIds = new HashSet<>();

        Map<String, RepayDtlPo> repayMap = new HashMap<>();

        //scheduleId -> 最新一次的代扣结果
        Map<String, List<PayRecvOrdDto>> payRecvOrdDtoMap = new HashMap<>();

        //scheduleId -> Link 批处理
        List<String> scheduleIds = (List<String>) this.extra(schedules, "scheduleId");
        List<LinkRepayDto> linkList = innerLinkRepayService.findByScheduleIds(scheduleIds);
        if (CollectionUtils.isNotEmpty(linkList)) {
            for (LinkRepayDto link : linkList) {
                List<LinkRepayDto> itemList = linkMap.get(link.getSchdId());
                if (CollectionUtils.isEmpty(itemList)) {
                    itemList = new ArrayList<>();
                }
                itemList.add(link);
                linkMap.put(link.getSchdId(), itemList);

                bizIds.add(link.getRepayId());
                bizIds.add(link.getSchdId());

                repayIds.add(link.getRepayId());
            }
        }

        if (CollectionUtils.isNotEmpty(repayIds)) {
            List<RepayDtlPo> repayDtlPos = repayDao.findByRepayIdIn(repayIds);
            if (CollectionUtils.isNotEmpty(repayDtlPos)) {
                for (RepayDtlPo repayDtlPo : repayDtlPos) {
                    repayMap.put(repayDtlPo.getRepayId(), repayDtlPo);
                }
            }
        }

        List<PayRecvOrdDto> payRecvOrdDtoList = innerPayRecvOrdService.findByBizIds(new ArrayList<>(bizIds));

        if (CollectionUtils.isNotEmpty(payRecvOrdDtoList)) {
            for (PayRecvOrdDto payRecvOrdDto : payRecvOrdDtoList) {
                List<PayRecvOrdDto> itemList = payRecvOrdDtoMap.get(payRecvOrdDto.getBizId());
                if (CollectionUtils.isEmpty(itemList)) {
                    itemList = new ArrayList<>();
                }
                itemList.add(payRecvOrdDto);
                payRecvOrdDtoMap.put(payRecvOrdDto.getBizId(), itemList);
            }
        }

        Map<String, Boolean> hasCompleteCoupon = new HashMap<>();

        for (ScheduleDto schedule : schedules) {
            List<MScheduleDto> left = new ArrayList<>();
            List<MRepayDto> right = new ArrayList<>();

            MSContainerDto container = new MSContainerDto();

            MScheduleDto mScheduleDto = new MScheduleDto();
            mScheduleDto.setScheduleId(schedule.getScheduleId());
            mScheduleDto.setPeriod(schedule.getSeqNo());
            mScheduleDto.setCapital(schedule.getShouldCapital());
            mScheduleDto.setInterest(schedule.getShouldInterest());
            mScheduleDto.setDate(schedule.getDueDate());

            //找到所有相关的link信息
            List<LinkRepayDto> links = linkMap.get(schedule.getScheduleId());
            //找到所有repayIds
            List<String> itemRepayIds = (List<String>) this.extra(links, "repayId");
            //找到所有根据repayId找到的payRecv

            List<PayRecvOrdDto> payRecvOrdDtos = this.getPayRecvOrdDtos(payRecvOrdDtoMap, itemRepayIds);
            PayRecvOrdDto latest = this.getLatest(payRecvOrdDtos);
            if (latest != null) {
                mScheduleDto.setPayRecv(latest);
            }
            //如果根据一堆的还款记录编号没有找到指令，再根据还款计划找一遍（对老数据进行兼容）
            if (mScheduleDto.getPayRecv() == null) {
                List<String> itemScheduleIds = new ArrayList<>();
                itemScheduleIds.add(schedule.getScheduleId());
                payRecvOrdDtos = this.getPayRecvOrdDtos(payRecvOrdDtoMap, itemScheduleIds);
                latest = this.getLatest(payRecvOrdDtos);
                if (latest != null) {
                    mScheduleDto.setPayRecv(latest);
                }
            }

            int days = this.getDays(schedule);

            mScheduleDto.setOverdue(days);
            mScheduleDto.setStatus(schedule.getScheduleStatus());
            mScheduleDto.setCanManual(days > 0);

            List<LinkRepayDto> linkRepayDtos = linkMap.get(schedule.getScheduleId());

            if (CollectionUtils.isNotEmpty(linkRepayDtos)) {
                for (LinkRepayDto linkRepayDto : linkRepayDtos) {
                    MRepayDto mRepayDto = new MRepayDto();
                    mRepayDto.setRepayId(linkRepayDto.getRepayId());
                    RepayDtlPo repayDtlPo = repayMap.get(linkRepayDto.getRepayId());
                    if (repayDtlPo == null || repayDtlPo.getStatus() != ERepayStatus.SUCCESS) {
                        continue;
                    }
                    mRepayDto.setCapital(linkRepayDto.getCapital());
                    mRepayDto.setInterest(linkRepayDto.getInterest());
                    mRepayDto.setFine(linkRepayDto.getFine());
                    mRepayDto.setDate(DateHelper.formatDate(linkRepayDto.getCreateTs()));
                    mRepayDto.setTransMode(repayDtlPo.getTransMode());

                    Boolean tt = hasCompleteCoupon.get(linkRepayDto.getRepayId());

                    if (tt == null || !tt) {
                        mRepayDto = this.completeCoupon(mRepayDto);
                        hasCompleteCoupon.put(linkRepayDto.getRepayId(), true);
                    }

                    right.add(mRepayDto);
                }
            }

            left.add(mScheduleDto);

            container.setLeft(left);
            container.setRight(right);

            list.add(container);
        }

        return list;
    }

    private int getDays(ScheduleDto schedule) {
        Date periodDate = DateHelper.getDate(schedule.getDueDate());
        Date now = new Date();
        int days = DateHelper.betweenDaysNew(periodDate, now);
        //如果逾期时间还没到当前时间，罚息计算的天数为0，主要是为了兼容一些直接改状态后的罚息计算的问题
        if (periodDate.getTime() > now.getTime()) {
            days = 0;
        }

        return days;
    }

    private List<PayRecvOrdDto> getPayRecvOrdDtos(Map<String, List<PayRecvOrdDto>> map, Collection<String> bizIds) {

        if (map.size() <= 0) {
            return new ArrayList<>();
        }

        List<PayRecvOrdDto> list = new ArrayList<>();

        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            List<PayRecvOrdDto> value = (List<PayRecvOrdDto>) entry.getValue();
            if (bizIds.contains(entry.getKey())) {
                list.addAll(value);
            }
        }

        return list;
    }

    private MRepayDto completeCoupon(MRepayDto mRepayDto) {
        List<LoanCouponDto> coupons = loanCouponService.listByRepayId(mRepayDto.getRepayId());
        if (CollectionUtils.isEmpty(coupons)) {
            return mRepayDto;
        }
        LoanCouponDto coupon = coupons.get(0);
        if (coupon == null) {
            return mRepayDto;
        }
        LoanMCouponDto loanMCouponDto = new LoanMCouponDto();
        loanMCouponDto.setDesc(
                MessageFormat.format("券类型:{0},券名称:{1},券码:{2}", coupon.getCouponType(), coupon.getCouponName(), coupon.getCouponCode())
        );

        BigDecimal price = AmtUtils.min(coupon.getPrice(), mRepayDto.getInterest());

        loanMCouponDto.setPrice(price);
        mRepayDto.setCoupon(loanMCouponDto);
        return mRepayDto;
    }

    private PayRecvOrdDto getLatest(List<PayRecvOrdDto> list) {

        PayRecvOrdDto latest = null;

        if (list != null && list.size() > 0) {
            for (PayRecvOrdDto payRecvOrdDto : list) {
                if (payRecvOrdDto.getPrType() == EPrType.RECEIVE) {
                    if (latest == null) {
                        latest = payRecvOrdDto;
                    }
                    if (payRecvOrdDto.getCreateDateTime().after(latest.getCreateDateTime())) {
                        latest = payRecvOrdDto;
                    }
                }
            }
        }

        return latest;
    }

    private List<String> extraRepayIds(List<LinkRepayDto> list) {
        List<String> rs = new ArrayList<>();

        for (LinkRepayDto linkRepayDto : list) {
            if (StringUtils.isNotEmpty(linkRepayDto.getRepayId()) && !rs.contains(linkRepayDto.getRepayId())) {
                rs.add(linkRepayDto.getRepayId());
            }
        }

        return rs;
    }

    @Override
    public List<ScheduleDto> getRepayList() {
        String now = DateHelper.getWorkDate();
        List<SchedulePo> schedules = scheduleDao.findByScheduleStatusAndDueDate(EScheduleStatus.NOTYET, now);
        List<ScheduleDto> list = new ArrayList<>();

        for (SchedulePo schedule : schedules) {
            list.add(ConverterService.convert(schedule, ScheduleDto.class));
        }

        return list;
    }

    @Override
    public List<ScheduleDto> getLoanRepayList(String loanId) {
        return innerScheduleService.getLoanRepayList(loanId);
    }

    @Override
    public List<ScheduleDto> findByStatusAndDueDate(EScheduleStatus scheduleStatus, List<String> dueDate) {
        List<SchedulePo> schedules = scheduleDao.findByScheduleStatusAndDueDateIn(EScheduleStatus.NOTYET, dueDate);
        List<ScheduleDto> list = new ArrayList<>();

        for (SchedulePo schedule : schedules) {
            list.add(ConverterService.convert(schedule, ScheduleDto.class));
        }

        return list;
    }

    @Override
    public ScheduleDto getCurrentSchedule(String loanId) {
        try {
            return innerScheduleService.getCurrentSchedule(loanId);
        } catch (Exception e) {
            LOGGER.warn("exception", e);
        }
        return null;
    }

    private List<?> extra(Collection<?> list, String fieldName) {
        List<Object> rs = new ArrayList<>();

        if (list == null || list.isEmpty()) {
            return rs;
        }

        Iterator iter = list.iterator();
        if (!iter.hasNext() || fieldName.isEmpty()) {
            return rs;
        }

        Field[] fields = iter.next().getClass().getDeclaredFields();

        list.forEach(item -> {
            for (int i = 0; i < fields.length; i++) {
                if (fieldName.equals(fields[i].getName())) {
                    try {
                        if (!fields[i].isAccessible()) {
                            fields[i].setAccessible(true);
                        }

                        if (fields[i].get(item) == null) {
                            continue;
                        }
                        rs.add(fields[i].get(item));
                    } catch (IllegalAccessException e) {
                    }
                }
            }
        });

        return rs;
    }
}
