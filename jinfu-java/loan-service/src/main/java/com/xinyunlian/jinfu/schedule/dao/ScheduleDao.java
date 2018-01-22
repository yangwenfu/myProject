package com.xinyunlian.jinfu.schedule.dao;

import com.xinyunlian.jinfu.schedule.entity.SchedulePo;
import com.xinyunlian.jinfu.schedule.enums.EScheduleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author willwang
 */
public interface ScheduleDao extends JpaRepository<SchedulePo, String>, JpaSpecificationExecutor<SchedulePo> {

    @Query("from SchedulePo i where i.loanId = ?1 order by i.seqNo asc")
    List<SchedulePo> getRepayedList(String loanId);

    /**
     * 方法名字变更，请慢慢往这里迁移
     *
     * @param loanId
     * @return
     */
    @Query("from SchedulePo i where i.loanId = ?1 order by i.seqNo asc")
    List<SchedulePo> findByLoanId(String loanId);

    List<SchedulePo> findByScheduleStatusAndDueDate(EScheduleStatus scheduleStatus, String dueDate);

    List<SchedulePo> findByScheduleStatusAndDueDateIn(EScheduleStatus scheduleStatus, List<String> dueDate);

    List<SchedulePo> findByLoanIdAndScheduleStatusInOrderBySeqNoAsc(String loanId, List<EScheduleStatus> scheduleStatuses);


}
