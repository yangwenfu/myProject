package com.xinyunlian.jinfu.cms.service;

import com.xinyunlian.jinfu.cms.dao.NoticeInfDao;
import com.xinyunlian.jinfu.cms.dao.NoticePlatformDao;
import com.xinyunlian.jinfu.cms.dto.ECmsValidStatus;
import com.xinyunlian.jinfu.cms.dto.NoticeInfDto;
import com.xinyunlian.jinfu.cms.dto.NoticeInfSearchDto;
import com.xinyunlian.jinfu.cms.dto.NoticePlatformDto;
import com.xinyunlian.jinfu.cms.entity.NoticeInfPo;
import com.xinyunlian.jinfu.cms.entity.NoticePlatformPo;
import com.xinyunlian.jinfu.cms.enums.ENoticePlatform;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dongfangchao on 2017/1/24/0024.
 */
@Service
public class NoticeInfServiceImpl implements NoticeInfService{

    private static final Logger LOGGER = LoggerFactory.getLogger(NoticeInfServiceImpl.class);

    @Autowired
    private NoticeInfDao noticeInfDao;

    @Autowired
    private NoticePlatformDao noticePlatformDao;

    @Override
    @Transactional
    public void addNotice(NoticeInfDto noticeInfDto) throws BizServiceException {
        try {
            if (noticeInfDto != null){
                NoticeInfPo noticeInfPo = ConverterService.convert(noticeInfDto, NoticeInfPo.class);
                NoticeInfPo dbNoticeInfPo = noticeInfDao.save(noticeInfPo);
                addNoticePlatFormBatch(dbNoticeInfPo.getId(), noticeInfDto.getNoticePlatformList());
            }
        } catch (Exception e) {
            LOGGER.error("新增公告失败", e);
            throw new BizServiceException(EErrorCode.CMS_NOTICE_ADD_ERROR);
        }
    }

    @Override
    @Transactional
    public void deleteNotice(Long noticeId) throws BizServiceException {
        try {
            noticeInfDao.delete(noticeId);
            noticePlatformDao.deleteByNoticeId(noticeId);
        } catch (Exception e) {
            LOGGER.error("删除公告失败", e);
            throw new BizServiceException(EErrorCode.CMS_NOTICE_DELETE_ERROR);
        }
    }

    @Override
    @Transactional
    public void updateNotice(NoticeInfDto noticeInfDto) throws BizServiceException {
        try {
            NoticeInfPo noticeInfPo = noticeInfDao.findOne(noticeInfDto.getId());
            if (noticeInfPo == null){
                throw new BizServiceException(EErrorCode.CMS_NOTICE_NOT_EXISTS);
            }
            noticeInfPo.setLinkUrl(noticeInfDto.getLinkUrl());
            noticeInfPo.setNoticeContent(noticeInfDto.getNoticeContent());
            noticeInfPo.setStartDate(noticeInfDto.getStartDate());
            noticeInfPo.setEndDate(noticeInfDto.getEndDate());

            noticePlatformDao.deleteByNoticeId(noticeInfDto.getId());
            addNoticePlatFormBatch(noticeInfDto.getId(), noticeInfDto.getNoticePlatformList());
        } catch (BizServiceException e) {
            LOGGER.error("更新公告失败", e);
            throw new BizServiceException(EErrorCode.CMS_NOTICE_UPDATE_ERROR);
        }
    }

    @Override
    public NoticeInfSearchDto getNoticeInfPage(NoticeInfSearchDto noticeInfSearchDto) throws BizServiceException {

        try {
            Specification<NoticeInfPo> spec = (root, query, cb) -> {
                Predicate predicate = cb.conjunction();
                List<Expression<Boolean>> expressions = predicate.getExpressions();
                if (noticeInfSearchDto != null) {

                    if (noticeInfSearchDto.getCmsValidStatus() != null){
                        if (noticeInfSearchDto.getCmsValidStatus() == ECmsValidStatus.FURTURE){
                            expressions.add(cb.greaterThan(root.get("startDate"), new Date()));
                        }else if (noticeInfSearchDto.getCmsValidStatus() == ECmsValidStatus.USING){
                            expressions.add(cb.greaterThanOrEqualTo(root.get("endDate"), new Date()));
                            expressions.add(cb.lessThanOrEqualTo(root.get("startDate"), new Date()));
                        }else if (noticeInfSearchDto.getCmsValidStatus() == ECmsValidStatus.OVER_DUE){
                            expressions.add(cb.lessThan(root.get("endDate"), new Date()));
                        }
                    }

                }
                return predicate;
            };

            Pageable pageable = new PageRequest(noticeInfSearchDto.getCurrentPage() - 1, noticeInfSearchDto.getPageSize());
            Page<NoticeInfPo> page = noticeInfDao.findAll(spec, pageable);

            List<NoticeInfDto> data = new ArrayList<>();

            Date now = new Date();
            page.getContent().forEach(po -> {
                NoticeInfDto noticeInfDto = ConverterService.convert(po, NoticeInfDto.class);

                if (now.compareTo(noticeInfDto.getStartDate()) > 0 && now.compareTo(noticeInfDto.getEndDate()) <= 0){
                    noticeInfDto.setCmsValidStatus(ECmsValidStatus.USING);
                }else if (now.compareTo(noticeInfDto.getEndDate()) > 0){
                    noticeInfDto.setCmsValidStatus(ECmsValidStatus.OVER_DUE);
                }else {
                    noticeInfDto.setCmsValidStatus(ECmsValidStatus.FURTURE);
                }

                List<NoticePlatformDto> noticePlatformDtoList = getNoticePlatform(noticeInfDto.getId());
                noticeInfDto.setNoticePlatformList(noticePlatformDtoList);

                data.add(noticeInfDto);
            });

            noticeInfSearchDto.setList(data);
            noticeInfSearchDto.setTotalPages(page.getTotalPages());
            noticeInfSearchDto.setTotalRecord(page.getTotalElements());
            return noticeInfSearchDto;
        } catch (Exception e) {
            LOGGER.error("分页查询公告失败", e);
            throw new BizServiceException(EErrorCode.CMS_NOTICE_PAGE_ERROR);
        }
    }

