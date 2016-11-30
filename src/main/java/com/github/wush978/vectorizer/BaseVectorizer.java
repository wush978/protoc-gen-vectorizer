package com.github.wush978.vectorizer;

import com.google.protobuf.ProtocolMessageEnum;

import java.util.Map;

/**
 * Created by wush on 2016/11/9.
 */
public class BaseVectorizer {

    public static Double CATEGORICAL_VALUE = 1.0;

    public static char ROW_DELIMITER = '\1';

    public static char COLLECTION_ITEM_DELIMITER = '\2';

    public static char KEY_VALUE_DELIMITER = '\3';

    protected static Vector.SparseVector.Builder apply(Map<String, Interaction> interaction, Vector.SparseVector.Builder builder) {
        for(Map.Entry<String, Interaction> e : interaction.entrySet()) {
            String[] keys = e.getValue().getKeys();
            if (keys == null) continue;
            Double[] values = e.getValue().getValues();
            if (values == null) continue;
            for(int i = 0;i < keys.length;i++) {
                numerical(values[i], getName(e.getKey(),keys[i]), builder);
            }
        }
        return builder;
    }

    /**
     * Append builder2 to builder
     *
     * @param builder
     * @param builder2
     */
    protected static Vector.SparseVector.Builder append(Vector.SparseVector.Builder builder, Vector.SparseVector.Builder builder2) {
        builder.addAllIndex(builder2.getIndexList());
        builder.addAllValue(builder2.getValueList());
        return builder;
    }

    private static String getName(String fieldName, String name) {
        return fieldName + KEY_VALUE_DELIMITER + name;
    }

    protected static Vector.SparseVector.Builder categorical(String fieldName, Boolean input, Vector.SparseVector.Builder builder) {
        builder.addIndex(getName(fieldName, (input ? "true" : "false")));
        builder.addValue(CATEGORICAL_VALUE);
        return builder;
    }

    protected static Vector.SparseVector.Builder categorical(String fieldName, Integer input, Vector.SparseVector.Builder builder) {
        builder.addIndex(getName(fieldName, Integer.toString(input)));
        builder.addValue(CATEGORICAL_VALUE);
        return builder;
    }

    protected static Vector.SparseVector.Builder categorical(String fieldName, Double input, Vector.SparseVector.Builder builder) {
        builder.addIndex(getName(fieldName, Double.toString(input)));
        builder.addValue(CATEGORICAL_VALUE);
        return builder;
    }

    protected static Vector.SparseVector.Builder categorical(String fieldName, String input, Vector.SparseVector.Builder builder) {
        builder.addIndex(getName(fieldName, input));
        builder.addValue(CATEGORICAL_VALUE);
        return builder;
    }

    protected static Vector.SparseVector.Builder categorical(String fieldName, ProtocolMessageEnum input, Vector.SparseVector.Builder builder) {
        builder.addIndex(getName(fieldName, input.toString()));
        builder.addValue(CATEGORICAL_VALUE);
        return builder;
    }

    protected static Vector.SparseVector.Builder numerical(Double input, String name, Vector.SparseVector.Builder builder) {
        builder.addIndex(name);
        builder.addValue(input);
        return builder;
    }

}
