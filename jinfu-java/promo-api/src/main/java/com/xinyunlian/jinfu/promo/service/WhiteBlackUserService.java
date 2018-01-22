package com.xinyunlian.jinfu.promo.service;

import com.xinyunlian.jinfu.promo.dto.WhiteBlackUserDto;
import com.xinyunlian.jinfu.promo.enums.ERecordType;

import java.util.List;

/**
 * 黑白名单Service
 * @author jll
 * @version 
 */

public interface WhiteBlackUserService {

    void save(List<WhiteBlackUserDto> records);

    List<WhiteBlackUserDto> findByPromoIdAndType(Long promoId, ERecordType recordType);

    void deleteByPromoIdAndType(Long promoId, ERecordType recordType);
}
