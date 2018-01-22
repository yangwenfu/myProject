package com.xinyunlian.jinfu.loan.service;


import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.loan.dao.LinkRepaySchdDao;
import com.xinyunlian.jinfu.loan.dto.repay.LinkRepayDto;
import com.xinyunlian.jinfu.loan.dto.req.LoanRepayLinkDto;
import com.xinyunlian.jinfu.loan.entity.LinkRepaySchdPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Willwang
 */
@Service
public class LinkRepaySchdServiceImpl implements LinkRepaySchdService{

    @Autowired
    private InnerLinkRepayService innerLinkRepayService;

    @Autowired
    private LinkRepaySchdDao linkRepaySchdDao;

    @Override
    public void add(String repayId, String scheduleId) {
        innerLinkRepayService.add(repayId, scheduleId);
    }

    @Override
    public void  add(LoanRepayLinkDto loanRepayLinkDto) {
        innerLinkRepayService.add(loanRepayLinkDto);
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
}