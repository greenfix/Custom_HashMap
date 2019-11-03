package ru.innopolis;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CoshTest {

    private Cosh cosh = new Cosh();

    @Test
    void putGetSize() {

        // Positive tests

        assertNull(cosh.put(0, "0"));
        assertNull(cosh.put(null, "null"));
        assertEquals("null", cosh.put(null, "16"));
        assertEquals(2, cosh.size());
        assertEquals("16", cosh.get(null));
        assertEquals("16", cosh.remove(null));
        assertEquals("0", cosh.remove(0));
        assertEquals(0, cosh.size());

//        // put
        assertNull(cosh.put("1", "one"));
        assertEquals("one", cosh.get("1"));
        assertEquals(1, cosh.size());

        // put
        assertNull(cosh.put("2", "two"));
        assertEquals("two", cosh.get("2"));
        assertEquals(2, cosh.size());

//        // rewrite value
        assertEquals("one", cosh.put("1", "rewrite"));
        assertEquals("rewrite", cosh.get("1"));
        assertEquals(2, cosh.size());

        // collision
        assertNull(cosh.put("10", "collision"));
        assertEquals("collision", cosh.get("10"));
        assertEquals(3, cosh.size());

        // other objects
        assertNull(cosh.put("100", 500));
        assertEquals(500, cosh.get("100"));

        // size test
        assertEquals(4, cosh.size());

        assertNull(cosh.put(100L, 300d));
        assertEquals(300d, cosh.get(100L));
        assertEquals(5, cosh.size());

        assertNull(cosh.put(null, "null"));
        assertEquals("null", cosh.get(null));
        assertEquals(6, cosh.size());

        assertNull(cosh.put("null", null));
        assertNull(cosh.get("null"));
        assertEquals(7, cosh.size());

        assertEquals("null", cosh.put(null, null));
        assertEquals(7, cosh.size());

        // expansion test
        int maxTest = 200;
        for (int i = 1; i <= maxTest; i++) {
            assertNull(cosh.put(i, "V_" + i));
        }

        // size test
        assertEquals(maxTest + 7, cosh.size());

        for (int i = 1; i <= maxTest; i++) {
            assertEquals("V_" + i, cosh.get(i));
        }

        // other objects
        Cosh kCosh = new Cosh(12, 0.5f);
        Cosh vCosh = new Cosh(2, 1f);
        assertNull(cosh.put(kCosh, vCosh));
        assertEquals(vCosh, cosh.get(kCosh));

        // Negative tests

        // no find key
        assertNull(cosh.get("555"));

    }

    @Test
    void remove() {

        assertNull(cosh.put("0", "zero"));
        assertNull(cosh.put("1", "one link"));
        // collision
        assertNull(cosh.put("10", "two link"));
        // collision
        assertNull(cosh.put("69", "three link"));
        // collision
        assertNull(cosh.put("78", "four link"));
        assertNull(cosh.put("2", "many"));

        assertNull(cosh.put("null", null));
        assertNull(cosh.put(null, "null"));

        assertEquals(8, cosh.size());

        // Positive tests

        // попытка удалить запись,
        // у которой хэш ключа совпадает с корзиной,
        // содержащей несколько записей
        assertNull(cosh.remove("96"));
        assertEquals(8, cosh.size());

        // remove middle form collision (two link)
        assertEquals("two link", cosh.remove("10"));
        assertEquals("four link", cosh.get("78"));
        assertEquals("three link", cosh.get("69"));
        assertEquals("one link", cosh.get("1"));
        assertEquals(7, cosh.size());

        // remove last form collision (four link)
        assertEquals("four link", cosh.remove("78"));
        assertEquals("one link", cosh.get("1"));
        assertEquals("three link", cosh.get("69"));
        assertEquals(6, cosh.size());

        // remove first form collision (one link)
        assertEquals("one link", cosh.remove("1"));
        assertEquals(5, cosh.size());

        // remove all
        assertEquals("zero", cosh.remove("0"));
        assertEquals("many", cosh.remove("2"));
        assertEquals("three link", cosh.remove("69"));
        assertEquals(2, cosh.size());

        assertEquals("null", cosh.remove(null));
        assertEquals(1, cosh.size());

        assertNull(cosh.remove("null"));
        assertEquals(0, cosh.size());

        assertNull(cosh.put("null", null));
        assertNull(cosh.put(null, "null"));
        assertEquals("null", cosh.put(null, null));
        assertEquals(2, cosh.size());
        assertNull(cosh.remove(null));
        assertEquals(1, cosh.size());
        assertNull(cosh.remove("null"));
        assertEquals(0, cosh.size());

        // Negative tests

        // no find key
        assertNull(cosh.remove("nothing key"));

    }

    @Test
    void containsKey() {

        // Positive tests

        assertNull(cosh.put("0", "zero"));
        assertNull(cosh.put("1", "one link"));
        assertNull(cosh.put("49", "two link"));
        assertNull(cosh.put("58", "three link"));
        assertNull(cosh.put("94", "four link"));
        assertNull(cosh.put("2", "many"));

        assertTrue(cosh.containsKey("0"));
        assertTrue(cosh.containsKey("49"));
        assertTrue(cosh.containsKey("58"));
        assertTrue(cosh.containsKey("94"));
        assertFalse(cosh.containsKey("nothing key"));

        assertFalse(cosh.containsKey(null));

        assertNull(cosh.put(null, "null"));
        assertNull(cosh.put("null", null));

        assertTrue(cosh.containsKey(null));
        assertTrue(cosh.containsKey("null"));

        assertEquals("null", cosh.put(null, null));
        assertTrue(cosh.containsKey(null));

    }

}