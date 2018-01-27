package com.xinyunlian.jinfu.qrcode.dao;

import com.xinyunlian.jinfu.qrcode.entity.ScanUserAgentPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 扫码工具DAO接口
 *
 * @author menglei
 */
public interface ScanUserAgentDao extends JpaRepository<ScanUserAgentPo, Long>, JpaSpecificationExecutor<ScanUserAgentPo> {

    @Query(nativeQuery = true, value = "select * from scan_user_agent where LOCATE(USER_AGENT, ?1)>0")
    List<ScanUserAgentPo> findByUserAgent(String UserAgent);

}
