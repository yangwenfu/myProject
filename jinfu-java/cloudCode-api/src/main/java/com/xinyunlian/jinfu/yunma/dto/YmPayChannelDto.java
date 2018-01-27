package com.xinyunlian.jinfu.yunma.dto;

import com.xinyunlian.jinfu.yunma.enums.EYmPayChannel;

import java.io.Serializable;

/**
 * Created by menglei on 2017-07-19.
 */
public class YmPayChannelDto implements Serializable {

    private static final long serialVersionUID = -8951652034987940359L;

    private Long id;

    private EYmPayChannel channel;

    private Long areaId;

    private String areaTreePath;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EYmPayChannel getChannel() {
        return channel;
    }

    public void setChannel(EYmPayChannel channel) {
        this.channel = channel;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getAreaTreePath() {
        return areaTreePath;
    }

    public void setAreaTreePath(String areaTreePath) {
        this.areaTreePath = areaTreePath;
    }
}
