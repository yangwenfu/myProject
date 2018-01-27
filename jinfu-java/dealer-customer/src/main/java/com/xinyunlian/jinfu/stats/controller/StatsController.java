package com.xinyunlian.jinfu.stats.controller;

import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.stats.dto.*;
import com.xinyunlian.jinfu.stats.service.StatsService;
import com.xinyunlian.jinfu.user.dto.DealerUserDto;
import com.xinyunlian.jinfu.user.service.DealerUserService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by menglei on 2016年09月18日.
 */
@Controller
@RequestMapping(value = "stats")
public class StatsController {

    @Autowired
    private StatsService statsService;
    @Autowired
    private DealerUserService dealerUserService;

    /**
     * 本周业绩统计
     *
     * @return
     */
    @RequestMapping(value = "/byWeek", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("本周业绩统计")
    public ResultDto<StatsDetailDto> getByWeek() {
        DealerUserDto dealerUserDto = (DealerUserDto) SecurityContext.getCurrentUser().getParamMap().get("dealerUserDto");
        Date date = new Date();
        String beginTime = DateHelper.getFirstDateOfWeek(DateHelper.formatDate(date, "yyyy-MM-dd"));
        String endTime = DateHelper.getLastDateOfWeek(DateHelper.formatDate(date, "yyyy-MM-dd"));
        StatsDetailDto statsDetailDto = statsService.getStats(beginTime, endTime, dealerUserDto);
        return ResultDtoFactory.toAck("获取成功", statsDetailDto);
    }

    /**
     * 本月业绩统计
     *
     * @return
     */
    @RequestMapping(value = "/byMonth", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("本月业绩统计")
    public ResultDto<StatsDetailDto> getByMonth() {
        DealerUserDto dealerUserDto = (DealerUserDto) SecurityContext.getCurrentUser().getParamMap().get("dealerUserDto");
        Date date = new Date();
        String beginTime = DateHelper.getFirstDateOfMonth(DateHelper.formatDate(date, "yyyy-MM-dd"));
        String endTime = DateHelper.getLastDateOfMonth(DateHelper.formatDate(date, "yyyy-MM-dd"));
        StatsDetailDto statsDetailDto = statsService.getStats(beginTime, endTime, dealerUserDto);
        return ResultDtoFactory.toAck("获取成功", statsDetailDto);
    }

    /**
     * 历史业绩统计
     *
     * @return
     */
    @RequestMapping(value = "/byHistory", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("历史业绩统计")
    public ResultDto<Object> getByHistory(StatsMonthSearchDto statsMonthSearchDto) {
        DealerUserDto dealerUserDto = (DealerUserDto) SecurityContext.getCurrentUser().getParamMap().get("dealerUserDto");
        statsMonthSearchDto.setUserId(SecurityContext.getCurrentUserId());
        statsMonthSearchDto.setDealerId(dealerUserDto.getDealerId());
        statsMonthSearchDto = statsService.getStatsByMonth(statsMonthSearchDto);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String yesterday = new SimpleDateFormat("MM月dd日").format(cal.getTime());
        statsMonthSearchDto.setExpiryDate(yesterday);
        return ResultDtoFactory.toAck("获取成功", statsMonthSearchDto);
    }

    /**
     * 代办列表
     *
     * @return
     */
    @RequestMapping(value = "/orderPage", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<StatsOrderSearchDto> getOrderPage(StatsOrderSearchDto statsOrderSearchDto) {
        String beginTime = StringUtils.EMPTY;
        String endTime = StringUtils.EMPTY;
        if (StringUtils.isNotEmpty(statsOrderSearchDto.getDateTime())) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
            String date = statsOrderSearchDto.getDateTime().replace(".", "-");//2016.01 转 2016-01
            beginTime = DateHelper.getFirstDateOfMonth(date + "-01");
            if (StringUtils.indexOf(yesterday, date) == 0) {
                endTime = yesterday;
            } else {
                endTime = DateHelper.getLastDateOfMonth(date + "-01");
            }
        } else if (StringUtils.isNotEmpty(statsOrderSearchDto.getBeginTime()) && StringUtils.isNotEmpty(statsOrderSearchDto.getEndTime())) {
            beginTime = statsOrderSearchDto.getBeginTime();
            endTime = statsOrderSearchDto.getEndTime();
        } else {
            return ResultDtoFactory.toNack("时间不能为空");
        }
        statsOrderSearchDto.setUserId(SecurityContext.getCurrentUserId());
        statsOrderSearchDto.setBeginTime(beginTime);
        statsOrderSearchDto.setEndTime(endTime);
        statsOrderSearchDto = statsService.getOrderPage(statsOrderSearchDto);
        if (StringUtils.isNotEmpty(beginTime)) {
            statsOrderSearchDto.setBeginTime(beginTime.replace("-", "."));
        }
        if (StringUtils.isNotEmpty(endTime)) {
            statsOrderSearchDto.setEndTime(DateHelper.formatDate(new Date(), "yyyy.MM.dd"));
        }
        return ResultDtoFactory.toAck("获取成功", statsOrderSearchDto);
    }

    /**
     * 店铺列表
     *
     * @return
     */
    @RequestMapping(value = "/storePage", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<StatsStoreSearchDto> getStorePage(StatsStoreSearchDto statsStoreSearchDto) {
        String beginTime = StringUtils.EMPTY;
        String endTime = StringUtils.EMPTY;
        if (StringUtils.isNotEmpty(statsStoreSearchDto.getDateTime())) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
            String date = statsStoreSearchDto.getDateTime().replace(".", "-");//2016.01 转 2016-01
            beginTime = DateHelper.getFirstDateOfMonth(date + "-01");
            if (StringUtils.indexOf(yesterday, date) == 0) {
                endTime = yesterday;
            } else {
                endTime = DateHelper.getLastDateOfMonth(date + "-01");
            }
        } else if (StringUtils.isNotEmpty(statsStoreSearchDto.getBeginTime()) && StringUtils.isNotEmpty(statsStoreSearchDto.getEndTime())) {
            beginTime = statsStoreSearchDto.getBeginTime();
            endTime = statsStoreSearchDto.getEndTime();
        } else {
            return ResultDtoFactory.toNack("时间不能为空");
        }
        statsStoreSearchDto.setUserId(SecurityContext.getCurrentUserId());
        statsStoreSearchDto.setBeginTime(beginTime);
        statsStoreSearchDto.setEndTime(endTime);
        statsStoreSearchDto = statsService.getStorePage(statsStoreSearchDto);
        if (StringUtils.isNotEmpty(beginTime)) {
            statsStoreSearchDto.setBeginTime(beginTime.replace("-", "."));
        }
        if (StringUtils.isNotEmpty(endTime)) {
            statsStoreSearchDto.setEndTime(DateHelper.formatDate(new Date(), "yyyy.MM.dd"));
        }
        return ResultDtoFactory.toAck("获取成功", statsStoreSearchDto);
    }

    /**
     * 拜访列表
     *
     * @param statsSignInfoSearchDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/signInfoPage", method = RequestMethod.GET)
    public ResultDto<StatsSignInfoSearchDto> getSignInfoPage(StatsSignInfoSearchDto statsSignInfoSearchDto) {
        String beginTime = StringUtils.EMPTY;
        String endTime = StringUtils.EMPTY;
        if (StringUtils.isNotEmpty(statsSignInfoSearchDto.getDateTime())) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
            String date = statsSignInfoSearchDto.getDateTime().replace(".", "-");//2016.01 转 2016-01
            beginTime = DateHelper.getFirstDateOfMonth(date + "-01");
            if (StringUtils.indexOf(yesterday, date) == 0) {
                endTime = yesterday;
            } else {
                endTime = DateHelper.getLastDateOfMonth(date + "-01");
            }
        } else if (StringUtils.isNotEmpty(statsSignInfoSearchDto.getBeginTime()) && StringUtils.isNotEmpty(statsSignInfoSearchDto.getEndTime())) {
            beginTime = statsSignInfoSearchDto.getBeginTime();
            endTime = statsSignInfoSearchDto.getEndTime();
        } else {
            return ResultDtoFactory.toNack("时间不能为空");
        }
        statsSignInfoSearchDto.setUserId(SecurityContext.getCurrentUserId());
        statsSignInfoSearchDto.setBeginTime(beginTime);
        statsSignInfoSearchDto.setEndTime(endTime);
        StatsSignInfoSearchDto page = statsService.getSignInfoPage(statsSignInfoSearchDto);
        if (StringUtils.isNotEmpty(beginTime)) {
            page.setBeginTime(beginTime.replace("-", "."));
        }
        if (StringUtils.isNotEmpty(endTime)) {
            page.setEndTime(DateHelper.formatDate(new Date(), "yyyy.MM.dd"));
        }
        return ResultDtoFactory.toAck("获取成功", page);
    }

    /**
     * 拜访列表
     *
     * @param statsMemberSearchDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/memberPage", method = RequestMethod.GET)
    public ResultDto<StatsMemberSearchDto> getMemberPage(StatsMemberSearchDto statsMemberSearchDto) {
        String beginTime = StringUtils.EMPTY;
        String endTime = StringUtils.EMPTY;
        if (StringUtils.isNotEmpty(statsMemberSearchDto.getDateTime())) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
            String date = statsMemberSearchDto.getDateTime().replace(".", "-");//2016.01 转 2016-01
            beginTime = DateHelper.getFirstDateOfMonth(date + "-01");
            if (StringUtils.indexOf(yesterday, date) == 0) {
                endTime = yesterday;
            } else {
                endTime = DateHelper.getLastDateOfMonth(date + "-01");
            }
        } else if (StringUtils.isNotEmpty(statsMemberSearchDto.getBeginTime()) && StringUtils.isNotEmpty(statsMemberSearchDto.getEndTime())) {
            beginTime = statsMemberSearchDto.getBeginTime();
            endTime = statsMemberSearchDto.getEndTime();
        } else {
            return ResultDtoFactory.toNack("时间不能为空");
        }
        statsMemberSearchDto.setUserId(SecurityContext.getCurrentUserId());
        statsMemberSearchDto.setBeginTime(beginTime);
        statsMemberSearchDto.setEndTime(endTime);
        StatsMemberSearchDto page = statsService.getMemberPage(statsMemberSearchDto);
        if (StringUtils.isNotEmpty(beginTime)) {
            page.setBeginTime(beginTime.replace("-", "."));
        }
        if (StringUtils.isNotEmpty(endTime)) {
            page.setEndTime(DateHelper.formatDate(new Date(), "yyyy.MM.dd"));
        }
        return ResultDtoFactory.toAck("获取成功", page);
    }

    /**
     * 通过分销员id查本周业绩统计
     *
     * @return
     */
    @RequestMapping(value = "/byWeekAndUserId", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("通过分销员id查本周业绩统计")
    public ResultDto<StatsDetailDto> getByWeekAndUserId(@RequestParam String userId) {
        DealerUserDto dealerUserDto = dealerUserService.getDealerUserById(SecurityContext.getCurrentUserId());
        if (dealerUserDto == null) {
            return ResultDtoFactory.toNack("分销员不存在");
        } else if (!dealerUserDto.getAdmin()) {
            return ResultDtoFactory.toNack("当前分销员不是管理员");
        }
        //判断分销员是不是当前分销商的
        DealerUserDto userDto = dealerUserService.getDealerUserById(userId);
        if (userDto == null) {
            return ResultDtoFactory.toNack("分销员不存在");
        } else if (!dealerUserDto.getDealerId().equals(userDto.getDealerId())) {
            return ResultDtoFactory.toNack("该分销员不属于当前分销商");
        }
        Date date = new Date();
        String beginTime = DateHelper.getFirstDateOfWeek(DateHelper.formatDate(date, "yyyy-MM-dd"));
        String endTime = DateHelper.getLastDateOfWeek(DateHelper.formatDate(date, "yyyy-MM-dd"));
        StatsDetailDto statsDetailDto = statsService.getStats(beginTime, endTime, userDto);
        return ResultDtoFactory.toAck("获取成功", statsDetailDto);
    }

    /**
     * 通过分销员id查本月业绩统计
     *
     * @return
     */
    @RequestMapping(value = "/byMonthAndUserId", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("通过分销员id查本月业绩统计")
    public ResultDto<StatsDetailDto> getByMonthAndUserId(@RequestParam String userId) {
        DealerUserDto dealerUserDto = dealerUserService.getDealerUserById(SecurityContext.getCurrentUserId());
        if (dealerUserDto == null) {
            return ResultDtoFactory.toNack("分销员不存在");
        } else if (!dealerUserDto.getAdmin()) {
            return ResultDtoFactory.toNack("当前分销员不是管理员");
        }
        //判断分销员是不是当前分销商的
        DealerUserDto userDto = dealerUserService.getDealerUserById(userId);
        if (userDto == null) {
            return ResultDtoFactory.toNack("分销员不存在");
        } else if (!dealerUserDto.getDealerId().equals(userDto.getDealerId())) {
            return ResultDtoFactory.toNack("该分销员不属于当前分销商");
        }
        Date date = new Date();
        String beginTime = DateHelper.getFirstDateOfMonth(DateHelper.formatDate(date, "yyyy-MM-dd"));
        String endTime = DateHelper.getLastDateOfMonth(DateHelper.formatDate(date, "yyyy-MM-dd"));
        StatsDetailDto statsDetailDto = statsService.getStats(beginTime, endTime, userDto);
        return ResultDtoFactory.toAck("获取成功", statsDetailDto);
    }

    /**
     * 通过分销员id查历史业绩统计
     *
     * @return
     */
    @RequestMapping(value = "/byHistoryAndUserId", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("通过分销员id查历史业绩统计")
    public ResultDto<Object> getByHistoryAndUserId(StatsMonthSearchDto statsMonthSearchDto) {
        DealerUserDto dealerUserDto = dealerUserService.getDealerUserById(SecurityContext.getCurrentUserId());
        if (dealerUserDto == null) {
            return ResultDtoFactory.toNack("分销员不存在");
        } else if (!dealerUserDto.getAdmin()) {
            return ResultDtoFactory.toNack("当前分销员不是管理员");
        }
        //判断分销员是不是当前分销商的
        DealerUserDto userDto = dealerUserService.getDealerUserById(statsMonthSearchDto.getUserId());
        if (userDto == null) {
            return ResultDtoFactory.toNack("分销员不存在");
        } else if (!dealerUserDto.getDealerId().equals(userDto.getDealerId())) {
            return ResultDtoFactory.toNack("该分销员不属于当前分销商");
        }
        statsMonthSearchDto.setUserId(userDto.getUserId());
        statsMonthSearchDto.setDealerId(userDto.getDealerId());
        statsMonthSearchDto = statsService.getStatsByMonth(statsMonthSearchDto);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String yesterday = new SimpleDateFormat("MM月dd日").format(cal.getTime());
        statsMonthSearchDto.setExpiryDate(yesterday);
        return ResultDtoFactory.toAck("获取成功", statsMonthSearchDto);
    }

    /**
     * 通过分销员id查代办列表
     *
     * @return
     */
    @RequestMapping(value = "/orderPageByUserId", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("通过分销员id查代办列表")
    public ResultDto<StatsOrderSearchDto> getOrderPageByUserId(StatsOrderSearchDto statsOrderSearchDto) {
        DealerUserDto dealerUserDto = dealerUserService.getDealerUserById(SecurityContext.getCurrentUserId());
        if (dealerUserDto == null) {
            return ResultDtoFactory.toNack("分销员不存在");
        } else if (!dealerUserDto.getAdmin()) {
            return ResultDtoFactory.toNack("当前分销员不是管理员");
        }
        //判断分销员是不是当前分销商的
        DealerUserDto userDto = dealerUserService.getDealerUserById(statsOrderSearchDto.getUserId());
        if (userDto == null) {
            return ResultDtoFactory.toNack("分销员不存在");
        } else if (!dealerUserDto.getDealerId().equals(userDto.getDealerId())) {
            return ResultDtoFactory.toNack("该分销员不属于当前分销商");
        }
        String beginTime = StringUtils.EMPTY;
        String endTime = StringUtils.EMPTY;
        if (StringUtils.isNotEmpty(statsOrderSearchDto.getDateTime())) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
            String date = statsOrderSearchDto.getDateTime().replace(".", "-");//2016.01 转 2016-01
            beginTime = DateHelper.getFirstDateOfMonth(date + "-01");
            if (StringUtils.indexOf(yesterday, date) == 0) {
                endTime = yesterday;
            } else {
                endTime = DateHelper.getLastDateOfMonth(date + "-01");
            }
        } else if (StringUtils.isNotEmpty(statsOrderSearchDto.getBeginTime()) && StringUtils.isNotEmpty(statsOrderSearchDto.getEndTime())) {
            beginTime = statsOrderSearchDto.getBeginTime();
            endTime = statsOrderSearchDto.getEndTime();
        } else {
            return ResultDtoFactory.toNack("时间不能为空");
        }
        statsOrderSearchDto.setUserId(userDto.getUserId());
        statsOrderSearchDto.setDealerId(userDto.getDealerId());
        statsOrderSearchDto.setBeginTime(beginTime);
        statsOrderSearchDto.setEndTime(endTime);
        statsOrderSearchDto = statsService.getOrderPage(statsOrderSearchDto);
        if (StringUtils.isNotEmpty(beginTime)) {
            statsOrderSearchDto.setBeginTime(beginTime.replace("-", "."));
        }
        if (StringUtils.isNotEmpty(endTime)) {
            statsOrderSearchDto.setEndTime(DateHelper.formatDate(new Date(), "yyyy.MM.dd"));
        }
        return ResultDtoFactory.toAck("获取成功", statsOrderSearchDto);
    }

    /**
     * 通过分销员id查店铺列表
     *
     * @return
     */
    @RequestMapping(value = "/storePageByUserId", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("通过分销员id查店铺列表")
    public ResultDto<StatsStoreSearchDto> getStorePageByUserId(StatsStoreSearchDto statsStoreSearchDto) {
        DealerUserDto dealerUserDto = dealerUserService.getDealerUserById(SecurityContext.getCurrentUserId());
        if (dealerUserDto == null) {
            return ResultDtoFactory.toNack("分销员不存在");
        } else if (!dealerUserDto.getAdmin()) {
            return ResultDtoFactory.toNack("当前分销员不是管理员");
        }
        //判断分销员是不是当前分销商的
        DealerUserDto userDto = dealerUserService.getDealerUserById(statsStoreSearchDto.getUserId());
        if (userDto == null) {
            return ResultDtoFactory.toNack("分销员不存在");
        } else if (!dealerUserDto.getDealerId().equals(userDto.getDealerId())) {
            return ResultDtoFactory.toNack("该分销员不属于当前分销商");
        }
        String beginTime = StringUtils.EMPTY;
        String endTime = StringUtils.EMPTY;
        if (StringUtils.isNotEmpty(statsStoreSearchDto.getDateTime())) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
            String date = statsStoreSearchDto.getDateTime().replace(".", "-");//2016.01 转 2016-01
            beginTime = DateHelper.getFirstDateOfMonth(date + "-01");
            if (StringUtils.indexOf(yesterday, date) == 0) {
                endTime = yesterday;
            } else {
                endTime = DateHelper.getLastDateOfMonth(date + "-01");
            }
        } else if (StringUtils.isNotEmpty(statsStoreSearchDto.getBeginTime()) && StringUtils.isNotEmpty(statsStoreSearchDto.getEndTime())) {
            beginTime = statsStoreSearchDto.getBeginTime();
            endTime = statsStoreSearchDto.getEndTime();
        } else {
            return ResultDtoFactory.toNack("时间不能为空");
        }
        statsStoreSearchDto.setUserId(userDto.getUserId());
        statsStoreSearchDto.setDealerId(userDto.getDealerId());
        statsStoreSearchDto.setBeginTime(beginTime);
        statsStoreSearchDto.setEndTime(endTime);
        statsStoreSearchDto = statsService.getStorePage(statsStoreSearchDto);
        if (StringUtils.isNotEmpty(beginTime)) {
            statsStoreSearchDto.setBeginTime(beginTime.replace("-", "."));
        }
        if (StringUtils.isNotEmpty(endTime)) {
            statsStoreSearchDto.setEndTime(DateHelper.formatDate(new Date(), "yyyy.MM.dd"));
        }
        return ResultDtoFactory.toAck("获取成功", statsStoreSearchDto);
    }

    /**
     * 通过分销员id查拜访列表
     *
     * @param statsSignInfoSearchDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/signInfoPageByUserId", method = RequestMethod.GET)
    @ApiOperation("通过分销员id查拜访列表")
    public ResultDto<StatsSignInfoSearchDto> getSignInfoPageByUserId(StatsSignInfoSearchDto statsSignInfoSearchDto) {
        DealerUserDto dealerUserDto = dealerUserService.getDealerUserById(SecurityContext.getCurrentUserId());
        if (dealerUserDto == null) {
            return ResultDtoFactory.toNack("分销员不存在");
        } else if (!dealerUserDto.getAdmin()) {
            return ResultDtoFactory.toNack("当前分销员不是管理员");
        }
        //判断分销员是不是当前分销商的
        DealerUserDto userDto = dealerUserService.getDealerUserById(statsSignInfoSearchDto.getUserId());
        if (userDto == null) {
            return ResultDtoFactory.toNack("分销员不存在");
        } else if (!dealerUserDto.getDealerId().equals(userDto.getDealerId())) {
            return ResultDtoFactory.toNack("该分销员不属于当前分销商");
        }
        String beginTime = StringUtils.EMPTY;
        String endTime = StringUtils.EMPTY;
        if (StringUtils.isNotEmpty(statsSignInfoSearchDto.getDateTime())) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
            String date = statsSignInfoSearchDto.getDateTime().replace(".", "-");//2016.01 转 2016-01
            beginTime = DateHelper.getFirstDateOfMonth(date + "-01");
            if (StringUtils.indexOf(yesterday, date) == 0) {
                endTime = yesterday;
            } else {
                endTime = DateHelper.getLastDateOfMonth(date + "-01");
            }
        } else if (StringUtils.isNotEmpty(statsSignInfoSearchDto.getBeginTime()) && StringUtils.isNotEmpty(statsSignInfoSearchDto.getEndTime())) {
            beginTime = statsSignInfoSearchDto.getBeginTime();
            endTime = statsSignInfoSearchDto.getEndTime();
        } else {
            return ResultDtoFactory.toNack("时间不能为空");
        }
        statsSignInfoSearchDto.setUserId(userDto.getUserId());
        statsSignInfoSearchDto.setDealerId(userDto.getDealerId());
        statsSignInfoSearchDto.setBeginTime(beginTime);
        statsSignInfoSearchDto.setEndTime(endTime);
        StatsSignInfoSearchDto page = statsService.getSignInfoPage(statsSignInfoSearchDto);
        if (StringUtils.isNotEmpty(beginTime)) {
            page.setBeginTime(beginTime.replace("-", "."));
        }
        if (StringUtils.isNotEmpty(endTime)) {
            page.setEndTime(DateHelper.formatDate(new Date(), "yyyy.MM.dd"));
        }
        return ResultDtoFactory.toAck("获取成功", page);
    }

    /**
     * 通过分销员id查云码列表
     *
     * @param statsMemberSearchDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/memberPageByUserId", method = RequestMethod.GET)
    @ApiOperation("通过分销员id查云码列表")
    public ResultDto<StatsMemberSearchDto> getMemberPageByUserId(StatsMemberSearchDto statsMemberSearchDto) {
        DealerUserDto dealerUserDto = dealerUserService.getDealerUserById(SecurityContext.getCurrentUserId());
        if (dealerUserDto == null) {
            return ResultDtoFactory.toNack("分销员不存在");
        } else if (!dealerUserDto.getAdmin()) {
            return ResultDtoFactory.toNack("当前分销员不是管理员");
        }
        //判断分销员是不是当前分销商的
        DealerUserDto userDto = dealerUserService.getDealerUserById(statsMemberSearchDto.getUserId());
        if (userDto == null) {
            return ResultDtoFactory.toNack("分销员不存在");
        } else if (!dealerUserDto.getDealerId().equals(userDto.getDealerId())) {
            return ResultDtoFactory.toNack("该分销员不属于当前分销商");
        }
        String beginTime = StringUtils.EMPTY;
        String endTime = StringUtils.EMPTY;
        if (StringUtils.isNotEmpty(statsMemberSearchDto.getDateTime())) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
            String date = statsMemberSearchDto.getDateTime().replace(".", "-");//2016.01 转 2016-01
            beginTime = DateHelper.getFirstDateOfMonth(date + "-01");
            if (StringUtils.indexOf(yesterday, date) == 0) {
                endTime = yesterday;
            } else {
                endTime = DateHelper.getLastDateOfMonth(date + "-01");
            }
        } else if (StringUtils.isNotEmpty(statsMemberSearchDto.getBeginTime()) && StringUtils.isNotEmpty(statsMemberSearchDto.getEndTime())) {
            beginTime = statsMemberSearchDto.getBeginTime();
            endTime = statsMemberSearchDto.getEndTime();
        } else {
            return ResultDtoFactory.toNack("时间不能为空");
        }
        statsMemberSearchDto.setUserId(userDto.getUserId());
        statsMemberSearchDto.setDealerId(userDto.getDealerId());
        statsMemberSearchDto.setBeginTime(beginTime);
        statsMemberSearchDto.setEndTime(endTime);
        StatsMemberSearchDto page = statsService.getMemberPage(statsMemberSearchDto);
        if (StringUtils.isNotEmpty(beginTime)) {
            page.setBeginTime(beginTime.replace("-", "."));
        }
        if (StringUtils.isNotEmpty(endTime)) {
            page.setEndTime(DateHelper.formatDate(new Date(), "yyyy.MM.dd"));
        }
        return ResultDtoFactory.toAck("获取成功", page);
    }

    /**
     * 通过分销商id查本周业绩统计
     *
     * @return
     */
    @RequestMapping(value = "/byWeekByDealerId", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("通过分销商id查本周业绩统计")
    public ResultDto<StatsDetailDto> getByWeekByDealerId() {
        DealerUserDto dealerUserDto = dealerUserService.getDealerUserById(SecurityContext.getCurrentUserId());
        if (dealerUserDto == null) {
            return ResultDtoFactory.toNack("分销员不存在");
        } else if (!dealerUserDto.getAdmin()) {
            return ResultDtoFactory.toNack("当前分销员不是管理员");
        }
        //查当前分销商业绩
        dealerUserDto.setUserId(null);
        Date date = new Date();
        String beginTime = DateHelper.getFirstDateOfWeek(DateHelper.formatDate(date, "yyyy-MM-dd"));
        String endTime = DateHelper.getLastDateOfWeek(DateHelper.formatDate(date, "yyyy-MM-dd"));
        StatsDetailDto statsDetailDto = statsService.getStats(beginTime, endTime, dealerUserDto);
        return ResultDtoFactory.toAck("获取成功", statsDetailDto);
    }

    /**
     * 通过分销商id查本月业绩统计
     *
     * @return
     */
    @RequestMapping(value = "/byMonthByDealerId", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("通过分销商id查本月业绩统计")
    public ResultDto<StatsDetailDto> getByMonthByDealerId() {
        DealerUserDto dealerUserDto = dealerUserService.getDealerUserById(SecurityContext.getCurrentUserId());
        if (dealerUserDto == null) {
            return ResultDtoFactory.toNack("分销员不存在");
        } else if (!dealerUserDto.getAdmin()) {
            return ResultDtoFactory.toNack("当前分销员不是管理员");
        }
        //查当前分销商业绩
        dealerUserDto.setUserId(null);
        Date date = new Date();
        String beginTime = DateHelper.getFirstDateOfMonth(DateHelper.formatDate(date, "yyyy-MM-dd"));
        String endTime = DateHelper.getLastDateOfMonth(DateHelper.formatDate(date, "yyyy-MM-dd"));
        StatsDetailDto statsDetailDto = statsService.getStats(beginTime, endTime, dealerUserDto);
        return ResultDtoFactory.toAck("获取成功", statsDetailDto);
    }

    /**
     * 历史业绩统计
     *
     * @return
     */
    @RequestMapping(value = "/byHistoryByDealerId", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("通过分销商id查历史业绩统计")
    public ResultDto<Object> getByHistoryByDealerId(StatsMonthSearchDto statsMonthSearchDto) {
        DealerUserDto dealerUserDto = dealerUserService.getDealerUserById(SecurityContext.getCurrentUserId());
        if (dealerUserDto == null) {
            return ResultDtoFactory.toNack("分销员不存在");
        } else if (!dealerUserDto.getAdmin()) {
            return ResultDtoFactory.toNack("当前分销员不是管理员");
        }
        statsMonthSearchDto.setDealerId(dealerUserDto.getDealerId());
        statsMonthSearchDto = statsService.getStatsByMonth(statsMonthSearchDto);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String yesterday = new SimpleDateFormat("MM月dd日").format(cal.getTime());
        statsMonthSearchDto.setExpiryDate(yesterday);
        return ResultDtoFactory.toAck("获取成功", statsMonthSearchDto);
    }

    /**
     * 通过分销商id查代办列表
     *
     * @return
     */
    @RequestMapping(value = "/orderPageByDealerId", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("通过分销商id查代办列表")
    public ResultDto<StatsOrderSearchDto> getOrderPageByDealerId(StatsOrderSearchDto statsOrderSearchDto) {
        DealerUserDto dealerUserDto = dealerUserService.getDealerUserById(SecurityContext.getCurrentUserId());
        if (dealerUserDto == null) {
            return ResultDtoFactory.toNack("分销员不存在");
        } else if (!dealerUserDto.getAdmin()) {
            return ResultDtoFactory.toNack("当前分销员不是管理员");
        }

        String beginTime = StringUtils.EMPTY;
        String endTime = StringUtils.EMPTY;
        if (StringUtils.isNotEmpty(statsOrderSearchDto.getDateTime())) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
            String date = statsOrderSearchDto.getDateTime().replace(".", "-");//2016.01 转 2016-01
            beginTime = DateHelper.getFirstDateOfMonth(date + "-01");
            if (StringUtils.indexOf(yesterday, date) == 0) {
                endTime = yesterday;
            } else {
                endTime = DateHelper.getLastDateOfMonth(date + "-01");
            }
        } else if (StringUtils.isNotEmpty(statsOrderSearchDto.getBeginTime()) && StringUtils.isNotEmpty(statsOrderSearchDto.getEndTime())) {
            beginTime = statsOrderSearchDto.getBeginTime();
            endTime = statsOrderSearchDto.getEndTime();
        } else {
            return ResultDtoFactory.toNack("时间不能为空");
        }
        statsOrderSearchDto.setDealerId(dealerUserDto.getDealerId());
        statsOrderSearchDto.setBeginTime(beginTime);
        statsOrderSearchDto.setEndTime(endTime);
        statsOrderSearchDto = statsService.getOrderPage(statsOrderSearchDto);
        if (StringUtils.isNotEmpty(beginTime)) {
            statsOrderSearchDto.setBeginTime(beginTime.replace("-", "."));
        }
        if (StringUtils.isNotEmpty(endTime)) {
            statsOrderSearchDto.setEndTime(DateHelper.formatDate(new Date(), "yyyy.MM.dd"));
        }
        return ResultDtoFactory.toAck("获取成功", statsOrderSearchDto);
    }

    /**
     * 通过分销商id查店铺列表
     *
     * @return
     */
    @RequestMapping(value = "/storePageByDealerId", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("通过分销商id查店铺列表")
    public ResultDto<StatsStoreSearchDto> getStorePageByDealerId(StatsStoreSearchDto statsStoreSearchDto) {
        DealerUserDto dealerUserDto = dealerUserService.getDealerUserById(SecurityContext.getCurrentUserId());
        if (dealerUserDto == null) {
            return ResultDtoFactory.toNack("分销员不存在");
        } else if (!dealerUserDto.getAdmin()) {
            return ResultDtoFactory.toNack("当前分销员不是管理员");
        }

        String beginTime = StringUtils.EMPTY;
        String endTime = StringUtils.EMPTY;
        if (StringUtils.isNotEmpty(statsStoreSearchDto.getDateTime())) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
            String date = statsStoreSearchDto.getDateTime().replace(".", "-");//2016.01 转 2016-01
            beginTime = DateHelper.getFirstDateOfMonth(date + "-01");
            if (StringUtils.indexOf(yesterday, date) == 0) {
                endTime = yesterday;
            } else {
                endTime = DateHelper.getLastDateOfMonth(date + "-01");
            }
        } else if (StringUtils.isNotEmpty(statsStoreSearchDto.getBeginTime()) && StringUtils.isNotEmpty(statsStoreSearchDto.getEndTime())) {
            beginTime = statsStoreSearchDto.getBeginTime();
            endTime = statsStoreSearchDto.getEndTime();
        } else {
            return ResultDtoFactory.toNack("时间不能为空");
        }
        statsStoreSearchDto.setDealerId(dealerUserDto.getDealerId());
        statsStoreSearchDto.setBeginTime(beginTime);
        statsStoreSearchDto.setEndTime(endTime);
        statsStoreSearchDto = statsService.getStorePage(statsStoreSearchDto);
        if (StringUtils.isNotEmpty(beginTime)) {
            statsStoreSearchDto.setBeginTime(beginTime.replace("-", "."));
        }
        if (StringUtils.isNotEmpty(endTime)) {
            statsStoreSearchDto.setEndTime(DateHelper.formatDate(new Date(), "yyyy.MM.dd"));
        }
        return ResultDtoFactory.toAck("获取成功", statsStoreSearchDto);
    }

    /**
     * 通过分销商id查拜访列表
     *
     * @param statsSignInfoSearchDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/signInfoPageByDealerId", method = RequestMethod.GET)
    @ApiOperation("通过分销商id查拜访列表")
    public ResultDto<StatsSignInfoSearchDto> getSignInfoPageByDealerId(StatsSignInfoSearchDto statsSignInfoSearchDto) {
        DealerUserDto dealerUserDto = dealerUserService.getDealerUserById(SecurityContext.getCurrentUserId());
        if (dealerUserDto == null) {
            return ResultDtoFactory.toNack("分销员不存在");
        } else if (!dealerUserDto.getAdmin()) {
            return ResultDtoFactory.toNack("当前分销员不是管理员");
        }

        String beginTime = StringUtils.EMPTY;
        String endTime = StringUtils.EMPTY;
        if (StringUtils.isNotEmpty(statsSignInfoSearchDto.getDateTime())) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
            String date = statsSignInfoSearchDto.getDateTime().replace(".", "-");//2016.01 转 2016-01
            beginTime = DateHelper.getFirstDateOfMonth(date + "-01");
            if (StringUtils.indexOf(yesterday, date) == 0) {
                endTime = yesterday;
            } else {
                endTime = DateHelper.getLastDateOfMonth(date + "-01");
            }
        } else if (StringUtils.isNotEmpty(statsSignInfoSearchDto.getBeginTime()) && StringUtils.isNotEmpty(statsSignInfoSearchDto.getEndTime())) {
            beginTime = statsSignInfoSearchDto.getBeginTime();
            endTime = statsSignInfoSearchDto.getEndTime();
        } else {
            return ResultDtoFactory.toNack("时间不能为空");
        }
        statsSignInfoSearchDto.setDealerId(dealerUserDto.getDealerId());
        statsSignInfoSearchDto.setBeginTime(beginTime);
        statsSignInfoSearchDto.setEndTime(endTime);
        StatsSignInfoSearchDto page = statsService.getSignInfoPage(statsSignInfoSearchDto);
        if (StringUtils.isNotEmpty(beginTime)) {
            page.setBeginTime(beginTime.replace("-", "."));
        }
        if (StringUtils.isNotEmpty(endTime)) {
            page.setEndTime(DateHelper.formatDate(new Date(), "yyyy.MM.dd"));
        }
        return ResultDtoFactory.toAck("获取成功", page);
    }

    /**
     * 通过分销商id查云码列表
     *
     * @param statsMemberSearchDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/memberPageByDealerId", method = RequestMethod.GET)
    @ApiOperation("通过分销商id查云码列表")
    public ResultDto<StatsMemberSearchDto> getMemberPageByDealerId(StatsMemberSearchDto statsMemberSearchDto) {
        DealerUserDto dealerUserDto = dealerUserService.getDealerUserById(SecurityContext.getCurrentUserId());
        if (dealerUserDto == null) {
            return ResultDtoFactory.toNack("分销员不存在");
        } else if (!dealerUserDto.getAdmin()) {
            return ResultDtoFactory.toNack("当前分销员不是管理员");
        }

        String beginTime = StringUtils.EMPTY;
        String endTime = StringUtils.EMPTY;
        if (StringUtils.isNotEmpty(statsMemberSearchDto.getDateTime())) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
            String date = statsMemberSearchDto.getDateTime().replace(".", "-");//2016.01 转 2016-01
            beginTime = DateHelper.getFirstDateOfMonth(date + "-01");
            if (StringUtils.indexOf(yesterday, date) == 0) {
                endTime = yesterday;
            } else {
                endTime = DateHelper.getLastDateOfMonth(date + "-01");
            }
        } else if (StringUtils.isNotEmpty(statsMemberSearchDto.getBeginTime()) && StringUtils.isNotEmpty(statsMemberSearchDto.getEndTime())) {
            beginTime = statsMemberSearchDto.getBeginTime();
            endTime = statsMemberSearchDto.getEndTime();
        } else {
            return ResultDtoFactory.toNack("时间不能为空");
        }
        statsMemberSearchDto.setDealerId(dealerUserDto.getDealerId());
        statsMemberSearchDto.setBeginTime(beginTime);
        statsMemberSearchDto.setEndTime(endTime);
        StatsMemberSearchDto page = statsService.getMemberPage(statsMemberSearchDto);
        if (StringUtils.isNotEmpty(beginTime)) {
            page.setBeginTime(beginTime.replace("-", "."));
        }
        if (StringUtils.isNotEmpty(endTime)) {
            page.setEndTime(DateHelper.formatDate(new Date(), "yyyy.MM.dd"));
        }
        return ResultDtoFactory.toAck("获取成功", page);
    }

}
