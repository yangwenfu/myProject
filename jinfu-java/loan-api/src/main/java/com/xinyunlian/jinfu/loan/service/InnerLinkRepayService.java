package com.xinyunlian.jinfu.loan.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.loan.dto.repay.LinkRepayDto;
import com.xinyunlian.jinfu.loan.dto.req.LoanRepayLinkDto;

import java.util.Collection;
import java.util.List;

/**
 * Created by Willwang on 2016/11/16.
 */
public interface InnerLinkRepayService {


    LinkRepayDto save(LinkRepayDto linkRepayDto);

    void add(String repayId, String scheduleId);

    void add(LoanRepayLinkDto loanRepayLinkDto);

    List<LinkRepayDto> findByScheduleId(String scheduleId);

    List<LinkRepayDto> findByScheduleIds(Collection<String> schedules) throws BizServiceException;

    List<LinkRepayDto> findByRepayId(String repayId);

    LinkRepayDto findByRepayIdAndScheduleId(String repayId, String scheduleId) throws BizServiceException;

}
