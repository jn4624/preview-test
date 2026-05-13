package com.jyujyu.inflearnspringtest.model;

import com.jyujyu.inflearnspringtest.MyCalculator;

public class StudentFailFixture {

    public static StudentFail create(StudentScore studentScore) {
        return StudentFail.builder()
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

    public static StudentFail create(String studentName, String exam) {
        return StudentFail.builder()
                .studentName(studentName)
                .exam(exam)
                .avgScore(40.0)
                .build();
    }
}
