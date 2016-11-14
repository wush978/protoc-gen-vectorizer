package com.github.wush978.test;

import com.github.wush978.vectorizer.Vector;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Created by wush on 2016/11/14.
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

        Vector.SparseVector v1 = com.github.wush978.reference.Vectorizer.apply(person1),
                v2 = com.github.wush978.reference.Vectorizer.apply(person2),
                v3 = com.github.wush978.reference.Vectorizer.apply(person3),
                v4 = com.github.wush978.reference.Vectorizer.apply(person4),
                u1 = com.github.wush978.test.Vectorizer.apply(person1),
                u2 = com.github.wush978.test.Vectorizer.apply(person2),
                u3 = com.github.wush978.test.Vectorizer.apply(person3),
                u4 = com.github.wush978.test.Vectorizer.apply(person4);

        assert(v1.equals(u1));
        assert(v2.equals(u2));
        assert(v3.equals(u3));
        assert(v4.equals(u4));
    }

}
