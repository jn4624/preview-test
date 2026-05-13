package com.jyujyu.inflearnspringtest.repository;

import com.jyujyu.inflearnspringtest.model.StudentScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentScoreRepository extends JpaRepository<StudentScore, Long> {
}
