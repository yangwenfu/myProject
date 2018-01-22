package com.xinyunlian.jinfu.shopkeeper.controller;

import com.xinyunlian.jinfu.ad.dto.AdFrontDto;
import com.xinyunlian.jinfu.ad.service.AdService;
import com.xinyunlian.jinfu.api.dto.ApiBaiduDto;
import com.xinyunlian.jinfu.api.service.ApiBaiduService;
import com.xinyunlian.jinfu.area.dto.SysAreaInfDto;
import com.xinyunlian.jinfu.area.service.SysAreaInfService;
import com.xinyunlian.jinfu.cms.dto.NoticeInfDto;
import com.xinyunlian.jinfu.cms.enums.ENoticePlatform;
import com.xinyunlian.jinfu.cms.service.NoticeInfService;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.util.MessageUtil;
import com.xinyunlian.jinfu.creditline.service.LoanUserCreditLineService;
import com.xinyunlian.jinfu.dict.dto.DictionaryItemDto;
import com.xinyunlian.jinfu.dict.service.DictService;
import com.xinyunlian.jinfu.loan.service.LoanService;
import com.xinyunlian.jinfu.prod.dto.ProdAppDetailDto;
import com.xinyunlian.jinfu.prod.dto.ProductDto;
import com.xinyunlian.jinfu.prod.enums.EProdAppDetailCfg;
import com.xinyunlian.jinfu.prod.enums.EShelfPlatform;
import com.xinyunlian.jinfu.prod.service.ProdService;
import com.xinyunlian.jinfu.shopkeeper.dto.home.FloorDto;
import com.xinyunlian.jinfu.shopkeeper.dto.home.HomePageDto;
import com.xinyunlian.jinfu.shopkeeper.dto.home.NoticeDto;
import com.xinyunlian.jinfu.shopkeeper.dto.home.TemplateDto;
import com.xinyunlian.jinfu.shopkeeper.service.PrivateHomeService;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.store.service.StoreService;
import com.xinyunlian.jinfu.thirdparty.nbcb.service.NBCBOrderService;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by menglei on 2016年08月30日.
 */
@RestController
@RequestMapping(value = "shopkeeper/home")
public class HomeController {
    @Autowired
    private AdService adService;
    @Autowired
    private NoticeInfService noticeInfService;
    @Autowired
    private ProdService prodService;
    @Autowired
    private PrivateHomeService privateHomeService;
    @Autowired
    private NBCBOrderService nbcbOrderService;
    @Autowired
    private LoanService loanService;
    @Autowired
    private UserService userService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private LoanUserCreditLineService loanUserCreditLineService;
    @Autowired
    private ApiBaiduService apiBaiduService;
    @Autowired
    private DictService dictService;
    @Autowired
    private SysAreaInfService sysAreaInfService;

    private static final String YLZG_MY_LOAN = "YLZG_MY_LOAN";

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    /**
     * 首页未登入
     *
     * @return
     */
    @GetMapping("/unLogin")
    public ResultDto<Object> unLogin(@RequestParam Integer width, @RequestParam Integer height) {
        HomePageDto homePageDto = new HomePageDto();

        //获取广告信息 posId的值需要固定，并与前端事先约定好各代表哪个位置
        List<AdFrontDto> list = getAdFront(17L, width, height);
        if (!CollectionUtils.isEmpty(list)) {
            homePageDto.getAdFrontDtos().addAll(list);
        }
        list = getAdFront(18L, width, height);
        if (!CollectionUtils.isEmpty(list)) {
            homePageDto.getAdFrontDtos().addAll(list);
        }
        list = getAdFront(19L, width, height);
        if (!CollectionUtils.isEmpty(list)) {
            homePageDto.getAdFrontDtos().addAll(list);
        }

        //获取文章信息 感兴趣文章的大类id需要事先约定好
        privateHomeService.setArticle(homePageDto);

        return ResultDtoFactory.toAck("获取成功", homePageDto);
    }

