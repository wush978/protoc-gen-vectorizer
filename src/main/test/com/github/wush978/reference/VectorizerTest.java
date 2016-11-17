package com.github.wush978.reference;

import com.github.wush978.test.TestPersons;
import com.github.wush978.vectorizer.Vector;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.TreeMultiset;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import com.github.wush978.test.PersonOuterClass;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.github.wush978.test.TestPersons.equalWithoutOrder;

/**
 * Created by wush on 2016/11/9.
 */

@RunWith(JUnit4.class)
public class VectorizerTest {

    @Test
    public void testVectorizer() {
        List<PersonOuterClass.Person> persons = TestPersons.getTestPersons();
        Vector.SparseVector result[] = new Vector.SparseVector[persons.size()];
        Collections2.transform(persons, new Function<PersonOuterClass.Person, Vector.SparseVector>() {
            public Vector.SparseVector apply(PersonOuterClass.Person input) {
                return Vectorizer.apply(input).build();
            }
        }).toArray(result);

        assert(equalWithoutOrder(result[0], result[1]));
        assert(!equalWithoutOrder(result[0], result[2]));
        assert(!equalWithoutOrder(result[0], result[3]));
        assert(!equalWithoutOrder(result[0], result[4]));
        assert(equalWithoutOrder(result[0], result[5]));
    }
}
