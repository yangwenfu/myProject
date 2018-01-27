package com.xinyunlian.jinfu.push.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.common.util.JpushUtils;
import com.xinyunlian.jinfu.push.dao.PushAreaDao;
import com.xinyunlian.jinfu.push.dao.PushDeviceDao;
import com.xinyunlian.jinfu.push.dao.PushMessageDao;
import com.xinyunlian.jinfu.push.dao.PushReadStateDao;
import com.xinyunlian.jinfu.push.dto.*;
import com.xinyunlian.jinfu.push.entity.PushAreaPo;
import com.xinyunlian.jinfu.push.entity.PushDevicePo;
import com.xinyunlian.jinfu.push.entity.PushMessagePo;
import com.xinyunlian.jinfu.push.entity.PushReadStatePo;
import com.xinyunlian.jinfu.push.enums.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by apple on 2017/1/3.
 */
@Service
public class PushServiceImpl implements PushService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final int BATCH_SIZE = 1000;

    private static final int MAX_RETRY_TIMES = 3;

    @Autowired
    private PushMessageDao pushMessageDao;

    @Autowired
    private PushReadStateDao pushReadStateDao;

    @Autowired
    private PushAreaDao pushAreaDao;

    @Autowired
    private PushDeviceDao pushDeviceDao;

    @Autowired
    private PushReadStateService pushReadStateService;

    @Override
    @Transactional
    public void createPushMessage(PushMessageCreateDto pushMessageCreateDto , List<String> userIds) {
        //保存推送消息到push_message表
        Long messageId = saveMessage(pushMessageCreateDto);
        pushMessageCreateDto.setMessageId(messageId);

        //保存推送到readState表
        saveReadState(pushMessageCreateDto,userIds);
        //如果不是所有区域将保存数据到表push_area中
        saveArea(pushMessageCreateDto);
    }

    @Override
    public PushMessagePageListDto getPushlistByUserId(PushRequestDto pushSearchDto,int pushObject) {
        Specification<PushReadStatePo> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (null != pushSearchDto) {
                expressions.add(cb.notEqual(root.get("pushMessagePo").get("pushStates"), "0"));
                if (StringUtils.isNotEmpty(pushSearchDto.getLastId())) {
                    expressions.add(cb.lessThanOrEqualTo(root.get("messageId"), pushSearchDto.getLastId()));
                }
                if (StringUtils.isNotEmpty(pushSearchDto.getUserId())) {
                    expressions.add(cb.equal(root.get("userId"), pushSearchDto.getUserId()));
                }
                expressions.add(cb.equal(root.get("pushMessagePo").get("pushObject"), pushObject));
            }
            return predicate;
        };

        Sort sort = new Sort(Sort.Direction.DESC, "pushMessagePo.pushTime");
        Pageable pageable = new PageRequest(pushSearchDto.getCurrentPage() - 1, pushSearchDto.getPageSize(), sort);

        Page<PushReadStatePo> page = pushReadStateDao.findAll(spec, pageable);

        List<PushMessagePageDto> data = new ArrayList<>();
        page.getContent().forEach(po -> {
            PushMessagePageDto dto = new PushMessagePageDto();
            dto.setMessageId(po.getPushMessagePo().getMessageId());
            dto.setTitle(po.getPushMessagePo().getTitle());
            dto.setImageUrl(AppConfigUtil.getConfig("file.addr") + po.getPushMessagePo().getImagePath());
            dto.setSubTitle(po.getPushMessagePo().getDescription());

            if (po.getPushMessagePo().getType() == 1) {
                dto.setUrl(AppConfigUtil.getConfig("html.addr") + "/message.html?id=" + po.getPushMessagePo().getMessageId());
            } else {
                dto.setUrl(po.getPushMessagePo().getUrl());
            }

            try {
                dto.setTime(TimeDiff(po.getPushMessagePo().getPushTime(), new Date()));
            } catch (Exception e) {

                dto.setTime(DateHelper.formatDate(new Date(po.getPushMessagePo().getPushTime().getTime()), "yyyy-MM-dd"));
            }
            if (po.getReadState() == 0) {
                dto.setReadStatus(Boolean.FALSE);
            } else {
                dto.setReadStatus(Boolean.TRUE);
            }
            data.add(dto);
        });

        PushMessagePageListDto resultDto = new PushMessagePageListDto();
        resultDto.setUnReadCount(getunreadMessageCountByUserId(pushSearchDto.getUserId(),pushObject));
        resultDto.setTotalPages(page.getTotalPages());
        resultDto.setCurrentPage(pushSearchDto.getCurrentPage());
        resultDto.setList(data);
        return resultDto;
    }

    @Override
    public PushMessageListDto getPushlistForWebByUserId(PushSearchDto pushSearchDto) {
        //所有地区
        if (StringUtils.isEmpty(pushSearchDto.getProvinceId()) && StringUtils.isEmpty(pushSearchDto.getCityId()) && StringUtils.isEmpty(pushSearchDto.getAreaId())) {
            return findMessage(null, pushSearchDto);
        } else {
            Specification<PushAreaPo> areaspec = (root, query, cb) -> {
                Predicate predicate = cb.conjunction();
                List<Expression<Boolean>> expressions = predicate.getExpressions();
                if (null != pushSearchDto) {
                    if (StringUtils.isNotEmpty(pushSearchDto.getProvinceId())) {
                        expressions.add(cb.equal(root.get("provinceId"), pushSearchDto.getProvinceId()));
                    }
                    if (StringUtils.isNotEmpty(pushSearchDto.getCityId())) {
                        expressions.add(cb.equal(root.get("cityId"), pushSearchDto.getCityId()));
                    }
                    if (StringUtils.isNotEmpty(pushSearchDto.getAreaId())) {
                        expressions.add(cb.equal(root.get("areaId"), pushSearchDto.getAreaId()));
                    }
                }
                return predicate;
            };
            List<PushAreaPo> areaPos = pushAreaDao.findAll(areaspec);
            if (CollectionUtils.isEmpty(areaPos)) {
                return new PushMessageListDto();
            }
            List<Long> messageIds = new ArrayList<>();
            for (PushAreaPo po : areaPos) {
                messageIds.add(po.getMessageId());
            }
            return findMessage(messageIds, pushSearchDto);
        }
    }

    @Override
    public Long getunreadMessageCountByUserId(String userId, int pushObject) {
        return (long) pushReadStateDao.getUnReadByUserId(userId,pushObject).size();
    }

    @Override
    public void readMessage(Long messageId, String userId) {
        PushReadStatePo po = pushReadStateDao.findByMessageIdAndUserId(messageId, userId);
        po.setReadState(PushReadState.READ);
        pushReadStateDao.saveAndFlush(po);
    }

    @Override
    @Transactional
    public void deleteMessage(Long messageId) {
        pushMessageDao.deleteByMessageId(messageId);
        pushReadStateDao.deleteByMessageId(messageId);
        pushAreaDao.deleteByMessageId(messageId);
    }

    @Override
    public PushMessageDto detail(Long messageId) {
        PushMessagePo push = pushMessageDao.findByMessageId(messageId);
        List<PushAreaPo> areas = pushAreaDao.findByMessageId(messageId);

        List<PushMessageAreaDto> pushMessageAreaDtos = new ArrayList<>();
        for (PushAreaPo po : areas) {
            PushMessageAreaDto dto = ConverterService.convert(po, PushMessageAreaDto.class);
            if (StringUtils.isNotEmpty(dto.getProvinceId())) {
                pushMessageAreaDtos.add(dto);
            }
        }

        PushMessageDto dto = ConverterService.convert(push, PushMessageDto.class);

        if (!CollectionUtils.isEmpty(pushMessageAreaDtos)) {
            dto.setAreas(pushMessageAreaDtos);
        }

        return dto;
    }

    @Override
    public void updateRegistrationId(PushDeviceDto dto,int pushObject) {
        if (StringUtils.isEmpty(dto.getPushToken())) {
            unBindRegistrationId(dto.getUserId(),pushObject);
            return;
        }

        if (StringUtils.isNotEmpty(dto.getUserId())) {
            PushDevicePo devicePo = pushDeviceDao.findByUserIdAndAppType(dto.getUserId(),pushObject);
            if (devicePo != null) {
                devicePo.setAppType(pushObject);
                devicePo.setPushToken(dto.getPushToken());
                pushDeviceDao.saveAndFlush(devicePo);
            }else {
                devicePo = new PushDevicePo();
                devicePo.setAppType(pushObject);
                devicePo.setPushToken(dto.getPushToken());
                devicePo.setUserId(dto.getUserId());
                pushDeviceDao.saveAndFlush(devicePo);
            }
        }
    }

    @Override
    public void unBindRegistrationId(String userId, int pushObject) {
        if (StringUtils.isNotEmpty(userId)) {
            PushDevicePo devicePo = pushDeviceDao.findByUserIdAndAppType(userId,pushObject);
            if (devicePo != null) {
                pushDeviceDao.delete(devicePo);
            }
        }
    }

    @Override
    public void pushJob() {

        List<String> deviceList = new ArrayList<>();
        Page<PushMessagePo> pos = getPushMessagePage();
        if (pos != null && !pos.getContent().isEmpty()) {
            for (PushMessagePo po : pos) {
                // TODO 数据库分页
                List<Object[]> messageToSend = pushReadStateDao.findMessagesByMessageId(po.getMessageId(), PushState.WAIT_SEND , po.getPushObject());
                List<Long> idList = new ArrayList<>(messageToSend.size());
                for (Object[] objects : messageToSend) {
                    idList.add((Long) objects[0]);
                }
                if (!idList.isEmpty()) {
                    updatePushReadState(idList, PushState.SENDING); // 标记为发送中
                }

                int size = messageToSend.size();
                int times = size / BATCH_SIZE; // 计算分批发送次数
                if (size % BATCH_SIZE != 0) {
                    times++;
                }
                for (int i = 0; i < times; i++) {
                    int fromIndex = BATCH_SIZE * i;
                    int toIndex = BATCH_SIZE + fromIndex;
                    if (toIndex > size) {
                        toIndex = size;
                    }
                    List<Object[]> batchList = messageToSend.subList(fromIndex, toIndex);
                    List<Long> sendingIdList = new ArrayList<>(BATCH_SIZE);
                    for (Object[] objArray : batchList) {
                        Long id = (Long) objArray[0];
                        String token = (String) objArray[1];
                        if (StringUtils.isNotEmpty(token)) {
                            deviceList.add(token);
                            sendingIdList.add(id);
                        }
                    }

                    boolean isSuccess = false;
                    if (po.getPushObject() == PushObject.XHB){
                        isSuccess = JpushUtils.sendPushMessage(deviceList, po.getTitle(), po.getTitle(), po.getPlatform(), true,AppConfigUtil.getConfig("xhb.push.key"),AppConfigUtil.getConfig("xhb.push.secrt"));
                    }else {
                        isSuccess = JpushUtils.sendPushMessage(deviceList, po.getTitle(), po.getTitle(), po.getPlatform(), true,AppConfigUtil.getConfig("zg.push.key"),AppConfigUtil.getConfig("zg.push.secrt"));
                    }
                    if (isSuccess) {
                        updatePushReadState(sendingIdList, PushState.SEND_SUCCESSED);
                    } else {
                        updatePushReadState(sendingIdList, PushState.SEND_FAILED);
                    }
                }

                // 不论消息是否全部发送成功，都标记该条消息为发送成功, 发送失败的消息由另外的调度一条一条补发
                updatePushState(PushState.SEND_SUCCESSED, po.getMessageId());
            }
        }
    }

    @Override
    public void compensatingPushJob(int pushObject) {

        Page<Object[]> messagePageToSend = getPushMessageTocompenstatingPush(MAX_RETRY_TIMES, BATCH_SIZE,pushObject);
        List<Object[]> content = messagePageToSend.getContent();
        if (!content.isEmpty()) {

            List<Long> idList = new ArrayList<>(content.size());
            for (Object[] objArrays : content) {
                Long id = (Long) objArrays[0];
                idList.add(id);
            }
            updatePushReadState(idList, PushState.SENDING);

            Map<Long, PushMessagePo> pushMessageCache = new HashMap<>();
            for (Object[] objArrays : content) {
                Long id = (Long) objArrays[0];
                Long messageId = (Long) objArrays[1];
                String token = (String) objArrays[2];
                PushMessagePo pushMessagePo = pushMessageCache.get(messageId);
                if (pushMessagePo == null) {
                    pushMessagePo = pushMessageDao.findByMessageId(messageId);
                    if (pushMessagePo != null) {
                        pushMessageCache.put(messageId, pushMessagePo);
                    }
                }
                List<String> arrayLists = new ArrayList<>(1);
                arrayLists.add(token);
                if (pushMessagePo != null) {
                    boolean success = false;
                    if (pushMessagePo.getPushObject() == PushObject.XHB){
                        success = JpushUtils.sendPushMessage(arrayLists, pushMessagePo.getTitle(), pushMessagePo.getTitle(), pushMessagePo.getPlatform(), true,AppConfigUtil.getConfig("xhb.push.key"),AppConfigUtil.getConfig("xhb.push.secrt"));
                    }else {
                        success = JpushUtils.sendPushMessage(arrayLists, pushMessagePo.getTitle(), pushMessagePo.getTitle(), pushMessagePo.getPlatform(), true,AppConfigUtil.getConfig("zg.push.key"),AppConfigUtil.getConfig("zg.push.secrt"));
                    }
                    if (success) {
                        pushReadStateService.updatePushStatesAndRetryTimes(PushState.SEND_SUCCESSED, id);
                    } else {
                        pushReadStateService.updatePushStatesAndRetryTimes(PushState.SEND_FAILED, id);
                    }
                }
            }
        }

    }

    @Override
    public void pushAPP(String content, List<String> pushToken) {
        pushJob();
//        JpushUtils.sendPushMessage(pushToken, content, content, 0, true);
    }

    private void updatePushState(Integer pushState, Long messageId) {
        PushMessagePo pushMessagePo = pushMessageDao.findByMessageId(messageId);
        pushMessagePo.setPushStates(pushState);
        pushMessageDao.save(pushMessagePo);

    }

    private void updatePushReadState(List<Long> idList, int pushStatus) {
        int batchSize = 100;
        int size = idList.size();
        int times = size / batchSize;
        if (size % batchSize != 0) {
            times++;
        }
        for (int i = 0; i < times; i++) {
            int start = i * batchSize;
            int end = start + batchSize;
            if (end > size) {
                end = size;
            }
            pushReadStateService.updatePushStatus(pushStatus, idList.subList(start, end));
        }
    }

    private Page<PushMessagePo> getPushMessagePage() {
        Specification<PushMessagePo> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            expressions.add(cb.lessThan(root.get("pushTime"), new Date()));
            expressions.add(cb.equal(root.get("pushStates"), "0"));
            return predicate;
        };
        Pageable pageable = new PageRequest(0, 1000);
        Page<PushMessagePo> poList = pushMessageDao.findAll(spec, pageable);
        for (PushMessagePo po : poList) {
            po.setPushStates(PushState.SENDING);//推送中
            pushMessageDao.save(po);
        }
        return poList;
    }

    private Page<Object[]> getPushMessageTocompenstatingPush(int maxRetryTimes, int pageSize,int pushObject) {
        Pageable pageable = new PageRequest(0, pageSize);
        Page<Object[]> page = pushReadStateDao.findByPushStatesAndRetryTimesLessThanOrderByMessageId(PushState.SEND_FAILED, maxRetryTimes, pageable,pushObject);
        return page;
    }

    PushMessagePo update(PushMessageCreateDto pushMessageCreateDto) {
        PushMessagePo msg = pushMessageDao.findByMessageId(pushMessageCreateDto.getMessageId());
        msg.setTitle(pushMessageCreateDto.getTitle());
        msg.setImagePath(pushMessageCreateDto.getImagePath());
        msg.setDescription(pushMessageCreateDto.getDescription());
        msg.setPushObject(pushMessageCreateDto.getPushObject());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date date = sdf.parse(pushMessageCreateDto.getPushTime());
            msg.setPushTime(date);
        } catch (ParseException e) {

        }
        msg.setPlatform(pushMessageCreateDto.getPlatform());
        msg.setType(pushMessageCreateDto.getType());
        msg.setUrl(pushMessageCreateDto.getUrl());
        msg.setPushStates(PushState.WAIT_SEND);
        msg.setIsDelete(0);
        msg.setContent(pushMessageCreateDto.getContent());
        msg.setLastMntTs(new Date());
        pushReadStateDao.deleteByMessageId(pushMessageCreateDto.getMessageId());
        pushAreaDao.deleteByMessageId(pushMessageCreateDto.getMessageId());
        return msg;
    }

    //保存推送消息到push_message表
    private Long saveMessage(PushMessageCreateDto pushMessageCreateDto) {
        PushMessagePo pushSavePo;
        if (pushMessageCreateDto.getMessageId() != null) {
            pushSavePo = update(pushMessageCreateDto);
        } else {
            pushSavePo = ConverterService.convert(pushMessageCreateDto, PushMessagePo.class);
            pushSavePo.setPushStates(PushState.WAIT_SEND);
            pushSavePo.setIsDelete(0);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = null;
            try {
                date = sdf.parse(pushMessageCreateDto.getPushTime());
            } catch (ParseException e) {

            }
            pushSavePo.setPushTime(date);

        }

        if (pushMessageCreateDto.getAreas() == null) {
            pushSavePo.setIsAllPlace(1);
        } else {
            pushSavePo.setIsAllPlace(0);
        }
        pushMessageDao.save(pushSavePo);
        return pushSavePo.getMessageId();
    }

    //保存推送到readState表
    private void saveReadState(PushMessageCreateDto pushMessageCreateDto,List<String> userIds) {
        if (CollectionUtils.isEmpty(userIds)){
            return;
        }

        //保存推送消息到push_readstate表
        for (String userId : userIds){
            PushReadStatePo pushReadStatePo = new PushReadStatePo();
            pushReadStatePo.setMessageId(pushMessageCreateDto.getMessageId());
            pushReadStatePo.setUserId(userId);
            pushReadStatePo.setReadState(PushReadState.UNREAD);
            pushReadStatePo.setRetryTimes(0);
            pushReadStatePo.setPushStates(PushState.WAIT_SEND);
            pushReadStateDao.save(pushReadStatePo);
        }
    }

    //如果不是所有区域将保存数据到表push_area中
    private void saveArea(PushMessageCreateDto pushMessageCreateDto) {
        if (CollectionUtils.isEmpty(pushMessageCreateDto.getAreas())){
            return;
        }
        //保存推送消息到push_area表
        for (PushMessageAreaDto createDto : pushMessageCreateDto.getAreas()) {
            PushAreaPo pushAreaPo = ConverterService.convert(createDto, PushAreaPo.class);
            pushAreaPo.setMessageId(pushMessageCreateDto.getMessageId());
            pushAreaDao.save(pushAreaPo);
        }
    }

    //查找筛选条件，web端查询
    private PushMessageListDto findMessage(List<Long> messageIds, PushSearchDto pushSearchDto) {

        Specification<PushMessagePo> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (null != pushSearchDto) {
                if (StringUtils.isNotEmpty(pushSearchDto.getPushStates())) {
                    expressions.add(cb.equal(root.get("pushStates"), pushSearchDto.getPushStates()));
                }
                if (StringUtils.isNotEmpty(pushSearchDto.getType()) && Long.valueOf(pushSearchDto.getType()) != 0) {
                    expressions.add(cb.equal(root.get("type"), pushSearchDto.getType()));
                }
                if (StringUtils.isNotEmpty(pushSearchDto.getPushObject())) {
                    expressions.add(cb.equal(root.get("pushObject"), pushSearchDto.getPushObject()));
                }
                if (StringUtils.isNotEmpty(pushSearchDto.getPlatform()) && Long.valueOf(pushSearchDto.getPlatform()) != 0) {
                    expressions.add(cb.equal(root.get("platform"), pushSearchDto.getPlatform()));
                }
                if (StringUtils.isNotEmpty(pushSearchDto.getLastId())) {
                    expressions.add(cb.lessThanOrEqualTo(root.get("messageId"), pushSearchDto.getLastId()));
                }
                if (StringUtils.isNotEmpty(pushSearchDto.getKeyword())) {
                    expressions.add(cb.like(root.get("title"), "%" + pushSearchDto.getKeyword() + "%"));
                }
                if (StringUtils.isNotEmpty(pushSearchDto.getBeginTime())) {
                    Date time = DateHelper.getStartDate(pushSearchDto.getBeginTime());
                    expressions.add(cb.greaterThanOrEqualTo(root.get("pushTime"), time));
                }
                if (StringUtils.isNotEmpty(pushSearchDto.getEndTime())) {
                    expressions.add(cb.lessThan(root.get("pushTime"), DateHelper.getEndDate(pushSearchDto.getEndTime())));
                }
                expressions.add(cb.equal(root.get("isDelete"), "0"));
                if (!CollectionUtils.isEmpty(messageIds)) {
                    expressions.add(cb.in(root.get("messageId")).value(messageIds));
                }
            }

            return predicate;
        };
        Sort sort = new Sort(Sort.Direction.DESC, "messageId");
        Pageable pageable = new PageRequest(pushSearchDto.getCurrentPage() - 1, pushSearchDto.getPageSize(), sort);

        Page<PushMessagePo> page = pushMessageDao.findAll(spec, pageable);

        List<PushMessageDto> data = new ArrayList<>();
        page.getContent().forEach(po -> {
            PushMessageDto dto = ConverterService.convert(po, PushMessageDto.class);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            dto.setPushTime(sdf.format(po.getPushTime()));

            if (dto.getPlatform() == PushPlatform.ALL) {
                dto.setPlatformName("Android、IOS");
            } else if (dto.getPlatform() == PushPlatform.ANDROID) {
                dto.setPlatformName("Android");
            } else {
                dto.setPlatformName("IOS");
            }

            if (dto.getPushObject() == PushObject.ALL) {
                dto.setPushObjectName("全部");
            } else if (dto.getPushObject() == PushObject.XHB) {
                dto.setPushObjectName("小伙伴");
            } else {
                dto.setPushObjectName("云联掌柜");
            }

            if (dto.getPushStates() == PushState.WAIT_SEND) {
                dto.setPushStatesName("待推送");
            } else if (dto.getPushStates() == PushState.SENDING) {
                dto.setPushStatesName("推送中");
            } else if (dto.getPushStates() == PushState.SEND_SUCCESSED) {
                dto.setPushStatesName("已推送");
            } else {
                dto.setPushStatesName("推送失败");
            }

            if (dto.getType() == PushType.TEXT) {
                dto.setTypeName("文本消息");
            } else {
                dto.setTypeName("链接消息");
            }

            if (po.getIsAllPlace() == 1){
                dto.setAreaCountName("1个区域");
                dto.setAreaCount(1);
                data.add(dto);
            }else {
                List<PushAreaPo> poList = pushAreaDao.findByMessageId(dto.getMessageId());
                List<PushMessageAreaDto> pushAreaDaos = new ArrayList<>();
                for (PushAreaPo areaPo : poList) {
                    PushMessageAreaDto dao = ConverterService.convert(areaPo, PushMessageAreaDto.class);
                    if (StringUtils.isNotEmpty(dao.getProvinceId())) {
                        pushAreaDaos.add(dao);
                    }
                }
                dto.setAreas(pushAreaDaos);
                dto.setAreaCountName(poList.size() + "个区域");
                dto.setAreaCount(poList.size());
                data.add(dto);
            }
        });

        PushMessageListDto resultDto = new PushMessageListDto();
        resultDto.setTotalPages(page.getTotalPages());
        resultDto.setCurrentPage(pushSearchDto.getCurrentPage());
        resultDto.setList(data);
        resultDto.setTotalRecord(page.getTotalElements());
        return resultDto;
    }

    private static String TimeDiff(Date pBeginDate, Date pEndDate) {

        String pBeginTime = DateHelper.formatDate(pBeginDate, "yyyy-MM-dd HH:mm");
        String pEndTime = DateHelper.formatDate(pEndDate, "yyyy-MM-dd HH:mm");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Long beginL = format.parse(pBeginTime).getTime();
            Long endL = format.parse(pEndTime).getTime();
            Long day = (endL - beginL) / 86400000;
            Long hour = ((endL - beginL) % 86400000) / 3600000;
            Long min = ((endL - beginL) % 86400000 % 3600000) / 60000;
            if (day > 2) {
                return DateHelper.formatDate(pBeginDate, "yyyy-MM-dd");
            }

            if (day > 0 && day <= 2) {
                return (day + "天前");
            }

            if (hour > 0 && hour < 24) {
                return (hour + "小时前");
            }

            if (min > 5 && min < 60) {
                return (min + "分前");
            } else {
                return "刚刚";
            }
        } catch (ParseException e) {
            return DateHelper.formatDate(pBeginDate, "yyyy-MM-dd");
        }
    }

    @Override
    public void pushMessageToApp(String userId, String message, EPushObject pushObject ,EPushPlatform platform) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean isSuccess = false;
                System.out.print(pushObject.getValue());
                PushDevicePo po = pushDeviceDao.findByUserIdAndAppType(userId,pushObject.getValue());
                if (po != null){
                    List<String> deviceList = new ArrayList<>();
                    deviceList.add(po.getPushToken());
                    if (pushObject.getValue() == PushObject.XHB){
                        isSuccess = JpushUtils.sendPushMessage(deviceList, message, message, platform.getValue(), false,AppConfigUtil.getConfig("xhb.push.key"),AppConfigUtil.getConfig("xhb.push.secrt"));
                    }else {
                        isSuccess = JpushUtils.sendPushMessage(deviceList, message, message, platform.getValue(), false,AppConfigUtil.getConfig("zg.push.key"),AppConfigUtil.getConfig("zg.push.secrt"));
                    }
                    if (isSuccess){
                        logger.info("推送给用户成功");
                        System.out.print("推送给用户成功");
                    }else{
                        logger.info("推送失败");
                        System.out.print("推送给用户失败");
                    }
                }
            }
        });
    }
}
