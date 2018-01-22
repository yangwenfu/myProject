package com.xinyunlian.jinfu.dealer.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.util.BizUtil;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.dealer.dao.DealerUserFeedbackDao;
import com.xinyunlian.jinfu.dealer.dto.DealerUserFeedbackDto;
import com.xinyunlian.jinfu.dealer.dto.DealerUserFeedbackSearchDto;
import com.xinyunlian.jinfu.dealer.entity.DealerGroupPo;
import com.xinyunlian.jinfu.dealer.entity.DealerUserFeedbackPo;
import com.xinyunlian.jinfu.user.dto.DealerUserDto;
import com.xinyunlian.jinfu.user.entity.DealerUserPo;
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
import java.util.List;

/**
 * Created by menglei on 2016年09月02日.
 */
@Service
public class DealerUserFeedbackServiceImpl implements DealerUserFeedbackService {

    @Autowired
    private DealerUserFeedbackDao dealerUserFeedbackDao;

    @Override
    @Transactional
    public DealerUserFeedbackSearchDto getFeedbackPage(DealerUserFeedbackSearchDto dealerUserFeedbackSearchDto) {

        Specification<DealerUserFeedbackPo> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (dealerUserFeedbackSearchDto != null) {
                if (StringUtils.isNotEmpty(dealerUserFeedbackSearchDto.getName())) {
                    expressions.add(cb.like(root.<DealerUserPo>get("dealerUserPo").get("name"), "%" + dealerUserFeedbackSearchDto.getName() + "%"));
                }
                if (StringUtils.isNotEmpty(dealerUserFeedbackSearchDto.getMobile())) {
                    expressions.add(cb.like(root.<DealerUserPo>get("dealerUserPo").get("mobile"), dealerUserFeedbackSearchDto.getMobile() + "%"));
                }
                if (StringUtils.isNotEmpty(dealerUserFeedbackSearchDto.getBeginTime())) {
                    expressions.add(cb.greaterThanOrEqualTo(root.get("createTs"), DateHelper.getStartDate(dealerUserFeedbackSearchDto.getBeginTime())));
                }
                if (StringUtils.isNotEmpty(dealerUserFeedbackSearchDto.getEndTime())) {
                    expressions.add(cb.lessThanOrEqualTo(root.get("createTs"), DateHelper.getEndDate(dealerUserFeedbackSearchDto.getEndTime())));
                }
            }
            return predicate;
        };

        Pageable pageable = new PageRequest(dealerUserFeedbackSearchDto.getCurrentPage() - 1, dealerUserFeedbackSearchDto.getPageSize(), Sort.Direction.DESC, "feedbackId");
        Page<DealerUserFeedbackPo> page = dealerUserFeedbackDao.findAll(spec, pageable);

        List<DealerUserFeedbackDto> data = new ArrayList<>();
        page.getContent().forEach(po -> {
            DealerUserFeedbackDto dealerUserFeedbackDto = ConverterService.convert(po, DealerUserFeedbackDto.class);
            if (po.getDealerUserPo() != null) {
                DealerUserDto dealerUserDto = ConverterService.convert(po.getDealerUserPo(), DealerUserDto.class);
                dealerUserFeedbackDto.setDealerUserDto(dealerUserDto);
            }
            dealerUserFeedbackDto.setCreateTime(DateHelper.formatDate(po.getCreateTs(), "yyyy-MM-dd HH:mm:ss"));
            data.add(dealerUserFeedbackDto);
        });

        dealerUserFeedbackSearchDto.setList(data);
        dealerUserFeedbackSearchDto.setTotalPages(page.getTotalPages());
        dealerUserFeedbackSearchDto.setTotalRecord(page.getTotalElements());

        return dealerUserFeedbackSearchDto;
    }

    @Transactional
    @Override
    public void createDealerUserFeedback(DealerUserFeedbackDto dealerUserFeedbackDto) {
        if (dealerUserFeedbackDto != null) {
            DealerUserFeedbackPo dealerUserFeedbackPo = ConverterService.convert(dealerUserFeedbackDto, DealerUserFeedbackPo.class);
            dealerUserFeedbackPo.setTitle(BizUtil.filterEmoji(dealerUserFeedbackDto.getTitle()));
            dealerUserFeedbackPo.setContent(BizUtil.filterEmoji(dealerUserFeedbackDto.getContent()));
            dealerUserFeedbackDao.save(dealerUserFeedbackPo);
        }
    }

    @Transactional
    @Override
    public void deleteFeedback(Long id) {
        DealerUserFeedbackPo dealerUserFeedbackPo = dealerUserFeedbackDao.findOne(id);
        if (dealerUserFeedbackPo != null) {
            dealerUserFeedbackDao.delete(id);
        }
    }

}
