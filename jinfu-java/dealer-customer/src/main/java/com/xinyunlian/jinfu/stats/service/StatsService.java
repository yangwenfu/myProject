package com.xinyunlian.jinfu.stats.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.dealer.dto.*;
import com.xinyunlian.jinfu.dealer.enums.EDealerStatsOrderType;
import com.xinyunlian.jinfu.dealer.enums.EDealerUserOrderStatus;
import com.xinyunlian.jinfu.dealer.service.*;
import com.xinyunlian.jinfu.prod.dto.ProductDto;
import com.xinyunlian.jinfu.prod.enums.EProd;
import com.xinyunlian.jinfu.prod.service.ProdService;
import com.xinyunlian.jinfu.report.dealer.dto.*;
import com.xinyunlian.jinfu.report.dealer.service.DealerStatsMonthService;
import com.xinyunlian.jinfu.report.dealer.service.DealerStatsOrderService;
import com.xinyunlian.jinfu.report.dealer.service.DealerStatsStoreService;
import com.xinyunlian.jinfu.stats.dto.*;
import com.xinyunlian.jinfu.user.dto.DealerUserDto;
import com.xinyunlian.jinfu.user.enums.EDealerUserStatus;
import com.xinyunlian.jinfu.user.service.DealerUserService;
import com.xinyunlian.jinfu.yunma.dto.YMMemberDto;
import com.xinyunlian.jinfu.yunma.dto.YMMemberSearchDto;
import com.xinyunlian.jinfu.yunma.service.YMMemberService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by menglei on 2016年12月07日.
 */
