package com.github.wush978.vectorizer;

import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by wush.wu on 2016/11/15.
 */
public class Interaction implements Serializable {

    List<String> a;

    List<String> b;

    List<Double> va;

    List<Double> vb;

    public Interaction() { }

    public static Interaction of() {
        return new Interaction();
    }

    public Interaction addA(String a) {
        if (this.a == null) this.a = new ArrayList();
        this.a.add(a);
        return this;
    }

    public Interaction addB(String b) {
        if (this.b == null) this.b = new ArrayList();
        this.b.add(b);
        return this;
    }

    public Interaction addAValue(Double value) {
        if (this.va == null) this.va = new ArrayList();
        this.va.add(value);
        return this;
    }

    public Interaction addBValue(Double value) {
        if (this.vb == null) this.vb = new ArrayList();
        this.vb.add(value);
        return this;
    }

    public boolean isPresent() {
        if (a == null) return false;
        if (b == null) return false;
        if (a.size() > 0 & b.size() > 0) {
            if (a.size() != va.size()) throw new RuntimeException("a and va are inconsistent");
            if (b.size() != vb.size()) throw new RuntimeException("b and vb are inconsistent");
            return true;
        }
        return false;
    }

    @Nullable
    public String[] getKeys() {
        if (!isPresent()) return null;
        String[] retval = new String[a.size() * b.size()];
        int index = 0;
        for(String sa : a) {
            for(String sb : b) {
                retval[index++] = sa + BaseVectorizer.ROW_DELIMITER + sb;
            }
        }
        return retval;
    }

    @Nullable
    public Double[] getValues() {
        if (!isPresent()) return null;
        Double[] retval = new Double[a.size() * b.size()];
        int index = 0;
        for(Double da : va) {
            for(Double db : vb) {
                retval[index++] = da * db;
            }
        }
        return retval;
    }
}
