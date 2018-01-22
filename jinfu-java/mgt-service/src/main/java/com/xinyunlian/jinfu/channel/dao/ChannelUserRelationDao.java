package com.xinyunlian.jinfu.channel.dao;

import com.xinyunlian.jinfu.channel.entity.ChannelUserRelationPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 渠道用户DAO接口
 *
 * @author jll
 */
public interface ChannelUserRelationDao extends JpaRepository<ChannelUserRelationPo, Long>, JpaSpecificationExecutor<ChannelUserRelationPo> {

    void deleteByParentUserId(String parentUserId);

    List<ChannelUserRelationPo> findByParentUserId(String parentUserId);

    Long countByParentUserId(String parentUserId);
}
