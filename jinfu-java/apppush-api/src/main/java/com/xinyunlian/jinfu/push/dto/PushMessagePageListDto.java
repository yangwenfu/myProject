package com.xinyunlian.jinfu.push.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by apple on 2017/1/10.
 */
public class PushMessagePageListDto implements Serializable {

    private static final long serialVersionUID = 5082583046322195200L;

    private Long unReadCount;

    private Integer currentPage;

    private Integer totalPages;

    private List<PushMessagePageDto> list;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<PushMessagePageDto> getList() {
        return list;
    }

    public void setList(List<PushMessagePageDto> list) {
        this.list = list;
    }

    public Long getUnReadCount() {
        return unReadCount;
    }

    public void setUnReadCount(Long unReadCount) {
        this.unReadCount = unReadCount;
    }
}
