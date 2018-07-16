package com.example.lesson16;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;

public class MainActivityTest {

    private MainActivity mainActivity = new MainActivity();

    @Test
    public void sumTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class mainActivityClass = mainActivity.getClass();
        Method method = mainActivityClass.getDeclaredMethod("sum", double.class, double.class);
        method.setAccessible(true);
        double sum = (double) method.invoke(mainActivity,2, 5);
        method.setAccessible(false);
        assertEquals(7, sum, 0.0);
    }

    @Test
    public void diffTest() {
        double diff = mainActivity.diff(5, 2);
        assertEquals(3, diff, 0.1);
    }

    @Test
    public void multTest() {
        double mult = mainActivity.mult(5, 2);
        assertEquals(10, mult, 0.1);
    }

    @Test
    public void divTest() {
        double div = mainActivity.div(5, 2);
        assertEquals(2.5, div, 0.1);

        div = mainActivity.div(5, 0);
        assertEquals(Double.POSITIVE_INFINITY, div, 0.1);
    }
}