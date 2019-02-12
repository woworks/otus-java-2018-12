package ru.otus.java.hw03;

import org.junit.Test;

import java.util.*;

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
        list.remove(null);

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

        List<Sample> collection = new ArrayList<>();
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

        List<Sample> collection = new ArrayList<>();
        collection.add(sample1);
        collection.add(sample2);

        list.addAll(collection);

        assertEquals(5, list.size());
        assertTrue(list.containsAll(collection));
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

        List<Sample> collection = new ArrayList();
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
        Sample sample1 = new Sample("One", 1L, new Date());
        Sample sample2 = new Sample("Two", 2L, new Date());
        Sample sample3 = new Sample("Three", 3L, new Date());

        List<Sample> list = new MyArrayList<>();
        list.add(sample1);
        list.add(sample2);
        list.add(sample3);


        List<Sample> listToRemove = new MyArrayList<>();
        listToRemove.add(sample2);
        listToRemove.add(sample3);

        list.removeAll(listToRemove);

        assertEquals(1, list.size());
        assertEquals(sample1, list.get(0));
    }

    @Test
    public void retainAll() {
        Sample sample1 = new Sample("One", 1L, new Date());
        Sample sample2 = new Sample("Two", 2L, new Date());
        Sample sample3 = new Sample("Three", 3L, new Date());

        List<Sample> list = new MyArrayList<>();
        list.add(sample1);
        list.add(sample2);
        list.add(sample3);


        List<Sample> listToRetain = new MyArrayList<>();
        listToRetain.add(sample2);
        listToRetain.add(sample3);

        list.retainAll(listToRetain);

        assertEquals(2, list.size());
        assertEquals(sample2, list.get(0));
        assertEquals(sample3, list.get(1));
    }

    @Test
    public void clear() {
        Sample sample1 = new Sample("One", 1L, new Date());
        Sample sample2 = new Sample("Two", 2L, new Date());
        Sample sample3 = new Sample("Three", 3L, new Date());

        List<Sample> list = new MyArrayList<>();
        list.add(sample1);
        list.add(sample2);
        list.add(sample3);

        list.clear();

        assertEquals(0, list.size());
    }

    @Test
    public void CollectionsAddAll() {
        Sample sample1 = new Sample("One", 1L, new Date());
        Sample sample2 = new Sample("Two", 2L, new Date());
        Sample sample3 = new Sample("Three", 3L, new Date());

        List<Sample> list = new MyArrayList<>();
        list.add(sample1);
        list.add(sample2);
        list.add(sample3);


        Sample sample4 = new Sample("Four", 4L, new Date());
        Sample sample5 = new Sample("Five", 5L, new Date());
        Sample sample6 = new Sample("Six", 6L, new Date());

        Sample[] listToAdd = new Sample[3];
        listToAdd[0] = sample4;
        listToAdd[1] = sample5;
        listToAdd[2] = sample6;


        Collections.addAll(list, listToAdd);

        assertEquals(6, list.size());
        assertEquals(sample4, list.get(3));
        assertEquals(sample5, list.get(4));
        assertEquals(sample6, list.get(5));

    }

    @Test
    public void ColllectionsCopy() {
        Sample sample1 = new Sample("One", 1L, new Date());
        Sample sample2 = new Sample("Two", 2L, new Date());
        Sample sample3 = new Sample("Three", 3L, new Date());

        List<Sample> list = new MyArrayList<>();
        list.add(sample1);
        list.add(sample2);
        list.add(sample3);


        Sample sample4 = new Sample("Four", 4L, new Date());
        Sample sample5 = new Sample("Five", 5L, new Date());
        Sample sample6 = new Sample("Six", 6L, new Date());

        List<Sample> listToCopy = new MyArrayList<>();
        listToCopy.add(sample4);
        listToCopy.add(sample5);
        listToCopy.add(sample6);


        Collections.copy(listToCopy, list);

        assertEquals(3, list.size());
        assertEquals(sample1, listToCopy.get(0));
        assertEquals(sample2, listToCopy.get(1));
        assertEquals(sample3, listToCopy.get(2));
    }

    @Test
    public void CollectionsSort() {
        Sample sample1 = new Sample("One", 1L, new Date());
        Sample sample2 = new Sample("Two", 2L, new Date());
        Sample sample3 = new Sample("Three", 3L, new Date());
        Sample sample4 = new Sample("Four", 4L, new Date());
        Sample sample5 = new Sample("Five", 5L, new Date());
        Sample sample6 = new Sample("Six", 6L, new Date());


        List<Sample> list = new MyArrayList<>();
        list.add(sample3);
        list.add(sample4);
        list.add(sample5);
        list.add(sample6);
        list.add(sample1);
        list.add(sample2);

        Collections.sort(list);

        assertEquals(6, list.size());
        assertEquals(sample1, list.get(0));
        assertEquals(sample2, list.get(1));
        assertEquals(sample3, list.get(2));
        assertEquals(sample4, list.get(3));
        assertEquals(sample5, list.get(4));
        assertEquals(sample6, list.get(5));
    }

    @Test
    public void collectionsSortInteger() {
        List<Integer> list = new MyArrayList<>();
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(1);
        list.add(2);

        Collections.sort(list);

        assertEquals(6, list.size());
        assertEquals(1, list.get(0).intValue());
        assertEquals(2, list.get(1).intValue());
        assertEquals(3, list.get(2).intValue());
        assertEquals(4, list.get(3).intValue());
        assertEquals(5, list.get(4).intValue());
        assertEquals(6, list.get(5).intValue());
    }

    @Test
    public void containsNull() {
        List<Sample> list = new MyArrayList<>();
        Sample sample1 = new Sample("One", 1L, new Date());
        list.add(null);
        list.add(sample1);

        assertTrue(list.contains(null));
    }

    @Test
    public void indexOf() {
        List<Integer> list = new MyArrayList<>();
        list.add(3);
        list.add(4);
        list.add(null);
        list.add(6);
        list.add(1);
        list.add(2);
        list.add(4);

        int nullIndex = list.indexOf(null);
        int fourIndex = list.indexOf(4);
        int nineIndex = list.indexOf(9);

        assertEquals(2, nullIndex);
        assertEquals(1, fourIndex);
        assertEquals(-1, nineIndex);
    }

    @Test
    public void lastIndexOf() {
        List<Integer> list = new MyArrayList<>();
        list.add(3);
        list.add(4);
        list.add(null);
        list.add(6);
        list.add(1);
        list.add(2);
        list.add(4);

        int nullIndex = list.lastIndexOf(null);
        int fourIndex = list.lastIndexOf(4);
        int nineIndex = list.lastIndexOf(9);

        assertEquals(2, nullIndex);
        assertEquals(6, fourIndex);
        assertEquals(-1, nineIndex);
    }


    @Test
    public void set() {
        List<Integer> list = new MyArrayList<>();
        list.add(3);
        list.set(0, 4);
        assertEquals(4 ,list.get(0).intValue());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void setOutOfSize() {
        List<Integer> list = new MyArrayList<>();
        list.add(3);
        list.set(1, 4);
    }

    @Test
    public void removeByIndex() {
        Sample sample1 = new Sample("One", 1L, new Date());
        Sample sample2 = new Sample("Two", 2L, new Date());
        Sample sample3 = new Sample("Three", 3L, new Date());

        List<Sample> list = new MyArrayList<>();
        list.add(sample1);
        list.add(sample2);
        list.add(sample3);

        list.remove(1);

        assertEquals(2, list.size());
        assertEquals(sample1, list.get(0));
        assertEquals(sample3, list.get(1));
    }
}
