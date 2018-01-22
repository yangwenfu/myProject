package com.xinyunlian.jinfu.push.service;

import com.xinyunlian.jinfu.push.dto.*;
import com.xinyunlian.jinfu.push.enums.EPushObject;
import com.xinyunlian.jinfu.push.enums.EPushPlatform;

import java.util.List;

/**
 * Created by apple on 2017/1/3.
 */
public interface PushService {

    /**
     * 新建推送
     */
    void createPushMessage(PushMessageCreateDto pushMessageDto,List<String> userIds);

    /**
     * 获取分页列表-手机端
     */
    PushMessagePageListDto getPushlistByUserId(PushRequestDto pushSearchDto,int pushObject) ;

    /**
     * 获取分页列表-web
     */
    PushMessageListDto getPushlistForWebByUserId(PushSearchDto pushSearchDto);

    /**
     * 获取用户未读条数,1小伙伴 2掌柜
     */
    Long getunreadMessageCountByUserId(String userId,int pushObject);

    /**
     * 推送已读标记
     */
    void readMessage(Long messageId , String userId);

    /**
     * 删除推送
     */
    void deleteMessage(Long messageId);

    /**
     * 单条推送详情
     */
    PushMessageDto detail(Long messageId);

    void updateRegistrationId(PushDeviceDto dto,int pushObject);

    void unBindRegistrationId(String userId,int pushObject);
    /**
     * 批量发送逻辑
     */
    void pushJob();

    /**
     * 补偿发送逻辑
     */
    void compensatingPushJob(int pushObject);

    void pushAPP(String content ,List<String> pushToken);

    /**
     * 推送消息到APP
     */
    void pushMessageToApp(String userId, String message, EPushObject pushObject, EPushPlatform platform);
}
