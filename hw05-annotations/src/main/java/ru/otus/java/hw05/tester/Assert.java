package ru.otus.java.hw05.tester;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Assert {
    private Assert(){

    }

    public static void isTrue(boolean isTrue) {
        if (!isTrue) {
            System.out.println("\t====> Assert Error! Cannot be false!");
        }
    }

    public static void isNotNull(Object isNotNull) {
        if (isNotNull == null) {
            System.out.println("\t====> Assert Error! Object cannot be null!");
        }
    }

    public static void isNotNegative(int notNegative) {
        if (notNegative < 0) {
            System.out.println("\t====> Assert Error!" + notNegative + " cannot Be Negative!");
        }
    }

    public static void matchPattern(String emailRegex, String testString) {
        Pattern pattern = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(testString);
        if (!matcher.matches()) {
            System.out.println("\t====> Assert Error!" + testString + " doesn't match the pattern!");
        }
    }

    public static void equals(int expected, int actual) {
        if (expected !=  actual) {
            System.out.println("\t====> Assert Error!" + actual + " is not equal to expected " + expected);
        }
    }

    public static void equals(String expected, String actual) {
        if (expected.equals(actual)) {
            System.out.println("\t====> Assert Error!" + actual + " is not equal to expected " + expected);
        }
    }
}
