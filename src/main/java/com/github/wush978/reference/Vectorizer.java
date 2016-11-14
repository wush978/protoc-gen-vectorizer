package com.github.wush978.reference;

import com.github.wush978.vectorizer.BaseVectorizer;
import com.github.wush978.test.PersonOuterClass;
import com.github.wush978.vectorizer.Vector;

/**
 * Created by wush on 2016/11/9.
 */
public class Vectorizer extends BaseVectorizer {

    public static Vector.SparseVector apply(PersonOuterClass.Person person) {
        Vector.SparseVector.Builder builder = Vector.SparseVector.newBuilder();

        String prefix = person.getClass().getCanonicalName() + ".";

        categorical(prefix + "age" + Integer.toString(person.getAge()).toString(), builder);

        categorical(prefix + "sex" + person.getSex().toString(), builder);

        return builder.build();
    }

}
