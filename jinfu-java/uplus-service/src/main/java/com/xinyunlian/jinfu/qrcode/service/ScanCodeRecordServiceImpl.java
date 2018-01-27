package com.xinyunlian.jinfu.qrcode.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.qrcode.dao.ScanCodeRecordDao;
import com.xinyunlian.jinfu.qrcode.dto.ScanCodeRecordDto;
import com.xinyunlian.jinfu.qrcode.entity.ScanCodeRecordPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by menglei on 2017年03月08日.
 */
@Service
public class ScanCodeRecordServiceImpl implements ScanCodeRecordService {

    @Autowired
    private ScanCodeRecordDao scanCodeRecordDao;

    @Override
    public List<ScanCodeRecordDto> findByProdCodeId(Long prodCodeId) {
        List<ScanCodeRecordPo> poList = scanCodeRecordDao.findByProdCodeId(prodCodeId);
        List<ScanCodeRecordDto> dtoList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(poList)) {
            for (ScanCodeRecordPo po : poList) {
                ScanCodeRecordDto dto = ConverterService.convert(po, ScanCodeRecordDto.class);
                dtoList.add(dto);
            }
        }
        return dtoList;
    }

    @Transactional
    @Override
    public void addScanCodeRecord(ScanCodeRecordDto scanCodeRecordDto) throws BizServiceException {
        if (scanCodeRecordDto != null) {
            ScanCodeRecordPo scanCodeRecordPo = ConverterService.convert(scanCodeRecordDto, ScanCodeRecordPo.class);
            scanCodeRecordDao.save(scanCodeRecordPo);
        }
    }

}
