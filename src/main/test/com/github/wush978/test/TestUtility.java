package com.github.wush978.test;

import com.github.wush978.vectorizer.Vector;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;

/**
 * Created by gmobi-wush on 2016/11/18.
 */
@RunWith(JUnit4.class)
public class TestUtility {

    @Test
    public void testEqualWithoutOrder() {
        Vector.SparseVector a = Vector.SparseVector.newBuilder()
            .addAllIndex(Arrays.asList(new String[] {"a", "b", "c" }))
            .addAllValue(Arrays.asList(new Double[] {1.1, 2.2, 3.3}))
            .build();
        Vector.SparseVector b = Vector.SparseVector.newBuilder()
                .addAllIndex(Arrays.asList(new String[] {"a", "c", "b" }))
                .addAllValue(Arrays.asList(new Double[] {1.1, 3.3, 2.2}))
                .build();
        assert(TestPersons.equalWithoutOrder(a, b));
        Vector.SparseVector c = Vector.SparseVector.newBuilder()
                .addAllIndex(Arrays.asList(new String[] {"a", "b", "c" }))
                .addAllValue(Arrays.asList(new Double[] {1.1, 3.3, 2.2}))
                .build();
        assert(!TestPersons.equalWithoutOrder(a, c));
    }

}
