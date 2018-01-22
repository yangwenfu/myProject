package com.xinyunlian.jinfu.system.entity;

import com.xinyunlian.jinfu.system.enums.ESysConfigType;
import com.xinyunlian.jinfu.system.enums.converter.ESysConfigTypeConverter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by menglei on 2017/6/12/0019.
 */
@Entity
@Table(name = "sys_config")
public class SysConfigPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TYPE")
    @Convert(converter = ESysConfigTypeConverter.class)
    private ESysConfigType type;

    @Column(name = "SCORE")
    private Integer score;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ESysConfigType getType() {
        return type;
    }

    public void setType(ESysConfigType type) {
        this.type = type;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
