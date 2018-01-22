package com.ylfin.wallet.entity;

import com.ylfin.core.entity.AbstractMybatisPlusEntity;
import lombok.Data;

@Data
public class XmccbAccount extends AbstractMybatisPlusEntity {

	private Long acctId;

	private String userId;

	private Integer acctType;

	private String bankcardNo;
}
