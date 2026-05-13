package com.jyujyu.inflearnspringtest.service;

import com.jyujyu.inflearnspringtest.controller.response.ExamFailStudentResponse;
import com.jyujyu.inflearnspringtest.controller.response.ExamPassStudentResponse;
import com.jyujyu.inflearnspringtest.model.*;
import com.jyujyu.inflearnspringtest.repository.StudentFailRepository;
import com.jyujyu.inflearnspringtest.repository.StudentPassRepository;
import com.jyujyu.inflearnspringtest.repository.StudentScoreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;
import java.util.stream.Stream;

class StudentScoreServiceMockTest {

    private StudentScoreService studentScoreService;
    private StudentScoreRepository studentScoreRepository;
    private StudentPassRepository studentPassRepository;
    private StudentFailRepository studentFailRepository;

    @BeforeEach
    public void beforeEach() {
        studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
        studentPassRepository = Mockito.mock(StudentPassRepository.class);
        studentFailRepository = Mockito.mock(StudentFailRepository.class);

        studentScoreService = new StudentScoreService(
                studentScoreRepository,
                studentPassRepository,
                studentFailRepository
        );
    }

    @Test
    @DisplayName("첫번째 Mock 테스트")
    public void firstSaveScoreMockTest() {
        // given
        String givenStudentName = "jyujyu";
        String givenExam = "testexam";
        Integer givenKorScore = 80;
        Integer givenEnglishScore = 100;
        Integer givenMathScore = 60;

        // when
        studentScoreService.saveScore(
                givenStudentName,
                givenExam,
                givenKorScore,
                givenEnglishScore,
                givenMathScore
        );
    }

    @Test
    @DisplayName("성적 저장 로직 검증 / 60점 이상인 경우")
    public void saveScoreMockTest() {
        // given : 평균점수가 60점 이상인 경우
        StudentScore expectStudentScore = StudentScoreTestDataBuilder.padded().build();
        StudentPass expectStudentPass = StudentPassFixture.create(expectStudentScore);

        ArgumentCaptor<StudentScore> studentScoreArgumentCaptor = ArgumentCaptor.forClass(StudentScore.class);
        ArgumentCaptor<StudentPass> studentPassArgumentCaptor = ArgumentCaptor.forClass(StudentPass.class);

        // when
        studentScoreService.saveScore(
                expectStudentScore.getStudentName(),
                expectStudentScore.getExam(),
                expectStudentScore.getKorScore(),
                expectStudentScore.getEnglishScore(),
                expectStudentScore.getMathScore()
        );

        // then
        Mockito.verify(studentScoreRepository, Mockito.times(1)).save(studentScoreArgumentCaptor.capture());

        StudentScore captoredStudentScore = studentScoreArgumentCaptor.getValue();
        Assertions.assertEquals(expectStudentScore.getStudentName(), captoredStudentScore.getStudentName());
        Assertions.assertEquals(expectStudentScore.getExam(), captoredStudentScore.getExam());
        Assertions.assertEquals(expectStudentScore.getKorScore(), captoredStudentScore.getKorScore());
        Assertions.assertEquals(expectStudentScore.getEnglishScore(), captoredStudentScore.getEnglishScore());
        Assertions.assertEquals(expectStudentScore.getMathScore(), captoredStudentScore.getMathScore());

        Mockito.verify(studentPassRepository, Mockito.times(1)).save(studentPassArgumentCaptor.capture());

        StudentPass captoredStudentPass = studentPassArgumentCaptor.getValue();
        Assertions.assertEquals(expectStudentPass.getStudentName(), captoredStudentPass.getStudentName());
        Assertions.assertEquals(expectStudentPass.getExam(), captoredStudentPass.getExam());
        Assertions.assertEquals(expectStudentPass.getAvgScore(), captoredStudentPass.getAvgScore());

        Mockito.verify(studentFailRepository, Mockito.times(0)).save(Mockito.any());
    }

