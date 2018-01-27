package com.xinyunlian.jinfu.dealer.dealer.controller;

import com.alibaba.fastjson.JSONObject;
import com.xinyunlian.jinfu.area.dto.SysAreaInfDto;
import com.xinyunlian.jinfu.area.enums.ESysAreaLevel;
import com.xinyunlian.jinfu.area.service.SysAreaInfService;
import com.xinyunlian.jinfu.channel.dto.ChannelUserAreaDto;
import com.xinyunlian.jinfu.channel.service.ChannelUserService;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.fileStore.FileStoreService;
import com.xinyunlian.jinfu.common.fileStore.enums.EFileType;
import com.xinyunlian.jinfu.common.security.StaticResourceSecurity;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.security.authc.ESourceType;
import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.common.util.excel.DataTablesExcelService;
import com.xinyunlian.jinfu.dealer.dealer.dto.resp.DealerDetailDto;
import com.xinyunlian.jinfu.dealer.dealer.dto.req.DealerWebDto;
import com.xinyunlian.jinfu.dealer.dealer.dto.resp.AuthProdDto;
import com.xinyunlian.jinfu.dealer.dto.*;
import com.xinyunlian.jinfu.dealer.enums.EDealerAuditStatus;
import com.xinyunlian.jinfu.dealer.enums.EDealerOpLogType;
import com.xinyunlian.jinfu.dealer.enums.EDealerStatus;
import com.xinyunlian.jinfu.dealer.service.DealerOpLogService;
import com.xinyunlian.jinfu.dealer.service.DealerProdService;
import com.xinyunlian.jinfu.prod.dto.ProductSearchDto;
import com.xinyunlian.jinfu.prod.service.ProdService;
import com.xinyunlian.jinfu.report.dealer.dto.DealerInfDto;
import com.xinyunlian.jinfu.report.dealer.dto.DealerReportSearchDto;
import com.xinyunlian.jinfu.report.dealer.dto.DealerUserReportDto;
import com.xinyunlian.jinfu.report.dealer.service.DealerReportService;
import com.xinyunlian.jinfu.dealer.service.DealerService;
import com.xinyunlian.jinfu.prod.dto.ProductDto;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.store.service.StoreService;
import com.xinyunlian.jinfu.user.dto.DealerUserDto;
import com.xinyunlian.jinfu.user.dto.MgtUserDto;
import com.xinyunlian.jinfu.user.enums.EDuty;
import com.xinyunlian.jinfu.user.service.DealerUserService;
import com.xinyunlian.jinfu.user.service.MgtUserService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by menglei on 2016年09月05日.
 */
