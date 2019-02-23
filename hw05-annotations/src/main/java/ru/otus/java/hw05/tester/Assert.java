package ru.otus.java.hw05.tester;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Assert {
    private Assert(){

    }

    public static void isTrue(boolean isTrue) {
        if (!isTrue) {
            throw new AssertException("Cannot be false!");
        }
    }

    public static void isNotNull(Object isNotNull) {
        if (isNotNull == null) {
            throw new AssertException("Object cannot be null!");
        }
    }

    public static void isNotNegative(int notNegative) {
        if (notNegative < 0) {
            throw new AssertException(notNegative + " cannot Be Negative!");
        }
    }

    public static void matchPattern(String emailRegex, String testString) {
        Pattern pattern = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(testString);
        if (!matcher.matches()) {
            throw new AssertException(testString + " doesn't match the pattern!");
        }
    }
}
