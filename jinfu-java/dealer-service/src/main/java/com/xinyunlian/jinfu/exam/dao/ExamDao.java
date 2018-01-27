package com.xinyunlian.jinfu.exam.dao;

import com.xinyunlian.jinfu.exam.entity.ExamPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by menglei on 2017年05月02日.
 */
public interface ExamDao extends JpaRepository<ExamPo, Long>, JpaSpecificationExecutor<ExamPo> {

    @Query(nativeQuery = true, value = "select * from exam where EXAM_ID not in ?1 and TYPE = ?2")
    List<ExamPo> findByExamIdsAndType(List<Long> examIds, String type);


}
