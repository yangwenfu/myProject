package com.xinyunlian.jinfu.yunma.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.yunma.dao.YMRouterAgentDao;
import com.xinyunlian.jinfu.yunma.dto.YMRouterAgentDto;
import com.xinyunlian.jinfu.yunma.dto.YMRouterSearchDto;
import com.xinyunlian.jinfu.yunma.entity.YMRouterAgentPo;
import com.xinyunlian.jinfu.yunma.enums.ERouterAgentStatus;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by menglei on 2017-01-04.
 */
@Service(value = "yMRouterAgentServiceImpl")
public class YMRouterAgentServiceImpl implements YMRouterAgentService {

    @Autowired
    private YMRouterAgentDao yMRouterAgentDao;

    @Override
    @Transactional
    public void addRouterAgent(YMRouterAgentDto dto) throws BizServiceException {
        YMRouterAgentPo routerAgentPo = yMRouterAgentDao.findByUserAgent(dto.getUserAgent());
        if(null != routerAgentPo){
            throw new BizServiceException(EErrorCode.USER_AGENT_EXIST,"userAgent已存在");
        }
        YMRouterAgentPo po = ConverterService.convert(dto, YMRouterAgentPo.class);
        if(null != po) {
            yMRouterAgentDao.save(po);
        }
    }

    @Override
    @Transactional
    public void updateRouterAgent(YMRouterAgentDto dto) throws BizServiceException {
        YMRouterAgentPo routerAgentPo = yMRouterAgentDao.findByUserAgent(dto.getUserAgent());
        if(null != routerAgentPo && dto.getId() != routerAgentPo.getId()){
            throw new BizServiceException(EErrorCode.USER_AGENT_EXIST,"userAgent已存在");
        }

        YMRouterAgentPo po = yMRouterAgentDao.findOne(dto.getId());
        ConverterService.convert(dto, po);
        yMRouterAgentDao.save(po);
    }

    @Override
    @Transactional
    public void delete(Long id){
        yMRouterAgentDao.delete(id);
    }

    @Override
    @Transactional
    public YMRouterAgentDto findByUserAgent(String userAgent) {
        YMRouterAgentPo po = yMRouterAgentDao.findByUserAgentAndStatus(userAgent, ERouterAgentStatus.ENABLE);
        if (po == null) {
            return null;
        }
        YMRouterAgentDto dto = ConverterService.convert(po, YMRouterAgentDto.class);
        return dto;
    }

    /**
     * 查询所有useragent
     * @return
     */
    @Override
    public List<YMRouterAgentDto> findAll() {
        List<YMRouterAgentPo> poList = yMRouterAgentDao.findAll();
        List<YMRouterAgentDto> dtoList = new ArrayList<>();
        for (YMRouterAgentPo po : poList) {
            YMRouterAgentDto dto = ConverterService.convert(po, YMRouterAgentDto.class);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public YMRouterSearchDto getRouterPage(YMRouterSearchDto searchDto) {
        Specification<YMRouterAgentPo> spec = (Root<YMRouterAgentPo> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (null != searchDto) {
                if (!StringUtils.isEmpty(searchDto.getName())) {
                    expressions.add(cb.equal(root.get("name"), searchDto.getName()));
                }

                if (!StringUtils.isEmpty(searchDto.getCreateStartDate())) {
                    expressions.add(cb.greaterThanOrEqualTo(root.get("createTs"), DateHelper.getStartDate(searchDto.getCreateStartDate())));
                }
                if (!StringUtils.isEmpty(searchDto.getCreateEndDate())) {
                    expressions.add(cb.lessThanOrEqualTo(root.get("createTs"), DateHelper.getEndDate(searchDto.getCreateEndDate())));
                }
            }
            return predicate;
        };

        Pageable pageable = new PageRequest(searchDto.getCurrentPage() - 1,
                searchDto.getPageSize(), Direction.DESC, "id");
        Page<YMRouterAgentPo> page = yMRouterAgentDao.findAll(spec, pageable);

        List<YMRouterAgentDto> data = new ArrayList<>();
        for (YMRouterAgentPo po : page.getContent()) {
            YMRouterAgentDto dto = ConverterService.convert(po, YMRouterAgentDto.class);
            data.add(dto);
        }
        searchDto.setList(data);
        searchDto.setTotalPages(page.getTotalPages());
        searchDto.setTotalRecord(page.getTotalElements());
        return searchDto;
    }

    @Override
    @Transactional
    public YMRouterAgentDto findLocalByUserAgent(String userAgent) {
        List<YMRouterAgentPo> list = yMRouterAgentDao.findLocalByUserAgent(userAgent);
        if (list.isEmpty()) {
            return null;
        }
        YMRouterAgentPo po = list.get(0);
        YMRouterAgentDto dto = ConverterService.convert(po, YMRouterAgentDto.class);
        return dto;
    }

}
