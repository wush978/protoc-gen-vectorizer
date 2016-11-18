package com.github.wush978.reference;

import com.github.wush978.vectorizer.BaseVectorizer;
import com.github.wush978.test.PersonOuterClass;
import com.github.wush978.vectorizer.Interaction;
import com.github.wush978.vectorizer.Vector;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wush on 2016/11/9.
 */
public class Vectorizer extends BaseVectorizer {

    private static Vector.SparseVector.Builder apply(PersonOuterClass.Person.Education src, Map<String, Interaction> interaction) {

        Vector.SparseVector.Builder builder = Vector.SparseVector.newBuilder();

        String prefix = src.getClass().getCanonicalName() + ".";

        categorical(getName(prefix + "school_name", src.getSchoolName()), builder);

        interaction.get("age-school_name").addB(src.getSchoolName());

        interaction.get("age-school_name").addBValue(CATEGORICAL_VALUE);

        return builder;

    }

    private static Vector.SparseVector.Builder apply(PersonOuterClass.Person.Contact src) {

        Vector.SparseVector.Builder builder = Vector.SparseVector.newBuilder();

        String prefix = src.getClass().getCanonicalName() + ".";

        if (src.hasPostalCode()) {
            categorical(getName(prefix + "postal_code", src.getPostalCode()), builder);
        }

        return builder;
    }

    public static Vector.SparseVector.Builder apply(PersonOuterClass.Person src) {

        Vector.SparseVector.Builder builder = Vector.SparseVector.newBuilder();

        Map<String, Interaction> interaction = new HashMap();

        interaction.put("age-sex", Interaction.<String, String>of());

        interaction.put("age-school_name", Interaction.<String, String>of());

        String prefix = src.getClass().getCanonicalName() + ".";

        categorical(getName(prefix + "age", Integer.toString(src.getAge())), builder);

        interaction.get("age-sex").addA(Integer.toString(src.getAge()));

        interaction.get("age-sex").addAValue(CATEGORICAL_VALUE);

        interaction.get("age-school_name").addA(Integer.toString(src.getAge()));

        interaction.get("age-school_name").addAValue(CATEGORICAL_VALUE);

        categorical(getName(prefix + "sex", src.getSex().toString()), builder);

        interaction.get("age-sex").addB(src.getSex().toString());

        interaction.get("age-sex").addBValue(CATEGORICAL_VALUE);

        for(int i = 0;i < src.getEducationCount();i++) {
            append(builder, apply(src.getEducation(i), interaction));
        }

        append(builder, apply(src.getContact()));

        apply(interaction, builder);

        return builder;
    }

}
