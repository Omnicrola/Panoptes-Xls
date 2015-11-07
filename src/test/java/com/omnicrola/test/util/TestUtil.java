package com.omnicrola.test.util;

import com.omnicrola.panoptes.data.Required;
import junit.framework.AssertionFailedError;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * Created by Eric on 9/4/2015.
 */
public class TestUtil {
    private static final Random random = new Random();
    private static final String characters = "abcdefghijklmopqrstuvwxyz" +
            "ABCDEFGHIJKLMOPQRSTUVWXYZ1234567890\"'-[]!@#$%^&*()<>,.";


    public static <T> T assertIsOfType(Class<T> targetClass, Object sourceObject) {
        if (sourceObject == null) {
            throw new AssertionFailedError("Object was null, expected a " + targetClass.getSimpleName());
        }
        if (targetClass.equals(sourceObject.getClass())) {
            return (T) sourceObject;
        } else {
            throw new AssertionFailedError("Object '" +
                    sourceObject.getClass().getSimpleName() +
                    "' was not of type : " +
                    targetClass.getSimpleName());
        }
    }

    public static void assertClassIsChildOf(Class<?> targetClass, Class<?> expectedParentClass) {
        Class<?> superclass = targetClass.getSuperclass();
        if (!superclass.equals(expectedParentClass)) {
            throw new AssertionFailedError("Class " + targetClass.getName() + " was not a child class of " + expectedParentClass.getName() + " \n was actually : " + superclass.getName());
        }
    }

    public static void assertImplementsInterface(Class<?> targetClass, Class<?> expectedInterface) {
        Class<?>[] interfaces = targetClass.getInterfaces();
        if (interfaces.length != 1) {
            throw new AssertionFailedError("Class '" + targetClass.getName() + "' does not implement exactly one interface of type : " + expectedInterface.getName());
        }
        if (!expectedInterface.equals(interfaces[0])) {
            throw new AssertionFailedError("Class " + targetClass.getName() + " does not implement interface : " + expectedInterface.getName());
        }
    }

    public static <T> T assertAnnotationPresent(Method method, Class<? extends Annotation> annotationClass) {
        Annotation annotation = method.getAnnotation(annotationClass);
        if (annotation == null) {
            throw new AssertionFailedError("Could not find annotation of type " + annotationClass.getSimpleName() + " on method " + method.getName());
        }
        return (T) annotation;
    }

    public static <T> T assertFieldHasAnnotation(Class<? extends Annotation> annotationClass, Field targetField) {
        Annotation annotation = targetField.getAnnotation(annotationClass);
        if (annotation == null) {
            throw new AssertionFailedError("Field " + targetField.getName() + " does not have annotation " +
                    "" + annotationClass.getName());
        }
        return (T) annotation;
    }

    public static <T> T assertClassHasAnnotation(Class<? extends Annotation> annotationClass, Class<?> targetClass) {
        Annotation annotation = targetClass.getAnnotation(annotationClass);
        if (annotation == null) {
            throw new AssertionFailedError("Class " + targetClass.getName() + " does not posses the annotation " +
                    "" + annotationClass.getName());
        }
        return (T) annotation;
    }

    public static Field getFieldByName(String fieldName, Class<?> targetClass) {
        try {
            return targetClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            throw new AssertionFailedError("Class " + targetClass.getName() + " has no field named " + fieldName);
        }
    }

    public static int randomInt() {
        return random.nextInt();
    }

    public static int randomInt(int maximum) {
        return random.nextInt(maximum);
    }

    public static String randomString(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(characters.charAt(random.nextInt(length)));
        }
        return stringBuilder.toString();
    }

    public static void assertClassExtends(Class<?> targetClass, Class<?> expectedSuperType) {
        Class<?> superclass = targetClass.getSuperclass();
        assertEquals(expectedSuperType, superclass);
    }


    public static void assertEquality(Object original, Object equal, Object... notEqual) {
        if (!original.equals(equal)) {
            throw new AssertionError("Objects where not equal " + original + " != " + equal);
        }
        if (original.hashCode() != equal.hashCode()) {
            throw new AssertionError("Equal objects did not have same hash :" +
                    original + "(" + original.hashCode() + ")" +
                    " " + equal + "(" + equal.hashCode() + ")");
        }

        for (Object notEqualObject : notEqual) {
            checkNotEqual(original, notEqualObject);
            checkNotEqualHash(original, notEqualObject);
        }
    }


    private static void checkNotEqual(Object original, Object notEqualObject) {
        if (original.equals(notEqualObject)) {
            throw new AssertionError("Unequal objects where actually equal: " + original + " == " + notEqualObject);
        }
    }

    private static void checkNotEqualHash(Object original, Object notEqualObject) {
        if (original.hashCode() == notEqualObject.hashCode()) {
            throw new AssertionError("Unequal objects have same hash :" +
                    original + "(" + original.hashCode() + ")" +
                    " " + notEqualObject + "(" + notEqualObject.hashCode() + ")");
        }
    }

    public static void assertHasInterface(Class<?> interfaceClass, Class<?> objectClass) {
        Class<?>[] interfaces = objectClass.getInterfaces();
        for (Class<?> singleInterface : interfaces) {
            if (singleInterface.equals(interfaceClass)) {
                return;
            }
        }
        throw new AssertionError("Class " + objectClass.getName() + " did not implement interface: " +
                "" + interfaceClass.getName());
    }

    public static void assertRequiredFieldIsPresent(String fieldName, Class<?> targetClass) throws Exception {
        Field field = assertFieldIsPresent(fieldName, targetClass);
        Required annotation = field.getAnnotation(Required.class);
        if (annotation == null) {
            throw new AssertionFailedError("The '@Required' annotation was not present on field '" + fieldName + "'");
        }
    }

    public static Field assertFieldIsPresent(String fieldName, Class<?> targetClass) throws Exception {
        try {
            Field field = targetClass.getField(fieldName);
            return field;
        } catch (NoSuchFieldException e) {
            throw new AssertionFailedError("Field " + fieldName + " was not present");
        }
    }
}
