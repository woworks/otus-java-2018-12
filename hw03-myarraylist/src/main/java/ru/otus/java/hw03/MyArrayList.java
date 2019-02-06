package ru.otus.java.hw03;

import java.util.*;

public class MyArrayList<T> implements List<T> {

     private static final int MARGIN = 5;
    private T[] underlyingArray = (T[]) new Object[MARGIN];
    private int arraySize = 0;

    private MyArrayIterator iterator = new MyArrayIterator();

    @Override
    public int size() {
        return this.arraySize;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < this.underlyingArray.length; i++) {
            if (o!= null && o.equals(((T) this.underlyingArray[i])) ||
                    (o == null && this.underlyingArray[i] == null)) {
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
        Object[] newArray = new Object[this.arraySize];
        System.arraycopy(underlyingArray, 0, newArray, 0, this.arraySize);
        return newArray;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        System.arraycopy(underlyingArray, 0, a, 0, this.arraySize);
        return a;
    }

    @Override
    public boolean add(T newObject) {
        int currentArrayLength = this.underlyingArray.length;
        if (this.arraySize >= currentArrayLength ) {
            T[] newArray = (T[]) new Object[currentArrayLength + MARGIN];
            System.arraycopy(this.underlyingArray, 0, newArray, 0, currentArrayLength);
            newArray[currentArrayLength] = newObject;
            this.underlyingArray = newArray;
        } else {
            this.underlyingArray[this.arraySize] = newObject;
        }

        this.arraySize++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        boolean found = false;

        for (int i = 0; i < underlyingArray.length - 1; i++) {
            if (o.equals(underlyingArray[i]) || found) {
                if (i == arraySize) {
                    underlyingArray[i] = null;
                } else {
                    underlyingArray[i] = underlyingArray[i + 1];
                    found = true;
                }
            }
        }

        if (found) {
            this.arraySize--;
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
        for (T newItem: c){
            this.add(newItem);
        }

        return c.size() > 0;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        Object[] newArray = new Object[this.arraySize + c.size()];

        T[] originalArray = (T[]) new Object[this.arraySize];

        System.arraycopy(underlyingArray, 0, originalArray, 0, this.arraySize);
        System.arraycopy(underlyingArray, 0, newArray, 0, this.arraySize);

        System.arraycopy(c.toArray(), 0, newArray, index, c.size());

        System.arraycopy(originalArray, index, newArray, index + c.size(), originalArray.length - index);

        this.underlyingArray = (T[]) newArray;
        this.arraySize = this.arraySize + c.size();

        return c.size() > 0;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        int initialSize = this.underlyingArray.length;
        Object[] cArray = c.toArray();
        for (int i = 0; i < c.size(); i++) {
            this.remove(cArray[i]);
        }

        int sizeAfterDelete = this.underlyingArray.length;

        return initialSize != sizeAfterDelete;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        int initialSize = this.arraySize;

        for (T item : this.underlyingArray) {
            if (item != null && !c.contains(item)) {
                this.remove(item);
            }
        }

        int sizeAfterDelete = this.arraySize;

        return initialSize != sizeAfterDelete;
    }

    @Override
    public void clear() {
        this.underlyingArray = (T[]) new Object[0];
        this.arraySize = 0;
    }

    @Override
    public T get(int index) {
        return this.underlyingArray[index];
    }

    @Override
    public T set(int index, T element) {
        T elementToReplace = underlyingArray[index];
        underlyingArray[index] = element;
        return elementToReplace;
    }

    @Override
    public void add(int index, T element) {
        this.addAll(index, Arrays.asList(element));
    }

    @Override
    public T remove(int index) {
        Iterator removeIterator = this.listIterator();
        int i = 0;
        T itemToRemove = null;
        while (removeIterator.hasNext()) {
            if (i == index) {
                itemToRemove = this.underlyingArray[i];
                removeIterator.remove();
            }
        }

        return itemToRemove;
    }

    @Override
    public int indexOf(Object o) {
        int index = 0;
        for (int i = 0; i < this.underlyingArray.length; i++) {
            if (((T) o).equals(this.underlyingArray[i])) {
                index = i;
            }
        }

        return index;
    }

    @Override
    public int lastIndexOf(Object o) {
        int lastIndex = 0;
        for (int i = this.underlyingArray.length - 1; i > 0; i--) {
            if (((T) o).equals(this.underlyingArray[i])) {
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
        T[] newArray = (T[]) new Object[toIndex - fromIndex];

        System.arraycopy(underlyingArray, fromIndex, newArray, 0, toIndex - fromIndex);
        return Arrays.asList(newArray);
    }

    public class MyArrayIterator implements Iterator {
        private int position = 0;

        public boolean hasNext() {
            return (position < underlyingArray.length - 1);
        }

        public T next() {
            if (this.hasNext())
                return underlyingArray[position++];
            else
                throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            MyArrayList.this.remove(position);

        }
    }

    public class MyArrayListIterator implements ListIterator {

        private int position = -1;

        public MyArrayListIterator() {
        }

        public MyArrayListIterator(int position) {
            this.position = position;
        }

        @Override
        public boolean hasNext() {
            return position < underlyingArray.length - 1;
        }

        @Override
        public Object next() {
            if (this.hasNext()) {
                return underlyingArray[++position];
            } else {
                throw new NoSuchElementException();
            }

        }

        @Override
        public boolean hasPrevious() {
            return position > 0;
        }

        @Override
        public Object previous() {
            if (this.hasPrevious())
                return underlyingArray[position--];
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
