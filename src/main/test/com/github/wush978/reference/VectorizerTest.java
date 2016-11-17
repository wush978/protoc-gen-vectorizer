package com.github.wush978.reference;

import com.github.wush978.test.TestPersons;
import com.github.wush978.vectorizer.Vector;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.TreeMultiset;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import com.github.wush978.test.PersonOuterClass;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by wush on 2016/11/9.
 */

@RunWith(JUnit4.class)
public class VectorizerTest {

    private class Entry implements Comparable<Entry> {

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

    boolean equalWithoutOrder(Vector.SparseVector a, Vector.SparseVector b) throws RuntimeException {
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

    @Test
    public void testVectorizer() {
        List<PersonOuterClass.Person> persons = TestPersons.getTestPersons();
        Vector.SparseVector result[] = new Vector.SparseVector[persons.size()];
        Collections2.transform(persons, new Function<PersonOuterClass.Person, Vector.SparseVector>() {
            public Vector.SparseVector apply(PersonOuterClass.Person input) {
                return Vectorizer.apply(input).build();
            }
        }).toArray(result);

        assert(equalWithoutOrder(result[0], result[1]));
        assert(!equalWithoutOrder(result[0], result[2]));
        assert(!equalWithoutOrder(result[0], result[3]));
        assert(!equalWithoutOrder(result[0], result[4]));
        assert(equalWithoutOrder(result[0], result[5]));
    }
}
