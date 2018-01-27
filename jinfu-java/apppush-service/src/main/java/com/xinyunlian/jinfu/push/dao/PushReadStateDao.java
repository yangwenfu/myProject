package com.xinyunlian.jinfu.push.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.xinyunlian.jinfu.push.entity.PushReadStatePo;

/**
 * Created by apple on 2017/1/4.
 */
public interface PushReadStateDao extends JpaRepository<PushReadStatePo, String>, JpaSpecificationExecutor<PushReadStatePo> {

    Long countByUserIdAndReadState(String userId,String readState);

    PushReadStatePo findByMessageIdAndUserId(Long messageId,String userId);

    @Query("SELECT a.id FROM PushReadStatePo a,PushMessagePo b WHERE a.messageId = b.messageId And b.pushStates = 2 And a.readState = 0 And a.userId = ?1 And b.pushObject = ?2")
    List<Object[]> getUnReadByUserId(String userId,int pushObject);

    @Query("SELECT a.userId FROM PushReadStatePo a,PushMessagePo b WHERE a.messageId = b.messageId And b.pushStates = 0 And a.userId = ?1 ")
    List<Object[]> findUserIdByMessageId(String userId);

    void deleteByMessageId(Long messageId);

    @Query("SELECT c.pushToken FROM PushReadStatePo b, PushDevicePo c  WHERE b.userId = c.userId AND b.messageId = ?1")
    List<String> findDeviceByMessageId(Long messageId);

    @Query("SELECT b.id, c.pushToken FROM PushReadStatePo b, PushDevicePo c  WHERE b.userId = c.userId AND b.messageId = ?1 AND b.pushStates = ?2 AND c.appType = ?3")
    List<Object[]> findMessagesByMessageId(Long messageId, int pushStates ,int pushObject);

    @Query(nativeQuery = true, value = "UPDATE push_readstate SET PUSH_STATES = ?1 WHERE MESSAGE_ID = ?2 ")
    @Modifying
    void updatePushStates(Integer pushStates, Long messageId);

    @Query(nativeQuery = true, value = "UPDATE push_readstate SET RETRY_TIMES = RETRY_TIMES + 1, PUSH_STATES = ?1 WHERE ID = ?2 ")
    @Modifying
    void updatePushStatesAndRetryTimes(Integer pushStates, Long id);

    @Query(nativeQuery = true, value = "UPDATE push_readstate SET PUSH_STATES = ?1 WHERE id in ?2 ")
    @Modifying
    void updatePushStates(Integer pushStates, List<Long> ids);
    @Query(nativeQuery = true, value = "UPDATE push_readstate SET RETRY_TIMES = RETRY_TIMES + 1, PUSH_STATES = ?1 WHERE ID in ?2 ")
    @Modifying
    void updatePushStatesAndRetryTimes(Integer pushStates,  List<Long> ids);

    @Query("SELECT b.id, b.messageId, c.pushToken FROM PushReadStatePo b, PushDevicePo c  WHERE b.userId = c.userId AND b.pushStates = ?1 AND b.retryTimes < ?2 AND c.appType = ?3 ORDER BY b.messageId ")
    Page<Object[]> findByPushStatesAndRetryTimesLessThanOrderByMessageId(int pushStates, int maxRetryTimes, Pageable pageable,int pushObject);
}
