package com.xinyunlian.jinfu.report.dealer.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.util.BizUtil;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.report.dealer.dao.*;
import com.xinyunlian.jinfu.report.dealer.dto.*;
import com.xinyunlian.jinfu.report.dealer.entity.*;
import com.xinyunlian.jinfu.report.dealer.enums.EDealerType;
import com.xinyunlian.jinfu.report.dealer.enums.EUserStatus;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by bright on 2016/11/29.
 */
@Service
public class DealerReportServiceImpl implements DealerReportService {

    @Autowired
    private DealerStoreDao dealerStoreDao;

    @Autowired
    private DealerOrderDao dealerOrderDao;

    @Autowired
    private DealerLogDao dealerLogDao;

    @Autowired
    private DealerDao dealerDao;

    @Autowired
    private InsuranceDao insuranceDao;

    @Autowired
    private UserReportDao userReportDao;

    @Autowired
    private DealerUserDao dealerUserDao;

    @Autowired
    private OldUserReportDao oldUserReportDao;

    @Override
    public List<DealerStoreInfDto> getStoreReport(DealerStoreSearchDto searchDto) {
        Specification<DealerStorePo> specification = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            List<Expression<Boolean>> expressions = predicate.getExpressions();


            if (!StringUtils.isEmpty(searchDto.getArea())) {
                expressions.add(cb.equal(root.get("area"), searchDto.getArea()));
            }
            if (!StringUtils.isEmpty(searchDto.getCity())) {
                expressions.add(cb.equal(root.get("city"), searchDto.getCity()));
            }
            if (!StringUtils.isEmpty(searchDto.getProvince())) {
                expressions.add(cb.equal(root.get("province"), searchDto.getProvince()));
            }
            if (!StringUtils.isEmpty(searchDto.getStreet())) {
                expressions.add(cb.like(root.get("street"), BizUtil.filterStringRight(searchDto.getStreet())));
            }
            if (null != searchDto.getSource()) {
                expressions.add(cb.equal(root.get("source"), searchDto.getSource()));
            }
            if (!StringUtils.isEmpty(searchDto.getStoreName())) {
                expressions.add(cb.like(root.get("storeName"), BizUtil.filterString(searchDto.getStoreName())));
            }
            if (!StringUtils.isEmpty(searchDto.getCreateStartDate())) {
                expressions.add(cb.greaterThanOrEqualTo(root.get("createTs"), DateHelper.getStartDate(searchDto.getCreateStartDate())));
            }
            if (!StringUtils.isEmpty(searchDto.getCreateEndDate())) {
                expressions.add(cb.lessThanOrEqualTo(root.get("createTs"), DateHelper.getEndDate(searchDto.getCreateEndDate())));
            }
            if (StringUtils.isNotEmpty(searchDto.getDealerName())) {
                expressions.add(cb.equal(root.get("dealerName"), searchDto.getDealerName()));
            }
            return predicate;
        };

        List<DealerStorePo> stores = dealerStoreDao.findAll(specification, new Sort(Sort.Direction.DESC, "storeId"));
        List<DealerStoreInfDto> dtos = new ArrayList<>(stores.size());

        stores.forEach(dealerStorePo -> {
            DealerStoreInfDto dealerStoreDto = ConverterService.convert(dealerStorePo, DealerStoreInfDto.class);
            dtos.add(dealerStoreDto);
        });