    @Override
    public NoticeInfDto getNoticeById(Long noticeId) throws BizServiceException {
        try {
            NoticeInfPo noticeInfPo = noticeInfDao.findOne(noticeId);
            if (noticeInfPo != null){
                NoticeInfDto noticeInfDto = ConverterService.convert(noticeInfPo, NoticeInfDto.class);
                List<NoticePlatformDto> noticePlatformDtoList = getNoticePlatform(noticeId);
                noticeInfDto.setNoticePlatformList(noticePlatformDtoList);
                return noticeInfDto;
            }else {
                throw new BizServiceException(EErrorCode.CMS_NOTICE_NOT_EXISTS);
            }
        }catch (BizServiceException e){
            LOGGER.error("查询公告失败", e);
            throw e;
        }catch (Exception e) {
            LOGGER.error("查询公告失败", e);
            throw new BizServiceException(EErrorCode.CMS_NOTICE_QUERY_ERROR);
        }
    }

    @Override
    @Transactional
    public void deleteNoticeBatch(List<Long> noticeIds) throws BizServiceException {
        try {
            noticeInfDao.deleteBatchByIds(noticeIds);
            noticePlatformDao.deleteBatchByNoticeIds(noticeIds);
        } catch (Exception e) {
            LOGGER.error("批量删除公告失败", e);
            throw new BizServiceException(EErrorCode.CMS_NOTICE_BATCH_DELETE_ERROR);
        }
    }

    @Override
    public List<NoticeInfDto> getNoticeByPlatform(ENoticePlatform noticePlatform) throws BizServiceException {
        List<NoticeInfDto> dtoList = new ArrayList<>();

        List<NoticeInfPo> poList = noticeInfDao.findByPlatformAndValid(noticePlatform.getCode());
        if (!CollectionUtils.isEmpty(poList)){
            poList.forEach(po -> {
                NoticeInfDto dto = ConverterService.convert(po, NoticeInfDto.class);
                List<NoticePlatformDto> noticePlatformDtoList = getNoticePlatform(dto.getId(), noticePlatform);
                dto.setNoticePlatformList(noticePlatformDtoList);
                dtoList.add(dto);
            });
        }
        return dtoList;
    }

    private void addNoticePlatFormBatch(Long noticeId, List<NoticePlatformDto> list){
        if (!CollectionUtils.isEmpty(list)){
            List<NoticePlatformPo> noticePlatformPoList = new ArrayList<>();
            list.forEach( noticePlatformDto -> {
                NoticePlatformPo noticePlatformPo = ConverterService.convert(noticePlatformDto, NoticePlatformPo.class);
                noticePlatformPo.setNoticeId(noticeId);
                noticePlatformPoList.add(noticePlatformPo);
            });
            noticePlatformDao.save(noticePlatformPoList);
        }
    }

    private List<NoticePlatformDto> getNoticePlatform(Long noticeId){
        List<NoticePlatformDto> dtoList = new ArrayList<>();
        List<NoticePlatformPo> tmpList = noticePlatformDao.findByNoticeId(noticeId);
        if (!CollectionUtils.isEmpty(tmpList)){
            tmpList.forEach(noticePlatformPo -> {
                NoticePlatformDto noticePlatformDto = ConverterService.convert(noticePlatformPo, NoticePlatformDto.class);
                dtoList.add(noticePlatformDto);
            });
        }
        return dtoList;
    }

    private List<NoticePlatformDto> getNoticePlatform(Long noticeId, ENoticePlatform noticePlatform){
        List<NoticePlatformDto> dtoList = new ArrayList<>();
        List<NoticePlatformPo> tmpList = noticePlatformDao.findByNoticeIdAndNoticePlatform(noticeId, noticePlatform);
        if (!CollectionUtils.isEmpty(tmpList)){
            tmpList.forEach(noticePlatformPo -> {
                NoticePlatformDto noticePlatformDto = ConverterService.convert(noticePlatformPo, NoticePlatformDto.class);
                dtoList.add(noticePlatformDto);
            });
        }
        return dtoList;
    }


}
