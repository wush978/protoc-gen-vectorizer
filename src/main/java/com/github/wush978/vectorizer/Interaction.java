package com.github.wush978.vectorizer;

import java.io.Serializable;

/**
 * Created by wush.wu on 2016/11/15.
 */
public class Interaction<A extends Comparable<A> & Serializable, B extends Comparable<B> & Serializable> implements Comparable<Interaction<A,B>>, Serializable {

    ComparableOptional<A> a;

    ComparableOptional<B> b;

    Double value = 1.0;

    public Interaction() { }

    public static <A extends Comparable<A> & Serializable, B extends Comparable<B> & Serializable> Interaction<A, B> of() {
        return new Interaction<A, B>();
    }

    public Interaction<A,B> setA(A a) {
        this.a = ComparableOptional.of(a);
        return this;
    }

    public Interaction<A,B> setB(B b) {
        this.b = ComparableOptional.of(b);
        return this;
    }

    public Interaction<A,B> setValue(Double value) {
        this.value = this.value * value;
        return this;
    }

    public int compareTo(Interaction<A, B> o) {
        int ra = a.get().compareTo(o.a.get());
        if (a.compareTo(o.a) != 0) {
            return ra;
        } else {
            return b.compareTo(o.b);
        }
    }

    public boolean isPresent() {
        return a.isPresent() & b.isPresent();
    }

    @Override
    public String toString() {
        return a.get().toString() + BaseVectorizer.ROW_DELIMITER + b.get().toString();
    }

    public Double getValue() {
        return value;
    }
}
