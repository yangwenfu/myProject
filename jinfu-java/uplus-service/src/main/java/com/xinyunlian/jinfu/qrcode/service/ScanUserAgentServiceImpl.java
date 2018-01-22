package com.xinyunlian.jinfu.qrcode.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.qrcode.dao.ScanUserAgentDao;
import com.xinyunlian.jinfu.qrcode.dto.ScanUserAgentDto;
import com.xinyunlian.jinfu.qrcode.entity.ScanUserAgentPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by menglei on 2017年03月08日.
 */
@Service
public class ScanUserAgentServiceImpl implements ScanUserAgentService {

    @Autowired
    private ScanUserAgentDao scanUserAgentDao;

    @Override
    @Transactional
    public ScanUserAgentDto getByUserAgent(String userAgent) {
        List<ScanUserAgentPo> list = scanUserAgentDao.findByUserAgent(userAgent);
        if (list.isEmpty()) {
            return null;
        }
        ScanUserAgentPo po = list.get(0);
        ScanUserAgentDto dto = ConverterService.convert(po, ScanUserAgentDto.class);
        return dto;
    }

}
