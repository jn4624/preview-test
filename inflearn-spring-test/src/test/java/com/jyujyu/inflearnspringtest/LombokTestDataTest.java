package com.jyujyu.inflearnspringtest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LombokTestDataTest {

    /**
     * Test 어노테이션의 import 패키지가 AssertJ는
     * junit4의 패키지이므로 jupiter import 패키지를 사용하도록 한다.
     */

    @Test
    public void testDataTest() {
        TestData testData = new TestData();
        testData.setName("jyujyu");

        Assertions.assertEquals("jyujyu", testData.getName());
    }
}