@Controller
@RequestMapping(value = "dealer")
@RequiresPermissions({"DT_MGT"})
public class DealerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DealerController.class);

    @Autowired
    private DealerService dealerService;
    @Autowired
    private FileStoreService fileStoreService;
    @Autowired
    private SysAreaInfService sysAreaInfService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private DealerReportService dealerReportService;
    @Autowired
    private MgtUserService mgtUserService;
    @Autowired
    private DealerOpLogService dealerOpLogService;
    @Autowired
    private DealerProdService dealerProdService;
    @Autowired
    private ProdService prodService;
    @Autowired
    private ChannelUserService channelUserService;
    @Autowired
    private DealerUserService dealerUserService;

    /**
     * 分页查询分销商
     *
     * @param dealerSearchDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultDto<DealerSearchDto> getDealerPage(DealerSearchDto dealerSearchDto) {
        //数据权限
        MgtUserDto mgtUser = mgtUserService.getMgtUserInf(SecurityContext.getCurrentUserId());
        if (mgtUser == null) {
            return ResultDtoFactory.toNack("用户不存在");
        }
        DealerSearchDto page = new DealerSearchDto();
        //创建人筛选
        if (StringUtils.isNotEmpty(dealerSearchDto.getCreateOpName())) {
            List<String> createOpIds = new ArrayList<>();
            List<MgtUserDto> createList = mgtUserService.findByNameLikeNotStatus(dealerSearchDto.getCreateOpName());
            for (MgtUserDto mgtUserDto : createList) {
                createOpIds.add(mgtUserDto.getUserId());
            }
            if (CollectionUtils.isEmpty(createOpIds)) {
                return ResultDtoFactory.toAck("获取成功", page);
            }
            dealerSearchDto.setCreateOpIds(createOpIds);
        }
        if (StringUtils.isNotEmpty(dealerSearchDto.getBelongName())) {
            List<String> belongIds = new ArrayList<>();
            if ("总部".equals(dealerSearchDto.getBelongName())) {
                belongIds.add("0");
            } else if ("北京分公司".equals(dealerSearchDto.getBelongName())) {
                belongIds.add("-1");
            } else if ("广东分公司".equals(dealerSearchDto.getBelongName())) {
                belongIds.add("-2");
            } else {
                List<MgtUserDto> belongList = mgtUserService.findByNameLikeNotStatus(dealerSearchDto.getBelongName());
                for (MgtUserDto mgtUserDto : belongList) {
                    belongIds.add(mgtUserDto.getUserId());
                }
            }
            if (CollectionUtils.isEmpty(belongIds)) {
                return ResultDtoFactory.toAck("获取成功", page);
            }
            dealerSearchDto.setBelongIds(belongIds);
        }

        if (EDuty.REGIONAL_MGT.getCode().equals(mgtUser.getDuty()) || EDuty.CITY_MGT.getCode().equals(mgtUser.getDuty())) {
            List<ChannelUserAreaDto> channelUserAreas = channelUserService.listUserArea(SecurityContext.getCurrentUserId());
            List<String> provinceIds = new ArrayList<>();
            List<String> cityIds = new ArrayList<>();
            for (ChannelUserAreaDto channelUserAreaDto : channelUserAreas) {
                if (channelUserAreaDto.getProvinceId() != null) {
                    provinceIds.add(String.valueOf(channelUserAreaDto.getProvinceId()));
                }
                if (channelUserAreaDto.getCityId() != null) {
                    cityIds.add(String.valueOf(channelUserAreaDto.getCityId()));
                }
            }
            dealerSearchDto.setProvinceIds(provinceIds);
            dealerSearchDto.setCityIds(cityIds);
        }
        page = dealerService.getDealerPage(dealerSearchDto);
        //获取创建人
        List<String> mgtUserIds = new ArrayList<>();
        for (DealerDto dealerDto : page.getList()) {
            if (StringUtils.isNotEmpty(dealerDto.getCreateOpId()) && !"system".equals(dealerDto.getCreateOpId())) {
                mgtUserIds.add(dealerDto.getCreateOpId());
            }
        }
        List<MgtUserDto> mgtUsers = mgtUserService.findByMgtUserIds(mgtUserIds);
        Map<String, MgtUserDto> mgtUserMap = new HashMap<>();
        for (MgtUserDto mgtUserDto : mgtUsers) {
            mgtUserMap.put(mgtUserDto.getUserId(), mgtUserDto);
        }
        //获取责任人
        List<String> belongIds = new ArrayList<>();
        for (DealerDto dealerDto : page.getList()) {
            if (StringUtils.isNotEmpty(dealerDto.getBelongId()) && !"0".equals(dealerDto.getBelongId())
                    && !"-1".equals(dealerDto.getBelongId()) && !"-2".equals(dealerDto.getBelongId())) {
                belongIds.add(dealerDto.getBelongId());
            }
        }
        List<MgtUserDto> belongs = mgtUserService.findByMgtUserIds(belongIds);
        Map<String, MgtUserDto> belongMap = new HashMap<>();
        for (MgtUserDto mgtUserDto : belongs) {
            belongMap.put(mgtUserDto.getUserId(), mgtUserDto);
        }
        List<DealerDto> list = new ArrayList<>();
        for (DealerDto dealerDto : page.getList()) {
            MgtUserDto createDto = mgtUserMap.get(dealerDto.getCreateOpId());
            if (createDto != null) {
                dealerDto.setCreateOpName(createDto.getName());
            }
            MgtUserDto belongDto = belongMap.get(dealerDto.getBelongId());
            if (belongDto != null) {
                dealerDto.setBelongName(belongDto.getName());
            } else if ("0".equals(dealerDto.getBelongId())) {
                dealerDto.setBelongName("总部");
            } else if ("-1".equals(dealerDto.getBelongId())) {
                dealerDto.setBelongName("北京分公司");
            } else if ("-2".equals(dealerDto.getBelongId())) {
                dealerDto.setBelongName("广东分公司");
            }
            list.add(dealerDto);
        }
        page.setList(list);
        return ResultDtoFactory.toAck("获取成功", page);
    }

    /**
     * 分页查询分销商审核
     *
     * @param dealerSearchDto
     * @return
     */
    @RequiresPermissions({"DT_AUDIT_MGT"})
    @ResponseBody
    @RequestMapping(value = "/auditList", method = RequestMethod.GET)
    public ResultDto<DealerSearchDto> getAuditPage(DealerSearchDto dealerSearchDto) {
        MgtUserDto mgtUser = mgtUserService.getMgtUserInf(SecurityContext.getCurrentUserId());
        if (mgtUser == null) {
            return ResultDtoFactory.toNack("用户不存在");
        } else if (StringUtils.isEmpty(mgtUser.getDuty())) {
            return ResultDtoFactory.toNack("权限不足，只有渠道总部，区域总监，城市经理才能访问");
        }
        dealerSearchDto.setDuty(mgtUser.getDuty());
        DealerSearchDto page = dealerService.getDealerPage(dealerSearchDto);
        //获取创建人
        List<String> mgtUserIds = new ArrayList<>();
        for (DealerDto dealerDto : page.getList()) {
            if (StringUtils.isNotEmpty(dealerDto.getCreateOpId()) && !"system".equals(dealerDto.getCreateOpId())) {
                mgtUserIds.add(dealerDto.getCreateOpId());
            }
        }
        List<MgtUserDto> mgtUsers = mgtUserService.findByMgtUserIds(mgtUserIds);
        Map<String, MgtUserDto> mgtUserMap = new HashMap<>();
        for (MgtUserDto mgtUserDto : mgtUsers) {
            mgtUserMap.put(mgtUserDto.getUserId(), mgtUserDto);
        }
        List<DealerDto> list = new ArrayList<>();
        for (DealerDto dealerDto : page.getList()) {
            MgtUserDto mgtUserDto = mgtUserMap.get(dealerDto.getCreateOpId());
            if (mgtUserDto != null) {
                dealerDto.setCreateOpName(mgtUserDto.getName());
            }
            list.add(dealerDto);
        }
        page.setList(list);
        return ResultDtoFactory.toAck("获取成功", page);
    }

    @RequiresPermissions({"DT_EXPORT"})
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public ModelAndView exportDealer(DealerReportSearchDto searchDto) {
        List<DealerInfDto> data = dealerReportService.getDealerReport(searchDto);
        Map<String, Object> model = new HashedMap();
        model.put("data", data);
        model.put("fileName", "分销商记录.xls");
        model.put("tempPath", "/templates/分销商记录.xls");
        DataTablesExcelService dataTablesExcelService = new DataTablesExcelService();
        return new ModelAndView(dataTablesExcelService, model);
    }

    @RequiresPermissions({"DT_USER_EXPORT"})
    @RequestMapping(value = "/user/export", method = RequestMethod.GET)
    public ModelAndView exportDealerUser(DealerReportSearchDto searchDto) {
        List<DealerUserReportDto> data = dealerReportService.getDealerUserReport(searchDto);
        Map<String, Object> model = new HashedMap();
        model.put("data", data);
        model.put("fileName", "分销人员记录.xls");
        model.put("tempPath", "/templates/分销人员记录.xls");
        DataTablesExcelService dataTablesExcelService = new DataTablesExcelService();
        return new ModelAndView(dataTablesExcelService, model);
    }

    /**
     * 根据id查询分销商详情
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public ResultDto<DealerDto> getDetail(@RequestParam String id) {
        DealerDto dto = dealerService.getDealerById(id);
        if (dto != null) {
            String fileAddr = AppConfigUtil.getConfig("file.addr");
            if (StringUtils.isNotEmpty(dto.getDealerExtraDto().getBizLicencePic())) {
                dto.getDealerExtraDto().setBizLicencePic(fileAddr + StaticResourceSecurity.getSecurityURI(dto.getDealerExtraDto().getBizLicencePic()));
            }
            if (StringUtils.isNotEmpty(dto.getDealerExtraDto().getIdCardNoPic1())) {
                dto.getDealerExtraDto().setIdCardNoPic1(fileAddr + StaticResourceSecurity.getSecurityURI(dto.getDealerExtraDto().getIdCardNoPic1()));
            }
            if (StringUtils.isNotEmpty(dto.getDealerExtraDto().getIdCardNoPic2())) {
                dto.getDealerExtraDto().setIdCardNoPic2(fileAddr + StaticResourceSecurity.getSecurityURI(dto.getDealerExtraDto().getIdCardNoPic2()));
            }
            if (StringUtils.isNotEmpty(dto.getDealerExtraDto().getAccountLicencePic())) {
                dto.getDealerExtraDto().setAccountLicencePic(fileAddr + StaticResourceSecurity.getSecurityURI(dto.getDealerExtraDto().getAccountLicencePic()));
            }
            if (StringUtils.isNotEmpty(dto.getBelongId())) {
                if ("0".equals(dto.getBelongId())) {
                    dto.setBelongName("总部");
                    dto.setBelongDuty("CHANNEL_MGT");
                } else if ("-1".equals(dto.getBelongId())) {
                    dto.setBelongName("北京分公司");
                    dto.setBelongDuty("CHANNEL_BRANCH");
                } else if ("-2".equals(dto.getBelongId())) {
                    dto.setBelongName("广东分公司");
                    dto.setBelongDuty("CHANNEL_BRANCH");
                } else {
                    MgtUserDto mgtUserDto = mgtUserService.getMgtUserInf(dto.getBelongId());
                    if (mgtUserDto != null) {
                        dto.setBelongName(mgtUserDto.getName());
                        dto.setBelongDuty(mgtUserDto.getDuty());
                    }
                }
            }

        }
        return ResultDtoFactory.toAck("获取成功", dto);
    }

    /**
     * 删除分销商
     *
     * @param dealerId
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> delete(@RequestParam String dealerId) {
        List<DealerUserDto> dealerUsers = dealerUserService.findDealerUsersByDealerId(dealerId);
        dealerService.deleteDealer(dealerId);
        for (DealerUserDto dealerUserDto : dealerUsers) {
            SecurityContext.clearAuthcCacheByUserId(dealerUserDto.getUserId(), ESourceType.DEALER_USER);
        }
        return ResultDtoFactory.toAck("删除成功");
    }

    /**
     * 添加分销商
     *
     * @param dealerWebDto
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> add(@RequestParam MultipartFile bizLicencePicFile,
                                 @RequestParam MultipartFile idCardNo1File,
                                 @RequestParam MultipartFile idCardNo2File,
                                 DealerWebDto dealerWebDto) {
        if (StringUtils.isEmpty(dealerWebDto.getBelongId())) {
            return ResultDtoFactory.toNack("责任人必选");
        }
        DealerDto dealerDto = ConverterService.convert(dealerWebDto, DealerDto.class);
        try {
            dealerDto = this.getPicDealer(bizLicencePicFile, idCardNo1File, idCardNo2File, dealerDto);
        } catch (IOException e) {
            return ResultDtoFactory.toNack("添加失败");
        }

        List<DealerProdDto> dealerProdList = this.getDealerProdList(dealerWebDto);//授权业务列表
        //List<DealerStoreDto> dealerStoreList = this.getDealerStoreList(dealerProdList);//授权地区对应的店铺列表

        dealerService.createDealer(dealerDto, dealerProdList, null);
        return ResultDtoFactory.toAck("添加成功");
    }

    /**
     * 添加分销商
     *
     * @param dealerWebDto
     * @return
     */
    @RequestMapping(value = "/v2/add", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "添加分销商")
    public ResultDto<Object> v2Add(@RequestBody DealerWebDto dealerWebDto) {
        if (StringUtils.isEmpty(dealerWebDto.getBelongId())) {
            return ResultDtoFactory.toNack("责任人必选");
        }
        DealerDto dealerDto = ConverterService.convert(dealerWebDto, DealerDto.class);

        List<DealerProdDto> dealerProdList = this.getDealerProdList(dealerWebDto);//授权业务列表
        DealerDto dealer = dealerService.saveDealer(dealerDto, dealerProdList);

        //日志
        String userId = SecurityContext.getCurrentUserId();
        MgtUserDto userDto = mgtUserService.getMgtUserInf(userId);
        DealerOpLogDto dealerOpLogDto = new DealerOpLogDto();
        dealerOpLogDto.setOpeartor(userDto.getName());
        dealerOpLogDto.setOpLog("创建");
        dealerOpLogDto.setDealerId(dealer.getDealerId());
        dealerOpLogDto.setType(EDealerOpLogType.CREATE);
        dealerOpLogService.createDealerOpLog(dealerOpLogDto);

        return ResultDtoFactory.toAck("添加成功");
    }

    /**
     * 修改分销商
     *
     * @param dealerWebDto
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> update(@RequestParam MultipartFile bizLicencePicFile,
                                    @RequestParam MultipartFile idCardNo1File,
                                    @RequestParam MultipartFile idCardNo2File,
                                    DealerWebDto dealerWebDto) {
        if (StringUtils.isEmpty(dealerWebDto.getBelongId())) {
            return ResultDtoFactory.toNack("责任人必选");
        }
        DealerDto dealerDto = ConverterService.convert(dealerWebDto, DealerDto.class);
        try {
            dealerDto = this.getPicDealer(bizLicencePicFile, idCardNo1File, idCardNo2File, dealerDto);
        } catch (IOException e) {
            return ResultDtoFactory.toNack("修改失败");
        }
        List<DealerProdDto> dealerProdList = this.getDealerProdList(dealerWebDto);
        //List<DealerStoreDto> dealerStoreList = this.getDealerStoreList(dealerProdList);//授权地区对应的店铺列表

        dealerService.updateDealer(dealerDto, dealerProdList, null);
        return ResultDtoFactory.toAck("修改成功");
    }

    /**
     * 修改分销商
     *
     * @param dealerWebDto
     * @return
     */
    @RequiresPermissions({"DT_UPDATE"})
    @RequestMapping(value = "/v2/update", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "修改分销商")
    public ResultDto<Object> v2Update(@RequestBody DealerWebDto dealerWebDto) {
        DealerDto oldDealerDto = dealerService.getDealerById(dealerWebDto.getDealerId());
        if (oldDealerDto == null) {
            return ResultDtoFactory.toNack("分销商不存在");
        }
        if (StringUtils.isEmpty(dealerWebDto.getBelongId())) {
            return ResultDtoFactory.toNack("责任人必选");
        }
        DealerProdSearchDto dealerProdSearchDto = new DealerProdSearchDto();
        dealerProdSearchDto.setDealerId(dealerWebDto.getDealerId());
        List<DealerProdDto> oldDealerProdList = dealerProdService.getDealerProdList(dealerProdSearchDto);
        List<AuthProdDto> oldAuthProdList = getAuthProdList(oldDealerProdList);
        DealerDetailDto oldDealerDetailDto = ConverterService.convert(oldDealerDto, DealerDetailDto.class);
        oldDealerDetailDto.setAuthProdList(oldAuthProdList);

        //修改
        DealerDto dealerDto = ConverterService.convert(dealerWebDto, DealerDto.class);
        List<DealerProdDto> dealerProdList = this.getDealerProdList(dealerWebDto);
        dealerService.updateDealer(dealerDto, dealerProdList, null);
        List<AuthProdDto> authProdList = getAuthProdList(dealerProdList);
        DealerDetailDto dealerDetailDto = ConverterService.convert(dealerDto, DealerDetailDto.class);
        dealerDetailDto.setAuthProdList(authProdList);

        //日志
        String userId = SecurityContext.getCurrentUserId();
        MgtUserDto userDto = mgtUserService.getMgtUserInf(userId);
        DealerOpLogDto dealerOpLogDto = new DealerOpLogDto();
        dealerOpLogDto.setOpeartor(userDto.getName());
        dealerOpLogDto.setOpLog("修改了信息");
        dealerOpLogDto.setDealerId(dealerWebDto.getDealerId());
        dealerOpLogDto.setType(EDealerOpLogType.UPDATE);
        dealerOpLogDto.setFrontBase(JSONObject.toJSON(oldDealerDetailDto).toString());
        dealerOpLogDto.setAfterBase(JSONObject.toJSON(dealerDetailDto).toString());
        dealerOpLogService.createDealerOpLog(dealerOpLogDto);
        return ResultDtoFactory.toAck("修改成功");
    }

    /**
     * 重新提交分销商
     *
     * @param dealerWebDto
     * @return
     */
    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "重新提交分销商")
    public ResultDto<Object> reset(@RequestBody DealerWebDto dealerWebDto) {
        DealerDto oldDealerDto = dealerService.getDealerById(dealerWebDto.getDealerId());
        if (oldDealerDto == null) {
            return ResultDtoFactory.toNack("分销商不存在");
        }
        if (EDealerAuditStatus.REFUSE.equals(oldDealerDto.getAuditStatus()) && !oldDealerDto.getCreateOpId().equals(SecurityContext.getCurrentUserId())) {
            return ResultDtoFactory.toNack("只有创建人才能重新提交");
        }
        DealerProdSearchDto dealerProdSearchDto = new DealerProdSearchDto();
        dealerProdSearchDto.setDealerId(dealerWebDto.getDealerId());
        List<DealerProdDto> oldDealerProdList = dealerProdService.getDealerProdList(dealerProdSearchDto);
        List<AuthProdDto> oldAuthProdList = getAuthProdList(oldDealerProdList);
        DealerDetailDto oldDealerDetailDto = ConverterService.convert(oldDealerDto, DealerDetailDto.class);
        oldDealerDetailDto.setAuthProdList(oldAuthProdList);

        //重新提交
        DealerDto dealerDto = ConverterService.convert(dealerWebDto, DealerDto.class);
        List<DealerProdDto> dealerProdList = this.getDealerProdList(dealerWebDto);
        dealerService.updateDealer(dealerDto, dealerProdList, null);
        List<AuthProdDto> authProdList = getAuthProdList(dealerProdList);
        DealerDetailDto dealerDetailDto = ConverterService.convert(dealerDto, DealerDetailDto.class);
        dealerDetailDto.setAuthProdList(authProdList);

        //日志
        String userId = SecurityContext.getCurrentUserId();
        MgtUserDto userDto = mgtUserService.getMgtUserInf(userId);
        DealerOpLogDto dealerOpLogDto = new DealerOpLogDto();
        dealerOpLogDto.setOpeartor(userDto.getName());
        dealerOpLogDto.setOpLog("重新提交");
        dealerOpLogDto.setDealerId(dealerWebDto.getDealerId());
        dealerOpLogDto.setType(EDealerOpLogType.RESET);
        dealerOpLogDto.setFrontBase(JSONObject.toJSON(oldDealerDetailDto).toString());
        dealerOpLogDto.setAfterBase(JSONObject.toJSON(dealerDetailDto).toString());
        dealerOpLogService.createDealerOpLog(dealerOpLogDto);
        return ResultDtoFactory.toAck("修改成功");
    }

    /**
     * 业务信息修改
     *
     * @param dealerWebDto
     * @return
     */
    @RequestMapping(value = "/v2/updateProd", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "业务信息修改")
    public ResultDto<Object> v2UpdateProd(@RequestBody DealerWebDto dealerWebDto) {
        DealerDto oldDealerDto = dealerService.getDealerById(dealerWebDto.getDealerId());
        if (oldDealerDto == null) {
            return ResultDtoFactory.toNack("分销商不存在");
        }
        DealerProdSearchDto dealerProdSearchDto = new DealerProdSearchDto();
        dealerProdSearchDto.setDealerId(dealerWebDto.getDealerId());
        List<DealerProdDto> oldDealerProdList = dealerProdService.getDealerProdList(dealerProdSearchDto);
        List<AuthProdDto> oldAuthProdList = getAuthProdList(oldDealerProdList);
        DealerDetailDto oldDealerDetailDto = ConverterService.convert(oldDealerDto, DealerDetailDto.class);
        oldDealerDetailDto.setAuthProdList(oldAuthProdList);

        //业务信息修改
        DealerDto dealerDto = ConverterService.convert(dealerWebDto, DealerDto.class);
        List<DealerProdDto> dealerProdList = this.getDealerProdList(dealerWebDto);
        dealerService.updateDealerProd(dealerDto, dealerProdList);
        List<AuthProdDto> authProdList = getAuthProdList(dealerProdList);
        DealerDetailDto dealerDetailDto = ConverterService.convert(dealerDto, DealerDetailDto.class);
        dealerDetailDto.setAuthProdList(authProdList);

        //日志
        String userId = SecurityContext.getCurrentUserId();
        MgtUserDto userDto = mgtUserService.getMgtUserInf(userId);
        DealerOpLogDto dealerOpLogDto = new DealerOpLogDto();
        dealerOpLogDto.setOpeartor(userDto.getName());
        dealerOpLogDto.setOpLog("修改了信息");
        dealerOpLogDto.setDealerId(dealerWebDto.getDealerId());
        dealerOpLogDto.setType(EDealerOpLogType.UPDATE);
        dealerOpLogDto.setFrontBase(JSONObject.toJSON(oldDealerDetailDto).toString());
        dealerOpLogDto.setAfterBase(JSONObject.toJSON(dealerDetailDto).toString());
        dealerOpLogService.createDealerOpLog(dealerOpLogDto);
        return ResultDtoFactory.toAck("修改成功");
    }

    /**
     * 分销商审批通过
     *
     * @param dto
     * @return
     */
    @RequiresPermissions({"DT_AUDIT_MGT"})
    @RequestMapping(value = "/auditPass", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "分销商审批通过")
    public ResultDto<Object> auditPass(@RequestBody DealerDto dto) {

        DealerDto dealerDto = dealerService.getDealerById(dto.getDealerId());
        if (dealerDto == null) {
            return ResultDtoFactory.toNack("分销商不存在");
        } else if (EDealerAuditStatus.REFUSE.equals(dealerDto.getAuditStatus())) {
            return ResultDtoFactory.toNack("分销商审批已被拒");
        } else if (EDealerAuditStatus.PASS.equals(dealerDto.getAuditStatus())) {
            return ResultDtoFactory.toNack("分销商审批已通过");
        }

        MgtUserDto mgtUser = mgtUserService.getMgtUserInf(SecurityContext.getCurrentUserId());
        if (mgtUser == null) {
            return ResultDtoFactory.toNack("用户不存在");
        }
        if (EDuty.REGIONAL_MGT.getCode().equals(mgtUser.getDuty())) {//大区经理
            if (!EDealerAuditStatus.UN_FIRST_AUDIT.equals(dealerDto.getAuditStatus())) {
                return ResultDtoFactory.toNack("权限不足");
            }
        }
        EDealerAuditStatus auditStatus = EDealerAuditStatus.UN_LAST_AUDIT;
        EDealerOpLogType opType = EDealerOpLogType.FIRST_AUDIT;
        String opLog = "初审通过";
        if (EDealerAuditStatus.UN_LAST_AUDIT.equals(dealerDto.getAuditStatus())) {
            auditStatus = EDealerAuditStatus.PASS;
            opType = EDealerOpLogType.LAST_AUDIT;
            opLog = "终审通过";
        }
        dealerDto.setAuditStatus(auditStatus);
        dealerDto.setRemark(null);
        dealerService.updateAudit(dealerDto);
        //日志
        String userId = SecurityContext.getCurrentUserId();
        MgtUserDto userDto = mgtUserService.getMgtUserInf(userId);
        DealerOpLogDto dealerOpLogDto = new DealerOpLogDto();
        dealerOpLogDto.setOpeartor(userDto.getName());
        dealerOpLogDto.setOpLog(opLog);
        dealerOpLogDto.setDealerId(dealerDto.getDealerId());
        dealerOpLogDto.setType(opType);
        dealerOpLogService.createDealerOpLog(dealerOpLogDto);
        return ResultDtoFactory.toAck("审批通过");
    }

    /**
     * 分销商审批拒绝
     *
     * @param dto
     * @return
     */
    @RequiresPermissions({"DT_AUDIT_MGT"})
    @RequestMapping(value = "/auditRefuse", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "分销商审批拒绝")
    public ResultDto<Object> auditRefuse(@RequestBody DealerDto dto) {
        if (StringUtils.isEmpty(dto.getRemark())) {
            return ResultDtoFactory.toNack("拒绝原因必填");
        }
        DealerDto dealerDto = dealerService.getDealerById(dto.getDealerId());
        if (dealerDto == null) {
            return ResultDtoFactory.toNack("分销商不存在");
        } else if (EDealerAuditStatus.REFUSE.equals(dealerDto.getAuditStatus())) {
            return ResultDtoFactory.toNack("分销商审批已被拒");
        } else if (EDealerAuditStatus.PASS.equals(dealerDto.getAuditStatus())) {
            return ResultDtoFactory.toNack("分销商审批已通过");
        }

        MgtUserDto mgtUser = mgtUserService.getMgtUserInf(SecurityContext.getCurrentUserId());
        if (mgtUser == null) {
            return ResultDtoFactory.toNack("用户不存在");
        }
        if (EDuty.REGIONAL_MGT.getCode().equals(mgtUser.getDuty())) {//大区经理
            if (!EDealerAuditStatus.UN_FIRST_AUDIT.equals(dealerDto.getAuditStatus())) {
                return ResultDtoFactory.toNack("权限不足");
            }
        }
        dealerDto.setAuditStatus(EDealerAuditStatus.REFUSE);
        dealerDto.setRemark(dto.getRemark());
        dealerService.updateAudit(dealerDto);

        EDealerOpLogType opType = EDealerOpLogType.FIRST_AUDIT;
        String opLog = "初审拒绝，原因：" + dto.getRemark();
        if (EDealerAuditStatus.UN_LAST_AUDIT.equals(dealerDto.getAuditStatus())) {
            opType = EDealerOpLogType.LAST_AUDIT;
            opLog = "终审拒绝，原因：" + dto.getRemark();
        }
        //日志
        String userId = SecurityContext.getCurrentUserId();
        MgtUserDto userDto = mgtUserService.getMgtUserInf(userId);
        DealerOpLogDto dealerOpLogDto = new DealerOpLogDto();
        dealerOpLogDto.setOpeartor(userDto.getName());
        dealerOpLogDto.setOpLog(opLog);
        dealerOpLogDto.setDealerId(dealerDto.getDealerId());
        dealerOpLogDto.setType(opType);
        dealerOpLogService.createDealerOpLog(dealerOpLogDto);
        return ResultDtoFactory.toAck("审批拒绝");
    }

    /**
     * 分销商禁用启用
     *
     * @param dealerId
     * @return
     */
    @RequiresPermissions({"DT_FROZEN"})
    @RequestMapping(value = "/frozen", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> frozen(@RequestParam String dealerId) {
        dealerService.updateFrozen(dealerId);
        DealerDto dealerDto = dealerService.getDealerById(dealerId);
        if (dealerDto != null && EDealerStatus.FROZEN.equals(dealerDto.getStatus())) {
            List<DealerUserDto> dealerUsers = dealerUserService.findDealerUsersByDealerId(dealerDto.getDealerId());
            for (DealerUserDto dealerUserDto : dealerUsers) {
                SecurityContext.clearAuthcCacheByUserId(dealerUserDto.getUserId(), ESourceType.DEALER_USER);
            }
        }
        return ResultDtoFactory.toAck("删除成功");
    }

    /**
     * 获取负责人
     *
     * @param duty
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/channelList", method = RequestMethod.GET)
    public ResultDto<List<MgtUserDto>> getChannelList(@RequestParam String duty) {
        if (!EDuty.CITY_MGT.getCode().equals(duty) && !EDuty.REGIONAL_MGT.getCode().equals(duty)) {
            return ResultDtoFactory.toAck("获取成功", null);
        }
        List<MgtUserDto> list = mgtUserService.findByDuty(duty);
        return ResultDtoFactory.toAck("获取成功", list);
    }

    /**
     * 授权业务数据处理
     *
     * @param dealerWebDto
     * @return
     */
    private List<DealerProdDto> getDealerProdList(DealerWebDto dealerWebDto) {
        List<DealerProdDto> dealerProdList = new ArrayList<>();
        List<AuthProdDto> authProdList = dealerWebDto.getAuthProdList();
        if (authProdList == null || authProdList.size() <= 0) {
            return dealerProdList;
        }
        for (AuthProdDto authProdDto : authProdList) {
            if (authProdDto.getProductList() != null && authProdDto.getProductList().size() > 0) {
                for (ProductDto productDto : authProdDto.getProductList()) {
                    DealerProdDto dealerProdDto = ConverterService.convert(authProdDto, DealerProdDto.class);
                    dealerProdDto.setProdId(productDto.getProdId());
                    dealerProdList.add(dealerProdDto);
                }
            }
        }
        return dealerProdList;
    }

    /**
     * 图片上传处理
     *
     * @param bizLicencePicFile
     * @param idCardNo1File
     * @param idCardNo2File
     * @param dealerDto
     * @return
     * @throws IOException
     */
    private DealerDto getPicDealer(MultipartFile bizLicencePicFile, MultipartFile idCardNo1File, MultipartFile idCardNo2File, DealerDto dealerDto) throws IOException {
        if (bizLicencePicFile != null && bizLicencePicFile.getSize() > 0) {
            String picPath =
                    fileStoreService.upload(EFileType.DEALER_INFO_IMG, bizLicencePicFile.getInputStream(), bizLicencePicFile.getOriginalFilename().replace(" ", ""));
            dealerDto.getDealerExtraDto().setBizLicencePic(picPath);
        }
        if (idCardNo1File != null && idCardNo1File.getSize() > 0) {
            String picPath =
                    fileStoreService.upload(EFileType.DEALER_INFO_IMG, idCardNo1File.getInputStream(), idCardNo1File.getOriginalFilename().replace(" ", ""));
            dealerDto.getDealerExtraDto().setIdCardNoPic1(picPath);
        }
        if (idCardNo2File != null && idCardNo2File.getSize() > 0) {
            String picPath =
                    fileStoreService.upload(EFileType.DEALER_INFO_IMG, idCardNo2File.getInputStream(), idCardNo2File.getOriginalFilename().replace(" ", ""));
            dealerDto.getDealerExtraDto().setIdCardNoPic2(picPath);
        }
        return dealerDto;
    }

    /**
     * 根据授权地区获取对应的店铺列表
     *
     * @param dealerProdList
     * @return
     */
    private List<DealerStoreDto> getDealerStoreList(List<DealerProdDto> dealerProdList) {
        List<DealerStoreDto> dealerStoreList = new ArrayList<>();
        if (dealerProdList == null || dealerProdList.size() <= 0) {
            return dealerStoreList;
        }
        //查询授权地区所对应的店铺
        List<SysAreaInfDto> areaList = new ArrayList<>();
        for (DealerProdDto dealerProdDto : dealerProdList) {
            //根据授权地区最子级id(districtId)查询对应街道id(streetId)
            if (StringUtils.isNotEmpty(dealerProdDto.getStreetId())) {
                SysAreaInfDto sysAreaInfDto = sysAreaInfService.getSysAreaInfById(Long.valueOf(dealerProdDto.getStreetId()));
                if (sysAreaInfDto != null) {
                    areaList.add(sysAreaInfDto);
                }
            } else {
                List<SysAreaInfDto> streetList = sysAreaInfService.getSpecificSysArea(Long.valueOf(dealerProdDto.getDistrictId()), ESysAreaLevel.STREET);
                if (!streetList.isEmpty()) {
                    areaList.addAll(streetList);
                }
                List<SysAreaInfDto> countyList = sysAreaInfService.getSpecificSysArea(Long.valueOf(dealerProdDto.getDistrictId()), ESysAreaLevel.COUNTY);
                if (!countyList.isEmpty()) {
                    areaList.addAll(countyList);
                }
            }
        }
        List<String> areaIdList = new ArrayList<>();
        for (SysAreaInfDto sysAreaInfDto : areaList) {
            areaIdList.add(sysAreaInfDto.getId() + StringUtils.EMPTY);
        }
        List<List<String>> chunkList = chunk(areaIdList, 500);
        List<StoreInfDto> storeList = new ArrayList<>();
        for (List<String> list : chunkList) {
            if (!CollectionUtils.isEmpty(list)) {
                List<StoreInfDto> tempList = storeService.findByDistrictIds(list);
                storeList.addAll(tempList);
            }
        }
        //根据最子级id(streetId)查询对应的店铺列表
//        List<StoreInfDto> storeList = storeService.findByDistrictIds(areaIdList);
        DealerStoreDto dealerStoreDto;
        for (StoreInfDto storeInfDto : storeList) {
            dealerStoreDto = new DealerStoreDto();
            dealerStoreDto.setStoreId(storeInfDto.getStoreId() + StringUtils.EMPTY);
            dealerStoreDto.setUserId(storeInfDto.getUserId());
            dealerStoreList.add(dealerStoreDto);
        }
        return dealerStoreList;
    }

    private List<List<String>> chunk(List<String> list, int chunkSize) {
        List<List<String>> chunks = new ArrayList<>();
        int size = list.size() / chunkSize + 1;
        for (int i = 0; i < size; i++) {
            int toIndex = (i + 1) == size ? list.size() : (i + 1) * chunkSize;
            chunks.add(list.subList(i * chunkSize, toIndex));
        }
        return chunks;
    }

    private List<AuthProdDto> getAuthProdList(List<DealerProdDto> dealerProdList) {
        if (CollectionUtils.isEmpty(dealerProdList)) {
            return null;
        }
        List<ProductDto> prodList = prodService.getProdList(new ProductSearchDto());
        List<AuthProdDto> authProdListTemp = new ArrayList<>();
        for (DealerProdDto dealerProdDto : dealerProdList) {
            AuthProdDto authProdDto = ConverterService.convert(dealerProdDto, AuthProdDto.class);
            prodList.stream().filter(productDto -> dealerProdDto.getProdId().equals(productDto.getProdId())).forEach(authProdDto::setProductDto);
            authProdListTemp.add(authProdDto);
        }
        Map<String, AuthProdDto> authProdMap = new HashMap<>();
        List<ProductDto> productList;
        for (AuthProdDto authProdDto : authProdListTemp) {
            productList = new ArrayList<>();
            if (authProdMap.get(authProdDto.getDistrictId()) != null) {
                productList = authProdMap.get(authProdDto.getDistrictId()).getProductList();
            }
            productList.add(authProdDto.getProductDto());
            authProdDto.setProductList(productList);
            authProdDto.setId(null);
            authProdDto.setProductDto(null);
            authProdMap.put(authProdDto.getDistrictId(), authProdDto);
        }
        List<AuthProdDto> authProdList = authProdMap.keySet().stream().map(authProdMap::get).collect(Collectors.toList());
        return authProdList;
    }

}
