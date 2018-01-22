package com.xinyunlian.jinfu.contract.dao;

import com.xinyunlian.jinfu.contract.entity.ContractBestCompanyPo;
import com.xinyunlian.jinfu.contract.enums.EBsSignType;
import com.xinyunlian.jinfu.contract.enums.ECntrctTmpltType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by dongfangchao on 2017/2/14/0014.
 */
public interface ContractBestCompanyDao extends JpaRepository<ContractBestCompanyPo, Long> {

    List<ContractBestCompanyPo> findByCntrctTmpltTypeAndSignType(ECntrctTmpltType cntrctTmpltType, EBsSignType signType);

}
