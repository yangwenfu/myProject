package com.xinyunlian.jinfu.dict.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/1/22/0022.
 */
@Entity
@Table(name = "dictionary_type")
public class DictionaryTypePo implements Serializable {
    private static final long serialVersionUID = 848512586954043686L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CODE")
    private String code;

    @Column(name = "TEXT")
    private String text;

    @Column(name = "STATUS")
    private Boolean status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
