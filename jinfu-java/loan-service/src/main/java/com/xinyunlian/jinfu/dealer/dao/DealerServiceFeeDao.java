package com.xinyunlian.jinfu.dealer.dao;

import com.xinyunlian.jinfu.dealer.entity.DealerServiceFeePo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DealerServiceFeeDao extends JpaRepository<DealerServiceFeePo, String> {

    List<DealerServiceFeePo> findByLoanId(String loanId);
}
