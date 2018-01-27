package com.xinyunlian.jinfu.exam.dao;

import com.xinyunlian.jinfu.exam.entity.ExamUserPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by menglei on 2017年05月02日.
 */
public interface ExamUserDao extends JpaRepository<ExamUserPo, Long>, JpaSpecificationExecutor<ExamUserPo> {

    ExamUserPo findByUserIdAndExamId(String userId, Long examId);

    List<ExamUserPo> findByUserId(String userId);

}
