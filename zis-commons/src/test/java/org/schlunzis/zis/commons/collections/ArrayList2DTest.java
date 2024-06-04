package org.schlunzis.zis.commons.collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class ArrayList2DTest {

    Object[][] defaultArray;
    ArrayList2D<Object> defaultList;

    @BeforeEach
    void setUp() {
        defaultArray = new Object[][]{
                {new Object(), new Object()},
                {new Object(), new Object(), new Object()},
                {new Object()}
        };
        defaultList = new ArrayList2D<>(defaultArray);
    }

    //##################################################
    // ArrayList2D()
    //##################################################

    @Test
    void constructorTest() {
        ArrayList2D<Object> list = new ArrayList2D<>();
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
    }

    //##################################################
    // ArrayList2D(int)
    //##################################################

    @Test
    void constructorCapacityTest() {
        assertThrows(IllegalArgumentException.class, () -> new ArrayList2D<>(-1));
        ArrayList2D<Object> list = new ArrayList2D<>(5);
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
    }

    //##################################################
    // TwoDListOfLists(TowDListOfLists)
    //##################################################

    @Test
    void constructorNullTest() {
        assertThrows(NullPointerException.class, () -> new ArrayList2D<>((ArrayList2D<Object>) null));
    }

    @Test
    void constructorOtherTest() {
        ArrayList2D<Object> list = new ArrayList2D<>(defaultList);
        assertEquals(6, list.size());
        assertEquals(2, list.size(0));
        assertEquals(3, list.size(1));
        assertEquals(1, list.size(2));
        assertEquals(defaultList.get(0, 0), list.get(0, 0));
        assertEquals(defaultList.get(0, 1), list.get(0, 1));
        assertEquals(defaultList.get(1, 0), list.get(1, 0));
        assertEquals(defaultList.get(1, 1), list.get(1, 1));
        assertEquals(defaultList.get(1, 2), list.get(1, 2));
        assertEquals(defaultList.get(2, 0), list.get(2, 0));
        assertFalse(list.isEmpty());
    }

    //##################################################
    // TwoDListOfLists(E[][])
    //##################################################

    @Test
    void constructorArrayNullTest() {
        assertThrows(NullPointerException.class, () -> new ArrayList2D<>((Object[][]) null));
    }

    @Test
    void constructorArrayOtherTest() {
        ArrayList2D<Object> list = defaultList;
        assertEquals(6, list.size());
        assertEquals(2, list.size(0));
        assertEquals(3, list.size(1));
        assertEquals(1, list.size(2));
        assertEquals(defaultArray[0][0], list.get(0, 0));
        assertEquals(defaultArray[0][1], list.get(0, 1));
        assertEquals(defaultArray[1][0], list.get(1, 0));
        assertEquals(defaultArray[1][1], list.get(1, 1));
        assertEquals(defaultArray[1][2], list.get(1, 2));
        assertEquals(defaultArray[2][0], list.get(2, 0));
        assertFalse(list.isEmpty());
    }


    //##################################################
    // iterator()
    //##################################################

    @Test
    void iteratorEmptyTest() {
        Iterator<Object> it = new ArrayList2D<>().iterator();
        assertNotNull(it);
        assertFalse(it.hasNext());
        assertThrows(NoSuchElementException.class, it::next);
    }

    @Test
    void iteratorSingleTest() {
        ArrayList2D<Object> list = new ArrayList2D<>();
        Object o1 = new Object();
        list.add(0, o1);
        int i = 0;
        Iterator<Object> iterator = list.iterator();
        while (iterator.hasNext()) {
            Object o = iterator.next();
            assertEquals(o, o1);
            i++;
        }
        assertEquals(1, i);
        assertFalse(iterator.hasNext());
    }

    @Test
    void iteratorMultipleTest() {
        ArrayList2D<Object> list = defaultList;
        Object[] oneDimArray = new Object[6];
        System.arraycopy(defaultArray[0], 0, oneDimArray, 0, 2);
        System.arraycopy(defaultArray[1], 0, oneDimArray, 2, 3);
        System.arraycopy(defaultArray[2], 0, oneDimArray, 5, 1);
        int i = 0;
        Iterator<Object> iterator = list.iterator();
        while (iterator.hasNext()) {
            Object o = iterator.next();
            assertEquals(oneDimArray[i++], o);
        }
        assertEquals(6, i);
        assertFalse(iterator.hasNext());
    }

    @Test
    void iteratorForTest() {
        ArrayList2D<Object> list = defaultList;
        Object[] oneDimArray = new Object[6];
        System.arraycopy(defaultArray[0], 0, oneDimArray, 0, 2);
        System.arraycopy(defaultArray[1], 0, oneDimArray, 2, 3);
        System.arraycopy(defaultArray[2], 0, oneDimArray, 5, 1);
        int i = 0;
        for (Object o : list) {
            assertEquals(oneDimArray[i++], o);
        }
        assertEquals(6, i);
    }

    //##################################################
    // size()
    //##################################################

    @Test
    void sizeTest() {
        assertEquals(6, defaultList.size());
        ArrayList2D<Object> list = new ArrayList2D<>();
        assertEquals(0, list.size());
        list.add(0, new Object());
        assertEquals(1, list.size());
        list.add(0, null);
        assertEquals(2, list.size());
        list.add(1, new Object());
        assertEquals(3, list.size());
        list.add(1, new Object());
        assertEquals(4, list.size());
        list.add(2, new Object());
        assertEquals(5, list.size());
        list.add(1, new Object());
        assertEquals(6, list.size());
    }

    //##################################################
    // get(int)
    //##################################################

    @Test
    void get1OutOfBoundsTest() {
        assertThrows(IndexOutOfBoundsException.class, () -> defaultList.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> defaultList.get(-42));
        assertThrows(IndexOutOfBoundsException.class, () -> defaultList.get(3));
        assertThrows(IndexOutOfBoundsException.class, () -> defaultList.get(42));
    }

    @Test
    void get1Test() {
        List<Object> row = defaultList.get(0);
        assertEquals(defaultArray[0][0], row.getFirst());
        assertEquals(defaultArray[0][1], row.get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> row.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> row.get(2));
    }

    //##################################################
    // get(int, int)
    //##################################################

    @Test
    void get2Test() {
        ArrayList2D<Object> list = new ArrayList2D<>();
        Object o1 = new Object();
        Object o2 = new Object();
        Object o3 = new Object();
        list.add(0, o1);
        list.add(0, o2);
        list.add(1, o3);
        assertEquals(o1, list.get(0, 0));
        assertEquals(o2, list.get(0, 1));
        assertEquals(o3, list.get(1, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(0, 2));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(2, 0));
    }

    //##################################################
    // set(int, int, E)
    //##################################################

    @Test
    void setTest() {
        ArrayList2D<Object> list = new ArrayList2D<>();
        Object o1 = new Object();
        Object o2 = new Object();
        Object o3 = new Object();
        list.add(0, o1);
        list.add(0, o2);
        list.add(1, o3);
        Object o4 = new Object();
        Object o5 = new Object();
        Object o6 = new Object();
        assertEquals(o1, list.set(0, 0, o4));
        assertEquals(o2, list.set(0, 1, o5));
        assertEquals(o3, list.set(1, 0, o6));
        assertEquals(o4, list.get(0, 0));
        assertEquals(o5, list.get(0, 1));
        assertEquals(o6, list.get(1, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> list.set(0, 2, o1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.set(2, 0, o2));
    }

    //##################################################
    // add(int, E)
    //##################################################

    @Test
    void addTest() {
        ArrayList2D<Object> list = new ArrayList2D<>();
        Object o1 = new Object();
        Object o2 = new Object();
        Object o3 = new Object();
        list.add(0, o1);
        list.add(0, o2);
        list.add(1, o3);
        assertEquals(o1, list.get(0, 0));
        assertEquals(o2, list.get(0, 1));
        assertEquals(o3, list.get(1, 0));
        Object o4 = new Object();
        Object o5 = new Object();
        Object o6 = new Object();
        list.add(0, o4);
        list.add(0, o5);
        list.add(1, o6);
        assertEquals(o1, list.get(0, 0));
        assertEquals(o2, list.get(0, 1));
        assertEquals(o4, list.get(0, 2));
        assertEquals(o5, list.get(0, 3));
        assertEquals(o3, list.get(1, 0));
        assertEquals(o6, list.get(1, 1));
    }

    //##################################################
    // remove(int, int)
    //##################################################

    @Test
    void removeTest() {
        ArrayList2D<Object> list = new ArrayList2D<>();
        Object o1 = new Object();
        Object o2 = new Object();
        Object o3 = new Object();
        list.add(0, o1);
        list.add(0, o2);
        list.add(1, o3);

        assertEquals(o1, list.remove(0, 0));
        assertEquals(o2, list.get(0, 0));
        assertEquals(o3, list.get(1, 0));

        assertEquals(o3, list.remove(1, 0));
        assertEquals(o2, list.get(0, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(1, 0));

        assertEquals(o2, list.remove(0, 0));
        assertTrue(list.isEmpty());
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(0, 0));
    }

    //##################################################
    // shortestList()
    //##################################################

    @Test
    void shortestRowEmptyTest() {
        ArrayList2D<Object> list = new ArrayList2D<>();
        assertEquals(0, list.shortestRow().size());
    }

    @Test
    void shortestRowTest() {
        ArrayList2D<Object> list = defaultList;
        assertEquals(1, list.shortestRow().size());
        assertEquals(defaultArray[2][0], list.shortestRow().getFirst());
    }

    //##################################################
    // longestList()
    //##################################################

    @Test
    void longestRowEmptyTest() {
        ArrayList2D<Object> list = new ArrayList2D<>();
        assertEquals(0, list.longestRow().size());
    }

    @Test
    void longestRowTest() {
        ArrayList2D<Object> list = defaultList;
        assertEquals(3, list.longestRow().size());
        assertEquals(defaultArray[1][0], list.longestRow().getFirst());
        assertEquals(defaultArray[1][1], list.longestRow().get(1));
        assertEquals(defaultArray[1][2], list.longestRow().get(2));
    }

    //##################################################
    // addToShortest(E)
    //##################################################

    @Test
    void addToShortestRowEmptyTest() {
        ArrayList2D<Object> list = new ArrayList2D<>();
        Object o1 = new Object();
        list.addToShortestRow(o1);
        assertEquals(1, list.size());
        assertEquals(1, list.size(0));
        assertEquals(o1, list.get(0, 0));
    }

    @Test
    void addToShortestRowTest() {
        ArrayList2D<Object> list = defaultList;
        Object o1 = new Object();
        list.addToShortestRow(o1);
        assertEquals(2, list.size(2));
        assertArrayEquals(defaultArray[0], list.get(0).toArray());
        assertArrayEquals(defaultArray[1], list.get(1).toArray());
        assertEquals(defaultArray[2][0], list.get(2, 0));
        assertEquals(o1, list.get(2, 1));
    }

    //##################################################
    // addToLongest(E)
    //##################################################

    @Test
    void addToLongestRowEmptyTest() {
        ArrayList2D<Object> list = new ArrayList2D<>();
        Object o1 = new Object();
        list.addToLongestRow(o1);
        assertEquals(1, list.size());
        assertEquals(1, list.size(0));
        assertEquals(o1, list.get(0, 0));
    }

    @Test
    void addToLongestRowTest() {
        ArrayList2D<Object> list = defaultList;
        Object o1 = new Object();
        list.addToLongestRow(o1);
        assertEquals(4, list.size(1));
        assertArrayEquals(defaultArray[0], list.get(0).toArray());
        assertArrayEquals(defaultArray[2], list.get(2).toArray());
        assertEquals(defaultArray[1][0], list.get(1, 0));
        assertEquals(defaultArray[1][1], list.get(1, 1));
        assertEquals(defaultArray[1][2], list.get(1, 2));
        assertEquals(o1, list.get(1, 3));
    }

}
