package com.xinyunlian.jinfu.dealer.dao;

import com.xinyunlian.jinfu.dealer.entity.DealerUserSubscribePo;
import com.xinyunlian.jinfu.dealer.enums.EDealerUserSubscribeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by menglei on 2017年08月03日.
 */
public interface DealerUserSubscribeDao extends JpaRepository<DealerUserSubscribePo, Long>, JpaSpecificationExecutor<DealerUserSubscribePo> {

    DealerUserSubscribePo findByOpenIdAndWechatType(String openId, EDealerUserSubscribeType wechatType);

}