    @GetMapping("/main")
    public ResultDto<Object> main(@RequestParam Integer width, @RequestParam Integer height,
                                  @RequestHeader(value = "ga-longitude", required = false) String longitude,
                                  @RequestHeader(value = "ga-latitude", required = false) String latitude) {
        HomePageDto homePageDto = new HomePageDto();

        String gps = latitude + "," + longitude;

        LOGGER.info("GPS : {}",gps);

        SysAreaInfDto sysAreaInfDto = this.gpsToArea(gps);

        //ATest信用额度
        UserInfoDto userInfoDto = userService.findUserByUserId(SecurityContext.getCurrentUserId());
        if ("A".equals(userInfoDto.getAbTest())) {
            homePageDto.setLoanCreditLineDto(loanUserCreditLineService.get(userInfoDto.getUserId()));
        }

        //获取公告
        List<NoticeInfDto> noticeInfDtos = noticeInfService.getNoticeByPlatform(ENoticePlatform.YLZG);
        if (!CollectionUtils.isEmpty(noticeInfDtos)) {
            noticeInfDtos.forEach(noticeInfDto -> {
                NoticeDto noticeDto = ConverterService.convert(noticeInfDto, NoticeDto.class);
                homePageDto.getNoticeDtos().add(noticeDto);
            });
        }

        //添加品牌广告位
        homePageDto.setBrandAdDtos(getAdFront(34L, width, height));

        //获取广告信息 posId的值需要固定，并与前端事先约定好各代表哪个位置
        List<AdFrontDto> list;
        List<StoreInfDto> byUserId = storeService.findByUserId(SecurityContext.getCurrentUserId());
        List<String> storeCities = byUserId.stream()
                .map(value -> value.getCityId())
                .collect(Collectors.toList());
        if (nbcbOrderService.hasEntryPermission(SecurityContext.getCurrentUserId(), storeCities) &&
                loanService.count(SecurityContext.getCurrentUserId()) > 0) {
            list = getAdFront(31L, width, height);
            homePageDto.getAdFrontDtos().addAll(list);
        }
        list = getAdFront(20L, width, height);
        homePageDto.getAdFrontDtos().addAll(list);

        //获取文章信息 感兴趣文章的大类id需要事先约定好
        privateHomeService.setArticle(homePageDto);

        //新品楼层
        FloorDto fresh = new FloorDto();
        fresh.setCode("fresh");
        fresh.setTitle("新品");
        List<ProductDto> freshDtos = prodService.getProdListByPlatformAndCfg(EShelfPlatform.YLZG, EProdAppDetailCfg.NEW, 10);
        if (!CollectionUtils.isEmpty(freshDtos)) {
            for (ProductDto productDto : freshDtos) {
                privateHomeService.productInToFloorForIndustry(productDto.getProdTypeInfDtoLv1(), productDto, fresh, sysAreaInfDto);
            }
        }

        //有新品则加入返回对象中
        if (fresh.getTemplateDtos().size() > 0) {
            homePageDto.getFloorDto().add(fresh);
        }

        //推荐
        FloorDto recommend = new FloorDto();
        recommend.setCode("recommend");
        recommend.setTitle("推荐");
        List<ProductDto> recommendDtos = prodService.getProdListByPlatformAndCfg(EShelfPlatform.YLZG, EProdAppDetailCfg.RCMD, 10);
        if (!CollectionUtils.isEmpty(recommendDtos)) {
            for (ProductDto productDto : recommendDtos) {
                privateHomeService.productInToFloorForIndustry(productDto.getProdTypeInfDtoLv1(), productDto, recommend,
                        sysAreaInfDto);
            }
        }

        //有推荐则加入返回对象中
        if (recommend.getTemplateDtos().size() > 0) {
            homePageDto.getFloorDto().add(recommend);
        }

        //热卖
        FloorDto hot = new FloorDto();
        hot.setCode("hot");
        hot.setTitle("热卖");
        List<ProductDto> hotDtos = prodService.getProdListByPlatformAndCfg(EShelfPlatform.YLZG, EProdAppDetailCfg.HOT, 10);
        if (!CollectionUtils.isEmpty(hotDtos)) {
            for (ProductDto productDto : hotDtos) {
                privateHomeService.productInToFloorForIndustry(productDto.getProdTypeInfDtoLv1(), productDto, hot,
                        sysAreaInfDto);
            }
        }

        //有热品则加入返回对象中
        if (hot.getTemplateDtos().size() > 0) {
            homePageDto.getFloorDto().add(hot);
        }

        privateHomeService.setProductFloor(homePageDto, EShelfPlatform.YLZG, sysAreaInfDto);

        return ResultDtoFactory.toAck("获取成功", homePageDto);
    }