@Service
public class StatsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatsService.class);

    @Autowired
    private DealerUserOrderService dealerUserOrderService;
    @Autowired
    private DealerUserStoreService dealerUserStoreService;
    @Autowired
    private ProdService prodService;
    @Autowired
    private DealerProdService dealerProdService;
    @Autowired
    private DealerStatsOrderService dealerStatsOrderService;
    @Autowired
    private DealerStatsStoreService dealerStatsStoreService;
    @Autowired
    private DealerStatsMonthService dealerStatsMonthService;
    @Autowired
    private YMMemberService yMMemberService;
    @Autowired
    private SignInfoService signInfoService;
    @Autowired
    private DealerUserService dealerUserService;

    /**
     * 根据时间获取订单列表
     *
     * @param statsOrderSearchDto
     * @return
     */
    public StatsOrderSearchDto getOrderPage(StatsOrderSearchDto statsOrderSearchDto) {
        //需要请求的参数
        DealerStatsOrderSearchDto dealerStatsOrderSearchDto = new DealerStatsOrderSearchDto();
        dealerStatsOrderSearchDto.setUserId(statsOrderSearchDto.getUserId());
        dealerStatsOrderSearchDto.setDealerId(statsOrderSearchDto.getDealerId());
        dealerStatsOrderSearchDto.setProdId(statsOrderSearchDto.getProdId());
        dealerStatsOrderSearchDto.setLastId(statsOrderSearchDto.getLastId());
        dealerStatsOrderSearchDto.setBeginTime(statsOrderSearchDto.getBeginTime());
        dealerStatsOrderSearchDto.setEndTime(statsOrderSearchDto.getEndTime());
        dealerStatsOrderSearchDto.setPageSize(10);
        dealerStatsOrderSearchDto.setCurrentPage(statsOrderSearchDto.getCurrentPage());
        dealerStatsOrderSearchDto.setTotalRecord(statsOrderSearchDto.getTotalRecord());
        dealerStatsOrderSearchDto.setTotalPages(statsOrderSearchDto.getTotalPages());
        dealerStatsOrderSearchDto = dealerStatsOrderService.getStatsOrderPage(dealerStatsOrderSearchDto);
        //转换
        statsOrderSearchDto.setPageSize(dealerStatsOrderSearchDto.getPageSize());
        statsOrderSearchDto.setCurrentPage(dealerStatsOrderSearchDto.getCurrentPage());
        statsOrderSearchDto.setTotalRecord(dealerStatsOrderSearchDto.getTotalRecord());
        statsOrderSearchDto.setTotalPages(dealerStatsOrderSearchDto.getTotalPages());
        //获取分销员
        List<String> dealerUserIds = new ArrayList<>();
        for (DealerStatsOrderDto dealerStatsOrderDto : dealerStatsOrderSearchDto.getList()) {
            dealerUserIds.add(dealerStatsOrderDto.getUserId());
        }
        List<DealerUserDto> dealerUsers = dealerUserService.findByDealerUserIds(dealerUserIds);
        Map<String, DealerUserDto> dealerUserMap = new HashMap<>();
        for (DealerUserDto dealerUserDto : dealerUsers) {
            dealerUserMap.put(dealerUserDto.getUserId(), dealerUserDto);
        }
        List<OrderInfoDto> date = new ArrayList<>();
        for (DealerStatsOrderDto dealerStatsOrderDto : dealerStatsOrderSearchDto.getList()) {
            OrderInfoDto orderInfoDto = ConverterService.convert(dealerStatsOrderDto, OrderInfoDto.class);
            //orderInfoDto.setUserCreateTs(DateHelper.formatDate(dealerStatsOrderDto.getUserCreateTs(), "yyyy.MM.dd HH:mm"));
            //orderInfoDto.setStoreCreateTs(DateHelper.formatDate(dealerStatsOrderDto.getStoreCreateTs(), "yyyy.MM.dd HH:mm"));
            orderInfoDto.setCreateTs(DateHelper.formatDate(dealerStatsOrderDto.getCreateTs(), "yyyy.MM.dd HH:mm"));
            orderInfoDto.setStatusStr(dealerStatsOrderDto.getStatus().getText());
            if (dealerStatsOrderDto.getProdId().equals(EProd.L01001.getCode()) || dealerStatsOrderDto.getProdId().equals(EProd.L01002.getCode()) || dealerStatsOrderDto.getProdId().equals(EProd.L01003.getCode())) {
                orderInfoDto.setType(EDealerStatsOrderType.USERNAME.getCode());
            } else {
                orderInfoDto.setType(EDealerStatsOrderType.STORENAME.getCode());
            }
            //获取分销员
            DealerUserDto dealerUserDto = dealerUserMap.get(dealerStatsOrderDto.getUserId());
            if (dealerUserDto != null) {
                orderInfoDto.setDealerUserName(dealerUserDto.getName());
                String mobile = dealerUserDto.getMobile();
                if (EDealerUserStatus.DELETE.equals(dealerUserDto.getStatus())) {
                    mobile = "已删除";
                }
                orderInfoDto.setDealerUserMobile(mobile);
            }
            date.add(orderInfoDto);
        }
        statsOrderSearchDto.setList(date);
        return statsOrderSearchDto;
    }

    /**
     * 根据时间获取订单列表
     *
     * @param statsStoreSearchDto
     * @return
     */
    public StatsStoreSearchDto getStorePage(StatsStoreSearchDto statsStoreSearchDto) {
        //需要请求的参数
        DealerStatsStoreSearchDto dealerStatsStoreSearchDto = new DealerStatsStoreSearchDto();
        dealerStatsStoreSearchDto.setUserId(statsStoreSearchDto.getUserId());
        dealerStatsStoreSearchDto.setDealerId(statsStoreSearchDto.getDealerId());
        dealerStatsStoreSearchDto.setLastId(statsStoreSearchDto.getLastId());
        dealerStatsStoreSearchDto.setBeginTime(statsStoreSearchDto.getBeginTime());
        dealerStatsStoreSearchDto.setEndTime(statsStoreSearchDto.getEndTime());
        dealerStatsStoreSearchDto.setPageSize(10);
        dealerStatsStoreSearchDto.setCurrentPage(statsStoreSearchDto.getCurrentPage());
        dealerStatsStoreSearchDto.setTotalRecord(statsStoreSearchDto.getTotalRecord());
        dealerStatsStoreSearchDto.setTotalPages(statsStoreSearchDto.getTotalPages());
        dealerStatsStoreSearchDto = dealerStatsStoreService.getStatsStorePage(dealerStatsStoreSearchDto);
        //转换
        statsStoreSearchDto.setPageSize(dealerStatsStoreSearchDto.getPageSize());
        statsStoreSearchDto.setCurrentPage(dealerStatsStoreSearchDto.getCurrentPage());
        statsStoreSearchDto.setTotalRecord(dealerStatsStoreSearchDto.getTotalRecord());
        statsStoreSearchDto.setTotalPages(dealerStatsStoreSearchDto.getTotalPages());
        //获取分销员
        List<String> dealerUserIds = new ArrayList<>();
        for (DealerStatsStoreDto dealerStatsStoreDto : dealerStatsStoreSearchDto.getList()) {
            dealerUserIds.add(dealerStatsStoreDto.getUserId());
        }
        List<DealerUserDto> dealerUsers = dealerUserService.findByDealerUserIds(dealerUserIds);
        Map<String, DealerUserDto> dealerUserMap = new HashMap<>();
        for (DealerUserDto dealerUserDto : dealerUsers) {
            dealerUserMap.put(dealerUserDto.getUserId(), dealerUserDto);
        }
        List<StoreInfoDto> date = new ArrayList<>();
        for (DealerStatsStoreDto dealerStatsStoreDto : dealerStatsStoreSearchDto.getList()) {
            StoreInfoDto storeInfoDto = ConverterService.convert(dealerStatsStoreDto, StoreInfoDto.class);
            storeInfoDto.setUserCreateTs(DateHelper.formatDate(dealerStatsStoreDto.getUserCreateTs(), "yyyy.MM.dd HH:mm"));
            storeInfoDto.setStoreCreateTs(DateHelper.formatDate(dealerStatsStoreDto.getStoreCreateTs(), "yyyy.MM.dd HH:mm"));
            if (StringUtils.isEmpty(dealerStatsStoreDto.getQrCodeeUrl())) {
                storeInfoDto.setQrCode(false);
            } else {
                storeInfoDto.setQrCode(true);
            }
            //获取分销员
            DealerUserDto dealerUserDto = dealerUserMap.get(dealerStatsStoreDto.getUserId());
            if (dealerUserDto != null) {
                storeInfoDto.setDealerUserName(dealerUserDto.getName());
                String mobile = dealerUserDto.getMobile();
                if (EDealerUserStatus.DELETE.equals(dealerUserDto.getStatus())) {
                    mobile = "已删除";
                }
                storeInfoDto.setDealerUserMobile(mobile);
            }
            date.add(storeInfoDto);
        }
        statsStoreSearchDto.setList(date);
        return statsStoreSearchDto;
    }

    /**
     * 根据时间获取拜访列表
     *
     * @param statsSignInfoSearchDto
     * @return
     */
    public StatsSignInfoSearchDto getSignInfoPage(StatsSignInfoSearchDto statsSignInfoSearchDto) {
        //需要请求的参数
        SignInfoViewSearchDto signInfoViewSearchDto = new SignInfoViewSearchDto();
        signInfoViewSearchDto.setUserId(statsSignInfoSearchDto.getUserId());
        signInfoViewSearchDto.setDealerId(statsSignInfoSearchDto.getDealerId());
        signInfoViewSearchDto.setLastId(statsSignInfoSearchDto.getLastId());
        signInfoViewSearchDto.setStartTime(statsSignInfoSearchDto.getBeginTime());
        signInfoViewSearchDto.setEndTime(statsSignInfoSearchDto.getEndTime());
        signInfoViewSearchDto.setPageSize(10);
        signInfoViewSearchDto.setCurrentPage(statsSignInfoSearchDto.getCurrentPage());
        signInfoViewSearchDto.setTotalRecord(statsSignInfoSearchDto.getTotalRecord());
        signInfoViewSearchDto.setTotalPages(statsSignInfoSearchDto.getTotalPages());
        signInfoViewSearchDto = signInfoService.getSignInfoPage(signInfoViewSearchDto);
        //转换
        statsSignInfoSearchDto.setPageSize(signInfoViewSearchDto.getPageSize());
        statsSignInfoSearchDto.setCurrentPage(signInfoViewSearchDto.getCurrentPage());
        statsSignInfoSearchDto.setTotalRecord(signInfoViewSearchDto.getTotalRecord());
        statsSignInfoSearchDto.setTotalPages(signInfoViewSearchDto.getTotalPages());
        //获取分销员
        List<String> dealerUserIds = new ArrayList<>();
        for (SignInfoViewDto signInfoViewDto : signInfoViewSearchDto.getList()) {
            dealerUserIds.add(signInfoViewDto.getUserId());
        }
        List<DealerUserDto> dealerUsers = dealerUserService.findByDealerUserIds(dealerUserIds);
        Map<String, DealerUserDto> dealerUserMap = new HashMap<>();
        for (DealerUserDto dealerUserDto : dealerUsers) {
            dealerUserMap.put(dealerUserDto.getUserId(), dealerUserDto);
        }
        List<StatsSignInfoDto> date = new ArrayList<>();
        for (SignInfoViewDto signInfoViewDto : signInfoViewSearchDto.getList()) {
            StatsSignInfoDto statsSignInfoDto = new StatsSignInfoDto();
            statsSignInfoDto.setId(signInfoViewDto.getId());
            statsSignInfoDto.setStoreName(signInfoViewDto.getStoreName());
            if (signInfoViewDto.getSignInTime() != null) {
                statsSignInfoDto.setSignInTime(DateHelper.formatDate(signInfoViewDto.getSignInTime(), "yyyy.MM.dd HH:mm"));
            } else {
                statsSignInfoDto.setSignInTime("--");
            }
            if (signInfoViewDto.getSignOutTime() != null) {
                statsSignInfoDto.setSignOutTime(DateHelper.formatDate(signInfoViewDto.getSignOutTime(), "yyyy.MM.dd HH:mm"));
            } else {
                statsSignInfoDto.setSignOutTime("--");
            }
            if (StringUtils.isNotEmpty(signInfoViewDto.getDistanceTime())) {
                statsSignInfoDto.setDistanceTime(signInfoViewDto.getDistanceTime());
            } else {
                statsSignInfoDto.setDistanceTime("--");
            }
            //获取分销员
            DealerUserDto dealerUserDto = dealerUserMap.get(signInfoViewDto.getUserId());
            if (dealerUserDto != null) {
                statsSignInfoDto.setDealerUserName(dealerUserDto.getName());
                String mobile = dealerUserDto.getMobile();
                if (EDealerUserStatus.DELETE.equals(dealerUserDto.getStatus())) {
                    mobile = "已删除";
                }
                statsSignInfoDto.setDealerUserMobile(mobile);
            }
            date.add(statsSignInfoDto);
        }
        statsSignInfoSearchDto.setList(date);
        return statsSignInfoSearchDto;
    }

    /**
     * 根据时间获取云码列表
     *
     * @param statsMemberSearchDto
     * @return
     */
    public StatsMemberSearchDto getMemberPage(StatsMemberSearchDto statsMemberSearchDto) {
        //需要请求的参数
        YMMemberSearchDto yMMemberSearchDto = new YMMemberSearchDto();
        yMMemberSearchDto.setLastId(statsMemberSearchDto.getLastId());
        yMMemberSearchDto.setStartBindTime(statsMemberSearchDto.getBeginTime());
        yMMemberSearchDto.setEndBindTime(statsMemberSearchDto.getEndTime());
        yMMemberSearchDto.setPageSize(10);
        yMMemberSearchDto.setCurrentPage(statsMemberSearchDto.getCurrentPage());
        yMMemberSearchDto.setTotalRecord(statsMemberSearchDto.getTotalRecord());
        yMMemberSearchDto.setTotalPages(statsMemberSearchDto.getTotalPages());
        if (StringUtils.isEmpty(statsMemberSearchDto.getUserId())) {
            List<DealerUserDto> userList= dealerUserService.findDealerUsersByDealerId(statsMemberSearchDto.getDealerId());
            List<String> createOpIds = new ArrayList<>();
            for (DealerUserDto userDto : userList) {
                createOpIds.add(userDto.getUserId());
            }
            yMMemberSearchDto.setCreateOpIds(createOpIds);
        } else {
            yMMemberSearchDto.setCreateOpId(statsMemberSearchDto.getUserId());
        }
        yMMemberSearchDto = yMMemberService.getMemberViewPage(yMMemberSearchDto);
        //转换
        statsMemberSearchDto.setPageSize(yMMemberSearchDto.getPageSize());
        statsMemberSearchDto.setCurrentPage(yMMemberSearchDto.getCurrentPage());
        statsMemberSearchDto.setTotalRecord(yMMemberSearchDto.getTotalRecord());
        statsMemberSearchDto.setTotalPages(yMMemberSearchDto.getTotalPages());
        //获取分销员
        List<String> dealerUserIds = new ArrayList<>();
        for (YMMemberSearchDto memberSearchDto : yMMemberSearchDto.getList()) {
            dealerUserIds.add(memberSearchDto.getCreateOpId());
        }
        List<DealerUserDto> dealerUsers = dealerUserService.findByDealerUserIds(dealerUserIds);
        Map<String, DealerUserDto> dealerUserMap = new HashMap<>();
        for (DealerUserDto dealerUserDto : dealerUsers) {
            dealerUserMap.put(dealerUserDto.getUserId(), dealerUserDto);
        }
        List<StatsMemberDto> date = new ArrayList<>();
        for (YMMemberSearchDto memberSearchDto : yMMemberSearchDto.getList()) {
            StatsMemberDto statsMemberDto = new StatsMemberDto();
            statsMemberDto.setId(memberSearchDto.getId());
            statsMemberDto.setStoreName(memberSearchDto.getStoreName());
            statsMemberDto.setUserName(memberSearchDto.getUserName());
            statsMemberDto.setBindTime(DateHelper.formatDate(memberSearchDto.getBindTime(), "yyyy.MM.dd HH:mm"));
            //获取分销员
            DealerUserDto dealerUserDto = dealerUserMap.get(memberSearchDto.getCreateOpId());
            if (dealerUserDto != null) {
                statsMemberDto.setDealerUserName(dealerUserDto.getName());
                String mobile = dealerUserDto.getMobile();
                if (EDealerUserStatus.DELETE.equals(dealerUserDto.getStatus())) {
                    mobile = "已删除";
                }
                statsMemberDto.setDealerUserMobile(mobile);
            }
            date.add(statsMemberDto);
        }
        statsMemberSearchDto.setList(date);
        return statsMemberSearchDto;
    }

    /**
     * 统计数据
     *
     * @param beginTime
     * @param endTime
     * @param dealerUserDto
     * @return
     */
    public StatsDetailDto getStats(String beginTime, String endTime, DealerUserDto dealerUserDto) {
        StatsDetailDto statsDetailDto = new StatsDetailDto();
        statsDetailDto.setBeginTime(beginTime);
        statsDetailDto.setEndTime(endTime);

        //代办数据统计处理
        DealerUserOrderDto dealerUserOrderDto = new DealerUserOrderDto();
        dealerUserOrderDto.setUserId(dealerUserDto.getUserId());
        dealerUserOrderDto.setDealerId(dealerUserDto.getDealerId());
        dealerUserOrderDto.setBeginTime(beginTime);
        dealerUserOrderDto.setEndTime(endTime);
        List<DealerUserOrderDto> orderList = dealerUserOrderService.getOrderListByUserId(dealerUserOrderDto);
        Map<String, StatsOrderDto> orderMap = getProdMap(dealerUserDto.getDealerId());
        //订单产品统计列表
        List<StatsOrderDto> statsOrders = new ArrayList<>();
        StatsOrderDto statsOrderDto;
        for (DealerUserOrderDto orderDto : orderList) {
            if (orderMap.get(orderDto.getProdId()) == null) {//不存在的新增
                statsOrderDto = new StatsOrderDto();
                ProductDto productDto = prodService.getProdById(orderDto.getProdId());
                if (productDto == null) {
                    continue;
                }
                statsOrderDto.setProdId(productDto.getProdId());
                statsOrderDto.setProdName(productDto.getProdName());
                if (orderDto.getStatus().equals(EDealerUserOrderStatus.SUCCESS)) {
                    statsOrderDto.setSuccessCount(1l);
                } else {
                    statsOrderDto.setSuccessCount(0l);
                }
                statsOrderDto.setAllCount(1l);
                orderMap.put(orderDto.getProdId(), statsOrderDto);
            } else {//已存在的+1
                statsOrderDto = orderMap.get(orderDto.getProdId());
                if (orderDto.getStatus().equals(EDealerUserOrderStatus.SUCCESS)) {
                    statsOrderDto.setSuccessCount(statsOrderDto.getSuccessCount() + 1l);
                }
                statsOrderDto.setAllCount(statsOrderDto.getAllCount() + 1l);
                orderMap.put(orderDto.getProdId(), statsOrderDto);
            }
        }
        statsOrders.addAll(orderMap.values());
        statsDetailDto.setStatsOrders(statsOrders);

        //店铺相关统计
        List<StatsStoreDto> statsStores = new ArrayList<>();
        //注册数据统计
        DealerUserStoreDto dealerUserStoreDto = new DealerUserStoreDto();
        dealerUserStoreDto.setUserId(dealerUserDto.getUserId());
        dealerUserStoreDto.setDealerId(dealerUserDto.getDealerId());
        dealerUserStoreDto.setBeginTime(beginTime);
        dealerUserStoreDto.setEndTime(endTime);
        List<DealerUserStoreDto> storeList = dealerUserStoreService.getStoreListByUserId(dealerUserStoreDto);
        //绑云码处理
        YMMemberSearchDto yMMemberSearchDto = new YMMemberSearchDto();
        yMMemberSearchDto.setStartBindTime(beginTime);
        yMMemberSearchDto.setEndBindTime(endTime);
        if (StringUtils.isEmpty(dealerUserDto.getUserId())) {
            List<DealerUserDto> userList= dealerUserService.findDealerUsersByDealerId(dealerUserDto.getDealerId());
            List<String> createOpIds = new ArrayList<>();
            for (DealerUserDto userDto : userList) {
                createOpIds.add(userDto.getUserId());
            }
            yMMemberSearchDto.setCreateOpIds(createOpIds);
        } else {
            yMMemberSearchDto.setCreateOpId(dealerUserDto.getUserId());
        }
        List<YMMemberDto> memberList = yMMemberService.getMemberList(yMMemberSearchDto);
        //拜访数据统计处理
        SignInfoViewSearchDto signInfoViewSearchDto = new SignInfoViewSearchDto();
        signInfoViewSearchDto.setStartTime(beginTime);
        signInfoViewSearchDto.setEndTime(endTime);
        signInfoViewSearchDto.setUserId(dealerUserDto.getUserId());
        signInfoViewSearchDto.setDealerId(dealerUserDto.getDealerId());
        List<SignInfoViewDto> signInfoList = signInfoService.getSignInfoList(signInfoViewSearchDto);

        StatsStoreDto statsStoreDto = new StatsStoreDto();
        statsStoreDto.setStatsName("店铺拜访");
        statsStoreDto.setDescribe("拜访");
        statsStoreDto.setCount(Long.valueOf(signInfoList.size()));
        statsStoreDto.setType("SIGN");
        statsStores.add(statsStoreDto);

        statsStoreDto = new StatsStoreDto();
        statsStoreDto.setStatsName("店铺添加");
        statsStoreDto.setDescribe("添加");
        statsStoreDto.setCount(Long.valueOf(storeList.size()));
        statsStoreDto.setType("STORE");
        statsStores.add(statsStoreDto);

        statsStoreDto = new StatsStoreDto();
        statsStoreDto.setStatsName("云联云码");
        statsStoreDto.setDescribe("开通");
        statsStoreDto.setCount(Long.valueOf(memberList.size()));
        statsStoreDto.setType("YUNMA");
        statsStores.add(statsStoreDto);

        statsDetailDto.setStatsStores(statsStores);
        return statsDetailDto;
    }

    /**
     * 统计历史数据
     *
     * @param statsMonthSearchDto
     * @return
     */
    public StatsMonthSearchDto getStatsByMonth(StatsMonthSearchDto statsMonthSearchDto) {
        DealerStatsMonthSearchDto dealerStatsMonthSearchDto = new DealerStatsMonthSearchDto();
        dealerStatsMonthSearchDto.setLastId(statsMonthSearchDto.getLastId());
        dealerStatsMonthSearchDto.setPageSize(10);
        dealerStatsMonthSearchDto.setCurrentPage(statsMonthSearchDto.getCurrentPage());
        dealerStatsMonthSearchDto.setTotalRecord(statsMonthSearchDto.getTotalRecord());
        dealerStatsMonthSearchDto.setTotalPages(statsMonthSearchDto.getTotalPages());
        if (StringUtils.isNotEmpty(statsMonthSearchDto.getUserId())) {
            dealerStatsMonthSearchDto.setUserId(statsMonthSearchDto.getUserId());
            dealerStatsMonthSearchDto = dealerStatsMonthService.getStatsMonthPageByUserId(dealerStatsMonthSearchDto);
        } else if (StringUtils.isNotEmpty(statsMonthSearchDto.getDealerId())) {
            dealerStatsMonthSearchDto.setDealerId(statsMonthSearchDto.getDealerId());
            dealerStatsMonthSearchDto = dealerStatsMonthService.getStatsMonthPageByDealerId(dealerStatsMonthSearchDto);
        }

        List<String> months = new ArrayList<>();
        for (DealerStatsMonthDto dealerStatsMonthDto : dealerStatsMonthSearchDto.getList()) {
            months.add(dealerStatsMonthDto.getMonthDate());
        }
        Map<String, List<StatsOrderDto>> orderMap = new HashMap<>();//代办
        Map<String, DealerStatsMonthObjectDto> signMap = new HashMap<>();//拜访
        Map<String, DealerStatsMonthObjectDto> memberMap = new HashMap<>();//云码
        List<StatsOrderDto> orders;
        if (months.size() > 0) {
            List<DealerStatsMonthOrderDto> orderList = new ArrayList<>();
            if (StringUtils.isNotEmpty(statsMonthSearchDto.getUserId())) {
                orderList = dealerStatsOrderService.getMonthByUserId(statsMonthSearchDto.getUserId(), months);
            } else if (StringUtils.isNotEmpty(statsMonthSearchDto.getDealerId())) {
                orderList = dealerStatsOrderService.getMonthByDealerId(statsMonthSearchDto.getDealerId(), months);
            }
            for (DealerStatsMonthOrderDto dealerStatsMonthOrderDto : orderList) {
                orders = new ArrayList<>();
                StatsOrderDto statsOrderDto = ConverterService.convert(dealerStatsMonthOrderDto, StatsOrderDto.class);
                if (orderMap.get(dealerStatsMonthOrderDto.getMonthDate()) != null) {
                    orders = orderMap.get(dealerStatsMonthOrderDto.getMonthDate());
                }
                orders.add(statsOrderDto);
                orderMap.put(dealerStatsMonthOrderDto.getMonthDate(), orders);
            }
            List<DealerStatsMonthObjectDto> signList = new ArrayList<>();
            if (StringUtils.isNotEmpty(statsMonthSearchDto.getUserId())) {
                signList = dealerStatsMonthService.getMonthSignInfoByUserId(statsMonthSearchDto.getUserId(), months);
            } else if (StringUtils.isNotEmpty(statsMonthSearchDto.getDealerId())) {
                signList = dealerStatsMonthService.getMonthSignInfoByDealerId(statsMonthSearchDto.getDealerId(), months);
            }
            for (DealerStatsMonthObjectDto dealerStatsMonthObjectDto : signList) {
                signMap.put(dealerStatsMonthObjectDto.getMonthDate(), dealerStatsMonthObjectDto);
            }
            List<DealerStatsMonthObjectDto> memberList = new ArrayList<>();
            if (StringUtils.isNotEmpty(statsMonthSearchDto.getUserId())) {
                memberList = dealerStatsMonthService.getMonthMemberByUserId(statsMonthSearchDto.getUserId(), months);
            } else if (StringUtils.isNotEmpty(statsMonthSearchDto.getDealerId())) {
                memberList = dealerStatsMonthService.getMonthMemberByDealerId(statsMonthSearchDto.getDealerId(), months);
            }
            for (DealerStatsMonthObjectDto dealerStatsMonthObjectDto : memberList) {
                memberMap.put(dealerStatsMonthObjectDto.getMonthDate(), dealerStatsMonthObjectDto);
            }
        }
        //查询当前可办业务，放到代办业务列表
        Map<String, StatsOrderDto> orderDtoMap = getProdMap(statsMonthSearchDto.getDealerId());
        Map<String, StatsOrderDto> _orderDtoMap;
        //转换
        statsMonthSearchDto.setPageSize(dealerStatsMonthSearchDto.getPageSize());
        statsMonthSearchDto.setCurrentPage(dealerStatsMonthSearchDto.getCurrentPage());
        statsMonthSearchDto.setTotalRecord(dealerStatsMonthSearchDto.getTotalRecord());
        statsMonthSearchDto.setTotalPages(dealerStatsMonthSearchDto.getTotalPages());
        List<StatsDetailDto> date = new ArrayList<>();
        List<StatsOrderDto> statsOrders;
        List<StatsStoreDto> statsStores;
        StatsStoreDto statsStoreDto;
        for (DealerStatsMonthDto dealerStatsMonthDto : dealerStatsMonthSearchDto.getList()) {
            StatsDetailDto statsDetailDto = ConverterService.convert(dealerStatsMonthDto, StatsDetailDto.class);
            statsDetailDto.setMonthDate(dealerStatsMonthDto.getMonthDate().replace("-", "."));
            if (orderMap.get(dealerStatsMonthDto.getMonthDate()) != null) {
                statsOrders = new ArrayList<>();
                _orderDtoMap = new HashMap<>();
                _orderDtoMap.putAll(orderDtoMap);
                for (StatsOrderDto statsOrderDto : orderMap.get(dealerStatsMonthDto.getMonthDate())) {
                    _orderDtoMap.put(statsOrderDto.getProdId(), statsOrderDto);
                }
                statsOrders.addAll(_orderDtoMap.values());
                statsDetailDto.setStatsOrders(statsOrders);
            } else {
                statsOrders = new ArrayList<>();
                statsOrders.addAll(orderDtoMap.values());
                statsDetailDto.setStatsOrders(statsOrders);
            }

            //拜访，云码，添加
            Long signInfoCount = 0L;
            Long qrCodeCount = 0L;
            if (signMap.get(dealerStatsMonthDto.getMonthDate()) != null) {
                DealerStatsMonthObjectDto dealerStatsMonthObjectDto = signMap.get(dealerStatsMonthDto.getMonthDate());
                signInfoCount = dealerStatsMonthObjectDto.getCount();
            }
            if (memberMap.get(dealerStatsMonthDto.getMonthDate()) != null) {
                DealerStatsMonthObjectDto dealerStatsMonthObjectDto = memberMap.get(dealerStatsMonthDto.getMonthDate());
                qrCodeCount = dealerStatsMonthObjectDto.getCount();
            }
            statsStores = new ArrayList<>();
            statsStoreDto = new StatsStoreDto();
            statsStoreDto.setStatsName("店铺拜访");
            statsStoreDto.setDescribe("拜访");
            statsStoreDto.setCount(signInfoCount);
            statsStoreDto.setType("SIGN");
            statsStores.add(statsStoreDto);

            statsStoreDto = new StatsStoreDto();
            statsStoreDto.setStatsName("店铺添加");
            statsStoreDto.setDescribe("添加");
            statsStoreDto.setCount(dealerStatsMonthDto.getRegisterCount());
            statsStoreDto.setType("STORE");
            statsStores.add(statsStoreDto);

            statsStoreDto = new StatsStoreDto();
            statsStoreDto.setStatsName("云联云码");
            statsStoreDto.setDescribe("开通");
            statsStoreDto.setCount(qrCodeCount);
            statsStoreDto.setType("YUNMA");
            statsStores.add(statsStoreDto);

            statsDetailDto.setStatsStores(statsStores);

            date.add(statsDetailDto);
        }
        statsMonthSearchDto.setList(date);
        return statsMonthSearchDto;
    }

    public Map<String, StatsOrderDto> getProdMap(String dealerId) {
        //可办业务列表
        List<ProductDto> prodList = new ArrayList<>();
        DealerProdSearchDto dealerProdSearchDto = new DealerProdSearchDto();
        dealerProdSearchDto.setDealerId(dealerId);
        dealerProdSearchDto.setExpire(true);
        List<DealerProdDto> dealerProdDtoList = dealerProdService.getDealerProdList(dealerProdSearchDto);
        if (dealerProdDtoList != null && dealerProdDtoList.size() > 0) {
            List<String> prodIdsList = new ArrayList<>();
            for (DealerProdDto dealerProdDto : dealerProdDtoList) {
                if (!dealerProdDto.getProdId().equals(EProd.P01001.getCode())) {//过滤云码
                    prodIdsList.add(dealerProdDto.getProdId());
                }
            }
            prodList = prodService.getProdListByIds(prodIdsList);
        }
        Map<String, StatsOrderDto> orderMap = new HashMap<>();
        StatsOrderDto statsOrderDto;
        for (ProductDto productDto : prodList) {//初始化展示业务
            statsOrderDto = new StatsOrderDto();
            statsOrderDto.setProdId(productDto.getProdId());
            statsOrderDto.setProdName(productDto.getProdName());
            statsOrderDto.setSuccessCount(0l);
            statsOrderDto.setAllCount(0l);
            orderMap.put(productDto.getProdId(), statsOrderDto);
        }
        return orderMap;
    }

}
