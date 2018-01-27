package com.xinyunlian.jinfu.exam.controller;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.exam.dto.ExamDto;
import com.xinyunlian.jinfu.exam.dto.ExamUserDto;
import com.xinyunlian.jinfu.exam.dto.ExamUserViewSearchDto;
import com.xinyunlian.jinfu.exam.dto.FirstExamDto;
import com.xinyunlian.jinfu.exam.enums.EExamType;
import com.xinyunlian.jinfu.exam.enums.EExamUserStatus;
import com.xinyunlian.jinfu.exam.service.ExamService;
import com.xinyunlian.jinfu.exam.service.ExamUserService;
import com.xinyunlian.jinfu.user.dto.DealerUserDto;
import com.xinyunlian.jinfu.user.service.DealerUserService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by menglei on 2017年05月02日.
 */
@Controller
@RequestMapping(value = "exam")
public class ExamController {

    @Autowired
    private ExamService examService;
    @Autowired
    private ExamUserService examUserService;
    @Autowired
    private DealerUserService dealerUserService;

    /**
     * 添加考试成绩
     *
     * @param examUserDto examId score userId
     * @return
     */
    @RequestMapping(value = "/saveExam", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "添加考试成绩")
    public ResultDto<Object> saveExam(@RequestBody ExamUserDto examUserDto) {
        if (!SecurityContext.getCurrentUserId().equals(examUserDto.getUserId())) {
            return ResultDtoFactory.toNack("考试信息与本人不符");
        }
        ExamDto exam = examService.getOne(examUserDto.getExamId());
        if (exam == null) {
            return ResultDtoFactory.toNack("考试不存在");
        }
        //是否要判断考试时间
        ExamUserDto examUser = examUserService.getByUserIdAndExamId(SecurityContext.getCurrentUserId(), examUserDto.getExamId());
        if (examUser != null) {
            return ResultDtoFactory.toNack("已参加过该考试");
        }
        DealerUserDto dealerUserDto = (DealerUserDto) SecurityContext.getCurrentUser().getParamMap().get("dealerUserDto");
        ExamUserDto dto = new ExamUserDto();
        dto.setDealerId(dealerUserDto.getDealerId());
        dto.setUserId(dealerUserDto.getUserId());
        dto.setExamId(examUserDto.getExamId());
        dto.setScore(examUserDto.getScore());
        EExamUserStatus status = EExamUserStatus.FAIL;
        if (examUserDto.getScore() >= exam.getPassLine()) {
            status = EExamUserStatus.PASS;
        }
        dto.setStatus(status);
        examUserService.createExamUser(dto);
        //首考需更新分销员考试状态
        if (EExamType.FIRST.equals(exam.getType())) {
            DealerUserDto dealerUser = dealerUserService.getDealerUserById(SecurityContext.getCurrentUserId());
            if (dealerUser.getExamPassed()) {
                return ResultDtoFactory.toNack("已通过首考");
            }
            if (examUserDto.getScore() >= exam.getPassLine()) {
                dealerUserService.updateExamPass(SecurityContext.getCurrentUserId());
            }
        }
        return ResultDtoFactory.toAck("添加成功");
    }

    /**
     * 获取首考信息
     *
     * @return url sojumpparm=userId-examId-passLine
     */
    @ResponseBody
    @RequestMapping(value = "/getFirstExam", method = RequestMethod.GET)
    @ApiOperation(value = "获取考试信息")
    public ResultDto<Map<String, Object>> getFirstExam() {
        DealerUserDto dealerUser = dealerUserService.getDealerUserById(SecurityContext.getCurrentUserId());

        Map<String, Object> resultMap = new HashMap<>();
        if (dealerUser == null) {
            return ResultDtoFactory.toNack("用户不存在");
        } else if (dealerUser.getExamPassed()) {
            resultMap.put("status", 5);
            resultMap.put("exam", null);
            return ResultDtoFactory.toAck("您已通过此次考试", resultMap);
        }

        //参加过的考试过滤 获取可以参加的首考
        List<ExamUserDto> examUserDtoList = examUserService.getByUserId(SecurityContext.getCurrentUserId());
        List<Long> examIds = new ArrayList<>();
        for (ExamUserDto examUserDto : examUserDtoList) {
            examIds.add(examUserDto.getExamId());
        }
        List<Long> canFirstExamIds = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(examIds)) {
            List<ExamDto> canFirstExams = examService.getCanFirstExamList(examIds);
            if (CollectionUtils.isEmpty(canFirstExams)) {
                resultMap.put("status", 1);
                resultMap.put("exam", null);
                return ResultDtoFactory.toAck("您已参加过此次考试", resultMap);
            }
            for (ExamDto examDto : canFirstExams) {
                canFirstExamIds.add(examDto.getExamId());
            }
        }