    private List<AdFrontDto> getAdFront(Long posId, Integer width, Integer height) {
        List<AdFrontDto> list = new ArrayList<>();
        try {
            list = adService.getAdFront(posId, width, height);
        } catch (BizServiceException e) {
            LOGGER.error("获取广告位错误", e);
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    /**
     * 获得所有产品列表 目前只有融资产品
     *
     * @return
     */
    @GetMapping("/product/list")
    public Object productList(@RequestParam String prodTypeCode, @RequestParam EShelfPlatform platform,
                              @RequestHeader(value = "ga-longitude", required = false) String longitude,
                              @RequestHeader(value = "ga-latitude", required = false) String latitude) {
        String gps = latitude + "," + longitude;
        LOGGER.info("GPS : {}",gps);
        SysAreaInfDto sysAreaInfDto = this.gpsToArea(gps);

        List<TemplateDto> templateDtos = privateHomeService.listProduct(prodTypeCode, platform,sysAreaInfDto);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), templateDtos);
    }

    /**
     * 获得所有产品列表 (登入后调用)
     *
     * @return
     */
    @GetMapping("/logged/product/list")
    public Object loggedProductList(@RequestParam String prodTypeCode, @RequestParam EShelfPlatform platform,
                                    @RequestHeader(value = "ga-longitude", required = false) String longitude,
                                    @RequestHeader(value = "ga-latitude", required = false) String latitude) {
        String gps = latitude + "," + longitude;
        LOGGER.info("GPS : {}",gps);
        SysAreaInfDto sysAreaInfDto = this.gpsToArea(gps);

        List<TemplateDto> templateDtos = privateHomeService.listProduct(prodTypeCode, platform, sysAreaInfDto);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), templateDtos);
    }

    @ApiOperation(value = "产品列表广告位")
    @GetMapping("/product/ad")
    public Object productAd(@RequestParam String prodTypeCode, @RequestParam Integer width,
                            @RequestParam Integer height) {
        List<AdFrontDto> list = new ArrayList<>();
        if ("30".equals(prodTypeCode)) {
            list = getAdFront(24L, width, height);
        } else {
            list = getAdFront(30L, width, height);
        }
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), list);
    }

    /**
     * 获得某个产品明细
     *
     * @return
     */
    @GetMapping("/product/get")
    public Object getProduct(@RequestParam String prodId) {
        ProdAppDetailDto prodAppDetailDto = new ProdAppDetailDto();
        ProductDto productDto = prodService.getProdById(prodId);
        if (productDto != null) {
            prodAppDetailDto = productDto.getProdAppDetailDto();
        }
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), prodAppDetailDto);
    }

    @ApiOperation(value = "获取广告信息")
    @GetMapping("/ad")
    public Object getAd(@RequestParam Long posId, @RequestParam Integer width,
                        @RequestParam Integer height) {
        List<AdFrontDto> list = new ArrayList<>();
        List<StoreInfDto> byUserId = storeService.findByUserId(SecurityContext.getCurrentUserId());
        List<String> storeCities = byUserId.stream()
                .map(value -> value.getCityId())
                .collect(Collectors.toList());
        if (!nbcbOrderService.hasEntryPermission(SecurityContext.getCurrentUserId(), storeCities) &&
                posId == 32) {
            return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), list);
        }
        list = getAdFront(posId, width, height);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), list);
    }

    /**
     * 获取我的贷款的贷款记录tab页面
     *
     * @return
     */
    @GetMapping("getMyLoanTab")
    public ResultDto getMyLoanTab() {
        List<DictionaryItemDto> list = dictService.getDictItemsByDictType(YLZG_MY_LOAN);

        List<StoreInfDto> stores = storeService.findByUserId(SecurityContext.getCurrentUserId());
        List<String> cities = stores.stream()
                .map(value -> value.getCityId())
                .collect(Collectors.toList());

        if (!nbcbOrderService.areaCovered(cities)) {
            list = list.stream()
                    .filter(item -> !"LOAN_BNB".equals(item.getCode()))
                    .collect(Collectors.toList());
        }
        return ResultDtoFactory.toAckData(list);
    }

    private SysAreaInfDto gpsToArea(String gps){
        SysAreaInfDto sysAreaInfDto = new SysAreaInfDto();
        sysAreaInfDto.setId(941L);
        //获取定位信息
        if (StringUtils.isNotEmpty(gps) && !gps.contains("null")) {//没坐标不显示
            ApiBaiduDto apiBaiduDto = apiBaiduService.getArea(gps);
            if (apiBaiduDto != null) {
                sysAreaInfDto = sysAreaInfService.getSysAreaByGbCode(apiBaiduDto.getGbCode());
            }else{
                sysAreaInfDto = new SysAreaInfDto();
                sysAreaInfDto.setId(941L);
            }
        }
        return sysAreaInfDto;
    }

}
