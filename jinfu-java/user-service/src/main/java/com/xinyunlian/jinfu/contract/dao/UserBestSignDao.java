package com.xinyunlian.jinfu.contract.dao;
import com.xinyunlian.jinfu.contract.entity.UserBestSignPo;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Created by dongfangchao on 2017/2/8/0008.
 */
public interface UserBestSignDao extends JpaRepository<UserBestSignPo, Long> {
    UserBestSignPo findByUserId(String userId);
}