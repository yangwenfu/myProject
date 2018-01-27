package com.xinyunlian.jinfu.carbank.service;

import com.xinyunlian.jinfu.carbank.dto.*;
import com.xinyunlian.jinfu.common.exception.BizServiceException;

import java.util.List;

/**
 * Created by dongfangchao on 2017/7/18/0018.
 */
public interface LoanCarbankOrderService {

    /**
     * 分页查询车闪贷订单信息
     * @param searchDto
     * @return
     * @throws BizServiceException
     */
    LoanCarbankOrderSearchDto getPage(LoanCarbankOrderSearchDto searchDto) throws BizServiceException;

    /**
     * 获得城市列表
     * @return
     * @throws BizServiceException
     */
    List<CityDto> getCities() throws BizServiceException;

    /**
     * 获得车辆品牌列表
     * @return
     * @throws BizServiceException
     */
    List<BrandDto> getVehicleBrands() throws BizServiceException;

    /**
     * 获取车系列表
     * @param vehicleBrandId
     * @return
     * @throws BizServiceException
     */
    List<SeriesDto> getVehicleSeries(Integer vehicleBrandId) throws BizServiceException;

    /**
     * 获取车型列表
     * @param vehicleBrandId
     * @param vehicleSeriesId
     * @return
     * @throws BizServiceException
     */
    List<VehicleModelDto> getVehicleModels(Integer vehicleBrandId, Integer vehicleSeriesId) throws BizServiceException;

    /**
     * 创建意向单
     * @param wishOrder
     * @return
     * @throws BizServiceException
     */
    LoanCarbankOrderDto createWishOrder(LoanCarbankOrderDto wishOrder) throws BizServiceException;

    /**
     * 获取订单状态
     * @param outTradeNo
     * @return
     * @throws BizServiceException
     */
    OrderStatusDto getOrderStatus(String outTradeNo) throws BizServiceException;

    /**
     * 更新订单状态
     * @param outTradeNo
     * @throws BizServiceException
     */
    void updateOrderStatus(String outTradeNo) throws BizServiceException;

    /**
     * 更新所有订单的状态
     * @throws BizServiceException
     */
    void updateAllOrderStatus() throws BizServiceException;

    /**
     * 更新所有订单的逾期状态
     * @throws BizServiceException
     */
    void updateAllOrderOverdue() throws BizServiceException;

    /**
     * 查询订单逾期状态
     * @param outTradeNo
     * @return
     * @throws BizServiceException
     */
    OrderOverDueDto getOrderOverdue(String outTradeNo) throws BizServiceException;

    /**
     * 更新订单逾期状态
     * @param outTradeNo
     * @throws BizServiceException
     */
    void updateOrderOverdue(String outTradeNo) throws BizServiceException;

    /**
     * 根据订单编号获取订单详情
     * @param cbOrderNo
     * @return
     * @throws BizServiceException
     */
    LoanCarbankOrderDto getLoanCarbankOrderDetail(String cbOrderNo) throws BizServiceException;

    /**
     * 更新指定用户的订单的状态
     * @throws BizServiceException
     */
    void updateOrderStatusByUser(String userId) throws BizServiceException;

    /**
     * 更新指定用户的订单的逾期状态
     * @throws BizServiceException
     */
    void updateOrderOverdueByUser(String userId) throws BizServiceException;

    Boolean hasSuccessOrder(String userId) throws BizServiceException;

}
