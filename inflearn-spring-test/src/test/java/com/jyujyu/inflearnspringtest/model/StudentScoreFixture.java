package com.jyujyu.inflearnspringtest.model;

public class StudentScoreFixture {

    public static StudentScore padded() {
        return StudentScore.builder()
                .studentName("defaultName")
                .exam("defaultExam")
                .korScore(80)
                .englishScore(100)
                .mathScore(90)
                .build();
    }

    public static StudentScore failed() {
        return StudentScore.builder()
                .studentName("defaultName")
                .exam("defaultExam")
                .korScore(50)
                .englishScore(40)
                .mathScore(30)
                .build();
    }
}
