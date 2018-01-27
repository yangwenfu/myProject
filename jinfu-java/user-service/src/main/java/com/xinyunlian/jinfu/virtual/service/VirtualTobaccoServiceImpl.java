package com.xinyunlian.jinfu.virtual.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.virtual.dao.VirtualTobaccoDao;
import com.xinyunlian.jinfu.virtual.dto.VirtualTobaccoDto;
import com.xinyunlian.jinfu.virtual.entity.VirtualTobaccoPo;
import com.xinyunlian.jinfu.virtual.enums.ETakeStatus;
import com.xinyunlian.jinfu.virtual.enums.ETakeType;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 虚拟烟草证ServiceImpl
 * @author jll
 * @version 
 */

@Service
public class VirtualTobaccoServiceImpl implements VirtualTobaccoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VirtualTobaccoServiceImpl.class);

	@Autowired
	private VirtualTobaccoDao virtualTobaccoDao;

	@Override
	public Long countByTakeStatus(ETakeStatus status){
		return virtualTobaccoDao.countByStatus(status);
	}

	@Override
	public Long countUsed(){
		return virtualTobaccoDao.countUsed();
	}

	@Override
	@Transactional
	public List<VirtualTobaccoDto> take(VirtualTobaccoDto virtualTobaccoDto, int takeNum){
		List<VirtualTobaccoDto> virtualTobaccoDtos = takeTobacco(virtualTobaccoDto,takeNum);
		if(virtualTobaccoDtos == null) {
			//个数不够生产初始数据
			List<VirtualTobaccoPo> tobaccoPos = build(virtualTobaccoDto);
			virtualTobaccoDao.save(tobaccoPos);
			virtualTobaccoDtos = takeTobacco(virtualTobaccoDto,takeNum);
		}

		return virtualTobaccoDtos;
	}

	private List<VirtualTobaccoDto> takeTobacco(VirtualTobaccoDto virtualTobaccoDto, int takeNum){
		List<VirtualTobaccoPo> virtualTobaccoPos = virtualTobaccoDao
				.findByRand(ETakeStatus.INITIAL.getCode(),virtualTobaccoDto.getAreaCode(),takeNum);
		//领用个数足够
		if(virtualTobaccoPos.size() >= takeNum){
			List<VirtualTobaccoDto> virtualTobaccoDtos = new ArrayList<>();
			virtualTobaccoPos.forEach(virtualTobaccoPo -> {
				virtualTobaccoPo.setStatus(ETakeStatus.TAKED);
				virtualTobaccoPo.setTakeType(ETakeType.HAND);
				virtualTobaccoPo.setTakeTime(new Date());
				virtualTobaccoPo.setAssignPerson(virtualTobaccoDto.getAssignPerson());
				VirtualTobaccoDto virtual = new VirtualTobaccoDto();
				virtual.setTobaccoCertificateNo(virtualTobaccoPo.getTobaccoCertificateNo());
				virtualTobaccoDtos.add(virtual);
			});
			return virtualTobaccoDtos;
		}
		return null;
	}


	private List<VirtualTobaccoPo> build(VirtualTobaccoDto virtualTobaccoDto){
		List<VirtualTobaccoPo> tobaccoPos = new ArrayList<>();
		tobaccoPos.addAll(buildDate(virtualTobaccoDto,"19"));
		tobaccoPos.addAll(buildDate(virtualTobaccoDto,"29"));
		return tobaccoPos;
	}

	private List<VirtualTobaccoPo> buildDate(VirtualTobaccoDto virtualTobaccoDto,String pinCode){
		List<VirtualTobaccoPo> tobaccoPos = new ArrayList<>();
		Long no = virtualTobaccoDao.findMaxSerial(virtualTobaccoDto.getAreaCode(),pinCode);
		if(no == null){
			no = 0L;
		}
		for (long i=(no+1);i<=500;i++){
			VirtualTobaccoPo tobaccoPo = new VirtualTobaccoPo();
			ConverterService.convert(virtualTobaccoDto,tobaccoPo);
			tobaccoPo.setPinCode(pinCode);
			tobaccoPo.setSerial(i);
			tobaccoPo.setStatus(ETakeStatus.INITIAL);
			tobaccoPo.setTobaccoCertificateNo(tobaccoPo.getAreaCode() + pinCode
					+ StringUtils.leftPad(i+"",4,"0"));
			tobaccoPos.add(tobaccoPo);
		}
		return tobaccoPos;
	}
	
}
