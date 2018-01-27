package com.xinyunlian.jinfu.insurance.controller;

import com.xinyunlian.jinfu.area.dto.SysAreaInfDto;
import com.xinyunlian.jinfu.area.service.SysAreaInfService;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.industry.dto.IndustryDto;
import com.xinyunlian.jinfu.industry.service.IndustryService;
import com.xinyunlian.jinfu.insurance.dto.*;
import com.xinyunlian.jinfu.insurance.enums.EInvokerType;
import com.xinyunlian.jinfu.insurance.enums.EPerInsDealSource;
import com.xinyunlian.jinfu.insurance.enums.EPerInsDealType;
import com.xinyunlian.jinfu.insurance.enums.EPerInsOrderStatus;
import com.xinyunlian.jinfu.insurance.service.InsuranceOrderService;
import com.xinyunlian.jinfu.insurance.service.VehicleInsDetailService;
import com.xinyunlian.jinfu.pingan.dto.PolicyDto;
import com.xinyunlian.jinfu.prod.dto.ProductDto;
import com.xinyunlian.jinfu.prod.enums.EProd;
import com.xinyunlian.jinfu.prod.service.ProdService;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.store.service.StoreService;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.service.UserService;
import com.xinyunlian.jinfu.zhongan.dto.VehicleInsDetailDto;
import com.xinyunlian.jinfu.zhongan.dto.ZhongAnRequestDto;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;

/**
 * Created by jll on 2016/10/9.
 */
