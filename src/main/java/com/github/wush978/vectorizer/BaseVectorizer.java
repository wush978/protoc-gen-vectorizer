package com.github.wush978.vectorizer;

/**
 * Created by wush on 2016/11/9.
 */
public class BaseVectorizer {

    protected static void categorical(String input, Vector.SparseVector.Builder builder) {
        builder.addIndex(input);
        builder.addValue(1.0);
    }

    protected static void numerical(Double input, String name, Vector.SparseVector.Builder builder) {
        builder.addIndex(name);
        builder.addValue(input);
    }

}
