package com.xinyunlian.jinfu.dealer.order.service;

import com.xinyunlian.jinfu.common.util.AmtUtils;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.dealer.dto.DealerUserOrderDto;
import com.xinyunlian.jinfu.dealer.order.dto.OrderDto;
import com.xinyunlian.jinfu.dealer.order.dto.OrderSearchDto;
import com.xinyunlian.jinfu.dealer.service.DealerUserOrderService;
import com.xinyunlian.jinfu.insurance.dto.PerInsInfoSearchDto;
import com.xinyunlian.jinfu.insurance.dto.PerInsuranceInfoDto;
import com.xinyunlian.jinfu.insurance.dto.PerInsuranceInfoPageDto;
import com.xinyunlian.jinfu.insurance.service.InsuranceOrderService;
import com.xinyunlian.jinfu.loan.dto.req.LoanApplySearchDto;
import com.xinyunlian.jinfu.loan.dto.resp.apply.LoanApplyListEachDto;
import com.xinyunlian.jinfu.loan.service.LoanApplQueryService;
import com.xinyunlian.jinfu.store.service.StoreService;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by menglei on 2016年10月08日.
 */
@Service
public class OrderService {

    @Autowired
    private LoanApplQueryService loanApplQueryService;
    @Autowired
    private DealerUserOrderService dealerUserOrderService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private UserService userService;
    @Autowired
    private InsuranceOrderService insuranceOrderService;

    /**
     * 小贷订单处理
     *
     * @param orderSearchDto
     * @return
     */
    public OrderSearchDto getLoanPage(OrderSearchDto orderSearchDto) {
        if (StringUtils.isNotEmpty(orderSearchDto.getProvinceId()) || StringUtils.isNotEmpty(orderSearchDto.getCityId())
                || StringUtils.isNotEmpty(orderSearchDto.getAreaId()) || StringUtils.isNotEmpty(orderSearchDto.getStreetId())
                || StringUtils.isNotEmpty(orderSearchDto.getStoreName())) {
            return orderSearchDto;
        }
        LoanApplySearchDto loanApplySearchDto = new LoanApplySearchDto();
        loanApplySearchDto.setApplId(orderSearchDto.getOrderNo());
        loanApplySearchDto.setProdId(orderSearchDto.getProdId());
        loanApplySearchDto.setUserName(orderSearchDto.getUserName());
        loanApplySearchDto.setApplStartDate(orderSearchDto.getBeginTime());
        loanApplySearchDto.setApplEndDate(orderSearchDto.getEndTime());
        loanApplySearchDto.setProdId(orderSearchDto.getProdId());
        loanApplySearchDto.setPageSize(orderSearchDto.getPageSize());
        loanApplySearchDto.setCurrentPage(orderSearchDto.getCurrentPage());
        //分销商，分销员筛选条件处理
        Set<String> orderNoList = new HashSet<>();
        if (StringUtils.isNotEmpty(orderSearchDto.getDealerName())
                || StringUtils.isNotEmpty(orderSearchDto.getUserName())
                || StringUtils.isNotEmpty(orderSearchDto.getDealerProvinceId())
                || StringUtils.isNotEmpty(orderSearchDto.getDealerCityId())
                || StringUtils.isNotEmpty(orderSearchDto.getDealerAreaId())
                || StringUtils.isNotEmpty(orderSearchDto.getDealerStreetId())) {
            DealerUserOrderDto dealerUserOrderDto = new DealerUserOrderDto();
            dealerUserOrderDto.setUserName(orderSearchDto.getUserName());
            dealerUserOrderDto.setDealerName(orderSearchDto.getDealerName());
            dealerUserOrderDto.setProvinceId(orderSearchDto.getDealerProvinceId());
            dealerUserOrderDto.setCityId(orderSearchDto.getDealerCityId());
            dealerUserOrderDto.setAreaId(orderSearchDto.getDealerAreaId());
            dealerUserOrderDto.setStreetId(orderSearchDto.getDealerStreetId());
            List<DealerUserOrderDto> userOrderList = dealerUserOrderService.getDealerUserOrderList(dealerUserOrderDto);
            if (userOrderList.size() <= 0) {
                return orderSearchDto;
            }
            for (DealerUserOrderDto userOrderDto : userOrderList) {
                orderNoList.add(userOrderDto.getOrderNo());
            }
        }
        if (orderNoList.size() > 0) {
            loanApplySearchDto.setAppIds(orderNoList);
        }
        loanApplySearchDto = loanApplQueryService.listLoanAppl(loanApplySearchDto);

        List<String> orderNos = new ArrayList<>();
        List<String> userIds = new ArrayList<>();
        for (LoanApplyListEachDto loanApplyListEachDto : loanApplySearchDto.getList()) {
            orderNos.add(loanApplyListEachDto.getApplId());
            userIds.add(loanApplyListEachDto.getUserId() + StringUtils.EMPTY);
        }
        Map<String, DealerUserOrderDto> orderNoMap = getOrderNoMap(orderNos);
        Map<String, UserInfoDto> userIdMap = new HashMap<>();
        if (userIds.size() > 0) {
            List<UserInfoDto> userList = userService.findUserByUserId(userIds);
            for (UserInfoDto userInfoDto : userList) {
                userIdMap.put(userInfoDto.getUserId(), userInfoDto);
            }
        }
        List<OrderDto> orderList = new ArrayList<>();
        OrderDto orderDto;
        for (LoanApplyListEachDto loanApplyListEachDto : loanApplySearchDto.getList()) {
            orderDto = new OrderDto();
            orderDto.setOrderNo(loanApplyListEachDto.getApplId());
            orderDto.setProdName(loanApplyListEachDto.getProductName());
            orderDto.setStoreUserName(loanApplyListEachDto.getUserName());
            orderDto.setAmount(loanApplyListEachDto.getApplAmt() + StringUtils.EMPTY);
            orderDto.setStatus(loanApplyListEachDto.getStatus().getText());
            orderDto.setCreateTime(DateHelper.formatDate(loanApplyListEachDto.getCreateDate(), "yyyy-MM-dd HH:mm:ss"));
            if (userIdMap.get(loanApplyListEachDto.getUserId()) != null) {
                orderDto.setStoreUserName(userIdMap.get(loanApplyListEachDto.getUserId()).getUserName());
            }
            if (orderNoMap.get(loanApplyListEachDto.getApplId()) != null) {
                orderDto.setType("分销代办");
                orderDto.setDealerName(orderNoMap.get(loanApplyListEachDto.getApplId()).getDealerDto().getDealerName());
                orderDto.setUserName(orderNoMap.get(loanApplyListEachDto.getApplId()).getDealerUserDto().getName());
            } else {
                orderDto.setType("自助成交");
            }
            orderList.add(orderDto);
        }

        orderSearchDto.setList(orderList);
        orderSearchDto.setCurrentPage(loanApplySearchDto.getCurrentPage());
        orderSearchDto.setPageSize(loanApplySearchDto.getPageSize());
        orderSearchDto.setTotalPages(loanApplySearchDto.getTotalPages());
        orderSearchDto.setTotalRecord(loanApplySearchDto.getTotalRecord());
        return orderSearchDto;
    }

