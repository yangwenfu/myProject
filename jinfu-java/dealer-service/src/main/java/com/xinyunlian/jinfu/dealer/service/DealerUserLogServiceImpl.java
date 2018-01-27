package com.xinyunlian.jinfu.dealer.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.dealer.dao.DealerUserLogDao;
import com.xinyunlian.jinfu.dealer.dto.DealerDto;
import com.xinyunlian.jinfu.dealer.dto.DealerUserLogDto;
import com.xinyunlian.jinfu.dealer.dto.DealerUserLogSearchDto;
import com.xinyunlian.jinfu.dealer.entity.DealerPo;
import com.xinyunlian.jinfu.dealer.entity.DealerUserLogPo;
import com.xinyunlian.jinfu.dealer.enums.EDealerUserLogType;
import com.xinyunlian.jinfu.user.dto.DealerUserDto;
import com.xinyunlian.jinfu.user.entity.DealerUserPo;
import org.apache.commons.lang.StringUtils;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by menglei on 2016年09月02日.
 */
@Service
public class DealerUserLogServiceImpl implements DealerUserLogService {

    @Autowired
    private DealerUserLogDao dealerUserLogDao;

    @Override
    @Transactional
    public DealerUserLogSearchDto getUserLogPage(DealerUserLogSearchDto dealerUserLogSearchDto) {

        Specification<DealerUserLogPo> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (dealerUserLogSearchDto != null) {
                if (StringUtils.isNotEmpty(dealerUserLogSearchDto.getName())) {
                    expressions.add(cb.like(root.<DealerUserPo>get("dealerUserPo").get("name"), "%" + dealerUserLogSearchDto.getName() + "%"));
                }
                if (StringUtils.isNotEmpty(dealerUserLogSearchDto.getDealerName())) {
                    expressions.add(cb.like(root.<DealerPo>get("dealerPo").get("dealerName"), "%" + dealerUserLogSearchDto.getDealerName() + "%"));
                }
                if (StringUtils.isNotEmpty(dealerUserLogSearchDto.getBeginTime())) {
                    expressions.add(cb.greaterThanOrEqualTo(root.get("createTs"), DateHelper.getStartDate(dealerUserLogSearchDto.getBeginTime())));
                }
                if (StringUtils.isNotEmpty(dealerUserLogSearchDto.getEndTime())) {
                    expressions.add(cb.lessThanOrEqualTo(root.get("createTs"), DateHelper.getEndDate(dealerUserLogSearchDto.getEndTime())));
                }
                if (dealerUserLogSearchDto.getType() != null) {
                    expressions.add(cb.equal(root.get("type"), dealerUserLogSearchDto.getType()));
                }

            }
            return predicate;
        };

        Pageable pageable = new PageRequest(dealerUserLogSearchDto.getCurrentPage() - 1, dealerUserLogSearchDto.getPageSize(), Sort.Direction.DESC, "logId");
        Page<DealerUserLogPo> page = dealerUserLogDao.findAll(spec, pageable);

        List<DealerUserLogDto> data = new ArrayList<>();
        page.getContent().forEach(po -> {
            DealerUserLogDto dealerUserLogDto = ConverterService.convert(po, DealerUserLogDto.class);
            dealerUserLogDto.setCreateTime(DateHelper.formatDate(po.getCreateTs(), "yyyy-MM-dd HH:mm:ss"));
            if (po.getDealerUserPo() != null) {
                DealerUserDto dealerUserDto = ConverterService.convert(po.getDealerUserPo(), DealerUserDto.class);
                if (dealerUserDto.getMobile().length() > 11) {
                    dealerUserDto.setMobile(dealerUserDto.getMobile().split("-")[1]);
                }
                dealerUserLogDto.setDealerUserDto(dealerUserDto);
            }
            if (po.getDealerPo() != null) {
                DealerDto dealerDto = ConverterService.convert(po.getDealerPo(), DealerDto.class);
                dealerUserLogDto.setDealerDto(dealerDto);
            }
            dealerUserLogDto.setRemark(dealerUserLogDto.getRemark().replace("业务办理:orderNo=", "订单编号:").replace("记录添加:noteId=", "记录编号:"));
            data.add(dealerUserLogDto);
        });

        dealerUserLogSearchDto.setList(data);
        dealerUserLogSearchDto.setTotalPages(page.getTotalPages());
        dealerUserLogSearchDto.setTotalRecord(page.getTotalElements());

        return dealerUserLogSearchDto;
    }

    @Transactional
    @Override
    public void createDealerUserLog(DealerUserLogDto dealerUserLogDto) {
        if (dealerUserLogDto != null) {
            DealerUserLogPo dealerUserLogPo = ConverterService.convert(dealerUserLogDto, DealerUserLogPo.class);
            dealerUserLogDao.save(dealerUserLogPo);
        }
    }

    @Transactional
    @Override
    public void createDealerUserLog(DealerUserDto dealerUserDto, String lng, String lat, String address,
                                    String storeUserId, String storeId, String remark, EDealerUserLogType type) {
        DealerUserLogDto dealerUserLogDto = new DealerUserLogDto();
        dealerUserLogDto.setUserId(dealerUserDto.getUserId());
        dealerUserLogDto.setDealerId(dealerUserDto.getDealerDto().getDealerId());
        dealerUserLogDto.setLng(lng);
        dealerUserLogDto.setLat(lat);
        dealerUserLogDto.setAddress(address);
        dealerUserLogDto.setType(type);
        dealerUserLogDto.setStoreUserId(storeUserId);
        if (StringUtils.isNotEmpty(storeId)) {
            dealerUserLogDto.setStoreId(storeId);
        }
        dealerUserLogDto.setRemark(remark);
        this.createDealerUserLog(dealerUserLogDto);
    }

    @Override
    @Transactional
    public List<DealerUserLogDto> findByStoreUserIdAndType(String storeUserId,EDealerUserLogType type){
        List<DealerUserLogDto> dealerUserLogDtos = new ArrayList<>();

        List<DealerUserLogPo> dealerUserLogPos = dealerUserLogDao.findByStoreUserIdAndType(storeUserId,type);

        if(!CollectionUtils.isEmpty(dealerUserLogPos)){
            dealerUserLogPos.forEach(dealerUserLogPo -> {
                DealerUserLogDto dealerUserLogDto = ConverterService.convert(dealerUserLogPo,DealerUserLogDto.class);
                DealerUserDto dealerUserDto = ConverterService
                        .convert(dealerUserLogPo.getDealerUserPo(),DealerUserDto.class);
                DealerDto dealerDto = ConverterService
                        .convert(dealerUserLogPo.getDealerPo(),DealerDto.class);

                dealerUserLogDto.setDealerUserDto(dealerUserDto);
                dealerUserLogDto.setDealerDto(dealerDto);
                dealerUserLogDtos.add(dealerUserLogDto);
            });
        }

        return dealerUserLogDtos;
    }

}
