package com.jyujyu.inflearnspringtest.service;

import com.jyujyu.inflearnspringtest.IntegrationTest;
import com.jyujyu.inflearnspringtest.MyCalculator;
import com.jyujyu.inflearnspringtest.controller.response.ExamFailStudentResponse;
import com.jyujyu.inflearnspringtest.controller.response.ExamPassStudentResponse;
import com.jyujyu.inflearnspringtest.model.StudentScore;
import com.jyujyu.inflearnspringtest.model.StudentScoreFixture;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class StudentScoreServiceIntegrationTest extends IntegrationTest {

    @Autowired
    private StudentScoreService studentScoreService;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void savePassedStudentScoreTest() {
        // given
        StudentScore studentScore = StudentScoreFixture.padded();

        // when
        studentScoreService.saveScore(
                studentScore.getStudentName(),
                studentScore.getExam(),
                studentScore.getKorScore(),
                studentScore.getEnglishScore(),
                studentScore.getMathScore()
        );

        entityManager.flush();
        entityManager.clear();

        // then
        List<ExamPassStudentResponse> passedStudentResponses =
                studentScoreService.getPassStudentList(studentScore.getExam());

        Assertions.assertEquals(1, passedStudentResponses.size());

        ExamPassStudentResponse passedStudentResponse = passedStudentResponses.get(0);

        Assertions.assertEquals(studentScore.getStudentName(), passedStudentResponse.getStudentName());
        Assertions.assertEquals(new MyCalculator(0.0)
                .add(studentScore.getKorScore().doubleValue())
                .add(studentScore.getEnglishScore().doubleValue())
                .add(studentScore.getMathScore().doubleValue())
                .divide(3.0)
                .getResult(), passedStudentResponse.getAvgScore());
    }

    @Test
    public void saveFailedStudentScoreTest() {
        // given
        StudentScore studentScore = StudentScoreFixture.failed();

        // when
        studentScoreService.saveScore(
                studentScore.getStudentName(),
                studentScore.getExam(),
                studentScore.getKorScore(),
                studentScore.getEnglishScore(),
                studentScore.getMathScore()
        );

        entityManager.flush();
        entityManager.clear();

        // then
        List<ExamFailStudentResponse> failedStudentResponses =
                studentScoreService.getFailStudentList(studentScore.getExam());

        Assertions.assertEquals(1, failedStudentResponses.size());

        ExamFailStudentResponse failedStudentResponse = failedStudentResponses.get(0);

        Assertions.assertEquals(studentScore.getStudentName(), failedStudentResponse.getStudentName());
        Assertions.assertEquals(new MyCalculator(0.0)
                .add(studentScore.getKorScore().doubleValue())
                .add(studentScore.getEnglishScore().doubleValue())
                .add(studentScore.getMathScore().doubleValue())
                .divide(3.0)
                .getResult(), failedStudentResponse.getAvgScore());
    }
}
