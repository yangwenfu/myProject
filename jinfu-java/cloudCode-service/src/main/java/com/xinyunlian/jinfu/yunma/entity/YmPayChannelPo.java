package com.xinyunlian.jinfu.yunma.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.yunma.enums.EYmPayChannel;
import com.xinyunlian.jinfu.yunma.enums.converter.EYmPayChannelConverter;

import javax.persistence.*;

/**
 * Created by menglei on 2017-07-19.
 */
@Entity
@Table(name = "ym_pay_channel")
public class YmPayChannelPo extends BaseMaintainablePo {
    private static final long serialVersionUID = 2962130238021106783L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name="CHANNEL")
    @Convert(converter = EYmPayChannelConverter.class)
    private EYmPayChannel channel;

    @Column(name = "AREA_ID")
    private Long areaId;

    @Column(name = "AREA_TREE_PATH")
    private String areaTreePath;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public EYmPayChannel getChannel() {
        return channel;
    }

    public void setChannel(EYmPayChannel channel) {
        this.channel = channel;
    }
}
