package com.xinyunlian.jinfu.dealer.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.dealer.dao.DealerDao;
import com.xinyunlian.jinfu.dealer.dao.DealerExtraDao;
import com.xinyunlian.jinfu.dealer.dao.DealerProdDao;
import com.xinyunlian.jinfu.dealer.dto.*;
import com.xinyunlian.jinfu.dealer.entity.DealerExtraPo;
import com.xinyunlian.jinfu.dealer.entity.DealerPo;
import com.xinyunlian.jinfu.dealer.entity.DealerProdPo;
import com.xinyunlian.jinfu.dealer.enums.EDealerAuditStatus;
import com.xinyunlian.jinfu.dealer.enums.EDealerStatus;
import com.xinyunlian.jinfu.dealer.enums.EDealerType;
import com.xinyunlian.jinfu.user.dao.DealerUserDao;
import com.xinyunlian.jinfu.user.entity.DealerUserPo;
import com.xinyunlian.jinfu.user.enums.EDealerUserStatus;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by menglei on 2016年08月26日.
 */
@Service
public class DealerServiceImpl implements DealerService {

    @Autowired
    private DealerDao dealerDao;
    @Autowired
    private DealerExtraDao dealerExtraDao;
    @Autowired
    private DealerProdDao dealerProdDao;
    @Autowired
    private DealerUserDao dealerUserDao;

