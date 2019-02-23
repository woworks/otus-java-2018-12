package ru.otus.java.hw05.test;

import ru.otus.java.hw05.annotations.After;
import ru.otus.java.hw05.annotations.Before;
import ru.otus.java.hw05.annotations.Test;
import ru.otus.java.hw05.tester.Assert;

public class CalculationTest {


    @Before
    public void setUp() {
        System.out.println("@Before");
    }

    @Test
    public void testFindMax() {
        System.out.println("   @Test:: case find max");
        Assert.equals(4, Calculation.findMax(new int[]{1, 3, 4, 2}));
        Assert.equals(-2, Calculation.findMax(new int[]{-12, -3, -4, -2}));
    }

    @Test
    public void testCube() {
        System.out.println("   @Test:: case cube");
        Assert.equals(27, Calculation.cube(3));
    }

    @Test
    public void testReverseWord() {
        System.out.println("   @Test:: case reverse word");
        Assert.equals("ym eman si nahk", Calculation.reverseWord("my name is khan"));
    }

    @After
    public void tearDown() {
        System.out.println("After");
    }

}