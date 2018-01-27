package com.xinyunlian.jinfu.shopkeeper.dto.card;

import com.xinyunlian.jinfu.shopkeeper.dto.my.StoreEachDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by King on 2017/3/6.
 */
public class StoreExtCheckDto {
    private boolean storeExtIsFull;
    private List<StoreEachDto> storeEachDtos = new ArrayList<>();

    public boolean isStoreExtIsFull() {
        return storeExtIsFull;
    }

    public void setStoreExtIsFull(boolean storeExtIsFull) {
        this.storeExtIsFull = storeExtIsFull;
    }

    public List<StoreEachDto> getStoreEachDtos() {
        return storeEachDtos;
    }

    public void setStoreEachDtos(List<StoreEachDto> storeEachDtos) {
        this.storeEachDtos = storeEachDtos;
    }
}
