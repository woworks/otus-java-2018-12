package ru.otus.java.hw05.reflection;

import ru.otus.java.hw05.test.PersonTest;
import ru.otus.java.hw05.test.TestFrameworkException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * Created by tully.
 */
@SuppressWarnings({"SameParameterValue", "BooleanVariableAlwaysNegated"})
public final class ReflectionHelper {
    private ReflectionHelper() {
    }

    public static <T> T instantiate(Class<T> type, Object... args) {
        try {
            if (args.length == 0) {
                final Constructor<T> constructor = type.getDeclaredConstructor();
                return constructor.newInstance();
            } else {
                final Class<?>[] classes = toClasses(args);
                final Constructor<T> constructor = type.getDeclaredConstructor(classes);
                return constructor.newInstance(args);
            }
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Object getFieldValue(Object object, String name) {
        Field field = null;
        boolean isAccessible = true;
        try {
            field = object.getClass().getDeclaredField(name); //getField() for public fields
            isAccessible = field.canAccess(object);
            field.setAccessible(true);
            return field.get(object);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            if (field != null && !isAccessible) {
                field.setAccessible(false);
            }
        }
        return null;
    }

    public static void setFieldValue(Object object, String name, Object value) {
        Field field = null;
        boolean isAccessible = true;
        try {
            field = object.getClass().getDeclaredField(name); //getField() for public fields
            isAccessible = field.canAccess(object);
            field.setAccessible(true);
            field.set(object, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            if (field != null && !isAccessible) {
                field.setAccessible(false);
            }
        }
    }

    public static Object callMethod(Object object, String name, Object... args) {
        Method method = null;
        boolean isAccessible = true;
        try {
            method = object.getClass().getDeclaredMethod(name, toClasses(args));
            isAccessible = method.canAccess(object);
            method.setAccessible(true);
            return method.invoke(object, args);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            if (method != null && !isAccessible) {
                method.setAccessible(false);
            }
        }
        return null;
    }

    public static boolean hasAnnotation(Method method, String annotation) {
        for (Annotation ann : method.getDeclaredAnnotations()) {
            if (ann.annotationType().getSimpleName().compareTo(annotation) == 0) {
                return true;
            }
        }
        return false;
    }

    private static Class<?>[] toClasses(Object[] args) {
        return Arrays.stream(args)
                .map(Object::getClass)
                .toArray(Class<?>[]::new);
//        final Class<?>[] classes = new Class<?>[args.length];
//        for (int i = 0; i < args.length; i++) {
//            classes[i] = args[i].getClass();
//        }
//        return classes;
    }

    public static List<Method> getTestMethods(Object object) {
        List<Method> testMethods = new ArrayList<>();
        for (Method method : object.getClass().getMethods()) {
            if (hasAnnotation(method, "Test")) {
                testMethods.add(method);
            }
        }
        return testMethods;
    }

    public static Method getBeforeMethod(Object object) throws TestFrameworkException {
        return getSingleAnnotation(object, "Before");
    }

    public static Method getAfterMethod(Object object) throws TestFrameworkException {
        return getSingleAnnotation(object, "After");

    }

    public static Method getSingleAnnotation(Object object, String annotationName) throws TestFrameworkException {
        int count = 0;
        Method returnMethod = null;
        for (Method method : object.getClass().getMethods()) {
            if (hasAnnotation(method, annotationName)) {
                returnMethod = method;
                count++;
            }
        }
        if (count > 1) {
            throw new TestFrameworkException("Cannot be more than one '" + annotationName + "' annotation");
        } else if (count == 0) {
            throw new TestFrameworkException("There is no '" + annotationName + "' annotation");
        }
        return returnMethod;
    }

    public static void objectInfo(Class<? extends Object> type) {
        System.out.println();
        System.out.println(String.format("SimpleName     : %60s", type.getSimpleName()));
        System.out.println(String.format("Package Name   : %60s", type.getPackageName()));
        System.out.println(String.format("Canonical Name : %60s", type.getCanonicalName()));
        System.out.println(String.format("Name           : %60s", type.getName()));
        System.out.println(String.format("Type Name      : %60s", type.getTypeName()));
        System.out.println(String.format("Class Loader   : %60s", type.getClassLoader()));
        System.out.println();
    }

}