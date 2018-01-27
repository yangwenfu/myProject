package com.xinyunlian.jinfu.schedule.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.loan.dto.req.LoanCustomerApplDto;
import com.xinyunlian.jinfu.schedule.dto.ScheduleDto;
import com.xinyunlian.jinfu.schedule.dto.management.MSContainerDto;
import com.xinyunlian.jinfu.schedule.enums.EScheduleStatus;

import java.util.List;

/**
 * @author willwang
 */
public interface ScheduleService {

    /**
     * 还款计划预览
     * @param apply
     * @return
     */
    List<ScheduleDto> preview(LoanCustomerApplDto apply);

    ScheduleDto get(String scheduleId);

    /**
     * 保存还款记录变更信息
     *
     * @param scheduleDto
     */
    ScheduleDto save(ScheduleDto scheduleDto);

    /**
     * 批量保存还款计划
     */
    void saveList(List<ScheduleDto> schedules);


    /**
     * 生成还款计划
     *
     * @param userId 用户编号
     * @param applId 贷款申请编号
     */
    void generate(String userId, String applId);

    /**
     * 还款计划列表
     *
     * @param loanId
     * @return
     */
    List<ScheduleDto> list(String userId, String loanId);

    /**
     * 后台还款计划以及还款列表的合并数据
     *
     * @param applId
     * @return
     */
    List<MSContainerDto> getManagementSchedule(String applId) throws BizServiceException;

    List<MSContainerDto> getManagementScheduleByLoanId(String loanId) throws BizServiceException;

    /**
     * 所有待还款的还款计划列表
     *
     * @return
     */
    List<ScheduleDto> getRepayList();

    List<ScheduleDto> getLoanRepayList(String loanId);

    /**
     * 根据计划状态和逾期时间查询还款计划
     *
     * @param scheduleStatus
     * @param dueDate
     * @return
     */
    List<ScheduleDto> findByStatusAndDueDate(EScheduleStatus scheduleStatus, List<String> dueDate);

    /**
     * 根据贷款编号获得当期
     * @param loanId
     * @return
     * @throws Exception
     */
    ScheduleDto getCurrentSchedule(String loanId);
}
