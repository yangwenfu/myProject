package com.xinyunlian.jinfu.channel.dto;

import com.xinyunlian.jinfu.channel.enums.EChannelStatus;
import com.xinyunlian.jinfu.channel.enums.EChannelType;

import java.io.Serializable;

/**
 * Created by bright on 2016/11/18.
 */
public class ChannelDto implements Serializable {


    private Integer id;

    private EChannelType chnlType;

    private String chnlName;

    private String beanName;

    private EChannelStatus chnlStatus;

    private Integer chnlPriority;

    private Integer chnlDelay;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setChnlType(EChannelType chnlType) {
        this.chnlType = chnlType;
    }

    public EChannelType getChnlType() {
        return chnlType;
    }

    public void setChnlName(String chnlName) {
        this.chnlName = chnlName;
    }

    public String getChnlName() {
        return chnlName;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public void setChnlStatus(EChannelStatus chnlStatus) {
        this.chnlStatus = chnlStatus;
    }

    public EChannelStatus getChnlStatus() {
        return chnlStatus;
    }

    public void setChnlPriority(Integer chnlPriority) {
        this.chnlPriority = chnlPriority;
    }

    public Integer getChnlPriority() {
        return chnlPriority;
    }

    public void setChnlDelay(Integer chnlDelay) {
        this.chnlDelay = chnlDelay;
    }

    public Integer getChnlDelay() {
        return chnlDelay;
    }

}
