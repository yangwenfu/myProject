package com.xinyunlian.jinfu.dealer.store.controller;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.common.util.excel.DataTablesExcelService;
import com.xinyunlian.jinfu.dealer.dto.DealerProdDto;
import com.xinyunlian.jinfu.dealer.dto.DealerProdSearchDto;
import com.xinyunlian.jinfu.dealer.dto.DealerStoreDto;
import com.xinyunlian.jinfu.dealer.service.DealerProdService;
import com.xinyunlian.jinfu.industry.dto.IndustryDto;
import com.xinyunlian.jinfu.industry.service.IndustryService;
import com.xinyunlian.jinfu.report.dealer.dto.DealerStoreInfDto;
import com.xinyunlian.jinfu.report.dealer.dto.DealerStoreSearchDto;
import com.xinyunlian.jinfu.dealer.dto.DealerUserStoreDto;
import com.xinyunlian.jinfu.report.dealer.service.DealerReportService;
import com.xinyunlian.jinfu.dealer.store.dto.StoreDto;
import com.xinyunlian.jinfu.dealer.store.dto.StoreInfoSearchDto;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.store.dto.req.StoreSearchDto;
import com.xinyunlian.jinfu.store.service.StoreService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
 * Created by menglei on 2016年09月18日.
 */
@Controller
@RequestMapping(value = "dealer/store")
@RequiresPermissions({"STORE_MGT"})
public class StoreController {

    @Autowired
    private StoreService storeService;
    @Autowired
    private DealerReportService dealerReportService;
    @Autowired
    private DealerProdService dealerProdService;
    @Autowired
    private IndustryService industryService;

