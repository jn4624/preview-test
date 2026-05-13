package com.jyujyu.inflearnspringtest.repository;

import com.jyujyu.inflearnspringtest.model.StudentPass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentPassRepository extends JpaRepository<StudentPass, Long> {
}
