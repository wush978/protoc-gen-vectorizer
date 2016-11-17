package com.github.wush978.vectorizer;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Supplier;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by wush.wu on 2016/11/15.
 */
final public class ComparableOptional<T extends Comparable<T> & Serializable> implements Comparable<ComparableOptional<T>> {

    Optional<T> reference;

    private ComparableOptional() {
        reference = Optional.absent();
    }

    public static <T extends Comparable<T> & Serializable> ComparableOptional<T> of() {
        return new ComparableOptional();
    }

    private ComparableOptional(T reference) {
        this.reference = Optional.of(reference);
    }

    public static <T extends Comparable<T> & Serializable> ComparableOptional<T> of(T reference) {
        return new ComparableOptional(reference);
    }

    public boolean isPresent() {
        return reference.isPresent();
    }

    public T get() {
        return reference.get();
    }

    public T or(T t) {
        return reference.or(t);
    }

    public Optional<T> or(Optional<? extends T> optional) {
        return reference.or(optional);
    }

    public T or(Supplier<? extends T> supplier) {
        return reference.or(supplier);
    }

    public T orNull() {
        return reference.orNull();
    }

    public Set<T> asSet() {
        return reference.asSet();
    }

    public <V> Optional<V> transform(Function<? super T, V> function) {
        return reference.transform(function);
    }

    public boolean equals(Object o) {
        if (o instanceof ComparableOptional) {
            if (reference.isPresent()) {
                if (((ComparableOptional) o).isPresent()) {
                    try {
                        T t = (T) ((ComparableOptional) o).get();
                        return reference.get().compareTo(t) == 0;
                    } catch (ClassCastException e) {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                // reference is absent
                return !((ComparableOptional) o).isPresent();
            }
        }
        return false;
    }

    public int hashCode() {
        return reference.hashCode();
    }

    public String toString() {
        if (reference.isPresent()) {
            return "ComparableOptional.of(" + reference.get() + ")";
        } else {
            return "ComparableOptional.of()";
        }
    }

    public int compareTo(ComparableOptional<T> o) {
        if (reference.isPresent()) {
            if (o.isPresent()) {
                return reference.get().compareTo(o.get());
            } else {
                return 1;
            }
        } else {
            // reference is absent
            if (o.isPresent()) {
                return -1;
            } else {
                return 0;
            }
        }
    }
}
