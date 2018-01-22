package com.xinyunlian.jinfu.dealer.exam.controller;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.util.excel.DataTablesExcelService;
import com.xinyunlian.jinfu.dealer.dto.SignInfoViewDto;
import com.xinyunlian.jinfu.dealer.dto.SignInfoViewSearchDto;
import com.xinyunlian.jinfu.dealer.exam.dto.ExamUserReportDto;
import com.xinyunlian.jinfu.dealer.signInfo.dto.SignInfoReportDto;
import com.xinyunlian.jinfu.exam.dto.ExamUserViewDto;
import com.xinyunlian.jinfu.exam.dto.ExamUserViewSearchDto;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by menglei on 2017年05月05日.
 */
@Controller
@RequestMapping(value = "dealer/examUser")
@RequiresPermissions({"EXAM_MGT"})
public class examUserController {

    @Autowired
    private ExamUserService examUserService;
    @Autowired
    private MgtUserService mgtUserService;

    /**
     * 考试成绩列表
     *
     * @param examUserViewSearchDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "考试成绩列表")
    public ResultDto<ExamUserViewSearchDto> getPage(ExamUserViewSearchDto examUserViewSearchDto) {
        ExamUserViewSearchDto page = examUserService.getExamUserViewPage(examUserViewSearchDto);
        //获取创建人
//        List<String> mgtUserIds = new ArrayList<>();
//        for (ExamUserViewDto examUserViewDto : page.getList()) {
//            if (StringUtils.isNotEmpty(examUserViewDto.getCreateDealerOpId()) && !"system".equals(examUserViewDto.getCreateDealerOpId())) {
//                mgtUserIds.add(examUserViewDto.getCreateDealerOpId());
//            }
//        }
//        List<MgtUserDto> mgtUsers = mgtUserService.findByMgtUserIds(mgtUserIds);
//        Map<String, MgtUserDto> mgtUserMap = new HashMap<>();
//        for (MgtUserDto mgtUserDto : mgtUsers) {
//            mgtUserMap.put(mgtUserDto.getUserId(), mgtUserDto);
//        }
//        List<ExamUserViewDto> list = new ArrayList<>();
//        for (ExamUserViewDto examUserViewDto : page.getList()) {
//            MgtUserDto mgtUserDto = mgtUserMap.get(examUserViewDto.getCreateDealerOpId());
//            if (mgtUserDto != null) {
//                examUserViewDto.setCreateDealerOpName(mgtUserDto.getName());
//            }
//            list.add(examUserViewDto);
//        }
//        page.setList(list);
        return ResultDtoFactory.toAck("获取成功", page);
    }

    /**
     * 考试导出
     *
     * @param searchDto
     * @return
     */
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    @ApiOperation(value = "考试导出")
    public ModelAndView exportExamUser(ExamUserViewSearchDto searchDto) {
        List<ExamUserViewDto> examUserList = examUserService.getExamUserViewReport(searchDto);
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
