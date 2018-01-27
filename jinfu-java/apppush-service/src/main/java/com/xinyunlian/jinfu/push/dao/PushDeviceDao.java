package com.xinyunlian.jinfu.push.dao;

import com.xinyunlian.jinfu.push.entity.PushDevicePo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by apple on 2017/1/12.
 */
public interface PushDeviceDao extends JpaRepository<PushDevicePo, String>, JpaSpecificationExecutor<PushDevicePo> {

    PushDevicePo findByUserIdAndAppType(String userId,int appType);

    @Query("FROM PushDevicePo a WHERE a.pushToken = ?1 ")
    PushDevicePo findUserWithRegistrationId(String pushToken);

    PushDevicePo findByPushToken(String pushToken);

    void deleteByUserId(String userId);
}
