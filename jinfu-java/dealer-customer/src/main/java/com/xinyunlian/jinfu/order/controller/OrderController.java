package com.xinyunlian.jinfu.order.controller;

import com.xinyunlian.jinfu.api.dto.ApiBaiduDto;
import com.xinyunlian.jinfu.api.service.ApiBaiduService;
import com.xinyunlian.jinfu.area.dto.SysAreaInfDto;
import com.xinyunlian.jinfu.area.service.SysAreaInfService;
import com.xinyunlian.jinfu.bank.dto.BankCardDto;
import com.xinyunlian.jinfu.bank.service.BankService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.dealer.dto.*;
import com.xinyunlian.jinfu.dealer.enums.EDealerStatus;
import com.xinyunlian.jinfu.dealer.enums.EDealerUserLogType;
import com.xinyunlian.jinfu.dealer.enums.EDealerUserOrderSource;
import com.xinyunlian.jinfu.dealer.service.*;
import com.xinyunlian.jinfu.insurance.dto.PerInsInfoSearchDto;
import com.xinyunlian.jinfu.insurance.dto.PerInsuranceInfoDto;
import com.xinyunlian.jinfu.insurance.enums.EInvokerType;
import com.xinyunlian.jinfu.insurance.enums.EPerInsDealSource;
import com.xinyunlian.jinfu.insurance.enums.EPerInsDealType;
import com.xinyunlian.jinfu.insurance.enums.EPerInsOrderStatus;
import com.xinyunlian.jinfu.insurance.service.InsuranceOrderService;
import com.xinyunlian.jinfu.pingan.dto.PolicyDto;
import com.xinyunlian.jinfu.prod.dto.OrderDto;
import com.xinyunlian.jinfu.prod.dto.ProductDto;
import com.xinyunlian.jinfu.prod.enums.EProd;
import com.xinyunlian.jinfu.prod.service.ProdService;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.store.service.StoreService;
import com.xinyunlian.jinfu.user.dto.DealerUserDto;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.enums.EDealerUserStatus;
import com.xinyunlian.jinfu.user.service.DealerUserService;
import com.xinyunlian.jinfu.user.service.UserService;
import com.xinyunlian.jinfu.yunma.dto.YMMemberDto;
import com.xinyunlian.jinfu.yunma.service.YMMemberService;
import com.xinyunlian.jinfu.zhongan.dto.ZhongAnRequestDto;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by menglei on 2016年09月21日.
 */