        return dtos;
    }

    @Override
    public List<DealerOrderDto> getOrderReport(DealerOrderSearchDto searchDto) {
        Specification<DealerOrderPo> specification = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            List<Expression<Boolean>> expressions = predicate.getExpressions();

            if(StringUtils.isNotEmpty(searchDto.getOrderNo())){
                expressions.add(cb.like(root.get("orderNo"), BizUtil.filterString(searchDto.getOrderNo())));
            }
            if(StringUtils.isNotEmpty(searchDto.getProdId())){
                expressions.add(cb.equal(root.get("productId"), searchDto.getProdId()));
            }
            if(StringUtils.isNotEmpty(searchDto.getProvinceId())){
                expressions.add(cb.equal(root.get("provinceId"), searchDto.getProvinceId()));
            }
            if(StringUtils.isNotEmpty(searchDto.getCityId())){
                expressions.add(cb.equal(root.get("cityId"), searchDto.getCityId()));
            }
            if(StringUtils.isNotEmpty(searchDto.getAreaId())){
                expressions.add(cb.equal(root.get("areaId"), searchDto.getAreaId()));
            }
            if(StringUtils.isNotEmpty(searchDto.getStreetId())){
                expressions.add(cb.equal(root.get("streetId"), searchDto.getStreetId()));
            }
            if(StringUtils.isNotEmpty(searchDto.getDealerProvinceId())){
                expressions.add(cb.equal(root.get("dealerProvinceId"), searchDto.getDealerProvinceId()));
            }
            if(StringUtils.isNotEmpty(searchDto.getDealerCityId())){
                expressions.add(cb.equal(root.get("dealerCityId"), searchDto.getDealerCityId()));
            }
            if(StringUtils.isNotEmpty(searchDto.getDealerAreaId())){
                expressions.add(cb.equal(root.get("dealerAreaId"), searchDto.getDealerAreaId()));
            }
            if(StringUtils.isNotEmpty(searchDto.getDealerStreetId())){
                expressions.add(cb.equal(root.get("dealerStreetId"), searchDto.getDealerStreetId()));
            }
            if(StringUtils.isNotEmpty(searchDto.getDealerName())){
                expressions.add(cb.like(root.get("dealerName"), BizUtil.filterString(searchDto.getDealerName())));
            }
            if(StringUtils.isNotEmpty(searchDto.getBeginTime())){
                expressions.add(cb.greaterThanOrEqualTo(root.get("orderDate"), DateHelper.getStartDate(searchDto.getBeginTime())));
            }
            if(StringUtils.isNotEmpty(searchDto.getEndTime())){
                expressions.add(cb.lessThanOrEqualTo(root.get("orderDate"), DateHelper.getEndDate(searchDto.getEndTime())));
            }
            if(StringUtils.isNotEmpty(searchDto.getStoreName())){
                expressions.add(cb.like(root.get("storeName"), BizUtil.filterString(searchDto.getStoreName())));
            }

            return predicate;
        };

        List<DealerOrderPo> pos =  dealerOrderDao.findAll(specification, new Sort(Sort.Direction.DESC, "orderDate"));
        List<DealerOrderDto> dtos = new ArrayList<>(pos.size());
        HashMap<String, String> orderNoMap = new HashMap<>();
        pos.forEach(dealerOrderPo -> {
            if(!orderNoMap.containsKey(dealerOrderPo.getOrderNo())){
                DealerOrderDto dto = ConverterService.convert(dealerOrderPo, DealerOrderDto.class);
                dtos.add(dto);
                orderNoMap.put(dealerOrderPo.getOrderNo(), dealerOrderPo.getOrderNo());
            }
        });
        return dtos;
    }

    @Override
    public List<DealerLogDto> getLogReport(DealerLogSearchDto dealerLogSearchDto) {
        Specification<DealerLogPo> specification = (root, query, cb) -> {

            Predicate predicate = cb.conjunction();

            List<Expression<Boolean>> expressions = predicate.getExpressions();

            if(dealerLogSearchDto != null){

                if(StringUtils.isNotEmpty(dealerLogSearchDto.getName())){
                    expressions.add(cb.like(root.get("dealerUserName"), BizUtil.filterString(dealerLogSearchDto.getName())));
                }
                if(StringUtils.isNotEmpty(dealerLogSearchDto.getDealerName())){
                    expressions.add(cb.like(root.get("dealerName"), BizUtil.filterString(dealerLogSearchDto.getDealerName())));
                }
                if(StringUtils.isNotEmpty(dealerLogSearchDto.getMobile())){
                    expressions.add(cb.equal(root.get("mobile"), dealerLogSearchDto.getMobile()));
                }
                if(dealerLogSearchDto.getType() != null){
                    expressions.add(cb.equal(root.get("type"), dealerLogSearchDto.getType()));
                }
                if(StringUtils.isNotEmpty(dealerLogSearchDto.getBeginTime())){
                    expressions.add(cb.greaterThanOrEqualTo(root.get("createTs"), DateHelper.getStartDate(dealerLogSearchDto.getBeginTime())));
                }
                if(StringUtils.isNotEmpty(dealerLogSearchDto.getEndTime())){
                    expressions.add(cb.lessThanOrEqualTo(root.get("createTs"), DateHelper.getEndDate(dealerLogSearchDto.getEndTime())));
                }
            }

            return predicate;
        };

        List<DealerLogPo> pos = dealerLogDao.findAll(specification, new Sort(Sort.Direction.DESC, "logId"));
        List<DealerLogDto> dtos = new ArrayList<>(pos.size());
        pos.forEach(dealerLogPo -> {
            DealerLogDto dto = ConverterService.convert(dealerLogPo, DealerLogDto.class);
            dto.setRemark(dto.getRemark().replace("业务办理:orderNo=", "订单编号:").replace("记录添加:noteId=", "记录编号:"));
            dtos.add(dto);
        });
        return dtos;
    }

    @Override
    public List<DealerInfDto> getDealerReport(DealerReportSearchDto searchDto) {
        Specification<DealerPo> specification = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            List<Expression<Boolean>> expressions = predicate.getExpressions();

            if(searchDto != null){
                if(StringUtils.isNotEmpty(searchDto.getDealerName())){
                    expressions.add(cb.like(root.get("dealerName"), BizUtil.filterString(searchDto.getDealerName())));
                }
                if(searchDto.getLevelId() != null){
                    expressions.add(cb.equal(root.get("levelId"), searchDto.getLevelId()));
                }
                if(StringUtils.isNotEmpty(searchDto.getProvinceId())){
                    expressions.add(cb.equal(root.get("provinceId"), searchDto.getProvinceId()));
                }
                if(StringUtils.isNotEmpty(searchDto.getCityId())){
                    expressions.add(cb.equal(root.get("cityId"), searchDto.getCityId()));
                }
                if(StringUtils.isNotEmpty(searchDto.getAreaId())){
                    expressions.add(cb.equal(root.get("areaId"), searchDto.getAreaId()));
                }
                if(StringUtils.isNotEmpty(searchDto.getStreetId())){
                    expressions.add(cb.equal(root.get("streetId"), searchDto.getStreetId()));
                }
                if(StringUtils.isNotEmpty(searchDto.getProdId())){
                    expressions.add(cb.like(root.get("productId"), BizUtil.filterString(searchDto.getProdId())));
                }
                if(StringUtils.isNotEmpty(searchDto.getBeginTime())){
                    expressions.add(cb.greaterThanOrEqualTo(root.get("createTs"), DateHelper.getStartDate(searchDto.getBeginTime())));
                }
                if(StringUtils.isNotEmpty(searchDto.getEndTime())){
                    expressions.add(cb.lessThanOrEqualTo(root.get("createTs"), DateHelper.getEndDate(searchDto.getEndTime())));
                }
                if(StringUtils.isNotEmpty(searchDto.getProdProvinceId())){
                    expressions.add(cb.equal(root.get("prodProvinceId"), searchDto.getProdProvinceId()));
                }
                if(StringUtils.isNotEmpty(searchDto.getProdCityId())){
                    expressions.add(cb.equal(root.get("prodCityId"), searchDto.getProdCityId()));
                }
                if(StringUtils.isNotEmpty(searchDto.getProdAreaId())){
                    expressions.add(cb.equal(root.get("prodAreaId"), searchDto.getProdAreaId()));
                }
                if(StringUtils.isNotEmpty(searchDto.getProdStreetId())){
                    expressions.add(cb.equal(root.get("prodStreetId"), searchDto.getProdStreetId()));
                }
                if(StringUtils.isNotEmpty(searchDto.getType())){
                    expressions.add(cb.equal(root.get("type"), EnumHelper.translate(EDealerType.class, searchDto.getType())));
                }
            }

            return predicate;
        };

        List<DealerPo> pos = dealerDao.findAll(specification, new Sort(Sort.Direction.DESC, "dealerId"));
        List<DealerInfDto> dtos = new ArrayList<>(pos.size());
        pos.forEach(dealerPo -> {
            DealerInfDto dealerDto = ConverterService.convert(dealerPo, DealerInfDto.class);
            dtos.add(dealerDto);
        });
        return dtos;
    }

    @Override
    public List<InsuranceDto> getInsuranceReport(InsuranceSearchDto searchDto) {
        Specification<InsurancePo> specification = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            List<Expression<Boolean>> expressions = predicate.getExpressions();

            if(searchDto != null){
                if(StringUtils.isNotEmpty(searchDto.getPerInsuranceOrderNo())){
                    expressions.add(cb.like(root.get("orderNo"), BizUtil.filterString(searchDto.getPerInsuranceOrderNo())));
                }
                if(StringUtils.isNotEmpty(searchDto.getPhoneNo())){
                    expressions.add(cb.like(root.get("phoneNo"), BizUtil.filterString(searchDto.getPhoneNo())));
                }
                if(StringUtils.isNotEmpty(searchDto.getTobaccoPermiNo())){
                    expressions.add(cb.like(root.get("tobaccoPermiNo"), BizUtil.filterString(searchDto.getTobaccoPermiNo())));
                }
                if(searchDto.getPerInsDealSource() != null){
                    expressions.add(cb.equal(root.get("dealSource"), searchDto.getPerInsDealSource()));
                }
                if (StringUtils.isNotEmpty(searchDto.getStoreAreaTreePath())) {
                    expressions.add(cb.like(root.get("storeAreaTreePath"), BizUtil.filterStringRight(searchDto.getStoreAreaTreePath())));
                }
                if(searchDto.getPerInsOrderStatus() != null){
                    expressions.add(cb.equal(root.get("orderStatus"), searchDto.getPerInsOrderStatus()));
                }
                if(StringUtils.isNotEmpty(searchDto.getDealerPerson())){
                    expressions.add(cb.like(root.get("dealerUserName"), BizUtil.filterString(searchDto.getDealerPerson())));
                }
                if(StringUtils.isNotEmpty(searchDto.getProductId())){
                    expressions.add(cb.like(root.get("productId"), searchDto.getProductId()));
                }
                if(StringUtils.isNotEmpty(searchDto.getPolicyHolder())){
                    expressions.add(cb.like(root.get("policyHolder"), searchDto.getPolicyHolder()));
                }
                if(StringUtils.isNotEmpty(searchDto.getRemark())){
                    expressions.add(cb.like(root.get("remark"), BizUtil.filterString(searchDto.getRemark())));
                }
                if (searchDto.getOrderDateFrom() != null){
                    Date orderDateFrom = DateHelper.getStartDate(searchDto.getOrderDateFrom());
                    expressions.add(cb.greaterThanOrEqualTo(root.get("orderDate"), orderDateFrom));
                }
                if (searchDto.getOrderDateTo() != null){
                    Date orderDateTo = DateHelper.getEndDate(searchDto.getOrderDateTo());
                    expressions.add(cb.lessThanOrEqualTo(root.get("orderDate"), orderDateTo));
                }
            }

            return predicate;
        };

        List<InsurancePo> pos = insuranceDao.findAll(specification, new Sort(Sort.Direction.DESC, "orderDate"));
        List<InsuranceDto> dtos = new ArrayList<>(pos.size());

        pos.forEach(insurancePo -> {
            InsuranceDto insuranceDto = ConverterService.convert(insurancePo, InsuranceDto.class);
            dtos.add(insuranceDto);
        });

        return dtos;
    }

    private List<UserReportDto> searchUserReport(UserReportSearchDto searchDto){
        Specification<UserReportPo> specification = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            List<Expression<Boolean>> expressions = predicate.getExpressions();

            if(searchDto != null){
                if(StringUtils.isNotEmpty(searchDto.getUserName())){
                    expressions.add(cb.like(root.get("userName"), BizUtil.filterString(searchDto.getUserName())));
                }
                if(StringUtils.isNotEmpty(searchDto.getMobile())){
                    expressions.add(cb.like(root.get("mobile"), BizUtil.filterString(searchDto.getMobile())));
                }
                if(StringUtils.isNotEmpty(searchDto.getAreaId())){
                    expressions.add(cb.equal(root.get("areaId"), searchDto.getAreaId()));
                }
                if(StringUtils.isNotEmpty(searchDto.getCityId())){
                    expressions.add(cb.equal(root.get("cityId"), searchDto.getCityId()));
                }
                if(StringUtils.isNotEmpty(searchDto.getProvinceId())){
                    expressions.add(cb.equal(root.get("provinceId"), searchDto.getProvinceId()));
                }
                if(StringUtils.isNotEmpty(searchDto.getStreetId())){
                    expressions.add(cb.equal(root.get("streetId"), searchDto.getStreetId()));
                }
                if(StringUtils.isNotEmpty(searchDto.getRegisterStartDate())){
                    expressions.add(cb.greaterThanOrEqualTo(root.get("createTs"), DateHelper.getStartDate(searchDto.getRegisterStartDate())));
                }
                if(StringUtils.isNotEmpty(searchDto.getRegisterEndDate())){
                    expressions.add(cb.lessThanOrEqualTo(root.get("createTs"), DateHelper.getEndDate(searchDto.getRegisterEndDate())));
                }
                if(searchDto.getUserSource() != null){
                    expressions.add(cb.equal(root.get("userSource"), searchDto.getUserSource()));
                }
                if(searchDto.getIdentityAuth() != null && true == searchDto.getIdentityAuth()){
                    expressions.add(cb.equal(root.get("identityAuth"), searchDto.getIdentityAuth()));
                }
                if(searchDto.getStoreAuth() != null && true == searchDto.getStoreAuth()){
                    expressions.add(cb.equal(root.get("storeAuth"), searchDto.getStoreAuth()));
                }
                if (StringUtils.isNotEmpty(searchDto.getTobaccoCertificateNo())) {
                    expressions.add(cb.equal(root.get("tobaccoCertificateNo"), searchDto.getTobaccoCertificateNo()));
                }
                if(searchDto.getUserStatus() != null){
                    expressions.add(cb.equal(root.get("userStatus"), searchDto.getUserStatus()));
                }
            }

            return predicate;
        };

        List<UserReportPo> pos = userReportDao.findAll(specification, new Sort(Sort.Direction.DESC, "createTs"));
        List<UserReportDto> dtos = new ArrayList<>(pos.size());
        pos.forEach(userReportPo -> {
            UserReportDto userReportDto = ConverterService.convert(userReportPo, UserReportDto.class);
            dtos.add(userReportDto);
        });

        return dtos;
    }

    private List<UserReportDto> searchOldUserReport(UserReportSearchDto searchDto){
        Specification<OldUserReportPo> specification = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            List<Expression<Boolean>> expressions = predicate.getExpressions();

            if(searchDto != null){
                if(StringUtils.isNotEmpty(searchDto.getUserName())){
                    expressions.add(cb.like(root.get("userName"), BizUtil.filterString(searchDto.getUserName())));
                }
                if(StringUtils.isNotEmpty(searchDto.getMobile())){
                    expressions.add(cb.like(root.get("mobile"), BizUtil.filterString(searchDto.getMobile())));
                }
                if(StringUtils.isNotEmpty(searchDto.getAreaId())){
                    expressions.add(cb.equal(root.get("areaId"), searchDto.getAreaId()));
                }
                if(StringUtils.isNotEmpty(searchDto.getCityId())){
                    expressions.add(cb.equal(root.get("cityId"), searchDto.getCityId()));
                }
                if(StringUtils.isNotEmpty(searchDto.getProvinceId())){
                    expressions.add(cb.equal(root.get("provinceId"), searchDto.getProvinceId()));
                }
                if(StringUtils.isNotEmpty(searchDto.getStreetId())){
                    expressions.add(cb.equal(root.get("streetId"), searchDto.getStreetId()));
                }
                if(StringUtils.isNotEmpty(searchDto.getRegisterStartDate())){
                    expressions.add(cb.greaterThanOrEqualTo(root.get("createTs"), DateHelper.getStartDate(searchDto.getRegisterStartDate())));
                }
                if(StringUtils.isNotEmpty(searchDto.getRegisterEndDate())){
                    expressions.add(cb.lessThanOrEqualTo(root.get("createTs"), DateHelper.getEndDate(searchDto.getRegisterEndDate())));
                }
                if(searchDto.getUserSource() != null){
                    expressions.add(cb.equal(root.get("userSource"), searchDto.getUserSource()));
                }
                if(searchDto.getIdentityAuth() != null && true == searchDto.getIdentityAuth()){
                    expressions.add(cb.equal(root.get("identityAuth"), searchDto.getIdentityAuth()));
                }
                if(searchDto.getStoreAuth() != null && true == searchDto.getStoreAuth()){
                    expressions.add(cb.equal(root.get("storeAuth"), searchDto.getStoreAuth()));
                }
                if (StringUtils.isNotEmpty(searchDto.getTobaccoCertificateNo())) {
                    expressions.add(cb.equal(root.get("tobaccoCertificateNo"), searchDto.getTobaccoCertificateNo()));
                }
                if(searchDto.getUserStatus() != null){
                    expressions.add(cb.equal(root.get("userStatus"), searchDto.getUserStatus()));
                }
            }

            return predicate;
        };

        List<OldUserReportPo> pos = oldUserReportDao.findAll(specification, new Sort(Sort.Direction.DESC, "createTs"));
        List<UserReportDto> dtos = new ArrayList<>(pos.size());
        pos.forEach(userReportPo -> {
            UserReportDto userReportDto = ConverterService.convert(userReportPo, UserReportDto.class);
            dtos.add(userReportDto);
        });

        return dtos;
    }

    @Override
    public List<UserReportDto> getUserReport(UserReportSearchDto searchDto) {
        if(searchDto.getUserStatus() == EUserStatus.ACTIVE){
            return searchUserReport(searchDto);
        } else {
            return searchOldUserReport(searchDto);
        }
    }

    @Override
    public List<DealerUserReportDto> getDealerUserReport(DealerReportSearchDto searchDto) {
        Specification<DealerUserReportPo> specification = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            List<Expression<Boolean>> expressions = predicate.getExpressions();

            if(searchDto != null){
                if(StringUtils.isNotEmpty(searchDto.getDealerName())){
                    expressions.add(cb.like(root.get("dealerName"), BizUtil.filterString(searchDto.getDealerName())));
                }
                if(searchDto.getLevelId() != null){
                    expressions.add(cb.equal(root.get("levelId"), searchDto.getLevelId()));
                }
                if(StringUtils.isNotEmpty(searchDto.getProvinceId())){
                    expressions.add(cb.equal(root.get("provinceId"), searchDto.getProvinceId()));
                }
                if(StringUtils.isNotEmpty(searchDto.getCityId())){
                    expressions.add(cb.equal(root.get("cityId"), searchDto.getCityId()));
                }
                if(StringUtils.isNotEmpty(searchDto.getAreaId())){
                    expressions.add(cb.equal(root.get("areaId"), searchDto.getAreaId()));
                }
                if(StringUtils.isNotEmpty(searchDto.getStreetId())){
                    expressions.add(cb.equal(root.get("streetId"), searchDto.getStreetId()));
                }
                if(StringUtils.isNotEmpty(searchDto.getProdId())){
                    expressions.add(cb.like(root.get("productId"), BizUtil.filterString(searchDto.getProdId())));
                }
                if(StringUtils.isNotEmpty(searchDto.getBeginTime())){
                    expressions.add(cb.greaterThanOrEqualTo(root.get("dealerCreateTs"), DateHelper.getStartDate(searchDto.getBeginTime())));
                }
                if(StringUtils.isNotEmpty(searchDto.getEndTime())){
                    expressions.add(cb.lessThanOrEqualTo(root.get("dealerCreateTs"), DateHelper.getEndDate(searchDto.getEndTime())));
                }
                if(StringUtils.isNotEmpty(searchDto.getProdProvinceId())){
                    expressions.add(cb.equal(root.get("prodProvinceId"), searchDto.getProdProvinceId()));
                }
                if(StringUtils.isNotEmpty(searchDto.getProdCityId())){
                    expressions.add(cb.equal(root.get("prodCityId"), searchDto.getProdCityId()));
                }
                if(StringUtils.isNotEmpty(searchDto.getProdAreaId())){
                    expressions.add(cb.equal(root.get("prodAreaId"), searchDto.getProdAreaId()));
                }
                if(StringUtils.isNotEmpty(searchDto.getProdStreetId())){
                    expressions.add(cb.equal(root.get("prodStreetId"), searchDto.getProdStreetId()));
                }
                if(StringUtils.isNotEmpty(searchDto.getType())){
                    expressions.add(cb.equal(root.get("type"), EnumHelper.translate(EDealerType.class, searchDto.getType())));
                }
            }

            return predicate;
        };

        List<DealerUserReportPo> pos = dealerUserDao.findAll(specification, new Sort(Sort.Direction.DESC, "userId"));
        List<DealerUserReportDto> dtos = new ArrayList<>(pos.size());
        HashMap<String, String> userIdMap = new HashMap<>();
        pos.forEach(dealerUserReportPo -> {
            if(!userIdMap.containsKey(dealerUserReportPo.getUserId())) {
                DealerUserReportDto dealerDto = ConverterService.convert(dealerUserReportPo, DealerUserReportDto.class);
                dealerDto.setAdministrator(String.valueOf(dealerUserReportPo.getAdmin()));
                dtos.add(dealerDto);
                userIdMap.put(dealerUserReportPo.getUserId(),dealerUserReportPo.getUserId());
            }
        });
        return dtos;
    }
}
