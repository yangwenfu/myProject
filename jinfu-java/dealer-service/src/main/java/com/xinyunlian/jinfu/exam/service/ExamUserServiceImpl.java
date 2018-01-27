package com.xinyunlian.jinfu.exam.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.exam.dao.ExamUserDao;
import com.xinyunlian.jinfu.exam.dao.ExamUserViewDao;
import com.xinyunlian.jinfu.exam.dto.*;
import com.xinyunlian.jinfu.exam.entity.ExamUserPo;
import com.xinyunlian.jinfu.exam.entity.ExamUserViewPo;
import com.xinyunlian.jinfu.exam.enums.EExamType;
import com.xinyunlian.jinfu.exam.enums.EExamUserStatus;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by menglei on 2017年05月02日.
 */
@Service
public class ExamUserServiceImpl implements ExamUserService {

    @Autowired
    private ExamUserDao examUserDao;
    @Autowired
    private ExamUserViewDao examUserViewDao;

    @Transactional
    @Override
    public ExamUserViewSearchDto getExamUserViewPage(ExamUserViewSearchDto examUserViewSearchDto) {
        Specification<ExamUserViewPo> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (null != examUserViewSearchDto) {
                if (StringUtils.isNotEmpty(examUserViewSearchDto.getUserName())) {
                    expressions.add(cb.like(root.get("userName"), "%" + examUserViewSearchDto.getUserName() + "%"));
                }
                if (StringUtils.isNotEmpty(examUserViewSearchDto.getMobile())) {
                    expressions.add(cb.like(root.get("mobile"), examUserViewSearchDto.getMobile() + "%"));
                }
                if (StringUtils.isNotEmpty(examUserViewSearchDto.getDealerName())) {
                    expressions.add(cb.like(root.get("dealerName"), "%" + examUserViewSearchDto.getDealerName() + "%"));
                }
                if (StringUtils.isNotEmpty(examUserViewSearchDto.getCreateDealerOpName())) {
                    expressions.add(cb.like(root.get("createDealerOpName"), "%" + examUserViewSearchDto.getCreateDealerOpName() + "%"));
                }
                if (("PASS").equals(examUserViewSearchDto.getStatus())) {
                    expressions.add(cb.equal(root.get("status"), EExamUserStatus.PASS));
                }
                if (("FAIL").equals(examUserViewSearchDto.getStatus())) {
                    expressions.add(cb.equal(root.get("status"), EExamUserStatus.FAIL));
                }
                if (StringUtils.isNotEmpty(examUserViewSearchDto.getProvinceId())) {
                    expressions.add(cb.equal(root.get("provinceId"), examUserViewSearchDto.getProvinceId()));
                }
                if (StringUtils.isNotEmpty(examUserViewSearchDto.getCityId())) {
                    expressions.add(cb.equal(root.get("cityId"), examUserViewSearchDto.getCityId()));
                }
                if (StringUtils.isNotEmpty(examUserViewSearchDto.getAreaId())) {
                    expressions.add(cb.equal(root.get("areaId"), examUserViewSearchDto.getAreaId()));
                }
                if (StringUtils.isNotEmpty(examUserViewSearchDto.getStreetId())) {
                    expressions.add(cb.equal(root.get("streetId"), examUserViewSearchDto.getStreetId()));
                }
                if (StringUtils.isNotEmpty(examUserViewSearchDto.getUserId())) {
                    expressions.add(cb.equal(root.get("userId"), examUserViewSearchDto.getUserId()));
                }
                if (StringUtils.isNotEmpty(examUserViewSearchDto.getBelongName())) {
                    expressions.add(cb.like(root.get("belongName"), "%" + examUserViewSearchDto.getBelongName() + "%"));
                }
                expressions.add(cb.equal(root.get("examId"), examUserViewSearchDto.getExamId()));
            }
            return predicate;
        };

        Pageable pageable = new PageRequest(examUserViewSearchDto.getCurrentPage() - 1, examUserViewSearchDto.getPageSize(), Sort.Direction.DESC, "id");
        Page<ExamUserViewPo> page = examUserViewDao.findAll(spec, pageable);
        List<ExamUserViewDto> data = new ArrayList<>();
        page.getContent().forEach(po -> {
            ExamUserViewDto dto = ConverterService.convert(po, ExamUserViewDto.class);
            data.add(dto);
        });
        examUserViewSearchDto.setList(data);
        examUserViewSearchDto.setTotalPages(page.getTotalPages());
        examUserViewSearchDto.setTotalRecord(page.getTotalElements());

