package com.xinyunlian.jinfu.crm.controller;

import com.xinyunlian.jinfu.common.constant.ApplicationConstant;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.common.util.MessageUtil;
import com.xinyunlian.jinfu.common.util.excel.DataTablesExcelService;
import com.xinyunlian.jinfu.crm.dto.*;
import com.xinyunlian.jinfu.crm.enums.ECrmUserStatus;
import com.xinyunlian.jinfu.crm.service.CrmCallService;
import com.xinyunlian.jinfu.crm.service.CrmCallTypeService;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.store.service.StoreService;
import com.xinyunlian.jinfu.user.dto.MgtUserDto;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.service.MgtUserService;
import com.xinyunlian.jinfu.user.service.UserService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 通话小计controller
 * Created by King on 2016/12/22.
 */
@RestController
@RequestMapping("crm/call")
public class CallController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CallController.class);
    @Autowired
    private CrmCallService callService;
    @Autowired
    private UserService userService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private CrmCallTypeService callTypeService;
    @Autowired
    private MgtUserService mgtUserService;

    /**
     * 添加通话记录前用户查询
     * @param crmCallDto
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<CrmUserDto> search(@RequestBody CrmCallDto crmCallDto) {
        CrmUserDto crmUserDto = new CrmUserDto();
        if(!StringUtils.isEmpty(crmCallDto.getTobaccoCertificateNo())) {
            StoreInfDto storeInfDto = storeService
                    .findByTobaccoCertificateNo(crmCallDto.getTobaccoCertificateNo());
            if(storeInfDto != null){
                crmUserDto.getStoreInfDtos().add(storeInfDto);
                if(!StringUtils.isEmpty(storeInfDto.getUserId())){
                    UserInfoDto userInfoDto = userService.findUserByUserId(storeInfDto.getUserId());
                    ConverterService.convert(userInfoDto,crmUserDto);
                    crmUserDto.setStatus(ECrmUserStatus.ACTIVE);
                }else{
                    crmUserDto.setStatus(ECrmUserStatus.NON_ACTIVE);
                }
            }

        }else if(!StringUtils.isEmpty(crmCallDto.getMobile())){
            UserInfoDto userInfoDto = userService.findUserByMobile(crmCallDto.getMobile());
            if(userInfoDto != null){
                ConverterService.convert(userInfoDto,crmUserDto);
                List<StoreInfDto> storeInfDtos = storeService.findByUserId(userInfoDto.getUserId());
                crmUserDto.getStoreInfDtos().addAll(storeInfDtos);
                crmUserDto.setStatus(ECrmUserStatus.ACTIVE);
            }else{
                crmUserDto.setStatus(ECrmUserStatus.NON_EXISTED);
            }
        }

        return ResultDtoFactory.toAck("获取成功",crmUserDto);
    }


    /**
     * 保存
     *
     * @param crmCallDtos
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> save(@RequestBody List<CrmCallDto> crmCallDtos) {
        if(!CollectionUtils.isEmpty(crmCallDtos)) {
            MgtUserDto mgtUserDto = mgtUserService.getMgtUserInf(SecurityContext.getCurrentUserId());
            crmCallDtos.forEach(crmCallDto -> {
                if(!CollectionUtils.isEmpty(crmCallDto.getCallNoteDtos())){
                    crmCallDto.getCallNoteDtos().forEach(callNoteDto -> {
                        if(null == callNoteDto.getCallNoteId()){
                            callNoteDto.setCreateName(mgtUserDto.getName());
                        }
                    });
                }
                if((null == crmCallDto.getQaStatus() || !crmCallDto.getQaStatus())
                        && StringUtils.isEmpty(crmCallDto.getQaNote())) {
                    crmCallDto.setDealPerson(mgtUserDto.getName());
                }
                callService.save(crmCallDto);
            });
        }
        return ResultDtoFactory.toAck("添加成功");
    }

    /**
     * 分页获取通话记录
     * @param callSearchDto
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<CallSearchDto> getUserPage(CallSearchDto callSearchDto){
        CallSearchDto dto = callService.getCallPage(callSearchDto);
        return ResultDtoFactory.toAck("获取成功", dto);
    }

    /**
     * 获取通话详情
     * @param callId
     * @return
     */
    @RequestMapping(value = "/getByID", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getById(@RequestParam Long callId) {
        CrmCallDto crmCallDto = callService.findByCallId(callId);
        CrmCallTypeDto crmCallTypeDto = callTypeService.findByCallTypeId(crmCallDto.getCallTypeId());
        if(crmCallTypeDto != null){
            crmCallDto.setCallTypePath(crmCallTypeDto.getCallTypePath());
        }
        List<CrmCallNoteDto> noteDtos = callService.listNotes(callId);
        if(!CollectionUtils.isEmpty(noteDtos)){
            crmCallDto.setCallNoteDtos(noteDtos);
        }
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"),crmCallDto);
    }

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public ModelAndView export(CallSearchDto callSearchDto){
        callSearchDto.setCurrentPage(1);
        callSearchDto.setPageSize(10000);
        CallSearchDto dto = callService.getCallPage(callSearchDto);
        List<CallReportDto> data = new ArrayList<>(dto.getList().size());

        dto.getList().forEach(crmCallDto -> {
            CallReportDto callReportDto = ConverterService.convert(crmCallDto,CallReportDto.class);
            List<CrmCallNoteDto> noteDtos = callService.listNotes(crmCallDto.getCallId());
            if(!CollectionUtils.isEmpty(noteDtos)){
                StringBuilder sb = new StringBuilder();
                noteDtos.forEach(callNoteDto -> {
                    sb.append(callNoteDto.getContent() + "," + callNoteDto.getCreateName() + "," +
                            DateHelper.formatDate(callNoteDto.getCreateTs(), ApplicationConstant.TIMESTAMP_FORMAT) + ";");
                });
                callReportDto.setDealContent(sb.toString());
            }
            CrmCallTypeDto callTypeDto = callTypeService.findByCallTypeId(crmCallDto.getCallTypeFirstId());
            if(null != callTypeDto) {
                callReportDto.setCallTypeFirstName(callTypeDto.getCallTypeName());
            }
            callTypeDto = callTypeService.findByCallTypeId(crmCallDto.getCallTypeSecondId());
            if(null != callTypeDto) {
                callReportDto.setCallTypeSecondName(callTypeDto.getCallTypeName());
            }
            if(crmCallDto.getQaStatus()){
                callReportDto.setQaStatus("已质检");
            }else{
                callReportDto.setQaStatus("未质检");
            }
            data.add(callReportDto);
        });

        Map<String, Object> model = new HashedMap();
        model.put("data", data);
        model.put("fileName","通话小计记录.xls");
        model.put("tempPath","/templates/通话小计记录.xls");
        DataTablesExcelService dataTablesExcelService = new DataTablesExcelService();
        return new ModelAndView(dataTablesExcelService, model);
    }

}