    /**
     * 根据userId获取店铺列表
     *
     * @param userId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listByUserId", method = RequestMethod.GET)
    public ResultDto<List<StoreDto>> getStoreList(@RequestParam String userId) {
        List<StoreInfDto> list = storeService.findByUserId(userId);
        List<StoreDto> storeList = new ArrayList<>();
        DealerUserStoreDto dealerUserStoreDto;
        for (StoreInfDto storeInfDto : list) {
            StoreDto storeDto = ConverterService.convert(storeInfDto, StoreDto.class);
            dealerUserStoreDto = new DealerUserStoreDto();
            dealerUserStoreDto.setStoreId(storeInfDto.getStoreId() + StringUtils.EMPTY);
            storeList.add(storeDto);
        }
        return ResultDtoFactory.toAck("获取成功", storeList);
    }

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public ModelAndView exportStore(DealerStoreSearchDto searchDto) {
        List<DealerStoreInfDto> data = dealerReportService.getStoreReport(searchDto);
        Map<String, Object> model = new HashedMap();
        model.put("data", data);
        model.put("fileName", "店铺记录.xls");
        model.put("tempPath", "/templates/店铺记录.xls");
        DataTablesExcelService dataTablesExcelService = new DataTablesExcelService();
        return new ModelAndView(dataTablesExcelService, model);
    }

    /**
     * 分页查询店铺
     *
     * @param storeInfoSearchDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultDto<StoreInfoSearchDto> getUserPage(StoreInfoSearchDto storeInfoSearchDto) {

        StoreInfoSearchDto storeInfoPage = new StoreInfoSearchDto();
        StoreSearchDto storeSearchDto = ConverterService.convert(storeInfoSearchDto, StoreSearchDto.class);

        //根据分销商名称查已授权的地区最子级districtId
        if (StringUtils.isNotEmpty(storeInfoSearchDto.getDealerName())) {
            DealerProdSearchDto dealerProdSearchDto = new DealerProdSearchDto();
            dealerProdSearchDto.setDealerName(storeInfoSearchDto.getDealerName());
            List<DealerProdDto> dealerProdList = dealerProdService.getDealerProdList(dealerProdSearchDto);
            if (dealerProdList == null || dealerProdList.size() <= 0) {
                return ResultDtoFactory.toAck("获取成功", storeInfoPage);
            }
            List<String> districtIds = new ArrayList<>();
            for (DealerProdDto dealerProdDto : dealerProdList) {
                districtIds.add(dealerProdDto.getDistrictId() + StringUtils.EMPTY);
            }
            if (districtIds != null && districtIds.size() > 0) {
                storeSearchDto.setAreaIds(districtIds);
            }
        }
        StoreSearchDto storePage = storeService.getStorePage(storeSearchDto);
        storeInfoPage = ConverterService.convert(storeInfoSearchDto, StoreInfoSearchDto.class);
        storeInfoPage.setPageSize(storePage.getPageSize());
        storeInfoPage.setCurrentPage(storePage.getCurrentPage());
        storeInfoPage.setTotalPages(storePage.getTotalPages());
        storeInfoPage.setTotalRecord(storePage.getTotalRecord());

        List<StoreSearchDto> list = storePage.getList();
        if (list == null || list.size() <= 0) {
            return ResultDtoFactory.toAck("获取成功", storeInfoPage);
        }
        Map<String, StoreDto> storesMap = new HashMap<>();
        DealerProdSearchDto dealerProdSearchDto = new DealerProdSearchDto();
        List<DealerProdDto> dealerProdList = dealerProdService.getDealerProdList(dealerProdSearchDto);

        //遍历行业名称
        List<IndustryDto> industryList = industryService.getAllIndustry();
        Map<String, String> industryMap = new HashMap<>();
        for (IndustryDto industryDto : industryList) {
            industryMap.put(industryDto.getMcc(), industryDto.getIndName());
        }

        for (StoreSearchDto storeSearch : list) {
            StoreDto storeDto = ConverterService.convert(storeSearch, StoreDto.class);
            storeDto.setCreateTime(DateHelper.formatDate(storeSearch.getCreateTs(), "yyyy-MM-dd HH:mm"));
            storeDto.setIndustryName(industryMap.get(storeSearch.getIndustryMcc()));
            Map<String, String> dealersMap = new HashMap<>();
            //求店铺的所属分销商
            for (DealerProdDto dealerProdDto : dealerProdList) {
                if (dealerProdDto.getProvinceId().equals(storeSearch.getProvinceId())
                        && (dealerProdDto.getCityId().equals(storeSearch.getCityId()) || StringUtils.isEmpty(dealerProdDto.getCityId()))
                        && (dealerProdDto.getAreaId().equals(storeSearch.getAreaId()) || StringUtils.isEmpty(dealerProdDto.getAreaId()))
                        && (dealerProdDto.getStreetId().equals(storeSearch.getStreetId()) || StringUtils.isEmpty(dealerProdDto.getStreetId()))) {
                    List<DealerStoreDto> dealerStoreList = storeDto.getDealerStoreList();
                    if (dealerStoreList == null) {
                        dealerStoreList = new ArrayList<>();
                    }
                    DealerStoreDto dealerStoreDto = ConverterService.convert(dealerProdDto, DealerStoreDto.class);
                    if (dealerProdDto.getDealerDto() != null) {
                        dealerStoreDto.setDealerName(dealerProdDto.getDealerDto().getDealerName());
                    }
                    if (dealersMap.get(dealerStoreDto.getDealerId()) == null) {
                        dealerStoreList.add(dealerStoreDto);
                        storeDto.setDealerStoreList(dealerStoreList);
                        dealersMap.put(dealerStoreDto.getDealerId(), dealerStoreDto.getDealerId());
                    }
                }
            }
            storesMap.put(storeSearch.getStoreId() + StringUtils.EMPTY, storeDto);
        }
        List<StoreDto> storeDtoList = new ArrayList<>(storesMap.values());
        Collections.sort(storeDtoList, (arg0, arg1) -> arg1.getStoreId().compareTo(arg0.getStoreId()));
        storeInfoPage.setList(storeDtoList);

        return ResultDtoFactory.toAck("获取成功", storeInfoPage);
    }

    static class StoreIdComparator implements Comparator {
        public int compare(Object object1, Object object2) {
            StoreDto s1 = (StoreDto) object1;
            StoreDto s2 = (StoreDto) object2;
            return s1.getStoreId().compareTo(s2.getStoreId());
        }
    }

}