    @Override
    @Transactional(readOnly = true)
    public DealerSearchDto getDealerPage(DealerSearchDto dealerSearchDto) {

        Specification<DealerPo> spec = (Root<DealerPo> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();

            Subquery<Long> subQuery = query.subquery(Long.class);
            Root<DealerProdPo> dealerProdPoRoot = subQuery.from(DealerProdPo.class);
            subQuery.select(dealerProdPoRoot.get("id"));
            Predicate subPredicate = cb.conjunction();
            List<Expression<Boolean>> subExpressions = subPredicate.getExpressions();
            if (null != dealerSearchDto) {
                if (StringUtils.isNotEmpty(dealerSearchDto.getDealerName())) {
                    expressions.add(cb.like(root.get("dealerName"), "%" + dealerSearchDto.getDealerName() + "%"));
                }
                if (StringUtils.isNotEmpty(dealerSearchDto.getIndustryId())) {
                    expressions.add(cb.equal(root.get("industryId"), dealerSearchDto.getIndustryId()));
                }
                if (dealerSearchDto.getLevelId() != null && dealerSearchDto.getLevelId() > 0) {
                    expressions.add(cb.equal(root.get("levelId"), dealerSearchDto.getLevelId()));
                }
                if (StringUtils.isNotEmpty(dealerSearchDto.getProvinceId())) {
                    expressions.add(cb.equal(root.get("provinceId"), dealerSearchDto.getProvinceId()));
                }
                if (StringUtils.isNotEmpty(dealerSearchDto.getCityId())) {
                    expressions.add(cb.equal(root.get("cityId"), dealerSearchDto.getCityId()));
                }
                if (StringUtils.isNotEmpty(dealerSearchDto.getAreaId())) {
                    expressions.add(cb.equal(root.get("areaId"), dealerSearchDto.getAreaId()));
                }
                if (StringUtils.isNotEmpty(dealerSearchDto.getStreetId())) {
                    expressions.add(cb.equal(root.get("streetId"), dealerSearchDto.getStreetId()));
                }
                if (("SUBSIDIARY").equals(dealerSearchDto.getType())) {
                    expressions.add(cb.equal(root.get("type"), EDealerType.SUBSIDIARY));
                }
                if (("DEALER").equals(dealerSearchDto.getType())) {
                    expressions.add(cb.equal(root.get("type"), EDealerType.DEALER));
                }
                if (StringUtils.isNotEmpty(dealerSearchDto.getBeginTime())) {
                    expressions.add(cb.greaterThanOrEqualTo(root.get("createTs"), DateHelper.getStartDate(dealerSearchDto.getBeginTime())));
                }
                if (StringUtils.isNotEmpty(dealerSearchDto.getEndTime())) {
                    expressions.add(cb.lessThanOrEqualTo(root.get("createTs"), DateHelper.getEndDate(dealerSearchDto.getEndTime())));
                }
                if (!StringUtils.isEmpty(dealerSearchDto.getProdId())) {
                    subExpressions.add(cb.equal(dealerProdPoRoot.get("prodId"), dealerSearchDto.getProdId()));
                }
                if (!StringUtils.isEmpty(dealerSearchDto.getProdProvinceId())) {
                    subExpressions.add(cb.equal(dealerProdPoRoot.get("provinceId"), dealerSearchDto.getProdProvinceId()));
                }
                if (!StringUtils.isEmpty(dealerSearchDto.getProdCityId())) {
                    subExpressions.add(cb.equal(dealerProdPoRoot.get("cityId"), dealerSearchDto.getProdCityId()));
                }
                if (!StringUtils.isEmpty(dealerSearchDto.getProdAreaId())) {
                    subExpressions.add(cb.equal(dealerProdPoRoot.get("areaId"), dealerSearchDto.getProdAreaId()));
                }
                if (!StringUtils.isEmpty(dealerSearchDto.getProdStreetId())) {
                    subExpressions.add(cb.equal(dealerProdPoRoot.get("streetId"), dealerSearchDto.getProdStreetId()));
                }
                if (("UN_FIRST_AUDIT").equals(dealerSearchDto.getAuditStatus())) {
                    expressions.add(cb.equal(root.get("auditStatus"), EDealerAuditStatus.UN_FIRST_AUDIT));
                }
                if (("UN_LAST_AUDIT").equals(dealerSearchDto.getAuditStatus())) {
                    expressions.add(cb.equal(root.get("auditStatus"), EDealerAuditStatus.UN_LAST_AUDIT));
                }
                if (("PASS").equals(dealerSearchDto.getAuditStatus())) {
                    expressions.add(cb.equal(root.get("auditStatus"), EDealerAuditStatus.PASS));
                }
                if (("REFUSE").equals(dealerSearchDto.getAuditStatus())) {
                    expressions.add(cb.equal(root.get("auditStatus"), EDealerAuditStatus.REFUSE));
                }
                if (("REFUSE").equals(dealerSearchDto.getAuditStatus())) {
                    expressions.add(cb.equal(root.get("auditStatus"), EDealerAuditStatus.REFUSE));
                }
                if (("REGIONAL_MGT").equals(dealerSearchDto.getDuty())) {
                    expressions.add(cb.equal(root.get("auditStatus"), EDealerAuditStatus.UN_FIRST_AUDIT));
                }
                if (("CHANNEL_MGT").equals(dealerSearchDto.getDuty())) {
                    expressions.add(cb.or(cb.equal(root.get("auditStatus"), EDealerAuditStatus.UN_FIRST_AUDIT), cb.equal(root.get("auditStatus"), EDealerAuditStatus.UN_LAST_AUDIT)));
                }
                if (!CollectionUtils.isEmpty(dealerSearchDto.getCityIds())) {
                    expressions.add(cb.in(root.get("cityId")).value(dealerSearchDto.getCityIds()));
                }
                if (!CollectionUtils.isEmpty(dealerSearchDto.getProvinceIds())) {
                    expressions.add(cb.in(root.get("provinceId")).value(dealerSearchDto.getProvinceIds()));
                }
                if (!CollectionUtils.isEmpty(dealerSearchDto.getCreateOpIds())) {
                    expressions.add(cb.in(root.get("createOpId")).value(dealerSearchDto.getCreateOpIds()));
                }
                if (!CollectionUtils.isEmpty(dealerSearchDto.getBelongIds())) {
                    expressions.add(cb.in(root.get("belongId")).value(dealerSearchDto.getBelongIds()));
                }
                if ((EDealerStatus.FROZEN.getCode()).equals(dealerSearchDto.getStatus())) {
                    expressions.add(cb.equal(root.get("status"), EDealerStatus.FROZEN));
                } else if ((EDealerStatus.NORMAL.getCode()).equals(dealerSearchDto.getStatus())) {
                    expressions.add(cb.equal(root.get("status"), EDealerStatus.NORMAL));
                } else {
                    expressions.add(cb.notEqual(root.get("status"), EDealerStatus.DELETE));
                }
                if (subExpressions.size() > 0) {
                    subExpressions.add(cb.equal(root.get("dealerId"), dealerProdPoRoot.get("dealerId")));
                    expressions.add(cb.exists(subQuery));
                    subQuery.where(subPredicate);
                }
            }
            return predicate;
        };

        Pageable pageable = new PageRequest(dealerSearchDto.getCurrentPage() - 1, dealerSearchDto.getPageSize(), Sort.Direction.DESC, "dealerId");
        Page<DealerPo> page = dealerDao.findAll(spec, pageable);

        List<DealerDto> data = new ArrayList<>();
        Map<String, DealerProdPo> authAreaMap;
        Map<String, DealerProdPo> authProdMap;
        for (DealerPo po : page.getContent()) {
            DealerDto dealerDto = ConverterService.convert(po, DealerDto.class);
            if (po.getDealerProdPoList() != null && po.getDealerProdPoList().size() > 0) {
                authAreaMap = new HashMap<>();
                authProdMap = new HashMap<>();
                for (DealerProdPo dealerProdPo : po.getDealerProdPoList()) {
                    authAreaMap.put(dealerProdPo.getDistrictId(), dealerProdPo);
                    authProdMap.put(dealerProdPo.getProdId(), dealerProdPo);
                }
                dealerDto.setAuthAreaCount(authAreaMap.size() + StringUtils.EMPTY);
                dealerDto.setAuthProdCount(authProdMap.size() + StringUtils.EMPTY);
            } else {
                dealerDto.setAuthAreaCount("0");
                dealerDto.setAuthProdCount("0");
            }
            DealerLevelDto dealerLevelDto = ConverterService.convert(po.getDealerLevelPo(), DealerLevelDto.class);
            dealerDto.setDealerLevelDto(dealerLevelDto);
            dealerDto.setCreateTime(DateHelper.formatDate(po.getCreateTs(), "yyyy-MM-dd HH:mm"));
            data.add(dealerDto);
        }

        dealerSearchDto.setList(data);
        dealerSearchDto.setTotalPages(page.getTotalPages());
        dealerSearchDto.setTotalRecord(page.getTotalElements());

        return dealerSearchDto;
    }

