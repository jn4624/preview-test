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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentScoreService {

    private final StudentScoreRepository studentScoreRepository;
    private final StudentPassRepository studentPassRepository;
    private final StudentFailRepository studentFailRepository;

    public void saveScore(String studentName, String exam, Integer kroScore, Integer englishScore, Integer mathScore) {
        StudentScore studentScore = StudentScore.builder()
                .exam(exam)
                .studentName(studentName)
                .korScore(kroScore)
                .englishScore(englishScore)
                .mathScore(mathScore)
                .build();

        studentScoreRepository.save(studentScore);

        MyCalculator myCalculator = new MyCalculator(0.0);
        Double avgScore = myCalculator
                .add(kroScore.doubleValue())
                .add(englishScore.doubleValue())
                .add(mathScore.doubleValue())
                .divide(3.0)
                .getResult();

        if (avgScore >= 60) {
            StudentPass studentPass = StudentPass.builder()
                    .exam(exam)
                    .studentName(studentName)
                    .avgScore(avgScore)
                    .build();

            studentPassRepository.save(studentPass);
        } else {
            StudentFail studentFail = StudentFail.builder()
                    .exam(exam)
                    .studentName(studentName)
                    .avgScore(avgScore)
                    .build();

            studentFailRepository.save(studentFail);
        }
    }

    public List<ExamPassStudentResponse> getPassStudentList(String exam) {
        List<StudentPass> studentPasses = studentPassRepository.findAll();

        return studentPasses.stream()
                .filter((pass) -> pass.getExam().equals(exam))
                .map((pass) -> new ExamPassStudentResponse(pass.getStudentName(), pass.getAvgScore()))
                .toList();
    }

    public List<ExamFailStudentResponse> getFailStudentList(String exam) {
        List<StudentFail> studentFails = studentFailRepository.findAll();

        return studentFails.stream()
                .filter((fail) -> fail.getExam().equals(exam))
                .map((fail) -> new ExamFailStudentResponse(fail.getStudentName(), fail.getAvgScore()))
                .toList();
    }
}
