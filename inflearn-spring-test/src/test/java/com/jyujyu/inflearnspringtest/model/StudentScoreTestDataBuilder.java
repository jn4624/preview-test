package com.jyujyu.inflearnspringtest.model;

public class StudentScoreTestDataBuilder {

    /**
     * 문제점
     * - 데이터의 오버라이딩이 가능하다는 점
     * - 즉, 자유도가 너무 높은게 잘못된 코드를 작성하게 만들 수도 있다는 점
     */

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
