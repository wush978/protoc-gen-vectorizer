package io.github.wush978.reference;

import io.github.wush978.vectorizer.Vector;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import io.github.wush978.test.PersonOuterClass;

/**
 * Created by wush on 2016/11/9.
 */

@RunWith(JUnit4.class)
public class VectorizerTest {

    @Test
    public void testVectorizer() {

        PersonOuterClass.Person person1 = PersonOuterClass.Person.newBuilder()
                .setId("A123456789")
                .setAge(11)
                .setSex("female")
                .build();

        PersonOuterClass.Person person2 = person1.toBuilder().setId("M123456789").build();
        PersonOuterClass.Person person3 = person1.toBuilder().setAge(12).build();
        PersonOuterClass.Person person4 = person1.toBuilder().setSex("male").build();

        Vector.SparseVector v1 = Vectorizer.apply(person1),
                v2 = Vectorizer.apply(person2),
                v3 = Vectorizer.apply(person3),
                v4 = Vectorizer.apply(person4);
        assert(v1.getIndexList().size() == 2);
        assert(v1.getIndexList().size() == v1.getValueList().size());
        for(Double d : v1.getValueList()) {
            assert(d == 1.0);
        }
        assert(v1.equals(v2));
        assert(!v1.equals(v3));
        assert(!v1.equals(v4));
    }
}
