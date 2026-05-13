package com.jyujyu.inflearnspringtest.model;

public class StudentScoreTestDataBuilder {

    public static StudentScore.StudentScoreBuilder padded() {
        return StudentScore.builder()
                .studentName("defaultName")
                .exam("defaultExam")
                .korScore(80)
                .englishScore(100)
                .mathScore(90);
    }

    public static StudentScore.StudentScoreBuilder failed() {
        return StudentScore.builder()
                .studentName("defaultName")
                .exam("defaultExam")
                .korScore(50)
                .englishScore(40)
                .mathScore(30);
    }
}
