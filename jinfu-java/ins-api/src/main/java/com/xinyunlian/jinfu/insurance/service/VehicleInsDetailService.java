package com.xinyunlian.jinfu.insurance.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.zhongan.dto.VehicleInsDetailDto;

/**
 * Created by dongfangchao on 2016/12/5/0005.
 */
public interface VehicleInsDetailService {

    /**
     * 根据id查询唯一的一条记录
     * @param id
     * @return
     * @throws BizServiceException
     */
    VehicleInsDetailDto getVehicleInsDetail(Long id) throws BizServiceException;

    /**
     * 新增车险详情
     * @param detailDto
     * @return
     * @throws BizServiceException
     */
    VehicleInsDetailDto addVehicleInsDetail(VehicleInsDetailDto detailDto) throws BizServiceException;

    /**
     * 根据订单号查询唯一的一条记录
     * @param orderId
     * @return
     * @throws BizServiceException
     */
    VehicleInsDetailDto getVehicleInsDetailByOrderId(String orderId) throws BizServiceException;

    /**
     * 更新车险详情
     * @param detailDto
     * @throws BizServiceException
     */
    void updateVehicleInsDetailById(VehicleInsDetailDto detailDto) throws BizServiceException;

}
