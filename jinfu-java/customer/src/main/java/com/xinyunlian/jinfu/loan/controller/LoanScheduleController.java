package com.xinyunlian.jinfu.loan.controller;

import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.util.MessageUtil;
import com.xinyunlian.jinfu.common.util.NumberUtil;
import com.xinyunlian.jinfu.loan.dto.req.LoanCustomerApplDto;
import com.xinyunlian.jinfu.loan.dto.schedule.SchedulePreviewRequestDto;
import com.xinyunlian.jinfu.loan.dto.schedule.ScheduleResponseDto;
import com.xinyunlian.jinfu.schedule.dto.ScheduleDto;
import com.xinyunlian.jinfu.schedule.enums.EScheduleStatus;
import com.xinyunlian.jinfu.schedule.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author willwang
 */

@Controller
@RequestMapping(value = "loan/schedule")
public class LoanScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    /**
     * 还款计划预览
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "preview", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<List<ScheduleResponseDto>> preview(@RequestBody SchedulePreviewRequestDto request) {
        LoanCustomerApplDto applDto = new LoanCustomerApplDto();
        applDto.setApplAmt(request.getAmt());
        applDto.setProductId(request.getProductId());
        applDto.setTermLen(request.getPeriod().toString());
        applDto.setFeeRt(request.getFeeRt());

        List<ScheduleDto> schedules = scheduleService.preview(applDto);

        List<ScheduleResponseDto> rs = this.getScheduleResponse(schedules);

        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), rs);
    }

    /**
     * 还款计划列表
     *
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto list(@RequestParam String loanId) {
        String userId = SecurityContext.getCurrentUserId();
        List<ScheduleDto> schedules = scheduleService.list(userId, loanId);

        List<ScheduleResponseDto> rs = this.getScheduleResponse(schedules);

        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), rs);
    }

    private List<ScheduleResponseDto> getScheduleResponse(List<ScheduleDto> schedules) {
        List<ScheduleResponseDto> rs = new ArrayList<>();
        for (ScheduleDto schedule : schedules) {
            ScheduleResponseDto dto = new ScheduleResponseDto();
            dto.setStatus(schedule.getScheduleStatus());
            dto.setPeriod(schedule.getSeqNo());
            dto.setTotal(schedules.size());
            dto.setRepayDate(schedule.getDueDate());
            dto.setCapital(NumberUtil.roundTwo(schedule.getShouldCapital()));
            dto.setInterest(NumberUtil.roundTwo(schedule.getShouldInterest().add(schedule.getShouldFee())));
            rs.add(dto);
        }
        return rs;
    }

}
