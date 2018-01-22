package com.xinyunlian.jinfu.store.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.BizUtil;
import com.xinyunlian.jinfu.store.dao.StoreWhiteListDao;
import com.xinyunlian.jinfu.store.dto.StoreWhiteListDto;
import com.xinyunlian.jinfu.store.dto.StoreWhiteListSearchDto;
import com.xinyunlian.jinfu.store.entity.StoreWhiteListPo;
import com.xinyunlian.jinfu.store.enums.EStoreWhiteListRemark;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by menglei on 2017年06月20日.
 */
@Service
public class StoreWhiteListServiceImpl implements StoreWhiteListService {

    @Autowired
    private StoreWhiteListDao storeWhiteListDao;

    @Override
    @Transactional(readOnly = true)
    public StoreWhiteListSearchDto getStorePage(StoreWhiteListSearchDto storeWhiteListSearchDto) {
        Specification<StoreWhiteListPo> spec = (Root<StoreWhiteListPo> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (null != storeWhiteListSearchDto) {
                if (!StringUtils.isEmpty(storeWhiteListSearchDto.getAreaId())) {
                    expressions.add(cb.equal(root.get("areaId"), storeWhiteListSearchDto.getAreaId()));
                }
                if (!StringUtils.isEmpty(storeWhiteListSearchDto.getCityId())) {
                    expressions.add(cb.equal(root.get("cityId"), storeWhiteListSearchDto.getCityId()));
                }
                if (!StringUtils.isEmpty(storeWhiteListSearchDto.getProvinceId())) {
                    expressions.add(cb.equal(root.get("provinceId"), storeWhiteListSearchDto.getProvinceId()));
                }
                if (!StringUtils.isEmpty(storeWhiteListSearchDto.getStreetId())){
                    expressions.add(cb.equal(root.get("streetId"), storeWhiteListSearchDto.getStreetId()));
                }
                if (!StringUtils.isEmpty(storeWhiteListSearchDto.getDealerId())) {
                    expressions.add(cb.equal(root.get("dealerId"), storeWhiteListSearchDto.getDealerId()));
                }
                if (!StringUtils.isEmpty(storeWhiteListSearchDto.getUserId())) {
                    expressions.add(cb.equal(root.get("userId"), storeWhiteListSearchDto.getUserId()));
                }
                if (!StringUtils.isEmpty(storeWhiteListSearchDto.getStoreName())) {
                    expressions.add(cb.like(root.get("storeName"), BizUtil.filterString(storeWhiteListSearchDto.getStoreName())));
                }
                if (!StringUtils.isEmpty(storeWhiteListSearchDto.getUserName())) {
                    expressions.add(cb.like(root.get("userName"), BizUtil.filterString(storeWhiteListSearchDto.getUserName())));
                }
                if (!StringUtils.isEmpty(storeWhiteListSearchDto.getPhone())) {
                    expressions.add(cb.like(root.get("phone"), BizUtil.filterString(storeWhiteListSearchDto.getPhone())));
                }
                if (storeWhiteListSearchDto.getStatus() != null) {
                    expressions.add(cb.equal(root.get("status"), storeWhiteListSearchDto.getStatus()));
                }
                if (storeWhiteListSearchDto.getRemark() != null) {
                    expressions.add(cb.equal(root.get("remark"), storeWhiteListSearchDto.getRemark()));
                }
                if (!CollectionUtils.isEmpty(storeWhiteListSearchDto.getDealerIds())) {
                    expressions.add(cb.in(root.get("dealerId")).value(storeWhiteListSearchDto.getDealerIds()));
                }
                if (!CollectionUtils.isEmpty(storeWhiteListSearchDto.getUserIds())) {
                    expressions.add(cb.in(root.get("userId")).value(storeWhiteListSearchDto.getUserIds()));
                }
                if (storeWhiteListSearchDto.getUnassigned()) {
                    expressions.add(cb.equal(root.get("dealerId"), StringUtils.EMPTY));
                }
                if (storeWhiteListSearchDto.getNotRemark()) {
                    expressions.add(cb.equal(root.get("remark"), EStoreWhiteListRemark.NOONE));
                }
            }
            return predicate;
        };

        Pageable pageable = new PageRequest(storeWhiteListSearchDto.getCurrentPage() - 1,
                storeWhiteListSearchDto.getPageSize(), Sort.Direction.DESC, "id");
        Page<StoreWhiteListPo> page = storeWhiteListDao.findAll(spec, pageable);

        List<StoreWhiteListDto> data = new ArrayList<>();
        for (StoreWhiteListPo po : page.getContent()) {
            StoreWhiteListDto storeWhiteListDto = ConverterService.convert(po, StoreWhiteListDto.class);
            data.add(storeWhiteListDto);
        }
        storeWhiteListSearchDto.setList(data);
        storeWhiteListSearchDto.setTotalPages(page.getTotalPages());
        storeWhiteListSearchDto.setTotalRecord(page.getTotalElements());
        return storeWhiteListSearchDto;
    }

    @Transactional
    @Override
    public void updateStatus(StoreWhiteListDto storeWhiteListDto) throws BizServiceException {
        if (storeWhiteListDto != null) {
            StoreWhiteListPo po = storeWhiteListDao.findOne(storeWhiteListDto.getId());
            if (po != null) {
                po.setStatus(storeWhiteListDto.getStatus());
                storeWhiteListDao.save(po);
            }
        }
    }

    @Transactional
    @Override
    public void updateRemark(StoreWhiteListDto storeWhiteListDto) throws BizServiceException {
        if (storeWhiteListDto != null) {
            StoreWhiteListPo po = storeWhiteListDao.findOne(storeWhiteListDto.getId());
            if (po != null) {
                po.setRemark(storeWhiteListDto.getRemark());
                storeWhiteListDao.save(po);
            }
        }
    }

    @Override
    public StoreWhiteListDto findOne(Long id) {
        StoreWhiteListPo po = storeWhiteListDao.findOne(id);
        return ConverterService.convert(po, StoreWhiteListDto.class);
    }

    @Transactional
    @Override
    public void updateDealerIdByIds(String dealerId, String userId, List<Long> ids) throws BizServiceException {
        storeWhiteListDao.updateDealerIdByIds(dealerId, userId, ids);
    }

}
