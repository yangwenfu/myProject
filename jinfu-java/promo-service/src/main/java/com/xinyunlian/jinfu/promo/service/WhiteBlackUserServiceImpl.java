package com.xinyunlian.jinfu.promo.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.promo.dao.WhiteBlackUserDao;
import com.xinyunlian.jinfu.promo.dto.WhiteBlackUserDto;
import com.xinyunlian.jinfu.promo.entity.WhiteBlackUserPo;
import com.xinyunlian.jinfu.promo.enums.ERecordType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 黑白名单ServiceImpl
 * @author jll
 * @version 
 */

@Service
public class WhiteBlackUserServiceImpl implements WhiteBlackUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WhiteBlackUserServiceImpl.class);

	@Autowired
	private WhiteBlackUserDao whiteBlackUserDao;

	@Override
	public void save(List<WhiteBlackUserDto> records){
		List<WhiteBlackUserPo> pos = new ArrayList<>();
		records.forEach(whiteBlackUserDto -> {
			WhiteBlackUserPo po = ConverterService.convert(whiteBlackUserDto, WhiteBlackUserPo.class);
			pos.add(po);
		});
		whiteBlackUserDao.save(pos);
	}

	@Override
	public List<WhiteBlackUserDto> findByPromoIdAndType(Long promoId, ERecordType recordType){
		List<WhiteBlackUserPo> pos = whiteBlackUserDao.findByPromoIdAndRecordType(promoId, recordType);
		List<WhiteBlackUserDto> records = new ArrayList<>(pos.size());
		pos.forEach(whiteBlackUserPo -> {
			WhiteBlackUserDto record = ConverterService.convert(whiteBlackUserPo, WhiteBlackUserDto.class);
			records.add(record);
		});
		return records;
	}

	@Override
	public void deleteByPromoIdAndType(Long promoId, ERecordType recordType) {
		whiteBlackUserDao.deleteByPromoIdAndRecordType(promoId, recordType);
	}
}
