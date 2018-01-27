package com.xinyunlian.jinfu.store.service;

import com.xinyunlian.jinfu.store.entity.StoreInfPo;

/**
 * @author Sephy
 * @since: 2017-06-25
 */
public interface StoreScoreStrategy {

	/**
	 * 计算店铺分数
	 *
	 * @param storeInfPo 店铺信息
	 * @return
	 */
	int computeScore(StoreInfPo storeInfPo);
}