    /**
     * 店铺保订单处理
     *
     * @param orderSearchDto
     * @return
     */
    public OrderSearchDto getInsPage(OrderSearchDto orderSearchDto) {
        if (StringUtils.isNotEmpty(orderSearchDto.getStoreUserName())) {
            return orderSearchDto;
        }
        PerInsInfoSearchDto perInsInfoSearchDto = new PerInsInfoSearchDto();
        perInsInfoSearchDto.setPerInsuranceOrderNo(orderSearchDto.getOrderNo());
        perInsInfoSearchDto.setOperatorName(orderSearchDto.getUserName());
        perInsInfoSearchDto.setStoreName(orderSearchDto.getStoreName());
        perInsInfoSearchDto.setProductId(orderSearchDto.getProdId());
        perInsInfoSearchDto.setPageSize(orderSearchDto.getPageSize());
        perInsInfoSearchDto.setCurrentPage(orderSearchDto.getCurrentPage());
        StringBuffer sb = new StringBuffer();
        if (StringUtils.isNotEmpty(orderSearchDto.getProvinceId())) {
            sb.append(",").append(orderSearchDto.getProvinceId());
        }
        if (StringUtils.isNotEmpty(orderSearchDto.getCityId())) {
            sb.append(",").append(orderSearchDto.getCityId());
        }
        if (StringUtils.isNotEmpty(orderSearchDto.getAreaId())) {
            sb.append(",").append(orderSearchDto.getAreaId());
        }
        if (StringUtils.isNotEmpty(orderSearchDto.getStreetId())) {
            sb.append(",").append(orderSearchDto.getStreetId());
        }
        if (StringUtils.isNotEmpty(sb.toString())) {
            sb.append(",");
        }
        perInsInfoSearchDto.setStoreAreaTreePath(sb.toString());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (StringUtils.isNotEmpty(orderSearchDto.getBeginTime())) {
                perInsInfoSearchDto.setOrderDateFrom(sdf.parse(orderSearchDto.getBeginTime()));
            }
            if (StringUtils.isNotEmpty(orderSearchDto.getEndTime())) {
                perInsInfoSearchDto.setOrderDateTo(sdf.parse(orderSearchDto.getEndTime()));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //分销商，分销员筛选条件处理
        List<String> orderNoList = new ArrayList<>();
        if (StringUtils.isNotEmpty(orderSearchDto.getDealerName())
                || StringUtils.isNotEmpty(orderSearchDto.getDealerProvinceId())
                || StringUtils.isNotEmpty(orderSearchDto.getDealerCityId())
                || StringUtils.isNotEmpty(orderSearchDto.getDealerAreaId())
                || StringUtils.isNotEmpty(orderSearchDto.getDealerStreetId())) {
            DealerUserOrderDto dealerUserOrderDto = new DealerUserOrderDto();
            dealerUserOrderDto.setDealerName(orderSearchDto.getDealerName());
            dealerUserOrderDto.setProvinceId(orderSearchDto.getDealerProvinceId());
            dealerUserOrderDto.setCityId(orderSearchDto.getDealerCityId());
            dealerUserOrderDto.setAreaId(orderSearchDto.getDealerAreaId());
            dealerUserOrderDto.setStreetId(orderSearchDto.getDealerStreetId());
            List<DealerUserOrderDto> userOrderList = dealerUserOrderService.getDealerUserOrderList(dealerUserOrderDto);
            if (userOrderList.size() <= 0) {
                return orderSearchDto;
            }
            for (DealerUserOrderDto userOrderDto : userOrderList) {
                orderNoList.add(userOrderDto.getOrderNo());
            }
        }
        if (orderNoList.size() > 0) {
            perInsInfoSearchDto.setPerInsuranceOrderNoList(orderNoList);
        }
        PerInsuranceInfoPageDto perInsuranceInfoPageDto = insuranceOrderService.getInsOrderPage(perInsInfoSearchDto);

        List<String> orderNos = new ArrayList<>();
        for (PerInsuranceInfoDto perInsuranceInfoDto : perInsuranceInfoPageDto.getList()) {
            orderNos.add(perInsuranceInfoDto.getPerInsuranceOrderNo());
        }
        Map<String, DealerUserOrderDto> orderNoMap = getOrderNoMap(orderNos);

        List<OrderDto> orderList = new ArrayList<>();
        OrderDto orderDto;
        for (PerInsuranceInfoDto perInsuranceInfoDto : perInsuranceInfoPageDto.getList()) {
            orderDto = new OrderDto();
            orderDto.setOrderNo(perInsuranceInfoDto.getPerInsuranceOrderNo());
            orderDto.setProdName(perInsuranceInfoDto.getProductName());
            orderDto.setStoreName(perInsuranceInfoDto.getStoreName());
            orderDto.setAddress(perInsuranceInfoDto.getStoreAddress());
            orderDto.setStatus(perInsuranceInfoDto.getOrderStatus().getText());
            orderDto.setCreateTime(DateHelper.formatDate(perInsuranceInfoDto.getOrderDate(), "yyyy-MM-dd HH:mm:ss"));
            if (perInsuranceInfoDto.getInsuranceAmt() != null) {
                orderDto.setAmount(AmtUtils.formatAmt2TwoDecimal(BigDecimal.valueOf(perInsuranceInfoDto.getInsuranceFee()/1000)));
            }
            if (orderNoMap.get(perInsuranceInfoDto.getPerInsuranceOrderNo()) != null) {
                orderDto.setType("分销代办");
                orderDto.setDealerName(orderNoMap.get(perInsuranceInfoDto.getPerInsuranceOrderNo()).getDealerDto().getDealerName());
                orderDto.setUserName(orderNoMap.get(perInsuranceInfoDto.getPerInsuranceOrderNo()).getDealerUserDto().getName());
            } else {
                orderDto.setType("自助成交");
            }
            orderList.add(orderDto);
        }

        orderSearchDto.setList(orderList);
        orderSearchDto.setCurrentPage(perInsuranceInfoPageDto.getCurrentPage());
        orderSearchDto.setPageSize(perInsuranceInfoPageDto.getPageSize());
        orderSearchDto.setTotalPages(perInsuranceInfoPageDto.getTotalPages());
        orderSearchDto.setTotalRecord(perInsuranceInfoPageDto.getTotalRecord());
        return orderSearchDto;
    }

    private Map<String, DealerUserOrderDto> getOrderNoMap(List<String> orderNos) {
        Map<String, DealerUserOrderDto> orderNoMap = new HashMap<>();
        if (orderNos.size() > 0) {
            DealerUserOrderDto dealerUserOrderDto = new DealerUserOrderDto();
            dealerUserOrderDto.setOrderNoList(orderNos);
            List<DealerUserOrderDto> userOrderList = dealerUserOrderService.getDealerUserOrderList(dealerUserOrderDto);
            for (DealerUserOrderDto userOrderDto : userOrderList) {
                orderNoMap.put(userOrderDto.getOrderNo(), userOrderDto);
            }
        }
        return orderNoMap;
    }

}
