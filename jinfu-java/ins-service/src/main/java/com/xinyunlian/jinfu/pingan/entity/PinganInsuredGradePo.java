package com.xinyunlian.jinfu.pingan.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/6/12/0012.
 */
@Entity
@Table(name = "pingan_insured_grade")
public class PinganInsuredGradePo implements Serializable {
    private static final long serialVersionUID = -4214213867140885021L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "INSURED_GRADE_CODE")
    private String insuredGradeCode;
    @Column(name = "INSURED_AMOUNT")
    private String insuredAmount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInsuredGradeCode() {
        return insuredGradeCode;
    }

    public void setInsuredGradeCode(String insuredGradeCode) {
        this.insuredGradeCode = insuredGradeCode;
    }

    public String getInsuredAmount() {
        return insuredAmount;
    }

    public void setInsuredAmount(String insuredAmount) {
        this.insuredAmount = insuredAmount;
    }
}
