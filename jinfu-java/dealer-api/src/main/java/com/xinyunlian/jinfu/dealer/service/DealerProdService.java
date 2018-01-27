package com.xinyunlian.jinfu.dealer.service;

import com.xinyunlian.jinfu.dealer.dto.DealerProdDto;
import com.xinyunlian.jinfu.dealer.dto.DealerProdSearchDto;

import java.util.List;

/**
 * Created by menglei on 2016年08月31日.
 */
public interface DealerProdService {

    List<DealerProdDto> getDealerProdList(DealerProdSearchDto dealerProdSearchDto);

    List<DealerProdDto> getByDealerIdAndArea(DealerProdDto dealerProdDto);

    List<DealerProdDto> getByDealerIdAndAreaAndProdId(DealerProdDto dealerProdDto);

}
