package com.jyujyu.inflearnspringtest.model;

import com.jyujyu.inflearnspringtest.MyCalculator;

public class StudentPassFixture {

    public static StudentPass create(StudentScore studentScore) {
        return StudentPass.builder()
                .studentName(studentScore.getStudentName())
                .exam(studentScore.getExam())
                .avgScore(new MyCalculator(0.0)
                        .add(studentScore.getKorScore().doubleValue())
                        .add(studentScore.getEnglishScore().doubleValue())
                        .add(studentScore.getMathScore().doubleValue())
                        .divide(3.0)
                        .getResult())
                .build();
    }

    public static StudentPass create(String studentName, String exam) {
        return StudentPass.builder()
                .studentName(studentName)
                .exam(exam)
                .avgScore(80.0)
                .build();
    }
}
