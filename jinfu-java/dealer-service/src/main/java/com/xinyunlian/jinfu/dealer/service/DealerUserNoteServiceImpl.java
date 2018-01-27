package com.xinyunlian.jinfu.dealer.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.BizUtil;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.dealer.dao.DealerUserNoteDao;
import com.xinyunlian.jinfu.dealer.dto.DealerDto;
import com.xinyunlian.jinfu.dealer.dto.DealerUserNoteDto;
import com.xinyunlian.jinfu.dealer.dto.DealerUserNoteSearchDto;
import com.xinyunlian.jinfu.dealer.entity.DealerUserNotePo;
import com.xinyunlian.jinfu.dealer.enums.EDealerUserNoteStatus;
import com.xinyunlian.jinfu.user.dto.DealerUserDto;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by menglei on 2016年08月30日.
 */
@Service
public class DealerUserNoteServiceImpl implements DealerUserNoteService {

    @Autowired
    private DealerUserNoteDao dealerUserNoteDao;

    @Transactional
    @Override
    public DealerUserNoteSearchDto getNotePage(DealerUserNoteSearchDto dealerUserNoteSearchDto) {

        Specification<DealerUserNotePo> spec = (root, query, cb) -> {

            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (null != dealerUserNoteSearchDto) {
                if (StringUtils.isNotEmpty(dealerUserNoteSearchDto.getStoreId())) {
                    expressions.add(cb.like(root.get("storeId"), dealerUserNoteSearchDto.getStoreId()));
                }
                if (StringUtils.isNotEmpty(dealerUserNoteSearchDto.getUserId())) {
                    expressions.add(cb.like(root.get("userId"), dealerUserNoteSearchDto.getUserId()));
                }
                if (StringUtils.isNotEmpty(dealerUserNoteSearchDto.getLastId())) {
                    expressions.add(cb.lessThanOrEqualTo(root.get("noteId"), dealerUserNoteSearchDto.getLastId()));
                }
                if (null != dealerUserNoteSearchDto.getBeginTime()) {
                    expressions.add(cb.greaterThanOrEqualTo(root.get("createTs"), DateHelper.getStartDate(dealerUserNoteSearchDto.getBeginTime())));
                }
                if (null != dealerUserNoteSearchDto.getEndTime()) {
                    expressions.add(cb.lessThanOrEqualTo(root.get("createTs"), DateHelper.getEndDate(dealerUserNoteSearchDto.getEndTime())));
                }
            }
            expressions.add(cb.equal(root.get("status"), EDealerUserNoteStatus.NORMAL));
            return predicate;
        };

        Pageable pageable = new PageRequest(dealerUserNoteSearchDto.getCurrentPage() - 1, dealerUserNoteSearchDto.getPageSize(), Sort.Direction.DESC, "noteId");
        Page<DealerUserNotePo> page = dealerUserNoteDao.findAll(spec, pageable);
        List<DealerUserNoteDto> data = new ArrayList<>();
        page.getContent().forEach(po -> {
            DealerUserNoteDto dealerUserNoteDto = ConverterService.convert(po, DealerUserNoteDto.class);
            dealerUserNoteDto.setCreateTime(DateHelper.formatDate(po.getCreateTs(), "yyyy.MM.dd HH:mm"));
            if (po.getDealerUserPo() != null) {
                DealerUserDto dealerUserDto = ConverterService.convert(po.getDealerUserPo(), DealerUserDto.class);
                dealerUserNoteDto.setDealerUserDto(dealerUserDto);
            }
            if (po.getDealerPo() != null) {
                DealerDto dealerDto = ConverterService.convert(po.getDealerPo(), DealerDto.class);
                dealerUserNoteDto.setDealerDto(dealerDto);
            }
            data.add(dealerUserNoteDto);
        });

        dealerUserNoteSearchDto.setList(data);
        dealerUserNoteSearchDto.setTotalPages(page.getTotalPages());
        dealerUserNoteSearchDto.setTotalRecord(page.getTotalElements());

        return dealerUserNoteSearchDto;
    }

    @Transactional
    @Override
    public DealerUserNoteDto createNote(DealerUserNoteDto dealerUserNoteDto) throws BizServiceException {
        if (dealerUserNoteDto == null) {
            throw new BizServiceException(EErrorCode.DEALER_PARAM_IS_NOT_NULL);
        }
        DealerUserNotePo dealerUserNotePo = ConverterService.convert(dealerUserNoteDto, DealerUserNotePo.class);
        dealerUserNotePo.setStatus(EDealerUserNoteStatus.NORMAL);
        dealerUserNotePo.setTitle(BizUtil.filterEmoji(dealerUserNoteDto.getTitle()));
        dealerUserNotePo.setContent(BizUtil.filterEmoji(dealerUserNoteDto.getContent()));
        dealerUserNoteDao.save(dealerUserNotePo);
        dealerUserNoteDto.setNoteId(dealerUserNotePo.getNoteId());
        return dealerUserNoteDto;
    }

    @Transactional
    @Override
    public void deleteNote(Long noteId) throws BizServiceException {
        DealerUserNotePo dealerUserNotePo = dealerUserNoteDao.findOne(noteId);
        if (dealerUserNotePo == null || dealerUserNotePo.getStatus() == EDealerUserNoteStatus.DELETE) {
            throw new BizServiceException(EErrorCode.DEALER_USER_NOTE_NOT_FOUND);
        }
        dealerUserNotePo.setStatus(EDealerUserNoteStatus.DELETE);
        dealerUserNoteDao.save(dealerUserNotePo);
    }

    @Override
    public long getCount(DealerUserNoteDto dealerUserNoteDto) {
        Specification<DealerUserNotePo> spec = (Root<DealerUserNotePo> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (null != dealerUserNoteDto) {
                if (!StringUtils.isEmpty(dealerUserNoteDto.getUserId())) {
                    expressions.add(cb.equal(root.get("userId"), dealerUserNoteDto.getUserId()));
                }
                if (!StringUtils.isEmpty(dealerUserNoteDto.getStoreId())) {
                    expressions.add(cb.equal(root.get("storeId"), dealerUserNoteDto.getStoreId()));
                }
                if (null != dealerUserNoteDto.getBeginTime()) {
                    expressions.add(cb.greaterThanOrEqualTo(root.get("createTs"), DateHelper.getStartDate(dealerUserNoteDto.getBeginTime())));
                }
                if (null != dealerUserNoteDto.getEndTime()) {
                    expressions.add(cb.lessThanOrEqualTo(root.get("createTs"), DateHelper.getEndDate(dealerUserNoteDto.getEndTime())));
                }
            }
            return predicate;
        };
        return dealerUserNoteDao.count(spec);
    }

}
