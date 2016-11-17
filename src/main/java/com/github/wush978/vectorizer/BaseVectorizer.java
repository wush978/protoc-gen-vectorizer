package com.github.wush978.vectorizer;

import java.util.Map;

/**
 * Created by wush on 2016/11/9.
 */
public class BaseVectorizer {

    protected static Double CATEGORICAL_VALUE = 1.0;

    protected static char ROW_DELIMITER = '\1';

    protected static char COLLECTION_ITEM_DELIMITER = '\2';

    protected static char KEY_VALUE_DELIMITER = '\3';

    protected static String getName(String fieldName, String Value) {
        return fieldName + KEY_VALUE_DELIMITER + Value;
    }

    protected static Vector.SparseVector.Builder apply(Map<String, Interaction<String, String>> interaction, Vector.SparseVector.Builder builder) {
        for(Map.Entry<String, Interaction<String, String>> e : interaction.entrySet()) {
            if (e.getValue().isPresent()) {
                numerical(e.getValue().getValue(), getName(e.getKey(), e.getValue().toString()), builder);
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

    protected static Vector.SparseVector.Builder categorical(String input, Vector.SparseVector.Builder builder) {
        builder.addIndex(input);
        builder.addValue(CATEGORICAL_VALUE);
        return builder;
    }

    protected static Vector.SparseVector.Builder numerical(Double input, String name, Vector.SparseVector.Builder builder) {
        builder.addIndex(name);
        builder.addValue(input);
        return builder;
    }

}
