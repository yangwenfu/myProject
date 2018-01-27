package com.xinyunlian.jinfu; /**
 * Created by Admin on 2016/8/10.
 */

import com.xinyunlian.jinfu.carbank.api.dto.request.*;
import com.xinyunlian.jinfu.carbank.api.dto.response.*;
import com.xinyunlian.jinfu.carbank.dao.LoanCarbankOrderDao;
import com.xinyunlian.jinfu.carbank.dto.*;
import com.xinyunlian.jinfu.carbank.entity.LoanCarbankOrderPo;
import com.xinyunlian.jinfu.carbank.enums.ECbOrderStatus;
import com.xinyunlian.jinfu.carbank.service.LoanCarbankOrderService;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author willwang
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:standard-code-dao-test.xml"})
public class CarBankTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarBankTest.class);

    @Autowired
    private LoanCarbankOrderService loanCarbankOrderService;

    @Autowired
    private LoanCarbankOrderDao loanCarbankOrderDao;

    @Test
    public void BrandTest() {
        List<BrandDto> vehicleBrands = loanCarbankOrderService.getVehicleBrands();
        System.out.println(JsonUtil.toJson(vehicleBrands));
    }

    @Test
    public void CityTest() {
        List<CityDto> cities = loanCarbankOrderService.getCities();
        System.out.println("CityTest: " + JsonUtil.toJson(cities));
    }

    @Test
    public void SeriesTest(){
        List<SeriesDto> vehicleSeries = loanCarbankOrderService.getVehicleSeries(1);
        System.out.println(JsonUtil.toJson(vehicleSeries));
    }

    @Test
    public void vehicleModelTest(){
        List<VehicleModelDto> vehicleModels = loanCarbankOrderService.getVehicleModels(1, 5);
        System.out.println("vehicleModelTest: " + JsonUtil.toJson(vehicleModels));
    }

    @Test
    public void createWishOrder(){
        WishOrderRequest request = new WishOrderRequest();
        request.setCityId(5);
        request.setMobile("13787656787");
        request.setVehicleModelId(19571);
        request.setRegisterDate("2010-07-10");
        request.setLoanAmount("1000");
        request.setTerm("12");
        request.setInvitationCode("hz160000");
        request.setDealerName("云联金服");

        WishOrderResponse response = request.send();
        System.out.println("createWishOrder: " + JsonUtil.toJson(response));
    }

    @Test
    public void orderStatus(){
        OrderStatusRequest request = new OrderStatusRequest();
        request.setLoanApplyNo("2367745371602");
        OrderStatusResponse response = request.send();
        System.out.println("orderStatus: " + JsonUtil.toJson(response));
    }

    @Test
    public void orderOverDue(){
        OrderOverDueRequest request = new OrderOverDueRequest();
        request.setApplyNo("2367745371602");
        OrderOverDueResponse response = request.send();
        System.out.println("orderOverDue: " + JsonUtil.toJson(response));
    }

    @Test
    public void getPage(){
        LoanCarbankOrderSearchDto searchDto = new LoanCarbankOrderSearchDto();
        searchDto.setUserId("UC0000000125");
        LoanCarbankOrderSearchDto page = loanCarbankOrderService.getPage(searchDto);
        System.out.println("getPage: " + JsonUtil.toJson(page));
    }

    @Test
    public void checkOrder(){
        Boolean hasSuccessOrder = loanCarbankOrderService.hasSuccessOrder("UC0000000402");
        System.out.println("hasSuccessOrder = " + hasSuccessOrder);
    }

    @Test
    public void saveOrder(){
        LoanCarbankOrderPo wishOrderPo = new LoanCarbankOrderPo();
        wishOrderPo.setOverdue(false);
        wishOrderPo.setLoanAmt(new BigDecimal("199"));
        wishOrderPo.setTermLen(14);
        wishOrderPo.setIssueLoanDate(new Date());
        wishOrderPo.setReason("reason");
        wishOrderPo.setOrderStatus(ECbOrderStatus.INVITE_TO_STORE);
        wishOrderPo.setOutTradeNo("1242342434");
        wishOrderPo.setCityId(1);
        wishOrderPo.setCityName("fwew");
        wishOrderPo.setUserId("UC0000000125");
        wishOrderPo.setUserName("wewtewt");
        wishOrderPo.setVehicleBrandId(1);
        wishOrderPo.setVehicleBrandName("wegwe");
        wishOrderPo.setVehicleModelId(2);
        wishOrderPo.setVehicleModelName("reweq");
        wishOrderPo.setVehicleRegisterDate(new Date());
        wishOrderPo.setVehicleSeriesId(4);
        wishOrderPo.setVehicleSeriesName("gerererf");
        wishOrderPo.setWishOrderMobile("17098765678");
        LoanCarbankOrderPo dbOrder = loanCarbankOrderDao.save(wishOrderPo);
        dbOrder.setLastMntOpId(dbOrder.getCreateOpId());
        dbOrder.setLastMntTs(dbOrder.getCreateTs());
        loanCarbankOrderDao.save(dbOrder);
    }

}

