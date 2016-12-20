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

        categorical(prefix + "school_name", src.getSchoolName(), builder);

        interaction.get("age-school_name").addBCategorical(src.getSchoolName());

        interaction.get("age-school_name").addBCategoricalValue(src.getSchoolName());

        return builder;

    }

    private static Vector.SparseVector.Builder apply(PersonOuterClass.Person.Contact src) {

        Vector.SparseVector.Builder builder = Vector.SparseVector.newBuilder();

        String prefix = src.getClass().getCanonicalName() + ".";

        if (src.hasPostalCode()) {
            categorical(prefix + "postal_code", src.getPostalCode(), builder);
        }

        return builder;
    }

    public static Vector.SparseVector.Builder apply(PersonOuterClass.Person src) {

        Vector.SparseVector.Builder builder = Vector.SparseVector.newBuilder();

        Map<String, Interaction> interaction = new HashMap();

        interaction.put("age-sex", Interaction.<String, String>of());

        interaction.put("age-school_name", Interaction.<String, String>of());

        String prefix = src.getClass().getCanonicalName() + ".";

        categorical(prefix + "age", src.getAge(), builder);

        interaction.get("age-sex").addACategorical(src.getAge());

        interaction.get("age-sex").addACategoricalValue(src.getAge());

        interaction.get("age-school_name").addACategorical(src.getAge());

        interaction.get("age-school_name").addACategoricalValue(src.getAge());

        categorical(prefix + "sex", src.getSex(), builder);

        interaction.get("age-sex").addBCategorical(src.getSex());

        interaction.get("age-sex").addBCategoricalValue(src.getSex());

        append(builder, apply(src.getContact()));

        for(int i = 0;i < src.getEducationCount();i++) {
            append(builder, apply(src.getEducation(i), interaction));
        }

        if (src.hasCountryCode()) categorical(prefix + "country_code", src.getCountryCode(), builder);

        categorical(prefix + "is_live", (src.getIsLive() ? "true" : "false"), builder);

        categorical(prefix + "created_at", com.github.wush978.util.SecondToHour.SecondToHour(src.getCreatedAt()), builder);

        apply(interaction, builder);

        return builder;
    }

}