@RestController
@RequestMapping(value = "ins")
public class InsuranceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InsuranceController.class);

    @Autowired
    private ProdService prodService;
    @Autowired
    private UserService userService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private InsuranceOrderService insuranceOrderService;
    @Autowired
    private SysAreaInfService sysAreaInfService;
    @Autowired
    private VehicleInsDetailService vehicleInsDetailService;
    @Autowired
    private IndustryService industryService;

    /**
     * 购买保险产品前进行验证
     * @return
     */
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public ResultDto<Object> check() {
        if(SecurityContext.getCurrentUserId() == null){
            return ResultDtoFactory.toNack("用户未登入");
        }
        //判断店铺是否已实名认证
        UserInfoDto userInfoDto = userService.findUserByUserId(SecurityContext.getCurrentUserId());
        if(userInfoDto.getIdentityAuth() == false){
            return ResultDtoFactory.toNack("用户未实名认证");
        }

        if (userInfoDto.getStoreAuth() == false) {
            return ResultDtoFactory.toNack("未绑定店铺");
        }

        List<StoreInfDto> storeInfDtos = storeService.findByUserId(SecurityContext.getCurrentUserId());
        return ResultDtoFactory.toAck("成功", storeInfDtos);

    }

    /**
     * WEB端请求下单
     * @param orderDto
     * @return
     */
    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public ResultDto<Object> order(@RequestBody OrderDto orderDto) {
        return getOrderUrl(orderDto, EInvokerType.WEB);
    }

    /**
     * 移动端请求下单
     * @param orderDto
     * @return
     */
    @RequestMapping(value = "/apporder", method = RequestMethod.POST)
    public ResultDto<Object> apporder(@RequestBody OrderDto orderDto) {
        return getOrderUrl(orderDto, EInvokerType.APP);
    }

    private boolean isValid(Object obj){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set constraintViolations = validator.validate(obj);
        if (!constraintViolations.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * 分页查询保单列表
     * @param searchDto
     * @return
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ResultDto<Object> getInsOrderPage(PerInsInfoSearchDto searchDto){
        List<StoreInfDto> storeInfDtos = storeService.findByUserId(SecurityContext.getCurrentUserId());

        PerInsuranceInfoExtPageDto extPageDto = new PerInsuranceInfoExtPageDto();
        if (!CollectionUtils.isEmpty(storeInfDtos)){
            List<Long> storeIdList = new ArrayList<>();
            storeInfDtos.forEach(dto -> {
                storeIdList.add(dto.getStoreId());
            });

            searchDto.setStoreIdList(storeIdList);
            PerInsuranceInfoPageDto pageDto = insuranceOrderService.getInsOrderPage(searchDto);

            //处理订单所属的产品类型
            List<PerInsuranceInfoExtDto> extDtoList = new ArrayList<>();
            if (!CollectionUtils.isEmpty(pageDto.getList())){
                List<PerInsuranceInfoDto> data = pageDto.getList();
                List<ProductDto> prodList = prodService.getProdList(null);
                Map<String, ProductDto> map = new HashMap<>();
                prodList.forEach(prod -> {
                    map.put(prod.getProdId(), prod);
                });

                data.forEach(item -> {
                    PerInsuranceInfoExtDto extDto = ConverterService.convert(item, PerInsuranceInfoExtDto.class);
                    ProductDto prod = map.get(extDto.getProductId());
                    if (prod.getProdTypeInfDtoLv3() != null){
                        extDto.setProdTypeName(prod.getProdTypeInfDtoLv3().getProdTypeName());
                    }else if (prod.getProdTypeInfDtoLv2() != null){
                        extDto.setProdTypeName(prod.getProdTypeInfDtoLv2().getProdTypeName());
                    }else {
                        extDto.setProdTypeName(prod.getProdTypeInfDtoLv1().getProdTypeName());
                    }
                    extDtoList.add(extDto);
                });
            }

            extPageDto = ConverterService.convert(pageDto, PerInsuranceInfoExtPageDto.class);
            extPageDto.setList(extDtoList);

        }else {
            extPageDto.setTotalPages(0);
            extPageDto.setTotalRecord(0l);
            extPageDto.setPageSize(searchDto.getPageSize());
            extPageDto.setCurrentPage(searchDto.getCurrentPage());
        }

        return ResultDtoFactory.toAck("查询成功", extPageDto);
    }

    /**
     * 检查保单pdf是否存在及用户是否有权限查看
     * @param orderId
     * @return
     */
    @RequestMapping(value="checkInsureDetail", method = RequestMethod.GET)
    public ResultDto<Object> checkInsureDetail(String orderId){

        List<StoreInfDto> storeInfDtos = storeService.findByUserId(SecurityContext.getCurrentUserId());
        if (!CollectionUtils.isEmpty(storeInfDtos)){
            Set<String> tpnSet = new HashSet<>();
            storeInfDtos.forEach(item -> {
                tpnSet.add(item.getTobaccoCertificateNo());
            });

            PerInsuranceInfoDto insuranceInfoDto = insuranceOrderService.getInsOrderByOrderId(orderId);
            if (insuranceInfoDto != null && tpnSet.contains(insuranceInfoDto.getTobaccoPermiNo())){
                try {
                    HttpHeaders headers = new HttpHeaders();
                    List list = new ArrayList<>();
                    list.add(MediaType.valueOf("application/pdf"));
                    headers.setAccept(list);

                    RestTemplate restTemplate = new RestTemplate();
                    ResponseEntity<byte[]> responseEntity = restTemplate.exchange(insuranceInfoDto.getOrderUrl(), HttpMethod.GET, new HttpEntity<byte[]>(headers), byte[].class);

                    HttpStatus httpStatus = responseEntity.getStatusCode();
                    if (httpStatus != HttpStatus.OK) {
                        LOGGER.info("http get 请求失败： " + httpStatus);
                        return ResultDtoFactory.toAck("保单文件没有生成，请稍后再试");
                    } else {
                        return ResultDtoFactory.toAck("true");
                    }
                } catch (Exception e) {
                    return ResultDtoFactory.toNack("获取保单信息失败");
                }
            }
        }

        return ResultDtoFactory.toNack("您没有查看该保单的权限");

    }

    /**
     * 查看保单详情pdf
     * @param orderId
     * @return
     */
    @RequestMapping(value="insureDetail", method = RequestMethod.GET, produces="application/pdf")
    public byte[] insureDetail(String orderId){

        List<StoreInfDto> storeInfDtos = storeService.findByUserId(SecurityContext.getCurrentUserId());
        if (!CollectionUtils.isEmpty(storeInfDtos)){
            Set<String> tpnSet = new HashSet<>();
            storeInfDtos.forEach(item -> {
                tpnSet.add(item.getTobaccoCertificateNo());
            });

            PerInsuranceInfoDto insuranceInfoDto = insuranceOrderService.getInsOrderByOrderId(orderId);
            if (insuranceInfoDto != null && tpnSet.contains(insuranceInfoDto.getTobaccoPermiNo())){
                try {
                    HttpHeaders headers = new HttpHeaders();
                    List list = new ArrayList<>();
                    list.add(MediaType.valueOf("application/pdf"));
                    headers.setAccept(list);

                    RestTemplate restTemplate = new RestTemplate();
                    ResponseEntity<byte[]> responseEntity = restTemplate.exchange(insuranceInfoDto.getOrderUrl(), HttpMethod.GET, new HttpEntity<byte[]>(headers), byte[].class);

                    byte[] responseBody = responseEntity.getBody();
                    HttpStatus httpStatus = responseEntity.getStatusCode();
                    if (httpStatus != HttpStatus.OK) {
                        LOGGER.info("http get 请求失败： " + httpStatus);
                        throw new BizServiceException(EErrorCode.INS_PDF_NOT_EXIST, "保单文件没有生成，请稍后再试");
                    } else {
                        return responseBody;
                    }
                } catch (Exception e) {
                    throw new BizServiceException(EErrorCode.INS_ORDER_NOT_EXIST, "获取保单信息失败");
                }
            }
        }

        throw new BizServiceException(EErrorCode.INS_AUTHORITY_NOT_ENOUGH, "您没有查看该保单的权限");

    }

    /**
     * 查看车险详情
     * @param orderId
     * @return
     */
    @RequestMapping(value="insureVehDetail", method = RequestMethod.GET)
    public ResultDto<Object> insureVehDetail(String orderId){
        VehicleInsDetailDto dto = vehicleInsDetailService.getVehicleInsDetailByOrderId(orderId);
        return ResultDtoFactory.toAck("查询成功", dto);
    }

    private ResultDto getOrderUrl(OrderDto orderDto, EInvokerType invokerType){
        StoreInfDto storeInfDto = storeService.findByStoreId(orderDto.getStoreId());
        StoreDto storeDto = ConverterService.convert(storeInfDto,StoreDto.class);
        if(!isValid(storeDto)){
            return ResultDtoFactory.toNack("店铺信息未完善");
        }

        IndustryDto industryDto = industryService.getByMcc(storeDto.getIndustryMcc());

        if(industryDto.getStoreLicence() && StringUtils.isEmpty(storeDto.getLicence())) {
            return ResultDtoFactory.toNack("店铺信息未完善");
        }

        //判断店铺所在的地区是否是业务配置的地区范围内
        Boolean flag = prodService.checkProdArea(orderDto.getProdId(), Long.valueOf(storeInfDto.getDistrictId()), industryDto.getMcc());
        if (!flag) {
            return ResultDtoFactory.toNack("该地区尚未开通此业务");
        }

        ProductDto productDto = prodService.getProdById(orderDto.getProdId());
        if (productDto == null) {
            return ResultDtoFactory.toNack("业务不存在");
        }

        //请求办业务
        //生成订单
        UserInfoDto userInfoDto = userService.findUserByUserId(SecurityContext.getCurrentUserId());
        if(userInfoDto == null){
            return ResultDtoFactory.toNack("用户不存在");
        }
        PerInsuranceInfoDto perInsuranceInfoDto = new PerInsuranceInfoDto();
        perInsuranceInfoDto.setOperatorName(userInfoDto.getUserName());
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
        perInsuranceInfoDto.setStoreId(storeInfDto.getStoreId());
        perInsuranceInfoDto.setStoreName(storeInfDto.getStoreName());
        perInsuranceInfoDto.setOrderStatus(EPerInsOrderStatus.INPROCESS);
        perInsuranceInfoDto.setDealType(EPerInsDealType.SELFSERVICE);
        perInsuranceInfoDto.setDealSource(EPerInsDealSource.MALL);
        perInsuranceInfoDto.setProductId(productDto.getProdId());
        perInsuranceInfoDto.setProductName(productDto.getProdName());
        String orderNo = insuranceOrderService.addInsOrderInfo(perInsuranceInfoDto);

        //生成跳转url
        List<SysAreaInfDto> sysAreaInfDtoList = sysAreaInfService.getSysAreaInfByIds(areaIds);
        SysAreaInfDto province = sysAreaInfDtoList.get(0);
        SysAreaInfDto city = sysAreaInfDtoList.get(1);
        SysAreaInfDto area = sysAreaInfDtoList.get(2);
        //判断代办产品类型
        String url = StringUtils.EMPTY;
        if (productDto.getProdId().equals(EProd.S01001.getCode())) {
            PolicyDto policyDto = new PolicyDto();
            policyDto.setAddress(storeInfDto.getAddress());
            policyDto.setAssuranceOrderNo(orderNo);
            policyDto.setContactMobile(userInfoDto.getMobile());
            policyDto.setStoreName(storeInfDto.getStoreName());
            policyDto.setTobaccoCertificateNo(storeInfDto.getTobaccoCertificateNo());
            policyDto.setContactName(userInfoDto.getUserName());

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
            url = insuranceOrderService.getPinganUrl(policyDto, invokerType);
        } else if (productDto.getProdId().equals(EProd.S01002.getCode())) {
            ZhongAnRequestDto zhongAnRequestDto = new ZhongAnRequestDto();
            zhongAnRequestDto.setUserID(orderNo);//订单号
            url = insuranceOrderService.getZhonganUrl(zhongAnRequestDto, invokerType);
        }

        LOGGER.debug("订单号：" + orderNo + "；获取的产品：" + productDto.getProdName() + ";" + productDto.getProdId());

        return ResultDtoFactory.toAck("请求成功", url);
    }

    /**
     * 查看保单详情pdf
     * @param orderId
     * @return
     */
    @RequestMapping(value="insureDetailUrl", method = RequestMethod.GET)
    public ResultDto insureDetailUrl(String orderId){

        List<StoreInfDto> storeInfDtos = storeService.findByUserId(SecurityContext.getCurrentUserId());
        if (!CollectionUtils.isEmpty(storeInfDtos)){
            Set<String> tpnSet = new HashSet<>();
            storeInfDtos.forEach(item -> {
                tpnSet.add(item.getTobaccoCertificateNo());
            });

            PerInsuranceInfoDto insuranceInfoDto = insuranceOrderService.getInsOrderByOrderId(orderId);
            if (insuranceInfoDto != null && tpnSet.contains(insuranceInfoDto.getTobaccoPermiNo())){
                if (StringUtils.isNotEmpty(insuranceInfoDto.getOrderUrl())){
                    Map<String, String> data = new HashMap<>();
                    data.put("orderUrl", insuranceInfoDto.getOrderUrl());
                    return ResultDtoFactory.toAck("获取成功", data);
                }else {
                    LOGGER.info("保单不存在，orderId = " + orderId);
                    throw new BizServiceException(EErrorCode.INS_PDF_NOT_EXIST, "保单文件没有生成，请稍后再试");
                }
            }
        }

        throw new BizServiceException(EErrorCode.INS_AUTHORITY_NOT_ENOUGH, "您没有查看该保单的权限");

    }

}
