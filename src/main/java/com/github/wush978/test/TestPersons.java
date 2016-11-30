package com.github.wush978.test;

import com.github.wush978.vectorizer.BaseVectorizer;
import com.github.wush978.vectorizer.Vector;
import com.google.common.collect.Multiset;
import com.google.common.collect.TreeMultiset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wush.wu on 2016/11/15.
 */
public class TestPersons {

    public static List<PersonOuterClass.Person> getTestPersons() {
        List<PersonOuterClass.Person> result = new ArrayList();
        // 0
        result.add(
            PersonOuterClass.Person.newBuilder()
                .setId("A123456789")
                .setAge(11)
                .setSex("female")
                .setContact(PersonOuterClass.Person.Contact.newBuilder()
                        .setEmailAddress("email@example.com")
                )
                .setIsLive(true)
                .build()
        );
        // 1
        result.add(result.get(0).toBuilder().setId("M123456789").build());
        // 2
        result.add(result.get(0).toBuilder().setAge(12).setIsLive(false).build());
        // 3
        result.add(result.get(0).toBuilder().setSex("male").build());
        // 4
        result.add(result.get(0).toBuilder().setContact(
                result.get(0).getContact().toBuilder().setPostalCode("123")
        ).build());
        // 5
        result.add(result.get(0).toBuilder().setContact(
                result.get(0).getContact().toBuilder().setAddress("example address")
        ).build());
        // 6
        result.add(result.get(0).toBuilder()
                .addEducation(
                        PersonOuterClass.Person.Education.newBuilder()
                        .setSchoolName("ntu")
                )
                .addEducation(
                        PersonOuterClass.Person.Education.newBuilder()
                        .setSchoolName("hsnu")
                )
                .build());
        // 7
        result.add(result.get(0).toBuilder()
                .setCountryCode(PersonOuterClass.Person.CountryCode.TW)
                .build());
        return result;
    }

    static Vector.SparseVector.Builder toBuilder(String[] caregoricalName, String[] categoricalValue, String[] interactionName, String[] interactionValue1, String[] interactionValue2) {
        List<String> index = new ArrayList();
        List<Double> value = new ArrayList();
        for(int i = 0;i < caregoricalName.length;i++) {
            index.add("com.github.wush978.test.PersonOuterClass.Person." + caregoricalName[i] + BaseVectorizer.KEY_VALUE_DELIMITER + categoricalValue[i]);
            value.add(1.0);
        }
        for(int i = 0;i < interactionName.length;i++) {
            index.add(interactionName[i] + BaseVectorizer.KEY_VALUE_DELIMITER + interactionValue1[i] + BaseVectorizer.ROW_DELIMITER + interactionValue2[i]);
            value.add(1.0);
        }
        return Vector.SparseVector.newBuilder()
                .addAllIndex(index)
                .addAllValue(value);
    }

    public static List<Vector.SparseVector> getExpectedVectors() {
        List<Vector.SparseVector> result = new ArrayList();
        String
                defaultCategoricalName[] = {"age", "sex", "isLive"},
                defaultCategoricalValue[] = {"11", "female", "true"},
                defaultInteractionName[] = {"age-sex"},
                defaultInteractionValue1[] = {"11"},
                defaultInteractionValue2[] = {"female"};

        // 0
        result.add(
                toBuilder(
                        defaultCategoricalName,
                        defaultCategoricalValue,
                        defaultInteractionName,
                        defaultInteractionValue1,
                        defaultInteractionValue2
                ).build()
        );
        // 1
        result.add(
                result.get(0).toBuilder().build()
        );
        // 2
        result.add(
                toBuilder(
                        defaultCategoricalName,
                        new String[] {"12", "female", "false"},
                        defaultInteractionName,
                        new String[] {"12"},
                        defaultInteractionValue2
                ).build()
        );
        // 3
        result.add(
                toBuilder(
                        defaultCategoricalName,
                        new String[] {"11", "male", "true"},
                        defaultInteractionName,
                        defaultInteractionValue1,
                        new String[] {"male"}
                ).build()
        );
        // 4
        result.add(
                toBuilder(
                        new String[] {"age", "sex", "isLive", "Contact.postal_code"},
                        new String[] {"11", "female", "true", "123"},
                        defaultInteractionName,
                        defaultInteractionValue1,
                        defaultInteractionValue2
                ).build()
        );
        // 5
        result.add(
                toBuilder(
                        defaultCategoricalName,
                        defaultCategoricalValue,
                        defaultInteractionName,
                        defaultInteractionValue1,
                        defaultInteractionValue2
                ).build()
        );
        // 6
        result.add(
                toBuilder(
                        new String[] {"age", "sex", "isLive", "Education.school_name", "Education.school_name"},
                        new String[] {"11", "female", "true", "ntu", "hsnu"},
                        new String[] {"age-sex", "age-school_name", "age-school_name"},
                        new String[] {"11", "11", "11"},
                        new String[] {"female", "ntu", "hsnu"}
                ).build()
        );
        // 7
        result.add(
                toBuilder(
                        new String[] {"age", "sex", "isLive", "country_code"},
                        new String[] {"11", "female", "true", "TW"},
                        defaultInteractionName,
                        defaultInteractionValue1,
                        defaultInteractionValue2
                ).build()
        );

        return result;
    }

    private static class Entry implements Comparable<Entry> {

        String k;

        Double v;

        Entry() {
            this.k = null;
            this.v = null;
        }

        Entry(String k, Double v) {
            this.k = k;
            this.v = v;
        }

        public String getKey() {
            return k;
        }

        public Double getValue() {
            return v;
        }

        public String setKey(String key) {
            k = key;
            return key;
        }

        public Double setValue(Double value) {
            v = value;
            return value;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Entry) {
                return compareTo((Entry) obj) == 0;
            }
            return false;
        }

        public int compareTo(Entry o) {
            int result = k.compareTo(o.k);
            if (result == 0) {
                return v.compareTo(o.v);
            }
            return result;
        }
    }

    public static boolean equalWithoutOrder(Vector.SparseVector a, Vector.SparseVector b) throws RuntimeException {
        List<String> sa = a.getIndexList(),
                sb = b.getIndexList();
        if (sa.size() != sb.size()) return false;
        List<Double> da = a.getValueList(), db = b.getValueList();
        if (da.size() != sa.size() | db.size() != sb.size()) {
            throw new RuntimeException("The index and value are inconsistent");
        }
        Multiset<Entry>
                l1 = TreeMultiset.create(),
                l2 = TreeMultiset.create();

        for(int i = 0;i < sa.size();i++) {
            l1.add(new Entry(sa.get(i), da.get(i)));
            l2.add(new Entry(sb.get(i), db.get(i)));
        }
        return l1.equals(l2);
    }
}
