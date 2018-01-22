package com.xinyunlian.jinfu.shopkeeper.dto.home;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by King on 2017/1/25.
 */
public class FloorDto {
    private String code;

    private String title;

    private boolean hasMore;

    private List<TemplateDto> templateDtos = new ArrayList<>();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public List<TemplateDto> getTemplateDtos() {
        return templateDtos;
    }

    public void setTemplateDtos(List<TemplateDto> templateDtos) {
        this.templateDtos = templateDtos;
    }
}
