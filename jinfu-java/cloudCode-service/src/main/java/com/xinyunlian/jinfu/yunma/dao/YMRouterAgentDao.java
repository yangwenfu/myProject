package com.xinyunlian.jinfu.yunma.dao;

import com.xinyunlian.jinfu.yunma.entity.YMRouterAgentPo;
import com.xinyunlian.jinfu.yunma.enums.ERouterAgentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by menglei on 2017年01月04日.
 */
public interface YMRouterAgentDao extends JpaRepository<YMRouterAgentPo, Long>, JpaSpecificationExecutor<YMRouterAgentPo> {

    YMRouterAgentPo findByUserAgentAndStatus(String userAgent, ERouterAgentStatus status);

    YMRouterAgentPo findByUserAgent(String userAgent);

    @Query(nativeQuery = true, value = "select * from ym_router_agent where STATUS='0' and LOCATE(USER_AGENT, ?1)>0")
    List<YMRouterAgentPo> findLocalByUserAgent(String UserAgent);

}
