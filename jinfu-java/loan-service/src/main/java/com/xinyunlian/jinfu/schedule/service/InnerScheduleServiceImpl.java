package com.xinyunlian.jinfu.schedule.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.AmtUtils;
import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.loan.dao.LoanDtlDao;
import com.xinyunlian.jinfu.loan.service.InnerApplService;
import com.xinyunlian.jinfu.promo.dao.PromoDao;
import com.xinyunlian.jinfu.schedule.dao.ScheduleDao;
import com.xinyunlian.jinfu.schedule.dto.ScheduleDto;
import com.xinyunlian.jinfu.schedule.entity.SchedulePo;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author willwang
 */
@Service
public class InnerScheduleServiceImpl implements InnerScheduleService {

    @Autowired
    private ScheduleDao scheduleDao;

    @Autowired
    private LoanDtlDao loanDtlDao;

    @Autowired
    private InnerApplService innerApplService;

    @Autowired
    private PromoDao promoDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(InnerScheduleService.class);

    @Override
    @Transactional
    public ScheduleDto save(ScheduleDto scheduleDto) {
        SchedulePo po;
        if (scheduleDto.getScheduleId() == null) {
            po = new SchedulePo();
        } else {
            po = scheduleDao.findOne(scheduleDto.getScheduleId());

            //scheduleId外部传入，po可能存在查不出的情况，如果没查到进行初始化
            if(po == null){
                po = new SchedulePo();
            }

        }

        if (po.getCreateTs() != null) {
            po.setVersionCt(0L);
        }
        ConverterService.convert(scheduleDto, po);
        scheduleDao.save(po);
        scheduleDto.setScheduleId(po.getScheduleId());
        return scheduleDto;
    }

    @Override
    @Transactional
    public List<ScheduleDto> list(String loanId) {
        List<SchedulePo> schedules = scheduleDao.getRepayedList(loanId);
        List<ScheduleDto> list = new ArrayList<>();

        for (SchedulePo schedule : schedules) {
            list.add(ConverterService.convert(schedule, ScheduleDto.class));
        }

        return list;
    }

    @Override
    public List<ScheduleDto> getLoanRepayList(String loanId) {
        return this.list(loanId);
    }

    @Override
    public ScheduleDto getCurrentSchedule(String loanId) throws Exception {
        List<SchedulePo> schedulePos = scheduleDao.getRepayedList(loanId);

        if(schedulePos.size() <= 0){
            return null;
        }

        String now = DateHelper.getNow();

        //如果时间都还没到第一期的日子认定为当期为第一期
        if(DateHelper.before(now, schedulePos.get(0).getDueDate())){
            return ConverterService.convert(schedulePos.get(0), ScheduleDto.class);
        }

        for(int i = 0;i < schedulePos.size() - 1; i++){

            String lastPeriod = schedulePos.get(i).getDueDate();
            String nowPeriod = schedulePos.get(i+1).getDueDate();

            if(DateHelper.between(lastPeriod, nowPeriod)){
                return ConverterService.convert(schedulePos.get(i+1), ScheduleDto.class);
            }
        }

        return null;

    }

    @Override
    public ScheduleDto getScheduleByPeriod(String loanId, Integer period) {
        List<ScheduleDto> list = this.list(loanId);

        if(CollectionUtils.isEmpty(list)){
            return null;
        }

        for (ScheduleDto scheduleDto : list) {
            if(period.equals(scheduleDto.getSeqNo())){
                return scheduleDto;
            }
        }

        return null;
    }

    @Override
    public ScheduleDto getNextSchedule(String loanId) throws BizServiceException {

        //获得当期还款计划
        ScheduleDto scheduleDto = null;
        try{
            scheduleDto = this.getCurrentSchedule(loanId);
        }catch(Exception e){
            LOGGER.warn("exception", e);
        }

        List<SchedulePo> schedules = scheduleDao.getRepayedList(loanId);

        //如果计划为空或者只有一期的计划，没有查询的意义
        if(schedules.size() <= 0 || schedules.size() == 1){
            return null;
        }

        //如果已经在所有还款计划之外，或者还款计划已经到了最后一期，下一期就直接为null
        if(scheduleDto == null || this.isLastSchedule(scheduleDto, schedules)){
            return null;
        }

        for (SchedulePo schedule : schedules) {
            if((scheduleDto.getSeqNo() + 1) == schedule.getSeqNo()){
                return ConverterService.convert(schedule, ScheduleDto.class);
            }
        }

        return null;
    }

    private boolean isLastSchedule(ScheduleDto scheduleDto, List<SchedulePo> schedules){

        return scheduleDto != null && schedules.size() > 0 && scheduleDto.getSeqNo() == schedules.get(schedules.size() - 1).getSeqNo();

    }
}
