package com.xinyunlian.jinfu.ad.controller;

import com.xinyunlian.jinfu.ad.dto.*;
import com.xinyunlian.jinfu.ad.enums.EAdStatus;
import com.xinyunlian.jinfu.ad.service.AdPosService;
import com.xinyunlian.jinfu.ad.service.AdService;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.fileStore.FileStoreService;
import com.xinyunlian.jinfu.common.fileStore.enums.EFileType;
import com.xinyunlian.jinfu.common.util.ImageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by DongFC on 2016-08-24.
 */
@RestController
@RequestMapping("ad")
public class AdController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdController.class);

    @Autowired
    private AdService adService;

    @Autowired
    private FileStoreService fileStoreService;

    @Autowired
    private AdPosService adPosService;


    /**
     * 分页查询广告
     * @param adInfSearchDto
     * @return
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ResultDto<AdInfSearchDto> getAdInfPage(@ApiIgnore AdInfSearchDto adInfSearchDto){
        AdInfSearchDto page = adService.getAdInfPage(adInfSearchDto);
        return ResultDtoFactory.toAck("获取成功", page);
    }

    /**
     * 新增广告
     * @param files
     * @param adInfWebDto
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultDto<String> addAdInf(@RequestParam("file[]") MultipartFile[] files, @Valid @ApiIgnore AdInfWebDto adInfWebDto, BindingResult result){

        if (result.hasErrors()){
            return ResultDtoFactory.toNack(result.getFieldError().getDefaultMessage());
        }

        AdPositionDto adPositionDto = adPosService.getAdPosById(adInfWebDto.getAdPosId());
        if (adPositionDto == null){
            return ResultDtoFactory.toNack("广告位不存在");
        }
        List<AdInfDto> adInfDtoList = adService.getAdInfByPosId(adInfWebDto.getAdPosId());
        if (!CollectionUtils.isEmpty(adInfDtoList) && adPositionDto.getAdNum() == adInfDtoList.size()){
            return ResultDtoFactory.toNack("广告位下配置的广告已满");
        }

        Calendar cal = Calendar.getInstance();
        if (adInfWebDto.getStartDate() == null){
            adInfWebDto.setStartDate(cal.getTime());
        }
        if (adInfWebDto.getEndDate() == null){
            cal.add(Calendar.YEAR, 999);
            adInfWebDto.setEndDate(cal.getTime());
        }
        AdInfDto adInfDto = ConverterService.convert(adInfWebDto, AdInfDto.class);
        if (files != null && files.length > 0){
            try {
                List<AdPicDto> adPicDtoList = new ArrayList<>();
                for (int i = 0;i < files.length; i++){
                    MultipartFile file = files[i];
                    if (!StringUtils.isEmpty(file.getOriginalFilename())){
                        String picPath =
                                fileStoreService.upload(EFileType.AD_INFO_IMG, file.getInputStream(), file.getOriginalFilename());
                        AdPicDto adPicDto = new AdPicDto();
                        adPicDto.setPicPath(picPath);
                        adPicDto.setAdPosSizeId(adInfWebDto.getAdPosSizeIdList().get(i));
                        Map<String, Integer> resolutionMap = ImageUtils.getResolution(file.getInputStream());
                        adPicDto.setPicWidth(resolutionMap.get("width"));
                        adPicDto.setPicHeight(resolutionMap.get("height"));
                        adPicDtoList.add(adPicDto);
                    }
                }
                adInfDto.setAdPicListAdd(adPicDtoList);
            } catch (Exception e) {
                LOGGER.error("广告图片上传失败", e);
                return ResultDtoFactory.toNack("新增广告失败");
            }
        }
        try {
            adService.saveAdInf(adInfDto);
        } catch (BizServiceException e) {
            LOGGER.error("新增广告失败", e);
            return ResultDtoFactory.toNack(e.getError());
        }

        return ResultDtoFactory.toAck("新增广告成功");
    }

    /**
     * 根据id查询广告
     * @param adId
     * @return
     */
    @RequestMapping(value = "/{adId}", method = RequestMethod.GET)
    public ResultDto<AdInfDto> getAdInfById(@PathVariable Long adId){
        AdInfDto dto = adService.getAdInfById(adId);
        if (dto == null){
            return ResultDtoFactory.toNack("广告不存在");
        }
        return ResultDtoFactory.toAck("获取成功", dto);
    }


    /**
     * 更新广告
     * @param adInfWebDto
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultDto<String> updateAdInf(@RequestParam("file[]") MultipartFile[] files, @ApiIgnore AdInfWebDto adInfWebDto){
        AdInfDto adInfDto = new AdInfDto();

        if (files != null && files.length > 0){
            List<AdPicDto> adPicListUpdate = new ArrayList<>();
            for (int i = 0; i < files.length; i++){
                MultipartFile file = files[i];
                if (!StringUtils.isEmpty(file.getOriginalFilename())){
                    try {
                        String picPath =
                                fileStoreService.upload(EFileType.AD_INFO_IMG, file.getInputStream(), file.getOriginalFilename());
                        AdPicDto adPicDto = new AdPicDto();
                        adPicDto.setPicPath(picPath);
                        adPicDto.setAdId(adInfWebDto.getAdId());
                        adPicDto.setAdPosSizeId(adInfWebDto.getAdPosSizeIdList().get(i));
                        Map<String, Integer> resolutionMap = ImageUtils.getResolution(file.getInputStream());
                        adPicDto.setPicWidth(resolutionMap.get("width"));
                        adPicDto.setPicHeight(resolutionMap.get("height"));

                        adPicListUpdate.add(adPicDto);
                    } catch (Exception e) {
                        LOGGER.error("广告图片上传失败", e);
                        return ResultDtoFactory.toNack("新增广告失败");
                    }
                }
            }

            adInfDto = ConverterService.convert(adInfWebDto, adInfDto);
            Calendar cal = Calendar.getInstance();
            if (adInfWebDto.getStartDate() == null){
                adInfDto.setStartDate(cal.getTime());
            }
            if (adInfWebDto.getEndDate() == null){
                cal.add(Calendar.YEAR, 999);
                adInfDto.setEndDate(cal.getTime());
            }
            adInfDto.setAdPositionDto(adInfWebDto.getAdPositionDto());
            adInfDto.setAdPicListUpdate(adPicListUpdate);
            adInfDto.setAdStatus(EAdStatus.NORMAL);
        }
        adService.updateAdInf(adInfDto);
        return ResultDtoFactory.toAck("更新广告成功");
    }

    /**
     * 批量删除广告
     * @param adIds
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResultDto<Object> deleteBatch(@RequestBody Long[] adIds){
        if (adIds == null || adIds.length == 0){
            return ResultDtoFactory.toNack("待删除广告不能为空");
        }

        List<Long> adIdList = CollectionUtils.arrayToList(adIds);
        try {
            adService.deleteAdInfBatch(adIdList);
            return ResultDtoFactory.toAck("广告删除成功");
        } catch (BizServiceException e) {
            LOGGER.error("广告删除失败", e);
            return ResultDtoFactory.toNack(e.getError());
        }
    }

    /**
     * 获取广告预览信息
     * @param adId
     * @return
     */
    @RequestMapping(value = "previewAd", method = RequestMethod.GET)
    public ResultDto<Object> previewAd(Long adId){
        AdInfDto adInfDto = adService.getAdInfById(adId);
        if (adInfDto == null){
            return ResultDtoFactory.toNack("广告信息不存在");
        }
        AdPositionDto adPositionDto = adPosService.getAdPosById(adInfDto.getAdPosId());
        AdPicDto adPicDto = adService.getMaxSizePic(adId);
        if (adPositionDto == null || adPicDto == null){
            return ResultDtoFactory.toNack("广告位或广告图片没配置");
        }
        AdPreviewDto adPreviewDto = new AdPreviewDto();
        adPreviewDto.setAdPositionDto(adPositionDto);
        adPreviewDto.setAdPicDto(adPicDto);
        return ResultDtoFactory.toAck("查询成功", adPreviewDto);
    }

}
