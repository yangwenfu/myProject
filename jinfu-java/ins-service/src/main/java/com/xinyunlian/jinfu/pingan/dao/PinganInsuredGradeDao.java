package com.xinyunlian.jinfu.pingan.dao;

import com.xinyunlian.jinfu.pingan.entity.PinganInsuredGradePo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by dongfangchao on 2017/6/12/0012.
 */
public interface PinganInsuredGradeDao extends JpaSpecificationExecutor<PinganInsuredGradePo>, JpaRepository<PinganInsuredGradePo, Long> {
}
