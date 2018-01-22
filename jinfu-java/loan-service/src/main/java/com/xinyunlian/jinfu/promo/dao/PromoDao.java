package com.xinyunlian.jinfu.promo.dao;

import com.xinyunlian.jinfu.promo.entity.PromoPo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by JL on 2016/11/23.
 */
public interface PromoDao extends JpaRepository<PromoPo, Long> {

    List<PromoPo> findByLoanId(String loanId);
}
