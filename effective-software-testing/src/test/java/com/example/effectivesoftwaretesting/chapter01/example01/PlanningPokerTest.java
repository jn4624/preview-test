package com.example.effectivesoftwaretesting.chapter01.example01;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PlanningPokerTest {

    @Test
    void rejectNullInput() {
        assertThatThrownBy(() ->
                new PlanningPoker().identifyExtremes(null)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rejectEmptyList() {
        assertThatThrownBy(() -> {
            List<Estimate> emptyList = Collections.emptyList();
            new PlanningPoker().identifyExtremes(emptyList);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rejectSingleEstimate() {
        assertThatThrownBy(() -> {
            List<Estimate> list = Collections.singletonList(new Estimate("Eleanor", 1));
            new PlanningPoker().identifyExtremes(list);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void twoEstimates() {
        List<Estimate> list = Arrays.asList(
                new Estimate("Mauricio", 10),
                new Estimate("Frank", 5));

        List<String> devs = new PlanningPoker().identifyExtremes(list);

        assertThat(devs)
                .containsExactlyInAnyOrder("Mauricio", "Frank");
    }

    @Test
    void manyEstimates() {
        List<Estimate> list = Arrays.asList(
                new Estimate("Mauricio", 10),
                new Estimate("Arie", 5),
                new Estimate("Frank", 7));

        List<String> devs = new PlanningPoker().identifyExtremes(list);

        assertThat(devs)
                .containsExactlyInAnyOrder("Mauricio", "Arie");
    }
}
