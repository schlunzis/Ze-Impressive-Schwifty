package org.schlunzis.zis.commons.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Interface for a 2D list. Elements are stored in rows and columns. The number of columns can vary between rows.
 * In general, you can imagine the list like a 2D array.
 * <pre> {@code
 *     new String[][] {
 *          {"a", "b", "c"},
 *          {"d", "e"},
 *          {"f", "g", "h", "i"}
 *     }
 * }</pre>
 * <p>
 * If you have a 2D list with the same elements, you can query the elements by row and column.
 * <pre> {@code
 *    List2D<String> list = ... // create a 2D list
 *    list.get(0, 0); // returns "a"
 *    list.get(0, 1); // returns "b"
 *    list.get(1, 0); // returns "d"
 *    list.get(2, 3); // returns "i"
 *
 *    list.get(0); // returns a List<String> containing ["a", "b", "c"]
 *  }</pre>
 *
 * @param <E> the type of elements in this list
 * @author Til7701
 * @since 0.0.1
 */
public interface List2D<E> extends Collection<E> {

    /**
     * Returns an iterator over the rows of this list. The iterator will return the first row, then the second row and
     * so on. The returned list is a view on the original list, so changes to the original list will be reflected in the
     * returned list. If the original list is modified during iteration, the behavior of the iterator is undefined.
     * The remove operation is supported by the iterator and will remove the current row from the original list.
     *
     * @return an iterator over the rows of this list
     */
    Iterator<List<E>> rowIterator();

    /**
     * Returns an iterator over the columns of this list. The iterator will return the first column of each row, then
     * the second column of each row and so on. If a row has fewer columns than the current column index, the iterator
     * will replace the element with {@code null}. The returned list is a view on the original list, so changes to the
     * original list will be reflected in the returned list. If the original list is modified during iteration, the
     * behavior of the iterator is undefined. The remove operation is supported by the iterator and will remove the
     * current column from the original list.
     *
     * @return an iterator over the columns of this list
     */
    Iterator<List<E>> columnIterator();

    /**
     * Returns the row at the specified index. The returned list is a view on the original list, so changes to the
     * original list will be reflected in the returned list. If the original list is modified after the call to this
     * method, the behavior of the returned list is undefined. The remove operation is supported by the returned list
     * and will remove the row from the original list.
     *
     * @param row the index of the row to return
     * @return the row at the specified index
     * @throws IndexOutOfBoundsException if the row is out of bounds
     */
    List<E> get(int row);

    /**
     * Returns the element at the specified row and column.
     *
     * @param row    the index of the row
     * @param column the index of the column
     * @return the element at the specified row and column
     * @throws IndexOutOfBoundsException if the row or column are out of bounds
     */
    E get(int row, int column);

    /**
     * Replaces the element at the specified row and column with the specified element. The method returns the element
     * previously at the specified position.
     *
     * @param row     the index of the row
     * @param column  the index of the column
     * @param element the new element to set
     * @return the element previously at the specified position
     * @throws IndexOutOfBoundsException if the row or column are out of bounds
     */
    E set(int row, int column, E element);

    /**
     * Adds the specified element to the specified row.
     *
     * @param row     the index of the row
     * @param element the element to add
     * @return {@code true} (as specified by {@link Collection#add})
     * @throws IndexOutOfBoundsException if the row is out of bounds
     */
    boolean add(int row, E element);

    /**
     * Adds a new row to this list. The new row will be empty.
     */
    void addRow();

    /**
     * Adds the specified list as a new row to this list. The method will throw a {@link NullPointerException} if the
     * specified list is {@code null}.
     *
     * @param newRow the list to add as a new row
     * @throws NullPointerException if the specified list is {@code null}
     */
    void addRow(List<E> newRow);

    /**
     * Removes the element at the specified row and column. The method returns the element previously at the specified position.
     * If the removed element was not at the end of a row, the elements in the row will be shifted to the left to fill
     * the gap.
     *
     * @param row    the index of the row
     * @param column the index of the column
     * @return the element previously at the specified position
     * @throws IndexOutOfBoundsException if the row or column are out of bounds
     */
    E remove(int row, int column);

    /**
     * Returns the number of columns in the specified row.
     *
     * @param row the index of the row
     * @return the number of columns in the specified row
     * @throws IndexOutOfBoundsException if the row is out of bounds
     */
    int size(int row);

    /**
     * Returns the shortest row in this list. The shortest row is the row with the fewest elements. If multiple rows
     * have the same number of elements, the first row will be returned. The returned list is a view on the original
     * list, so changes to the original list will be reflected in the returned list. If the original list is modified
     * after the call to this method, the behavior of the returned list is undefined. The remove operation is supported
     * by the returned list and will remove the element from the original list.
     *
     * @return the shortest row in this list
     */
    List<E> shortestRow();

    /**
     * Returns the longest row in this list. The longest row is the row with the most elements. If multiple rows have
     * the same number of elements, the first row will be returned. The returned list is a view on the original list, so
     * changes to the original list will be reflected in the returned list. If the original list is modified after the
     * call to this method, the behavior of the returned list is undefined. The remove operation is supported by the
     * returned list and will remove the element from the original list.
     *
     * @return the longest row in this list
     */
    List<E> longestRow();

    /**
     * Adds the specified element to the shortest row in this list. If multiple rows have the same number of elements,
     * the element will be added to the first row. The method returns {@code true} if the element was added to the row.
     *
     * @param element the element to add
     * @return {@code true} if the element was added to the row
     */
    boolean addToShortestRow(E element);

    /**
     * Adds the specified element to the longest row in this list. If multiple rows have the same number of elements,
     * the element will be added to the first row. The method returns {@code true} if the element was added to the row.
     *
     * @param element the element to add
     * @return {@code true} if the element was added to the row
     */
    boolean addToLongestRow(E element);

    /**
     * Returns a copy of this list. The copy will contain the same elements as this list, but changes to the copy will
     * not affect this list and vice versa.
     *
     * @return a copy of this list
     */
    List2D<E> copy();

}
