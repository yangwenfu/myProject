package com.xinyunlian.jinfu.activeMq;

import com.alibaba.fastjson.JSONObject;
import com.xinyunlian.jinfu.area.dto.SysAreaInfDto;
import com.xinyunlian.jinfu.area.service.SysAreaInfService;
import com.xinyunlian.jinfu.center.dto.CenterUserDto;
import com.xinyunlian.jinfu.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class UserConsumer {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private UserService userService;
	@Autowired
	private SysAreaInfService sysAreaInfService;
	@Autowired
	private MongoOperations mongoOperations;
	
	@JmsListener(destination="queue.member-center.user.jinfu")
	public void receiveQueue(String userDTO){
		logger.info(userDTO);
		CenterUserDto centerUserDto = JSONObject.parseObject(userDTO, CenterUserDto.class);
		//判断来源是否是金服的
		if (centerUserDto == null || "金服".equals(centerUserDto.getSource())){
			return;
		}

		if(centerUserDto.getAreaGbCode() != null) {
			SysAreaInfDto sysAreaInfDto = sysAreaInfService.getSysAreaByGbCode(centerUserDto.getAreaGbCode());
			if(sysAreaInfDto == null){
				logger.error("地址库匹配不到");
				return;
			}
			String[] pathArray = sysAreaInfDto.getTreePath().split(",");
			if (pathArray != null && pathArray.length > 0){
				centerUserDto.setProvinceId(Long.parseLong(pathArray[1]));
				centerUserDto.setCityId(Long.parseLong(pathArray[2]));
				centerUserDto.setAreaId(sysAreaInfDto.getId());
			}
		}

		String ret = userService.saveFromUserCenter(centerUserDto);
		if(ret != null){
			logger.error(ret);
		}else{
			mongoOperations.save(centerUserDto,"user");
		}

	}
}
