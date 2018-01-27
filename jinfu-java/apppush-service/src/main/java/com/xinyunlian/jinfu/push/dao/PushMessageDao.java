package com.xinyunlian.jinfu.push.dao;

import com.xinyunlian.jinfu.push.entity.PushMessagePo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by apple on 2017/1/3.
 */
public interface PushMessageDao extends JpaRepository<PushMessagePo, String>, JpaSpecificationExecutor<PushMessagePo> {

    @Query("SELECT a.messageId,a.title,a.imagePath,a.description,a.url,a.createTs,b.readState FROM PushMessagePo a, PushReadStatePo b WHERE a.messageId = b.messageId AND b.userId = ?1")
    List<Object[]> findByUserId(String userId);

    @Query("FROM PushMessagePo a WHERE a.pushTime < ?1 And a.pushStates = 0")
    List<PushMessagePo> findMessageByPushTime(String pushTime);

    PushMessagePo findByMessageId(Long messageId);

    void deleteByMessageId(Long messageId);

    @Query(nativeQuery = true, value = "UPDATE push_message SET PUSH_STATE = ?1 WHERE MESSAGE_ID = ?2 ")
    @Modifying
    void updatePushState(Long pushTates, Long messageId);

}
