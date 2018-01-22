package com.xinyunlian.jinfu.app.controller;

import com.xinyunlian.jinfu.app.dto.*;
import com.xinyunlian.jinfu.app.enums.EOperatingSystem;
import com.xinyunlian.jinfu.app.service.AppVersionControlService;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.fileStore.FileStoreService;
import com.xinyunlian.jinfu.common.fileStore.enums.EFileType;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.user.dto.MgtUserDto;
import com.xinyunlian.jinfu.user.service.MgtUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by dongfangchao on 2016/12/21/0021.
 */
@RestController
@RequestMapping("appVerCtrl")
public class AppVerCtrlController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppVerCtrlController.class);

    @Autowired
    private AppVersionControlService appVersionControlService;

    @Autowired
    private FileStoreService fileStoreService;

    @Autowired
    private MgtUserService mgtUserService;

    /**
     * 分页查询版本控制
     * @param searchDto
     * @return
     */
    @GetMapping(value = "/page")
    public ResultDto<Object> getAppPage(AppVerCtrlSearchDto searchDto){
        searchDto = appVersionControlService.getAppVerCtrlPage(searchDto);
        return ResultDtoFactory.toAck("查询成功", searchDto);
    }

    /**
     * 新增app版本控制
     * @param file
     * @param dto
     * @return
     */
    @PostMapping(value = "/add")
    public ResultDto<Object> addAppVerCtrl(@RequestParam(value = "file", required = false) MultipartFile file, AppVersionControlDto dto){
        AppVersionControlDto checkDto = appVersionControlService.getLatestAppInfo(dto.getAppSource(), dto.getOperatingSystem());
        if (checkDto != null){
            return ResultDtoFactory.toNack("APP已存在，请不要重复添加！");
        }

        if (file != null && !StringUtils.isEmpty(file.getOriginalFilename())){
            try {
                String originalName = file.getOriginalFilename();
                String suffix = originalName.substring(originalName.lastIndexOf("."), originalName.length());
                String appPath =
                        fileStoreService.uploadDirect(EFileType.APP_FILE_PATH, file.getInputStream(), dto.getAppSource().getApp() + suffix);
                dto.setAppPath(appPath);
            } catch (IOException e) {
                LOGGER.error("应用上传失败", e);
                return ResultDtoFactory.toNack("新增版本控制信息失败");
            }
        }

        try {
            appVersionControlService.addAppVerCtrl(dto);

            AppVersionControlDto newDto = appVersionControlService.getLatestAppInfo(dto.getAppSource(), dto.getOperatingSystem());
            String userId = SecurityContext.getCurrentUserId();
            concatAppOpLog(userId, newDto, null, true);
        } catch (BizServiceException e) {
            return ResultDtoFactory.toNack(e.getError());
        }
        return ResultDtoFactory.toAck("新增成功");

    }

    /**
     * 发布新的APP版本
     * @param file
     * @param req
     * @return
     */
    @PostMapping(value = "/normalRelease")
    public ResultDto<Object> normalRelease(@RequestParam(value = "file", required = false) MultipartFile file, @Valid AppVersionControlReq req, BindingResult result){

        if (result.hasErrors()){
            return ResultDtoFactory.toNack(result.getFieldError().getDefaultMessage());
        }

        AppVersionControlDto currentRelease = appVersionControlService.getApp(req.getAppSource().getCode(), req.getOperatingSystem().getCode());
        if (currentRelease == null){
            return ResultDtoFactory.toNack("APP不存在");
        }

        AppVersionControlDto nextRelease = ConverterService.convert(req, AppVersionControlDto.class);
        nextRelease.setId(currentRelease.getId());
        nextRelease.setNormalRelease(true);
        nextRelease.setReleaseTime(Calendar.getInstance().getTime());

        //版本号校验
        Integer compareResult =
                appVersionControlService.compareAppVersionName(nextRelease.getAppSource().getCode(),
                        nextRelease.getOperatingSystem().getCode(), nextRelease.getVersionName(), true);
        if (compareResult != 1){
            return ResultDtoFactory.toNack("版本号不能低于或等于上一个正式版本号！");
        }

        AppVersionControlLogDto appVersionControlLogDto = ConverterService.convert(currentRelease, AppVersionControlLogDto.class);
        if (nextRelease.getOperatingSystem() == EOperatingSystem.ANDROID){

            if (file == null){
                return ResultDtoFactory.toNack("上传的APP包不能为空");
            }
            try {
                String originalName = file.getOriginalFilename();
                String suffix = originalName.substring(originalName.lastIndexOf("."), originalName.length());

                String appPackName = currentRelease.getAppSource().getApp() + suffix;
                String appPackNameLog = currentRelease.getAppSource().getApp() + "." + System.currentTimeMillis() + suffix;
                fileStoreService.renameFile(EFileType.APP_FILE_PATH.getPath(), appPackName, appPackNameLog);

                String appPath =
                        fileStoreService.uploadDirect(EFileType.APP_FILE_PATH, file.getInputStream(), appPackName);
                nextRelease.setAppPath(appPath);
                appVersionControlLogDto.setAppPath(currentRelease.getAppPath().replace(appPackName, appPackNameLog));

            } catch (IOException e) {
                LOGGER.error("应用上传失败", e);
                return ResultDtoFactory.toNack("新增版本控制信息失败");
            }

        }

        try {
            appVersionControlService.normalReleaseUpdate(nextRelease, appVersionControlLogDto);
            String userId = SecurityContext.getCurrentUserId();
            concatAppOpLog(userId, nextRelease, currentRelease, false);

            //更新缓存
            appVersionControlService.updateCache(req.getAppSource().getCode(), req.getOperatingSystem().getCode(), false);
            return ResultDtoFactory.toAck("修改成功");
        } catch (BizServiceException e) {
            return ResultDtoFactory.toNack(e.getError());
        }

    }

    /**
     * 分页查询APP操作日志
     * @param searchDto
     * @return
     */
    @GetMapping(value = "/getAppOpLogPage")
    public ResultDto<Object> getAppOpLogPage(AppOpLogSearchDto searchDto){
        searchDto = appVersionControlService.getAppOpLogPage(searchDto);
        return ResultDtoFactory.toAck("查询成功", searchDto);
    }

    /**
     * 获取APP详情
     * @param appSource
     * @param operatingSystem
     * @return
     */
    @GetMapping("getApp")
    public ResultDto getApp(String appSource, String operatingSystem){
        AppVersionControlDto app = appVersionControlService.getApp(appSource, operatingSystem);
        return ResultDtoFactory.toAckData(app);
    }

    /**
     * 修改当前APP的version name
     * @param appVersion
     * @return
     */
    @PostMapping("updateVersionName")
    public ResultDto updateVersionName(@RequestBody @Valid AppVersionDto appVersion, BindingResult result){

        if (result.hasErrors()){
            return ResultDtoFactory.toNack(result.getFieldError().getDefaultMessage());
        }

        AppVersionControlDto oldOne = appVersionControlService.getApp(appVersion.getAppSource(), appVersion.getOperatingSystem());

        if (!oldOne.getNormalRelease()){
            return ResultDtoFactory.toNack("撤回版本不支持修改版本号等信息");
        }
        if (appVersion.getVersionName().equals(oldOne.getVersionName()) && appVersion.getUpdateTip().equals(oldOne.getUpdateTip())){
            return ResultDtoFactory.toAck("没有修改，不保存");
        }

        //版本号校验
        Integer compareResult =
                appVersionControlService.compareAppVersionName(appVersion.getAppSource(), appVersion.getOperatingSystem(), appVersion.getVersionName(), false);
        if (compareResult != 1){
            return ResultDtoFactory.toNack("修改的版本号不能低于或等于上一个正式版本号！");
        }

        AppVersionControlDto newOne =
                appVersionControlService.updateAppVersionName(appVersion.getAppSource(), appVersion.getOperatingSystem(),
                        appVersion.getVersionName(), appVersion.getUpdateTip());

        //更新缓存
        appVersionControlService.updateCache(appVersion.getAppSource(), appVersion.getOperatingSystem(), true);

        //记日志
        String userId = SecurityContext.getCurrentUserId();
        concatAppOpLog(userId, newOne, oldOne, false);

        return ResultDtoFactory.toAck("更新成功");
    }

    /**
     * 撤回发布版本
     * @param appRollingBackDto
     * @param result
     * @return
     */
    @PostMapping("rollingBackPreviousVersion")
    public ResultDto rollingBackPreviousVersion(@RequestBody @Valid AppRollingBackDto appRollingBackDto, BindingResult result){
        if(result.hasErrors()){
            return ResultDtoFactory.toNack(result.getFieldError().getDefaultMessage());
        }

        //记日志用
        AppVersionControlDto thePreviousRelease = appVersionControlService.getThePreviousRelease(appRollingBackDto.getAppSource(), appRollingBackDto.getOperatingSystem());
        AppVersionControlDto theCurrentRelease = appVersionControlService.getTheCurrentRelease(appRollingBackDto.getAppSource(), appRollingBackDto.getOperatingSystem());

        appVersionControlService.rollingBackPreviousVersion(appRollingBackDto.getAppSource(), appRollingBackDto.getOperatingSystem());

        //更新缓存
        appVersionControlService.updateCache(appRollingBackDto.getAppSource(), appRollingBackDto.getOperatingSystem(), true);

        //记日志
        String userId = SecurityContext.getCurrentUserId();
        concatAppOpLog(userId, thePreviousRelease, theCurrentRelease, false);

        return ResultDtoFactory.toAck("撤回成功");
    }

    /**
     * 获取历史版本信息列表
     * @param searchDto
     * @return
     */
    @GetMapping("getHistoryRelease")
    public ResultDto getHistoryRelease(AppVerCtrlLogSearchDto searchDto){
        AppVerCtrlLogSearchDto ret = appVersionControlService.getHistoryRelease(searchDto);
        return ResultDtoFactory.toAckData(ret);
    }

    private void concatAppOpLog(String userId, AppVersionControlDto newDto, AppVersionControlDto oldDto, Boolean add){
        MgtUserDto userDto = mgtUserService.getMgtUserInf(userId);

        StringBuilder opLog = new StringBuilder();
        if (add){
            String appForceUpdate = newDto.getAppForceUpdate()?"是":"否";

            opLog.append("设置版本名为“")
                    .append(newDto.getVersionName())
                    .append("”, 版本码为“")
                    .append(newDto.getVersionCode())
                    .append("”, 强制更新为“")
                    .append(appForceUpdate)
                    .append("”");
        }else {
            String oldAppForceUpdate = oldDto.getAppForceUpdate()?"是":"否";
            String newAppForceUpdate = newDto.getAppForceUpdate()?"是":"否";

            opLog.append("修改版本名“")
                    .append(oldDto.getVersionName())
                    .append("”为“")
                    .append(newDto.getVersionName())
                    .append("”, 版本码“")
                    .append(oldDto.getVersionCode())
                    .append("”为“")
                    .append(newDto.getVersionCode())
                    .append("”, 强制更新“")
                    .append(oldAppForceUpdate)
                    .append("”为“")
                    .append(newAppForceUpdate)
                    .append("”");
        }

        AppOpLogDto logDto = new AppOpLogDto();
        logDto.setAppId(oldDto.getId());
        logDto.setOperator(userDto.getName());
        logDto.setUpdateTime(new Date());
        logDto.setOpLog(opLog.toString());
        logDto.setVersionName(newDto.getVersionName());
        logDto.setVersionCode(newDto.getVersionCode());
        logDto.setAppForceUpdate(newDto.getAppForceUpdate());
        appVersionControlService.addAppOpLog(logDto);
    }

}
