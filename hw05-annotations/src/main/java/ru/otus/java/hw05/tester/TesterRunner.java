package ru.otus.java.hw05.tester;

import ru.otus.java.hw05.reflection.ReflectionHelper;
import ru.otus.java.hw05.test.PersonTest;
import ru.otus.java.hw05.test.TestFrameworkException;

import java.lang.reflect.Method;
import java.util.List;

public class TesterRunner {

    public static void runTest(Class<PersonTest> type) {

        ReflectionHelper.objectInfo(type);
        PersonTest personTestSourceObject = new PersonTest();

        try {
            List<Method> testMethods = ReflectionHelper.getTestMethods(personTestSourceObject);
            Method beforeMethod = ReflectionHelper.getBeforeMethod(personTestSourceObject);
            Method afterMethod = ReflectionHelper.getAfterMethod(personTestSourceObject);

            for (Method method : testMethods) {
                PersonTest personTest = new PersonTest();
                ReflectionHelper.callMethod(personTest, beforeMethod.getName());
                ReflectionHelper.callMethod(personTest, method.getName());
                ReflectionHelper.callMethod(personTest, afterMethod.getName());
            }

        } catch (TestFrameworkException e) {
            System.out.println("Could not run the tests");
            e.printStackTrace();
        }


    }
}
