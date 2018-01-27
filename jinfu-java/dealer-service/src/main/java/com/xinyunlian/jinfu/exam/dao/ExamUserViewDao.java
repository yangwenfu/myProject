package com.xinyunlian.jinfu.exam.dao;

import com.xinyunlian.jinfu.exam.entity.ExamUserViewPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by menglei on 2017年05月02日.
 */
public interface ExamUserViewDao extends JpaRepository<ExamUserViewPo, Long>, JpaSpecificationExecutor<ExamUserViewPo> {

}
