package com.xinyunlian.jinfu.app.service;

import com.xinyunlian.jinfu.app.dao.AppOpLogDao;
import com.xinyunlian.jinfu.app.dao.AppVersionControlDao;
import com.xinyunlian.jinfu.app.dao.AppVersionControlLogDao;
import com.xinyunlian.jinfu.app.dto.*;
import com.xinyunlian.jinfu.app.entity.AppOpLogPo;
import com.xinyunlian.jinfu.app.entity.AppVersionControlLogPo;
import com.xinyunlian.jinfu.app.entity.AppVersionControlPo;
import com.xinyunlian.jinfu.app.enums.EAppSource;
import com.xinyunlian.jinfu.app.enums.EOperatingSystem;
import com.xinyunlian.jinfu.common.cache.constant.CacheType;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.fileStore.FileStoreService;
import com.xinyunlian.jinfu.common.fileStore.enums.EFileType;
import com.xinyunlian.jinfu.common.security.StaticResourceSecurity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.io.IOException;
import java.util.*;

/**
 * Created by DongFC on 2016-10-08.
 */
@Service
public class AppVersionControlServiceImpl implements AppVersionControlService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppVersionControlServiceImpl.class);

    @Autowired
    private AppVersionControlDao appVersionControlDao;

    @Autowired
    private AppOpLogDao appOpLogDao;

    @Value("${file.addr}")
    private String fileAddr;

    @Autowired
    private RedisCacheManager redisCacheManager;

    @Autowired
    private AppVersionControlLogDao appVersionControlLogDao;

    @Autowired
    private FileStoreService fileStoreService;

    @Override
    public AppVersionControlDto getLatestAppInfo(EAppSource appSource, EOperatingSystem operatingSystem) {
        return getLatestOne(appSource.getCode(), operatingSystem.getCode());
    }

    @Override
    public AppVerCtrlSearchDto getAppVerCtrlPage(AppVerCtrlSearchDto searchDto) throws BizServiceException {
        Specification<AppVersionControlPo> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (searchDto != null) {
                if (searchDto.getAppSource() != null) {
                    expressions.add(cb.equal(root.get("appSource"), searchDto.getAppSource()));
                }
            }
            return predicate;
        };

        Pageable pageable = new PageRequest(searchDto.getCurrentPage() - 1, searchDto.getPageSize());
        Page<AppVersionControlPo> page = appVersionControlDao.findAll(spec, pageable);

        List<AppVersionControlDto> data = new ArrayList<>();
        page.getContent().forEach(po -> {
            AppVersionControlDto dto = ConverterService.convert(po, AppVersionControlDto.class);
            if (dto.getOperatingSystem() != EOperatingSystem.IOS){
                dto.setAppPath(fileAddr + StaticResourceSecurity.getSecurityURI(dto.getAppPath()));
            }
            data.add(dto);
        });

        searchDto.setList(data);
        searchDto.setTotalPages(page.getTotalPages());
        searchDto.setTotalRecord(page.getTotalElements());

        return searchDto;
    }

    @Override
    @Transactional
    public void addAppVerCtrl(AppVersionControlDto dto) throws BizServiceException{
        AppVersionControlPo po = ConverterService.convert(dto, AppVersionControlPo.class);

        AppVersionControlPo existsPo = appVersionControlDao.findByAppSourceAndOperatingSystem(dto.getAppSource().getCode(), dto.getOperatingSystem().getCode());
        if (existsPo == null){
            appVersionControlDao.save(po);
        }else {
            throw new BizServiceException(EErrorCode.APP_INFO_EXISTS);
        }
    }

    @Override
    @Transactional
    public void normalReleaseUpdate(AppVersionControlDto nextRelease, AppVersionControlLogDto appVersionControlLogDto) throws BizServiceException {
        try {
            AppVersionControlPo currentRelease =
                    appVersionControlDao.findByAppSourceAndOperatingSystem(nextRelease.getAppSource().getCode(), nextRelease.getOperatingSystem().getCode());
            if (currentRelease != null){

                //修改历史版本信息
                AppVersionControlLogPo oldLog =
                        appVersionControlLogDao.findByAppSourceAndOperatingSystemAndVersionName(appVersionControlLogDto.getAppSource().getCode(),
                                appVersionControlLogDto.getOperatingSystem().getCode(), appVersionControlLogDto.getVersionName());
                if (oldLog != null){
                    oldLog.setAppPath(appVersionControlLogDto.getAppPath());
                }
                AppVersionControlLogPo nextAppVersionControlLogPo = ConverterService.convert(nextRelease, AppVersionControlLogPo.class);
                appVersionControlLogDao.save(nextAppVersionControlLogPo);

                //保存最新APP信息
                ConverterService.convert(nextRelease, currentRelease);
            }
        } catch (Exception e) {
            LOGGER.error("更新app版本信息失败", e);
            throw new BizServiceException(EErrorCode.APP_INFO_UPDATE_FAILED);
        }
    }

    @Override
    public void addAppOpLog(AppOpLogDto dto) throws BizServiceException {
        AppOpLogPo po = ConverterService.convert(dto, AppOpLogPo.class);
        appOpLogDao.save(po);
    }

    @Override
    public AppOpLogSearchDto getAppOpLogPage(AppOpLogSearchDto searchDto) throws BizServiceException {
        Specification<AppOpLogPo> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (searchDto != null) {
                if (searchDto.getAppId() != null) {
                    expressions.add(cb.equal(root.get("appId"), searchDto.getAppId()));
                }
            }
            return predicate;
        };

        Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
        Pageable pageable = new PageRequest(searchDto.getCurrentPage() - 1, searchDto.getPageSize(), sort);
        Page<AppOpLogPo> page = appOpLogDao.findAll(spec, pageable);

        List<AppOpLogDto> data = new ArrayList<>();
        page.getContent().forEach(po -> {
            AppOpLogDto dto = ConverterService.convert(po, AppOpLogDto.class);
            data.add(dto);
        });

        searchDto.setList(data);
        searchDto.setTotalPages(page.getTotalPages());
        searchDto.setTotalRecord(page.getTotalElements());

        return searchDto;
    }

    @Override
    public AppVersionControlDto getApp(String appSource, String operatingSystem) throws BizServiceException {
        AppVersionControlPo po = appVersionControlDao.findByAppSourceAndOperatingSystem(appSource, operatingSystem);
        AppVersionControlDto dto = ConverterService.convert(po, AppVersionControlDto.class);
        return dto;
    }

    @Override
    public AppVersionControlDto checkAppVersion(String appSource, String operatingSystem, String versionName) throws BizServiceException {

        AppVersionControlDto currentReleaseDto = getLatestOne(appSource, operatingSystem);
        if (currentReleaseDto == null){
            throw new BizServiceException(EErrorCode.APP_INFO_NOT_EXISTS);
        }else {
            Map<String, Boolean> cacheMap = redisCacheManager.getCache(CacheType.APP_VERSION_INFO).get(appSource + "-" + operatingSystem, Map.class);
            if (CollectionUtils.isEmpty(cacheMap)){
                cacheMap = new HashMap<>();
                //查找是否有新的APP更新记录
                Boolean needUpdate = needUpdate(appSource, operatingSystem, versionName, cacheMap, currentReleaseDto);
                if (needUpdate){
                    return currentReleaseDto;
                }
            }else {
                if (cacheMap.containsKey(versionName)){
                    if (cacheMap.get(versionName)){
                        return currentReleaseDto;
                    }
                }else {
                    //查找是否有新的APP更新记录
                    Boolean needUpdate = needUpdate(appSource, operatingSystem, versionName, cacheMap, currentReleaseDto);
                    if (needUpdate){
                        return currentReleaseDto;
                    }
                }
            }
            redisCacheManager.getCache(CacheType.APP_VERSION_INFO).put(appSource + "-" + operatingSystem, cacheMap);
        }

        return null;
    }

    @Override
    public Integer compareAppVersionName(String appSource, String operatingSystem, String versionName, Boolean newRelease) throws BizServiceException {
        AppVersionControlPo app = appVersionControlDao.findByAppSourceAndOperatingSystem(appSource, operatingSystem);
        if (app == null){
            throw new BizServiceException(EErrorCode.APP_INFO_NOT_EXISTS);
        }

        AppVersionControlLogPo releaseApp = null;
        if (newRelease){
            releaseApp = appVersionControlLogDao.findTheCurrentRelease(appSource, operatingSystem);
        }else {
            releaseApp = appVersionControlLogDao.findThePreviousRelease(appSource, operatingSystem);
        }
        if (releaseApp == null){
            return 1;
        }else {
            return compareVersionName(versionName, releaseApp.getVersionName());
        }
    }

    @Override
    @Transactional
    public AppVersionControlDto updateAppVersionName(String appSource, String operatingSystem, String versionName, String updateTip) throws BizServiceException {
        AppVersionControlPo app = appVersionControlDao.findByAppSourceAndOperatingSystem(appSource, operatingSystem);
        if (app == null){
            throw new BizServiceException(EErrorCode.APP_INFO_NOT_EXISTS);
        }

        AppVersionControlLogPo currentLog =
                appVersionControlLogDao.findByAppSourceAndOperatingSystemAndVersionName(appSource, operatingSystem, app.getVersionName());

        app.setVersionName(versionName);
        app.setUpdateTip(updateTip);
        if (currentLog == null){
            AppVersionControlLogPo controlLogPo = ConverterService.convert(app, AppVersionControlLogPo.class);
            appVersionControlLogDao.save(controlLogPo);
        }else {
            currentLog.setVersionName(versionName);
            currentLog.setUpdateTip(updateTip);
        }

        return ConverterService.convert(app, AppVersionControlDto.class);
    }

    @Override
    @Transactional
    public void rollingBackPreviousVersion(String appSource, String operatingSystem) throws BizServiceException {
        try {
            AppVersionControlPo app = appVersionControlDao.findByAppSourceAndOperatingSystem(appSource, operatingSystem);
            if (app == null){
                throw new BizServiceException(EErrorCode.APP_INFO_NOT_EXISTS);
            }
            if (!app.getNormalRelease()){
                throw new BizServiceException(EErrorCode.APP_NOT_SUPPORT_ROLLING_BACK);
            }

            AppVersionControlLogPo thePreviousRelease = appVersionControlLogDao.findThePreviousRelease(appSource, operatingSystem);
            if (thePreviousRelease == null){
                throw new BizServiceException(EErrorCode.APP_NO_AVAILABLE_VERSION_ROLLING_BACK);
            }
            String deleteVersionName = app.getVersionName();
            Date releaseTime = Calendar.getInstance().getTime();

            app.setNormalRelease(false);
            app.setVersionName(thePreviousRelease.getVersionName());
            app.setVersionCode(thePreviousRelease.getVersionCode());
            app.setUpdateTip(thePreviousRelease.getUpdateTip());
            app.setAppForceUpdate(thePreviousRelease.getAppForceUpdate());
            app.setReleaseTime(releaseTime);
            String appPath = thePreviousRelease.getAppPath();

            if (!appPath.startsWith("http")){
                String suffix = app.getAppPath().substring(app.getAppPath().lastIndexOf("."), app.getAppPath().length());
                String appPackName = app.getAppSource().getApp() + suffix;
                //先删除老版本的APP包
                fileStoreService.delete(EFileType.APP_FILE_PATH.getPath(), appPackName);
                //再将上一个版本回滚
                String oldAppPackName = appPath.substring(appPath.lastIndexOf("/") + 1, appPath.length());
                fileStoreService.renameFile(EFileType.APP_FILE_PATH.getPath(), oldAppPackName, appPackName);

                appPath = appPath.substring(0, appPath.lastIndexOf("/") + 1) + appPackName;
            }

            app.setAppPath(appPath);
            thePreviousRelease.setAppPath(appPath);
            thePreviousRelease.setReleaseTime(releaseTime);
            appVersionControlLogDao.delete(appSource, operatingSystem, deleteVersionName);

        } catch (IOException e) {
            LOGGER.error("APP版本撤回失败", e);
            throw new BizServiceException(EErrorCode.APP_ROLLING_BACK_FAILED);
        }
    }

    @Override
    public AppVersionControlDto getThePreviousRelease(String appSource, String operatingSystem) throws BizServiceException {
        AppVersionControlLogPo thePreviousRelease = appVersionControlLogDao.findThePreviousRelease(appSource, operatingSystem);
        if (thePreviousRelease == null){
            throw new BizServiceException(EErrorCode.APP_NO_AVAILABLE_VERSION_ROLLING_BACK);
        }
        return ConverterService.convert(thePreviousRelease, AppVersionControlDto.class);
    }

    @Override
    public AppVersionControlDto getTheCurrentRelease(String appSource, String operatingSystem) throws BizServiceException {
        AppVersionControlPo appVersionControlPo = appVersionControlDao.findByAppSourceAndOperatingSystem(appSource, operatingSystem);
        return ConverterService.convert(appVersionControlPo, AppVersionControlDto.class);
    }

    @Override
    public AppVerCtrlLogSearchDto getHistoryRelease(AppVerCtrlLogSearchDto searchDto) throws BizServiceException {

        Specification<AppVersionControlLogPo> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (searchDto != null) {
                if (searchDto.getAppSource() != null) {
                    expressions.add(cb.equal(root.get("appSource"), searchDto.getAppSource()));
                }
                if (searchDto.getOperatingSystem() != null) {
                    expressions.add(cb.equal(root.get("operatingSystem"), searchDto.getOperatingSystem()));
                }
            }
            return predicate;
        };

        Sort sort = new Sort(Sort.Direction.DESC, "appLogId");
        Pageable pageable = new PageRequest(searchDto.getCurrentPage() - 1, searchDto.getPageSize(), sort);
        Page<AppVersionControlLogPo> page = appVersionControlLogDao.findAll(spec, pageable);

        List<AppVersionControlLogDto> data = new ArrayList<>();
        page.getContent().forEach(po -> {
            AppVersionControlLogDto dto = ConverterService.convert(po, AppVersionControlLogDto.class);
            if (dto.getOperatingSystem() != EOperatingSystem.IOS){
                dto.setAppPath(fileAddr + StaticResourceSecurity.getSecurityURI(dto.getAppPath()));
            }
            data.add(dto);
        });

        searchDto.setList(data);
        searchDto.setTotalPages(page.getTotalPages());
        searchDto.setTotalRecord(page.getTotalElements());

        return searchDto;
    }

    @Override
    public void updateCache(String appSource, String operatingSystem, Boolean versionChanged) throws BizServiceException {
        AppVersionControlPo app = appVersionControlDao.findByAppSourceAndOperatingSystem(appSource, operatingSystem);
        if (app == null){
            throw new BizServiceException(EErrorCode.APP_INFO_NOT_EXISTS);
        }
        String key = appSource + "-" + operatingSystem + "-" + "APP";
        AppVersionControlDto dto = ConverterService.convert(app, AppVersionControlDto.class);
        if (!StringUtils.isEmpty(dto.getAppPath())){
            if (!dto.getAppPath().startsWith("http")){
                dto.setAppPath(fileAddr + StaticResourceSecurity.getSecurityURI(dto.getAppPath()));
            }
        }
        redisCacheManager.getCache(CacheType.APP_VERSION_INFO).put(key, dto);

        if (versionChanged){
            Map<String, String> cacheMap = redisCacheManager.getCache(CacheType.APP_VERSION_INFO).get(appSource + "-" + operatingSystem, Map.class);
            if (!CollectionUtils.isEmpty(cacheMap)){
                cacheMap.clear();
                redisCacheManager.getCache(CacheType.APP_VERSION_INFO).put(appSource + "-" + operatingSystem, cacheMap);
            }
        }

    }

    private AppVersionControlDto getLatestOne(String appSource, String operationSystem){
        String key = appSource.toUpperCase() + "-" + operationSystem.toUpperCase() + "-" + "APP";
        AppVersionControlDto retDto = redisCacheManager.getCache(CacheType.APP_VERSION_INFO).get(key, AppVersionControlDto.class);
        if (retDto == null){
            AppVersionControlPo po = appVersionControlDao.findByAppSourceAndOperatingSystem(appSource.toUpperCase(), operationSystem.toUpperCase());
            retDto = ConverterService.convert(po, AppVersionControlDto.class);

            if (retDto != null){
                if (!StringUtils.isEmpty(retDto.getAppPath()) && !retDto.getAppPath().startsWith("http")){
                    retDto.setAppPath(fileAddr + StaticResourceSecurity.getSecurityURI(retDto.getAppPath()));
                }
                redisCacheManager.getCache(CacheType.APP_VERSION_INFO).put(key, retDto);
            }
        }
        return retDto;
    }


    private Boolean needUpdate(String appSource, String operatingSystem, String versionName, Map cacheMap, AppVersionControlDto currentRelease){
        AppVersionControlLogPo userApp = appVersionControlLogDao.findByAppSourceAndOperatingSystemAndVersionName(appSource, operatingSystem, versionName);
        Integer compareResult = compareVersionName(versionName, currentRelease.getVersionName());
        if (userApp == null){//用户版本不存在
            if (compareResult == 1){
                return false;
            }else if (compareResult == -1){
                cacheMap.put(versionName, true);
                return true;
            }
        }else if (userApp != null && compareResult == -1){
            List<AppVersionControlLogPo> laterForceUpdateApp =
                    appVersionControlLogDao.findLaterForceUpdateApp(appSource, operatingSystem, userApp.getAppLogId());
            if (!CollectionUtils.isEmpty(laterForceUpdateApp)){
                cacheMap.put(versionName, true);
                return true;
            }else {
                return false;
            }
        }

        return false;
    }

    private Integer compareVersionName(String self, String other){
        String[] modifyVArray = self.split("\\.");
        String[] previousVArray = other.split("\\.");
        if (modifyVArray.length == previousVArray.length){
            for (int i = 0; i < modifyVArray.length; i++){
                if (Integer.parseInt(modifyVArray[i]) != Integer.parseInt(previousVArray[i])){
                    return Integer.parseInt(modifyVArray[i]) > Integer.parseInt(previousVArray[i]) ? 1 : -1;
                }
            }
            return 0;
        }else if (modifyVArray.length > previousVArray.length){
            for (int i = 0; i < previousVArray.length; i++){
                if (Integer.parseInt(modifyVArray[i]) != Integer.parseInt(previousVArray[i])){
                    return Integer.parseInt(modifyVArray[i]) > Integer.parseInt(previousVArray[i]) ? 1 : -1;
                }
            }
            return 1;
        }else {
            for (int i = 0; i < modifyVArray.length; i++){
                if (Integer.parseInt(modifyVArray[i]) != Integer.parseInt(previousVArray[i])){
                    return Integer.parseInt(modifyVArray[i]) > Integer.parseInt(previousVArray[i]) ? 1 : -1;
                }
            }
            return -1;
        }
    }

}
