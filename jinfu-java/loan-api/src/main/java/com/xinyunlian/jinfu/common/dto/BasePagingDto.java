package com.xinyunlian.jinfu.common.dto;

import java.io.Serializable;

/**
 * @author willwang
 */
public class BasePagingDto implements Serializable{

    private Integer pageSize;
    private Integer currentPage;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }
}