    @Override
    @Transactional(readOnly = true)
    public DealerDto getDealerById(String dealerId) {
        DealerDto dealerDto = null;
        if (dealerId != null) {
            DealerPo dealerPo = dealerDao.findOne(dealerId);
            if (dealerPo != null && dealerPo.getStatus() != EDealerStatus.DELETE) {
                dealerDto = ConverterService.convert(dealerPo, DealerDto.class);
                if (dealerPo.getDealerExtraPo() != null) {
                    DealerExtraDto dealerExtraDto = ConverterService.convert(dealerPo.getDealerExtraPo(), DealerExtraDto.class);
                    dealerDto.setDealerExtraDto(dealerExtraDto);
                }
            }
        }
        return dealerDto;
    }

    @Transactional
    @Override
    public void deleteDealer(String dealerId) throws BizServiceException {
        DealerPo dealerPo = dealerDao.findOne(dealerId);
        if (dealerPo == null || dealerPo.getStatus() == EDealerStatus.DELETE) {
            throw new BizServiceException(EErrorCode.DEALER_NOT_FOUND);
        }
        dealerPo.setStatus(EDealerStatus.DELETE);

        List<DealerUserPo> userList = dealerUserDao.findByDealerId(dealerId);
        if (userList != null && userList.size() > 0) {
            Long timestamp = System.currentTimeMillis();
            for (DealerUserPo dealerUserPo : userList) {
                if (dealerUserPo.getStatus() != EDealerUserStatus.DELETE) {
                    dealerUserPo.setMobile("delete" + timestamp + "-" + dealerUserPo.getMobile());
                    dealerUserPo.setStatus(EDealerUserStatus.DELETE);
                }
            }
        }
        //处理业务授权数据
        dealerProdDao.deleteByDealerId(dealerPo.getDealerId());
    }

