package com.xinyunlian.jinfu.feedback.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.feedback.dao.LoanFeedbackDao;
import com.xinyunlian.jinfu.feedback.dto.FeedbackDto;
import com.xinyunlian.jinfu.feedback.dto.FeedbackSearchDto;
import com.xinyunlian.jinfu.feedback.entity.LoanFeedbackPo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Willwang on 2016/11/10.
 */
@Service
public class LoanFeedbackServiceImpl implements LoanFeedbackService {

    @Autowired
    private LoanFeedbackDao loanFeedbackDao;

    @Override
    public void add(FeedbackDto feedbackAddDto) {
        LoanFeedbackPo po = ConverterService.convert(feedbackAddDto, LoanFeedbackPo.class);
        loanFeedbackDao.save(po);
    }

    @Override
    public void delete(Long id) {
        loanFeedbackDao.delete(id);
    }

    @Override
    public void hasRead(Long id,boolean status) {
        LoanFeedbackPo po = loanFeedbackDao.findOne(id);
        po.setRead(status);
        loanFeedbackDao.save(po);
    }

    @Override
    public FeedbackSearchDto list(FeedbackSearchDto feedbackSearchDto) {
        Specification<LoanFeedbackPo> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (feedbackSearchDto != null) {
                if (StringUtils.isNotEmpty(feedbackSearchDto.getUserName())) {
                    expressions.add(cb.like(root.get("userName"), "%" + feedbackSearchDto.getUserName() + "%"));
                }

                if (StringUtils.isNotEmpty(feedbackSearchDto.getMobile())) {
                    expressions.add(cb.like(root.get("mobile"), feedbackSearchDto.getMobile() + "%"));
                }
                if (StringUtils.isNotEmpty(feedbackSearchDto.getBeginTime())) {
                    expressions.add(cb.greaterThanOrEqualTo(root.get("createTs"), DateHelper.getStartDate(feedbackSearchDto.getBeginTime())));
                }
                if (StringUtils.isNotEmpty(feedbackSearchDto.getEndTime())) {
                    expressions.add(cb.lessThanOrEqualTo(root.get("createTs"), DateHelper.getEndDate(feedbackSearchDto.getEndTime())));
                }
                if(feedbackSearchDto.getRead() != null){
                    expressions.add(cb.equal(root.get("read"),feedbackSearchDto.getRead()));
                }
            }
            return predicate;
        };

        Pageable pageable = new PageRequest(feedbackSearchDto.getCurrentPage() - 1, feedbackSearchDto.getPageSize(), Sort.Direction.DESC, "feedbackId");
        Page<LoanFeedbackPo> page = loanFeedbackDao.findAll(spec, pageable);

        List<FeedbackDto> data = new ArrayList<>();
        page.getContent().forEach(po -> {
            FeedbackDto feedbackDto = ConverterService.convert(po, FeedbackDto.class);
            feedbackDto.setCreateTime(DateHelper.formatDate(po.getCreateTs(), "yyyy-MM-dd HH:mm:ss"));
            data.add(feedbackDto);
        });

        feedbackSearchDto.setList(data);
        feedbackSearchDto.setTotalPages(page.getTotalPages());
        feedbackSearchDto.setTotalRecord(page.getTotalElements());

        return feedbackSearchDto;
    }
}
