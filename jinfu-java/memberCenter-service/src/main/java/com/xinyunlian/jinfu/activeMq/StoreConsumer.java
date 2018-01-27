package com.xinyunlian.jinfu.activeMq;

import com.alibaba.fastjson.JSONObject;
import com.xinyunlian.jinfu.area.dto.SysAreaInfDto;
import com.xinyunlian.jinfu.area.service.SysAreaInfService;
import com.xinyunlian.jinfu.center.dto.CenterStoreDto;
import com.xinyunlian.jinfu.store.service.StoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class StoreConsumer {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private SysAreaInfService sysAreaInfService;
	@Autowired
	private StoreService storeService;
	@Autowired
	private MongoOperations mongoOperations;
	
	@JmsListener(destination="queue.member-center.store.jinfu")
	public void receiveQueue(String storeDTO){
		logger.info(storeDTO.toString());
		CenterStoreDto centerStoreDto = JSONObject.parseObject(storeDTO, CenterStoreDto.class);
		//判断来源是否是金服的
		if (centerStoreDto == null || "金服".equals(centerStoreDto.getSource())){
			return;
		}

		if(centerStoreDto.getAreaGbCode() != null) {
			SysAreaInfDto sysAreaInfDto = sysAreaInfService.getSysAreaByGbCode(centerStoreDto.getAreaGbCode());
			if(sysAreaInfDto == null){
				logger.error("地址库匹配不到");
				return;
			}
			String[] pathArray = sysAreaInfDto.getTreePath().split(",");
			if (pathArray != null && pathArray.length > 0){
				centerStoreDto.setProvinceId(Long.parseLong(pathArray[1]));
				centerStoreDto.setCityId(Long.parseLong(pathArray[2]));
				centerStoreDto.setAreaId(sysAreaInfDto.getId());
			}
		}

		String ret = storeService.saveFromUserCenter(centerStoreDto);
		if(ret != null){
			logger.error(ret);
		}else{
			mongoOperations.save(centerStoreDto,"store");
		}
	}
}
