package com.brent.ik.combinations;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class SubListTest {
    @Test
    void testSublists() {

        List<List<Coin>> list1 = Arrays.asList(
                Arrays.asList(new Coin("gold", 1.0),
                        new Coin("silver", 0.5)),
                Arrays.asList(new Coin("bronze", 0.1),
                        new Coin("platinum", 2.0))
        );

        List<List<Coin>> list2 = Arrays.asList(
                Arrays.asList(new Coin("bronze", 0.1),
                        new Coin("platinum", 2.0)),
                Arrays.asList(new Coin("silver", 0.5),
                        new Coin("gold", 1.0))
        );


        Assertions.assertThat(list1)
                .hasSize(list2.size())
                .allSatisfy(subList1 ->
                        Assertions.assertThat(list2)
                                .anySatisfy(subList2 ->
                                        Assertions.assertThat(subList1)
                                                .containsExactlyInAnyOrderElementsOf(subList2)
                                )
                );
    }

}