        return examUserViewSearchDto;
    }

    @Transactional
    @Override
    public List<ExamUserViewDto> getExamUserViewReport(ExamUserViewSearchDto examUserViewSearchDto) {
        Specification<ExamUserViewPo> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (null != examUserViewSearchDto) {
                if (StringUtils.isNotEmpty(examUserViewSearchDto.getUserName())) {
                    expressions.add(cb.like(root.get("userName"), "%" + examUserViewSearchDto.getUserName() + "%"));
                }
                if (StringUtils.isNotEmpty(examUserViewSearchDto.getMobile())) {
                    expressions.add(cb.like(root.get("mobile"), examUserViewSearchDto.getMobile() + "%"));
                }
                if (StringUtils.isNotEmpty(examUserViewSearchDto.getDealerName())) {
                    expressions.add(cb.like(root.get("dealerName"), "%" + examUserViewSearchDto.getDealerName() + "%"));
                }
                if (StringUtils.isNotEmpty(examUserViewSearchDto.getCreateDealerOpName())) {
                    expressions.add(cb.like(root.get("createDealerOpName"), "%" + examUserViewSearchDto.getCreateDealerOpName() + "%"));
                }
                if (("PASS").equals(examUserViewSearchDto.getStatus())) {
                    expressions.add(cb.equal(root.get("status"), EExamUserStatus.PASS));
                }
                if (("FAIL").equals(examUserViewSearchDto.getStatus())) {
                    expressions.add(cb.equal(root.get("status"), EExamUserStatus.FAIL));
                }
                if (StringUtils.isNotEmpty(examUserViewSearchDto.getProvinceId())) {
                    expressions.add(cb.equal(root.get("provinceId"), examUserViewSearchDto.getProvinceId()));
                }
                if (StringUtils.isNotEmpty(examUserViewSearchDto.getCityId())) {
                    expressions.add(cb.equal(root.get("cityId"), examUserViewSearchDto.getCityId()));
                }
                if (StringUtils.isNotEmpty(examUserViewSearchDto.getAreaId())) {
                    expressions.add(cb.equal(root.get("areaId"), examUserViewSearchDto.getAreaId()));
                }
                if (StringUtils.isNotEmpty(examUserViewSearchDto.getStreetId())) {
                    expressions.add(cb.equal(root.get("streetId"), examUserViewSearchDto.getStreetId()));
                }
                if (StringUtils.isNotEmpty(examUserViewSearchDto.getUserId())) {
                    expressions.add(cb.equal(root.get("userId"), examUserViewSearchDto.getUserId()));
                }
                expressions.add(cb.equal(root.get("examId"), examUserViewSearchDto.getExamId()));
            }
            return predicate;
        };

        List<ExamUserViewPo> list = examUserViewDao.findAll(spec);
        List<ExamUserViewDto> data = new ArrayList<>();
        list.forEach(po -> {
            ExamUserViewDto dto = ConverterService.convert(po, ExamUserViewDto.class);
            data.add(dto);
        });

        return data;
    }

    @Transactional
    @Override
    public List<ExamUserViewDto> getExamViewReport(ExamSearchDto examSearchDto) {
        Specification<ExamUserViewPo> spec = (root, query, cb) -> {

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
                    expressions.add(cb.greaterThanOrEqualTo(root.get("examCreateTs"), DateHelper.getStartDate(examSearchDto.getStartTime())));
                }
                if (null != examSearchDto.getEndTime()) {
                    expressions.add(cb.lessThanOrEqualTo(root.get("examCreateTs"), DateHelper.getEndDate(examSearchDto.getEndTime())));
                }
                if (StringUtils.isNotEmpty(examSearchDto.getMonthTime())) {
                    expressions.add(cb.greaterThanOrEqualTo(root.get("startTime"), DateHelper.getDate(DateHelper.getFirstDateOfMonth(examSearchDto.getMonthTime()))));
                    expressions.add(cb.lessThanOrEqualTo(root.get("startTime"), DateHelper.getDate(DateHelper.getLastDateOfMonth(examSearchDto.getMonthTime()))));
                }
            }
            return predicate;
        };

        List<ExamUserViewPo> list = examUserViewDao.findAll(spec);
        List<ExamUserViewDto> data = new ArrayList<>();
        list.forEach(po -> {
            ExamUserViewDto dto = ConverterService.convert(po, ExamUserViewDto.class);
            data.add(dto);
        });

        return data;
    }

    @Override
    @Transactional(readOnly = true)
    public ExamUserDto getByUserIdAndExamId(String userId, Long examId) {
        ExamUserPo examUserPo = examUserDao.findByUserIdAndExamId(userId, examId);
        if (examUserPo == null) {
            return null;
        }
        ExamUserDto examUserDto = ConverterService.convert(examUserPo, ExamUserDto.class);
        return examUserDto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamUserDto> getByUserId(String userId) {
        List<ExamUserDto> rs = new ArrayList<>();
        List<ExamUserPo> list = examUserDao.findByUserId(userId);
        for (ExamUserPo examUserPo : list) {
            rs.add(ConverterService.convert(examUserPo, ExamUserDto.class));
        }
        return rs;
    }

    @Transactional
    @Override
    public void createExamUser(ExamUserDto examUserDto) throws BizServiceException {
        if (examUserDto != null) {
            ExamUserPo examUserPo = ConverterService.convert(examUserDto, ExamUserPo.class);
            examUserDao.save(examUserPo);
        }
    }

}