    @Transactional
    @Override
    public void createDealer(DealerDto dealerDto, List<DealerProdDto> dealerProdList, List<DealerStoreDto> dealerStoreList) {
        if (dealerDto != null) {
            DealerPo dealerPo = ConverterService.convert(dealerDto, DealerPo.class);
            DealerExtraPo dealerExtraPo = ConverterService.convert(dealerDto.getDealerExtraDto(), DealerExtraPo.class);
            dealerPo.setStatus(EDealerStatus.NORMAL);
            dealerPo.setAuditStatus(EDealerAuditStatus.UN_FIRST_AUDIT);
            dealerPo.setIndustryId("0");
            dealerDao.save(dealerPo);
            dealerExtraPo.setDealerId(dealerPo.getDealerId());
            dealerExtraDao.save(dealerExtraPo);
            //处理业务授权数据
            if (!CollectionUtils.isEmpty(dealerProdList)) {
                List<DealerProdPo> dealerProdPoList = new ArrayList<>();
                for (DealerProdDto dealerProdDto : dealerProdList) {
                    DealerProdPo dealerProdPo = ConverterService.convert(dealerProdDto, DealerProdPo.class);
                    dealerProdPo.setDealerId(dealerPo.getDealerId());
                    dealerProdPoList.add(dealerProdPo);
                }
                if (!CollectionUtils.isEmpty(dealerProdPoList)) {
                    dealerProdDao.save(dealerProdPoList);
                }
            }
        }
    }

    @Transactional
    @Override
    public DealerDto saveDealer(DealerDto dealerDto, List<DealerProdDto> dealerProdList) {
        if (dealerDto == null) {
            return null;
        }
        DealerPo dealerPo = ConverterService.convert(dealerDto, DealerPo.class);
        DealerExtraPo dealerExtraPo = ConverterService.convert(dealerDto.getDealerExtraDto(), DealerExtraPo.class);
        dealerPo.setStatus(EDealerStatus.NORMAL);
        dealerPo.setAuditStatus(EDealerAuditStatus.UN_FIRST_AUDIT);
        dealerPo.setIndustryId("0");
        dealerDao.save(dealerPo);
        dealerDto.setDealerId(dealerPo.getDealerId());
        dealerExtraPo.setDealerId(dealerPo.getDealerId());
        dealerExtraDao.save(dealerExtraPo);
        //处理业务授权数据
        if (!CollectionUtils.isEmpty(dealerProdList)) {
            List<DealerProdPo> dealerProdPoList = new ArrayList<>();
            for (DealerProdDto dealerProdDto : dealerProdList) {
                DealerProdPo dealerProdPo = ConverterService.convert(dealerProdDto, DealerProdPo.class);
                dealerProdPo.setDealerId(dealerPo.getDealerId());
                dealerProdPoList.add(dealerProdPo);
            }
            if (!CollectionUtils.isEmpty(dealerProdPoList)) {
                dealerProdDao.save(dealerProdPoList);
            }
        }
        return dealerDto;
    }

