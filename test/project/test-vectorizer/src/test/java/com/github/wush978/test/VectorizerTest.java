package com.github.wush978.test;

import com.github.wush978.test.TestPersons;
import com.github.wush978.vectorizer.Vector;
import static com.github.wush978.test.TestPersons.equalWithoutOrder;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

/**
 * Created by wush on 2016/11/14.
 */
@RunWith(JUnit4.class)
public class VectorizerTest {

    @Test
    public void testVectorizer() {

        List<PersonOuterClass.Person> persons = TestPersons.getTestPersons();
        Vector.SparseVector referenceResult[] = new Vector.SparseVector[persons.size()];
        Collections2.transform(persons, new Function<PersonOuterClass.Person, Vector.SparseVector>() {
            public Vector.SparseVector apply(PersonOuterClass.Person input) {
                return com.github.wush978.reference.Vectorizer.apply(input).build();
            }
        }).toArray(referenceResult);

        assert(equalWithoutOrder(referenceResult[0], referenceResult[1]));
        assert(!equalWithoutOrder(referenceResult[0], referenceResult[2]));
        assert(!equalWithoutOrder(referenceResult[0], referenceResult[3]));
        assert(!equalWithoutOrder(referenceResult[0], referenceResult[4]));
        assert(equalWithoutOrder(referenceResult[0], referenceResult[5]));

        Vector.SparseVector testResult[] = new Vector.SparseVector[persons.size()];
        Collections2.transform(persons, new Function<PersonOuterClass.Person, Vector.SparseVector>() {
            public Vector.SparseVector apply(PersonOuterClass.Person input) {
                return com.github.wush978.test.Vectorizer.apply(input).build();
            }
        }).toArray(testResult);

        assert(referenceResult.length == testResult.length);
        for(int i = 0;i < persons.size();i++) {
            assert(equalWithoutOrder(referenceResult[i], testResult[i]));
        }
    }

}