    @Test
    @DisplayName("성적 저장 로직 검증 / 60점 미만인 경우")
    public void saveScoreMockTest2() {
        // given : 평균점수가 60점 미만인 경우
        StudentScore expectStudentScore = StudentScoreFixture.failed();
        StudentFail expectStudentFail = StudentFailFixture.create(expectStudentScore);

        ArgumentCaptor<StudentScore> studentScoreArgumentCaptor = ArgumentCaptor.forClass(StudentScore.class);
        ArgumentCaptor<StudentFail> studentFailArgumentCaptor = ArgumentCaptor.forClass(StudentFail.class);

        // when
        studentScoreService.saveScore(
                expectStudentScore.getStudentName(),
                expectStudentScore.getExam(),
                expectStudentScore.getKorScore(),
                expectStudentScore.getEnglishScore(),
                expectStudentScore.getMathScore()
        );

        // then
        Mockito.verify(studentScoreRepository, Mockito.times(1)).save(studentScoreArgumentCaptor.capture());

        StudentScore captoredStudentScore = studentScoreArgumentCaptor.getValue();
        Assertions.assertEquals(expectStudentScore.getStudentName(), captoredStudentScore.getStudentName());
        Assertions.assertEquals(expectStudentScore.getExam(), captoredStudentScore.getExam());
        Assertions.assertEquals(expectStudentScore.getKorScore(), captoredStudentScore.getKorScore());
        Assertions.assertEquals(expectStudentScore.getEnglishScore(), captoredStudentScore.getEnglishScore());
        Assertions.assertEquals(expectStudentScore.getMathScore(), captoredStudentScore.getMathScore());

        Mockito.verify(studentPassRepository, Mockito.times(0)).save(Mockito.any());
        Mockito.verify(studentFailRepository, Mockito.times(1)).save(studentFailArgumentCaptor.capture());

        StudentFail captoredStudentFail = studentFailArgumentCaptor.getValue();
        Assertions.assertEquals(expectStudentFail.getStudentName(), captoredStudentFail.getStudentName());
        Assertions.assertEquals(expectStudentFail.getExam(), captoredStudentFail.getExam());
        Assertions.assertEquals(expectStudentFail.getAvgScore(), captoredStudentFail.getAvgScore());

    }

    @Test
    @DisplayName("합격자 명단 가져오기 검증")
    public void getPassStudentListTest() {
        // given
        String givenTestExam = "testexam";

        StudentPass expectStudent1 = StudentPassFixture.create("jyujyu", givenTestExam);
        StudentPass expectStudent2 = StudentPassFixture.create("testName", givenTestExam);
        StudentPass notExpectStudent3 = StudentPassFixture.create("anotherStudent", "anotherExam");

        Mockito.when(studentPassRepository.findAll()).thenReturn(List.of(
                expectStudent1,
                expectStudent2,
                notExpectStudent3
        ));

        // when
        List<ExamPassStudentResponse> expectPassResponses = Stream.of(expectStudent1, expectStudent2)
                .map((pass) -> new ExamPassStudentResponse(pass.getStudentName(), pass.getAvgScore()))
                .toList();
        List<ExamPassStudentResponse> responses = studentScoreService.getPassStudentList(givenTestExam);

        // then
        Assertions.assertIterableEquals(expectPassResponses, responses);
    }

    @Test
    @DisplayName("불합격자 명단 가져오기 검증")
    public void getFailStudentListTest() {
        // given
        String givenTestExam = "testexam";

        StudentFail notExpectStudent1 = StudentFailFixture.create("jyujyu", "anotherExam");
        StudentFail expectStudent2 = StudentFailFixture.create("testName", givenTestExam);
        StudentFail expectStudent3 = StudentFailFixture.create("testName2", givenTestExam);

        Mockito.when(studentFailRepository.findAll()).thenReturn(List.of(
                notExpectStudent1,
                expectStudent2,
                expectStudent3
        ));

        // when
        List<ExamFailStudentResponse> expectFailResponses = Stream.of(expectStudent2, expectStudent3)
                .map((fail) -> new ExamFailStudentResponse(fail.getStudentName(), fail.getAvgScore()))
                .toList();
        List<ExamFailStudentResponse> responses = studentScoreService.getFailStudentList(givenTestExam);

        // then
        Assertions.assertIterableEquals(expectFailResponses, responses);
    }
}
