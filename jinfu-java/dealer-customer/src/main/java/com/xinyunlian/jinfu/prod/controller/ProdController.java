package com.xinyunlian.jinfu.prod.controller;

import com.xinyunlian.jinfu.api.dto.ApiBaiduDto;
import com.xinyunlian.jinfu.api.service.ApiBaiduService;
import com.xinyunlian.jinfu.area.dto.SysAreaInfDto;
import com.xinyunlian.jinfu.area.service.SysAreaInfService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.dealer.dto.DealerProdDto;
import com.xinyunlian.jinfu.dealer.dto.DealerProdSearchDto;
import com.xinyunlian.jinfu.dealer.dto.SignInfoDto;
import com.xinyunlian.jinfu.dealer.service.DealerProdService;
import com.xinyunlian.jinfu.dealer.service.DealerService;
import com.xinyunlian.jinfu.dealer.service.SignInfoService;
import com.xinyunlian.jinfu.prod.dto.ProductDto;
import com.xinyunlian.jinfu.prod.enums.EProd;
import com.xinyunlian.jinfu.prod.enums.EShelfPlatform;
import com.xinyunlian.jinfu.prod.service.ProdService;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.store.service.StoreService;
import com.xinyunlian.jinfu.user.dto.DealerUserDto;
import com.xinyunlian.jinfu.user.dto.HomeInfoDto;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by menglei on 2016年09月21日.
 */
@Controller
@RequestMapping(value = "product")
public class ProdController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProdController.class);

    @Autowired
    private ProdService prodService;
    @Autowired
    private DealerProdService dealerProdService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private DealerService dealerService;
    @Autowired
    private SignInfoService signInfoService;
    @Autowired
    private ApiBaiduService apiBaiduService;
    @Autowired
    private SysAreaInfService sysAreaInfService;

    /**
     * 可办业务列表
     *
     * @param storeId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultDto<List<ProductDto>> getProdList(@RequestParam String storeId) {
        //是否已签到
//        SignInfoDto signInfoDto = signInfoService.getByUserIdAndStoreId(SecurityContext.getCurrentUserId(), Long.valueOf(storeId));
//        if (signInfoDto == null) {
//            return ResultDtoFactory.toNack("该店铺未签到");
//        }
        List<ProductDto> prodList = new ArrayList<>();

        StoreInfDto storeInfDto = storeService.findByStoreId(Long.valueOf(storeId));
        if (storeInfDto == null) {
            return ResultDtoFactory.toNack("店铺不存在");
        }
        DealerUserDto dealerUserDto = (DealerUserDto) SecurityContext.getCurrentUser().getParamMap().get("dealerUserDto");
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
        dealerProdSearchDto.setDistrictIdsList(districtIdsList);
        dealerProdSearchDto.setExpire(true);
        List<DealerProdDto> dealerProdDtoList = dealerProdService.getDealerProdList(dealerProdSearchDto);
        if (dealerProdDtoList == null || dealerProdDtoList.size() <= 0) {
            return ResultDtoFactory.toAck("获取成功", prodList);
        }

        List<String> prodIdsList = new ArrayList<>();
        for (DealerProdDto dealerProdDto : dealerProdDtoList) {
            prodIdsList.add(dealerProdDto.getProdId());
        }
        //新产品列表
        prodList = prodService.getProdListByIdPlatformInd(prodIdsList, EShelfPlatform.BUDDY, storeInfDto.getIndustryMcc());

        //把云联云码排序放最前面
        List<ProductDto> resultList = new ArrayList<>();
        List<ProductDto> _resultList = new ArrayList<>();
        for (ProductDto productDto : prodList) {
            if (productDto.getProdId().equals(EProd.P01001.getCode())) {
                resultList.add(productDto);
            } else {
                _resultList.add(productDto);
            }
        }
        if (!CollectionUtils.isEmpty(_resultList)) {
            resultList.addAll(_resultList);
        }
        return ResultDtoFactory.toAck("获取成功", resultList);
    }

    /**
     * 其他业务列表
     *
     * @return
     */
    @RequestMapping(value = "/otherList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("其他业务列表")
    public ResultDto<Object> getOtherList(HomeInfoDto homeInfoDto) {
        DealerUserDto dealerUserDto = (DealerUserDto) SecurityContext.getCurrentUser().getParamMap().get("dealerUserDto");

        List<ProductDto> prodList = new ArrayList<>();//首页业务代办
        ProductDto productDto;
        //保骉车险显示
        if (StringUtils.isNotEmpty(homeInfoDto.getLogLng()) && StringUtils.isNotEmpty(homeInfoDto.getLogLat())) {//没坐标不显示
            ApiBaiduDto apiBaiduDto = apiBaiduService.getArea(homeInfoDto.getLogLat() + "," + homeInfoDto.getLogLng());
            SysAreaInfDto sysAreaInfDto = new SysAreaInfDto();
            if (apiBaiduDto != null) {
                sysAreaInfDto = sysAreaInfService.getSysAreaByGbCode(apiBaiduDto.getGbCode());
                if (sysAreaInfDto == null) {
                    LOGGER.info("baidu gbcode error:" + JsonUtil.toJson(apiBaiduDto));
                }
            }
            if (sysAreaInfDto == null) {
                return ResultDtoFactory.toAck("获取成功", prodList);
            }
            StoreInfDto storeInfDto = storeService.findByStoreId(100869L);//默认店铺id100869
            if (storeInfDto == null) {
                return ResultDtoFactory.toAck("获取成功", prodList);
            }
            List<String> districtIdsList = new ArrayList<>();
            List<String> areaList = Arrays.asList(sysAreaInfDto.getTreePath().split(","));
            districtIdsList.add(String.valueOf(sysAreaInfDto.getId()));
            for (String areaId : areaList) {
                if (StringUtils.isNotEmpty(areaId)) {
                    districtIdsList.add(areaId);
                }
            }
            DealerProdSearchDto dealerProdSearchDto = new DealerProdSearchDto();
            dealerProdSearchDto.setDealerId(dealerUserDto.getDealerId());
            dealerProdSearchDto.setDistrictIdsList(districtIdsList);
            dealerProdSearchDto.setExpire(true);
            List<DealerProdDto> dealerProdDtoList = dealerProdService.getDealerProdList(dealerProdSearchDto);
            List<String> prodIdsList = new ArrayList<>();
            for (DealerProdDto dealerProdDto : dealerProdDtoList) {
                prodIdsList.add(dealerProdDto.getProdId());
            }
            if (CollectionUtils.isEmpty(dealerProdDtoList)) {
                return ResultDtoFactory.toAck("获取成功", prodList);
            }
            //支持产品列表
            List<ProductDto> allProdList = prodService.getProdListByIdPlatformInd(prodIdsList, EShelfPlatform.BUDDY, storeInfDto.getIndustryMcc());
            for (ProductDto dto : allProdList) {
                if (EProd.S01002.getCode().equals(dto.getProdId()) || EProd.L01004.getCode().equals(dto.getProdId())) {//保骉车险，车闪贷
                    prodList.add(dto);
                }
            }
        }
        return ResultDtoFactory.toAck("获取成功", prodList);
    }

}
