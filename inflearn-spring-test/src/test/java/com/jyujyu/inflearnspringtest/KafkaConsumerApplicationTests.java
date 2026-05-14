package com.jyujyu.inflearnspringtest;

import com.jyujyu.inflearnspringtest.service.KafkaConsumerService;
import com.jyujyu.inflearnspringtest.service.KafkaProducerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

public class KafkaConsumerApplicationTests extends IntegrationTest {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @MockitoBean
    private KafkaConsumerService kafkaConsumerService;

    @Test
    public void kafkaSendAndConsumeTest() {
        // given
        String topic = "test-topic";
        String expectValue = "expect-value";

        // when
        kafkaProducerService.send(topic, expectValue);

        // then
        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(kafkaConsumerService, Mockito.timeout(5000).times(1))
                .process(stringCaptor.capture());

        Assertions.assertEquals(expectValue, stringCaptor.getValue());
    }
}
