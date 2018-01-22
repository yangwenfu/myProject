package com.xinyunlian.jinfu.loan.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.loan.dao.LinkRepaySchdDao;
import com.xinyunlian.jinfu.loan.dto.repay.LinkRepayDto;
import com.xinyunlian.jinfu.loan.dto.req.LoanRepayLinkDto;
import com.xinyunlian.jinfu.loan.entity.LinkRepaySchdPo;
import com.xinyunlian.jinfu.loan.entity.RepayDtlPo;
import com.xinyunlian.jinfu.repay.dao.RepayDao;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Willwang on 2016/11/16.
 */
@Service
public class InnerLinkRepayServiceImpl implements InnerLinkRepayService {

    @Autowired
    private LinkRepaySchdDao linkRepaySchdDao;

    @Override
    @Transactional
    public LinkRepayDto save(LinkRepayDto linkRepayDto) {
        LinkRepaySchdPo po;
        if (linkRepayDto.getRepayId() == null) {
            po = new LinkRepaySchdPo();
        } else {
            po = linkRepaySchdDao.findOne(linkRepayDto.getJnlNo());
        }

        if (po.getCreateTs() != null) {
            po.setVersionCt(0L);
        }
        ConverterService.convert(linkRepayDto, po);
        po = linkRepaySchdDao.save(po);
        return ConverterService.convert(po, LinkRepayDto.class);
    }

    @Override
    public void add(String repayId, String scheduleId) {
        LinkRepaySchdPo po = new LinkRepaySchdPo();
        po.setRepayId(repayId);
        po.setSchdId(scheduleId);
        linkRepaySchdDao.save(po);
    }

    @Override
    public void add(LoanRepayLinkDto loanRepayLinkDto) {
        LinkRepaySchdPo po = ConverterService.convert(loanRepayLinkDto, LinkRepaySchdPo.class);
        po.setSchdId(loanRepayLinkDto.getScheduleId());
        linkRepaySchdDao.save(po);
    }

    @Override
    public List<LinkRepayDto> findByScheduleId(String scheduleId) {
        List<LinkRepaySchdPo> list = linkRepaySchdDao.findBySchdId(scheduleId);

        List<LinkRepayDto> rs = new ArrayList<>();

        for (LinkRepaySchdPo linkRepaySchdPo : list) {
            rs.add(ConverterService.convert(linkRepaySchdPo, LinkRepayDto.class));
        }

        return rs;
    }

    @Override
    public List<LinkRepayDto> findByScheduleIds(Collection<String> schedules) throws BizServiceException {
        List<LinkRepaySchdPo> linkRepaySchdPos = linkRepaySchdDao.findBySchdIdIn(schedules);

        if(CollectionUtils.isEmpty(linkRepaySchdPos)){
            return new ArrayList<>();
        }

        return ConverterService.convertToList(linkRepaySchdPos, LinkRepayDto.class);
    }

    @Override
    public List<LinkRepayDto> findByRepayId(String repayId) {
        List<LinkRepaySchdPo> list = linkRepaySchdDao.findByRepayId(repayId);

        List<LinkRepayDto> rs = new ArrayList<>();

        for (LinkRepaySchdPo linkRepaySchdPo : list) {
            rs.add(ConverterService.convert(linkRepaySchdPo, LinkRepayDto.class));
        }

        return rs;
    }


    @Override
    public LinkRepayDto findByRepayIdAndScheduleId(String repayId, String scheduleId) throws BizServiceException {
        LinkRepaySchdPo po = linkRepaySchdDao.findByRepayIdAndSchdId(repayId, scheduleId);

        if(po == null){
            return null;
        }

        return ConverterService.convert(po, LinkRepayDto.class);
    }
}
