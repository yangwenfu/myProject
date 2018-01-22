package com.xinyunlian.jinfu.insurance.dao;

import com.xinyunlian.jinfu.insurance.entity.VehicleInsDetailPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by dongfangchao on 2016/12/5/0005.
 */
public interface VehicleInsDetailDao extends JpaRepository<VehicleInsDetailPo, Long>, JpaSpecificationExecutor<VehicleInsDetailPo> {

    VehicleInsDetailPo findByPerInsuranceOrderNo(String perInsuranceOrderNo);

}
