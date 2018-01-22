package com.xinyunlian.jinfu.store.dto;

import java.io.Serializable;

/**
 * Created by menglei on 2017年03月24日.
 */
public class FranchiseDto implements Serializable {

    private static final long serialVersionUID = -1L;

    private Long id;
    private Long storeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }
}
