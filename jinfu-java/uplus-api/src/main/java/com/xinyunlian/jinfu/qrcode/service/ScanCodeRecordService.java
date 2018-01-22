package com.xinyunlian.jinfu.qrcode.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.qrcode.dto.ScanCodeRecordDto;

import java.util.List;

/**
 * Created by menglei on 2017年03月08日.
 */
public interface ScanCodeRecordService {

    List<ScanCodeRecordDto> findByProdCodeId(Long prodCodeId);

    void addScanCodeRecord(ScanCodeRecordDto scanCodeRecordDto) throws BizServiceException;

}
