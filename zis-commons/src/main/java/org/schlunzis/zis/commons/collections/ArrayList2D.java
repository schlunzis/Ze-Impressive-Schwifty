package org.schlunzis.zis.commons.collections;

import java.util.*;

/**
 * This is an implementation of the {@link List2D} interface. It uses {@link ArrayList}s to store the elements. This
 * implementation is not thread safe.
 *
 * @param <E>
 * @author Til7701
 * @since 0.0.1
 */
public class ArrayList2D<E> extends AbstractList2D<E> implements RandomAccess {

    private static final int DEFAULT_CAPACITY = 10;

    private final List<List<E>> elementData;

    /**
     * Creates an empty list with a capacity of 10.
     */
    public ArrayList2D() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Creates an empty list with the given capacity.
     *
     * @param initialRowsCapacity the capacity for row
     * @throws IllegalArgumentException if the given capacity is negative
     */
    public ArrayList2D(int initialRowsCapacity) {
        this.elementData = new ArrayList<>(initialRowsCapacity);
    }

    /**
     * Creates a new list with the elements of the given list. Changes to this list do not affect the other list and
     * vice versa.
     *
     * @param other the list to take the other elements from
     * @throws NullPointerException if the other list is null
     */
    public ArrayList2D(List2D<E> other) {
        Objects.requireNonNull(other, "Other list must not be null.");
        this.elementData = new ArrayList<>();
        for (Iterator<List<E>> it = other.rowIterator(); it.hasNext(); ) {
            List<E> otherRow = it.next();
            List<E> newRow = new ArrayList<>(otherRow.size());
            newRow.addAll(otherRow);
            elementData.add(newRow);
        }
    }

    /**
     * Creates a new list with the given elements. References to the array are lost.
     *
     * @param array the array to take the elements from
     */
    public ArrayList2D(E[][] array) {
        Objects.requireNonNull(array, "Array must not be null.");
        this.elementData = new ArrayList<>(array.length);
        for (E[] row : array) {
            List<E> list = new ArrayList<>(row.length);
            Collections.addAll(list, row);
            elementData.add(list);
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new ElementIterator();
    }

    @Override
    public Iterator<List<E>> rowIterator() {
        return new RowIterator();
    }

    @Override
    public Iterator<List<E>> columnIterator() {
        return new ColumnIterator();
    }

    @Override
    public int size() {
        int size = 0;
        for (List<E> list : elementData) {
            size += list.size();
        }
        return size;
    }

    @Override
    public List<E> get(int row) {
        return elementData.get(row);
    }

    @Override
    public E get(int row, int column) {
        return elementData.get(row).get(column);
    }

    @Override
    public E set(int row, int column, E element) {
        return elementData.get(row).set(column, element);
    }

    @Override
    public boolean add(int row, E element) {
        if (row >= elementData.size()) {
            for (int i = elementData.size(); i <= row; i++)
                elementData.add(new ArrayList<>());
        }
        return elementData.get(row).add(element);
    }

    @Override
    public void addRow() {
        elementData.add(new ArrayList<>());
    }

    @Override
    public void addRow(List<E> newRow) {
        Objects.requireNonNull(newRow, "New row must not be null.");
        elementData.add(newRow);
    }

    @Override
    public E remove(int row, int column) {
        return elementData.get(row).remove(column);
    }

    @Override
    public int size(int row) {
        return elementData.get(row).size();
    }

    @Override
    public List<E> shortestRow() {
        if (elementData.isEmpty()) return new ArrayList<>();
        List<E> shortestList = elementData.getFirst();
        for (int i = 1; i < elementData.size(); i++) {
            if (elementData.get(i).size() < shortestList.size())
                shortestList = elementData.get(i);
        }
        return shortestList;
    }

    @Override
    public List<E> longestRow() {
        if (elementData.isEmpty()) return new ArrayList<>();
        List<E> longestList = elementData.getFirst();
        for (int i = 1; i < elementData.size(); i++) {
            if (elementData.get(i).size() > longestList.size())
                longestList = elementData.get(i);
        }
        return longestList;
    }

    @Override
    public boolean addToShortestRow(E element) {
        if (elementData.isEmpty())
            elementData.add(new ArrayList<>());
        return shortestRow().add(element);
    }

    @Override
    public boolean addToLongestRow(E element) {
        if (elementData.isEmpty())
            elementData.add(new ArrayList<>());
        return longestRow().add(element);
    }

    @Override
    public List2D<E> copy() {
        return new ArrayList2D<>(this);
    }

    private class ElementIterator implements Iterator<E> {
        private int row = 0;
        private int column = 0;

        @Override
        public boolean hasNext() {
            return row < elementData.size() && column < elementData.get(row).size();
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E result = elementData.get(row).get(column);
            column++;
            if (column >= elementData.get(row).size()) {
                row++;
                column = 0;
            }
            return result;
        }
    }

    private class RowIterator implements Iterator<List<E>> {
        private int row = 0;

        @Override
        public boolean hasNext() {
            return row < elementData.size();
        }

        @Override
        public List<E> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            List<E> result = elementData.get(row);
            row++;
            return result;
        }
    }

    private class ColumnIterator implements Iterator<List<E>> {
        private final ColumnView columnView;

        public ColumnIterator() {
            columnView = new ColumnView(-1);
        }

        @Override
        public boolean hasNext() {
            return columnView.getColumnIndex() < longestRow().size();
        }

        @Override
        public List<E> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            columnView.columnIndex = columnView.columnIndex + 1;
            return columnView;
        }
    }

    private class ColumnView extends AbstractList<E> implements List2DColumnView<E> {
        private int columnIndex;

        private ColumnView(int columnIndex) {
            this.columnIndex = columnIndex;
        }

        @Override
        public E get(int rowIndex) {
            return elementData.get(rowIndex).get(columnIndex);
        }

        @Override
        public int size() {
            return elementData.size();
        }

        @Override
        public int getColumnIndex() {
            return columnIndex;
        }

        @Override
        public final boolean equals(Object o) {
            return super.equals(o);
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }
    }

}
