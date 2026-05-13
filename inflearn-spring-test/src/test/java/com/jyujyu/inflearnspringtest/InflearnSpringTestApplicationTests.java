package com.jyujyu.inflearnspringtest;

import com.jyujyu.inflearnspringtest.model.StudentScore;
import com.jyujyu.inflearnspringtest.model.StudentScoreFixture;
import com.jyujyu.inflearnspringtest.repository.StudentScoreRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class InflearnSpringTestApplicationTests extends IntegrationTest {

    @Autowired
    private StudentScoreRepository studentScoreRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void contextLoads() {
        StudentScore studentScore = StudentScoreFixture.padded();
        StudentScore savedStudentScore = studentScoreRepository.save(studentScore);

        entityManager.flush();
        entityManager.clear();

        StudentScore queryStudentScore = studentScoreRepository.findById(savedStudentScore.getId()).orElseThrow();

        Assertions.assertEquals(savedStudentScore.getId(), queryStudentScore.getId());
        Assertions.assertEquals(savedStudentScore.getStudentName(), queryStudentScore.getStudentName());
        Assertions.assertEquals(savedStudentScore.getExam(), queryStudentScore.getExam());
        Assertions.assertEquals(savedStudentScore.getKorScore(), queryStudentScore.getKorScore());
        Assertions.assertEquals(savedStudentScore.getEnglishScore(), queryStudentScore.getEnglishScore());
        Assertions.assertEquals(savedStudentScore.getMathScore(), queryStudentScore.getMathScore());
    }
}