    @Transactional
    @Override
    public void updateDealer(DealerDto dealerDto, List<DealerProdDto> dealerProdList, List<DealerStoreDto> dealerStoreList) throws BizServiceException {
        if (dealerDto != null) {
            DealerPo dealerPo = dealerDao.findOne(dealerDto.getDealerId());
            if (dealerPo == null || dealerPo.getStatus() == EDealerStatus.DELETE) {
                throw new BizServiceException(EErrorCode.DEALER_NOT_FOUND);
            }
            dealerPo.setDealerId(dealerDto.getDealerId());
            dealerPo.setDealerName(dealerDto.getDealerName());
            //dealerPo.setIndustryId(dealerDto.getIndustryId());
            dealerPo.setLevelId(dealerDto.getLevelId());
            dealerPo.setDistrictId(dealerDto.getDistrictId());
            dealerPo.setProvinceId(dealerDto.getProvinceId());
            dealerPo.setCityId(dealerDto.getCityId());
            dealerPo.setAreaId(dealerDto.getAreaId());
            dealerPo.setStreetId(dealerDto.getStreetId());
            dealerPo.setProvince(dealerDto.getProvince());
            dealerPo.setCity(dealerDto.getCity());
            dealerPo.setArea(dealerDto.getArea());
            dealerPo.setStreet(dealerDto.getStreet());
            dealerPo.setAddress(dealerDto.getAddress());
            dealerPo.setType(dealerDto.getType());
            dealerPo.setBeginTime(dealerDto.getBeginTime());
            dealerPo.setEndTime(dealerDto.getEndTime());
            if (EDealerAuditStatus.REFUSE.equals(dealerPo.getAuditStatus())) {
                dealerPo.setAuditStatus(EDealerAuditStatus.UN_FIRST_AUDIT);
            }
            if (StringUtils.isNotEmpty(dealerDto.getBelongId())) {
                dealerPo.setBelongId(dealerDto.getBelongId());
            }
            dealerDao.save(dealerPo);

            DealerExtraPo dealerExtraPo = dealerExtraDao.findOne(dealerDto.getDealerId());
            if (dealerExtraPo == null) {
                throw new BizServiceException(EErrorCode.DEALER_NOT_FOUND);
            }
            dealerExtraPo.setDealerId(dealerPo.getDealerId());
            dealerExtraPo.setBizLicence(dealerDto.getDealerExtraDto().getBizLicence());
            if (StringUtils.isNotEmpty(dealerDto.getDealerExtraDto().getBizLicencePic())) {
                dealerExtraPo.setBizLicencePic(dealerDto.getDealerExtraDto().getBizLicencePic());
            }
            dealerExtraPo.setContact(dealerDto.getDealerExtraDto().getContact());
            dealerExtraPo.setPhone(dealerDto.getDealerExtraDto().getPhone());
            dealerExtraPo.setIdCardNo(dealerDto.getDealerExtraDto().getIdCardNo());
            if (StringUtils.isNotEmpty(dealerDto.getDealerExtraDto().getIdCardNoPic1())) {
                dealerExtraPo.setIdCardNoPic1(dealerDto.getDealerExtraDto().getIdCardNoPic1());
            }
            if (StringUtils.isNotEmpty(dealerDto.getDealerExtraDto().getIdCardNoPic2())) {
                dealerExtraPo.setIdCardNoPic2(dealerDto.getDealerExtraDto().getIdCardNoPic2());
            }
            if (StringUtils.isNotEmpty(dealerDto.getDealerExtraDto().getAccountLicencePic())) {
                dealerExtraPo.setAccountLicencePic(dealerDto.getDealerExtraDto().getAccountLicencePic());
            }
            dealerExtraPo.setFinanContact(dealerDto.getDealerExtraDto().getFinanContact());
            dealerExtraPo.setFinanPhone(dealerDto.getDealerExtraDto().getFinanPhone());
            dealerExtraPo.setBankName(dealerDto.getDealerExtraDto().getBankName());
            dealerExtraPo.setBankCode(dealerDto.getDealerExtraDto().getBankCode());
            dealerExtraPo.setBankCardName(dealerDto.getDealerExtraDto().getBankCardName());
            dealerExtraPo.setBankCardNo(dealerDto.getDealerExtraDto().getBankCardNo());
            dealerExtraDao.save(dealerExtraPo);

            //处理业务授权数据
            dealerProdDao.deleteByDealerId(dealerPo.getDealerId());
            if (!CollectionUtils.isEmpty(dealerProdList)) {
                List<DealerProdPo> dealerProdPoList = new ArrayList<>();
                DealerProdPo dealerProdPo;
                for (DealerProdDto dealerProdDto : dealerProdList) {
                    dealerProdPo = ConverterService.convert(dealerProdDto, DealerProdPo.class);
                    dealerProdPo.setDealerId(dealerPo.getDealerId());
                    dealerProdPoList.add(dealerProdPo);
                }
                if (!CollectionUtils.isEmpty(dealerProdPoList)) {
                    dealerProdDao.save(dealerProdPoList);
                }
            }
        }
    }

