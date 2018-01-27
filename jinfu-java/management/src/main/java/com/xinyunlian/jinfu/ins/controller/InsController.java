package com.xinyunlian.jinfu.ins.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.common.util.excel.DataTablesExcelService;
import com.xinyunlian.jinfu.ins.dto.PerInsInfoExtSearchDto;
import com.xinyunlian.jinfu.insurance.dto.PerInsInfoSearchDto;
import com.xinyunlian.jinfu.insurance.dto.PerInsuranceInfoDto;
import com.xinyunlian.jinfu.insurance.dto.PerInsuranceInfoPageDto;
import com.xinyunlian.jinfu.insurance.service.InsuranceOrderService;
import com.xinyunlian.jinfu.insurance.service.VehicleInsDetailService;
import com.xinyunlian.jinfu.report.dealer.dto.InsuranceDto;
import com.xinyunlian.jinfu.report.dealer.dto.InsuranceSearchDto;
import com.xinyunlian.jinfu.report.dealer.enums.EInsuranceSource;
import com.xinyunlian.jinfu.report.dealer.enums.EInsuranceStatus;
import com.xinyunlian.jinfu.report.dealer.service.DealerReportService;
import com.xinyunlian.jinfu.zhongan.dto.VehicleInsDetailDto;

/**
 * Created by DongFC on 2016-11-04.
 */
@RestController
@RequestMapping("ins")
public class InsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InsController.class);

    @Autowired
    private InsuranceOrderService insuranceOrderService;
    @Autowired
    private DealerReportService dealerReportService;
    @Autowired
    private VehicleInsDetailService vehicleInsDetailService;

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public ModelAndView exportInsOrder(PerInsInfoExtSearchDto extSearchDto){
        InsuranceSearchDto searchDto = ConverterService.convert(extSearchDto, InsuranceSearchDto.class);
        if(Objects.nonNull(extSearchDto.getPerInsOrderStatus())) {
            searchDto.setPerInsOrderStatus(EnumHelper.translate(EInsuranceStatus.class, extSearchDto.getPerInsOrderStatus().getCode()));
        }
        if(Objects.nonNull(extSearchDto.getPerInsDealSource())){
            searchDto.setPerInsDealSource(EnumHelper.translate(EInsuranceSource.class, extSearchDto.getPerInsDealSource().getCode()));
        }

        StringBuilder builder = new StringBuilder();
        if (!StringUtils.isEmpty(extSearchDto.getProvinceId())){
            builder.append(",").append(extSearchDto.getProvinceId());
            if (!StringUtils.isEmpty(extSearchDto.getCityId())){
                builder.append(",").append(extSearchDto.getCityId());
                if (!StringUtils.isEmpty(extSearchDto.getCountyId())){
                    builder.append(",").append(extSearchDto.getCountyId());
                }
            }
            builder.append(",");
            searchDto.setStoreAreaTreePath(builder.toString());
        }

        List<InsuranceDto> data = dealerReportService.getInsuranceReport(searchDto);

        Map<String, Object> model = new HashedMap();
        model.put("data", data);
        model.put("fileName","保险记录.xls");
        model.put("tempPath", "/templates/保险记录.xls");
        DataTablesExcelService dataTablesExcelService = new DataTablesExcelService();
        return new ModelAndView(dataTablesExcelService, model);
    }

    /**
     * 分页查询保单列表
     * @param extSearchDto
     * @return
     */
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public ResultDto<Object> getInsOrderPage(@RequestBody PerInsInfoExtSearchDto extSearchDto){

        PerInsInfoSearchDto searchDto = ConverterService.convert(extSearchDto, PerInsInfoSearchDto.class);

        if (extSearchDto.getOrderDateFrom() != null){
            Date dateFrom = DateHelper.getStartDate(extSearchDto.getOrderDateFrom());
            searchDto.setOrderDateFrom(dateFrom);
        }
        if(extSearchDto.getOrderDateTo() != null){
            Date dateTo = DateHelper.getEndDate(extSearchDto.getOrderDateTo());
            searchDto.setOrderDateTo(dateTo);
        }

        StringBuilder builder = new StringBuilder();
        if (!StringUtils.isEmpty(searchDto.getProvinceId())){
            builder.append(",").append(searchDto.getProvinceId());
            if (!StringUtils.isEmpty(searchDto.getCityId())){
                builder.append(",").append(searchDto.getCityId());
                if (!StringUtils.isEmpty(searchDto.getCountyId())){
                    builder.append(",").append(searchDto.getCountyId());
                }
            }
            builder.append(",");
            searchDto.setStoreAreaTreePath(builder.toString());
        }

        PerInsuranceInfoPageDto pageDto = insuranceOrderService.getInsOrderPage(searchDto);

        return ResultDtoFactory.toAck("查询成功", pageDto);
    }

    /**
     * 检查保单pdf是否存在
     * @param orderId
     * @return
     */
    @RequestMapping(value="checkInsureDetail", method = RequestMethod.GET)
    public ResultDto<Object> checkInsureDetail(String orderId){

        PerInsuranceInfoDto insuranceInfoDto = insuranceOrderService.getInsOrderByOrderId(orderId);
        if (insuranceInfoDto != null){
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
                    return ResultDtoFactory.toNack("保单文件没有生成，请稍后再试");
                } else {
                    return ResultDtoFactory.toAck("true");
                }
            } catch (Exception e) {
                return ResultDtoFactory.toNack("获取保单信息失败");
            }
        }else {
            return ResultDtoFactory.toNack("获取保单信息失败");
        }

    }

    /**
     * 查看保单详情pdf
     * @param orderId
     * @return
     */
    @RequestMapping(value="insureDetail", method = RequestMethod.GET, produces="application/pdf")
    public byte[] insureDetail(String orderId){
        PerInsuranceInfoDto insuranceInfoDto = insuranceOrderService.getInsOrderByOrderId(orderId);
        if (insuranceInfoDto != null){
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
        }else {
            throw new BizServiceException(EErrorCode.INS_ORDER_NOT_EXIST, "获取保单信息失败");
        }
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

}
