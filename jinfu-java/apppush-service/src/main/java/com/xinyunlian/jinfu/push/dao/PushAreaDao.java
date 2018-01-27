package com.xinyunlian.jinfu.push.dao;

import com.xinyunlian.jinfu.push.entity.PushAreaPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by apple on 2017/1/10.
 */
public interface PushAreaDao extends JpaRepository<PushAreaPo, String>, JpaSpecificationExecutor<PushAreaPo> {

    Long countByMessageId(Long messageId);

    List<PushAreaPo> findByMessageId(Long messageId);

    void deleteByMessageId(Long messageId);

}