    @Transactional
    @Override
    public void updateDealerProd(DealerDto dealerDto, List<DealerProdDto> dealerProdList) throws BizServiceException {
        //处理业务授权数据
        dealerProdDao.deleteByDealerId(dealerDto.getDealerId());
        if (!CollectionUtils.isEmpty(dealerProdList)) {
            List<DealerProdPo> dealerProdPoList = new ArrayList<>();
            DealerProdPo dealerProdPo;
            for (DealerProdDto dealerProdDto : dealerProdList) {
                dealerProdPo = ConverterService.convert(dealerProdDto, DealerProdPo.class);
                dealerProdPo.setDealerId(dealerDto.getDealerId());
                dealerProdPoList.add(dealerProdPo);
            }
            if (!CollectionUtils.isEmpty(dealerProdPoList)) {
                dealerProdDao.save(dealerProdPoList);
            }
        }
    }

    @Transactional
    @Override
    public void updateAudit(DealerDto dealerDto) {
        if (dealerDto != null) {
            DealerPo dealerPo = dealerDao.findOne(dealerDto.getDealerId());
            if (dealerPo != null) {
                dealerPo.setAuditStatus(dealerDto.getAuditStatus());
                dealerPo.setRemark(dealerDto.getRemark());
                dealerDao.save(dealerPo);
            }
        }
    }

    @Transactional
    @Override
    public void updateFrozen(String dealerId) {
        DealerPo dealerPo = dealerDao.findOne(dealerId);
        if (dealerPo != null && !dealerPo.getStatus().equals(EDealerStatus.DELETE)) {
            if (dealerPo.getStatus().equals(EDealerStatus.FROZEN)) {
                dealerPo.setStatus(EDealerStatus.NORMAL);
            } else if (dealerPo.getStatus().equals(EDealerStatus.NORMAL)) {
                dealerPo.setStatus(EDealerStatus.FROZEN);
            }
            dealerDao.save(dealerPo);
        }
    }

    @Override
    public List<DealerDto> findByDealerIds(List<String> dealerIds) {
        List<DealerDto> rs = new ArrayList<>();

        if (!CollectionUtils.isEmpty(dealerIds)) {
            List<DealerPo> list = dealerDao.findByDealerIdIn(dealerIds);
            for (DealerPo dealerPo : list) {
                rs.add(ConverterService.convert(dealerPo, DealerDto.class));
            }
        }

        return rs;
    }

    @Override
    public List<DealerDto> findByDealerName(String dealerName) {
        List<DealerDto> rs = new ArrayList<>();

        if (StringUtils.isNotEmpty(dealerName)) {
            List<DealerPo> list = dealerDao.findByDealerName(dealerName);
            for (DealerPo dealerPo : list) {
                rs.add(ConverterService.convert(dealerPo, DealerDto.class));
            }
        }

        return rs;
    }

    @Override
    public List<DealerDto> findByDealerNameLike(String dealerName) {
        List<DealerDto> rs = new ArrayList<>();
        List<DealerPo> list = dealerDao.findByDealerNameLike(dealerName);
        for (DealerPo dealerPo : list) {
            rs.add(ConverterService.convert(dealerPo, DealerDto.class));
        }

        return rs;
    }

    @Override
    public BigDecimal getDealerFeeRt(String dealerUserMobile) {
        if (StringUtils.isNotBlank(dealerUserMobile)) {
            DealerPo dealerPo = dealerDao.findByDealerUserMobile(dealerUserMobile);
            if (dealerPo != null) {
                return dealerPo.getServiceFeeRt();
            }
        }
        return null;
    }


}
