package ru.sbtqa.tag.qautils.reflect;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Reflection helper to deal with class fields
 */
public class FieldUtils extends org.apache.commons.lang3.reflect.FieldUtils {

    /**
     * It is like {@link org.apache.commons.lang3.reflect.FieldUtils}
     * getAllFieldsList but extended by inherited declared fields only. Very
     * fast.
     *
     * @param clazz TODO
     * @return {@link List} list of fields
     */
    public static List<Field> getDeclaredFieldsWithInheritance(Class clazz) {
        List<Field> fields = new ArrayList<>();

        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));

        Class supp = clazz.getSuperclass();

        while (!supp.getName().equals("java.lang.Object")) {
            fields.addAll(Arrays.asList(supp.getDeclaredFields()));
            supp = supp.getSuperclass();

        }
        return fields;
    }

    /**
     * It is like {@link org.apache.commons.lang3.reflect.FieldUtils}
     * getAllFieldsList but extended by inherited declared fields only. Very
     * fast.
     *
     * @param object TODO
     * @return {@link List} list of fields
     */
    public static List<Field> getDeclaredFieldsWithInheritance(Object object) {
        return getDeclaredFieldsWithInheritance(object.getClass());
    }
}
