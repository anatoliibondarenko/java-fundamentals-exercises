package com.bobocode.se;

import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

/**
 * A generic comparator that is comparing a random field of the given class. The field is either primitive or
 * {@link Comparable}. It is chosen during comparator instance creation and is used for all comparisons.
 * <p>
 * If no field is available to compare, the constructor throws {@link IllegalArgumentException}
 *
 * @param <T> the type of the objects that may be compared by this comparator
 *            <p><p>
 *            <strong>
 *            <p>
 * @author Stanislav Zabramnyi
 */
public class RandomFieldComparator<T> implements Comparator<T> {
    private Field randomField;
    private Class<?> givenClass;

    public RandomFieldComparator(Class<T> targetType) {
        givenClass = Objects.requireNonNull(targetType);
        randomField = choseField(targetType);
    }

    private Field choseField(Class<T> targetType) {
        return Arrays.stream(targetType.getDeclaredFields())
                .filter(field -> field.getType().isPrimitive() || Comparable.class.isAssignableFrom(field.getType()))
                .findAny().orElseThrow(() -> new IllegalArgumentException("There is no field is available to compare"));
    }

    /**
     * Compares two objects of the class T by the value of the field that was randomly chosen. It allows null values
     * for the fields, and it treats null value greater than a non-null value.
     *
     * @param o1
     * @param o2
     * @return positive int in case of first parameter {@param o1} is greater than second one {@param o2},
     * zero if objects are equals,
     * negative int in case of first parameter {@param o1} is less than second one {@param o2}.
     */
    @Override
    public int compare(T o1, T o2) {
        Objects.requireNonNull(o1);
        Objects.requireNonNull(o2);
        return randomFieldCompare(o1, o2);
    }


    private <U extends Comparable<? super U>> int randomFieldCompare(T o1, T o2) {
        randomField.setAccessible(true);
        U value1 = null;
        U value2 = null;
        try {
            value1 = (U) randomField.get(o1);
            value2 = (U) randomField.get(o2);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Comparator<U> comparator = Comparator.nullsLast(Comparator.naturalOrder());
        return comparator.compare(value1, value2);
    }

    /**
     * Returns the name of the randomly-chosen comparing field.
     */
    public String getComparingFieldName() {
        return randomField.getName();
    }

    /**
     * Returns a statement "Random field comparator of class '%s' is comparing '%s'" where the first param is the name
     * of the type T, and the second parameter is the comparing field name.
     *
     * @return a predefined statement
     */
    @Override
    public String toString() {
        return String.format("Random field comparator of class '%s' is comparing '%s'", this.givenClass.getSimpleName(), this.getComparingFieldName());
    }
}
