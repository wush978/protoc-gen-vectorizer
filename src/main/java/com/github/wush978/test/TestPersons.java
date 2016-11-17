package com.github.wush978.test;

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

        result.add(result.get(0).toBuilder().setId("M123456789").build());
        result.add(result.get(0).toBuilder().setAge(12).build());
        result.add(result.get(0).toBuilder().setSex("male").build());
        result.add(result.get(0).toBuilder().setContact(
                result.get(0).getContact().toBuilder().setPostalCode("123")
        ).build());
        result.add(result.get(0).toBuilder().setContact(
                result.get(0).getContact().toBuilder().setAddress("example address")
        ).build());
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
        List<String> sa = a.getIndexList(), sb = b.getIndexList();
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
