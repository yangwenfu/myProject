package com.xinyunlian.jinfu.yunma.dao;

import com.xinyunlian.jinfu.yunma.entity.YmThirdUserPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by menglei on 2017年01月04日.
 */
public interface YmThirdUserDao extends JpaRepository<YmThirdUserPo, Long>, JpaSpecificationExecutor<YmThirdUserPo> {

    YmThirdUserPo findByUserId(String userId);

    YmThirdUserPo findByMemberIdAndThirdConfigId(Long memberId, Long thirdConfigId);

    YmThirdUserPo findByMemberId(Long memberId);

}
