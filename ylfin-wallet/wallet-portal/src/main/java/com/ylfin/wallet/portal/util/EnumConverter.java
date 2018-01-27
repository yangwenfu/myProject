package com.ylfin.wallet.portal.util;

import com.xinyunlian.jinfu.user.enums.EHouseProperty;
import com.xinyunlian.jinfu.user.enums.EMarryStatus;
import com.xinyunlian.jinfu.user.enums.ERelationship;
import com.xinyunlian.jinfu.user.enums.ESource;

public abstract class EnumConverter {

	/**
	 * 将 code 转换为枚举
	 * @param code
	 * @return
	 */
	public static ESource convertToESource(String code) {
		return null;
	}

	public static ERelationship getERelationship(String relationship){
		ERelationship[] values = ERelationship.values();
		for(ERelationship value:values){
			if (relationship.equals(value.getCode())){
				return value;
			}
		}
		return null;
	}


	public static EMarryStatus getEMarryStatus(String sMarryStatus){
		EMarryStatus[] values = EMarryStatus.values();
		for(EMarryStatus value:values){
			if (sMarryStatus.equals(value.getCode())){
				return value;
			}
		}
		return null;
	}

	public static EHouseProperty getEHouseProperty(String eHouseProperty){
		EHouseProperty[] values = EHouseProperty.values();
		for(EHouseProperty value:values){
			if (eHouseProperty.equals(value.getCode())){
				return value;
			}
		}
		return null;
	}
}
