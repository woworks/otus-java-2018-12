package ru.otus.java.hw03;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MyArrayList<T> implements List<T> {

    private ArrayContainer<T> container = new ArrayContainer<>();

    private MyArrayIterator iterator = new MyArrayIterator();

    @Override
    public int size() {
        return container.get().length;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < this.container.get().length; i++) {
            if (((T)o).equals(((T) this.container.get()[i]))){
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new MyArrayIterator();
    }

    @Override
    public Object[] toArray() {
        Object[] newArray = new Object[container.get().length];
        System.arraycopy(container.get(),0, newArray, 0, container.get().length);
        return newArray;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        System.arraycopy(container.get(),0, a, 0, container.get().length);
        return a;
    }

    @Override
    public boolean add(T newObject) {
        int currentArraySize = this.container.get().length;
        T[] newArray = (T[])new Object[currentArraySize + 1];
        System.arraycopy(this.container.get(),0, newArray, 0, currentArraySize);
        newArray[currentArraySize] = newObject;
        this.container = new ArrayContainer<T>(newArray);

        return true;
    }

    @Override
    public boolean remove(Object o) {
        Object[] newArray = new Object[container.get().length - 1];
        boolean found = false;
        int j = 0;
        for (int i = 0; i < container.get().length; i++) {
            if (!((T)o).equals(((T) container.get()[i]))){
                newArray[j++] = container.get()[i];
            } else {
                found = true;
            }
        }

        if (found) {
            container.set((T[])newArray);
        }

        return found;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
         for (Object item: c) {
            if (!this.contains(item)) {
                return false;
            }
         }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        Object[] newArray = new Object[container.get().length + c.size()];
        System.arraycopy(container.get(), 0, newArray, 0, container.get().length);
        System.arraycopy(c.toArray(), 0, newArray, container.get().length, c.size());
        container.set((T[]) newArray);

        return c.size() > 0;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        Object[] newArray = new Object[container.get().length + c.size()];

        T[] originalArray = (T[])new Object[container.get().length];

        System.arraycopy(container.get(), 0, originalArray, 0, container.get().length);
        System.arraycopy(container.get(), 0, newArray, 0, container.get().length);

        System.arraycopy(c.toArray(), 0, newArray, index, c.size());

        System.arraycopy(originalArray, index, newArray, index + c.size() - 1, originalArray.length - index);

        container.set((T[]) newArray);

        return c.size() > 0;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new NotImplementedException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new NotImplementedException();
    }

    @Override
    public void clear() {
        this.container.set((T[]) new Object[0]);
    }

    @Override
    public T get(int index) {
        return this.container.get()[index];
    }

    @Override
    public T set(int index, T element) {
        T elementToReplace = container.get()[index];
        container.get()[index] = element;
        return elementToReplace;
    }

    @Override
    public void add(int index, T element) {
        throw new NotImplementedException();
    }

    @Override
    public T remove(int index) {
        throw new NotImplementedException();
    }

    @Override
    public int indexOf(Object o) {
        throw new NotImplementedException();
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new NotImplementedException();
    }

    @Override
    public ListIterator<T> listIterator() {
        throw new NotImplementedException();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new NotImplementedException();
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new NotImplementedException();
    }

    public class MyArrayIterator implements Iterator {
        private int position = 0;

        public boolean hasNext() {
            if (position <  container.get().length)
                return true;
            else
                return false;
        }

        public T next() {
            if (this.hasNext())
                return container.get()[position++];
            else
                return null;
        }

        @Override
        public void remove() {
            MyArrayList.this.remove(position);

        }
    }
}

final class ArrayContainer<T> {
    private T[] underlyingArray;

    public ArrayContainer(T[] underlyingArray) {
        this.underlyingArray = underlyingArray;
    }

    public ArrayContainer() {
        this.underlyingArray = (T[]) new Object[0];
    }

    public T[] get() {
        return underlyingArray;
    }

    public void set(T[] underlyingArray) {
        this.underlyingArray = underlyingArray;
    }
}
