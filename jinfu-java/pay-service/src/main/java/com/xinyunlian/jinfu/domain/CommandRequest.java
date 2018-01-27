package com.xinyunlian.jinfu.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xinyunlian.jinfu.enums.ETrxType;
import com.xinyunlian.jinfu.executer.CommandExecuter;
import com.xinyunlian.jinfu.factory.ExecuterFactory;

public abstract class CommandRequest<F extends CommandResponse>  {

	@SignatureIgnore
	@JsonIgnore
	protected CommandExecuter commandExecuter;

	public CommandRequest() {
		super();
		this.commandExecuter = ExecuterFactory.getExecuter(this);
	}

	@SuppressWarnings("unchecked")
	public F execute() {
		return (F) this.commandExecuter.execute(this);
	}

	public abstract ETrxType getTrxType();

}
