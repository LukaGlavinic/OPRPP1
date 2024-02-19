package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class SimpleHashtableTest {

	@Test
	public void constructor0Test() {
		SimpleHashtable<String, Integer> mapa = new SimpleHashtable<>();
		assertEquals(0, mapa.size());
	}
	
	@Test
	public void constructorTest() {
		SimpleHashtable<String, Integer> mapa = new SimpleHashtable<>(2);
		assertEquals(0, mapa.size());
	}
	
	@Test
	public void putTest() {
		SimpleHashtable<String, Integer> mapa = new SimpleHashtable<>(2);
		assertEquals(0, mapa.size());
		mapa.put("Marko", 5);
		assertEquals(1, mapa.size());
		assertEquals("[Marko=5]", mapa.toString());
	}
	
	@Test
	public void put2Test() {
		SimpleHashtable<String, Integer> mapa = new SimpleHashtable<>(2);
		assertEquals(0, mapa.size());
		mapa.put("Marko", 5);
		mapa.put("Ivica", 3);
		mapa.put("Josip", 2);
		assertEquals(3, mapa.size());
		assertEquals("[Josip=2, Marko=5, Ivica=3]", mapa.toString());
	}
	
	@Test
	public void toStringTest() {
		SimpleHashtable<String, Integer> mapa = new SimpleHashtable<>(2);
		assertEquals(0, mapa.size());
		assertEquals("[]", mapa.toString());
	}
	
	@Test
	public void toString2Test() {
		SimpleHashtable<String, Integer> mapa = new SimpleHashtable<>(2);
		assertEquals(0, mapa.size());
		mapa.put("Marko", 5);
		mapa.put("Ivica", 3);
		mapa.put("Josip", 2);
		assertEquals(3, mapa.size());
		assertEquals("[Josip=2, Marko=5, Ivica=3]", mapa.toString());
	}
	
	@Test
	public void clearTest() {
		SimpleHashtable<String, Integer> mapa = new SimpleHashtable<>(2);
		assertEquals(0, mapa.size());
		mapa.put("Marko", 5);
		mapa.put("Ivica", 3);
		mapa.put("Josip", 2);
		assertEquals(3, mapa.size());
		assertEquals("[Josip=2, Marko=5, Ivica=3]", mapa.toString());
		mapa.clear();
		assertEquals(0, mapa.size());
		assertEquals("[]", mapa.toString());
	}
	
	@Test
	public void isEmptyTest() {
		SimpleHashtable<String, Integer> mapa = new SimpleHashtable<>(2);
		assertEquals(0, mapa.size());
        assertTrue(mapa.isEmpty());
	}
	
	@Test
	public void isEmpty2Test() {
		SimpleHashtable<String, Integer> mapa = new SimpleHashtable<>(2);
		mapa.put("Marko", 5);
		mapa.put("Ivica", 3);
		mapa.put("Josip", 2);
		assertEquals(3, mapa.size());
        assertFalse(mapa.isEmpty());
	}
	
	@Test
	public void containsKeyTest() {
		SimpleHashtable<String, Integer> mapa = new SimpleHashtable<>(2);
		mapa.put("Marko", 5);
		mapa.put("Ivica", 3);
		mapa.put("Josip", 2);
		assertEquals(3, mapa.size());
        assertFalse(mapa.containsKey("Luka"));
	}
	
	@Test
	public void containsKey2Test() {
		SimpleHashtable<String, Integer> mapa = new SimpleHashtable<>(2);
		mapa.put("Marko", 5);
		mapa.put("Ivica", 3);
		mapa.put("Josip", 2);
		assertEquals(3, mapa.size());
        assertTrue(mapa.containsKey("Marko"));
	}
	
	@Test
	public void containsValueTest() {
		SimpleHashtable<String, Integer> mapa = new SimpleHashtable<>(2);
		mapa.put("Marko", 5);
		mapa.put("Ivica", 3);
		mapa.put("Josip", 2);
		assertEquals(3, mapa.size());
        assertFalse(mapa.containsValue(6));
	}
	
	@Test
	public void containsValue2Test() {
		SimpleHashtable<String, Integer> mapa = new SimpleHashtable<>(2);
		mapa.put("Marko", 5);
		mapa.put("Ivica", 3);
		mapa.put("Josip", 2);
		assertEquals(3, mapa.size());
        assertTrue(mapa.containsValue(3));
	}
	
	@Test
	public void sizeTest() {
		SimpleHashtable<String, Integer> mapa = new SimpleHashtable<>(2);
		assertEquals(0, mapa.size());
	}
	
	@Test
	public void size2Test() {
		SimpleHashtable<String, Integer> mapa = new SimpleHashtable<>(2);
		mapa.put("Marko", 5);
		mapa.put("Ivica", 3);
		mapa.put("Josip", 2);
		assertEquals(3, mapa.size());
	}
	
	@Test
	public void getTest() {
		SimpleHashtable<String, Integer> mapa = new SimpleHashtable<>(2);
		mapa.put("Marko", 5);
		mapa.put("Ivica", 3);
		mapa.put("Josip", 2);
        assertNull(mapa.get(null));
	}
	
	@Test
	public void get2Test() {
		SimpleHashtable<String, Integer> mapa = new SimpleHashtable<>(2);
		mapa.put("Marko", 5);
		mapa.put("Ivica", 3);
		mapa.put("Josip", 2);
		assertEquals(2, mapa.get("Josip"));
	}
	
	@Test
	public void removeTest() {
		SimpleHashtable<String, Integer> mapa = new SimpleHashtable<>(2);
		mapa.put("Marko", 5);
		mapa.put("Ivica", 3);
		mapa.put("Josip", 2);
		assertEquals(2, mapa.remove("Josip"));
		assertEquals(2, mapa.size());
		assertEquals("[Marko=5, Ivica=3]", mapa.toString());
	}
	
	@Test
	public void remove2Test() {
		SimpleHashtable<String, Integer> mapa = new SimpleHashtable<>(2);
		mapa.put("Marko", 5);
		mapa.put("Ivica", 3);
		mapa.put("Josip", 2);
        assertNull(mapa.remove(null));
	}
}