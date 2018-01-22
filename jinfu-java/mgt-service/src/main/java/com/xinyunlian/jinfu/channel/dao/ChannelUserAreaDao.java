package com.xinyunlian.jinfu.channel.dao;

import com.xinyunlian.jinfu.channel.entity.ChannelUserAreaPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 渠道人员负责地区DAO接口
 *
 * @author jll
 */
public interface ChannelUserAreaDao extends JpaRepository<ChannelUserAreaPo, Long>, JpaSpecificationExecutor<ChannelUserAreaPo> {
    void deleteByUserId(String userId);

    List<ChannelUserAreaPo> findByUserId(String userId);

    Long countByUserId(String userId);
}
