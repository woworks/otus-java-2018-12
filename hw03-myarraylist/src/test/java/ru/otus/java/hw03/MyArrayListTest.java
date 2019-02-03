package ru.otus.java.hw03;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class MyArrayListTest {

    @Test
    public void size() {
        List<Sample> list = new MyArrayList<>();
        list.add(new Sample());
        list.add(new Sample());
        assertEquals(2, list.size());

    }

    @Test
    public void isEmpty() {

        List<Sample> list = new MyArrayList<>();
        list.add(new Sample());
        list.add(new Sample());
        assertFalse(list.isEmpty());
        assertTrue(new MyArrayList<Sample>().isEmpty());

    }

    @Test
    public void contains() {
        List<Sample> list = new MyArrayList<>();
        Sample sample1 = new Sample("One", 1L, new Date());
        Sample sample2 = new Sample("Two", 2L, new Date());
        Sample sample3 = new Sample("Three", 3L, new Date());
        Sample sample4 = new Sample("Four", 4L, new Date());
        list.add(sample1);
        list.add(sample2);
        list.add(sample3);

        assertTrue(list.contains(sample2));
        assertFalse(list.contains(sample4));
    }

    @Test
    public void iterator() {
        List<Sample> list = new MyArrayList<>();
        Sample sample1 = new Sample("One", 1L, new Date());
        Sample sample2 = new Sample("Two", 2L, new Date());
        Sample sample3 = new Sample("Three", 3L, new Date());
        Sample sample4 = new Sample("Four", 4L, new Date());
        list.add(sample1);
        list.add(sample2);
        list.add(sample3);
        list.add(sample4);

        Iterator iterator = list.iterator();

        while (iterator.hasNext()){
            assertNotNull(iterator.next());
        }
    }

    @Test
    public void toArray() {
        Sample[] sampleArray = new Sample[3];
        Sample sample1 = new Sample("One", 1L, new Date());
        Sample sample2 = new Sample("Two", 2L, new Date());
        Sample sample3 = new Sample("Three", 3L, new Date());
        sampleArray[0] = sample1;
        sampleArray[1] = sample2;
        sampleArray[2] = sample3;

        List<Sample> list = new MyArrayList<>();
        list.add(sample1);
        list.add(sample2);
        list.add(sample3);

        assertArrayEquals(sampleArray, list.toArray());
    }

    @Test
    public void toArrayWithParam() {
        Sample[] sampleArray = new Sample[3];
        Sample[] newArray = new Sample[3];
        Sample sample1 = new Sample("One", 1L, new Date());
        Sample sample2 = new Sample("Two", 2L, new Date());
        Sample sample3 = new Sample("Three", 3L, new Date());
        sampleArray[0] = sample1;
        sampleArray[1] = sample2;
        sampleArray[2] = sample3;

        List<Sample> list = new MyArrayList<>();
        list.add(sample1);
        list.add(sample2);
        list.add(sample3);

        list.toArray(newArray);

        assertArrayEquals(sampleArray, newArray);
    }

    @Test
    public void remove() {
        Sample sample1 = new Sample("One", 1L, new Date());
        Sample sample2 = new Sample("Two", 2L, new Date());
        Sample sample3 = new Sample("Three", 3L, new Date());

        List<Sample> list = new MyArrayList<>();
        list.add(sample1);
        list.add(sample2);
        list.add(sample3);

        list.remove(sample2);

        assertEquals(2, list.size());
        assertEquals(sample1, list.get(0));
        assertEquals(sample3, list.get(1));
    }

    @Test
    public void containsAll() {
        Sample sample1 = new Sample("One", 1L, new Date());
        Sample sample2 = new Sample("Two", 2L, new Date());
        Sample sample3 = new Sample("Three", 3L, new Date());

        List<Sample> list = new MyArrayList<>();
        list.add(sample1);
        list.add(sample2);
        list.add(sample3);

        List collection = new ArrayList();
        collection.add(sample1);
        collection.add(sample2);

        assertTrue(list.containsAll(collection));
    }

    @Test
    public void addAll() {
        Sample sample1 = new Sample("One", 1L, new Date());
        Sample sample2 = new Sample("Two", 2L, new Date());
        Sample sample3 = new Sample("Three", 3L, new Date());

        List<Sample> list = new MyArrayList<>();
        list.add(sample1);
        list.add(sample2);
        list.add(sample3);

        List collection = new ArrayList();
        collection.add(sample1);
        collection.add(sample2);

        list.addAll(collection);

        assertEquals(5, list.size());
        assertTrue(list.containsAll(list));
        assertEquals(sample1, list.get(0));
        assertEquals(sample2, list.get(1));
        assertEquals(sample3, list.get(2));
        assertEquals(sample1, list.get(3));
        assertEquals(sample2, list.get(4));
    }

    @Test
    public void addAllWithIndex() {
        Sample sample1 = new Sample("One", 1L, new Date());
        Sample sample2 = new Sample("Two", 2L, new Date());
        Sample sample3 = new Sample("Three", 3L, new Date());
        Sample sample4 = new Sample("Four", 4L, new Date());
        Sample sample5 = new Sample("Five", 5L, new Date());

        List<Sample> list = new MyArrayList<>();
        list.add(sample1);
        list.add(sample2);
        list.add(sample3);

        List collection = new ArrayList();
        collection.add(sample4);
        collection.add(sample5);

        list.addAll(1, collection);

        assertEquals(5, list.size());
        assertEquals(sample1, list.get(0));
        assertEquals(sample4, list.get(1));
        assertEquals(sample5, list.get(2));
        assertEquals(sample2, list.get(3));
        assertEquals(sample3, list.get(4));
    }

    @Test
    public void removeAll() {
    }

    @Test
    public void retainAll() {
    }

    @Test
    public void clear() {
    }

    @Test
    public void addWithParam() {
    }

    @Test
    public void remove1() {
    }

    @Test
    public void indexOf() {
    }

    @Test
    public void lastIndexOf() {
    }

    @Test
    public void listIterator() {
    }

    @Test
    public void listIterator1() {
    }

    @Test
    public void subList() {
    }
}