        ExamDto examDto = new ExamDto();
        examDto.setType(EExamType.FIRST);
        Date date = new Date();
        //正在进行中的考试
        examDto.setStartTime(date);
        examDto.setEndTime(date);
        //已考试过，可以参加的首考
        examDto.setExamIds(canFirstExamIds);
        List<ExamDto> list = examService.getValidExamList(examDto);
        if (CollectionUtils.isEmpty(list)) {
            //未开始的考试
            examDto.setStartTime(null);
            list = examService.getValidExamList(examDto);
        }
        if (CollectionUtils.isEmpty(list)) {
            resultMap.put("status", 2);
            resultMap.put("exam", null);
            return ResultDtoFactory.toAck("最近暂无准入考试", resultMap);
        }
        ExamDto exam = list.get(0);
        FirstExamDto dto = ConverterService.convert(exam, FirstExamDto.class);
        dto.setStartTime(DateHelper.formatDate(exam.getStartTime(), "yyyy年MM月dd日 HH:mm"));
        dto.setEndTime(DateHelper.formatDate(exam.getEndTime(), "yyyy年MM月dd日 HH:mm"));
        if (examDto.getStartTime() == null) {
            //未开始的考试
            dto.setUrl(StringUtils.EMPTY);
            resultMap.put("status", 3);
        } else {
            String queryString = "?sojumpparm=" + dealerUser.getUserId() + "-" + exam.getExamId() + "-" + exam.getPassLine() + "-" + System.currentTimeMillis();
            dto.setUrl(exam.getUrl() + queryString);
            resultMap.put("status", 4);
        }
        resultMap.put("exam", dto);
        return ResultDtoFactory.toAck("获取成功", resultMap);
    }

    /**
     * 获取常规考试信息
     *
     * @return url sojumpparm=userId-examId-passLine
     */
    @ResponseBody
    @RequestMapping(value = "/getExam", method = RequestMethod.GET)
    @ApiOperation(value = "获取常规考试信息")
    public ResultDto<Map<String, Object>> getExam() {
        DealerUserDto dealerUser = dealerUserService.getDealerUserById(SecurityContext.getCurrentUserId());

        Map<String, Object> resultMap = new HashMap<>();
        if (dealerUser == null) {
            return ResultDtoFactory.toNack("用户不存在");
        } else if (!dealerUser.getExamPassed()) {
            return ResultDtoFactory.toNack("您未通过首考");
        }
        //参加过的考试过滤 获取可以参加的常规考试
        List<ExamUserDto> examUserDtoList = examUserService.getByUserId(SecurityContext.getCurrentUserId());
        List<Long> examIds = new ArrayList<>();
        for (ExamUserDto examUserDto : examUserDtoList) {
            examIds.add(examUserDto.getExamId());
        }
        List<Long> canExamIds = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(examIds)) {
            //当前有进行中的考试，并已参加
            ExamDto examDto = new ExamDto();
            examDto.setType(EExamType.ROUTINE);
            Date date = new Date();
            examDto.setStartTime(date);
            examDto.setEndTime(date);
            List<ExamDto> ingList = examService.getValidExamList(examDto);//当前有进行中的考试
            examDto.setExamIds(examIds);
            List<ExamDto> joinedList = examService.getValidExamList(examDto);//当前进行中已参加的考试
            if (CollectionUtils.isNotEmpty(ingList) && CollectionUtils.isNotEmpty(joinedList)) {
                if (ingList.size() == joinedList.size()) {
                    resultMap.put("status", 1);
                    resultMap.put("exam", ingList.get(0));
                    return ResultDtoFactory.toAck("您已参加过此次考试", resultMap);
                }
            }
            //可参加的考试
            List<ExamDto> canExams = examService.getCanExamList(examIds);
            if (CollectionUtils.isEmpty(canExams)) {
                resultMap.put("status", 2);
                resultMap.put("exam", null);
                return ResultDtoFactory.toAck("近期暂无考试", resultMap);
            }
            for (ExamDto dto : canExams) {
                canExamIds.add(dto.getExamId());
            }
        }

        ExamDto examDto = new ExamDto();
        examDto.setType(EExamType.ROUTINE);
        Date date = new Date();
        //正在进行中的考试
        examDto.setStartTime(date);
        examDto.setEndTime(date);
        //已考试过，可以参加的常规考试
        examDto.setExamIds(canExamIds);
        List<ExamDto> list = examService.getValidExamList(examDto);
        if (CollectionUtils.isEmpty(list)) {
            //未开始的考试
            examDto.setStartTime(null);
            list = examService.getValidExamList(examDto);
        }
        if (CollectionUtils.isEmpty(list)) {
            resultMap.put("status", 2);
            resultMap.put("exam", null);
            return ResultDtoFactory.toAck("近期暂无考试", resultMap);
        }
        ExamDto exam = list.get(0);
        FirstExamDto dto = ConverterService.convert(exam, FirstExamDto.class);
        dto.setStartTime(DateHelper.formatDate(exam.getStartTime(), "yyyy年MM月dd日 HH:mm"));
        dto.setEndTime(DateHelper.formatDate(exam.getEndTime(), "yyyy年MM月dd日 HH:mm"));
        if (examDto.getStartTime() == null) {
            //未开始的考试
            dto.setUrl(StringUtils.EMPTY);
            resultMap.put("status", 3);
        } else {
            String queryString = "?sojumpparm=" + dealerUser.getUserId() + "-" + exam.getExamId() + "-" + exam.getPassLine() + "-" + System.currentTimeMillis();
            dto.setUrl(exam.getUrl() + queryString);
            resultMap.put("status", 4);
        }
        resultMap.put("exam", dto);
        return ResultDtoFactory.toAck("获取成功", resultMap);
    }

    /**
     * 获取历史考试成绩
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getHistoryExamPage", method = RequestMethod.GET)
    @ApiOperation(value = "获取历史考试成绩")
    public ResultDto<ExamUserViewSearchDto> getHistoryExamPage(ExamUserViewSearchDto examUserViewSearchDto) {
        examUserViewSearchDto.setUserId(SecurityContext.getCurrentUserId());
        examUserViewSearchDto = examUserService.getExamUserViewPage(examUserViewSearchDto);
        return ResultDtoFactory.toAck("获取成功", examUserViewSearchDto);
    }

}
