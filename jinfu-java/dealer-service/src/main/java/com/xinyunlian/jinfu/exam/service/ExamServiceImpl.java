package com.xinyunlian.jinfu.exam.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.exam.dao.ExamDao;
import com.xinyunlian.jinfu.exam.dto.ExamDto;
import com.xinyunlian.jinfu.exam.dto.ExamSearchDto;
import com.xinyunlian.jinfu.exam.entity.ExamPo;
import com.xinyunlian.jinfu.exam.enums.EExamStatus;
import com.xinyunlian.jinfu.exam.enums.EExamType;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.*;
import java.util.*;

/**
 * Created by menglei on 2017年05月02日.
 */
@Service
public class ExamServiceImpl implements ExamService {

    @Autowired
    private ExamDao examDao;

    @Transactional
    @Override
    public ExamSearchDto getExamPage(ExamSearchDto examSearchDto) {

        Specification<ExamPo> spec = (root, query, cb) -> {

            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (null != examSearchDto) {
                if (StringUtils.isNotEmpty(examSearchDto.getName())) {
                    expressions.add(cb.like(root.get("name"), "%" + examSearchDto.getName() + "%"));
                }
                if (("FIRST").equals(examSearchDto.getType())) {
                    expressions.add(cb.equal(root.get("type"), EExamType.FIRST));
                }
                if (("ROUTINE").equals(examSearchDto.getType())) {
                    expressions.add(cb.equal(root.get("type"), EExamType.ROUTINE));
                }
                if (("UNSTART").equals(examSearchDto.getStatus())) {
                    expressions.add(cb.greaterThan(root.get("startTime"), new Date()));
                }
                if (("ING").equals(examSearchDto.getStatus())) {
                    expressions.add(cb.lessThanOrEqualTo(root.get("startTime"), new Date()));
                    expressions.add(cb.greaterThanOrEqualTo(root.get("endTime"), new Date()));
                }
                if (("OVER").equals(examSearchDto.getStatus())) {
                    expressions.add(cb.lessThan(root.get("endTime"), new Date()));
                }
                if (null != examSearchDto.getStartTime()) {
                    expressions.add(cb.greaterThanOrEqualTo(root.get("createTs"), DateHelper.getStartDate(examSearchDto.getStartTime())));
                }
                if (null != examSearchDto.getEndTime()) {
                    expressions.add(cb.lessThanOrEqualTo(root.get("createTs"), DateHelper.getEndDate(examSearchDto.getEndTime())));
                }
                if (StringUtils.isNotEmpty(examSearchDto.getMonthTime())) {
                    expressions.add(cb.greaterThanOrEqualTo(root.get("startTime"), DateHelper.getDate(DateHelper.getFirstDateOfMonth(examSearchDto.getMonthTime()))));
                    expressions.add(cb.lessThanOrEqualTo(root.get("startTime"), DateHelper.getDate(DateHelper.getLastDateOfMonth(examSearchDto.getMonthTime()))));
                }
            }
            return predicate;
        };

        Pageable pageable = new PageRequest(examSearchDto.getCurrentPage() - 1, examSearchDto.getPageSize(), Sort.Direction.DESC, "examId");
        Page<ExamPo> page = examDao.findAll(spec, pageable);
        List<ExamDto> data = new ArrayList<>();
        page.getContent().forEach(po -> {
            ExamDto dto = ConverterService.convert(po, ExamDto.class);
            data.add(dto);
        });

        examSearchDto.setList(data);
        examSearchDto.setTotalPages(page.getTotalPages());
        examSearchDto.setTotalRecord(page.getTotalElements());

        return examSearchDto;
    }

    @Override
    @Transactional(readOnly = true)
    public ExamDto getOne(Long examId) {
        ExamPo examPo = examDao.findOne(examId);
        if (examPo == null) {
            return null;
        }
        ExamDto examDto = ConverterService.convert(examPo, ExamDto.class);
        return examDto;
    }

    @Override
    public List<ExamDto> getValidExamList(ExamDto examDto) {
        Specification<ExamPo> spec = (Root<ExamPo> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (null != examDto) {
                if (examDto.getType() != null) {
                    expressions.add(cb.equal(root.get("type"), examDto.getType()));
                }
                if (examDto.getStartTime() != null && examDto.getEndTime() != null) {//正在进行中的考试
                    expressions.add(cb.lessThanOrEqualTo(root.get("startTime"), examDto.getStartTime()));
                    expressions.add(cb.greaterThanOrEqualTo(root.get("endTime"), examDto.getEndTime()));
                } else if (examDto.getEndTime() != null) {//未开始的考试
                    expressions.add(cb.greaterThanOrEqualTo(root.get("endTime"), examDto.getEndTime()));
                }
                if (!CollectionUtils.isEmpty(examDto.getExamIds())) {
                    expressions.add(cb.in(root.get("examId")).value(examDto.getExamIds()));
                }

                expressions.add(cb.equal(root.get("status"), EExamStatus.NORMAL));
            }
            return predicate;
        };
        List<ExamPo> list = examDao.findAll(spec);
        List<ExamDto> result = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            for (ExamPo po : list) {
                ExamDto dto = ConverterService.convert(po, ExamDto.class);
                result.add(dto);
            }
        }
        return result;
    }

    @Override
    public List<ExamDto> getCanFirstExamList(List<Long> examIds) {
        List<ExamPo> list = examDao.findByExamIdsAndType(examIds, EExamType.FIRST.getCode());
        List<ExamDto> result = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            for (ExamPo po : list) {
                ExamDto dto = ConverterService.convert(po, ExamDto.class);
                result.add(dto);
            }
        }
        return result;
    }

    @Override
    public List<ExamDto> getCanExamList(List<Long> examIds) {
        List<ExamPo> list = examDao.findByExamIdsAndType(examIds, EExamType.ROUTINE.getCode());
        List<ExamDto> result = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            for (ExamPo po : list) {
                ExamDto dto = ConverterService.convert(po, ExamDto.class);
                result.add(dto);
            }
        }
        return result;
    }

    @Transactional
    @Override
    public void createExam(ExamDto examDto) throws BizServiceException {
        if (examDto != null) {
            ExamPo examPo = ConverterService.convert(examDto, ExamPo.class);
            examPo.setStatus(EExamStatus.NORMAL);
            examDao.save(examPo);
        }
    }

    @Transactional
    @Override
    public void deleteExam(Long examId) throws BizServiceException {
        ExamPo examPo = examDao.findOne(examId);
        examPo.setStatus(EExamStatus.DELETE);
        examDao.save(examPo);
    }

    @Transactional
    @Override
    public void updateExam(ExamDto examDto) throws BizServiceException {
        if (examDto != null) {
            ExamPo examPo = examDao.findOne(examDto.getExamId());
            if (examPo == null) {
                throw new BizServiceException(EErrorCode.DEALER_NOT_FOUND);
            }
            examPo.setName(examDto.getName());
            examPo.setType(examDto.getType());
            examPo.setUrl(examDto.getUrl());
            examPo.setStartTime(examDto.getStartTime());
            examPo.setEndTime(examDto.getEndTime());
            examPo.setPassLine(examDto.getPassLine());
            examDao.save(examPo);
        }
    }

}
