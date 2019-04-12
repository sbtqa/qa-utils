package ru.sbtqa.tag.qautils.reflect;

import java.util.ArrayList;
import java.util.List;

/**
 * Reflection helper to deal with classes
 *
 */
public class ClassUtilsExt extends org.apache.commons.lang3.ClassUtils {

    /**
     * It is like {@link org.apache.commons.lang3.ClassUtils}
     * getAllSuperClasses but extended by Really all possible super classes.
     *
     * @param clazz the class to reflect
     * @return {@link List} a list of super classes
     */
    public static List<Class> getSuperclassesWithInheritance(Class clazz) {
        List<Class> classes = new ArrayList<>();

        Class supp = clazz.getSuperclass();

        // interfaces has null super class
        while (supp != null && supp != java.lang.Object.class) {
            classes.add(supp);
            supp = supp.getSuperclass();
        }
        return classes;
    }
}
