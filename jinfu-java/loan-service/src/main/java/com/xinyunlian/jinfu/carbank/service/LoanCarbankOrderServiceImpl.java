package com.xinyunlian.jinfu.carbank.service;

import com.xinyunlian.jinfu.carbank.api.dto.request.*;
import com.xinyunlian.jinfu.carbank.api.dto.response.*;
import com.xinyunlian.jinfu.carbank.dao.LoanCarbankOrderDao;
import com.xinyunlian.jinfu.carbank.dto.*;
import com.xinyunlian.jinfu.carbank.entity.LoanCarbankOrderPo;
import com.xinyunlian.jinfu.carbank.enums.ECbOrderStatus;
import com.xinyunlian.jinfu.common.cache.constant.CacheType;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.ylfin.redis.lock.RedisLock;
import com.ylfin.redis.lock.RedisLockFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by dongfangchao on 2017/7/18/0018.
 */
@Service
public class LoanCarbankOrderServiceImpl implements LoanCarbankOrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoanCarbankOrderServiceImpl.class);

    private static final String CARBANK_CITY = "CARBANK_CITY";
    private static final String CARBANK_VEHICLE_BRAND = "CARBANK_VEHICLE_BRAND";
    private static final String CARBANK_VEHICLE_SERIES = "CARBANK_VEHICLE_SERIES";

    private static final String REQUEST_SUCCESS = "SUCCESS";

    private static final String DEFAULT_INVITATION_CODE = "hz160000";

    private static final String DEFAULT_DEALER_NAME = "云联金服";

    @Autowired
    private RedisCacheManager redisCacheManager;

    @Autowired
    private LoanCarbankOrderDao loanCarbankOrderDao;

    @Autowired
    private RedisLockFactory redisLockFactory;

    @Override
    public LoanCarbankOrderSearchDto getPage(LoanCarbankOrderSearchDto searchDto) throws BizServiceException {

        Specification<LoanCarbankOrderPo> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (searchDto != null) {
                if (!StringUtils.isEmpty(searchDto.getUserId())) {
                    expressions.add(cb.equal(root.get("userId"), searchDto.getUserId()));
                }
            }
            return predicate;
        };

        Sort sort = new Sort(Sort.Direction.DESC, "lastMntTs");
        Pageable pageable = new PageRequest(searchDto.getCurrentPage() - 1, searchDto.getPageSize(), sort);
        Page<LoanCarbankOrderPo> page = loanCarbankOrderDao.findAll(spec, pageable);

        List<LoanCarbankOrderDto> data = page.getContent().stream()
                .map(item -> ConverterService.convert(item, LoanCarbankOrderDto.class))
                .collect(Collectors.toList());

        searchDto.setList(data);
        searchDto.setTotalPages(page.getTotalPages());
        searchDto.setTotalRecord(page.getTotalElements());
        return searchDto;
    }

    @Override
    public List<CityDto> getCities() throws BizServiceException {
        List<CityDto> cities = redisCacheManager.getCache(CacheType.CAR_BANK_CACHE).get(CARBANK_CITY, List.class);
        if (CollectionUtils.isEmpty(cities)){
            CityRequest request = new CityRequest();
            CityResponse response = request.send();
            if (response == null){
                throw new BizServiceException(EErrorCode.COMM_INTERNAL_ERROR);
            }
            if (REQUEST_SUCCESS.equals(response.getStatus())){
                cities = getConvert(response.getContent(), CityDto.class);
                redisCacheManager.getCache(CacheType.CAR_BANK_CACHE).put(CARBANK_CITY, cities);
            }
        }

        return cities;
    }

    @Override
    public List<BrandDto> getVehicleBrands() throws BizServiceException {
        List<BrandDto> brands = redisCacheManager.getCache(CacheType.CAR_BANK_CACHE).get(CARBANK_VEHICLE_BRAND, List.class);
        if (CollectionUtils.isEmpty(brands)){
            BrandRequest request = new BrandRequest();
            BrandResponse response = request.send();
            if (response == null){
                throw new BizServiceException(EErrorCode.COMM_INTERNAL_ERROR);
            }
            if (REQUEST_SUCCESS.equals(response.getStatus())){
                brands = getConvert(response.getContent(), BrandDto.class);
                redisCacheManager.getCache(CacheType.CAR_BANK_CACHE).put(CARBANK_VEHICLE_BRAND, brands);
            }
        }

        return brands;
    }

    @Override
    public List<SeriesDto> getVehicleSeries(Integer vehicleBrandId) throws BizServiceException {
        String cacheKey = CARBANK_VEHICLE_SERIES + "-" + vehicleBrandId;
        List<SeriesDto> series = redisCacheManager.getCache(CacheType.CAR_BANK_CACHE).get(cacheKey, List.class);
        if (CollectionUtils.isEmpty(series)){
            SeriesRequest request = new SeriesRequest();
            request.setVehicleBrandId(vehicleBrandId.toString());
            SeriesResponse response = request.send();
            if (response == null){
                throw new BizServiceException(EErrorCode.COMM_INTERNAL_ERROR);
            }
            if (REQUEST_SUCCESS.equals(response.getStatus())){
                series = getConvert(response.getContent(), SeriesDto.class);
                redisCacheManager.getCache(CacheType.CAR_BANK_CACHE).put(cacheKey, series);
            }
        }

        return series;
    }

    @Override
    public List<VehicleModelDto> getVehicleModels(Integer vehicleBrandId, Integer vehicleSeriesId) throws BizServiceException {
        String cacheKey = CARBANK_VEHICLE_SERIES + "-" + vehicleBrandId + "-" + vehicleSeriesId;
        List<VehicleModelDto> models = redisCacheManager.getCache(CacheType.CAR_BANK_CACHE).get(cacheKey, List.class);
        if (CollectionUtils.isEmpty(models)){
            VehicleModelRequest request = new VehicleModelRequest();
            request.setVehicleSeriesId(vehicleSeriesId.toString());
            VehicleModelResponse response = request.send();
            if (response == null){
                throw new BizServiceException(EErrorCode.COMM_INTERNAL_ERROR);
            }
            if (REQUEST_SUCCESS.equals(response.getStatus())){
                models = getConvert(response.getContent(), VehicleModelDto.class);
                redisCacheManager.getCache(CacheType.CAR_BANK_CACHE).put(cacheKey, models);
            }
        }

        return models;
    }

    @Override
    @Transactional
    public LoanCarbankOrderDto createWishOrder(LoanCarbankOrderDto wishOrder) throws BizServiceException {

        LoanCarbankOrderPo wishOrderPo = ConverterService.convert(wishOrder, LoanCarbankOrderPo.class);
        WishOrderRequest request = new WishOrderRequest();
        request.setCityId(wishOrder.getCityId());
        request.setMobile(wishOrder.getWishOrderMobile());
        request.setVehicleModelId(wishOrder.getVehicleModelId());
        if (wishOrder.getVehicleRegisterDate() != null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            request.setRegisterDate(sdf.format(wishOrder.getVehicleRegisterDate()));
        }
        request.setInvitationCode(DEFAULT_INVITATION_CODE);
        request.setDealerName(DEFAULT_DEALER_NAME);

        WishOrderResponse response = request.send();
        if (response == null){
            throw new BizServiceException(EErrorCode.COMM_INTERNAL_ERROR);
        }
        if (REQUEST_SUCCESS.equals(response.getStatus()) && response.getContent() != null){
            wishOrderPo.setOutTradeNo(response.getContent().getLoanApplyNo());
            wishOrderPo.setOverdue(false);
            LoanCarbankOrderPo dbOrder = loanCarbankOrderDao.save(wishOrderPo);
            //满足排序需求，只对lastMntTs字段排序
            dbOrder.setLastMntTs(dbOrder.getCreateTs());
            dbOrder.setLastMntOpId(dbOrder.getCreateOpId());
            LoanCarbankOrderPo dbOrder2 = loanCarbankOrderDao.save(dbOrder);
            return ConverterService.convert(dbOrder2, LoanCarbankOrderDto.class);
        }else {
            throw new BizServiceException(EErrorCode.CARBANK_CREATE_WISH_ORDER_ERROR);
        }
    }

    @Override
    public OrderStatusDto getOrderStatus(String outTradeNo) throws BizServiceException {
        OrderStatusRequest request = new OrderStatusRequest();
        request.setLoanApplyNo(outTradeNo);
        OrderStatusResponse response = request.send();
        if (response == null){
            throw new BizServiceException(EErrorCode.COMM_INTERNAL_ERROR);
        }
        if (response.getContent() != null && REQUEST_SUCCESS.equals(response.getStatus())){
            return ConverterService.convert(response.getContent(), OrderStatusDto.class);
        }
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateOrderStatus(String outTradeNo) throws BizServiceException {
        RedisLock redisLock = redisLockFactory.getLock("CARBANK_UPDATE_STATUS_LOCK");
        try {
            boolean isLock = redisLock.blockLock(20000, TimeUnit.MILLISECONDS);
            if (isLock){
                try{
                    LoanCarbankOrderPo order = loanCarbankOrderDao.findByOutTradeNo(outTradeNo);
                    if (order == null){
                        throw new BizServiceException(EErrorCode.CARBANK_ORDER_NOT_EXISTS);
                    }
                    OrderStatusDto orderStatus = getOrderStatus(outTradeNo);
                    if (orderStatus != null){
                        order.setOrderStatus(EnumHelper.translate(ECbOrderStatus.class, orderStatus.getStatus().toString()));
                        order.setReason(orderStatus.getReason());
                        if (!StringUtils.isEmpty(orderStatus.getIssueLoanDate())){
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            order.setIssueLoanDate(sdf.parse(orderStatus.getIssueLoanDate()));
                        }
                        order.setTermLen(Integer.parseInt(orderStatus.getTerm()));
                        order.setLoanAmt(new BigDecimal(orderStatus.getLoanAmt()));
                    }
                }finally {
                    redisLock.unlock();
                }
            }
        } catch (Exception e) {
            LOGGER.error("更新订单状态异常", e);
            throw new BizServiceException(EErrorCode.COMM_INTERNAL_ERROR);
        }
    }

    @Override
    @Transactional
    public void updateAllOrderStatus() throws BizServiceException {
        List<LoanCarbankOrderPo> orders = loanCarbankOrderDao.findAll();
        if (!CollectionUtils.isEmpty(orders)){
            for (LoanCarbankOrderPo order:orders) {
                if (!StringUtils.isEmpty(order.getOutTradeNo())){
                    try {
                        updateOrderStatus(order.getOutTradeNo());
                    } catch (BizServiceException e) {
                        LOGGER.error("订单状态更新失败", e);
                    }
                }
            }
        }
    }

    @Override
    @Transactional
    public void updateAllOrderOverdue() throws BizServiceException {
        List<LoanCarbankOrderPo> orders = loanCarbankOrderDao.findAll();
        if (!CollectionUtils.isEmpty(orders)){
            for (LoanCarbankOrderPo order:orders) {
                if (!StringUtils.isEmpty(order.getOutTradeNo())){
                    try {
                        updateOrderOverdue(order.getOutTradeNo());
                    } catch (BizServiceException e) {
                        LOGGER.error("订单逾期状态更新失败", e);
                    }
                }
            }
        }
    }

    @Override
    public OrderOverDueDto getOrderOverdue(String outTradeNo) throws BizServiceException {
        OrderOverDueRequest request = new OrderOverDueRequest();
        request.setApplyNo(outTradeNo);
        OrderOverDueResponse response = request.send();
        if (response == null){
            throw new BizServiceException(EErrorCode.COMM_INTERNAL_ERROR);
        }
        if (response.getContent() != null && REQUEST_SUCCESS.equals(response.getStatus())){
            return ConverterService.convert(response.getContent(), OrderOverDueDto.class);
        }
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateOrderOverdue(String outTradeNo) throws BizServiceException {
        RedisLock redisLock = redisLockFactory.getLock("CARBANK_UPDATE_OVERDUE_LOCK");
        try {
            boolean isLock = redisLock.blockLock(20000, TimeUnit.MILLISECONDS);
            if (isLock){
                try {
                    LoanCarbankOrderPo order = loanCarbankOrderDao.findByOutTradeNo(outTradeNo);
                    if (order == null){
                        throw new BizServiceException(EErrorCode.CARBANK_ORDER_NOT_EXISTS);
                    }
                    OrderOverDueDto orderOverdue = getOrderOverdue(order.getOutTradeNo());
                    if (orderOverdue != null){
                        order.setOverdue(orderOverdue.getStatus());
                    }
                } finally {
                    redisLock.unlock();
                }
            }
        } catch (Exception e){
            LOGGER.error("更新订单逾期异常", e);
            throw new BizServiceException(EErrorCode.COMM_INTERNAL_ERROR);
        }
    }

    @Override
    public LoanCarbankOrderDto getLoanCarbankOrderDetail(String cbOrderNo) throws BizServiceException {
        LoanCarbankOrderPo one = loanCarbankOrderDao.findOne(cbOrderNo);
        return ConverterService.convert(one, LoanCarbankOrderDto.class);
    }

    @Override
    @Transactional
    public void updateOrderStatusByUser(String userId) throws BizServiceException {
        List<LoanCarbankOrderPo> orders = loanCarbankOrderDao.findByUserId(userId);
        if (!CollectionUtils.isEmpty(orders)){
            orders.forEach(item -> updateOrderStatus(item.getOutTradeNo()));
        }
    }

    @Override
    @Transactional
    public void updateOrderOverdueByUser(String userId) throws BizServiceException {
        List<LoanCarbankOrderPo> orders = loanCarbankOrderDao.findByUserId(userId);
        if (!CollectionUtils.isEmpty(orders)){
            orders.forEach(item -> updateOrderOverdue(item.getOutTradeNo()));
        }
    }

    @Override
    @Transactional
    public Boolean hasSuccessOrder(String userId) throws BizServiceException {
        updateOrderStatusByUser(userId);
        updateOrderOverdueByUser(userId);

        List<LoanCarbankOrderPo> successOrders = loanCarbankOrderDao.findSuccessOrder(userId);
        if (!CollectionUtils.isEmpty(successOrders)){
            return true;
        }

        return false;
    }

    private <T, R> List<R> getConvert(List<T> content, Class<R> r){
        List<R> retList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(content)){
            retList = content.stream()
                    .map(item -> ConverterService.convert(item, r))
                    .collect(Collectors.toList());
        }
        return retList;
    }
}
