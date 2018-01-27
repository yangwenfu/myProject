package com.xinyunlian.jinfu.channel.entity;

import com.xinyunlian.jinfu.channel.enums.EChannelStatus;
import com.xinyunlian.jinfu.channel.enums.EChannelType;
import com.xinyunlian.jinfu.channel.enums.converter.EChannelStatusConverter;
import com.xinyunlian.jinfu.channel.enums.converter.EChannelTypeConverter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by bright on 2016/11/18.
 */
@Entity
@Table(name = "channel")
public class ChannelPo implements Serializable{
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "CHNL_TYPE")
    @Convert(converter = EChannelTypeConverter.class)
    private EChannelType chnlType;

    @Column(name = "CHNL_NAME")
    private String chnlName;

    @Column(name = "BEAN_NAME")
    private String beanName;

    @Column(name = "CHNL_STATUS")
    @Convert(converter = EChannelStatusConverter.class)
    private EChannelStatus chnlStatus;

    @Column(name = "CHNL_PRIORITY")
    private Integer chnlPriority;

    @Column(name = "CHNL_DELAY")
    private Integer chnlDelay;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EChannelType getChnlType() {
        return chnlType;
    }

    public void setChnlType(EChannelType chnlType) {
        this.chnlType = chnlType;
    }

    public String getChnlName() {
        return chnlName;
    }

    public void setChnlName(String chnlName) {
        this.chnlName = chnlName;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public EChannelStatus getChnlStatus() {
        return chnlStatus;
    }

    public void setChnlStatus(EChannelStatus chnlStatus) {
        this.chnlStatus = chnlStatus;
    }

    public Integer getChnlPriority() {
        return chnlPriority;
    }

    public void setChnlPriority(Integer chnlPriority) {
        this.chnlPriority = chnlPriority;
    }

    public Integer getChnlDelay() {
        return chnlDelay;
    }

    public void setChnlDelay(Integer chnlDelay) {
        this.chnlDelay = chnlDelay;
    }
}
