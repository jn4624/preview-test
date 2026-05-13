package com.jyujyu.inflearnspringtest.service;

import com.jyujyu.inflearnspringtest.MyCalculator;
import com.jyujyu.inflearnspringtest.controller.response.ExamFailStudentResponse;
import com.jyujyu.inflearnspringtest.controller.response.ExamPassStudentResponse;
import com.jyujyu.inflearnspringtest.model.StudentFail;
import com.jyujyu.inflearnspringtest.model.StudentPass;
import com.jyujyu.inflearnspringtest.model.StudentScore;
import com.jyujyu.inflearnspringtest.repository.StudentFailRepository;
import com.jyujyu.inflearnspringtest.repository.StudentPassRepository;
import com.jyujyu.inflearnspringtest.repository.StudentScoreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;
import java.util.stream.Stream;

class StudentScoreServiceMockTest {

    @Test
    @DisplayName("첫번째 Mock 테스트")
    public void firstSaveScoreMockTest() {
        // given
        StudentScoreService studentScoreService = new StudentScoreService(
                Mockito.mock(StudentScoreRepository.class),
                Mockito.mock(StudentPassRepository.class),
                Mockito.mock(StudentFailRepository.class)
        );

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
        StudentScoreRepository studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
        StudentPassRepository studentPassRepository = Mockito.mock(StudentPassRepository.class);
        StudentFailRepository studentFailRepository = Mockito.mock(StudentFailRepository.class);

        StudentScoreService studentScoreService = new StudentScoreService(
                studentScoreRepository,
                studentPassRepository,
                studentFailRepository
        );

        String givenStudentName = "jyujyu";
        String givenExam = "testexam";
        Integer givenKorScore = 80;
        Integer givenEnglishScore = 100;
        Integer givenMathScore = 60;

        StudentScore expectStudentScore = StudentScore.builder()
                .studentName(givenStudentName)
                .exam(givenExam)
                .korScore(givenKorScore)
                .englishScore(givenEnglishScore)
                .mathScore(givenMathScore)
                .build();
        StudentPass expectStudentPass = StudentPass.builder()
                .studentName(givenStudentName)
                .exam(givenExam)
                .avgScore(new MyCalculator(0.0)
                        .add(givenKorScore.doubleValue())
                        .add(givenEnglishScore.doubleValue())
                        .add(givenMathScore.doubleValue())
                        .divide(3.0)
                        .getResult())
                .build();

        ArgumentCaptor<StudentScore> studentScoreArgumentCaptor = ArgumentCaptor.forClass(StudentScore.class);
        ArgumentCaptor<StudentPass> studentPassArgumentCaptor = ArgumentCaptor.forClass(StudentPass.class);

        // when
        studentScoreService.saveScore(
                givenStudentName,
                givenExam,
                givenKorScore,
                givenEnglishScore,
                givenMathScore
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
        StudentScoreRepository studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
        StudentPassRepository studentPassRepository = Mockito.mock(StudentPassRepository.class);
        StudentFailRepository studentFailRepository = Mockito.mock(StudentFailRepository.class);

        StudentScoreService studentScoreService = new StudentScoreService(
                studentScoreRepository,
                studentPassRepository,
                studentFailRepository
        );

        String givenStudentName = "jyujyu";
        String givenExam = "testexam";
        Integer givenKorScore = 40;
        Integer givenEnglishScore = 40;
        Integer givenMathScore = 60;

        StudentScore expectStudentScore = StudentScore.builder()
                .studentName(givenStudentName)
                .exam(givenExam)
                .korScore(givenKorScore)
                .englishScore(givenEnglishScore)
                .mathScore(givenMathScore)
                .build();
        StudentFail expectStudentFail = StudentFail.builder()
                .studentName(givenStudentName)
                .exam(givenExam)
                .avgScore(new MyCalculator(0.0)
                        .add(givenKorScore.doubleValue())
                        .add(givenEnglishScore.doubleValue())
                        .add(givenMathScore.doubleValue())
                        .divide(3.0)
                        .getResult())
                .build();

        ArgumentCaptor<StudentScore> studentScoreArgumentCaptor = ArgumentCaptor.forClass(StudentScore.class);
        ArgumentCaptor<StudentFail> studentFailArgumentCaptor = ArgumentCaptor.forClass(StudentFail.class);

        // when
        studentScoreService.saveScore(
                givenStudentName,
                givenExam,
                givenKorScore,
                givenEnglishScore,
                givenMathScore
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
        StudentScoreRepository studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
        StudentPassRepository studentPassRepository = Mockito.mock(StudentPassRepository.class);
        StudentFailRepository studentFailRepository = Mockito.mock(StudentFailRepository.class);

        String givenTestExam = "testexam";

        StudentPass expectStudent1 = StudentPass.builder()
                .id(1L)
                .studentName("jyujyu")
                .exam(givenTestExam)
                .avgScore(70.0)
                .build();
        StudentPass expectStudent2 = StudentPass.builder()
                .id(2L)
                .studentName("test")
                .exam(givenTestExam)
                .avgScore(80.0)
                .build();
        StudentPass notExpectStudent3 = StudentPass.builder()
                .id(3L)
                .studentName("iamnot")
                .exam("secondexam")
                .avgScore(90.0)
                .build();

        Mockito.when(studentPassRepository.findAll()).thenReturn(List.of(
                expectStudent1,
                expectStudent2,
                notExpectStudent3
        ));

        StudentScoreService studentScoreService = new StudentScoreService(
                studentScoreRepository,
                studentPassRepository,
                studentFailRepository
        );

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
        StudentScoreRepository studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
        StudentPassRepository studentPassRepository = Mockito.mock(StudentPassRepository.class);
        StudentFailRepository studentFailRepository = Mockito.mock(StudentFailRepository.class);

        String givenTestExam = "testexam";

        StudentFail notExpectStudent1 = StudentFail.builder()
                .id(1L)
                .studentName("jyujyu")
                .exam("secondexam")
                .avgScore(50.0)
                .build();
        StudentFail expectStudent2 = StudentFail.builder()
                .id(2L)
                .studentName("test")
                .exam(givenTestExam)
                .avgScore(45.0)
                .build();
        StudentFail expectStudent3 = StudentFail.builder()
                .id(3L)
                .studentName("iamnot")
                .exam(givenTestExam)
                .avgScore(35.0)
                .build();

        Mockito.when(studentFailRepository.findAll()).thenReturn(List.of(
                notExpectStudent1,
                expectStudent2,
                expectStudent3
        ));

        StudentScoreService studentScoreService = new StudentScoreService(
                studentScoreRepository,
                studentPassRepository,
                studentFailRepository
        );

        // when
        List<ExamFailStudentResponse> expectFailResponses = Stream.of(expectStudent2, expectStudent3)
                .map((fail) -> new ExamFailStudentResponse(fail.getStudentName(), fail.getAvgScore()))
                .toList();
        List<ExamFailStudentResponse> responses = studentScoreService.getFailStudentList(givenTestExam);

        // then
        Assertions.assertIterableEquals(expectFailResponses, responses);
    }
}
