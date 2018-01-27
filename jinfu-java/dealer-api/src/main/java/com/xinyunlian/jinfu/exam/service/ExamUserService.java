package com.xinyunlian.jinfu.exam.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.exam.dto.ExamSearchDto;
import com.xinyunlian.jinfu.exam.dto.ExamUserDto;
import com.xinyunlian.jinfu.exam.dto.ExamUserViewDto;
import com.xinyunlian.jinfu.exam.dto.ExamUserViewSearchDto;

import java.util.List;

/**
 * Created by menglei on 2017年05月02日.
 */
public interface ExamUserService {

    ExamUserViewSearchDto getExamUserViewPage(ExamUserViewSearchDto examUserViewSearchDto);

    List<ExamUserViewDto> getExamUserViewReport(ExamUserViewSearchDto examUserViewSearchDto);

    List<ExamUserViewDto> getExamViewReport(ExamSearchDto examSearchDto);

    ExamUserDto getByUserIdAndExamId(String userId, Long examId);

    List<ExamUserDto> getByUserId(String userId);

    void createExamUser(ExamUserDto examUserDto) throws BizServiceException;

}
