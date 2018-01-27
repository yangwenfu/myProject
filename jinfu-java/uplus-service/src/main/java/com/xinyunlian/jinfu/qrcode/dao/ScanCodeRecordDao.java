package com.xinyunlian.jinfu.qrcode.dao;

import com.xinyunlian.jinfu.qrcode.entity.ScanCodeRecordPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 扫码记录DAO接口
 *
 * @author menglei
 */
public interface ScanCodeRecordDao extends JpaRepository<ScanCodeRecordPo, Long>, JpaSpecificationExecutor<ScanCodeRecordPo> {

    List<ScanCodeRecordPo> findByProdCodeId(Long prodCodeId);

}
