package com.xinyunlian.jinfu.report.dealer.service;

import com.xinyunlian.jinfu.report.dealer.dao.DealerStatsMonthDao;
import com.xinyunlian.jinfu.report.dealer.dto.DealerStatsMonthDto;
import com.xinyunlian.jinfu.report.dealer.dto.DealerStatsMonthObjectDto;
import com.xinyunlian.jinfu.report.dealer.dto.DealerStatsMonthSearchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bright on 2016/11/29.
 */
@Service
public class DealerStatsMonthServiceImpl implements DealerStatsMonthService {

    @Autowired
    protected DealerStatsMonthDao dealerStatsMonthDao;

    @Transactional
    @Override
    public DealerStatsMonthSearchDto getStatsMonthPageByUserId(DealerStatsMonthSearchDto dealerStatsMonthSearchDto) {
        dealerStatsMonthSearchDto.setPageSize(10000);
        dealerStatsMonthSearchDto.setCurrentPage(1);
        dealerStatsMonthSearchDto.setTotalRecord(1l);
        dealerStatsMonthSearchDto.setTotalPages(1);

        List<DealerStatsMonthDto> list = new ArrayList<>();
        List<Object[]> statsList = dealerStatsMonthDao.findMonthByUserId(dealerStatsMonthSearchDto.getUserId());
        for (Object[] object : statsList) {
            DealerStatsMonthDto dealerStatsMonthDto = new DealerStatsMonthDto();
            dealerStatsMonthDto.setMonthDate(object[0].toString());
            dealerStatsMonthDto.setRegisterCount(Long.valueOf(object[1].toString()));
            dealerStatsMonthDto.setQrCodeCount(Long.valueOf(object[2].toString()));
            dealerStatsMonthDto.setNoteCount(Long.valueOf(object[3].toString()));
            list.add(dealerStatsMonthDto);
        }
        dealerStatsMonthSearchDto.setList(list);

        return dealerStatsMonthSearchDto;
    }

    @Transactional
    @Override
    public DealerStatsMonthSearchDto getStatsMonthPageByDealerId(DealerStatsMonthSearchDto dealerStatsMonthSearchDto) {
        dealerStatsMonthSearchDto.setPageSize(10000);
        dealerStatsMonthSearchDto.setCurrentPage(1);
        dealerStatsMonthSearchDto.setTotalRecord(1l);
        dealerStatsMonthSearchDto.setTotalPages(1);

        List<DealerStatsMonthDto> list = new ArrayList<>();
        List<Object[]> statsList = dealerStatsMonthDao.findMonthByDealerId(dealerStatsMonthSearchDto.getDealerId());
        for (Object[] object : statsList) {
            DealerStatsMonthDto dealerStatsMonthDto = new DealerStatsMonthDto();
            dealerStatsMonthDto.setMonthDate(object[0].toString());
            dealerStatsMonthDto.setRegisterCount(Long.valueOf(object[1].toString()));
            dealerStatsMonthDto.setQrCodeCount(Long.valueOf(object[2].toString()));
            dealerStatsMonthDto.setNoteCount(Long.valueOf(object[3].toString()));
            list.add(dealerStatsMonthDto);
        }
        dealerStatsMonthSearchDto.setList(list);

        return dealerStatsMonthSearchDto;
    }

    /**
     * 按分销员id 统计月订单
     * @param userId
     * @return
     */
    @Transactional
    @Override
    public List<DealerStatsMonthObjectDto> getMonthSignInfoByUserId(String userId, List<String> months) {
        List<DealerStatsMonthObjectDto> list = new ArrayList<>();
        List<Object[]> statsList = dealerStatsMonthDao.findMonthSignInfoByUserId(userId, months);
        for (Object[] object : statsList) {
            DealerStatsMonthObjectDto dealerStatsMonthObjectDto = new DealerStatsMonthObjectDto();
            dealerStatsMonthObjectDto.setMonthDate(object[0].toString());
            dealerStatsMonthObjectDto.setUserId(object[1].toString());
            dealerStatsMonthObjectDto.setDealerId(object[2].toString());
            dealerStatsMonthObjectDto.setCount(Long.valueOf(object[3].toString()));

            list.add(dealerStatsMonthObjectDto);
        }
        return list;
    }

    /**
     * 按分销商id 统计月订单
     * @param dealerId
     * @return
     */
    @Transactional
    @Override
    public List<DealerStatsMonthObjectDto> getMonthSignInfoByDealerId(String dealerId, List<String> months) {
        List<DealerStatsMonthObjectDto> list = new ArrayList<>();
        List<Object[]> statsList = dealerStatsMonthDao.findMonthSignInfoByDealerId(dealerId, months);
        for (Object[] object : statsList) {
            DealerStatsMonthObjectDto dealerStatsMonthObjectDto = new DealerStatsMonthObjectDto();
            dealerStatsMonthObjectDto.setMonthDate(object[0].toString());
            dealerStatsMonthObjectDto.setUserId(object[1].toString());
            dealerStatsMonthObjectDto.setDealerId(object[2].toString());
            dealerStatsMonthObjectDto.setCount(Long.valueOf(object[3].toString()));

            list.add(dealerStatsMonthObjectDto);
        }
        return list;
    }

    /**
     * 按分销员id 统计月订单
     * @param userId
     * @return
     */
    @Transactional
    @Override
    public List<DealerStatsMonthObjectDto> getMonthMemberByUserId(String userId, List<String> months) {
        List<DealerStatsMonthObjectDto> list = new ArrayList<>();
        List<Object[]> statsList = dealerStatsMonthDao.findMonthMemberByUserId(userId, months);
        for (Object[] object : statsList) {
            DealerStatsMonthObjectDto dealerStatsMonthObjectDto = new DealerStatsMonthObjectDto();
            dealerStatsMonthObjectDto.setMonthDate(object[0].toString());
            dealerStatsMonthObjectDto.setUserId(object[1].toString());
            dealerStatsMonthObjectDto.setCount(Long.valueOf(object[2].toString()));

            list.add(dealerStatsMonthObjectDto);
        }
        return list;
    }

    /**
     * 按分销商id 统计月订单
     * @param dealerId
     * @return
     */
    @Transactional
    @Override
    public List<DealerStatsMonthObjectDto> getMonthMemberByDealerId(String dealerId, List<String> months) {
        List<DealerStatsMonthObjectDto> list = new ArrayList<>();
        List<Object[]> statsList = dealerStatsMonthDao.findMonthMemberByDealerId(dealerId, months);
        for (Object[] object : statsList) {
            DealerStatsMonthObjectDto dealerStatsMonthObjectDto = new DealerStatsMonthObjectDto();
            dealerStatsMonthObjectDto.setMonthDate(object[0].toString());
            dealerStatsMonthObjectDto.setUserId(object[1].toString());
            dealerStatsMonthObjectDto.setCount(Long.valueOf(object[2].toString()));

            list.add(dealerStatsMonthObjectDto);
        }
        return list;
    }

}
