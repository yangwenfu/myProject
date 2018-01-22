package com.xinyunlian.jinfu.external.service;

import com.xinyunlian.jinfu.external.dto.LoanRefundsDto;
import com.xinyunlian.jinfu.external.dto.RefundsAdvanceDto;

/**
 * Created by godslhand on 2017/7/14.
 */
public interface LoanApplOutRefundsAdvanceService {

    RefundsAdvanceDto findByApplId(String applId);

    void updateDisableTag(String applId,String outRefundsId);

    void save(RefundsAdvanceDto refundsAdvanceDto);

}
