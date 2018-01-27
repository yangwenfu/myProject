package com.xinyunlian.jinfu.dealer.dao;

import com.xinyunlian.jinfu.dealer.entity.SignInfoViewPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by menglei on 2017年05月03日.
 */
public interface SignInfoViewDao extends JpaRepository<SignInfoViewPo, Long>, JpaSpecificationExecutor<SignInfoViewPo> {


}