@Controller
@RequestMapping(value = "order")
public class OrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private ProdService prodService;
    @Autowired
    private DealerProdService dealerProdService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private UserService userService;
    @Autowired
    private DealerUserOrderService dealerUserOrderService;
    @Autowired
    private InsuranceOrderService insuranceOrderService;
    @Autowired
    private SysAreaInfService sysAreaInfService;
    @Autowired
    private DealerUserLogService dealerUserLogService;
    @Autowired
    private DealerService dealerService;
    @Autowired
    private BankService bankService;
    @Autowired
    private YMMemberService yMMemberService;
    @Autowired
    private DealerUserService dealerUserService;
    @Autowired
    private ApiBaiduService apiBaiduService;

    private String DOMAIN_URL = AppConfigUtil.getConfig("domain.url");

    /**
     * 业务代办(店铺宝，车险，云小钱)
     *
     * @param orderDto
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> save(@RequestBody OrderDto orderDto) {
        DealerUserDto dealerUserDto = (DealerUserDto) SecurityContext.getCurrentUser().getParamMap().get("dealerUserDto");
        //判断店铺是否已实名认证
        StoreInfDto storeInfDto = storeService.findByStoreId(Long.valueOf(orderDto.getStoreId()));
        if (storeInfDto == null) {
            return ResultDtoFactory.toNack("店铺不存在");
        }
        //判断店铺所在的地区是否是业务配置的地区范围内
        Boolean flag = prodService.checkProdArea(orderDto.getProdId(), Long.valueOf(storeInfDto.getDistrictId()), storeInfDto.getIndustryMcc());
        if (!flag) {
            return ResultDtoFactory.toNack("该地区尚未开通此业务");
        }
        UserInfoDto userInfoDto = userService.findUserByUserId(storeInfDto.getUserId());
        if (userInfoDto == null) {
            return ResultDtoFactory.toNack("商户不存在");
        } else if (!userInfoDto.getIdentityAuth()) {
            return ResultDtoFactory.toNack("商户未实名认证");
        }
        ProductDto productDto = prodService.getProdById(orderDto.getProdId());
        if (productDto == null) {
            return ResultDtoFactory.toNack("业务不存在");
        } else if (!productDto.getProdId().equals(EProd.S01001.getCode()) && !productDto.getProdId().equals(EProd.S01002.getCode())
                && !productDto.getProdId().equals(EProd.L01003.getCode()) && !productDto.getProdId().equals(EProd.L01004.getCode())) {
            //暂时只能办理店铺宝和车险
            return ResultDtoFactory.toNack("该地区尚未开通此业务");
        }
        //该店铺可办业务列表
        List<String> districtIdsList = new ArrayList<>();
        if (StringUtils.isNotEmpty(storeInfDto.getProvinceId())) {
            districtIdsList.add(storeInfDto.getProvinceId());
        }
        if (StringUtils.isNotEmpty(storeInfDto.getCityId())) {
            districtIdsList.add(storeInfDto.getCityId());
        }
        if (StringUtils.isNotEmpty(storeInfDto.getAreaId())) {
            districtIdsList.add(storeInfDto.getAreaId());
        }
        if (StringUtils.isNotEmpty(storeInfDto.getStreetId())) {
            districtIdsList.add(storeInfDto.getStreetId());
        }
        DealerProdSearchDto dealerProdSearchDto = new DealerProdSearchDto();
        dealerProdSearchDto.setDealerId(dealerUserDto.getDealerId());
        dealerProdSearchDto.setProdId(orderDto.getProdId());
        dealerProdSearchDto.setDistrictIdsList(districtIdsList);
        dealerProdSearchDto.setExpire(true);
        List<DealerProdDto> dealerProdDtoList = dealerProdService.getDealerProdList(dealerProdSearchDto);
        if (dealerProdDtoList == null || dealerProdDtoList.size() <= 0) {
            return ResultDtoFactory.toNack("该业务不在您的业务范围内");
        }
        //请求办业务
        if (productDto.getProdId().equals(EProd.L01003.getCode())) {//云小钱业务特殊处理
            YMMemberDto yMMemberDto = yMMemberService.getMemberByStoreId(storeInfDto.getStoreId());
            if (yMMemberDto == null || StringUtils.isEmpty(yMMemberDto.getQrcodeUrl())) {
                return ResultDtoFactory.toNack("请先绑定云码");
            }
            List<BankCardDto> bankCardList = bankService.findByUserId(userInfoDto.getUserId());
            if (bankCardList.isEmpty()) {
                return ResultDtoFactory.toNack("请先绑定银行卡");
            }
            StringBuilder sb = new StringBuilder();
            for (BankCardDto bankCardDto : bankCardList) {
                sb.append(",").append(bankCardDto.getBankCardNo() + "-" + bankCardDto.getBankCode());
            }
            String url = DOMAIN_URL + "/xyl-iou/web/dealer/acct/registerPage?" +
                    "guid=" + storeInfDto.getTobaccoCertificateNo() + "&storeName=" + storeInfDto.getStoreName() +
                    "&province=" + storeInfDto.getProvince() + "&city=" + storeInfDto.getCity() +
                    "&district=" + storeInfDto.getArea() + "&street=" + storeInfDto.getStreet() +
                    "&storeAddress=" + storeInfDto.getAddress() + "&name=" + userInfoDto.getUserName() +
                    "&phone=" + userInfoDto.getMobile() + "&idCardNo=" + userInfoDto.getIdCardNo() +
                    "&bankList=" + sb.substring(1);
//            //建立代办业务关系
//            DealerUserOrderDto dealerUserOrderDto = new DealerUserOrderDto();
//            dealerUserOrderDto.setDealerId(dealerUserDto.getDealerId());
//            dealerUserOrderDto.setUserId(SecurityContext.getCurrentUserId());
//            dealerUserOrderDto.setStoreId(storeInfDto.getStoreId() + StringUtils.EMPTY);
//            dealerUserOrderDto.setStoreUserId(storeInfDto.getUserId());
//            dealerUserOrderDto.setProdId(orderDto.getProdId());
//            dealerUserOrderDto.setSource(EDealerUserOrderSource.DEALER);
//            dealerUserOrderService.createDealerUserOrder(dealerUserOrderDto);
            //插入分销员操作日志
            dealerUserLogService.createDealerUserLog(dealerUserDto, orderDto.getLogLng(), orderDto.getLogLat(), orderDto.getLogAddress(),
                    storeInfDto.getUserId(), storeInfDto.getStoreId() + StringUtils.EMPTY, "业务办理:云小钱", EDealerUserLogType.ORDER);
            return ResultDtoFactory.toAck("请求成功", url);
        }
        //生成订单
        PerInsuranceInfoDto perInsuranceInfoDto = new PerInsuranceInfoDto();
        perInsuranceInfoDto.setOperatorName(dealerUserDto.getName());
        perInsuranceInfoDto.setOrderDate(new Date());
        perInsuranceInfoDto.setTobaccoPermiNo(storeInfDto.getTobaccoCertificateNo());
        StringBuffer sb = new StringBuffer();
        List<Long> areaIds = new ArrayList<>();
        if (StringUtils.isNotEmpty(storeInfDto.getProvinceId())) {
            sb.append(",").append(storeInfDto.getProvinceId());
            areaIds.add(Long.valueOf(storeInfDto.getProvinceId()));
        }
        if (StringUtils.isNotEmpty(storeInfDto.getCityId())) {
            sb.append(",").append(storeInfDto.getCityId());
            areaIds.add(Long.valueOf(storeInfDto.getCityId()));
        }
        if (StringUtils.isNotEmpty(storeInfDto.getAreaId())) {
            sb.append(",").append(storeInfDto.getAreaId());
            areaIds.add(Long.valueOf(storeInfDto.getAreaId()));
        }
        if (StringUtils.isNotEmpty(storeInfDto.getStreetId())) {
            sb.append(",").append(storeInfDto.getStreetId());
        }
        if (sb != null) {
            perInsuranceInfoDto.setStoreAreaTreePath(sb.toString() + ",");
        }
        //临时处理
        if (EProd.L01004.getCode().equals(productDto.getProdId())) {
            return ResultDtoFactory.toNack("该地区尚未开通此业务");
        }
        perInsuranceInfoDto.setStoreId(storeInfDto.getStoreId());
        perInsuranceInfoDto.setStoreName(storeInfDto.getStoreName());
        perInsuranceInfoDto.setOrderStatus(EPerInsOrderStatus.INPROCESS);
        perInsuranceInfoDto.setDealType(EPerInsDealType.MANAGERSERVICE);
        perInsuranceInfoDto.setDealSource(EPerInsDealSource.BUDDY);
        perInsuranceInfoDto.setProductId(productDto.getProdId());
        perInsuranceInfoDto.setProductName(productDto.getProdName());
        String orderNo = insuranceOrderService.addInsOrderInfo(perInsuranceInfoDto);
        //生成跳转url
        List<SysAreaInfDto> sysAreaInfDtoList = sysAreaInfService.getSysAreaInfByIds(areaIds);
        SysAreaInfDto province = sysAreaInfDtoList.get(0);
        SysAreaInfDto city = sysAreaInfDtoList.get(1);
        SysAreaInfDto area = sysAreaInfDtoList.get(2);
        //判断代办产品类型
        String url;
        if (EProd.S01001.getCode().equals(productDto.getProdId())) {
            PolicyDto policyDto = new PolicyDto();
            policyDto.setAddress(storeInfDto.getAddress());
            policyDto.setAssuranceOrderNo(orderNo);
            policyDto.setContactName(userInfoDto.getUserName());
            policyDto.setContactMobile(userInfoDto.getMobile());
            policyDto.setStoreName(storeInfDto.getStoreName());
            policyDto.setTobaccoCertificateNo(storeInfDto.getTobaccoCertificateNo());
            if (province != null) {
                policyDto.setProvinceCnName(province.getName());
                policyDto.setProvinceCode(province.getGbCode());
            }
            if (city != null) {
                policyDto.setCityCnName(city.getName());
                policyDto.setCityCode(city.getGbCode());
            }
            if (area != null) {
                policyDto.setCountyCnName(area.getName());
                policyDto.setCountyCode(area.getGbCode());
            }
            url = insuranceOrderService.getPinganUrl(policyDto, EInvokerType.APP);
        } else if (productDto.getProdId().equals(EProd.S01002.getCode())) {
            ZhongAnRequestDto zhongAnRequestDto = new ZhongAnRequestDto();
            zhongAnRequestDto.setUserID(orderNo);//订单号
            url = insuranceOrderService.getZhonganUrl(zhongAnRequestDto, EInvokerType.APP);

            LOGGER.debug("返回的url：" + url);
        } else {
            return ResultDtoFactory.toNack("该地区尚未开通此业务");
        }

        LOGGER.debug("订单号：" + orderNo + "获取的产品：" + productDto.getProdName() + ";" + productDto.getProdId());

        //建立代办业务关系
        DealerUserOrderDto dealerUserOrderDto = new DealerUserOrderDto();
        dealerUserOrderDto.setDealerId(dealerUserDto.getDealerId());
        dealerUserOrderDto.setUserId(SecurityContext.getCurrentUserId());
        dealerUserOrderDto.setStoreId(storeInfDto.getStoreId() + StringUtils.EMPTY);
        dealerUserOrderDto.setStoreUserId(storeInfDto.getUserId());
        dealerUserOrderDto.setProdId(orderDto.getProdId());
        dealerUserOrderDto.setOrderNo(orderNo);
        dealerUserOrderDto.setSource(EDealerUserOrderSource.DEALER);
        dealerUserOrderService.createDealerUserOrder(dealerUserOrderDto);
        //插入分销员操作日志
        dealerUserLogService.createDealerUserLog(dealerUserDto, orderDto.getLogLng(), orderDto.getLogLat(), orderDto.getLogAddress(),
                storeInfDto.getUserId(), storeInfDto.getStoreId() + StringUtils.EMPTY, "业务办理:orderNo=" + orderNo, EDealerUserLogType.ORDER);

        return ResultDtoFactory.toAck("请求成功", url);
    }

    /**
     * 业务代办(首页)
     *
     * @param orderDto
     * @return
     */
    @RequestMapping(value = "/saveProd", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("业务代办(首页)")
    public ResultDto<Object> saveProd(@RequestBody OrderDto orderDto) {
        DealerUserDto dealerUserDto = (DealerUserDto) SecurityContext.getCurrentUser().getParamMap().get("dealerUserDto");
        //判断店铺是否已实名认证
        StoreInfDto storeInfDto = storeService.findByStoreId(100869L);////默认店铺id100869
        if (storeInfDto == null) {
            return ResultDtoFactory.toNack("店铺不存在");
        }
        //判断店铺所在的地区是否是业务配置的地区范围内
        Boolean flag = prodService.checkProdArea(orderDto.getProdId(), Long.valueOf(storeInfDto.getDistrictId()), storeInfDto.getIndustryMcc());
        if (!flag) {
            return ResultDtoFactory.toNack("该地区尚未开通此业务");
        }
        ProductDto productDto = prodService.getProdById(orderDto.getProdId());
        if (productDto == null) {
            return ResultDtoFactory.toNack("业务不存在");
        } else if (!productDto.getProdId().equals(orderDto.getProdId())) {
            //暂时只能办理保骉车险
            return ResultDtoFactory.toNack("该地区尚未开通此业务");
        }
        //该店铺可办业务列表
        List<String> districtIdsList = new ArrayList<>();
        if (StringUtils.isEmpty(orderDto.getLogLng()) || StringUtils.isEmpty(orderDto.getLogLat())) {//没坐标不显示
            return ResultDtoFactory.toNack("坐标必传，请打开定位");
        }
        ApiBaiduDto apiBaiduDto = apiBaiduService.getArea(orderDto.getLogLat() + "," + orderDto.getLogLng());
        if (apiBaiduDto == null) {
            return ResultDtoFactory.toNack("该地区暂不支持:" + orderDto.getLogLat() + "," + orderDto.getLogLng());
        }
        //安卓bug临时解决办法
        if ("0".equals(apiBaiduDto.getGbCode())) {
            apiBaiduDto = apiBaiduService.getArea(orderDto.getLogLng() + "," + orderDto.getLogLat());
            if (apiBaiduDto == null) {
                return ResultDtoFactory.toNack("该地区暂不支持:" + orderDto.getLogLat() + "," + orderDto.getLogLng());
            }
        }
        SysAreaInfDto sysAreaInfDto = sysAreaInfService.getSysAreaByGbCode(apiBaiduDto.getGbCode());
        if (sysAreaInfDto == null) {
            return ResultDtoFactory.toNack("该地区暂不支持:" + apiBaiduDto.getGbCode());
        }
        List<String> areaList = Arrays.asList(sysAreaInfDto.getTreePath().split(","));
        districtIdsList.add(String.valueOf(sysAreaInfDto.getId()));
        for (String areaId : areaList) {
            if (StringUtils.isNotEmpty(areaId)) {
                districtIdsList.add(areaId);
            }
        }
//        if (StringUtils.isNotEmpty(storeInfDto.getProvinceId())) {
//            districtIdsList.add(storeInfDto.getProvinceId());
//        }
//        if (StringUtils.isNotEmpty(storeInfDto.getCityId())) {
//            districtIdsList.add(storeInfDto.getCityId());
//        }
//        if (StringUtils.isNotEmpty(storeInfDto.getAreaId())) {
//            districtIdsList.add(storeInfDto.getAreaId());
//        }
//        if (StringUtils.isNotEmpty(storeInfDto.getStreetId())) {
//            districtIdsList.add(storeInfDto.getStreetId());
//        }
        DealerProdSearchDto dealerProdSearchDto = new DealerProdSearchDto();
        dealerProdSearchDto.setDealerId(dealerUserDto.getDealerId());
        dealerProdSearchDto.setProdId(orderDto.getProdId());
        dealerProdSearchDto.setDistrictIdsList(districtIdsList);
        dealerProdSearchDto.setExpire(true);
        List<DealerProdDto> dealerProdDtoList = dealerProdService.getDealerProdList(dealerProdSearchDto);
        if (dealerProdDtoList == null || dealerProdDtoList.size() <= 0) {
            return ResultDtoFactory.toNack("该业务不在您的业务范围内");
        }
        //请求办业务
        //生成订单
        PerInsuranceInfoDto perInsuranceInfoDto = new PerInsuranceInfoDto();
        perInsuranceInfoDto.setOperatorName(dealerUserDto.getName());
        perInsuranceInfoDto.setOrderDate(new Date());
        perInsuranceInfoDto.setTobaccoPermiNo(storeInfDto.getTobaccoCertificateNo());
        StringBuffer sb = new StringBuffer();
        List<Long> areaIds = new ArrayList<>();
        if (StringUtils.isNotEmpty(storeInfDto.getProvinceId())) {
            sb.append(",").append(storeInfDto.getProvinceId());
            areaIds.add(Long.valueOf(storeInfDto.getProvinceId()));
        }
        if (StringUtils.isNotEmpty(storeInfDto.getCityId())) {
            sb.append(",").append(storeInfDto.getCityId());
            areaIds.add(Long.valueOf(storeInfDto.getCityId()));
        }
        if (StringUtils.isNotEmpty(storeInfDto.getAreaId())) {
            sb.append(",").append(storeInfDto.getAreaId());
            areaIds.add(Long.valueOf(storeInfDto.getAreaId()));
        }
        if (StringUtils.isNotEmpty(storeInfDto.getStreetId())) {
            sb.append(",").append(storeInfDto.getStreetId());
        }
        if (sb != null) {
            perInsuranceInfoDto.setStoreAreaTreePath(sb.toString() + ",");
        }
        //临时处理
        if (EProd.L01004.getCode().equals(productDto.getProdId())) {
            return ResultDtoFactory.toNack("该地区尚未开通此业务");
        }
        perInsuranceInfoDto.setStoreId(storeInfDto.getStoreId());
        perInsuranceInfoDto.setStoreName(storeInfDto.getStoreName());
        perInsuranceInfoDto.setOrderStatus(EPerInsOrderStatus.INPROCESS);
        perInsuranceInfoDto.setDealType(EPerInsDealType.MANAGERSERVICE);
        perInsuranceInfoDto.setDealSource(EPerInsDealSource.BUDDY);
        perInsuranceInfoDto.setProductId(productDto.getProdId());
        perInsuranceInfoDto.setProductName(productDto.getProdName());
        String orderNo = insuranceOrderService.addInsOrderInfo(perInsuranceInfoDto);
        //判断代办产品类型
        String url = StringUtils.EMPTY;
        if (EProd.S01002.getCode().equals(productDto.getProdId())) {
            ZhongAnRequestDto zhongAnRequestDto = new ZhongAnRequestDto();
            zhongAnRequestDto.setUserID(orderNo);//订单号
            url = insuranceOrderService.getZhonganUrl(zhongAnRequestDto, EInvokerType.APP);
            LOGGER.debug("返回的url：" + url);
        } else {
            return ResultDtoFactory.toNack("该地区尚未开通此业务");
        }
        LOGGER.debug("订单号：" + orderNo + "获取的产品：" + productDto.getProdName() + ";" + productDto.getProdId());
        //建立代办业务关系
        DealerUserOrderDto dealerUserOrderDto = new DealerUserOrderDto();
        dealerUserOrderDto.setDealerId(dealerUserDto.getDealerId());
        dealerUserOrderDto.setUserId(SecurityContext.getCurrentUserId());
        dealerUserOrderDto.setStoreId(storeInfDto.getStoreId() + StringUtils.EMPTY);
        dealerUserOrderDto.setStoreUserId(storeInfDto.getUserId());
        dealerUserOrderDto.setProdId(orderDto.getProdId());
        dealerUserOrderDto.setOrderNo(orderNo);
        dealerUserOrderDto.setSource(EDealerUserOrderSource.DEALER);
        dealerUserOrderService.createDealerUserOrder(dealerUserOrderDto);
        //插入分销员操作日志
        dealerUserLogService.createDealerUserLog(dealerUserDto, orderDto.getLogLng(), orderDto.getLogLat(), orderDto.getLogAddress(),
                storeInfDto.getUserId(), storeInfDto.getStoreId() + StringUtils.EMPTY, "业务办理:orderNo=" + orderNo, EDealerUserLogType.ORDER);
        return ResultDtoFactory.toAck("请求成功", url);
    }

    /**
     * 根据店铺id查订单列表(店铺宝，车险)
     *
     * @param orderDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listByStoreId", method = RequestMethod.GET)
    public ResultDto<List<PerInsuranceInfoDto>> getOrderList(OrderDto orderDto) {
        PerInsInfoSearchDto searchDto = new PerInsInfoSearchDto();
        searchDto.setProductId(orderDto.getProdId());
        searchDto.setStoreId(Long.parseLong(orderDto.getStoreId()));

        List<PerInsuranceInfoDto> list = insuranceOrderService.getInsOrder(searchDto);
        return ResultDtoFactory.toAck("获取成功", list);
    }

}
