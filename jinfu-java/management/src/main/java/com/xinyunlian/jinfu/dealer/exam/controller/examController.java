package com.xinyunlian.jinfu.dealer.exam.controller;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.util.excel.DataTablesExcelService;
import com.xinyunlian.jinfu.dealer.exam.dto.ExamUserReportDto;
import com.xinyunlian.jinfu.exam.dto.ExamDto;
import com.xinyunlian.jinfu.exam.dto.ExamSearchDto;
import com.xinyunlian.jinfu.exam.dto.ExamUserViewDto;
import com.xinyunlian.jinfu.exam.enums.EExamStatus;
import com.xinyunlian.jinfu.exam.service.ExamService;
import com.xinyunlian.jinfu.exam.service.ExamUserService;
import com.xinyunlian.jinfu.user.dto.MgtUserDto;
import com.xinyunlian.jinfu.user.service.MgtUserService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
 * Created by menglei on 2017年05月05日.
 */
@Controller
@RequestMapping(value = "dealer/exam")
@RequiresPermissions({"EXAM_MGT"})
public class examController {

    @Autowired
    private ExamService examService;
    @Autowired
    private MgtUserService mgtUserService;
    @Autowired
    private ExamUserService examUserService;

    /**
     * 考试列表
     *
     * @param examSearchDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "考试列表")
    public ResultDto<ExamSearchDto> getPage(ExamSearchDto examSearchDto) {
        ExamSearchDto page = examService.getExamPage(examSearchDto);
        //获取创建人
        List<String> mgtUserIds = new ArrayList<>();
        for (ExamDto examDto : page.getList()) {
            if (StringUtils.isNotEmpty(examDto.getCreateOpId()) && !"system".equals(examDto.getCreateOpId())) {
                mgtUserIds.add(examDto.getCreateOpId());
            }
        }
        List<MgtUserDto> mgtUsers = mgtUserService.findByMgtUserIds(mgtUserIds);
        Map<String, MgtUserDto> mgtUserMap = new HashMap<>();
        for (MgtUserDto mgtUserDto : mgtUsers) {
            mgtUserMap.put(mgtUserDto.getUserId(), mgtUserDto);
        }
        List<ExamDto> list = new ArrayList<>();
        for (ExamDto examDto : page.getList()) {
            MgtUserDto mgtUserDto = mgtUserMap.get(examDto.getCreateOpId());
            if (mgtUserDto != null) {
                examDto.setCreateOpName(mgtUserDto.getName());
            }
            list.add(examDto);
        }
        page.setList(list);
        return ResultDtoFactory.toAck("获取成功", page);
    }

    /**
     * 添加考试
     *
     * @param examDto
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "添加考试")
    public ResultDto<Object> add(@RequestBody ExamDto examDto) {
        if (examDto.getPassLine() <= 0) {
            return ResultDtoFactory.toNack("合格分数线必须大于0");
        }
        if (examDto.getEndTime().compareTo(examDto.getStartTime()) <= 0) {
            return ResultDtoFactory.toNack("开始时间必须小于截止时间");
        }
        examService.createExam(examDto);
        return ResultDtoFactory.toAck("添加成功");
    }

    /**
     * 详情
     *
     * @param examId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ApiOperation(value = "详情")
    public ResultDto<ExamDto> getDetail(@RequestParam Long examId) {
        ExamDto dto = examService.getOne(examId);
        return ResultDtoFactory.toAck("获取成功", dto);
    }

    /**
     * 编辑考试
     *
     * @param examDto
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "编辑考试")
    public ResultDto<Object> update(@RequestBody ExamDto examDto) {
        ExamDto dto = examService.getOne(examDto.getExamId());
        if (dto == null) {
            return ResultDtoFactory.toNack("考试不存在");
        }
        if (new Date().compareTo(examDto.getStartTime()) >= 0) {
            return ResultDtoFactory.toNack("该考试进行中，无法修改");
        } else if (examDto.getStatus() == EExamStatus.DELETE) {
            return ResultDtoFactory.toNack("该考试已经关闭");
        }
        if (examDto.getPassLine() <= 0) {
            return ResultDtoFactory.toNack("合格分数线必须大于0");
        }
        if (examDto.getEndTime().compareTo(examDto.getStartTime()) <= 0) {
            return ResultDtoFactory.toNack("开始时间必须小于截止时间");
        }
        examService.updateExam(examDto);
        return ResultDtoFactory.toAck("添加成功");
    }

    /**
     * 关闭考试
     *
     * @param examId
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "关闭考试")
    public ResultDto<Object> delete(@RequestParam Long examId) {
        ExamDto examDto = examService.getOne(examId);
        if (new Date().compareTo(examDto.getStartTime()) >= 0) {
            return ResultDtoFactory.toNack("该考试进行中，无法修改");
        } else if (examDto.getStatus() == EExamStatus.DELETE) {
            return ResultDtoFactory.toNack("该考试已经关闭");
        }
        examService.deleteExam(examId);
        return ResultDtoFactory.toAck("关闭成功");
    }

    /**
     * 考试导出
     *
     * @param examSearchDto
     * @return
     */
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    @ApiOperation(value = "考试导出")
    public ModelAndView exportExam(ExamSearchDto examSearchDto) {
        List<ExamUserViewDto> examUserList = examUserService.getExamViewReport(examSearchDto);
        //获取创建人
        List<String> mgtUserIds = new ArrayList<>();
        for (ExamUserViewDto examUserViewDto : examUserList) {
            if (StringUtils.isNotEmpty(examUserViewDto.getCreateDealerOpId()) && !"system".equals(examUserViewDto.getCreateDealerOpId())) {
                mgtUserIds.add(examUserViewDto.getCreateDealerOpId());
            }
        }
        List<MgtUserDto> mgtUsers = mgtUserService.findByMgtUserIds(mgtUserIds);
        Map<String, MgtUserDto> mgtUserMap = new HashMap<>();
        for (MgtUserDto mgtUserDto : mgtUsers) {
            mgtUserMap.put(mgtUserDto.getUserId(), mgtUserDto);
        }
        List<ExamUserViewDto> list = new ArrayList<>();
        for (ExamUserViewDto examUserViewDto : examUserList) {
            MgtUserDto mgtUserDto = mgtUserMap.get(examUserViewDto.getCreateDealerOpId());
            if (mgtUserDto != null) {
                examUserViewDto.setCreateDealerOpName(mgtUserDto.getName());
            }
            list.add(examUserViewDto);
        }

        List<ExamUserReportDto> data = new ArrayList<>();
        for (ExamUserViewDto examUserViewDto : list) {
            ExamUserReportDto examUserReportDto = ConverterService.convert(examUserViewDto, ExamUserReportDto.class);
            examUserReportDto.setRemark(examUserViewDto.getStatus().getText());
            data.add(examUserReportDto);
        }
        Map<String, Object> model = new HashedMap();
        model.put("data", data);
        model.put("fileName", "分销员考试成绩.xls");
        model.put("tempPath", "/templates/分销员考试成绩.xls");
        DataTablesExcelService dataTablesExcelService = new DataTablesExcelService();
        return new ModelAndView(dataTablesExcelService, model);
    }

}
