package com.github.wush978.test;

import com.github.wush978.vectorizer.BaseVectorizer;
import com.github.wush978.vectorizer.Vector;
import com.google.common.collect.Multiset;
import com.google.common.collect.TreeMultiset;

import java.util.ArrayList;
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
                .build()
        );
        // 1
        result.add(result.get(0).toBuilder().setId("M123456789").build());
        // 2
        result.add(result.get(0).toBuilder().setAge(12).build());
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

    public static List<Vector.SparseVector> getExpectedVectors() {
        List<Vector.SparseVector> result = new ArrayList();
        // 0
        result.add(
                Vector.SparseVector.newBuilder()
                        .addIndex("com.github.wush978.test.PersonOuterClass.Person.age" + BaseVectorizer.KEY_VALUE_DELIMITER + "11")
                        .addValue(1.0)
                        .addIndex("com.github.wush978.test.PersonOuterClass.Person.sex" + BaseVectorizer.KEY_VALUE_DELIMITER + "female")
                        .addValue(1.0)
                        .addIndex("age-sex" + BaseVectorizer.KEY_VALUE_DELIMITER + "11" + BaseVectorizer.ROW_DELIMITER + "female")
                        .addValue(1.0)
                        .build()
        );
        // 1
        result.add(
                Vector.SparseVector.newBuilder()
                        .addIndex("com.github.wush978.test.PersonOuterClass.Person.age" + BaseVectorizer.KEY_VALUE_DELIMITER + "11")
                        .addValue(1.0)
                        .addIndex("com.github.wush978.test.PersonOuterClass.Person.sex" + BaseVectorizer.KEY_VALUE_DELIMITER + "female")
                        .addValue(1.0)
                        .addIndex("age-sex" + BaseVectorizer.KEY_VALUE_DELIMITER + "11" + BaseVectorizer.ROW_DELIMITER + "female")
                        .addValue(1.0)
                        .build()
        );
        // 2
        result.add(
                Vector.SparseVector.newBuilder()
                        .addIndex("com.github.wush978.test.PersonOuterClass.Person.age" + BaseVectorizer.KEY_VALUE_DELIMITER + "12")
                        .addValue(1.0)
                        .addIndex("com.github.wush978.test.PersonOuterClass.Person.sex" + BaseVectorizer.KEY_VALUE_DELIMITER + "female")
                        .addValue(1.0)
                        .addIndex("age-sex" + BaseVectorizer.KEY_VALUE_DELIMITER + "12" + BaseVectorizer.ROW_DELIMITER + "female")
                        .addValue(1.0)
                        .build()
        );
        // 3
        result.add(
                Vector.SparseVector.newBuilder()
                        .addIndex("com.github.wush978.test.PersonOuterClass.Person.age" + BaseVectorizer.KEY_VALUE_DELIMITER + "11")
                        .addValue(1.0)
                        .addIndex("com.github.wush978.test.PersonOuterClass.Person.sex" + BaseVectorizer.KEY_VALUE_DELIMITER + "male")
                        .addValue(1.0)
                        .addIndex("age-sex" + BaseVectorizer.KEY_VALUE_DELIMITER + "11" + BaseVectorizer.ROW_DELIMITER + "male")
                        .addValue(1.0)
                        .build()
        );
        // 4
        result.add(
                Vector.SparseVector.newBuilder()
                        .addIndex("com.github.wush978.test.PersonOuterClass.Person.age" + BaseVectorizer.KEY_VALUE_DELIMITER + "11")
                        .addValue(1.0)
                        .addIndex("com.github.wush978.test.PersonOuterClass.Person.sex" + BaseVectorizer.KEY_VALUE_DELIMITER + "female")
                        .addValue(1.0)
                        .addIndex("age-sex" + BaseVectorizer.KEY_VALUE_DELIMITER + "11" + BaseVectorizer.ROW_DELIMITER + "female")
                        .addValue(1.0)
                        .addIndex("com.github.wush978.test.PersonOuterClass.Person.Contact.postal_code" + BaseVectorizer.KEY_VALUE_DELIMITER + "123")
                        .addValue(1.0)
                        .build()
        );
        // 5
        result.add(
                Vector.SparseVector.newBuilder()
                        .addIndex("com.github.wush978.test.PersonOuterClass.Person.age" + BaseVectorizer.KEY_VALUE_DELIMITER + "11")
                        .addValue(1.0)
                        .addIndex("com.github.wush978.test.PersonOuterClass.Person.sex" + BaseVectorizer.KEY_VALUE_DELIMITER + "female")
                        .addValue(1.0)
                        .addIndex("age-sex" + BaseVectorizer.KEY_VALUE_DELIMITER + "11" + BaseVectorizer.ROW_DELIMITER + "female")
                        .addValue(1.0)
                        .build()
        );
        // 6
        result.add(
                Vector.SparseVector.newBuilder()
                        .addIndex("com.github.wush978.test.PersonOuterClass.Person.age" + BaseVectorizer.KEY_VALUE_DELIMITER + "11")
                        .addValue(1.0)
                        .addIndex("com.github.wush978.test.PersonOuterClass.Person.sex" + BaseVectorizer.KEY_VALUE_DELIMITER + "female")
                        .addValue(1.0)
                        .addIndex("age-sex" + BaseVectorizer.KEY_VALUE_DELIMITER + "11" + BaseVectorizer.ROW_DELIMITER + "female")
                        .addValue(1.0)
                        .addIndex("com.github.wush978.test.PersonOuterClass.Person.Education.school_name" + BaseVectorizer.KEY_VALUE_DELIMITER + "ntu")
                        .addValue(1.0)
                        .addIndex("com.github.wush978.test.PersonOuterClass.Person.Education.school_name" + BaseVectorizer.KEY_VALUE_DELIMITER + "hsnu")
                        .addValue(1.0)
                        .addIndex("age-school_name" + BaseVectorizer.KEY_VALUE_DELIMITER + "11" + BaseVectorizer.ROW_DELIMITER + "ntu")
                        .addValue(1.0)
                        .addIndex("age-school_name" + BaseVectorizer.KEY_VALUE_DELIMITER + "11" + BaseVectorizer.ROW_DELIMITER + "hsnu")
                        .addValue(1.0)
                        .build()
        );
        // 7
        result.add(
                Vector.SparseVector.newBuilder()
                        .addIndex("com.github.wush978.test.PersonOuterClass.Person.age" + BaseVectorizer.KEY_VALUE_DELIMITER + "11")
                        .addValue(1.0)
                        .addIndex("com.github.wush978.test.PersonOuterClass.Person.sex" + BaseVectorizer.KEY_VALUE_DELIMITER + "female")
                        .addValue(1.0)
                        .addIndex("age-sex" + BaseVectorizer.KEY_VALUE_DELIMITER + "11" + BaseVectorizer.ROW_DELIMITER + "female")
                        .addValue(1.0)
                        .addIndex("com.github.wush978.test.PersonOuterClass.Person.country_code" + BaseVectorizer.KEY_VALUE_DELIMITER + "TW")
                        .addValue(1.0)
                        .build()
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
