package com.xinyunlian.jinfu.push.controller;

import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.fileStore.FileStoreService;
import com.xinyunlian.jinfu.common.fileStore.enums.EFileType;
import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.push.dto.*;
import com.xinyunlian.jinfu.push.enums.PushObject;
import com.xinyunlian.jinfu.push.service.PushService;
import com.xinyunlian.jinfu.store.service.StoreService;
import com.xinyunlian.jinfu.user.dto.DealerUserDto;
import com.xinyunlian.jinfu.user.service.DealerUserService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 2017/1/3.
 */
@RestController
@RequestMapping("push")
@RequiresPermissions({"PUSH_MGT"})
public class APPPushController {
    private static final Logger LOGGER = LoggerFactory.getLogger(APPPushController.class);
    @Autowired
    private FileStoreService fileStoreService;

    @Autowired
    private PushService pushService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private DealerUserService dealerUserService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> add(@RequestBody @Valid PushMessageCreateDto pushMessageCreateDto){
        List<String> userIds = new ArrayList<String>();
        if (pushMessageCreateDto.getPushObject() == PushObject.XHB){
            userIds = findUserFromDealer(pushMessageCreateDto.getAreas());
        }else if (pushMessageCreateDto.getPushObject() == PushObject.ZG){
            userIds = findUserFromStore(pushMessageCreateDto.getAreas());
        }else {
            return ResultDtoFactory.toNack("参数错误");
        }
        pushService.createPushMessage(pushMessageCreateDto,userIds);
        return ResultDtoFactory.toAck("推送新建成功");
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ResultDto<Object> getMessageList(PushSearchDto pushSearchDto) {
        PushMessageListDto messageDtos= pushService.getPushlistForWebByUserId(pushSearchDto);
        return ResultDtoFactory.toAck("成功",messageDtos);
    }

    @RequestMapping(value = "/pushTest", method = RequestMethod.POST)
    public ResultDto<Object> getMessageList(@RequestBody @Valid PushTestDto pushTestDto) {
        pushService.pushAPP(pushTestDto.getContent() ,pushTestDto.getPushToken());
        return ResultDtoFactory.toAck("成功");
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResultDto<Object> delete(@RequestParam Long messageId) {
        pushService.deleteMessage(messageId);
        return ResultDtoFactory.toAck("推送删除成功");
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public ResultDto<Object> detail(@RequestParam Long messageId) {
        return ResultDtoFactory.toAck("成功",pushService.detail(messageId));
    }

    /**
     * 上传图片文件
     *
     * @param imagePicFile
     * @return
     */
    @RequestMapping(value = "/uploadPic", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> uploadDataFile(@RequestParam MultipartFile imagePicFile) {

        if (imagePicFile == null){
            return ResultDtoFactory.toNack("请选择上传图片");
        }
        String dataPath = StringUtils.EMPTY;
        try {
            dataPath = fileStoreService.upload(EFileType.PUSH_IMG, imagePicFile.getInputStream(), imagePicFile.getOriginalFilename().replace(" ", ""));
        } catch (IOException e) {
            LOGGER.error("文件上传失败", e);
            return ResultDtoFactory.toNack("文件上传失败");
        }

        PushImageDto dto = new PushImageDto();
        dto.setImagePath(dataPath);
        dto.setFileAddr(AppConfigUtil.getConfig("file.addr"));
        return ResultDtoFactory.toAck("上传成功", dto);
    }

    /*
     * 查找小伙伴中符合要求的用户
     */
    private List<String> findUserFromDealer(List<PushMessageAreaDto> areaDtoList) {

        List<DealerUserDto> userDtos;
        if (CollectionUtils.isEmpty(areaDtoList)) {
            userDtos = dealerUserService.getAllDealerUsers();
        } else {
            List<String> list = new ArrayList<String>();
            for (PushMessageAreaDto dto : areaDtoList) {
                if (StringUtils.isNotEmpty(dto.getAreaId())) {
                    list.add(String.valueOf(dto.getAreaId()));
                } else if (StringUtils.isNotEmpty(dto.getCityId())) {
                    list.add(String.valueOf(dto.getCityId()));
                } else if (StringUtils.isNotEmpty(dto.getProvinceId())) {
                    list.add(String.valueOf(dto.getProvinceId()));
                }
            }
            userDtos = dealerUserService.getDealerUserIdsByAddressIDS(list);
        }

        List<String> userIds = new ArrayList<String>();
        if (userDtos != null){
            for (DealerUserDto userDto : userDtos) {
                userIds.add(userDto.getUserId());
            }
        }
        return userIds;
    }

    /*
     * 查找云联掌柜中符合要求的用户
     */
    private List<String> findUserFromStore(List<PushMessageAreaDto> areaDtoList) {
        List<String> userIds;
        if (CollectionUtils.isEmpty(areaDtoList)) {
            userIds = storeService.findAllUsers();
        } else {
            List<String> list = new ArrayList<String>();
            for (PushMessageAreaDto dto : areaDtoList) {
                if (StringUtils.isNotEmpty(dto.getAreaId())) {
                    list.add(dto.getAreaId());
                } else if (StringUtils.isNotEmpty(dto.getCityId())) {
                    list.add(dto.getCityId());
                } else if (StringUtils.isNotEmpty(dto.getProvinceId())) {
                    list.add(dto.getProvinceId());
                }
            }
            userIds = storeService.findUseridsByAddressIDs(list);
        }
        return userIds;
    }
}