package com.xinyunlian.jinfu.push.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by apple on 2017/1/10.
 */
public class PushMessageListDto implements Serializable {

    private static final long serialVersionUID = 5082583046322195200L;

    private Integer currentPage = 0;

    private Integer totalPages = 0;

    private List<PushMessageDto> list;

    private Long totalRecord = 0L;

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

    public List<PushMessageDto> getList() {
        return list;
    }

    public void setList(List<PushMessageDto> list) {
        this.list = list;
    }

    public Long getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(Long totalRecord) {
        this.totalRecord = totalRecord;
    }
}
