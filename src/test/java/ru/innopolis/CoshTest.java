package ru.innopolis;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CoshTest {

    private Cosh cosh = new Cosh();

    @Test
    void putGetSize() {

        // Positive tests

        // put
        assertEquals(null, cosh.put("1", "one"));
        assertEquals("one", cosh.get("1"));
        assertEquals(1, cosh.size());

        // put
        assertEquals(null, cosh.put("2", "two"));
        assertEquals("two", cosh.get("2"));
        assertEquals(2, cosh.size());

        // rewrite value
        assertEquals("one", cosh.put("1", "rewrite"));
        assertEquals("rewrite", cosh.get("1"));
        assertEquals(2, cosh.size());

        // collision
        assertEquals(null, cosh.put("49", "collision"));
        assertEquals("collision", cosh.get("49"));
        assertEquals(3, cosh.size());

        // other objects
        assertEquals(null, cosh.put(100, 500));
        assertEquals(500, cosh.get(100));

        // size test
        assertEquals(4, cosh.size());

        assertEquals(null, cosh.put(100L, 300d));
        assertEquals(300d, cosh.get(100L));
        assertEquals(5, cosh.size());

        // expansion test
        for (int i = 1; i < 300; i++) {
            assertEquals(null, cosh.put(i, "V_" + i));
        }

        // size test
        assertEquals(304, cosh.size());

        // other objects
        Cosh kCosh = new Cosh(1, 1);
        Cosh vCosh = new Cosh("1", "1");
        assertEquals(null, cosh.put(kCosh, vCosh));
        assertEquals(vCosh, cosh.get(kCosh));

        // Negative tests

        // no find key
        assertEquals(null, cosh.get("555"));

        // null key in put
        try {
            try {
                cosh.put(null, "others");
            } catch (IllegalArgumentException e) {
                assertTrue(true);
            }
        } catch (Throwable e) {
            assertTrue(false);
        }

        // null value in put
        try {
            try {
                cosh.put("555", null);
            } catch (IllegalArgumentException e) {
                assertTrue(true);
            }
        } catch (Throwable e) {
            assertTrue(false);
        }

        // null key in get
        try {
            try {
                cosh.get(null);
            } catch (IllegalArgumentException e) {
                assertTrue(true);
            }
        } catch (Throwable e) {
            assertTrue(false);
        }

    }

    @Test
    void remove() {

        assertEquals(null, cosh.put("0", "zero"));
        assertEquals(null, cosh.put("1", "one link"));
        // collision
        assertEquals(null, cosh.put("49", "two link"));
        // collision
        assertEquals(null, cosh.put("58", "three link"));
        // collision
        assertEquals(null, cosh.put("94", "four link"));
        assertEquals(null, cosh.put("2", "many"));

        assertEquals(6, cosh.size());

        // Positive tests

       // remove middle form collision (two link)
        assertEquals("two link", cosh.remove("49"));
        assertEquals("four link", cosh.get("94"));
        assertEquals("three link", cosh.get("58"));
        assertEquals("one link", cosh.get("1"));
        assertEquals(5, cosh.size());

        // remove last form collision (four link)
        assertEquals("four link", cosh.remove("94"));
        assertEquals("one link", cosh.get("1"));
        assertEquals("three link", cosh.get("58"));
        assertEquals(4, cosh.size());

        // remove first form collision (one link)
        assertEquals("one link", cosh.remove("1"));
        assertEquals(3, cosh.size());

        // remove all
        assertEquals("zero", cosh.remove("0"));
        assertEquals("many", cosh.remove("2"));
        assertEquals("three link", cosh.remove("58"));
        assertEquals(0, cosh.size());

        // Negative tests

        // no find key
        assertEquals(null, cosh.remove("nothing key"));

        // null key in remove
        try {
            try {
                assertEquals("zero", cosh.remove(null));
            } catch (IllegalArgumentException e) {
                assertTrue(true);
            }
        } catch (Throwable e) {
            assertTrue(false);
        }

        // попытка удалить запись,
        // у которой хэш ключа совпадает с корзиной,
        // содержащей несколько записей
        assertEquals(null, cosh.remove("85"));

    }

    @Test
    void containsKey(){

        assertEquals(null, cosh.put("0", "zero"));
        assertEquals(null, cosh.put("1", "one link"));
        assertEquals(null, cosh.put("49", "two link"));
        assertEquals(null, cosh.put("58", "three link"));
        assertEquals(null, cosh.put("94", "four link"));
        assertEquals(null, cosh.put("2", "many"));

        // Positive tests

        assertEquals(true, cosh.containsKey("0"));
        assertEquals(true, cosh.containsKey("49"));
        assertEquals(true, cosh.containsKey("58"));
        assertEquals(true, cosh.containsKey("94"));
        assertEquals(false, cosh.containsKey("nothing key"));

        // Negative tests

        // null key in containsKey
        try {
            try {
                assertEquals(false, cosh.containsKey(null));
            } catch (IllegalArgumentException e) {
                assertTrue(true);
            }
        } catch (Throwable e) {
            assertTrue(false);
        }

    }

}