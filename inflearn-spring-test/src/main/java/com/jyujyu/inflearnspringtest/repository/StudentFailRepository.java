package com.jyujyu.inflearnspringtest.repository;

import com.jyujyu.inflearnspringtest.model.StudentFail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentFailRepository extends JpaRepository<StudentFail, Long> {
}
