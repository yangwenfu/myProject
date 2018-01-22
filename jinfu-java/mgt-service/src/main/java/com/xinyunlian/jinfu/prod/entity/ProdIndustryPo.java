package com.xinyunlian.jinfu.prod.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/5/17/0017.
 */
@Entity
@Table(name = "prod_industry")
public class ProdIndustryPo implements Serializable {
    private static final long serialVersionUID = 8108406010024036454L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "PROD_ID")
    private String prodId;

    @Column(name = "IND_MCC")
    private String indMcc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getIndMcc() {
        return indMcc;
    }

    public void setIndMcc(String indMcc) {
        this.indMcc = indMcc;
    }
}
