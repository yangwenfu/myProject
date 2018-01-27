package com.xinyunlian.jinfu.user.dao;

import com.xinyunlian.jinfu.user.entity.ClientSaltPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by DongFC on 2016-10-19.
 */
public interface ClientSaltDao extends JpaRepository<ClientSaltPo, Long>, JpaSpecificationExecutor<ClientSaltPo> {

    ClientSaltPo findByClientId(String clientId);

}
