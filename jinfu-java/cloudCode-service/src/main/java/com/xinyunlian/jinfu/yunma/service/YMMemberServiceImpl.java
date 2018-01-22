package com.xinyunlian.jinfu.yunma.service;

import com.alibaba.fastjson.JSON;
import com.xinyunlian.jinfu.clerk.dao.ClerkAuthDao;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.BizUtil;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.mq.DestinationDefine;
import com.xinyunlian.jinfu.mq.sender.QueueSender;
import com.xinyunlian.jinfu.trade.dao.YmBizDao;
import com.xinyunlian.jinfu.trade.dao.YmTradeDao;
import com.xinyunlian.jinfu.trade.entity.YmBizPo;
import com.xinyunlian.jinfu.trade.enums.EBizCode;
import com.xinyunlian.jinfu.yunma.dao.*;
import com.xinyunlian.jinfu.yunma.dto.MemberIntoDto;
import com.xinyunlian.jinfu.yunma.dto.YMMemberDto;
import com.xinyunlian.jinfu.yunma.dto.YMMemberSearchDto;
import com.xinyunlian.jinfu.yunma.dto.YmMemberBizDto;
import com.xinyunlian.jinfu.yunma.entity.*;
import com.xinyunlian.jinfu.yunma.enums.*;
import com.ylfin.redis.lock.RedisLock;
import com.ylfin.redis.lock.RedisLockFactory;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.*;
import java.util.*;
import java.util.concurrent.TimeUnit;


/**
 * 云码商铺ServiceImpl
 *
 * @author jll
 */

