package com.xinyunlian.jinfu.app.service;

import com.xinyunlian.jinfu.app.dto.*;
import com.xinyunlian.jinfu.app.enums.EAppSource;
import com.xinyunlian.jinfu.app.enums.EOperatingSystem;
import com.xinyunlian.jinfu.common.exception.BizServiceException;

/**
 * Created by DongFC on 2016-10-08.
 */
public interface AppVersionControlService {

    /**
     * 获取最新的APP版本信息
     * @param appSource
     * @param operatingSystem
     * @return
     */
    AppVersionControlDto getLatestAppInfo(EAppSource appSource, EOperatingSystem operatingSystem);

    /**
     * 分页获取app版本控制信息
     * @param searchDto
     * @return
     * @throws BizServiceException
     */
    AppVerCtrlSearchDto getAppVerCtrlPage(AppVerCtrlSearchDto searchDto) throws BizServiceException;

    /**
     * 新增app版本控制
     * @param dto
     * @return
     * @throws BizServiceException
     */
    void addAppVerCtrl(AppVersionControlDto dto) throws BizServiceException;

    /**
     * 正常发布APP版本（新增历史版本和发布版本，并修改历史版本的APP路径）
     * @param nextRelease
     * @param appVersionControlLogDto
     * @return
     * @throws BizServiceException
     */
    void normalReleaseUpdate(AppVersionControlDto nextRelease, AppVersionControlLogDto appVersionControlLogDto) throws BizServiceException;

    /**
     * 新增操作日志
     * @param dto
     * @throws BizServiceException
     */
    void addAppOpLog(AppOpLogDto dto) throws BizServiceException;

    /**
     *
     * @param searchDto
     * @return
     * @throws BizServiceException
     */
    AppOpLogSearchDto getAppOpLogPage(AppOpLogSearchDto searchDto) throws BizServiceException;

    /**
     * 根据appSource和operatingSystem获取app信息
     * @param appSource
     * @param operatingSystem
     * @return
     * @throws BizServiceException
     */
    AppVersionControlDto getApp(String appSource, String operatingSystem) throws BizServiceException;

    /**
     * 检查app版本
     * @param appSource
     * @param operatingSystem
     * @param versionName
     * @return
     * @throws BizServiceException
     */
    AppVersionControlDto checkAppVersion(String appSource, String operatingSystem, String versionName) throws BizServiceException;

    /**
     * 比较APP的VERSION NAME
     * @param appSource
     * @param operatingSystem
     * @param versionName
     * @param newRelease
     * @return 1：入参的版本号比数据库的大，-1：入参的版本号小于数据库的版本号，0：入参的版本号等于数据库的版本号
     * @throws BizServiceException
     */
    Integer compareAppVersionName(String appSource, String operatingSystem, String versionName, Boolean newRelease) throws BizServiceException;

    /**
     * 更新APP版本号
     * @param appSource
     * @param operatingSystem
     * @param versionName
     * @param updateTip
     * @throws BizServiceException
     */
    AppVersionControlDto updateAppVersionName(String appSource, String operatingSystem, String versionName, String updateTip) throws BizServiceException;

    /**
     * 撤回发布版本
     * @param appSource
     * @param operatingSystem
     * @throws BizServiceException
     */
    void rollingBackPreviousVersion(String appSource, String operatingSystem) throws BizServiceException;

    /**
     * 查找上一个发布版本
     * @param appSource
     * @param operatingSystem
     * @return
     * @throws BizServiceException
     */
    AppVersionControlDto getThePreviousRelease(String appSource, String operatingSystem) throws BizServiceException;

    /**
     * 查看找当前线上版本
     * @param appSource
     * @param operatingSystem
     * @return
     * @throws BizServiceException
     */
    AppVersionControlDto getTheCurrentRelease(String appSource, String operatingSystem) throws BizServiceException;

    /**
     * 获取历史发布版本列表
     * @param searchDto
     * @return
     * @throws BizServiceException
     */
    AppVerCtrlLogSearchDto getHistoryRelease(AppVerCtrlLogSearchDto searchDto) throws BizServiceException;

    /**
     * 更新缓存
     * @param appSource
     * @param operatingSystem
     * @param versionChanged
     * @throws BizServiceException
     */
    void updateCache(String appSource, String operatingSystem, Boolean versionChanged) throws BizServiceException;

}
