package com.xinyunlian.jinfu.system.controller;

import com.xinyunlian.jinfu.app.enums.EDataType;
import com.xinyunlian.jinfu.app.service.DataVersionControlService;
import com.xinyunlian.jinfu.area.dto.SysAreaInfDto;
import com.xinyunlian.jinfu.area.enums.ESysAreaLevel;
import com.xinyunlian.jinfu.area.service.SysAreaInfService;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.fileStore.FileStoreService;
import com.xinyunlian.jinfu.common.fileStore.enums.EFileType;
import com.xinyunlian.jinfu.common.security.StaticResourceSecurity;
import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.config.dto.ConfigDto;
import com.xinyunlian.jinfu.config.enums.ECategory;
import com.xinyunlian.jinfu.config.service.ConfigService;
import com.xinyunlian.jinfu.spiderproxy.service.ProxyRefreshService;
import com.xinyunlian.jinfu.system.dto.AreaDto;
import com.xinyunlian.jinfu.system.dto.CityDto;
import com.xinyunlian.jinfu.system.dto.ProvinceDto;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

/**
 * Created by menglei on 2016年08月30日.
 */
@RestController
@RequestMapping(value = "system")
public class SystemController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SystemController.class);

    @Autowired
    private SysAreaInfService sysAreaInfService;
    @Autowired
    private FileStoreService fileStoreService;
    @Autowired
    private DataVersionControlService dataVersionControlService;

    @Autowired
    private ProxyRefreshService proxyRefreshService;

    @Autowired
    private ConfigService configService;

    private String fileAddr = AppConfigUtil.getConfig("file.addr");

    /**
     * 查询所有区域(app json文件使用)
     *
     * @return
     */
    @RequestMapping(value = "/area/jsonList", method = RequestMethod.GET)
    public ResultDto<List<ProvinceDto>> getJsonList() {
        List<SysAreaInfDto> provinceList = sysAreaInfService.getSysAreaInfByLevel(ESysAreaLevel.PROVINCE);
        List<SysAreaInfDto> cityList = sysAreaInfService.getSysAreaInfByLevel(ESysAreaLevel.CITY);
        List<SysAreaInfDto> areaList = sysAreaInfService.getSysAreaInfByLevel(ESysAreaLevel.COUNTY);
        //区域处理
        Map<Long, List<AreaDto>> areaMap = new HashMap<>();
        AreaDto areaDto;
        List<AreaDto> areaDtoList;
        for (SysAreaInfDto area : areaList) {
            areaDto = new AreaDto();
            areaDtoList = new ArrayList<>();
            if (areaMap.get(area.getParent()) != null) {
                areaDtoList = areaMap.get(area.getParent());
            }
            areaDto = ConverterService.convert(area, AreaDto.class);
            areaDtoList.add(areaDto);
            areaMap.put(area.getParent(), areaDtoList);
        }
        //城市处理
        Map<Long, List<CityDto>> cityMap = new HashMap<>();
        CityDto cityDto;
        List<CityDto> cityDtoList;
        for (SysAreaInfDto city : cityList) {
            cityDto = new CityDto();
            cityDtoList = new ArrayList<>();
            if (cityMap.get(city.getParent()) != null) {
                cityDtoList = cityMap.get(city.getParent());
            }
            cityDto = ConverterService.convert(city, CityDto.class);
            if (areaMap.get(city.getId()) != null) {
                cityDto.setAreaList(areaMap.get(city.getId()));
            }
            cityDtoList.add(cityDto);
            cityMap.put(city.getParent(), cityDtoList);
        }
        List<ProvinceDto> provinceDtoList = new ArrayList<>();
        for (SysAreaInfDto province : provinceList) {
            ProvinceDto po = ConverterService.convert(province, ProvinceDto.class);
            if (cityMap.get(po.getId()) != null) {
                po.setCityList(cityMap.get(po.getId()));
            }
            provinceDtoList.add(po);
        }
        return ResultDtoFactory.toAck("获取成功", provinceDtoList);
    }

    /**
     * 上传date文件(json文件 上传)
     *
     * @param dateFile
     * @return
     */
    @RequestMapping(value = "/uploadDataFile", method = RequestMethod.POST)
    public ResultDto<Object> uploadDataFile(@RequestParam String type, @RequestParam MultipartFile dateFile) {
        if (StringUtils.isEmpty(type)) {
            return ResultDtoFactory.toNack("请选择上传类型");
        }
        if (dateFile == null){
            return ResultDtoFactory.toNack("请选择上传文件");
        }
        String dataPath = StringUtils.EMPTY;
        try {
            if (type.equals("JSON")) {
                dataPath = fileStoreService.upload(EFileType.DATA_FILE_PATH, dateFile.getInputStream(), dateFile.getOriginalFilename().replace(" ", ""));
                dataVersionControlService.updateDataPath(dataPath, EDataType.AREA);
            } else {
                return ResultDtoFactory.toNack("请选择上传类型");
            }
        } catch (IOException e) {
            LOGGER.error("文件上传失败", e);
            return ResultDtoFactory.toNack("文件上传失败");
        }
        return ResultDtoFactory.toAck("上传成功", dataPath);
    }

    @RequestMapping(value = "refreshProxy", method = RequestMethod.POST)
    public ResultDto refreshProxy(){
        String ip = proxyRefreshService.getNewProxyIp();
        ConfigDto proxyIpConfig = configService.getByCategoryAndKey(ECategory.SPIDER, "ProxyIp");
        if(Objects.isNull(proxyIpConfig)){
            proxyIpConfig = new ConfigDto();
            proxyIpConfig.setKey("ProxyIp");
            proxyIpConfig.setCached(true);
            proxyIpConfig.setCategory(ECategory.SPIDER);
        }
        proxyIpConfig.setValue(ip);
        configService.save(proxyIpConfig);
        return ResultDtoFactory.toAck("操作成功");
    }

    /**
     * 统一批量上传文件入口
     * @param files
     * @param fileType
     * @return
     */
    @RequestMapping(value = "uploadBatch", method = RequestMethod.POST)
    public ResultDto uploadBatch(@RequestParam("file[]") MultipartFile[] files, EFileType fileType){
        if (files != null && files.length > 0){
            List<Map<String, String>> filePathList = new ArrayList<>();
            for (int i = 0; i < files.length;i++){
                try {
                    String filePath =
                            fileStoreService.upload(fileType, files[i].getInputStream(), files[i].getOriginalFilename());
                    Map<String, String> filePathMap = new HashMap<>();
                    filePathMap.put("absolutePath", fileAddr + StaticResourceSecurity.getSecurityURI(filePath));
                    filePathMap.put("relativePath", filePath);
                    filePathList.add(filePathMap);
                } catch (Exception e) {
                    LOGGER.error("上传失败", e);
                    return ResultDtoFactory.toNack("上传失败");
                }
            }
            return ResultDtoFactory.toAck("上传成功", filePathList);
        }
        return ResultDtoFactory.toAck("上传文件不能为空");
    }

    /**
     * 统一单个上传文件入口
     * @param file
     * @param fileType
     * @return
     */
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public ResultDto upload(@RequestParam("file") MultipartFile file, EFileType fileType){
        try {
            if (file != null){
                String filePath =
                        fileStoreService.upload(fileType, file.getInputStream(), file.getOriginalFilename());

                Map<String, String> filePathMap = new HashMap<>();
                filePathMap.put("absolutePath", fileAddr + StaticResourceSecurity.getSecurityURI(filePath));
                filePathMap.put("relativePath", filePath);
                return ResultDtoFactory.toAck("上传成功", filePathMap);
            }
        } catch (Exception e) {
            LOGGER.error("上传失败", e);
            return ResultDtoFactory.toNack("上传失败");
        }
        return ResultDtoFactory.toNack("上传文件不能为空");
    }

}
