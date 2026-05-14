package com.jyujyu.inflearnspringtest.service;

import com.jyujyu.inflearnspringtest.IntegrationTest;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.util.List;

public class S3ServiceTest extends IntegrationTest {

    @Autowired
    private S3Service s3Service;

    @Test
    public void s3PutAndGetTest() throws Exception {
        // given
        String bucket = "test-bucket";
        String key = "sampleObject.txt";
        File sampleFile = new ClassPathResource("static/sample.txt").getFile();

        // when
        s3Service.putFile(bucket, key, sampleFile);

        // then
        File resultFile = s3Service.getFile(bucket, key);

        List<String> sampleFileLines = FileUtils.readLines(sampleFile);
        List<String> resultFileLines = FileUtils.readLines(resultFile);

        Assertions.assertIterableEquals(sampleFileLines, resultFileLines);
    }
}
