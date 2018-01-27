package com.xinyunlian.jinfu.yunma.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.yunma.dao.YmDepotDao;
import com.xinyunlian.jinfu.yunma.dao.YmDepotViewDao;
import com.xinyunlian.jinfu.yunma.dto.YmDepotDto;
import com.xinyunlian.jinfu.yunma.dto.YmDepotSearchDto;
import com.xinyunlian.jinfu.yunma.dto.YmDepotViewDto;
import com.xinyunlian.jinfu.yunma.entity.YmDepotPo;
import com.xinyunlian.jinfu.yunma.entity.YmDepotViewPo;
import com.xinyunlian.jinfu.yunma.enums.EDepotReceiveStatus;
import com.xinyunlian.jinfu.yunma.enums.EDepotStatus;
import com.ylfin.redis.lock.RedisLock;
import com.ylfin.redis.lock.RedisLockFactory;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by menglei on 2017-08-28.
 */
@Service
public class YmDepotServiceImpl implements YmDepotService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YmDepotServiceImpl.class);

    @Autowired
    private YmDepotDao ymDepotDao;

    @Autowired
    private YmDepotViewDao ymDepotViewDao;

    @Autowired
    private RedisLockFactory redisLockFactory;

    @Value("${yunma.qrcode.url}")
    private String YUNMA_QRCODE_URL;

    @Override
    @Transactional(readOnly = true)
    public YmDepotSearchDto getDepotPage(YmDepotSearchDto searchDto) {
        Specification<YmDepotViewPo> spec = (Root<YmDepotViewPo> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (null != searchDto) {
                if (!StringUtils.isEmpty(searchDto.getMobile())) {
                    expressions.add(cb.equal(root.get("mobile"), searchDto.getMobile()));
                }
                if (!StringUtils.isEmpty(searchDto.getQrCodeNo())) {
                    expressions.add(cb.equal(root.get("qrCodeNo"), searchDto.getQrCodeNo()));
                }
                if (searchDto.getStatus() != null) {
                    expressions.add(cb.equal(root.get("status"), searchDto.getStatus()));
                }
            }
            return predicate;
        };

        Pageable pageable = new PageRequest(searchDto.getCurrentPage() - 1,
                searchDto.getPageSize(), Sort.Direction.DESC, "id");
        Page<YmDepotViewPo> page = ymDepotViewDao.findAll(spec, pageable);

        List<YmDepotViewDto> data = new ArrayList<>();
        for (YmDepotViewPo po : page.getContent()) {
            YmDepotViewDto dto = ConverterService.convert(po, YmDepotViewDto.class);
            data.add(dto);
        }
        searchDto.setList(data);
        searchDto.setTotalPages(page.getTotalPages());
        searchDto.setTotalRecord(page.getTotalElements());
        return searchDto;
    }

    @Transactional
    @Override
    public void saveBatch(List<YmDepotDto> ymDepotDtos) throws BizServiceException {
        if (!CollectionUtils.isEmpty(ymDepotDtos)) {
            List<YmDepotPo> list = new ArrayList<>();
            for (YmDepotDto ymDepotDto : ymDepotDtos) {
                YmDepotPo po = ConverterService.convert(ymDepotDto, YmDepotPo.class);
                po.setQrCodeUrl(YUNMA_QRCODE_URL + po.getQrCodeNo());
                list.add(po);
            }
            if (!CollectionUtils.isEmpty(list)) {
                ymDepotDao.save(list);
            }
        }
    }

    @Override
    public List<YmDepotDto> findByQrCodeNo(List<String> qrCodeNo) {
        List<YmDepotDto> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(qrCodeNo)) {
            List<YmDepotPo> poList = ymDepotDao.findByQrcodeNo(qrCodeNo);
            for (YmDepotPo po : poList) {
                YmDepotDto dto = ConverterService.convert(po, YmDepotDto.class);
                list.add(dto);
            }
        }
        return list;
    }

    @Transactional
    @Override
    public void updateBatchUsed(List<String> qrCodeNo) throws BizServiceException {
        if (!CollectionUtils.isEmpty(qrCodeNo)) {
            List<YmDepotPo> poList = ymDepotDao.findByQrcodeNo(qrCodeNo);
            List<YmDepotPo> list = new ArrayList<>();
            for (YmDepotPo dto : poList) {
                if (EDepotStatus.UNBIND_UNUSE.equals(dto.getStatus())) {
                    dto.setStatus(EDepotStatus.UNBIND_USE);
                } else if (EDepotStatus.BIND_UNUSE.equals(dto.getStatus())) {
                    dto.setStatus(EDepotStatus.BIND_USE);
                }
                dto.setReceiveStatus(EDepotReceiveStatus.RECEIVE);
                list.add(dto);
            }
            if (!CollectionUtils.isEmpty(list)) {
                ymDepotDao.save(list);
            }
        }
    }

    @Override
    public List<YmDepotViewDto> findErrorQrCodeNo() {
        List<YmDepotViewDto> list = new ArrayList<>();
        List<YmDepotViewPo> poList = ymDepotViewDao.findErrorQrCodeNo();
        for (YmDepotViewPo po : poList) {
            YmDepotViewDto dto = ConverterService.convert(po, YmDepotViewDto.class);
            list.add(dto);
        }
        return list;
    }

    @Transactional
    @Override
    public YmDepotDto findNewBind() throws BizServiceException {
        YmDepotPo po = ymDepotDao.findUnBindAndUnUse();
        if (po == null || !EDepotStatus.UNBIND_UNUSE.equals(po.getStatus())) {
            return null;
        }
        YmDepotPo ymDepotPo = new YmDepotPo();
        RedisLock lock = redisLockFactory.getLock(po.getQrCodeNo(), 5000L);
        try {
            if (lock.blockLock(3L, TimeUnit.SECONDS)) {
                try {
                    po.setStatus(EDepotStatus.BIND_UNUSE);
                    ymDepotPo = ymDepotDao.save(po);
                } finally {
                    lock.unlock();
                }
            }
        } catch (InterruptedException e) {
            LOGGER.error("获取未绑定未使用云码并更新云码已绑定失败", e);
            throw new BizServiceException(EErrorCode.COMM_INTERNAL_ERROR);
        }
        return ConverterService.convert(ymDepotPo, YmDepotDto.class);
    }

    @Transactional
    @Override
    public void updateMailInfo(YmDepotDto ymDepotDto) throws BizServiceException {
        YmDepotPo po = ymDepotDao.findByQrCodeNo(ymDepotDto.getQrCodeNo());
        if (po == null || !EDepotStatus.BIND_UNUSE.equals(po.getStatus())) {
            return;
        }
        po.setMailName(ymDepotDto.getMailName());
        po.setMailMobile(ymDepotDto.getMailMobile());
        po.setMailAddress(ymDepotDto.getMailAddress());
        ymDepotDao.save(po);
    }

    @Override
    public List<YmDepotViewDto> findByYmIds(List<String> ymIds) {
        List<YmDepotViewDto> list = new ArrayList<>();
        List<YmDepotViewPo> poList = ymDepotViewDao.findByYmIdIn(ymIds);
        for (YmDepotViewPo po : poList) {
            YmDepotViewDto dto = ConverterService.convert(po, YmDepotViewDto.class);
            list.add(dto);
        }
        return list;
    }

    @Override
    public YmDepotDto findByQrCodeNo(String qrCodeNo) {
        YmDepotPo po = ymDepotDao.findByQrCodeNo(qrCodeNo);
        return ConverterService.convert(po, YmDepotDto.class);
    }

    @Transactional
    @Override
    public void updateStatusAndReceiveStatus(YmDepotDto ymDepotDto) throws BizServiceException {
        YmDepotPo po = ymDepotDao.findByQrCodeNo(ymDepotDto.getQrCodeNo());
        if (po == null) {
            return;
        }
        if (ymDepotDto.getStatus() != null) {
            po.setStatus(ymDepotDto.getStatus());
        }
        if (ymDepotDto.getReceiveStatus() != null) {
            po.setReceiveStatus(ymDepotDto.getReceiveStatus());
        }
        ymDepotDao.save(po);
    }

    @Override
    public List<YmDepotDto> findBind() {
        List<YmDepotDto> list = new ArrayList<>();
        List<YmDepotPo> poList = ymDepotDao.findBind();
        for (YmDepotPo po : poList) {
            YmDepotDto dto = ConverterService.convert(po, YmDepotDto.class);
            list.add(dto);
        }
        return list;
    }

}
