package com.xinyunlian.jinfu.exam.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.exam.dto.ExamDto;
import com.xinyunlian.jinfu.exam.dto.ExamSearchDto;

import java.util.List;

/**
 * Created by menglei on 2017年05月02日.
 */
public interface ExamService {

    ExamSearchDto getExamPage(ExamSearchDto examSearchDto);

    ExamDto getOne(Long examId);

    /**
     * 查询有效的考试
     * @param examDto
     * @return
     */
    List<ExamDto> getValidExamList(ExamDto examDto);

    /**
     * 查询能首考的考试
     * @param examIds 已考试的列表
     * @return
     */
    List<ExamDto> getCanFirstExamList(List<Long> examIds);

    /**
     * 查询能考的常规考试
     * @param examIds 已考试的列表
     * @return
     */
    List<ExamDto> getCanExamList(List<Long> examIds);

    void createExam(ExamDto examDto) throws BizServiceException;

    void deleteExam(Long examId) throws BizServiceException;

    void updateExam(ExamDto examDto) throws BizServiceException;

}
