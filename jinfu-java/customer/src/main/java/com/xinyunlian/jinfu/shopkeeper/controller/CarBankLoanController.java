package com.xinyunlian.jinfu.shopkeeper.controller;

import com.xinyunlian.jinfu.carbank.dto.*;
import com.xinyunlian.jinfu.carbank.service.LoanCarbankOrderService;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.shopkeeper.dto.carbank.WishOrderRequest;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by dongfangchao on 2017/7/19/0019.
 */
@RestController
@RequestMapping(value = "shopkeeper/carbank")
public class CarBankLoanController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarBankLoanController.class);

    @Autowired
    private LoanCarbankOrderService loanCarbankOrderService;

    @Autowired
    private UserService userService;

    @GetMapping("getOrderPage")
    public ResultDto getOrderPage(LoanCarbankOrderSearchDto searchDto){
        String userId = SecurityContext.getCurrentUserId();
        if (StringUtils.isEmpty(userId)){
            return ResultDtoFactory.toNack("用户未登录");
        }
        searchDto.setUserId(userId);
        LOGGER.debug("getOrderPage userId = {}", userId);
        LoanCarbankOrderSearchDto page = loanCarbankOrderService.getPage(searchDto);
        return ResultDtoFactory.toAckData(page);
    }

    @GetMapping("getCities")
    public ResultDto getCities(){
        List<CityDto> cities = loanCarbankOrderService.getCities();
        return ResultDtoFactory.toAckData(cities);
    }

    @GetMapping("getVehicleBrands")
    public ResultDto getVehicleBrands(){
        List<BrandDto> vehicleBrands = loanCarbankOrderService.getVehicleBrands();
        return ResultDtoFactory.toAckData(vehicleBrands);
    }

    @GetMapping("getVehicleSeries")
    public ResultDto getVehicleSeries(Integer vehicleBrandId){
        List<SeriesDto> vehicleSeries = loanCarbankOrderService.getVehicleSeries(vehicleBrandId);
        return ResultDtoFactory.toAckData(vehicleSeries);
    }

    @GetMapping("getVehicleModels")
    public ResultDto getVehicleModels(Integer vehicleBrandId, Integer vehicleSeriesId){
        List<VehicleModelDto> vehicleModels = loanCarbankOrderService.getVehicleModels(vehicleBrandId, vehicleSeriesId);
        return ResultDtoFactory.toAckData(vehicleModels);
    }

    @PostMapping("createWishOrder")
    public ResultDto createWishOrder(@RequestBody @Valid WishOrderRequest request, BindingResult result){
        if (result.hasErrors()){
            return ResultDtoFactory.toNack(result.getFieldError().getDefaultMessage());
        }

        LoanCarbankOrderDto wishOrder = ConverterService.convert(request, LoanCarbankOrderDto.class);

        String userId = SecurityContext.getCurrentUserId();
        UserInfoDto user = userService.findUserByUserId(userId);
        wishOrder.setUserId(userId);
        wishOrder.setUserName(user.getUserName());

        LoanCarbankOrderDto loanCarbankOrder = loanCarbankOrderService.createWishOrder(wishOrder);
        loanCarbankOrderService.updateOrderStatus(loanCarbankOrder.getOutTradeNo());

        return ResultDtoFactory.toAck();
    }

    @GetMapping("getOrderDetail")
    public ResultDto getOrderDetail(String cbOrderNo){
        LoanCarbankOrderDto loanCarbankOrderDetail = loanCarbankOrderService.getLoanCarbankOrderDetail(cbOrderNo);
        return ResultDtoFactory.toAckData(loanCarbankOrderDetail);
    }

    /**
     * 检查用户能否下单车闪贷
     * @return
     */
    @GetMapping("canCreateWishOrder")
    public ResultDto canCreateWishOrder(){
        String userId = SecurityContext.getCurrentUserId();
        if (StringUtils.isEmpty(userId)){
            return ResultDtoFactory.toNack("用户未登录");
        }
        Boolean hasSuccessOrder = loanCarbankOrderService.hasSuccessOrder(userId);
        if (hasSuccessOrder){
            return ResultDtoFactory.toNack("已有逾期或放款中的订单，请不要重复提交");
        }
        return ResultDtoFactory.toAck();
    }

}