@Service(value = "yMMemberServiceImpl")
public class YMMemberServiceImpl implements YMMemberService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YMMemberServiceImpl.class);

    @Autowired
    private YMMemberDao ymMemberDao;
    @Autowired
    private YmMemberBizDao memberBizDao;
    @Autowired
    private YmBizDao ymBizDao;
    @Autowired
    private YMMemberViewDao ymMemberViewDao;
    @Autowired
    private ClerkAuthDao clerkAuthDao;
    @Autowired
    private YmTradeDao ymTradeDao;
    @Autowired
    private YmIntoInfoDao ymIntoInfoDao;
    @Autowired
    private QueueSender queueSender;

    @Autowired
    private RedisLockFactory redisLockFactory;
    @Autowired
    private YmDepotDao ymDepotDao;

    @Override
    public YMMemberDto get(Long id) {
        YMMemberPo po = ymMemberDao.findOne(id);
        if (null == po) {
            return null;
        }
        YMMemberDto dto = ConverterService.convert(po, YMMemberDto.class);
        List<YmMemberBizPo> memberBizPos = memberBizDao.findByMemberNo(po.getMemberNo());
        if (!CollectionUtils.isEmpty(memberBizPos)) {
            memberBizPos.forEach(ymMemberBizPo -> {
                YmMemberBizDto memberBizDto = ConverterService.convert(ymMemberBizPo, YmMemberBizDto.class);
                dto.getMemberBizDtos().add(memberBizDto);
            });
        }
        return dto;
    }

    @Override
    @Transactional
    public void alterStatus(Long id, EMemberStatus status) {
        YMMemberPo po = ymMemberDao.findOne(id);
        if (null != po) {
            po.setMemberStatus(status);
        }
    }

    @Override
    @Transactional
    public void alterBankCard(Long id, Long bankCardId) {
        YMMemberPo po = ymMemberDao.findOne(id);
        if (null != po) {
            po.setBankCardId(bankCardId);
        }
    }

    @Override
    @Transactional
    public void unbind(Long id) {
        YMMemberPo po = ymMemberDao.findOne(id);
        if (null != po) {
            YmDepotPo ymDepotPo = ymDepotDao.findByQrCodeNo(po.getQrcodeNo());
            if (ymDepotPo != null) {
                ymDepotPo.setStatus(EDepotStatus.UNBIND_USE);
                ymDepotPo.setReceiveStatus(EDepotReceiveStatus.RECEIVE);
            }
            po.setMemberStatus(EMemberStatus.UNBIND);
            po.setQrcodeNo(null);
            po.setQrcodeUrl(null);
            po.setBindTime(null);
            po.setBankCardId(null);
        }
    }

    @Override
    @Transactional
    public void unbindByStoreId(Long storeId) {
        clerkAuthDao.deleteByStoreId(storeId);
        YMMemberPo po = ymMemberDao.findByStoreId(storeId);
        if (null != po) {
            YmDepotPo ymDepotPo = ymDepotDao.findByQrCodeNo(po.getQrcodeNo());
            if (ymDepotPo != null) {
                ymDepotPo.setStatus(EDepotStatus.UNBIND_USE);
                ymDepotPo.setReceiveStatus(EDepotReceiveStatus.RECEIVE);
            }
            po.setMemberStatus(EMemberStatus.UNBIND);
            po.setQrcodeNo(null);
            po.setQrcodeUrl(null);
            po.setBindTime(null);
            po.setBankCardId(null);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public YMMemberSearchDto getMemberPage(YMMemberSearchDto searchDto) {
        Specification<YMMemberPo> spec = (Root<YMMemberPo> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (null != searchDto) {
                if (!StringUtils.isEmpty(searchDto.getMemberNo())) {
                    expressions.add(cb.equal(root.get("memberNo"), searchDto.getMemberNo()));
                }

                if (!StringUtils.isEmpty(searchDto.getStartBindTime())) {
                    expressions.add(cb.greaterThanOrEqualTo(root.get("bindTime"), DateHelper.getStartDate(searchDto.getStartBindTime())));
                }
                if (!StringUtils.isEmpty(searchDto.getEndBindTime())) {
                    expressions.add(cb.lessThanOrEqualTo(root.get("bindTime"), DateHelper.getEndDate(searchDto.getEndBindTime())));
                }

                if (!CollectionUtils.isEmpty(searchDto.getStoreIds())) {
                    expressions.add(root.get("storeId").in(searchDto.getStoreIds()));
                }

            }
            return predicate;
        };

        Pageable pageable = new PageRequest(searchDto.getCurrentPage() - 1,
                searchDto.getPageSize(), Direction.DESC, "id");
        Page<YMMemberPo> page = ymMemberDao.findAll(spec, pageable);

        List<YMMemberSearchDto> data = new ArrayList<>();
        for (YMMemberPo po : page.getContent()) {
            YMMemberSearchDto dto = ConverterService.convert(po, YMMemberSearchDto.class);
            data.add(dto);
        }
        searchDto.setList(data);
        searchDto.setTotalPages(page.getTotalPages());
        searchDto.setTotalRecord(page.getTotalElements());
        return searchDto;
    }

    @Override
    @Transactional(readOnly = true)
    public YMMemberSearchDto getMemberViewPage(YMMemberSearchDto searchDto) {
        Specification<YMMemberViewPo> spec = (Root<YMMemberViewPo> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (null != searchDto) {
                if (StringUtils.isNotEmpty(searchDto.getLastId())) {
                    expressions.add(cb.lessThanOrEqualTo(root.get("id"), searchDto.getLastId()));
                }
                if (!StringUtils.isEmpty(searchDto.getStoreName())) {
                    expressions.add(cb.like(root.get("storeName"), BizUtil.filterString(searchDto.getStoreName())));
                }
                if (!StringUtils.isEmpty(searchDto.getUserName())) {
                    expressions.add(cb.like(root.get("userName"), BizUtil.filterString(searchDto.getUserName())));
                }
                if (!StringUtils.isEmpty(searchDto.getMobile())) {
                    expressions.add(cb.like(root.get("mobile"), BizUtil.filterString(searchDto.getMobile())));
                }
                if (null != searchDto.getProvinceId()) {
                    expressions.add(cb.equal(root.get("provinceId"), searchDto.getProvinceId()));
                }
                if (null != searchDto.getCityId()) {
                    expressions.add(cb.equal(root.get("cityId"), searchDto.getCityId()));
                }
                if (null != searchDto.getAreaId()) {
                    expressions.add(cb.equal(root.get("areaId"), searchDto.getAreaId()));
                }
                if (!StringUtils.isEmpty(searchDto.getMemberNo())) {
                    expressions.add(cb.equal(root.get("memberNo"), searchDto.getMemberNo()));
                }
                if (!StringUtils.isEmpty(searchDto.getQrcodeNo())) {
                    expressions.add(cb.equal(root.get("qrcodeNo"), searchDto.getQrcodeNo()));
                }
                if (!StringUtils.isEmpty(searchDto.getStartBindTime())) {
                    expressions.add(cb.greaterThanOrEqualTo(root.get("bindTime"), DateHelper.getStartDate(searchDto.getStartBindTime())));
                }
                if (!StringUtils.isEmpty(searchDto.getEndBindTime())) {
                    expressions.add(cb.lessThanOrEqualTo(root.get("bindTime"), DateHelper.getEndDate(searchDto.getEndBindTime())));
                }
                if (!CollectionUtils.isEmpty(searchDto.getStoreIds())) {
                    expressions.add(root.get("storeId").in(searchDto.getStoreIds()));
                }
                if (StringUtils.isNotEmpty(searchDto.getCreateOpId())) {
                    expressions.add(cb.equal(root.get("createOpId"), searchDto.getCreateOpId()));
                }
                if (!CollectionUtils.isEmpty(searchDto.getCreateOpIds())) {
                    expressions.add(cb.in(root.get("createOpId")).value(searchDto.getCreateOpIds()));
                }

                expressions.add(cb.notEqual(root.get("memberStatus"), EMemberStatus.DELETE));
            }
            return predicate;
        };

        Pageable pageable = new PageRequest(searchDto.getCurrentPage() - 1,
                searchDto.getPageSize(), Direction.DESC, "id");
        Page<YMMemberViewPo> page = ymMemberViewDao.findAll(spec, pageable);

        List<YMMemberSearchDto> data = new ArrayList<>();
        for (YMMemberViewPo po : page.getContent()) {
            YMMemberSearchDto dto = ConverterService.convert(po, YMMemberSearchDto.class);
            data.add(dto);
        }
        searchDto.setList(data);
        searchDto.setTotalPages(page.getTotalPages());
        searchDto.setTotalRecord(page.getTotalElements());
        return searchDto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<YMMemberSearchDto> getMemberExportList(YMMemberSearchDto searchDto) {
        Specification<YMMemberViewPo> spec = (Root<YMMemberViewPo> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (null != searchDto) {
                if (!StringUtils.isEmpty(searchDto.getStoreName())) {
                    expressions.add(cb.like(root.get("storeName"), BizUtil.filterString(searchDto.getStoreName())));
                }
                if (!StringUtils.isEmpty(searchDto.getUserName())) {
                    expressions.add(cb.like(root.get("userName"), BizUtil.filterString(searchDto.getUserName())));
                }
                if (!StringUtils.isEmpty(searchDto.getMobile())) {
                    expressions.add(cb.like(root.get("mobile"), BizUtil.filterString(searchDto.getMobile())));
                }
                if (null != searchDto.getProvinceId()) {
                    expressions.add(cb.equal(root.get("provinceId"), searchDto.getProvinceId()));
                }
                if (null != searchDto.getCityId()) {
                    expressions.add(cb.equal(root.get("cityId"), searchDto.getCityId()));
                }
                if (null != searchDto.getAreaId()) {
                    expressions.add(cb.equal(root.get("areaId"), searchDto.getAreaId()));
                }
                if (!StringUtils.isEmpty(searchDto.getMemberNo())) {
                    expressions.add(cb.equal(root.get("memberNo"), searchDto.getMemberNo()));
                }
                if (!StringUtils.isEmpty(searchDto.getQrcodeNo())) {
                    expressions.add(cb.equal(root.get("qrcodeNo"), searchDto.getQrcodeNo()));
                }
                if (!StringUtils.isEmpty(searchDto.getStartBindTime())) {
                    expressions.add(cb.greaterThanOrEqualTo(root.get("bindTime"), DateHelper.getStartDate(searchDto.getStartBindTime())));
                }
                if (!StringUtils.isEmpty(searchDto.getEndBindTime())) {
                    expressions.add(cb.lessThanOrEqualTo(root.get("bindTime"), DateHelper.getEndDate(searchDto.getEndBindTime())));
                }
                if (!CollectionUtils.isEmpty(searchDto.getStoreIds())) {
                    expressions.add(root.get("storeId").in(searchDto.getStoreIds()));
                }

                expressions.add(cb.notEqual(root.get("memberStatus"), EMemberStatus.DELETE));
            }
            return predicate;
        };

        List<YMMemberViewPo> list = ymMemberViewDao.findAll(spec, new Sort(Direction.DESC, "id"));

        List<YMMemberSearchDto> data = new ArrayList<>();
        for (YMMemberViewPo po : list) {
            YMMemberSearchDto dto = ConverterService.convert(po, YMMemberSearchDto.class);
            data.add(dto);
        }
        return data;
    }

    @Override
    @Transactional
    public void addMember(YMMemberDto dto) throws BizServiceException {
        ymMemberDao.deleteByStoreId(dto.getStoreId());

        dto.setMemberStatus(EMemberStatus.ENABLE);
        //民生银行
        //dto.setMemberAuditStatus(EMemberAuditStatus.AUDITING);
        //众码2.0
        dto.setMemberAuditStatus(EMemberAuditStatus.SUCCESS);
        dto.setMemberIntoStatus(EMemberIntoStatus.INTO_ING);
        dto.setBindTime(new Date());
        YMMemberPo po = ConverterService.convert(dto, YMMemberPo.class);
        ymMemberDao.save(po);
        YmMemberBizDto ymMemberBizDto;
        List<YmBizPo> ymBizPos = ymBizDao.findAll();
        for (YmBizPo ymBizPo : ymBizPos) {
            ymMemberBizDto = new YmMemberBizDto();
            ymMemberBizDto.setMemberNo(dto.getMemberNo());
            ymMemberBizDto.setBizCode(ymBizPo.getCode());
            ymMemberBizDto.setRate(ymBizPo.getRate());
            YmMemberBizPo ymMemberBizPo = ConverterService.convert(ymMemberBizDto, YmMemberBizPo.class);
            memberBizDao.save(ymMemberBizPo);
        }
    }

    @Override
    @Transactional
    public void updateMemberBiz(List<YmMemberBizDto> memberBizDtos) {
        if (!CollectionUtils.isEmpty(memberBizDtos)) {
            memberBizDtos.forEach(ymMemberBizDto -> {
                if (null != ymMemberBizDto.getId()) {
                    YmMemberBizPo po = memberBizDao.findOne(ymMemberBizDto.getId());
                    if (po.getBizCode() == ymMemberBizDto.getBizCode()) {
                        po.setRate(ymMemberBizDto.getRate());
                    }
                }
            });
        }
    }

    @Override
    @Transactional
    public void updateMemberAuditStatus(YMMemberDto dto) {
        YMMemberPo po = ymMemberDao.findById(dto.getId());
        if (po == null) {
            return;
        }
        po.setMemberAuditStatus(dto.getMemberAuditStatus());
        ymMemberDao.save(po);
    }

    @Override
    @JmsListener(destination = DestinationDefine.CLOUD_CODE_MERCHANT_RESULT)
    public void updateIntoStatusFromMQ(String json) {
        try {
            Map<String, Object> params = JsonUtil.toObject(Map.class, json);
            String merchantId = (String) (params.get("merchantId"));//商户号
            RedisLock lock = redisLockFactory.getLock(merchantId, 10000L);
            if (lock.blockLock(5L, TimeUnit.SECONDS)) {
                try {
                    String outMerchantId = (String) (params.get("outMerchantId"));//外部商户号
                    Boolean success = (Boolean) params.get("success");//是否成功
                    String channelProvider = (String) params.get("channelProvider");//通道
                    String failReason = (String) params.get("failReason");//错误原因
                    EMemberIntoStatus intoStatus = EMemberIntoStatus.INTO_FAILED;
                    if (success != null && success) {
                        intoStatus = EMemberIntoStatus.INTO_SUCCESS;
                    }
                    //保存云码进件信息
                    YmIntoInfoPo ymIntoInfoPo = ymIntoInfoDao.findByMemberNoAndChannel(merchantId, channelProvider);
                    if (ymIntoInfoPo == null) {
                        ymIntoInfoPo = new YmIntoInfoPo();
                    }
                    ymIntoInfoPo.setMemberNo(merchantId);
                    ymIntoInfoPo.setOutMemberNo(outMerchantId);
                    ymIntoInfoPo.setChannel(channelProvider);
                    ymIntoInfoPo.setStatus(intoStatus.getCode());
                    ymIntoInfoPo.setRemark(failReason);
                    ymIntoInfoDao.save(ymIntoInfoPo);

                    YMMemberPo po = ymMemberDao.findByMemberNo(merchantId);
                    if (po == null) {
                        throw new BizServiceException(EErrorCode.CLOUDCODE_API_GET_ERROR, "云码店铺不存在：" + json);
                    }
                    if (!EMemberIntoStatus.INTO_SUCCESS.equals(po.getMemberIntoStatus())) {
                        //更新状态
                        po.setMemberIntoStatus(intoStatus);
                        ymMemberDao.save(po);
                    }
                } finally {
                    lock.unlock();
                }
            }
        } catch (Exception e) {
            LOGGER.error("进件状态更新失败", e);
        }
    }

    @Override
    @Transactional
    public void updateMemberNoAndAuditStatus(YMMemberDto dto) {
        YMMemberPo po = ymMemberDao.findOne(dto.getId());
        if (po == null) {
            return;
        }
        String odlMemberNo = po.getMemberNo();
        po.setMemberNo(dto.getMemberNo());
        po.setMemberAuditStatus(dto.getMemberAuditStatus());
        po.setActivate(dto.getActivate());
        ymMemberDao.save(po);
        //更新费率memberNo
        List<YmMemberBizPo> ymBizPos = memberBizDao.findByMemberNo(odlMemberNo);
        for (YmMemberBizPo memberBizPo : ymBizPos) {
            memberBizPo.setMemberNo(dto.getMemberNo());
            memberBizDao.save(memberBizPo);
        }
        //更新订单memberNo
        ymTradeDao.updateByMemberNo(dto.getMemberNo(), odlMemberNo);
    }

    @Override
    public YMMemberDto getMemberByStoreId(Long storeId) {
        YMMemberPo po = ymMemberDao.findByStoreId(storeId);
        if (null == po) {
            return null;
        }
        YMMemberDto dto = ConverterService.convert(po, YMMemberDto.class);
        return dto;
    }

    @Override
    public List<YMMemberDto> getMemberListByUserId(String userId) {
        List<YMMemberDto> dtoList = new ArrayList<>();
        List<YMMemberPo> poList = ymMemberDao.findByUserId(userId);
        if (!CollectionUtils.isEmpty(poList)) {
            for (YMMemberPo po : poList) {
                YMMemberDto dto = ConverterService.convert(po, YMMemberDto.class);
                dtoList.add(dto);
            }
        }
        return dtoList;
    }

    @Override
    public YMMemberDto getMemberByQrCodeNo(String qrCodeNo) {
        YMMemberPo po = ymMemberDao.findByQrCodeNo(qrCodeNo);
        if (po == null) {
            return null;
        }
        YMMemberDto dto = ConverterService.convert(po, YMMemberDto.class);
        List<YmMemberBizPo> memberBizPos = memberBizDao.findByMemberNo(po.getMemberNo());
        if (!CollectionUtils.isEmpty(memberBizPos)) {
            memberBizPos.forEach(ymMemberBizPo -> {
                YmMemberBizDto memberBizDto = ConverterService.convert(ymMemberBizPo, YmMemberBizDto.class);
                dto.getMemberBizDtos().add(memberBizDto);
            });
        }
        return dto;
    }

    @Override
    public YmMemberBizDto getMemberBizByMemberNoAndBizCode(String memberNo, EBizCode bizCode) {
        YmMemberBizPo po = memberBizDao.findByMemberNoAndBizCode(memberNo, bizCode);
        if (null == po) {
            return null;
        }
        YmMemberBizDto dto = ConverterService.convert(po, YmMemberBizDto.class);
        return dto;
    }

    @Override
    public List<YMMemberDto> getMemberByStoreIds(List<Long> ids) {
        List<YMMemberPo> pos = ymMemberDao.findByStoreIds(ids);
        List<YMMemberDto> dtos = new ArrayList<>();
        pos.forEach(po -> {
            YMMemberDto dto = ConverterService.convert(po, YMMemberDto.class);
            dtos.add(dto);
        });
        return dtos;
    }

    @Override
    public YMMemberDto getMemberByMemberNo(String memberNo) {
        YMMemberPo po = ymMemberDao.findByMemberNo(memberNo);
        if (null == po) {
            return null;
        }
        YMMemberDto dto = ConverterService.convert(po, YMMemberDto.class);
        return dto;
    }

    @Override
    public List<YMMemberDto> getMemberListByUserIdAndMemberAuditStatus(String userId, EMemberAuditStatus memberAuditStatus) {
        List<YMMemberDto> dtoList = new ArrayList<>();
        List<YMMemberPo> poList = ymMemberDao.findByUserIdAndMemberAuditStatus(userId, memberAuditStatus.getCode());
        if (!CollectionUtils.isEmpty(poList)) {
            for (YMMemberPo po : poList) {
                YMMemberDto dto = ConverterService.convert(po, YMMemberDto.class);
                dtoList.add(dto);
            }
        }
        return dtoList;
    }

    @Override
    public YMMemberDto getMemberByQrCodeNoAndUserId(String qrCodeNo, String userId) {
        YMMemberPo po = ymMemberDao.findByQrCodeNoAndUserId(qrCodeNo, userId);
        if (null == po) {
            return null;
        }
        YMMemberDto dto = ConverterService.convert(po, YMMemberDto.class);
        return dto;
    }

    @Override
    @Transactional
    public void updateMemberActivate(YMMemberDto dto) {
        YMMemberPo po = ymMemberDao.findById(dto.getId());
        if (po == null) {
            return;
        }
        po.setActivate(true);
        ymMemberDao.save(po);
    }

    @Override
    public List<YMMemberDto> getMemberList(YMMemberSearchDto searchDto) {
        Specification<YMMemberPo> spec = (Root<YMMemberPo> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (null != searchDto) {
                if (StringUtils.isNotEmpty(searchDto.getStartBindTime())) {
                    expressions.add(cb.greaterThanOrEqualTo(root.get("bindTime"), DateHelper.getStartDate(searchDto.getStartBindTime())));
                }
                if (StringUtils.isNotEmpty(searchDto.getEndBindTime())) {
                    expressions.add(cb.lessThanOrEqualTo(root.get("bindTime"), DateHelper.getEndDate(searchDto.getEndBindTime())));
                }
                if (StringUtils.isNotEmpty(searchDto.getCreateOpId())) {
                    expressions.add(cb.equal(root.get("createOpId"), searchDto.getCreateOpId()));
                }
                if (!CollectionUtils.isEmpty(searchDto.getIds())) {
                    expressions.add(cb.in(root.get("id")).value(searchDto.getIds()));
                }
                if (!CollectionUtils.isEmpty(searchDto.getCreateOpIds())) {
                    expressions.add(cb.in(root.get("createOpId")).value(searchDto.getCreateOpIds()));
                }
            }
            return predicate;
        };

        List<YMMemberPo> list = ymMemberDao.findAll(spec, new Sort(Direction.DESC, "id"));

        List<YMMemberDto> data = new ArrayList<>();
        for (YMMemberPo po : list) {
            YMMemberDto dto = ConverterService.convert(po, YMMemberDto.class);
            data.add(dto);
        }
        return data;
    }

    @Override
    public List<YMMemberDto> findByBankCardId(Long bankCardId){
        List<YMMemberPo> list = ymMemberDao.findByBankCardId(bankCardId);
        List<YMMemberDto> ymMemberDtos = new ArrayList<>();
        if(!CollectionUtils.isEmpty(list)) {
            for (YMMemberPo po : list) {
                YMMemberDto dto = ConverterService.convert(po, YMMemberDto.class);
                ymMemberDtos.add(dto);
            }
        }

        return ymMemberDtos;
    }

    @Override
    public void memberIntoToCenter(MemberIntoDto memberIntoDto) {
        try{
            //发送云码进件信息推送到会员中心通知
            queueSender.send(DestinationDefine.CLOUD_CODE_MERCHANT_BATCH_CREATION, JSON.toJSONString(memberIntoDto));
        }catch (Exception e) {
            LOGGER.error("发送centerMQ失败", e);
        }
    }

    @Override
    @Transactional
    public void updateMemberDealerUserId(String qrCodeNo, String dealerUserId) {
        YMMemberPo po = ymMemberDao.findByQrCodeNo(qrCodeNo);
        if (po == null || StringUtils.isNotEmpty(po.getDealerUserId())) {
            return;
        }
        po.setDealerUserId(dealerUserId);
        ymMemberDao.save(po);
    }

}
