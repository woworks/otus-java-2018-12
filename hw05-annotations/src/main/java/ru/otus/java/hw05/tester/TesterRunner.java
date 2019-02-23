package ru.otus.java.hw05.tester;

import ru.otus.java.hw05.reflection.ReflectionHelper;
import ru.otus.java.hw05.test.TestFrameworkException;

import java.lang.reflect.Method;
import java.util.List;

public class TesterRunner {

    public static void runTest(Class<? extends Object> type) {

        ReflectionHelper.objectInfo(type);
        Object testSourceObject = ReflectionHelper.instantiate(type);

        try {
            List<Method> testMethods = ReflectionHelper.getTestMethods(testSourceObject);
            Method beforeMethod = ReflectionHelper.getBeforeMethod(testSourceObject);
            Method afterMethod = ReflectionHelper.getAfterMethod(testSourceObject);

            for (Method method : testMethods) {
                Object testObject = ReflectionHelper.instantiate(type);
                ReflectionHelper.callMethod(testObject, beforeMethod.getName());
                ReflectionHelper.callMethod(testObject, method.getName());
                ReflectionHelper.callMethod(testObject, afterMethod.getName());
            }

        } catch (TestFrameworkException e) {
            System.out.println("Could not run the tests");
            e.printStackTrace();
        }


    }
}
