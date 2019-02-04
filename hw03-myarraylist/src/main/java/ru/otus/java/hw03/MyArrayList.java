package ru.otus.java.hw03;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

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
            if (((T) o).equals(((T) this.container.get()[i]))) {
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
        System.arraycopy(container.get(), 0, newArray, 0, container.get().length);
        return newArray;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        System.arraycopy(container.get(), 0, a, 0, container.get().length);
        return a;
    }

    @Override
    public boolean add(T newObject) {
        int currentArraySize = this.container.get().length;
        T[] newArray = (T[]) new Object[currentArraySize + 1];
        System.arraycopy(this.container.get(), 0, newArray, 0, currentArraySize);
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
            if (!((T) o).equals(((T) container.get()[i]))) {
                newArray[j++] = container.get()[i];
            } else {
                found = true;
            }
        }

        if (found) {
            container.set((T[]) newArray);
        }

        return found;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object item : c) {
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

        T[] originalArray = (T[]) new Object[container.get().length];

        System.arraycopy(container.get(), 0, originalArray, 0, container.get().length);
        System.arraycopy(container.get(), 0, newArray, 0, container.get().length);

        System.arraycopy(c.toArray(), 0, newArray, index, c.size());

        System.arraycopy(originalArray, index, newArray, index + c.size(), originalArray.length - index);

        container.set((T[]) newArray);

        return c.size() > 0;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        int initialSize = this.container.get().length;
        for (Object item : c) {
            this.remove(item);
        }

        int sizeAfterDelete = this.container.get().length;

        return initialSize != sizeAfterDelete;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        int initialSize = this.container.get().length;

        for (T item : this.container.get()) {
            if (!c.contains(item)) {
                this.remove(item);
            }
        }

        int sizeAfterDelete = this.container.get().length;

        return initialSize != sizeAfterDelete;
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
        this.addAll(index, Arrays.asList(element));
    }

    @Override
    public T remove(int index) {
        Iterator iterator = this.listIterator();
        int i = 0;
        T itemToRemove = null;
        while (iterator.hasNext()) {
            if (i == index) {
                itemToRemove = this.container.get()[i];
                iterator.remove();
            }
        }

        return itemToRemove;
    }

    @Override
    public int indexOf(Object o) {
        int index = 0;
        for (int i = 0; i < this.container.get().length; i++) {
            if (((T) o).equals(this.container.get()[i])) {
                index = i;
            }
        }

        return index;
    }

    @Override
    public int lastIndexOf(Object o) {
        int lastIndex = 0;
        for (int i = this.container.get().length - 1; i > 0; i--) {
            if (((T) o).equals(this.container.get()[i])) {
                lastIndex = i;
            }
        }

        return lastIndex;
    }

    @Override
    public ListIterator<T> listIterator() {
        return new MyArrayListIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return new MyArrayListIterator(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        T[] newArray = (T[])new Object[toIndex - fromIndex];

        System.arraycopy(container.get(), fromIndex, newArray, 0, toIndex - fromIndex);
        return Arrays.asList(newArray);
    }

    public class MyArrayIterator implements Iterator {
        private int position = 0;

        public boolean hasNext() {
            if (position < container.get().length - 1)
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

    public class MyArrayListIterator implements ListIterator {

        private int position = 0;

        public MyArrayListIterator() {
            this.position = 0;
        }

        public MyArrayListIterator(int position) {
            this.position = position;
        }

        @Override
        public boolean hasNext() {
            if (position < container.get().length - 1)
                return true;
            else
                return false;
        }

        @Override
        public Object next() {
            if (this.hasNext())
                return container.get()[position++];
            else
                return null;
        }

        @Override
        public boolean hasPrevious() {
            return position > 0;
        }

        @Override
        public Object previous() {
            if (this.hasPrevious())
                return container.get()[position--];
            else
                return null;
        }

        @Override
        public int nextIndex() {
            if (this.hasNext())
                return ++position;
            else
                return -1;
        }

        @Override
        public int previousIndex() {
            if (this.hasPrevious())
                return --position;
            else
                return -1;
        }

        @Override
        public void remove() {
            MyArrayList.this.remove(position);
        }

        @Override
        public void set(Object o) {
            MyArrayList.this.set(position, (T) o);
        }

        @Override
        public void add(Object o) {
            MyArrayList.this.add((T) o);
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
