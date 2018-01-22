package com.xinyunlian.jinfu.schedule.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.schedule.dto.ScheduleDto;
import java.util.List;

/**
 * @author willwang
 */
public interface InnerScheduleService {

    /**
     * 保存还款记录变更信息
     *
     * @param scheduleDto
     */
    ScheduleDto save(ScheduleDto scheduleDto);

    /**
     * 还款计划列表
     *
     * @param loanId
     * @return
     */
    List<ScheduleDto> list(String loanId);

    /**
     * 根据贷款编号查找还款计划列表
     * @param loanId
     * @return
     */
    List<ScheduleDto> getLoanRepayList(String loanId);

    /**
     * 获得当前还款计划
     * @param loanId
     * @return
     */
    ScheduleDto getCurrentSchedule(String loanId) throws Exception;

    /**
     * 根据期号获得还款计划
     * @param loanId
     * @param period
     * @return
     */
    ScheduleDto getScheduleByPeriod(String loanId, Integer period);

    /**
     * 获取下一期还款计划
     * @param loanId
     * @return
     * @throws Exception
     */
    ScheduleDto getNextSchedule(String loanId) throws BizServiceException;
}
