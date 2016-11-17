package com.github.wush978.reference;

import com.github.wush978.vectorizer.BaseVectorizer;
import com.github.wush978.test.PersonOuterClass;
import com.github.wush978.vectorizer.Interaction;
import com.github.wush978.vectorizer.Vector;
import com.google.common.collect.Iterables;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wush on 2016/11/9.
 */
public class Vectorizer extends BaseVectorizer {

    public static Vector.SparseVector.Builder apply(PersonOuterClass.Person.Education src) {

        Vector.SparseVector.Builder builder = Vector.SparseVector.newBuilder();

        String prefix = src.getClass().getCanonicalName() + ".";

        categorical(prefix + "school_name" + src.getSchoolName(), builder);

        return builder;

    }

    public static Vector.SparseVector.Builder apply(PersonOuterClass.Person.Contact src) {

        Vector.SparseVector.Builder builder = Vector.SparseVector.newBuilder();

        String prefix = src.getClass().getCanonicalName() + ".";

        if (src.hasPostalCode()) {
            categorical(prefix + "contact" + src.getPostalCode(), builder);
        }

        return builder;
    }

    public static Vector.SparseVector.Builder apply(PersonOuterClass.Person src) {

        Vector.SparseVector.Builder builder = Vector.SparseVector.newBuilder();

        Map<String, Interaction<String, String>> interaction = new HashMap();

        interaction.put("age-sex", Interaction.<String, String>of());

        String prefix = src.getClass().getCanonicalName() + ".";

        categorical(getName(prefix + "age", Integer.toString(src.getAge())), builder);

        interaction.get("age-sex").setA(Integer.toString(src.getAge()));

        interaction.get("age-sex").setValue(CATEGORICAL_VALUE);

        categorical(getName(prefix + "sex", src.getSex().toString()), builder);

        interaction.get("age-sex").setB(src.getSex().toString());

        interaction.get("age-sex").setValue(CATEGORICAL_VALUE);

        for(int i = 0;i < src.getEducationCount();i++) {
            append(builder, apply(src.getEducation(i)));
        }

        append(builder, apply(src.getContact()));

        apply(interaction, builder);

        return builder;
    }

}
